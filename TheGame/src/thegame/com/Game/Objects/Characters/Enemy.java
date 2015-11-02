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
        EnumMap<sides, List<MapObject>> collision = collision();

        List<Player> players = playing.getPlayers();

        for (Player player : players)
        {
            float playerX = player.getX() + (player.getW()/2);
            float playerY = player.getY() + (player.getH()/2);

            if ((xPosition - distanceX) < playerX && (xPosition + distanceX) > playerX)
            {
                if ((yPosition - distanceY) < playerY && (yPosition + distanceY) > playerY)
                {
                    // WALK TO PLAYER
                    if (playerX < xPosition)
                    {
                        walkLeft();
                    } else if (playerX > xPosition)
                    {
                        walkRight();
                    }

                    // COLLISION LEFT
                    if (collision.get(sides.LEFT).size() > 0)
                    {
                        for (MapObject colLeft : collision.get(sides.LEFT))
                        {
                            if (colLeft instanceof Player)
                            {
                                useTool(colLeft.getX(), colLeft.getY());
                            } else
                            {
                                jump();
                            }
                        }
                        collision.get(sides.LEFT).get(0).hit(holding, sides.LEFT);
                        // COLLISION RIGHT
                    } else if (collision.get(sides.RIGHT).size() > 0)
                    {
                        for (MapObject colRight : collision.get(sides.RIGHT))
                        {
                            if (colRight instanceof Player)
                            {
                                useTool(colRight.getX(), colRight.getY());
                            } else
                            {
                                jump();
                            }
                        }
                    }
                    // COLLISION TOP
                } else if (collision.get(sides.TOP).size() > 0)
                {
                    for (MapObject colTop : collision.get(sides.TOP))
                    {
                        if (colTop instanceof Player)
                        {
                            useTool(colTop.getX(), colTop.getY());
                        } else
                        {
                            walkLeft();
                        }
                    }
                }
                // COLLISON BOTTOM
            } else if (collision.get(sides.BOTTOM).size() > 0)
            {
                for (MapObject colBottom : collision.get(sides.BOTTOM))
                {
                    if (colBottom instanceof Player)
                    {
                        useTool(colBottom.getX(), colBottom.getY());
                    } else
                    {
                        walkLeft();
                    }
                }
            }
        }

        fall(collision);
        moveH(collision);
        moveV(collision);
    }

}
