package thegame.com.Game.Objects.Characters;

import java.awt.Image;
import java.util.*;

/**
 *
 * @author laure
 */
public class Enemy extends CharacterGame {

    Collection<SkillType> skillType;

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
     * @param solid
     */
    public Enemy(String name, int hp, SkillType[] skills, int x, int y, Image skin, int height, int width, float solid)
    {
        super(name, hp, skills, x, y, skin, height, width, solid);
    }

}
