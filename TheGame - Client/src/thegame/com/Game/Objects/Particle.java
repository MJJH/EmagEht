package thegame.com.Game.Objects;

import java.rmi.RemoteException;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.CharacterGame;

/**
 * A particle is a MapObject that belongs to nobody and has no use but to pick
 * up
 *
 * @author Martijn
 */
public class Particle extends MapObject {

    private MapObject object;
    private final int count;

    /**
     * Create a new particle
     *
     * @param object the object this particle used to be and will be once picked
     * up
     * @param x the horizontal position of this object
     * @param y the vertical position of this object
     * @param height the height of this object
     * @param width the width of this object
     * @param solid the density of this object
     * @param map
     * @param count the ammount of this object
     * @throws java.rmi.RemoteException
     */
    public Particle(MapObject object, int x, int y, int height, int width, float solid, Map map, int count) throws RemoteException
    {
        super(x, y, object.getSkin(), height, width, 0, map);
        setObject(object);
        this.count = count;
    }

    private void setObject(MapObject object)
    {
        if (object == null
                || object instanceof CharacterGame
                || object instanceof Liquid)
        {
            throw new IllegalArgumentException("Illegal object as Particle");
        } else
        {
            this.object = object;
        }
    }

    @Override
    public Boolean call()
    {
        return false;
        // Fall And Maybe go in 
    }

    @Override
    public void createSkin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
