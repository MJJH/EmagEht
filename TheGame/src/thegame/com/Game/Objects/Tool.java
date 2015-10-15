package thegame.com.Game.Objects;

import java.awt.Image;

/**
 *
 * @author laure
 */
public class Tool extends MapObject {

    private final ToolType type;

    /**
     *
     * @param type
     * @param skin
     * @param height
     * @param width
     */
    public Tool(ToolType type, Image skin, int height, int width)
    {
        super(skin, height, width);
        this.type = type;
    }

}
