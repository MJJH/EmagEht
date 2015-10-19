package thegame.com.Game.Objects;

import thegame.com.Game.Objects.Characters.*;
import javafx.scene.image.Image;

/**
 * An object that can be drawn on the map
 * @author Martijn
 */
public abstract class MapObject {

    //private int id;
    private float xPosition;
    private float yPosition;
    private float hSpeed;
    private float vSpeed;
    private Image skin;
    private float height;
    private float width;
    private float solid;

    /**
     * Create a new MapObject to use in the game
     * @param x         the horizontal position of this object
     * @param y         the vertical position of this object
     * @param skin      the texture of this object
     * @param height    the height of this object
     * @param width     the width of this object
     * @param solid     the density of this object
     */
    public MapObject(float x, float y, Image skin, float height, float width, float solid)
    {
        this.setX(x);
        this.setY(y);
        this.setH(height);
        this.setW(width);
        this.setS(solid);
        
        this.skin = skin;
    }

    /**
     * Create a new MapObject without position and density
     * @param skin
     * @param height
     * @param width
     */
    public MapObject(Image skin, float height, float width)
    {
        this.setH(height);
        this.setW(width);
        
        this.skin = skin;
    }

    /**
     * Update the object
     * Will be called everytime the map is updated
     */
    public void update()
    {
        // TODO - implement MapObject.update
        throw new UnsupportedOperationException();
    }
    
    private void setX(float x) {
        if(x >= 0)
            this.xPosition = x;
        else
            this.xPosition = 0;
    }
    
    private void setY(float y) {
        if(y >= 0)
            this.yPosition = y;
        else
            this.yPosition = 0;
    }
    
    private void setH(float h) {
        if(h > 0)
            this.height = Float.floatToIntBits(h);
        else
            throw new IllegalArgumentException("Height cant be 0.");
    }
    
    private void setW(float w) {
        if(w > 0)
            this.height = Float.floatToIntBits(w);
        else
            throw new IllegalArgumentException("Width cant be 0.");
    }
    
    private void setS(float s) {
        if(s >= 0 && s <= 1)
            this.solid = s;
        else
            throw new IllegalArgumentException("Solid should be between 0 and 1");
    }
    
    /**
     * Get x (horizontal) position
     * @return xPosition
     */
    public float getX() {
        return xPosition;
    }
    
    /**
     * Get y (vertical) position
     * @return yPosition
     */
    public float getY() {
        return yPosition;
    }
    
    /**
     * Get height
     * @return height
     */
    public float getH() {
        return height;
    }
    
    /**
     * Get width
     * @return width
     */
    public float getW() {
        return width;
    }
    
    /**
     * Get density of object
     * @return solid
     */
    public float getS() {
        return solid;
    }
    
    /**
     * Get horizontal speed
     * @return hSpeed
     */
    public float getSX() {
        return hSpeed;
    }
    
    /**
     * Get vertical speed
     * @return vSpeed
     */
    public float getSY() {
        return vSpeed;
    }
    
    /**
     * Get skin
     * @return skin
     */
    public Image getSkin() {
        return skin;
    }
}
