package thegame.com.Game.Objects;

import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.engine.Movement;
import thegame.engine.Physics;

/**
 * A particle is a MapObject that belongs to nobody and has no use but to pick
 * up
 *
 * @author Martijn
 */
public class Particle extends MapObject {

    private static final long serialVersionUID = 5529682098267757690L;

    private MapObject object;
    private int count;
    private transient boolean pickedUp;

    /**
     * Create a new particle
     *
     * @param object the object this particle used to be and will be once picked
     * up
     * @param x the horizontal position of this object
     * @param y the vertical position of this object
     * @param map
     */
    public Particle(MapObject object, float x, float y, Map map)
    {
        super(x + (object.getW() / 4), y - (object.getH() / 4), 0.5f, 0.5f, 0, map);
        setObject(object);
        this.count = 1;
        sXDecay = 0.04f;
        sXIncrease = 0.075f;
        sYIncrease = 0.05f;
        sXMax = 0.3f;
        sYMax = 0.3f;
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

    public MapObject getObject()
    {
        return object;
    }

    public void addObjectCount()
    {
        count++;
        playing.addToUpdate(this);
    }

    public int getObjectCount()
    {
        return count;
    }

    @Override
    public Boolean call()
    {
        Boolean ret = false;
        
        if (Physics.gravity(this))
        {
            ret = true;
        }

        if (Movement.moveH(this))
        {
            ret = true;
        }

        if (Movement.moveV(this))
        {
            ret = true;
        }
        
        if (Movement.deglitch(this))
        {
            ret = true;
        }

        return ret;
    }

    @Override
    public void hit(Tool use, sides hitDirection)
    {
    }

    public void setPickedUp(boolean b)
    {
        pickedUp = b;
    }

    public boolean getPickedUp()
    {
        return pickedUp;
    }

}
