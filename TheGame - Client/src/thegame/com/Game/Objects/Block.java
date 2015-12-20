package thegame.com.Game.Objects;

import display.Image;
import java.util.EnumMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import thegame.engine.Collision;

/**
 *
 * @author Mark
 */
public class Block extends MapObject {

    private static final long serialVersionUID = 5529685098267757690L;

    private int damage;
    private BlockType type;

    @Override
    public void createSkin()
    {
        try {
            skin = type.skin;
            if(!(skin instanceof display.Image))
                return;
            
            display.Image i = (display.Image) skin.clone();
            skin = i;
            
            Block t = playing.getBlock((int) yPosition + 1, (int) xPosition);
            Block b = playing.getBlock((int) yPosition - 1, (int) xPosition);
            Block l = playing.getBlock((int) yPosition, (int) xPosition + 1);
            Block r = playing.getBlock((int) yPosition, (int) xPosition - 1);
            
            if(t == null) {
                i.addBorder(Image.borderSide.TOP, Color.BLACK);
            }
            if(l == null) {
                i.addBorder(Image.borderSide.LEFT, Color.BLACK);
            }
            if(r == null) {
                i.addBorder(Image.borderSide.RIGHT, Color.BLACK);
            }
            if(b == null) {
                i.addBorder(Image.borderSide.BOTTOM, Color.BLACK);
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BlockType getType()
    {
        return type;
    }

    @Override
    public void update(MapObject update)
    {
        setX(update.getX());
        setY(update.getY());
    }
}
