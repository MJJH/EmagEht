package thegame.com.Game.Objects;

import java.awt.Image;

/**
 *
 * @author laure
 */
public class Particle extends MapObject {

    /**
     *
     * @param object
     * @param x
     * @param y
     * @param skin
     * @param height
     * @param width
     * @param solid
     */
    public Particle(MapObject object, int x, int y, Image skin, int height, int width, float solid)
    {
        super(x, y, skin, height, width, solid);
    }

}
