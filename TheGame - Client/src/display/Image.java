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
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 *
 * @author Martijn
 */
public class Image extends Skin {

    private WritableImage image;
    private Map<String, javafx.scene.image.Image> parts;
    
    public Image(int height, int width) {
        this.height = height;
        this.width = width;
        
        parts = new HashMap<>();
        this.image = new WritableImage(width, height);
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
    
    public void addPart(String name, String path, int ix, int iy, int iwidth, int iheight, int px, int py, boolean transparant) throws IOException {
        if(px + iwidth > width || py + iheight > height)
            throw new IllegalArgumentException("Part '"+name+"' does not fit in Image");
        
        BufferedImage bI;
        bI = ImageIO.read(new File(path));
        
        javafx.scene.image.Image i = SwingFXUtils.toFXImage(bI.getSubimage(ix, iy, iwidth, iheight), null);
        
        PixelWriter edit = image.getPixelWriter();
        PixelReader read = i.getPixelReader();
        
        for(int y = 0; y < iheight; y++) {
            for(int x = 0; x < iwidth; x++) {
                Color c = read.getColor(x, y);
                
                if(transparant && (int) Math.round(c.getRed()* 255) / 32 == 7)
                    continue;
              
                
                edit.setColor(px + x, py + y, c);
            }
        }
        
        parts.put(name, i);
    }
    
    public void recolour(Color[] colors) {
        PixelWriter edit = image.getPixelWriter();
        PixelReader read = image.getPixelReader();
        
        if(colors == null) return;
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(read.getColor(x, y).getOpacity() < 1)
                    continue;
                
                int r = (int) Math.round((read.getColor(x, y).getRed()* 255) / 32);
                if(colors.length > r && colors[r] != null) {
                   edit.setColor(x, y, colors[r]);
                }
            }
        }
    }
    
    public void flipHorizontal() {
        PixelWriter edit = image.getPixelWriter();
        PixelReader read = image.getPixelReader();
        
        
        WritableImage writableImage 
                = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
         
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = read.getColor(x, y);
                pixelWriter.setColor(width - x - 1, y, color);
            }
        }
        
        image = writableImage;
    }
    
    
    @Override
    public javafx.scene.image.Image show() {
        return image;
        
    }
    
}
