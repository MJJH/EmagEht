/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import display.Animation;
import display.IntColor;
import display.Parts;
import display.Sets;
import display.Skin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import thegame.com.Game.Crafting;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.ObjectType;
import thegame.com.Menu.Message;
import thegame.config;

/**
 *
 * @author Martijn
 */
public class GameUtilities {
    private GraphicsContext g;
    private Player me;
    private Map play;
    private Scene s;
    
    private boolean inventory,
                    chat,
                    lines;
    
    private String typing;
    
    // Drawing
    private int startX,
                startY;
    
    private Color guiBackground;
    private Color guiFontColor;
    private Font guiFont;
    
    private display.Image shield,
                          head,
                          body,
                          greaves,
                          heart;
    
    private Timer notificationTimer;
    
    // Crafting
    private int selected;
    private int offset;
                         
    
    public GameUtilities (Player me, Map playing, GraphicsContext g, Scene scene) {
        this.me = me;
        this.g = g;
        play = playing;
        s = scene;
        
        guiBackground = new Color(0.35f, 0.35f, 0.35f, 1f);
        guiFontColor = Color.WHITE;
        guiFont = Font.font("monospaced", 10);
        
        notificationTimer = new Timer();
        typing = "~";
        inventory = chat = lines = false;
        startX = startY = 0;
        try {
             Color[] t = new Color[]
            {
                new Color(0, 0, 0, 0.3), new Color(0.2, 0.2, 0.2, 0.3), new Color(0.4, 0.4, 0.4, 0.3), new Color(0.6, 0.6, 0.6, 0.3), new Color(0.8, 0.8, 0.8, 0.3), new Color(1, 1, 1, 0.3)
            };
            heart = new display.Image(Sets.sets.get("heart"));
            shield = new display.Image(Sets.sets.get("shield"));
            shield.recolour(t);
            head = new display.Image(Parts.parts.get("playerHead"));
            head.recolour(t);
            body = new display.Image(Sets.sets.get("tShirt"));
            body.recolour(t);
            greaves = new display.Image(Sets.sets.get("shorts"));
            greaves.recolour(t);
        } catch (IOException ex) {
        }
    }
    
    public int[] getStartXstartY() {
        return new int[] { startX, startY };
    }
    
    public void setChat(String chat)
    {
        typing = chat;
        this.chat = true;
    }
    
    public void closeChat()
    {
        typing = "~";
        this.chat = false;
    }
    
    public boolean isInventory()
    {
        return inventory;
    }
    
    public boolean toggleInventory() {
        return inventory = !inventory;
    }
    
    public boolean isChat()
    {
        return chat;
    }
    
    public boolean toggleChat() {
        if(chat && typing != "~") {
            typing = "~";
        }
        return chat = !chat;
    }
    
    public void drawMap() {
        // Clear sceen
        g.clearRect(0, 0, s.getWidth(), s.getHeight());
        
        // Set player variables (So they can't change mid method)
        float pX = me.getX();
        float pY = me.getY();
        float pW = me.getW();
        float pH = me.getH();
        
        // Get world size
        int playH = play.getHeight();
        int playW = play.getWidth();
        
        // Get viewable blocks
        List<MapObject> view = getViewable();
        
        // Calculate players offset
        float[] dydx = calculateDXDY(playW, playH, pX, pY, pW, pH);
        float dx = dydx[0];
        float dy = dydx[1];
        
        for (MapObject draw : view) 
        {
            float x, y;
            if(draw == me) 
            {
                x = pX;
                y = pY;
            } else
            {
                x = draw.getX();
                y = draw.getY();
            }
            
            x = (x + startX) * config.block.val - dx;
            y = ((float) s.getHeight() - (y + startY) * config.block.val + dy);
                  
            drawObject(draw, x, y);
            
            if(draw instanceof CharacterGame && !draw.equals(me)) 
            {
                drawHealth(draw, x, y);
            }
        }
        
        drawInfo();
        
        if(inventory) 
        {
            drawInventory();
            drawCrafting();
            drawArmor();
        }
        
        if (inventory || me.getHolding() != null)
        {
            drawTool();
        }
        
        if(chat)
        {
            drawChat();
        }
        
        if(lines)
        {
            g.beginPath();
            g.setLineWidth(1);
            g.setStroke(Color.rgb(0, 0, 0, 0.2));
            g.strokeLine(s.getWidth() / 2, 0, s.getWidth() / 2, s.getHeight());
            g.strokeLine(0, s.getHeight() / 2, s.getWidth(), s.getHeight() / 2);
            g.closePath();
        }
    }
    
