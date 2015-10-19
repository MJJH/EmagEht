package thegame.com.Game.Objects.Characters;

import java.util.*;
import javafx.scene.image.Image;

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
    public Enemy(String name, int hp, java.util.Map<SkillType, Integer> skills, float x, float y, Image skin, float height, float width)
    {
        super(name, hp, skills, x, y, skin, height, width);
    }
    
    /**
     * This method will move the enemy
     * @param x, X-coordinate of the enemy
     * @param y, Y-coordinate of the enemy
     */
    public void move (float x, float y)
    {
        /*
        dx = player_x - enemy_x;
        dy = player_y - enemy_y;
        float norm = Math.sqrt(dx * dx + dy * dy);
        if (norm)
        {
            dx *= (enemy.speed / norm);
            dy *= (enemy.speed / norm);
        }
        */
    }
}
