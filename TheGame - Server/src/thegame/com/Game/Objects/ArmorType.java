package thegame.com.Game.Objects;

import java.io.Serializable;


/**
 * A class representing the type of Armor used.
 * @author Mark 
 */
public class ArmorType implements Serializable{

    public final String name;
    public final int multiplier;
    public final int reqLvl;
    public final bodyPart bodypart;

    public enum bodyPart { HELMET, CHESTPLATE, GREAVES, BOOTS, SHIELD }

    /**
     * Initiates an instance of this class with the following attributes
     * @param name The name of the ArmorType 
     * @param multiplier The multiplier of the Armortype
     * @param reqLvl The required level of the Armortype
     * @param bodyPart The place on the body where to wear the Armor
     */
    public ArmorType(String name, int multiplier, int reqLvl, bodyPart bodyPart)
    {
        this.name = name;
        this.multiplier = multiplier;
        this.reqLvl = reqLvl;
        this.bodypart = bodyPart;
    }
    
}
