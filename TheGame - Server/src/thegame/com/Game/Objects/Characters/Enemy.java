package thegame.com.Game.Objects.Characters;

import java.util.EnumMap;
import java.util.List;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.Tool;
import thegame.com.Game.Objects.ToolType;
import thegame.engine.Collision;
import thegame.engine.Movement;
import thegame.engine.Physics;

/**
 * This constructor creates an enemy
 *
 * @author Nick Bijmoer
 */
public class Enemy extends CharacterGame {

    private static final long serialVersionUID = 6729685098267757690L;
    private final float distanceX = 30;
    private final float distanceY = 10;

    /**
     *
     * @param name, name of the enemy
     * @param hp, HP of the enemy
     * @param skills, Skills that the enemy has
     * @param x, X- Coordinate of the enemy
     * @param y, Y- Coordinate of the enemy
     * @param height, Height of the enemy
     * @param width, Width of the enemy
     * @param map
     */
    public Enemy(String name, int hp, java.util.Map<SkillType, Integer> skills, float x, float y, float height, float width, Map map)
    {
        super(name, hp, skills, x, y, height, width, map);

        ToolType test = new ToolType("Zwaardje", 20, 1000, 3f, 1, ToolType.toolType.SWORD, 0.3f, 1, 1);
        Tool equip = new Tool(test, map);
        equipTool(equip);
    }

    @Override
    public Boolean call()
    {
        EnumMap<sides, List<MapObject>> collision = Collision.collision(this, false);

        List<Player> players = playing.getPlayers();

        for (Player player : players)
        {
            float playerX = player.getX() + (player.getW() / 2);
            float playerY = player.getY() + (player.getH() / 2);

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
                        //collision.get(sides.LEFT).get(0).hit(holding, sides.LEFT);
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

        Boolean ret = false;
        if (Physics.gravity(this))
        {
            ret = true;
        }

        if (Movement.moveH(this))
        {
            ret = true;
        }

        if (Movement.moveV(this))
        {
            ret = true;
        }

        while (!collision.get(sides.CENTER).isEmpty())
        {
            yPosition++;
            collision = Collision.collision(this, false);
            ret = true;
        }

        return ret;
    }

    @Override
    public void knockBack(float kb, sides hitDirection)
    {
        switch (hitDirection)
        {
            case LEFT:
                sX = -kb;
                sY = kb;
                break;
            case RIGHT:
                sX = kb;
                sY = kb;
                break;
        }
    }

    @Override
    public int updateHP(int change)
    {
        hp -= change;

        if (hp > 100)
        {
            hp = 100;
        } else if (hp <= 0)
        {
            hp = 0;
        }

        return hp;
    }

}
