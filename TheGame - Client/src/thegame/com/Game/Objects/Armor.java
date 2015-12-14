package thegame.com.Game.Objects;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Game.Map;

/**
 *
 * @author Mark 
 */
public class Armor extends MapObject {

    private ArmorType type;

    /**
     * The method to get the armor type.
     * @return the armor type
     */
    public ArmorType getArmorType ()
    {
        return type;
    }
    
    @Override
    public void createSkin() {
        try {
            this.skin = type.getSkin();
        } catch (IOException ex) {
            Logger.getLogger(Armor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
