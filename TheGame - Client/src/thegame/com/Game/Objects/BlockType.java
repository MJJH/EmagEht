package thegame.com.Game.Objects;

import display.Animation;
import display.IntColor;
import display.Parts;
import display.Skin;
import display.iTexture;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A class representing the type of block used
 *
 * @author Mark
 */
public enum BlockType {

    Dirt("Dirt", 5, 3, ToolType.toolType.SHOVEL, 1, Parts.Block, new Color[]
    {
        null,
        IntColor.rgb(68, 46, 32),
        IntColor.rgb(108, 72, 51),
        IntColor.rgb(158, 106, 75)
    }),
    Sand("Sand", 5, 3, ToolType.toolType.SHOVEL, 1, Parts.Block, new Color[]
    {
        null,
        IntColor.rgb(255, 230, 53),
        IntColor.rgb(255, 238, 117),
        IntColor.rgb(255, 241, 186)
    }),
    Stone("Stone", 25, 3, ToolType.toolType.PICKAXE, 1, Parts.Block, new Color[]
    {
        null,
        IntColor.rgb(87, 87, 87),
        IntColor.rgb(110, 110, 110),
        IntColor.rgb(159, 159, 159)
    }),
    Coal("Coal", 30, 3, ToolType.toolType.PICKAXE, 1, Parts.Ore, new Color[]
    {
        null,
        IntColor.rgb(10, 10, 10),
        IntColor.rgb(23, 20, 20),
        IntColor.rgb(40, 40, 40),
        IntColor.rgb(100, 100, 120)
    }),
    Copper("Copper", 40, 3, ToolType.toolType.PICKAXE, 1, Parts.Ore, new Color[]
    {
        null,
        IntColor.rgb(97, 87, 87),
        IntColor.rgb(120, 110, 110),
        IntColor.rgb(169, 159, 159),
        IntColor.rgb(170, 100, 100)
    }),
    Tin("Tin", 40, 3, ToolType.toolType.PICKAXE, 1, Parts.Ore, new Color[]
    {
        null,
        IntColor.rgb(100, 100, 100),
        IntColor.rgb(140, 140, 150),
        IntColor.rgb(189, 189, 199),
        IntColor.rgb(240, 240, 255)
    }),
    Iron("Iron", 60, 3, ToolType.toolType.PICKAXE, 1, Parts.Ore, new Color[]
    {
        null,
        IntColor.rgb(87, 87, 97),
        IntColor.rgb(120, 110, 130),
        IntColor.rgb(169, 159, 169),
        IntColor.rgb(130, 130, 180)
    }),
    Obsidian("Obsidian", 120, 3, ToolType.toolType.PICKAXE, 1, Parts.Block, new Color[]
    {
        null,
        IntColor.rgb(12, 0, 57),
        IntColor.rgb(18, 10, 60),
        IntColor.rgb(18, 10, 80)
    }),
    Wood("Wood", 10, 0, ToolType.toolType.AXE, 0, Parts.Block, new Color[]
    {
        null,
        IntColor.rgb(130, 90, 30),
        IntColor.rgb(133, 97, 35),
        IntColor.rgb(140, 110, 40)
    }),
    
    CaveStone("CaveBackground", 0, 0, null, 1, Parts.Block, new Color[]
    {
        null,
        IntColor.rgb(37, 37, 37),
        IntColor.rgb(60, 60, 60),
        IntColor.rgb(109, 109, 109)
    });

    public final String name;
    public final int strength;
    public final int reqToolLvl;
    public final ToolType.toolType reqTool;
    public final float solid;

    public Skin skin;
    public final Color[] colors;

    /**
     * Initiates an instance of this class with the following attributes
     *
     * @param name The name of the BlockType
     * @param strength The strength of the Armortype
     * @param reqLvl The required level of the BlockType
     * @param btx
     * @param bty
     */
    BlockType(String name, int strength, int reqLvl, ToolType.toolType req, float solid, iTexture skin, Color[] colors)
    {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.solid = solid;
        this.colors = colors;

        display.Image i;
        try
        {
            i = new display.Image(skin);
            i.recolour(colors);
            this.skin = i;
        } catch (IOException ex)
        {
            Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getName()
    {
        return name;
    }
}
