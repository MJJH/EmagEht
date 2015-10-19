package thegame.com.Game.Objects;

import java.awt.Image;

/**
 * A class representing the type of Armor used.
 * @author Mark 
 */
public class ArmorType {

    final String name;
    final int dia;
    final int reqLvl;
    final Image skin;

    /**
     * Initiates an instance of this class with the following attributes
     * @param name The name of the ArmorType 
     * @param dia The diameter of the Armortype
     * @param reqLvl The required level of the Armortype
     * @param skin The look of the Armortype
     */
    public ArmorType(String name, int dia, int reqLvl, Image skin)
    {
     this.name = name;
     this.dia = dia;
     this.reqLvl = reqLvl;
     this.skin = skin;
    }
    
}
