package thegame.com.Game.Objects;

import thegame.com.Game.GameLogic;
import thegame.com.Game.Map;

/**
 *
 * @author Mark 
 */
public class Armor extends MapObject {

    private final ArmorType type;

    /**
     * Initiates an instance of this class with the type as an attribute
     * @param type The type of the armor ( From the class ArmorType)
     * @param height the height of the armor
     * @param width the width of the armor
     * @param map the map with this armor
     * @param gameLogic
     */
    public Armor(ArmorType type, float height, float width, Map map, GameLogic gameLogic)
    {
        super(height, width, map, gameLogic);
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

    @Override
    public Boolean call() {return false;}

    @Override
    public void hit(Tool use, sides hitDirection) {}
}
