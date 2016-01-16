package thegame.com.Game.Objects;

import display.IntColor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

/**
 * A tool is an object that a character can use to mine, fight, dig or chop
 *
 * @author Martijn
 */
public class Tool extends MapObject {

    private static final long serialVersionUID = 6529585098267757690L;

    public ToolType type;

    @Override
    public void createSkin()
    {
        try
        {
            this.skin = type.getSkin();
        } catch (IOException ex)
        {
        }
    }

    @Override
    public void update(MapObject update)
    {
    }
    
    @Override
    public void setType() {
        this.type = ToolType.tooltypes.get(this.type.name);
    }
}
