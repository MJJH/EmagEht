/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Martijn
 */
public class Frame {
    private Image original;
    private Map<Parts, Integer> rotation;
    private WritableImage image;
    private int offsetTop;
    private int offsetBot;
    private int offsetLeft;
    private int offsetRight;
    
    public Frame(Image o) {
        this.original = o;
        this.rotation = new HashMap<>();
    }
    
    public Map<Parts, Integer> getRotations() {
        return rotation;
    }
    
    public void repaint() {
        offsetTop = 0;
        offsetBot = 0;
        offsetLeft = 0;
        offsetRight = 0;
        
        image = new WritableImage(original.getWidth(), original.getHeight());
        PixelWriter pw = image.getPixelWriter();
        PixelReader pr;
        
        for(Parts p : original.getParts().keySet()) {
            PartImage pi = original.getParts().get(p);
            
            double w = 0;
            double h = 0;
            
            // Rotate!
            if(rotation.containsKey(p)){
                double rx = pi.image.getWidth() / 2;
                double ry = pi.image.getHeight() / 10;
                
                if(p.getType() != iTexture.Type.BODY)
                {
                    for(Parts tp : original.getParts().keySet()) {
                        if(tp.getType() == iTexture.Type.BODY && tp.getPart() == p.getPart()) {
                            PartImage tpi = original.getParts().get(tp);
                            rx = tpi.image.getWidth() / 2 + tpi.x - pi.x;
                            ry = tpi.image.getHeight() / 2 + tpi.y - pi.y;
                        }
                    }
                }

                double angle = (rotation.get(p)+360)%360;
                double rad = Math.toRadians(angle);
                
                w = rx + Math.abs((pi.image.getWidth() - rx) * Math.cos(rad)) + Math.abs((pi.image.getHeight() - ry) * Math.sin(rad));
                h = ry - Math.abs((pi.image.getWidth() - rx) * Math.sin(rad)) + Math.abs((pi.image.getHeight() - ry) * Math.cos(rad));
                
                double zeroX = rx + Math.abs((0 - rx) * Math.cos(rad)) + Math.abs((0 - ry) * Math.sin(rad));
                double zeroY = ry - Math.abs((0 - rx) * Math.sin(rad)) + Math.abs((0 - ry) * Math.cos(rad));
                
                if((angle < 90 && angle > 0) || (angle > 270 && angle < 360)) {
                    pi.x -= w - pi.image.getWidth();
                }
                if(angle < 180 && angle > 0) {
                    pi.y -= h - pi.image.getHeight();
                }
                
                BufferedImage bI = SwingFXUtils.fromFXImage(pi.image, null);
                BufferedImage sI = new BufferedImage((int) Math.ceil(w*2), (int) Math.ceil(h*2), bI.getType());
                Graphics2D g = sI.createGraphics();
                g.rotate(rad, rx, ry);
                int posX = (int) (w - zeroX);
                int posY = (int) (h - zeroY);
                g.drawImage(bI, null, posX, posY);
                pi.image = SwingFXUtils.toFXImage(sI, null);
            }
            
            boolean offsetChange = false;
            int changedLeft = 0;
            if(pi.x < 0) {
                changedLeft = Math.abs(pi.x);
                offsetLeft += Math.abs(pi.x);
                offsetChange = true;
            }
            if(pi.x + w > image.getWidth()) {
                offsetRight += pi.x + w - image.getWidth();
                offsetChange = true;
            }
            int changedTop = 0;
            if(pi.y < 0) {
                changedTop = Math.abs(pi.y);
                offsetTop += Math.abs(pi.y);
                offsetChange = true;
            }
            if(pi.y + h > image.getHeight()) {
                offsetBot += pi.y + h - image.getHeight();
                offsetChange = true;
            }
            
            if(offsetChange) {
                WritableImage i = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
                image = new WritableImage(offsetLeft + original.width + offsetRight, offsetTop + original.height + offsetBot);
                
                pw = image.getPixelWriter();
                PixelReader npr = i.getPixelReader();
                for(int y = 0; y < i.getHeight(); y++) {
                    for(int x = 0; x < i.getWidth(); x++) {
                        pw.setColor(x + changedLeft, y + changedTop, npr.getColor(x, y));
                    }
                }
            }
                
            
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

                                       
                    int paintX = x + pi.x + offsetLeft;
                    int paintY = y + pi.y + offsetTop;
                    
                    if(paintX < 0)
                        System.out.println("");
                    
                    if(pi.hFlip) {
                        paintX = (int) (image.getWidth() - paintX - 1);
                    }
                    if(pi.vFlip) {
                        paintY = (int) (image.getHeight() - paintY - 1);
                    }
                        
                    pw.setColor(paintX, paintY, c);
                }
            }
        }
    }
    
    public javafx.scene.image.Image show() {
        if(image == null)
            return original.show();
        else
            return image;
    }
    
}
