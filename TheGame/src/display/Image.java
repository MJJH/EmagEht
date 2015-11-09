/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Martijn
 */
public class Image extends Skin {

    private WritableImage image;
    private int height;
    private int width;
    private Map<String, javafx.scene.image.Image> parts;
    
    public Image(int height, int width) {
        this.height = height;
        this.width = width;
        
        parts = new HashMap<>();
    }
    
    public Image(int height, int width, String path) {
        this.height = height;
        this.width = width;
        
        parts = new HashMap<>();
        
        javafx.scene.image.Image i = new javafx.scene.image.Image(path);
        
        parts.put("background", i);
        
        this.image = new WritableImage(i.getPixelReader(), width, height);
    }
    
    public Image(int height, int width, String path, int ix, int iy, int iwidth, int iheight) throws IOException {
        this.height = height;
        this.width = width;
        
        parts = new HashMap<>();
        
        BufferedImage bI;
        bI = ImageIO.read(new File(path));
        
        javafx.scene.image.Image i = SwingFXUtils.toFXImage(bI.getSubimage(ix, iy, iwidth, iheight), null);
        
        parts.put("background", i);
        
        this.image = new WritableImage(i.getPixelReader(), width, height);
    }
    
    public Image(int height, int width, String path, int ix, int iy, int iwidth, int iheight, int px, int py) throws IOException {
        this.height = height;
        this.width = width;
        
        parts = new HashMap<>();
        
        BufferedImage bI;
        bI = ImageIO.read(new File(path));
        
        javafx.scene.image.Image i = SwingFXUtils.toFXImage(bI.getSubimage(ix, iy, iwidth, iheight), null);
        
        parts.put("background", i);
        this.image = new WritableImage(i.getPixelReader(), px, py, width, height);
    }
    
    
    @Override
    public javafx.scene.image.Image show() {
        return image;
        
    }
    
}
