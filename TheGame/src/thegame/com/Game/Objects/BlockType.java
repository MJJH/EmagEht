package thegame.com.Game.Objects;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.time.Clock.system;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

/**
 * A class representing the type of block used
 *
 * @author Mark
 */
public enum BlockType {
    Dirt("Dirt", 5, 3, 0, 0),
    Sand("Sand", 5, 3, 20, 20),
    Stone("Stone", 25, 3, 0, 20),
    Coal("Coal", 30, 3, 0, 100),
    Copper("Copper", 40, 3, 0, 40),
    Tin("Tin", 40, 3, 20, 40),
    Iron("Iron", 60, 3, 0, 80),
    Obsidian("Obsidian", 120, 3, 20, 80);
    
    public final String name;
    public final int strength;
    public final int reqToolLvl;
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
    BlockType(String name, int strength, int reqLvl, float btx, float bty) {
        this.name = name;
        this.strength = strength;
        this.reqToolLvl = reqLvl;
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
