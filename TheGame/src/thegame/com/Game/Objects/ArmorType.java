package thegame.com.Game.Objects;

import java.awt.Image;

/**
 * A class representing the type of Armor used.
 * @author Mark 
 */
public class ArmorType {

    private final String name;
    private final int multiplier;
    private final int reqLvl;
    private final Image skin;

    /**
     * Initiates an instance of this class with the following attributes
     * @param name The name of the ArmorType 
     * @param multiplier The multiplier of the Armortype
     * @param reqLvl The required level of the Armortype
     * @param skin The look of the Armortype
     */
    public ArmorType(String name, int multiplier, int reqLvl, Image skin)
    {
     this.name = name;
     this.multiplier = multiplier;
     this.reqLvl = reqLvl;
     this.skin = skin;
    }
    
}
