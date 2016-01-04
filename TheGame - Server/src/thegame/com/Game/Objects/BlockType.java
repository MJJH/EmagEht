package thegame.com.Game.Objects;

import java.io.IOException;

/**
 * A class representing the type of block used
 *
 * @author Mark
 */
public enum BlockType implements iObjectType {

    Dirt("Dirt", 5, 3, ToolType.toolType.SHOVEL, 1),
    Sand("Sand", 5, 3, ToolType.toolType.SHOVEL, 1),
    Stone("Stone", 25, 3, ToolType.toolType.PICKAXE, 1),
    Coal("Coal", 30, 3, ToolType.toolType.PICKAXE, 1),
    Copper("Copper", 40, 3, ToolType.toolType.PICKAXE, 1),
    Tin("Tin", 40, 3, ToolType.toolType.PICKAXE, 1),
    Iron("Iron", 60, 3, ToolType.toolType.PICKAXE, 1),
    Obsidian("Obsidian", 120, 3, ToolType.toolType.PICKAXE, 1),
    Wood("Wood", 10, 0, ToolType.toolType.AXE, 0),
    CaveStone("CaveStone", 0, 0, null, 0),
    Leafleft("Leafleft",0,0,null,0),
    Leafright("Leafright",0,0,null,0);

    public final String name;
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
    BlockType(String name, int strength, int reqLvl, ToolType.toolType req, float solid)
    {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.solid = solid;
    }

    public String getName()
    {
        return name;
    }
}
