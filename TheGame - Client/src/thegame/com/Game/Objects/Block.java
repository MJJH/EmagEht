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
        skin = type.skin;
        /*if((type != type.Dirt))
            return;

        display.Image i = new display.Image((Image) skin);
        skin = i;

        Block t = playing.getBlock((int) yPosition + 1, (int) xPosition);
        //Block b = playing.getBlock((int) yPosition - 1, (int) xPosition);
        //Block r = playing.getBlock((int) yPosition, (int) xPosition + 1);
        //Block l = playing.getBlock((int) yPosition, (int) xPosition - 1);

        //Block t = null, b = null, l = null, r = null;
        
        /*if(b != null && !b.getType().equals(this.getType())) {
            i.addBorder(Image.borderSide.BOTTOM, Color.BLACK, 1);
        }
        if(r != null && !r.getType().equals(this.getType())) {
            i.addBorder(Image.borderSide.RIGHT, Color.BLACK, 1);
        }
        if(l != null && !l.getType().equals(this.getType())) {
            i.addBorder(Image.borderSide.LEFT, Color.BLACK, 1);
        }
        if(t != null && !t.getType().equals(this.getType())) {
            i.addBorder(Image.borderSide.TOP, Color.BLACK, 1);
        }*/
        
        /*if(t == null || !(t instanceof Block) || t instanceof Background) {
            i.addBorder(Image.borderSide.TOP, Color.GREEN, 5);
        }*/
        /*if(l == null || !(l instanceof Block) || l instanceof Background) {
            i.addBorder(Image.borderSide.LEFT, Color.GREEN, 2);
        }
        if(r == null || !(r instanceof Block) || r instanceof Background) {
            i.addBorder(Image.borderSide.RIGHT, Color.GREEN, 2);
        }*/
        /*if(b == null || !(b instanceof Block) || b instanceof Background) {
            i.addBorder(Image.borderSide.BOTTOM, Color.BLACK, 2);
        }*/
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
