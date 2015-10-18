package thegame.com.Game.Objects;

import java.awt.Image;

/**
 * A tool is an object that a character can use to mine, fight, dig or chop
 * @author Martijn
 */
public class Tool extends MapObject {

    private final ToolType type;

    /**
     * Create a usable tool
     * @param type      the type of this tool (sword, hatchet, etc)
     * @param skin      the texture of this object
     * @param height    the height of this object
     * @param width     the width of this object
     */
    public Tool(ToolType type, Image skin, int height, int width)
    {
        super(skin, height, width);
        this.type = type;
    }

}
