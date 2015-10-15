package thegame.com.Game.Objects;

import java.awt.Image;

/**
 *
 * @author laure
 */
public class Armor extends MapObject {

    private final ArmorType type;

    /**
     *
     * @param type
     * @param skin
     * @param height
     * @param width
     */
    public Armor(ArmorType type, Image skin, int height, int width)
    {
        super(skin, height, width);
        this.type = type;
    }

}
