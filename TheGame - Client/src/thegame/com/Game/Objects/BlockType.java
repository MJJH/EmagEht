package thegame.com.Game.Objects;

import display.IntColor;
import display.Parts;
import display.Skin;
import display.iTexture;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

/**
 * A class representing the type of block used
 *
 * @author Mark
 */
public class BlockType extends ObjectType {
    private static final long serialVersionUID = 6522685098267704690L;
    public static Map<String, BlockType> blocktypes = new HashMap<>();
/*
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
    Wood("Wood", 10, 0, ToolType.toolType.AXE, 0, null, null),
    Leafleft("Leafleft", 0, 0, null, 0, Parts.Leafleft, new Color[]
    {
        null,
        null,
        null,
        null
    }),
    
     Leafright("Leafright", 0, 0, null, 0, Parts.Leafright, new Color[]
    {
        null,
        null,
        null,
        null
    }),
     
    CaveStone("CaveBackground", 0, 0, null, 1, Parts.Block, new Color[]
    {
        null,
        IntColor.rgb(40, 40, 40),
        IntColor.rgb(42, 42, 42),
        IntColor.rgb(44, 44, 44)
    });
*/
    public final int strength;
    public final int reqToolLvl;
    public final ToolType.toolType reqTool;
    public final float solid;
    private iTexture texture;

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
    public BlockType(String name, int strength, int reqLvl, ToolType.toolType req, float solid, iTexture skin, Color[] colors)
    {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.solid = solid;
        this.colors = colors;
        texture = skin;
        blocktypes.put(name, this);
    }

    public String getName()
    {
        return name;
    }

    protected Skin createSkin(Block t, Block b, Block l, Block r) {
        switch(this.getName()) {
            case "Wood":
                Parts[] possible; 
                if(t == null) {
                    possible = new Parts[] { Parts.TreeTop };
                } 
                else if(b != null && b.getType().getName() == "Wood") {
                   possible = new Parts[] { Parts.TreeMiddle, Parts.TreeMiddle1, Parts.TreeMiddle2, Parts.TreeMiddle3 }; 
                } else {
                   possible = new Parts[] { Parts.TreeMiddle, Parts.TreeTrunk1, Parts.TreeTrunk2 }; 
                }
                {
                    try {
                        display.Image i = new display.Image(possible[(int)(Math.random() * (possible.length))]);
                        i.recolour(new Color[] {
                            null,
                            IntColor.rgb(83, 49, 24),
                            IntColor.rgb(103, 60, 30),
                            IntColor.rgb(125, 80, 40),
                            IntColor.rgb(150, 98, 45),
                            IntColor.rgb(150, 108, 30)
                        });
                        return i;
                    } catch (IOException ex) {
                        Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "Dirt":
                if(t == null || t.getS() == 0 || t instanceof Background) {
                    try {
                        display.Image i = (display.Image) this.skin.clone();
                        i.addTexture(Parts.Top);
                        i.recolour(Parts.Top, new Color[] {
                            null,
                            IntColor.rgb(34, 177, 76),
                            IntColor.rgb(74, 200, 106),
                            IntColor.rgb(104, 255, 156),
                        });
                        return i;
                    } catch (IOException | CloneNotSupportedException ex) {
                        Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
        
        return this.skin;
    }

    @Override
    protected void createSkin() throws IOException {
        if(texture == null)
            return;
        
        display.Image i = new display.Image(texture);
        i.recolour(colors);
        this.skin = i;
    }
}
