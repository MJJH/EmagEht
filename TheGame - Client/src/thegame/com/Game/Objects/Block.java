package thegame.com.Game.Objects;

import display.Image;
import display.IntColor;
import display.Parts;
import java.io.IOException;
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

    protected int damage;
    protected BlockType type;

    @Override
    public void createSkin()
    {            
        try {
            skin = type.getSkin();
        } catch (IOException ex) {
        }
        
        /*
        if(type.skin == null || type.getName() == "Dirt")
        {
            Block t = playing.getBlock((int) yPosition + 1, (int) xPosition);
            Block b = playing.getBlock((int) yPosition - 1, (int) xPosition);
            Block r = playing.getBlock((int) yPosition, (int) xPosition + 1);
            Block l = playing.getBlock((int) yPosition, (int) xPosition - 1);
            skin = type.createSkin(t, b, l, r);
        } */
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
