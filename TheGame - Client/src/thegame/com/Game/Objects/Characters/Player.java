package thegame.com.Game.Objects.Characters;

import display.Animation;
import display.Skin;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javax.transaction.xa.XAException;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.MapObject;
import thegame.shared.iGameLogic;



/**
 *
 * @author laure
 */
public class Player extends CharacterGame {
    private static final long serialVersionUID = 6629685098267757690L;
    private boolean connected;
    private int spawnX;
    private int spawnY;
    
    private HashMap<String, Skin> skins;

    /**
     * This constructor will create an player
     * @param character, 
     * @param name, Name of the player
     * @param hp, HP of the player
     * @param skills, Skills of the player
     * @param attacks, Attacks of the player
     * @param x, X-coordinate of the player
     * @param y, Y-coordinate of the player
     * @param skin, Skin of the player
     * @param height, height of the player
     * @param width, width of the player
     * @param map
     */
    public Player(Character character, String name, int hp, java.util.Map<SkillType, Integer> skills, AttackType[] attacks, float x, float y, Skin skin, float height, float width, Map map) throws RemoteException
    {
        super(name, hp, skills, x, y, skin, height, width, map);
    }

    /**
     * This will spawn the player in the game on a location.
     */
    public void spawn()
    {
        // TODO - implement Player.spawn
        throw new UnsupportedOperationException();
    }

    /**
     * THis method will level the Player up by 1 level.
     */
    public void LevelUp()
    {
        // TODO - implement Player.LevelUp
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Boolean call() 
    {
        EnumMap<sides, List<MapObject>> collision = collision();
        Boolean ret = false;
        if(fall(collision))
            ret = true;
        
        if(moveH(collision))
            ret = true;
        
        if(moveV(collision))
            ret = true;
        
        while(!collision.get(sides.CENTER).isEmpty()) {
            yPosition++;
            collision = collision();
            ret = true;
        }
        
        return ret;
    }
    
    public boolean useTool(float x, float y, iGameLogic gameLogic)
    {
        if (System.currentTimeMillis() - used >= holding.type.speed)
        {
            try
            {
                return gameLogic.useTool(id, x, y);
            } catch (RemoteException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        return false;
    }

    public void setCords(float x, float y)
    {
        xPosition = x;
        yPosition = y;
    }
    
    @Override
    public Skin getSkin() {
        if(skins == null)
            return null;
        
        if(direction == sides.RIGHT)
            return skins.get("standRight");
        else
            return skins.get("standLeft");
    }

    @Override
    public void createSkin() {
        
        try {
            skins = new HashMap<>();
            display.Image i = new display.Image(43, 24);
            String pathname = "src/resources//player.png";
            i.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-40);
            i.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            display.Image i3 = new display.Image(43, 24);
            i3.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i3.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i3.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i3.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i3.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-50);
            i3.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i3.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            display.Image i4 = new display.Image(43, 24);
            i4.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i4.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i4.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i4.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i4.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-40);
            i4.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i4.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            display.Image i5 = new display.Image(43, 24);
            i5.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i5.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i5.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i5.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i5.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-30);
            i5.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i5.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            Animation d = new Animation(3);
            d.addFrame(i);
            d.addFrame(i3);
            d.addFrame(i4);
            d.addFrame(i5);
            skins.put("standRight", d);
            
            display.Image i6 = new display.Image(43, 24);
            i6.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i6.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i6.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i6.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i6.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-40);
            i6.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i6.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            display.Image i7 = new display.Image(43, 24);
            i7.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i7.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i7.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i7.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i7.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-50);
            i7.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i7.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            display.Image i8 = new display.Image(43, 24);
            i8.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i8.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i8.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i8.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i8.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-40);
            i8.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i8.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            display.Image i9 = new display.Image(43, 24);
            i9.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i9.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i9.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i9.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i9.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true,-30);
            i9.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i9.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            Animation d2 = new Animation(3);
            i6.flipHorizontal();
            i7.flipHorizontal();
            i8.flipHorizontal();
            i9.flipHorizontal();
            
            d2.addFrame(i6);
            d2.addFrame(i7);
            d2.addFrame(i8);
            d2.addFrame(i9);
            skins.put("standLeft", d2);
            
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void knockBack(float hSpeed, float vSpeed)
    {
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
        playing.addToUpdate(this);
    }
}
