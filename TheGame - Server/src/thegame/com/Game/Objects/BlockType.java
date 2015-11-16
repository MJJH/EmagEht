package thegame.com.Game.Objects;

import display.Animation;
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
    Dirt("Dirt", 5, 3, 0, 0, ToolType.toolType.SHOVEL),
    Sand("Sand", 5, 3, 20, 20, ToolType.toolType.SHOVEL),
    Stone("Stone", 25, 3, 0, 20, ToolType.toolType.PICKAXE),
    Coal("Coal", 30, 3, 0, 100, ToolType.toolType.PICKAXE),
    Copper("Copper", 40, 3, 0, 40, ToolType.toolType.PICKAXE),
    Tin("Tin", 40, 3, 20, 40, ToolType.toolType.PICKAXE),
    Iron("Iron", 60, 3, 0, 80, ToolType.toolType.PICKAXE),
    Obsidian("Obsidian", 120, 3, 20, 80, ToolType.toolType.PICKAXE);
    
    public final String name;
    public final int strength;
    public final int reqToolLvl;
    public final int imageX;
    public final int imageY;
    public final ToolType.toolType reqTool;
    public final Skin skin;

    /**
     * Initiates an instance of this class with the following attributes
     *
     * @param name The name of the BlockType
     * @param strength The strength of the Armortype
     * @param reqLvl The required level of the BlockType
     * @param btx
     * @param bty
     */
    BlockType(String name, int strength, int reqLvl, int btx, int bty, ToolType.toolType req) {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.imageX = btx;
        this.imageY = bty;
        
        this.skin = this.createSkin();
    }
    

    /**
     *
     * @return @throws IOException
     */
    public Skin createSkin()
    {
        try {
            if(name == "Obsidian") {
                Animation test = new Animation(60);
                display.Image i = new display.Image(20, 20, "src/resources/mapping.png", 40, 0, 20, 20);
                i.recolour(new Color[]{Color.TRANSPARENT, Color.RED, Color.BLUE, Color.GRAY, Color.GREEN, Color.GREENYELLOW, Color.AQUA});
                test.addFrame(i);
                display.Image i2 = new display.Image(20, 20, "src/resources/mapping.png", 40, 0, 20, 20);
                i2.recolour(new Color[]{Color.BLUE, Color.RED, Color.BLUE, Color.GRAY, Color.RED, Color.RED, Color.RED});
                test.addFrame(i2);
                return test;
            } else
                return new display.Image(20, 20, "src/resources/mapping.png", imageX, imageY, 20, 20);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        return null;
    }
}