    private void drawChat()
    {
        g.beginPath();
        g.setFill(guiBackground);

        g.setFont(guiFont);
        int RectHeight = 250;
        int RectWidth = 400;
        g.fillRoundRect(10, (s.getHeight() - RectHeight) - 10, RectWidth, RectHeight, 5, 5);        
        
        g.setStroke(Color.BLACK);
        g.strokeRoundRect(9, (s.getHeight() - RectHeight) - 11, RectWidth + 2, RectHeight + 2, 5, 5);
        g.strokeRoundRect(11, (s.getHeight() - RectHeight) - 9, RectWidth - 2, RectHeight - 2, 5, 5);
        
        g.setStroke(Color.WHITE);
        g.strokeRoundRect(10, (s.getHeight() - RectHeight) - 10, RectWidth, RectHeight, 5, 5);

        int textPosition = 10;
        List<Message> chatMessages = play.getChatMessages();
        if (chatMessages.size() < 15)
        {
            for (Message message : chatMessages)
            {
                g.strokeText((message.getSender().getUsername() + ": " + message.getText()), 15, (s.getHeight() - RectHeight) + textPosition);
                textPosition += 15;
            }
        } else
        {
            chatMessages.remove(0);
        }
        if (!typing.equals("~"))
        {
            g.setFont(Font.font("monospaced", 11));
            g.setFill(IntColor.rgb(50, 50, 50));
            g.fillRoundRect(12, (s.getHeight() - 29), RectWidth - 4, 17, 5, 5);
            g.strokeText(typing, 15, s.getHeight() - 17);
            g.setStroke(Color.BLACK);
            g.strokeLine(11, (s.getHeight() - 29), RectWidth+9, (s.getHeight() - 29));
            g.setStroke(Color.WHITE);
            g.strokeLine(11, (s.getHeight() - 28), RectWidth+9, (s.getHeight() - 28));
            g.closePath();
        } else 
        {
            notificationTimer.schedule(new TimerTask() {
                @Override
                public void run()
                {
                    closeChat();
                }
            }, 3000);
        }
        g.closePath();
    }
    
    private void drawInfo()
    {
        g.beginPath();
        g.setFill(guiBackground);
        g.fillRoundRect(s.getWidth() - 130, 10, 120f, 60.0f, 5, 5);
        
        g.setStroke(Color.BLACK);
        g.strokeRoundRect(s.getWidth() - 131, 9, 122f, 62.0f, 5, 5);
        g.strokeRoundRect(s.getWidth() - 129, 11, 118f, 58.0f, 5, 5);
        
        g.setStroke(Color.WHITE);
        g.strokeRoundRect(s.getWidth() - 130, 10, 120f, 60.0f, 5, 5);

        //draw hearth
        for (int i = 0; i < play.getLifes(); i++)
        {
            g.drawImage(heart.show(), ((s.getWidth() - 109) + (21 * i)), 28);
        }

        // draw health
        g.setFill(guiFontColor);
        g.setFont(guiFont);
        g.fillText("TEAM LIFES", s.getWidth() - 100, 21);

        g.setStroke(Color.BLACK);
        g.strokeRect(s.getWidth() - 121, 50, 102.0f, 13.0f);

        g.setFill(Color.RED);
        g.fillRect(s.getWidth() - 120, 51, 100.0f, 11.0f);

        g.setFill(Color.GREEN);
        int hp = me.getHP();
        int maxhp = me.getMaxHP();
        double breedte = (double) hp / maxhp;
        breedte = breedte * 100;
        g.fillRect(s.getWidth() - 120, 51, breedte, 11.0f);
        g.closePath();
    }
    
    private void drawTool()
    {
        g.beginPath();
        g.setFill(guiBackground);
        g.fillRoundRect(s.getWidth() - 50, s.getHeight() - 50, 40, 40, 5, 5);        
        
        g.setStroke(Color.BLACK);
        g.strokeRoundRect(s.getWidth() - 51, s.getHeight() - 51, 42f, 42.0f, 5, 5);
        g.strokeRoundRect(s.getWidth() - 49, s.getHeight() - 49, 38f, 38.0f, 5, 5);
        
        g.setStroke(Color.WHITE);
        g.strokeRoundRect(s.getWidth() - 50, s.getHeight() - 50, 40, 40, 5, 5);

        if (me.getHolding() != null)
        {
            display.Skin i = me.getHolding().getSkin();
            g.setFill(Color.RED);
            if (i == null)
            {
                g.setFill(Color.RED);
                g.fillRect(s.getWidth() - 50 + 10, s.getHeight() - 50 + 10, 20, 20);
            } else
            {
                g.drawImage(i.show(), s.getWidth() - 50 + (40 - i.getWidth()) / 2, s.getHeight() - 50 + (40 - i.getHeight()) / 2);
            }
        }
        g.closePath();
    }
    
