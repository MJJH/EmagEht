package thegame.com.Game.Objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the type of Armor used.
 *
 * @author Mark
 */
public class ArmorType extends ObjectType {

    private static final long serialVersionUID = 5529685098264757690L;

    public static Map<String, ArmorType> armortypes = new HashMap<>();
    public int multiplier;
    public int reqLvl;
    public bodyPart bodypart;

    public ArmorType(String name, int multiplier, int reqLvl, bodyPart bodypart)
    {
        super(name);
        this.multiplier = multiplier;
        this.reqLvl = reqLvl;
        this.bodypart = bodypart;

        armortypes.put(name, this);
    }

    public enum bodyPart implements Serializable{

        HELMET, CHESTPLATE, GREAVES, SHIELD
    }
}
