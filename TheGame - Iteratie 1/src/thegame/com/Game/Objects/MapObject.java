package thegame.com.Game.Objects;

import display.Skin;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;

/**
 * An object that can be drawn on the map
 *
 * @author Martijn
 */
public abstract class MapObject {

    //private int id;
    protected float xPosition;
    protected float yPosition;
    protected float hSpeed;
    protected float vSpeed;
    protected Skin skin;
    protected float height;
    protected float width;
    protected float solid;
    protected final Map playing;

    public boolean debug = false;


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
     */
    public MapObject(float x, float y, Skin skin, float height, float width, float solid, Map map)
    {
        this.playing = map;
        this.skin = skin;
        this.setX(x);
        this.setY(y);
        this.setH(height);
        this.setW(width);
        this.setS(solid);
    }

    /**
     * Create a new MapObject without position and density
     *
     * @param skin
     * @param height
     * @param width
     * @param map
     */
    public MapObject(Skin skin, float height, float width, Map map)
    {
        this.skin = skin;
        this.playing = map;
        this.setH(height);
        this.setW(width);
    }

    /**
     * Update the object Will be called everytime the map is updated
     */
    public abstract void update();

    private void setX(float x)
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
        if (collision.get(sides.BOTTOM).isEmpty() && vSpeed > -1)
        {
            vSpeed -= .1f;
            return true;
        }
        return false;
    }

    protected boolean moveH(EnumMap<sides, List<MapObject>> collision)
    {
        List<MapObject> found;
        
        if(hSpeed > 0) {
            hSpeed -= 0.2f;
            if(hSpeed <= 0)
                hSpeed = 0;
            
            found = collision.get(sides.RIGHT);
            if(found.isEmpty()) {
                setX(xPosition + hSpeed);
                return true;
            } else {
                float minX = -1;
                for(MapObject mo : found) {
                    if(minX == -1 || mo.getX() < minX)
                        minX = mo.getX();
                }
                
                if(minX == -1) {
                    setX(xPosition + hSpeed);
                    return true;
                }
                
                hSpeed = 0;
                setX(minX - width);
                return true;
            }
        } else if(hSpeed < 0) {
            hSpeed += 0.2f;
            if(hSpeed >= 0)
                hSpeed = 0;
            
            found = collision.get(sides.LEFT);
            if(found.isEmpty()) {
                setX(xPosition + hSpeed);
                return true;
            } else {
                float maxX = -1;
                for(MapObject mo : found) {
                    
                    if(maxX == -1 || mo.getX() + mo.getW() > maxX)
                        maxX = mo.getX() + mo.getW();
                }
                
                if(maxX == -1) {
                    setX(xPosition + hSpeed);
                    return true;
                }
                
                hSpeed = 0;
                setX(maxX);
                return true;
            }
        }
        

        return false;
    }

    protected boolean moveV(EnumMap<sides, List<MapObject>> collision)
    {
        List<MapObject> found;
        
        if(vSpeed > 0) {
            found = collision.get(sides.TOP);
            if(found.isEmpty()) {
                setY(yPosition + vSpeed);
                return true;
            } else {
                float minY = -1;
                for(MapObject mo : found) {
                    
                    if(minY == -1 || mo.getY() < minY)
                        minY = mo.getY();
                }
                
                if(minY == -1) {
                    setY(yPosition + vSpeed);
                    return true;
                }
                
                vSpeed = 0;
                setY(minY - height);
                return true;
            }
        } else if(vSpeed < 0) {
            found = collision.get(sides.BOTTOM);
            if(found.isEmpty()) {
                setY(yPosition + vSpeed);
                
                
                found = collision().get(sides.BOTTOM);
                if(!found.isEmpty()) {
                float maxY = -1;
                for(MapObject mo : found) {
                    
                    if(maxY == -1 || mo.getY() > maxY)
                        maxY = mo.getY() + height;
                    }

                    if(maxY == -1) {
                        setY(yPosition + vSpeed);
                        return true;
                    }

                    vSpeed = 0;
                    setY(maxY);
                }
                
                return true;
            } else {
                float maxY = -1;
                for(MapObject mo : found) {
                    
                    if(maxY == -1 || mo.getY() > maxY)
                        maxY = mo.getY() + height;
                }
                
                if(maxY == -1) {
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
        collision.put(sides.TOP, new ArrayList<>());
        collision.put(sides.BOTTOM, new ArrayList<>());
        collision.put(sides.LEFT, new ArrayList<>());
        collision.put(sides.RIGHT, new ArrayList<>());
        collision.put(sides.CENTER, new ArrayList<>());

        
        List<MapObject> mos = playing.getObjects((int) Math.round(xPosition - 1), 
                                                (int) Math.round(yPosition - height - 1), 
                                                (int) Math.round(xPosition + width + 1), 
                                                (int) Math.round(yPosition + 1));
        
        for(MapObject mo : mos) {
            if(mo.equals(this))
                continue;
            
            List<sides> found = this.collision(mo);
            if(found != null) {
                for(sides s : found) {
                    collision.get(s).add(mo);
                }
            }
        }
        
        return collision;
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
    
    public ArrayList<sides> collision(MapObject mo) {
        if(this.solid == 0 || mo.solid == 0)
            return null;
        
        Rectangle r1 = new Rectangle(xPosition, yPosition, width, height);
        Rectangle r2 = new Rectangle(mo.getX(), mo.getY(), mo.getW(), mo.getH());
        
        if(r2.getX() == 4 && r2.getY() == 21) {
            System.err.println("");
        }
        
        boolean right = (r2.getX() >= r1.getX() && r2.getX() <= r1.getX() + r1.getWidth());
        boolean left  = (r2.getX() + r2.getWidth() >= r1.getX() && r2.getX() + r2.getWidth() <= r1.getX() + r1.getWidth());
        boolean top   = (r2.getY() - r2.getHeight() <= r1.getY() && r2.getY() - r2.getHeight() >= r1.getY() - r1.getHeight());
        boolean bott  = (r2.getY() >= r1.getY() - r1.getHeight() && r2.getY() <= r1.getY());
        
       if((right || left) && (top || bott)){
        ArrayList<sides> ret = new ArrayList<>();
        if(bott && top && left && right)
            ret.add(sides.CENTER);

        if(right && !(mo.getY() - mo.getH() >= yPosition || mo.getY() <= yPosition - height))
            ret.add(sides.RIGHT);
        if(left && !(mo.getY() - mo.getH() >= yPosition || mo.getY() <= yPosition - height))
            ret.add(sides.LEFT);
        if(top && !(mo.getX() + mo.getW() <= xPosition || mo.getX() >= xPosition + width))
            ret.add(sides.TOP);
        if(bott && !(mo.getX() + mo.getW() <= xPosition || mo.getX() >= xPosition + width))
            ret.add(sides.BOTTOM);
            
            return ret;
        } else {
            return null;
        }        
    }

    public abstract void hit(Tool used, sides hitDirection);
    public enum sides {
        
        TOP, BOTTOM, LEFT, RIGHT, CENTER
    }
}
