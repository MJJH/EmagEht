package thegame.com.Game.Objects;

import java.rmi.RemoteException;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
