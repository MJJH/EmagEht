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
    
    public Frame(Image o) {
        this.original = o;
        this.rotation = new HashMap<>();
    }
    
    public Map<Parts, Integer> getRotations() {
        return rotation;
    }
    
    public void repaint() {
        image = new WritableImage(original.getWidth(), original.getHeight());
        PixelWriter pw = image.getPixelWriter();
        PixelReader pr;
        
        for(Parts p : original.getParts().keySet()) {
            PartImage pi = original.getParts().get(p);
            
            
            
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

                double rad = Math.toRadians((rotation.get(p)+360)%360);
                int w = (int) Math.ceil(Math.abs(pi.image.getWidth() * Math.sin(rad)) + Math.abs(pi.image.getHeight() * Math.cos(rad)));
                int h = (int) Math.ceil(Math.abs(pi.image.getWidth() * Math.cos(rad)) + Math.abs(pi.image.getHeight() * Math.sin(rad)));
                
                BufferedImage bI = SwingFXUtils.fromFXImage(pi.image, null);
                BufferedImage sI = new BufferedImage(w, h, bI.getType());
                Graphics2D g = sI.createGraphics();
                g.rotate(rad, rx, ry);
                g.drawImage(bI, null, 0, 0);
                pi.image = SwingFXUtils.toFXImage(sI, null);
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
                                       
                    int paintX = x + pi.x;
                    int paintY = y + pi.y;
                    
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
