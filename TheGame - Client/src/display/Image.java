/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.iTexture.Type;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private Map<Parts, PartImage> parts;
    
    public Image(iTexture texture) throws IOException {
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        
        parts = new LinkedHashMap<>();
        
        BufferedImage bI;
        bI = ImageIO.read(new File(iTexture.path));
        WritableImage i;
        
        if(texture instanceof Parts) {
            Parts t = (Parts) texture;
            i = SwingFXUtils.toFXImage(bI.getSubimage(t.getX(), t.getY(), t.getWidth(), t.getHeight()), null);
            parts.put(t, new PartImage(i, 0, 0));
        } else if(texture instanceof Sets) {
            Sets s = (Sets) texture;
            for(CombineParts cp : s.parts) {
                i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                parts.put(cp.part, new PartImage(i, cp.x, cp.y));
            }
        }
            
        repaint();
    }
    
    public void addTexture(iTexture texture) throws IOException {
        BufferedImage bI;
        bI = ImageIO.read(new File(iTexture.path));
        WritableImage i;
        
        if(texture instanceof Parts) {
            Parts t = (Parts) texture;
            
            calculateNewSize(t);
            Parts parent = getParent(t);
            
            i = SwingFXUtils.toFXImage(bI.getSubimage(t.getX(), t.getY(), t.getWidth(), t.getHeight()), null);
            parts.put(t, new PartImage(i, parent.getConnectX() - t.getConnectX(), parent.getConnectY() - t.getConnectY()));
        } else if(texture instanceof Sets) {
            Sets s = (Sets) texture;
            for(CombineParts cp : s.parts) {
                
                calculateNewSize(cp.part);
                Parts parent = getParent(cp.part);
                
                i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                parts.put(cp.part, new PartImage(i, parent.getConnectX() - cp.part.getConnectX(), parent.getConnectY() - cp.part.getConnectY()));
            }
        }
            
        repaint();
        
      
    }
    
    public Map<Parts, PartImage> getParts() {
        return parts;
    }
    
    public void recolour(Color[] colors) {
        for(PartImage pi : parts.values()) {
            pi.recolors = colors;
        }
        repaint();
    }
    
    public void recolour(Parts p, Color[] colors) {
        if(parts.containsValue(p))
            parts.get(p).recolors = colors;
        repaint();
    }
    
    public void flipHorizontal() {
        for(PartImage pi : parts.values()) {
            pi.hFlip = !pi.hFlip;
        }
        repaint();
    }
    
    public void flipHorizontal(Parts p) {
        if(parts.containsValue(p))
            parts.get(p).hFlip = !parts.get(p).hFlip;
        repaint();
    }
    
    public void flipVertical() {
        for(PartImage pi : parts.values()) {
            pi.vFlip = !pi.vFlip;
        }
        repaint();
    }
    
    public void flipVorizontal(Parts p) {
        if(parts.containsValue(p))
            parts.get(p).vFlip = !parts.get(p).vFlip;
        repaint();
    }
    
    
    @Override
    public javafx.scene.image.Image show() {
        return image;
        
    }
    
    private void repaint() 
    {
        image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();
        PixelReader pr;
        
        for(PartImage pi : parts.values()) {
            pr = pi.image.getPixelReader();
            
            for(int y = 0; y < pi.image.getHeight(); y++) {
                for(int x = 0; x < pi.image.getWidth(); x++) {
                    Color c = pr.getColor(x, y);
                    
                    if((int) Math.round(c.getRed()* 255) / 32 == 7 || c.getOpacity() < 1)
                        continue;
                    
                    int r = (int) Math.round((c.getRed()* 255) / 32);
                    
                    if(pi.recolors.length > r && pi.recolors[r] != null) {
                       c = pi.recolors[r];
                    }
                    
                    int paintX = x + pi.x;
                    int paintY = y + pi.y;
                    
                    if(pi.hFlip) {
                        paintX = width - paintX - 1;
                    }
                    if(pi.vFlip) {
                        paintY = height - paintY - 1;
                    }
                    
                    
                    pw.setColor(paintX, paintY, c);
                }
            }
        }
    }
    
    private Parts getParent(Parts t) {
        if(t.getType() != Type.BODY) {
            for(Parts p : parts.keySet()) {
                if(p.getPart() == t.getPart() && p.getType() == Type.BODY) {
                    return p;
                }
            }
        }
        return null;
    }

    private void calculateNewSize(Parts t) {
        Parts parent = getParent(t);
        
        int difTop = 0, difBot = 0, difLeft = 0, difRight = 0;
        if(parent != null) {
            if(parent.getConnectY() - t.getConnectY() < 0) {
                difTop = parent.getConnectY() - t.getConnectY();
            }
            if(parent.getConnectY() + (t.getHeight() - t.getConnectY()) > height) {
                difBot = parent.getConnectY() + (t.getHeight() - t.getConnectY());
            }
            if(parent.getConnectX() - t.getConnectX() < 0) {
                difLeft = parent.getConnectX() - t.getConnectX();
            }
            if(parent.getConnectX() + (t.getWidth() - t.getConnectX()) > width) {
                difRight = parent.getConnectX() + (t.getWidth() - t.getConnectX());
            }
            
            for(PartImage p : parts.values()) {
                p.x += difLeft;
                p.y += difTop;
            }
            
            height += difTop + difBot;
            width += difLeft + difRight;
        }
    }
    
}
