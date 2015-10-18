package thegame.com.Game.Objects.Characters;

import java.awt.Image;

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
     * @param solid, solidness of the player
     */
    public Player(Character character, String name, int hp, SkillType[] skills, AttackType[] attacks, int x, int y, Image skin, int height, int width, float solid)
    {
        super(name, hp, skills, x, y, skin, height, width, solid);
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

}
