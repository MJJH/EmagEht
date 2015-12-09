package thegame.com.Game.Objects.Characters;

import display.Animation;
import display.Image;
import display.Sets;
import display.Skin;
import display.iTexture;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import thegame.com.Game.Objects.MapObject;
import thegame.shared.IGameClientToServer;

/**
 *
 * @author laure
 */
public class Player extends CharacterGame {

    private static final long serialVersionUID = 6629685098267757690L;
    private boolean connected;
    private int spawnX;
    private int spawnY;
    private transient boolean toUpdate;

    private HashMap<String, Skin> skins;

    public void walkRight()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        direction = sides.RIGHT;

        if (!c.get(sides.RIGHT).isEmpty())
        {
            return;
        }

        if (hSpeed < 0)
        {
            hSpeed = 0.075f;
        } else if (hSpeed < 0.3)
        {
            hSpeed += 0.075f;
        }
    }

    public void walkLeft()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        direction = sides.LEFT;

        if (!c.get(sides.LEFT).isEmpty())
        {
            return;
        }

        if (hSpeed > 0)
        {
            hSpeed = -0.075f;
        } else if (hSpeed > -0.3)
        {
            hSpeed -= 0.075f;
        }
    }

    public void jump()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        if ((!c.get(sides.BOTTOM).isEmpty() || jumping) && c.get(sides.TOP).isEmpty())
        {
            jumping = true;
            vSpeed += 0.05f;

            if (vSpeed >= 0.3f)
            {
                vSpeed = 0.3f;
                jumping = false;
            }
        }
    }

    public void stopJump()
    {
        jumping = false;
    }

    private boolean fall(EnumMap<sides, List<MapObject>> collision)
    {
        if (collision.get(sides.BOTTOM).isEmpty() && vSpeed > -1)
        {
            vSpeed -= .025f;
            return true;
        }
        return false;
    }

    private boolean moveH(EnumMap<sides, List<MapObject>> collision)
    {
        List<MapObject> found;

        if (hSpeed > 0)
        {
            hSpeed -= 0.04f;
            if (hSpeed <= 0)
            {
                hSpeed = 0;
            }

            found = collision.get(sides.RIGHT);
            if (found.isEmpty())
            {
                setX(xPosition + hSpeed);

                found = collision().get(sides.RIGHT);
                if (!found.isEmpty())
                {
                    float minX = -1;
                    for (MapObject mo : found)
                    {
                        if (minX == -1 || mo.getX() < minX)
                        {
                            minX = mo.getX();
                        }
                    }

                    hSpeed = 0;
                    setX(minX - width);
                }

                return true;
            } else
            {
                float minX = -1;
                for (MapObject mo : found)
                {
                    if (minX == -1 || mo.getX() < minX)
                    {
                        minX = mo.getX();
                    }
                }

                if (minX == -1)
                {
                    setX(xPosition + hSpeed);
                    return true;
                }

                hSpeed = 0;
                setX(minX - width);
                return true;
            }
        } else if (hSpeed < 0)
        {
            hSpeed += 0.04f;
            if (hSpeed >= 0)
            {
                hSpeed = 0;
            }

            found = collision.get(sides.LEFT);
            if (found.isEmpty())
            {
                setX(xPosition + hSpeed);

                found = collision().get(sides.LEFT);
                if (!found.isEmpty())
                {
                    float maxX = -1;
                    for (MapObject mo : found)
                    {

                        if (maxX == -1 || mo.getX() + mo.getW() > maxX)
                        {
                            maxX = mo.getX() + mo.getW();
                        }
                    }

                    if (maxX == -1)
                    {
                        setX(xPosition + hSpeed);
                        return true;
                    }

                    hSpeed = 0;
                    setX(maxX);
                }

                return true;
            } else
            {
                float maxX = -1;
                for (MapObject mo : found)
                {

                    if (maxX == -1 || mo.getX() + mo.getW() > maxX)
                    {
                        maxX = mo.getX() + mo.getW();
                    }
                }

                hSpeed = 0;
                setX(maxX);
                return true;
            }
        }

        return false;
    }

    private boolean moveV(EnumMap<sides, List<MapObject>> collision)
    {
        List<MapObject> found;

        if (vSpeed > 0)
        {
            found = collision.get(sides.TOP);
            if (found.isEmpty())
            {
                setY(yPosition + vSpeed);

                found = collision().get(sides.TOP);
                if (!found.isEmpty())
                {
                    float minY = -1;
                    for (MapObject mo : found)
                    {

                        if (minY == -1 || mo.getY() < minY)
                        {
                            minY = mo.getY() - mo.getH();
                        }
                    }

                    vSpeed = 0;
                    setY(minY);
                }

                return true;
            } else
            {
                float minY = -1;
                for (MapObject mo : found)
                {

                    if (minY == -1 || mo.getY() < minY)
                    {
                        minY = mo.getY() - mo.getH();
                    }
                }

                if (minY == -1)
                {
                    setY(yPosition + vSpeed);
                    return true;
                }

                vSpeed = 0;
                setY(minY);
                return true;
            }
        } else if (vSpeed < 0)
        {
            this.stopJump();
            found = collision.get(sides.BOTTOM);
            if (found.isEmpty())
            {
                setY(yPosition + vSpeed);

                found = collision().get(sides.BOTTOM);
                if (!found.isEmpty())
                {
                    float maxY = -1;
                    for (MapObject mo : found)
                    {

                        if (maxY == -1 || mo.getY() > maxY)
                        {
                            maxY = mo.getY() + height;
                        }
                    }

                    vSpeed = 0;
                    setY(maxY);
                }

                return true;
            } else
            {
                float maxY = -1;
                for (MapObject mo : found)
                {

                    if (maxY == -1 || mo.getY() > maxY)
                    {
                        maxY = mo.getY() + height;
                    }
                }

                if (maxY == -1)
                {
                    setY(yPosition + vSpeed);
                    return true;
                }

                vSpeed = 0;
                setY(maxY);

                return true;
            }
        }

        return false;
    }

    private EnumMap<sides, List<MapObject>> collision()
    {
        EnumMap<sides, List<MapObject>> collision = new EnumMap<>(sides.class);
        collision.put(sides.TOP, new ArrayList<>());
        collision.put(sides.BOTTOM, new ArrayList<>());
        collision.put(sides.LEFT, new ArrayList<>());
        collision.put(sides.RIGHT, new ArrayList<>());
        collision.put(sides.CENTER, new ArrayList<>());

        List<MapObject> mos = playing.getBlocksAndObjects((int) Math.round(xPosition - 2),
                (int) Math.round(yPosition - height - 3),
                (int) Math.round(xPosition + width + 1),
                (int) Math.round(yPosition + 2));

        for (MapObject mo : mos)
        {
            if (mo == this)
            {
                continue;
            }

            List<sides> found = this.collision(mo);
            if (found != null)
            {
                for (sides s : found)
                {
                    collision.get(s).add(mo);
                }
            }
        }

        return collision;
    }

    public ArrayList<sides> collision(MapObject mo)
    {
        if (this.solid == 0 || mo.getS() == 0)
        {
            return null;
        }

        Rectangle r1 = new Rectangle(xPosition, yPosition, width, height);
        Rectangle r2 = new Rectangle(mo.getX(), mo.getY(), mo.getW(), mo.getH());

        boolean right = (r2.getX() >= r1.getX() && r2.getX() <= r1.getX() + r1.getWidth());
        boolean left = (r2.getX() + r2.getWidth() >= r1.getX() && r2.getX() + r2.getWidth() <= r1.getX() + r1.getWidth());
        boolean top = (r2.getY() - r2.getHeight() <= r1.getY() && r2.getY() - r2.getHeight() >= r1.getY() - r1.getHeight());
        boolean bott = (r2.getY() >= r1.getY() - r1.getHeight() && r2.getY() <= r1.getY());

        if (right || left || top || bott)
        {
            ArrayList<sides> ret = new ArrayList<>();
            if (bott && top && left && right)
            {
                ret.add(sides.CENTER);
            }

            if (right && !(mo.getY() - mo.getH() >= yPosition || mo.getY() <= yPosition - height))
            {
                ret.add(sides.RIGHT);
            }
            if (left && !(mo.getY() - mo.getH() >= yPosition || mo.getY() <= yPosition - height))
            {
                ret.add(sides.LEFT);
            }
            if (top && !(mo.getX() + mo.getW() <= xPosition || mo.getX() >= xPosition + width))
            {
                ret.add(sides.TOP);
            }
            if (bott && !(mo.getX() + mo.getW() <= xPosition || mo.getX() >= xPosition + width))
            {
                ret.add(sides.BOTTOM);
            }

            return ret;
        } else
        {
            return null;
        }
    }

    public void update()
    {
        EnumMap<sides, List<MapObject>> collision = collision();
        Boolean newToUpdate = false;
        if (fall(collision))
        {
            newToUpdate = true;
        }

        if (moveH(collision))
        {
            newToUpdate = true;
        }

        if (moveV(collision))
        {
            newToUpdate = true;
        }

        while (!collision.get(sides.CENTER).isEmpty())
        {
            yPosition++;
            collision = collision();
            newToUpdate = true;
        }
    }

    public void useTool(float x, float y, IGameClientToServer gameLogic)
    {
        if (System.currentTimeMillis() - used >= holding.type.speed)
        {
            try
            {
                if (gameLogic.useTool(id, x, y))
                {
                    //feedback
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setCords(float x, float y)
    {
        xPosition = x;
        yPosition = y;
    }

    @Override
    public Skin getSkin()
    {
        if (skins == null)
        {
            return null;
        }

        if (direction == sides.RIGHT)
        {
            return skins.get("standRight");
        } else
        {
            return skins.get("standLeft");
        }
    }

    @Override
    public void createSkin()
    {
        try
        {
            skins = new HashMap<>();
            Color[] h = new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.26, 0.15, 0.065, 1),
                new Color(0.36, 0.205, 0.095, 1),
                new Color(0.42, 0.29, 0.195, 1),
                new Color(0.445, 0.355, 0.29, 1),
                new Color(1, 1, 1, 1)
            };

            Image d = new Image(Sets.player);
            d.recolour(h);

            Animation a = new Animation(d, 4);
            a.addFrameByPart(iTexture.Part.FRONTARM, 20);

            skins.put("standRight", a);

            Image d2 = new Image(Sets.player);
            d2.flipHorizontal();
            d2.recolour(h);
            skins.put("standLeft", d2);

        } catch (IOException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void knockBack(float hSpeed, float vSpeed)
    {
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
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

    float getSpawnX()
    {
        return spawnX;
    }

    float getSpawnY()
    {
        return spawnY;
    }
}
