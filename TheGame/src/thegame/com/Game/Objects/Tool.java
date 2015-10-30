package thegame.com.Game.Objects;

import javafx.scene.image.Image;
import thegame.com.Game.Map;

/**
 * A tool is an object that a character can use to mine, fight, dig or chop
 * @author Martijn
 */
public class Tool extends MapObject {

    private final ToolType type;

    /**
     * Create a usable tool
     * @param type      the type of this tool (sword, hatchet, etc)
     * @param height    the height of this object
     * @param width     the width of this object
     */
    public Tool(ToolType type, int height, int width, Map map)
    {
        super(type.skin, type.height, type.width, map);
        this.type = type;
    }

    @Override
    public void update() {}

}
