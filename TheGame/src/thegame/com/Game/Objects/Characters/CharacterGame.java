package thegame.com.Game.Objects.Characters;

import java.awt.Image;
import thegame.com.Game.Objects.*;

/**
 *
 * @author laure
 */
public class CharacterGame extends MapObject {

    private int hp;
    private final SkillType[] skills;

    /**
     *
     * @param name
     * @param hp
     * @param skills
     * @param x
     * @param y
     * @param skin
     * @param height
     * @param width
     * @param parameter
     */
    public CharacterGame(String name, int hp, SkillType[] skills, int x, int y, Image skin, int height, int width, float parameter)
    {
        super(x, y, skin, height, width, parameter);
        this.skills = skills;
    }

    /**
     *
     * @param object
     * @return 
     */
    public boolean addToBackpack(MapObject object)
    {
        return false;
    }

    /**
     *
     * @param object
     */
    public void dropItem(MapObject object)
    {

    }

    /**
     *
     * @param armor
     */
    public void wearArmor(Armor armor)
    {

    }

}
