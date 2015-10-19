package thegame.com.Game.Objects;

import java.awt.Image;
import thegame.com.Game.Objects.Characters.CharacterGame;

/**
 *  A particle is a MapObject that belongs to nobody and has no use but to pick up
 * @author Martijn
 */
public class Particle extends MapObject {

    private MapObject object;
    
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
    public Particle(MapObject object, int x, int y, int height, int width, float solid)
    {
        super(x, y, object.getSkin(), height, width, 0);
        setObject(object);
    }
    
    private void setObject(MapObject object) {
        if (object == null ||
            object instanceof CharacterGame ||
            object instanceof Liquid)
            throw new IllegalArgumentException("Illegal object as Particle");
        
        else
            this.object = object;
    }

}
