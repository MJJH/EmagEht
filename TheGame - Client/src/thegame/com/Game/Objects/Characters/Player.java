package thegame.com.Game.Objects.Characters;

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
            i.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true);
            i.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            skins.put("standRight", i);
            
            display.Image i2 = new display.Image(43, 24);
            i2.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i2.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i2.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i2.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i2.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true);
            i2.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);
            
            i2.recolour(new Color[]{ 
                new Color(0, 0, 0, 1), 
                new Color(0.52, 0.30, 0.13, 1), 
                new Color(0.72, 0.41, 0.19, 1), 
                new Color(0.84, 0.58, 0.39, 1), 
                new Color(0.89, 0.71, 0.58, 1), 
                new Color(1, 0, 0, 1)
            });
            
            i2.flipHorizontal();
            
            skins.put("standLeft", i2);
            
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