    private void drawArmor()
    {
        g.beginPath();
        for (int y = 0; y < 4; y++)
        {
            g.setFill(guiBackground);
            g.fillRoundRect(s.getWidth() - 50, s.getHeight() - 100 - 50 * y, 40, 40, 5, 5);            
            
            g.setStroke(Color.BLACK);
            g.strokeRoundRect(s.getWidth() - 51, s.getHeight() - 1 - 100 - 50 * y, 42f, 42.0f, 5, 5);
            g.strokeRoundRect(s.getWidth() - 49, s.getHeight() + 1 - 100 - 50 * y, 38f, 38.0f, 5, 5);
            
            g.setStroke(Color.WHITE);
            g.strokeRoundRect(s.getWidth() - 50, s.getHeight() - 100 - 50 * y, 40, 40, 5, 5);
            
            display.Image i;

            switch (y)
            {
                case 0:
                    if (me.getArmor().get(ArmorType.bodyPart.SHIELD) != null)
                    {
                        i = (display.Image) me.getArmor().get(ArmorType.bodyPart.SHIELD).getSkin();
                    } else
                    {
                        i = shield;
                    }
                    break;
                case 1:
                    if (me.getArmor().get(ArmorType.bodyPart.GREAVES) != null)
                    {
                        i = (display.Image) me.getArmor().get(ArmorType.bodyPart.GREAVES).getSkin();
                    } else
                    {
                        i = greaves;
                    }
                    break;
                case 2:
                    if (me.getArmor().get(ArmorType.bodyPart.CHESTPLATE) != null)
                    {
                        i = (display.Image) me.getArmor().get(ArmorType.bodyPart.CHESTPLATE).getSkin();
                    } else
                    {
                        i = body;
                    }
                    break;
                default:
                    if (me.getArmor().get(ArmorType.bodyPart.HELMET) != null)
                    {
                        i = (display.Image) me.getArmor().get(ArmorType.bodyPart.HELMET).getSkin();
                    } else
                    {
                        i = head;
                    }
            }

            if (i == null)
            {
                g.setFill(Color.RED);
                g.fillRect(s.getWidth() - 50 + 10, s.getHeight() - 100 - 50 * y + 10, 20, 20);
            } else
            {
                g.drawImage(i.show(), s.getWidth() - 50 + (40 - i.getWidth()) / 2, s.getHeight() - 100 - 50 * y + (40 - i.getHeight()) / 2);
            }
        }
        g.closePath();
    }
    
