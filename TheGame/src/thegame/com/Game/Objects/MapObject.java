package thegame.com.Game.Objects;

import thegame.com.Game.Objects.Characters.*;
import java.awt.Image;

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
    private int height;
    private int width;
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
    public MapObject(int x, int y, Image skin, int height, int width, float solid)
    {
        // TODO - implement MapObject.MapObject
        throw new UnsupportedOperationException();
    }

    /**
     * Create a new MapObject without position and density
     * @param skin
     * @param height
     * @param width
     */
    public MapObject(Image skin, int height, int width)
    {

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

}
