package thegame.com.Game.Objects;

import display.Animation;
import display.IntColor;
import display.Parts;
import display.Skin;
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
    Dirt("Dirt", 5, 3, 0, 0, ToolType.toolType.SHOVEL, 1, new Color[] { 
        null,
        IntColor.rgb(68, 46, 32),
        IntColor.rgb(108, 72, 51),
        IntColor.rgb(158, 106, 75)
    }),
    Sand("Sand", 5, 3, 20, 20, ToolType.toolType.SHOVEL, 1, new Color[] { 
        null,
        IntColor.rgb(255, 230, 53),
        IntColor.rgb(255, 238, 117),
        IntColor.rgb(255, 241, 186)
    }),
    Stone("Stone", 25, 3, 0, 20, ToolType.toolType.PICKAXE, 1, new Color[] { 
        null,
        IntColor.rgb(87, 87, 87),
        IntColor.rgb(110, 110, 110),
        IntColor.rgb(159, 159, 159)
    }),
    Coal("Coal", 30, 3, 0, 100, ToolType.toolType.PICKAXE, 1, new Color[] { 
        new Color(0.24, 0.12, 0.01, 1),
        new Color(0.35, 0.18, 0, 1),
        new Color(148/255, 78/255, 7/255, 1)
    }),
    Copper("Copper", 40, 3, 0, 40, ToolType.toolType.PICKAXE, 1, new Color[] { 
        new Color(0.24, 0.12, 0.01, 1),
        new Color(0.35, 0.18, 0, 1),
        new Color(148/255, 78/255, 7/255, 1)
    }),
    Tin("Tin", 40, 3, 20, 40, ToolType.toolType.PICKAXE, 1, new Color[] { 
        new Color(0.24, 0.12, 0.01, 1),
        new Color(0.35, 0.18, 0, 1),
        new Color(148/255, 78/255, 7/255, 1)
    }),
    Iron("Iron", 60, 3, 0, 80, ToolType.toolType.PICKAXE, 1, new Color[] { 
        new Color(0.24, 0.12, 0.01, 1),
        new Color(0.35, 0.18, 0, 1),
        new Color(148/255, 78/255, 7/255, 1)
    }),
    Obsidian("Obsidian", 120, 3, 20, 80, ToolType.toolType.PICKAXE, 1, new Color[] { 
        new Color(0.24, 0.12, 0.01, 1),
        new Color(0.35, 0.18, 0, 1),
        new Color(148/255, 78/255, 7/255, 1)
    }),
    
    Wood("Wood", 10, 0, 0, 100, ToolType.toolType.AXE, 0, new Color[] { 
        new Color(0.24, 0.12, 0.01, 1),
        new Color(0.35, 0.18, 0, 1),
        new Color(148/255, 78/255, 7/255, 1)
    });
    
    public final String name;
    public final int strength;
    public final int reqToolLvl;
    public final int imageX;
    public final int imageY;
    public final ToolType.toolType reqTool;
    public final Skin skin;
    public final float solid;
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
    BlockType(String name, int strength, int reqLvl, int btx, int bty, ToolType.toolType req, float solid, Color[] colors) {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.imageX = btx;
        this.imageY = bty;
        this.solid = solid;
        this.colors = colors;
        this.skin = this.createSkin();
    }
    
    BlockType(String name, int strength, int reqLvl, ToolType.toolType req, float solid, Skin skin, Color[] colors) {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.imageX = 0;
        this.imageY = 0;
        this.solid = solid;
        this.colors = colors;
        this.skin = skin;
    }
    

    /**
     *
     * @return @throws IOException
     */
    public Skin createSkin()
    {
        try {
            display.Image i = new display.Image(Parts.Block);
            i.recolour(colors);
            return i;
        } catch (IOException ex) {
            return null;
        }
    }
}
