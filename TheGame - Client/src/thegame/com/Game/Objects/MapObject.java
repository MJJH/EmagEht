package thegame.com.Game.Objects;

import display.Skin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.Callable;
import javafx.scene.shape.Rectangle;
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
    protected float hSpeed;
    protected float vSpeed;
    protected float height;
    protected float width;
    protected float solid;
    
    protected transient Skin skin;
    protected transient Map playing;

    public enum sides implements Serializable{

        TOP, BOTTOM, LEFT, RIGHT, CENTER
    }

    protected void setX(float x)
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
            hSpeed = 0;
        }
    }

    protected void setY(float y)
    {
        if (y > height && y < playing.getHeight())
        {
            this.yPosition = y;
        } else if (y <= height)
        {
            this.yPosition = height;
            vSpeed = 0;
        } else
        {
            this.yPosition = playing.getHeight();
            vSpeed = 0;
        }
    }

    protected void setH(float h)
    {
        if (h > 0)
        {
            this.height = h;
        } else
        {
            throw new IllegalArgumentException("Height cant be 0.");
        }
    }

    protected void setW(float w)
    {
        if (w > 0)
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
        if(skin == null)
            createSkin();
        
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

    public abstract void createSkin();
}
