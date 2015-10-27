package thegame.com.Game.Objects.Characters;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;
import javax.transaction.xa.XAException;
import thegame.com.Game.Map;



/**
 *
 * @author laure
 */
public class Player extends CharacterGame {

    private int keymap;
    private boolean connected;
    private int spawnX;
    private int spawnY;
    private Map map;
    

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
     */
    public Player(Character character, String name, int hp, java.util.Map<SkillType, Integer> skills, AttackType[] attacks, float x, float y, Image skin, float height, float width, Map map)
    {
        super(name, hp, skills, x, y, skin, height, width);
        this.map = map;
        
        
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
    public void update() 
    {
    vSpeed -= .1;
     HashMap<String, Boolean> c = Collision();
     if(c.get("Top") && vSpeed > 0)
     {
         vSpeed = 0;
     }
     if(c.get("Bottom") && vSpeed < 0)
     {
         vSpeed = 0;
     }
     
     if(c.get("Left") && hSpeed < 0)
     {
         hSpeed = 0;
     }
     if(c.get("Right") && hSpeed > 0)
     {
         hSpeed = 0;
     }
     xPosition += hSpeed;
     yPosition += vSpeed;
    }
    
     public HashMap<String,Boolean> Collision()
    {
        float currentX = xPosition;
        float currentY = yPosition;
        HashMap<String, Boolean> Collide = new HashMap<String, Boolean>();
        Collide.put("Top", false);
        Collide.put("Bottom",false);
        Collide.put("Left", false);
        Collide.put("Right", false);
        
        if(vSpeed > 0)
        {
        //onder
        for(int x = (int) Math.floor(currentX); x < Math.ceil(currentX + width); x++)
        {
        if(map.GetTile(x, (int) (currentY + height)) != null)
        {
         Collide.put("Top", true);
         break;
        }
        }
        }
        else if(vSpeed <= 0.0)
         for(int x = (int) Math.floor(currentX); x < Math.ceil(currentX + width); x++)
        {
        if(map.GetTile(x, (int) currentY) != null)
        {
         Collide.put("Bottom", true);
         
         break;
        }
        }
        
        if(hSpeed <0)
        {
        for(int y = (int) Math.floor(currentY); y < Math.ceil(currentY + height); y++)
        {
        if(map.GetTile((int) currentX, y) != null)
        {
         Collide.put("Left", true);
         break;
        }
        }
        }
        else if(hSpeed > 0)
        {
        for(int y = (int) Math.floor(currentY); y < Math.ceil(currentY + height); y++)
        {
        if(map.GetTile((int) (currentX + width), y) != null)
        {
         Collide.put("Right", true);
         break;
        }
        }
        }
        return Collide;
        
    }
    
    
    /*
    public void testMove() 
    {
        this.moveX(0.1f);
    }
    */
}
