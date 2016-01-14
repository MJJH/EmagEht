package thegame.com.Game.Objects;

import display.Skin;
import java.io.Serializable;
import thegame.com.Game.Map;

/**
 * An object that can be drawn on the map
 *
 * @author Martijn
 */
public abstract class MapObject implements Serializable {

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
    protected boolean placeable;
    protected boolean stackable;

    protected transient Skin skin;
    protected transient Map playing;

    public enum sides implements Serializable {

        TOP, BOTTOM, LEFT, RIGHT, CENTER
    }

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

    protected void setH(float h)
    {
        if (h >= 0)
        {
            this.height = h;
        } else
        {
            throw new IllegalArgumentException("Height cant be 0.");
        }
    }

    protected void setW(float w)
    {
        if (w >= 0)
        {
            this.width = w;
        } else
        {
            throw new IllegalArgumentException("Width cant be 0.");
        }
    }

    protected void setS(float s)
    {
        if (s >= 0 && s <= 1)
        {
            this.solid = s;
        } else
        {
            throw new IllegalArgumentException("Solid should be between 0 and 1");
        }
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

    /**
     * Get skin
     *
     * @return skin
     */
    public Skin getSkin()
    {
        if (skin == null)
        {
            createSkin();
        }

        return skin;
    }

    public int getID()
    {
        return id;
    }

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

    public Map getMap()
    {
        return playing;
    }

    public void setSX(float newSpeed)
    {
        sX = newSpeed;
    }

    public void setSY(float newSpeed)
    {
        sY = newSpeed;
    }

    public abstract void createSkin();

    public abstract void update(MapObject update);
}
