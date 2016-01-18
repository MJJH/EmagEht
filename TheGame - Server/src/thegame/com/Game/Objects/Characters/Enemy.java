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
    private transient final float distanceX = 30;
    private transient final float distanceY = 10;
    private transient Player agroPlayer;

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
        sXMax = 0.2f;
        sYMax = 0.2f;
        ToolType test = new ToolType("Zwaardje", 20, 1000, 3f, 1, ToolType.toolType.SWORD, 0.3f);
        Tool equip = new Tool(test, map);
        equipTool(equip);
    }

    @Override
    public Boolean call()
    {
        if (agroPlayer == null)
        {
            List<Player> players = playing.getPlayers();
            for (Player player : players)
            {
                if (((xPosition + (width / 2)) - distanceX) < (player.getX() + (player.getW() / 2)) && ((xPosition + (width / 2)) + distanceX) > (player.getX() + (player.getW() / 2)))
                {
                    if (((yPosition + (height / 2)) - distanceY) < (player.getY() + (player.getH() / 2)) && ((yPosition + (height / 2)) + distanceY) > (player.getY() + (player.getH() / 2)))
                    {
                        if (agroPlayer == null)
                        {
                            agroPlayer = player;
                        } else
                        {
                            if (Math.abs((xPosition + (width / 2)) - (player.getX() + (player.getW() / 2))) < Math.abs((xPosition + (width / 2)) - (agroPlayer.getX() + (agroPlayer.getW() / 2))))
                            {
                                agroPlayer = player;
                            }
                        }
                    }
                }
            }
        }
        if (agroPlayer != null)
        {
            if (((xPosition + (width / 2)) - distanceX) < (agroPlayer.getX() + (agroPlayer.getW() / 2)) && ((xPosition + (width / 2)) + distanceX) > (agroPlayer.getX() + (agroPlayer.getW() / 2)))
            {
                if (((yPosition + (height / 2)) - distanceY) < (agroPlayer.getY() + (agroPlayer.getH() / 2)) && ((yPosition + (height / 2)) + distanceY) > (agroPlayer.getY() + (agroPlayer.getH() / 2)))
                {
                    // WALK TO PLAYER
                    if(xPosition == agroPlayer.getX() + agroPlayer.getW())
                    {
                        //PLAYER LEFT
                    }
                    else if(xPosition + width == agroPlayer.getX())
                    {
                        //PLAYER RIGHT
                    }
                    else if ((agroPlayer.getX() + (agroPlayer.getW() / 2)) < (xPosition + (width / 2)))
                    {
                        walkLeft();
                    } else if ((agroPlayer.getX() + (agroPlayer.getW() / 2)) > (xPosition + (width / 2)))
                    {
                        walkRight();
                    }

                    EnumMap<sides, List<MapObject>> collision = Collision.collision(this, false);

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

                        // COLLISION TOP
                    } else if (collision.get(sides.TOP).size() > 0)
                    {
                        for (MapObject colTop : collision.get(sides.TOP))
                        {
                            if (colTop instanceof Player)
                            {
                                useTool(colTop.getX(), colTop.getY());
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
                            }
                        }
                    }
                } else
                {
                    agroPlayer = null;
                }
            } else
            {
                agroPlayer = null;
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

        if (Movement.deglitch(this))
        {
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
