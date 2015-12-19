package thegame.com.Game.Objects;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark
 */
public class Armor extends MapObject {

    private static final long serialVersionUID = 5529685098264757290L;

    private ArmorType type;

    /**
     * The method to get the armor type.
     *
     * @return the armor type
     */
    public ArmorType getArmorType()
    {
        return type;
    }

    @Override
    public void createSkin()
    {
        try
        {
            this.skin = type.getSkin();
        } catch (IOException ex)
        {
            Logger.getLogger(Armor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(MapObject update)
    {
    }
}
