package thegame.com.Game.Objects;

import javafx.scene.image.Image;

/**
 *
 * @author Mark 
 */
public class Armor extends MapObject {

    private final ArmorType type;

    /**
     * Initiates an instance of this class with the type as an attribute
     * @param type The type of the armor ( From the class ArmorType) 
     * @param skin the look of the armor
     * @param height the height of the armor
     * @param width the width of the armor
     */
    public Armor(ArmorType type,Image skin, int height, int width)
    {
        super(skin, height, width);
        this.type = type;
    }
    
    /**
     * The method to get the armor type.
     * @return the armor type
     */
    public ArmorType getArmorType ()
    {
        return type;
    }
}
