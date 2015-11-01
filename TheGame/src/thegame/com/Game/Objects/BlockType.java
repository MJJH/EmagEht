package thegame.com.Game.Objects;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

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
    public final ToolType.toolType reqTool;
    public final Image skin;

    /**
     * Initiates an instance of this class with the following attributes
     *
     * @param name The name of the BlockType
     * @param strength The strength of the Armortype
     * @param reqLvl The required level of the BlockType
     * @param btx
     * @param bty
     */
    BlockType(String name, int strength, int reqLvl, float btx, float bty, ToolType.toolType req) {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
        this.reqTool = req;
        this.skin = getskin(btx, bty);
    }
    

    /**
     *
     * @return @throws IOException
     */
    public Image getskin(float btx, float bty)
    {
        int X = Math.round(btx);
        int Y = Math.round(bty);
        BufferedImage bigImg = null;
        try {
            bigImg = ImageIO.read(new File("src/resources/mapping.png"));
        } catch (IOException ex) {}
        BufferedImage small = bigImg.getSubimage(X, Y, 20, 20);
        Image returnimg = SwingFXUtils.toFXImage(small, null);
        return returnimg;
    }
}
