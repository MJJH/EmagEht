package thegame.com.Game.Objects;

import thegame.GameClientToServerHandler;
import thegame.com.Game.Map;

/**
 * A tool is an object that a character can use to mine, fight, dig or chop
 *
 * @author Martijn
 */
public class Tool extends MapObject {

    private static final long serialVersionUID = 6529585098267757690L;

    public final ToolType type;

    /**
     * Create a usable tool
     *
     * @param type the type of this tool (sword, hatchet, etc)
     * @param map the map this tool is used on
     */
    public Tool(ToolType type, Map map)
    {
        super(type.height, type.width, map);
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
