package thegame.com.Game.Objects;

import display.IntColor;
import java.io.IOException;
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
    public void createSkin() {
        try {
            display.Image i = new display.Image(display.Parts.Flint);
            i.recolour(new Color[] { 
                null,
                IntColor.rgb(35, 25, 25),
                IntColor.rgb(60, 60, 60),
                IntColor.rgb(100, 100, 110)
            });
            this.skin = i;
        } catch (IOException ex) {
            
        }
    }
}
