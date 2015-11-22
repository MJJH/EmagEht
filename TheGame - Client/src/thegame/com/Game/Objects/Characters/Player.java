package thegame.com.Game.Objects.Characters;

import display.Skin;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import javafx.scene.image.Image;
import javax.transaction.xa.XAException;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.MapObject;



/**
 *
 * @author laure
 */
public class Player extends CharacterGame {
    private static final long serialVersionUID = 6629685098267757690L;
    private boolean connected;
    private int spawnX;
    private int spawnY;
    

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
        
        return ret;
    }
}
