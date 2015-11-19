package thegame.com.Game.Objects;

import java.rmi.RemoteException;
import thegame.com.Game.Map;

/**
 *
 * @author Mark
 */
public class Block extends MapObject {
    private static final long serialVersionUID = 5529685098267757690L;
    
    private int damage;
    private BlockType type;

    private boolean interaction;

    /**
     * Initiates this class
     *
     * @param type The type of the block
     * @param x the X-coordinate of its location
     * @param y the Y- coordinate of its location
     * @param solid A Float representing its liquefide state.
     * @param map
     * @throws java.rmi.RemoteException
     */
    public Block(BlockType type, float x, float y, float solid, Map map) throws RemoteException
    {
        super(x, y, type.skin, 1.0f, 1.0f, solid, map);
        this.type = type;
    }

    /**
     *
     * @param tool
     * @return
     */
    public int hitBlock(Tool tool)
    {
        return 0;
    }

    @Override
    public Boolean call()
    {
        if (interaction)
        {
            interaction = false;
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public void hit(Tool use, sides hitDirection)
    {
        interaction = true;
        playing.removeMapObject(this);
    }
    
    public void createSkin()
    {
        skin = type.skin;
    }
}
