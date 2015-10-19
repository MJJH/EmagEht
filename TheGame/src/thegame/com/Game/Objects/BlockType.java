package thegame.com.Game.Objects;

import java.awt.Image;

/**
 * A class representing the type of block used
 * @author Mark
 */
public class BlockType {

    private final String name;
    private final int strength;
    private final int reqToolLvl;
    private final Image skin;

    /**
     * Initiates an instance of this class with the following attributes
     * @param name The name of the BlockType 
     * @param strength The strength of the Armortype
     * @param reqLvl The required level of the BlockType
     * @param skin The look of the Armortype
     */
    public BlockType(String name, int strength, int reqLvl, Image skin)
    {
     this.name = name;
     this.strength = strength;
     this.reqToolLvl = reqLvl;
     this.skin = skin;
    }
}
