package thegame.com.Game.Objects;

import java.awt.Image;

/**
 *  A particle is a MapObject that belongs to nobody and has no use but to pick up
 * @author Martijn
 */
public class Particle extends MapObject {

    /**
     * Create a new particle
     * @param object    the object this particle used to be and will be once picked up
     * @param x         the horizontal position of this object
     * @param y         the vertical position of this object
     * @param skin      the texture of this object
     * @param height    the height of this object
     * @param width     the width of this object
     * @param solid     the density of this object
     */
    public Particle(MapObject object, int x, int y, Image skin, int height, int width, float solid)
    {
        super(x, y, skin, height, width, solid);
    }

}
