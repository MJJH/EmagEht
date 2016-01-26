package thegame.com.Game.Objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A tooltype defines what a tool will be able to do
 *
 * @author Martijn
 */
public class ToolType extends ObjectType {

    private static final long serialVersionUID = 6522685098267757690L;

    public static Map<String, ToolType> tooltypes = new HashMap<>();

    public int strength;
    public float speed;
    public float range;
    public int reqLvl;
    public float kb;
    public float height;
    public float width;
    public toolType type;

    public enum toolType implements Serializable {

        PICKAXE, AXE, SWORD, SHOVEL, FLINT
    }

    /**
     *
     * @param name
     * @param strength
     * @param speed
     * @param range
     * @param reqLvl
     * @param type
     * @param kb
     * @param skin
     * @param height
     * @param width
     */
    public ToolType(String name, int strength, float speed, float range, int reqLvl, toolType type, float kb)
    {
        super(name);
        this.strength = strength;
        this.speed = speed;
        this.range = range;
        this.reqLvl = reqLvl;
        this.kb = kb;
        this.type = type;

        tooltypes.put(name, this);
    }

}
