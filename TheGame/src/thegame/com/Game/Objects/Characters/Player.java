package thegame.com.Game.Objects.Characters;

import java.util.ArrayList;
import javafx.scene.image.Image;



/**
 *
 * @author laure
 */
public class Player extends CharacterGame {

    private int keymap;
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
     */
    public Player(Character character, String name, int hp, java.util.Map<SkillType, Integer> skills, AttackType[] attacks, float x, float y, Image skin, float height, float width)
    {
        super(name, hp, skills, x, y, skin, height, width);
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
    
    public void testMove() 
    {
        this.moveX(0.1f);
    }

}
