package thegame.com.Game.Objects;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the type of block used
 *
 * @author Mark
 */
public class BlockType extends ObjectType {
    private static final long serialVersionUID = 6522685098267704690L;
    public static Map<String, BlockType> blocktypes = new HashMap<>();

    public final int strength;
    public final int reqToolLvl;
    public final ToolType.toolType reqTool;
    public final float solid;

    /**
     * Initiates an instance of this class with the following attributes
     *
     * @param name The name of the BlockType
     * @param strength The strength of the Armortype
     * @param reqLvl The required level of the BlockType
     * @param btx
     * @param bty
     */
    public BlockType(String name, int strength, int reqLvl, ToolType.toolType req, float solid)
    {
        super(name);
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.solid = solid;
        
        blocktypes.put(name, this);
    }

    public String getName()
    {
        return name;
    }
}
