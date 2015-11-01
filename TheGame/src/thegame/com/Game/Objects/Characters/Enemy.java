package thegame.com.Game.Objects.Characters;

import java.util.*;
import javafx.scene.image.Image;
import thegame.com.Game.Objects.MapObject;

/**
 * This constructor creates an enemy
 *
 * @author Nick Bijmoer
 */
public class Enemy extends CharacterGame {

    private final float distanceX = 30;
    private final float distanceY = 10;

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
    public void update()
    {
        EnumMap<sides, List<MapObject>> collision = Collision();

        List<Player> players = playing.getPlayers();

        for (Player player : players)
        {
            float playerX = player.getX();
            float playerY = player.getY();

            if ((xPosition - distanceX) < playerX && (xPosition + distanceX) > playerX)
            {
                if ((yPosition - distanceY) < playerY && (yPosition + distanceY) > playerY)
                {
                    // WALK TO PLAYER
                    if (playerX + player.getW() < xPosition)
                    {
                        walkLeft();
                    } else if (playerX - player.getW() > xPosition)
                    {
                        walkRight();
                    }

                    // DETECT OBSTACLES LEFT AND RIGHT
                    if (collision.get(sides.LEFT).size() > 0 || collision.get(sides.RIGHT).size() > 0)
                    {
                        // PLAYER
                        if (collision.get(sides.LEFT).size() > 0 && collision.get(sides.LEFT).get(0) instanceof Player)
                        {
                            //hit left
                        } else if (collision.get(sides.RIGHT).size() > 0 && collision.get(sides.RIGHT).get(0) instanceof Player)
                        {
                            //hit right
                        } else
                        // OTHER
                        {
                            Jump();
                        }
                    }

                    // NO STACKING
                    if (collision.get(sides.TOP).size() > 0 || collision.get(sides.BOTTOM).size() > 0)
                    {
                        if (collision.get(sides.TOP).size() > 0 && (collision.get(sides.TOP).get(0) instanceof Player || collision.get(sides.TOP).get(0) instanceof Enemy))
                        {
                            walkLeft();
                        } else if (collision.get(sides.BOTTOM).size() > 0 && (collision.get(sides.BOTTOM).get(0) instanceof Player || collision.get(sides.BOTTOM).get(0) instanceof Enemy))
                        {
                            walkRight();
                        }
                    }
                }

            }
        }

        fall(collision);
        moveH(collision);
        moveV(collision);
    }

}
