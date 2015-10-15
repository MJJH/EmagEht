package thegame.com.Game.Objects;

import thegame.com.Game.Objects.Characters.*;
import java.awt.Image;

/**
 *
 * @author laure
 */
public abstract class MapObject {

    CharacterGame backpack;
    private int id;
    private float xPosition;
    private float yPosition;
    private float hSpeed;
    private float vSpeed;
    private Image skin;
    private int height;
    private int width;
    private float solid;

    /**
     *
     * @param x
     * @param y
     * @param skin
     * @param height
     * @param width
     * @param solid
     */
    public MapObject(int x, int y, Image skin, int height, int width, float solid)
    {
        // TODO - implement MapObject.MapObject
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param skin
     * @param height
     * @param width
     */
    public MapObject(Image skin, int height, int width)
    {

    }

    /**
     *
     */
    public void update()
    {
        // TODO - implement MapObject.update
        throw new UnsupportedOperationException();
    }

}
