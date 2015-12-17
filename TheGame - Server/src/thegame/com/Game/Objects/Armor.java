package thegame.com.Game.Objects;

import thegame.GameClientToServerHandler;
import thegame.com.Game.Map;

/**
 *
 * @author Mark
 */
public class Armor extends MapObject {

    private static final long serialVersionUID = 5529685098264757290L;

    private ArmorType type;

    /**
     * Initiates an instance of this class with the type as an attribute
     *
     * @param type The type of the armor ( From the class ArmorType)
     * @param height the height of the armor
     * @param width the width of the armor
     * @param map the map with this armor
     */
    public Armor(ArmorType type, float height, float width, Map map)
    {
        super(height, width, map);
        this.type = type;
    }

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
    public Boolean call()
    {
        return false;
    }

    @Override
    public void hit(Tool use, sides hitDirection)
    {
    }
}
