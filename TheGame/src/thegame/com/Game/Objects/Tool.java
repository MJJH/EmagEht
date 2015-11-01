package thegame.com.Game.Objects;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import thegame.com.Game.Map;

/**
 * A tool is an object that a character can use to mine, fight, dig or chop
 * @author Martijn
 */
public class Tool extends MapObject {

    public final ToolType type;

    /**
     * Create a usable tool
     * @param type      the type of this tool (sword, hatchet, etc)
     * @param map       the map this tool is used on
     */
    public Tool(ToolType type, Map map)
    {
        super(type.skin, type.height, type.width, map);
        this.type = type;
    }

    @Override
    public void update() {}
    
    @Override
    public void hit(Tool use, Direction hitDirection) {}

}
