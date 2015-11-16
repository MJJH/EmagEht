package thegame.com.Game.Objects;

import java.rmi.RemoteException;
import thegame.com.Game.Map;

/**
 * A tool is an object that a character can use to mine, fight, dig or chop
 *
 * @author Martijn
 */
public class Tool extends MapObject {

    public final ToolType type;

    /**
     * Create a usable tool
     *
     * @param type the type of this tool (sword, hatchet, etc)
     * @param map the map this tool is used on
     * @throws java.rmi.RemoteException
     */
    public Tool(ToolType type, Map map) throws RemoteException
    {
        super(type.skin, type.height, type.width, map);
        this.type = type;
    }

    @Override
    public Boolean call()
    {
        return false;
    }

    @Override
    public void hit(Tool use, sides hitDirection)
    {
    }

}
