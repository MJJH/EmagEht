package thegame.com.Game.Objects;

import java.io.IOException;

/**
 * A class representing the type of block used
 *
 * @author Mark
 */
public enum BlockType {

    Dirt("Dirt", 5, 3, 0, 0, ToolType.toolType.SHOVEL, 1),
    Sand("Sand", 5, 3, 20, 20, ToolType.toolType.SHOVEL, 1),
    Stone("Stone", 25, 3, 0, 20, ToolType.toolType.PICKAXE, 1),
    Coal("Coal", 30, 3, 0, 100, ToolType.toolType.PICKAXE, 1),
    Copper("Copper", 40, 3, 0, 40, ToolType.toolType.PICKAXE, 1),
    Tin("Tin", 40, 3, 20, 40, ToolType.toolType.PICKAXE, 1),
    Iron("Iron", 60, 3, 0, 80, ToolType.toolType.PICKAXE, 1),
    Obsidian("Obsidian", 120, 3, 20, 80, ToolType.toolType.PICKAXE, 1),
    Wood("Wood", 10, 0, 0, 100, ToolType.toolType.AXE, 0);

    public final String name;
    public final int strength;
    public final int reqToolLvl;
    public final int imageX;
    public final int imageY;
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
    BlockType(String name, int strength, int reqLvl, int btx, int bty, ToolType.toolType req, float solid)
    {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.imageX = btx;
        this.imageY = bty;
        this.solid = solid;
    }
}
