package thegame.com.Game.Objects;

/**
 * A class representing the type of Armor used.
 *
 * @author Mark
 */
public class ArmorType extends ObjectType {

    private static final long serialVersionUID = 5529685098264757690L;

    public String name;
    public int multiplier;
    public int reqLvl;
    public bodyPart bodypart;

    public enum bodyPart {

        HELMET, CHESTPLATE, GREAVES, SHIELD
    }
}
