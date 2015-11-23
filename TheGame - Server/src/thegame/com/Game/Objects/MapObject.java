package thegame.com.Game.Objects;

import display.Skin;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Game.GameLogic;
import thegame.com.Game.Map;

/**
 * An object that can be drawn on the map
 *
 * @author Martijn
 */
public abstract class MapObject implements Callable<Boolean>, Serializable, Cloneable {

    private static final long serialVersionUID = 6529685098267757690L;

    private int id;
    protected float xPosition;
    protected float yPosition;
    protected float hSpeed;
    protected float vSpeed;
    protected transient Skin skin;
    protected float height;
    protected float width;
    protected float solid;
    protected Map playing;

    public boolean debug = false;

    public enum sides {

        TOP, BOTTOM, LEFT, RIGHT
    }

    /**
     * Create a new MapObject to use in the game
     *
     * @param x the horizontal position of this object
     * @param y the vertical position of this object
     * @param skin the texture of this object
     * @param height the height of this object
     * @param width the width of this object
     * @param solid the density of this object
     * @param map the map that this is on
     * @param gameLogic
     */
    public MapObject(float x, float y, Skin skin, float height, float width, float solid, Map map, GameLogic gameLogic)
    {
        this.playing = map;
        this.skin = skin;
        this.setX(x);
        this.setY(y);
        this.setH(height);
        this.setW(width);
        this.setS(solid);

        try
        {
            id = gameLogic.getMapObjectID();
        } catch (RemoteException ex)
        {
            Logger.getLogger(MapObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create a new MapObject without position and density
     *
     * @param skin
     * @param height
     * @param width
     * @param map
     * @throws java.rmi.RemoteException
     */
    public MapObject(Skin skin, float height, float width, Map map) throws RemoteException
    {
        this.skin = skin;
        this.playing = map;
        this.setH(height);
        this.setW(width);
    }

    /**
     * Update the object Will be called everytime the map is updated
     */
    @Override
    public abstract Boolean call();

    private void setX(float x)
    {
        if (x >= 0 && x < playing.getWidth())
        {
            this.xPosition = x;
        } else
        {
            if (x <= 0)
            {
                this.xPosition = 0;
            } else if (x + width >= playing.getWidth())
            {
                this.xPosition = playing.getWidth() - width;
            }
            hSpeed = 0;
        }
    }

    private void setY(float y)
    {
        if (y >= 0 && y < playing.getHeight())
        {
            this.yPosition = y;
        } else
        {
            this.yPosition = 0;
            vSpeed = 0;
        }
    }

    protected boolean fall(EnumMap<sides, List<MapObject>> collision)
    {
        if (collision.get(sides.BOTTOM).isEmpty() && vSpeed > -5)
        {
            vSpeed -= .1f;
            return true;
        }
        return false;
    }

    protected boolean moveH(EnumMap<sides, List<MapObject>> collision)
    {
        if (hSpeed > 0)
        {
            List<MapObject> r = collision.get(sides.RIGHT);
            if (r.isEmpty())
            {
                hSpeed -= 0.2;
                if (hSpeed < 0)
                {
                    hSpeed = 0;
                }

                setX(xPosition + hSpeed / 2);
                return true;
            } else
            {
                hSpeed = 0;

                float minX = 999;
                for (MapObject c : r)
                {
                    if (c.getX() - this.width < minX)
                    {
                        minX = c.getX() - this.width;
                    }
                }

                setX(minX);
                return true;
            }
        } else if (hSpeed < 0)
        {
            List<MapObject> l = collision.get(sides.LEFT);
            if (l.isEmpty())
            {
                hSpeed += 0.2;

                if (hSpeed > 0)
                {
                    hSpeed = 0;
                }

                setX(xPosition + hSpeed / 2);
                return true;
            } else
            {
                hSpeed = 0;

                float maxX = 0;
                for (MapObject c : l)
                {
                    if (c.getX() + c.getW() > maxX)
                    {
                        maxX = c.getX() + c.getW();
                    }
                }

                setX(maxX);
                return true;
            }
        }

        return false;
    }

    protected boolean moveV(EnumMap<sides, List<MapObject>> collision)
    {
        if (vSpeed > 0)
        {
            List<MapObject> t = collision.get(sides.TOP);
            if (t.isEmpty())
            {
                setY(yPosition + vSpeed);
                return true;
            } else
            {
                vSpeed = 0;

                float minY = 999;
                for (MapObject c : t)
                {
                    if (c.getY() - this.height < minY)
                    {
                        minY = c.getY() - this.height;
                    }
                }

                setY(minY);
                return true;
            }
        } else if (vSpeed < 0)
        {
            List<MapObject> b = collision.get(sides.BOTTOM);
            if (b.isEmpty())
            {
                setY(yPosition + vSpeed);
                return true;
            } else
            {
                vSpeed = 0;

                float maxY = 0;
                for (MapObject c : b)
                {
                    if (c.getY() + c.getH() > maxY)
                    {
                        maxY = c.getY() + c.getH();
                    }
                }

                setY(maxY);
                return true;
            }
        }

        return false;
    }

    private void setH(float h)
    {
        if (h > 0)
        {
            this.height = h;
        } else
        {
            throw new IllegalArgumentException("Height cant be 0.");
        }
    }

    private void setW(float w)
    {
        if (w > 0)
        {
            this.width = w;
        } else
        {
            throw new IllegalArgumentException("Width cant be 0.");
        }
    }

    private void setS(float s)
    {
        if (s >= 0 && s <= 1)
        {
            this.solid = s;
        } else
        {
            throw new IllegalArgumentException("Solid should be between 0 and 1");
        }
    }

    public EnumMap<sides, List<MapObject>> collision()
    {
        EnumMap<sides, List<MapObject>> collision = new EnumMap<>(sides.class);
        List<MapObject> founds;

        // Bottom
        founds = new ArrayList<>();
        for (float x = xPosition; x <= xPosition + width; x += width / 3)
        {
            MapObject found = playing.GetTile(x, yPosition - height, this, true);
            if (found != null && found != this)
            {
                founds.add(found);
            }
        }
        collision.put(sides.BOTTOM, founds);

        // Top
        founds = new ArrayList<>();
        for (float x = xPosition; x <= xPosition + width; x += width / 3)
        {
            MapObject found = playing.GetTile(x, yPosition + 1, this, true);
            if (found != null && found != this)
            {
                founds.add(found);
            }
        }
        collision.put(sides.TOP, founds);

        // Left
        founds = new ArrayList<>();
        for (float y = yPosition; y <= yPosition + height; y += height / 3)
        {
            MapObject found = playing.GetTile(xPosition - 1, y, this, true);
            if (found != null && found != this)
            {
                founds.add(found);
            }
        }
        collision.put(sides.LEFT, founds);

        // Right
        founds = new ArrayList<>();
        for (float y = yPosition; y <= yPosition + height; y += height / 3)
        {
            MapObject found = playing.GetTile(xPosition + width, y, this, true);
            if (found != null && found != this)
            {
                founds.add(found);
            }
        }
        collision.put(sides.RIGHT, founds);

        return collision;
    }

    public int getID()
    {
        return id;
    }

    /**
     * Get x (horizontal) position
     *
     * @return xPosition
     */
    public float getX()
    {
        return xPosition;
    }

    /**
     * Get y (vertical) position
     *
     * @return yPosition
     */
    public float getY()
    {
        return yPosition;
    }

    /**
     * Get height
     *
     * @return height
     */
    public float getH()
    {
        return height;
    }

    /**
     * Get width
     *
     * @return width
     */
    public float getW()
    {
        return width;
    }

    /**
     * Get density of object
     *
     * @return solid
     */
    public float getS()
    {
        return solid;
    }

    /**
     * Get horizontal speed
     *
     * @return hSpeed
     */
    public float getSX()
    {
        return hSpeed;
    }

    /**
     * Get vertical speed
     *
     * @return vSpeed
     */
    public float getSY()
    {
        return vSpeed;
    }

    /**
     * Get skin
     *
     * @return skin
     */
    public Skin getSkin()
    {
        return skin;
    }

    public float distance(MapObject to)
    {
        float x1 = this.xPosition + this.width / 2;
        float x2 = to.xPosition + to.width / 2;
        float y1 = this.yPosition + this.height / 2;
        float y2 = to.yPosition + to.height / 2;

        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public abstract void hit(Tool used, sides hitDirection);

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof MapObject)
        {
            MapObject mo = (MapObject) o;
            return id == mo.getID();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        return hash;
    }
    
    public void setMap(Map set)
    {
        this.playing = set;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
