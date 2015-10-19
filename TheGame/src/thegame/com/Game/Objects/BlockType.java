package thegame.com.Game.Objects;

import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
/**
 * A class representing the type of block used
 * @author Mark
 */
public class BlockType {

    private final String name;
    private final int strength;
    private final int reqToolLvl;
    final Image skin;
    private float btx;
    private float bty;
    /**
     * Initiates an instance of this class with the following attributes
     * @param name The name of the BlockType 
     * @param strength The strength of the Armortype
     * @param reqLvl The required level of the BlockType
     * @param btx
     * @param skin The look of the Armortype
     * @param bty
     * @throws java.io.IOException
     */
    public BlockType(String name, int strength, int reqLvl, float btx, float bty) throws IOException
    {
     this.name = name;
     this.strength = 1;
     this.reqToolLvl = 1;
     this.btx = btx;
     this.bty = bty;
     this.skin = getskin();
    }
    
    public Image getskin() throws IOException
    {
    int X = Float.floatToIntBits(btx);
    int Y = Float.floatToIntBits(bty);
    
    BufferedImage bigImg = ImageIO.read(new File("/resources/mapping.png"));
    BufferedImage small = bigImg.getSubimage(X,Y,20,20);
    Image returnimg =  SwingFXUtils.toFXImage(small,null);
    return returnimg;
    }
}
