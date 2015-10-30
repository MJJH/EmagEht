package thegame.com.Game.Objects.Characters;

import java.util.*;
import javafx.scene.image.Image;
import thegame.com.Game.Objects.MapObject;

/**
 * This constructor creates an enemy
 * @author Nick Bijmoer
 */
public class Enemy extends CharacterGame {

    
    /**
     *
     * @param name, name of the enemy
     * @param hp, HP of the enemy
     * @param skills, Skills that the enemy has
     * @param x, X- Coordinate of the enemy
     * @param y, Y- Coordinate of the enemy
     * @param skin, Skin that the enemy has
     * @param height, Height of the enemy
     * @param width, Width of the enemy
     */
    public Enemy(String name, int hp, java.util.Map<SkillType, Integer> skills, float x, float y, Image skin, float height, float width, thegame.com.Game.Map map)
    {
        super(name, hp, skills, x, y, skin, height, width, map);
    }
    

    @Override
    public void update() {
        EnumMap<sides, List<MapObject>> collision = Collision();
        fall(collision);
        moveH(collision);
        moveV(collision);
    }
    
}