    private void drawInventory() 
    {
        g.beginPath();
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 10; x++)
            {
                g.setFill(guiBackground);
                g.fillRoundRect(10 + 50 * x, 10 + 50 * y, 40, 40, 5, 5);                
                
                g.setStroke(Color.BLACK);
                g.strokeRoundRect(-1 + 10 + 50 * x, -1 + 10 + 50 * y, 42, 42, 5, 5);
                g.strokeRoundRect(1 + 10 + 50 * x, 1 + 10 + 50 * y, 38, 38, 5, 5);
                
                g.setStroke(Color.WHITE);
                g.strokeRoundRect(10 + 50 * x, 10 + 50 * y, 40, 40, 5, 5);

                int spot = y * 10 + x;
                if (me.getBackpackMap()[spot] != null && !me.getBackpackMap()[spot].isEmpty())
                {
                    Skin i = me.getBackpackMap()[spot].get(0).getSkin();
                    if (i != null)
                    {
                        g.drawImage(i.show(), 10 + 50 * x + (40 - i.getWidth()) / 2, 10 + 50 * y + (40 - i.getHeight()) / 2);
                    } else
                    {
                        g.setFill(Color.RED);
                        g.fillRect(10 + 50 * x + 10, 10 + 50 * y + 10, 20, 20);
                    }

                    if (me.getBackpackMap()[spot].size() > 1)
                    {
                        g.setFill(guiFontColor);
                        g.setFont(guiFont);
                        String t = me.getBackpackMap()[spot].size() + "";
                        g.fillText(t, 10 + 50 * x + 40 - ((t.length() - 1) * 5) - 8, 10 + 50 * y + 38);
                    }
                }

            }
        }
        g.closePath();
    }
    
    private void drawHealth(MapObject mo, float x, float y)
    {
        g.beginPath();
        CharacterGame character = (CharacterGame) mo;
        g.strokeRect(x - 1, y - 10, mo.getW() * config.block.val + 2, 5.0f);
        g.setFill(Color.RED);
        g.fillRect(x, y - 9, mo.getW() * config.block.val, 3.0f);
        g.setFill(Color.GREEN);
        int hp = character.getHP();
        int maxhp = character.getMaxHP();
        double breedte = (double) hp / maxhp;
        breedte = breedte * mo.getW() * config.block.val;
        g.fillRect(x, y - 9, breedte, 3.0f);
        g.closePath();
    }
    
    private void drawObject(MapObject mo, float x, float y) 
    {
        g.beginPath();
        Skin s = mo.getSkin();
        if (s == null)
        {
            g.setFill(Color.RED);
            g.fillRect(x, y, mo.getW() * config.block.val, mo.getH() * config.block.val);
        } else
        {
            float divX = ((mo.getW() * config.block.val) - s.getWidth()) / 2;
            float divY = ((mo.getH() * config.block.val) - s.getHeight());
        
            if(s instanceof display.Image || ((Animation) s).getFrame() == null)
            {
                g.drawImage(s.show(), x + divX, y + divY, s.getWidth(), s.getHeight());
            } else
            {
                Animation a = (Animation) s;
                g.drawImage(a.show(), x + divX - a.getFrame().getOffsetLeft(), y + divY - a.getFrame().getOffsetTop());
            }
        }
        g.closePath();
    }
    
    private List<MapObject> getViewable() 
    {
        // Get amount of blocks that need te be loaded 
        int blockHorizontal = (int) Math.ceil(s.getWidth() / config.block.val) + 4;
        int blockVertical = (int) Math.ceil(s.getHeight() / config.block.val) + 4;

        // Calculate the mid position of the player
        int midX = (int) Math.floor(me.getX() + (me.getW() / 2));
        int midY = (int) Math.ceil(me.getY() - (me.getH() / 2));

        // Calculate at what block we should start drawing (the player object should be centered)
        startX = (int) Math.ceil(midX - blockHorizontal / 2);
        startY = (int) Math.ceil(midY - blockVertical / 2);
        // And what will the ending blocks be
        int endX = startX + blockHorizontal;
        int endY = startY + blockVertical;

        // If we are on the left side on the map, draw the map more to the right
        while (startX < 0)
        {
            startX++;
            endX++;
        }
        // If we are to the top side of the map, draw the map more to the bottom
        while (startY < 0)
        {
            startY++;
            endY++;
        }
        // If we are on the right side of the map, draw the map more to the left
        while (endX > play.getWidth())
        {
            startX--;
            endX--;
        }
        // If we are to the bottom side of the map, draw the map more to the top
        while (endY > play.getWidth())
        {
            startY--;
            endY--;
        }
        // If there are less blocks than could be displayed, just display less
        if (startX <= 0 && endX >= play.getWidth())
        {
            startX = 0;
            endX = play.getWidth();
        }
        // Same for height
        if (startY <= 0 && endY >= play.getHeight())
        {
            startY = 0;
            endY = play.getHeight();
        }

        // Ask the map for the blocks and objects that should be drawn in this area.
        return play.getBlocksAndObjects(startX, startY, endX, endY);
    }
    
    public float[] calculateDXDY(int w, int h, float px, float py, float pw, float ph) 
    {
        // Get amount of blocks that fit on screen
        int blockHorizontal = (int) Math.ceil(s.getWidth() / config.block.val) + 4;
        int blockVertical = (int) Math.ceil(s.getHeight() / config.block.val) + 4;
        
        // preset Delta values
        float dx = 0;
        float dy = 0;

        // once the player's center is on the middle
        if ((px + pw / 2) * config.block.val > s.getWidth() / 2 && (px + pw / 2) * config.block.val < w * config.block.val - s.getWidth() / 2)
        {
            // DX will be the center of the map
            dx = (px + pw / 2) * config.block.val;
            dx -= (s.getWidth() / 2);

            // If you are no longer on the right side of the map
            if (startX >= 0)
            {
                dx += (startX) * config.block.val;
            }
        } else if ((px + pw / 2) * config.block.val >= w * config.block.val - s.getWidth() / 2)
        {
            // Dit is het goede moment. Hier moet iets gebeuren waardoor de aller laatste blok helemaal rechts wordt getekent en niet meer beweegt
            dx = (w - blockHorizontal + 2) * config.block.val * 2;
        }

        // once the player's center is on the middle
        if ((py - ph / 2) * config.block.val > s.getHeight() / 2
                && (py - ph / 2) * config.block.val < h * config.block.val - s.getHeight() / 2)
        {
            // DX will be the center of the map
            dy = (py - ph / 2) * config.block.val;
            dy -= (s.getHeight() / 2);

            // If you are no longer on the right side of the map
            if (startX >= 0)
            {
                dy += (startY) * config.block.val;
            }
        } else if ((py - ph / 2) * config.block.val >= h * config.block.val - s.getHeight() / 2)
        {
            // Dit is het goede moment. Hier moet iets gebeuren waardoor de aller laatste blok helemaal rechts wordt getekent en niet meer beweegt
            dy = (h - blockVertical + 2) * config.block.val * 2;
        }
        
        
        if(w < blockHorizontal - 4) {
            dx = (float) (s.getWidth() / 2 - ((w / 2) * config.block.val)) * -1;
        }
        if(h < blockVertical - 4) {
            dy = (float) (s.getHeight() / 2 - ((h / 2) * config.block.val)) * -1;
        }
        
        return new float[] { dx, dy };
    }

    private void drawCrafting() {
        g.beginPath();
        
        // Get craftings
        //List<Crafting> toCraft = me.getCrafting();
        List<Crafting> toCraft = new ArrayList<>(5);
        
        int maxX = toCraft.size();
        if(maxX > 10)
            maxX = 10;
        
        for (int x = 0; x < maxX; x++)
        {
            g.setFill(guiBackground);
            g.fillRoundRect(10 + 50 * x, 200, 40, 40, 5, 5);                

            g.setStroke(Color.BLACK);
            g.strokeRoundRect(-1 + 10 + 50 * x, -1 + 200, 42, 42, 5, 5);
            g.strokeRoundRect(1 + 10 + 50 * x, 1 + 200, 38, 38, 5, 5);

            if(x == selected)
                g.setStroke(Color.BLACK);
            else
                g.setStroke(Color.WHITE);
            g.strokeRoundRect(10 + 50 * x, 200, 40, 40, 5, 5);
            
            Skin i = toCraft.get(x + offset).crafting.skin;
            if (i != null)
            {
                g.drawImage(i.show(), 10 + 50 * x + (40 - i.getWidth()) / 2, 200 + (40 - i.getHeight()) / 2);
            } else
            {
                g.setFill(Color.RED);
                g.fillRect(10 + 50 * x + 10, 200 + 10, 20, 20);
            }
        }
        
        if(selected + offset - 1 >= toCraft.size() && toCraft.get(selected + offset) != null && toCraft.get(selected + offset).recources != null)
        {
            for (int y = 0; y < toCraft.get(selected + offset).recources.size(); y++)
            {
                g.setFill(guiBackground);
                g.fillRoundRect(10 + 50 * (selected), 245 + y * 35, 30, 30, 5, 5);                

                g.setStroke(Color.BLACK);
                g.strokeRoundRect(-1 + 10 + 50 * (selected), -1 + 245 + y * 35, 32, 32, 5, 5);
                g.strokeRoundRect(1 + 10 + 50 * (selected), 1 + 245 + y * 35, 28, 28, 5, 5);

                g.setStroke(Color.BLACK);
                g.strokeRoundRect(10 + 50 * (selected), 245 + y * 35, 30, 30, 5, 5);
                
                Skin i = ((ObjectType) toCraft.get(selected + offset).recources.keySet().toArray()[y]).skin;
                if (i != null)
                {
                    g.drawImage(i.show(), 10 + 50 * selected + (30 - i.getWidth()) / 2, 245 + y * 35 + (30 - i.getHeight()) / 2);
                } else
                {
                    g.setFill(Color.RED);
                    g.fillRect(10 + 50 * selected + 5, 245 + y * 35 + 5, 20, 20);
                }

                if ((Integer) toCraft.get(selected + offset).recources.values().toArray()[y] > 1)
                {
                    g.setFill(guiFontColor);
                    g.setFont(guiFont);
                    String t = (Integer) toCraft.get(selected + offset).recources.values().toArray()[y] + "";
                    g.fillText(t, 10 + 50 * selected + 30 - ((t.length() - 1) * 5) - 8, 245 * y + 38);
                }
            }
        }
        
        
        g.closePath();
    }
}
