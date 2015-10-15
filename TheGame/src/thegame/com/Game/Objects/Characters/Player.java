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
     *
     * @param character
     * @param name
     * @param hp
     * @param skills
     * @param attacks
     * @param x
     * @param y
     * @param skin
     * @param height
     * @param width
     * @param solid
     */
    public Player(Character character, String name, int hp, SkillType[] skills, AttackType[] attacks, int x, int y, Image skin, int height, int width, float solid)
    {
        super(name, hp, skills, x, y, skin, height, width, solid);
    }

    /**
     *
     */
    public void spawn()
    {
        // TODO - implement Player.spawn
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    public void LevelUp()
    {
        // TODO - implement Player.LevelUp
        throw new UnsupportedOperationException();
    }

}
