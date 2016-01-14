package thegame.com.Game.Objects;

import java.io.Serializable;
import java.util.concurrent.Callable;
import thegame.com.Game.Map;

/**
 * An object that can be drawn on the map
 *
 * @author Martijn
 */
public abstract class MapObject implements Callable<Boolean>, Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    protected int id;
    protected float xPosition;
    protected float yPosition;
    protected float sX;
    protected float sY;
    protected float sXDecay;
    protected float sXMax;
    protected float sYMax;
    protected float sXIncrease;
    protected float sYIncrease;
    protected float height;
    protected float width;
    protected float solid;
    protected transient Map playing;
    protected boolean placeable;
    protected boolean stackable;

    public boolean debug = false;

    public Map getMap()
    {
        return playing;
    }

    public void setSX(float f)
    {
        sX = f;
    }

    public void setSY(float f)
    {
        sY = f;
    }

    public enum sides implements Serializable {

        TOP, BOTTOM, LEFT, RIGHT, CENTER
    }

    /**
     * Create a new MapObject to use in the game
     *
     * @param x the horizontal position of this object
     * @param y the vertical position of this object
     * @param height the height of this object
     * @param width the width of this object
     * @param solid the density of this object
     * @param map the map that this is on
     */
    public MapObject(float x, float y, float height, float width, float solid, Map map, boolean place, boolean stackable)
    {
        this.playing = map;
        this.setX(x);
        this.setY(y);
        this.setH(height);
        this.setW(width);
        this.setS(solid);
        this.placeable = place;
        this.stackable = stackable;
        this.id = map.getMapObjectID();
    }

    /**
     * Create a new MapObject without position and density
     *
     * @param height
     * @param width
     * @param map
     */
    public MapObject(float height, float width, Map map, boolean place, boolean stackable)
    {
        this.playing = map;
        this.setH(height);
        this.setW(width);
        this.placeable = place;
        this.stackable = stackable;
        this.id = map.getMapObjectID();
    }

    /**
     * Update the object Will be called everytime the map is updated
     */
    @Override
    public abstract Boolean call();

    public void setX(float x)
    {
        if (x >= 0 && x + width < playing.getWidth())
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
            sX = 0;
        }
    }

    public void setY(float y)
    {
        if (y > height && y < playing.getHeight())
        {
            this.yPosition = y;
        } else if (y <= height)
        {
            this.yPosition = height;
            sY = 0;
        } else
        {
            this.yPosition = playing.getHeight();
            sY = 0;
        }
    }

    private void setH(float h)
    {
        if (h >= 0)
        {
            this.height = h;
        } else
        {
            throw new IllegalArgumentException("Height cant be below 0.");
        }
    }

    private void setW(float w)
    {
        if (w >= 0)
        {
            this.width = w;
        } else
        {
            throw new IllegalArgumentException("Width cant be below 0.");
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
     * @return sX
     */
    public float getSX()
    {
        return sX;
    }

    /**
     * Get vertical speed
     *
     * @return sY
     */
    public float getSY()
    {
        return sY;
    }
    
    public float getSXDecay()
    {
        return sXDecay;
    }
    
    public float getSXMax()
    {
        return sXMax;
    }
    
    public float getSYMax()
    {
        return sYMax;
    }
    
    public float getSXIncrease()
    {
        return sXIncrease;
    }
    
    public float getSYIncrease()
    {
        return sYIncrease;
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
        return id;
    }

    public void setMap(Map set)
    {
        this.playing = set;
    }
}
