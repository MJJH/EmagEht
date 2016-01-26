/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
    private Map<CombineParts, PartImage> parts;
    private Color background;
    private Map<borderSide, Color> border;
    private int scale;
    private boolean flippedHorizontal;
    private boolean flippedVertical;

    private List<CombineParts> sortList() {
        List<CombineParts> cps = new ArrayList<>(parts.keySet());
        Collections.sort(cps);
        return cps;
    }
    public enum borderSide { TOP, LEFT, BOTTOM, RIGHT };
    
    public Image(int height, int width, Color background) {
        this.height = height;
        this.width = width;
        this.background = background;
        border = new HashMap<>();
        parts = new LinkedHashMap<>();
        
        repaint();
    }

    public Image(Sets texture) throws IOException
    {
        this.border = new HashMap<>();
        this.height = texture.getHeight();
        this.width = texture.getWidth();

        parts = new LinkedHashMap<>();

        BufferedImage bI;
        bI = ImageIO.read(new File(iTexture.path));
        WritableImage i;
        scale = 1;


        Sets s = texture;
        for (CombineParts cp : s.parts)
        {
            i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
            parts.put(cp, new PartImage(i, cp.x, cp.y));
        }

        repaint();

    }
    
    public CombineParts findCombineParts(Parts key){
        for(CombineParts cp : parts.keySet()) {
            if(cp.part == key) {
                return cp;
            }
        }
        return null;
    }
    
    public void setBackground(Color background) {
        this.background = background;
    }
    
    public Color getBackground() {
        return background;
    }
    
    public Map<borderSide, Color> getBorder() {
        return border;
    }

    public void addTexture(Sets texture) throws IOException
    {
        BufferedImage bI;
        bI = ImageIO.read(new File(iTexture.path));
        WritableImage i;

        if (texture instanceof Sets)
        {
            Sets s = (Sets) texture;
            for (CombineParts cp : s.parts)
            {
                Point p = calculateNewSize(cp.part);

                i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                
                PartImage ad = new PartImage(i, p.x, p.y);
                ad.hFlip = flippedHorizontal;
                ad.vFlip = flippedVertical;
                parts.put(cp, ad);
            }
        }

        repaint();

    }
    
    public void addTexture(Sets texture, Color[] colorset) throws IOException {
        BufferedImage bI;
        bI = ImageIO.read(new File(iTexture.path));
        WritableImage i;

        if (texture instanceof Sets)
        {
            Sets s = (Sets) texture;
            for (CombineParts cp : s.parts)
            {
                Point p = calculateNewSize(cp.part);

                i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                
                PartImage ad = new PartImage(i, p.x, p.y);
                ad.hFlip = flippedHorizontal;
                ad.vFlip = flippedVertical;
                parts.put(cp, ad);
                recolour(cp, colorset);
            }
        }

        repaint();
    }
    

    public Map<CombineParts, PartImage> getParts()
    {
        return parts;
    }

    public void recolour(Color[] colors)
    {
        for (PartImage pi : parts.values())
        {
            pi.recolors = colors;
        }
        repaint();
    }

    public void recolour(CombineParts p, Color[] colors)
    {
        if (parts.containsKey(p))
        {
            parts.get(p).recolors = colors;
        }
        repaint();
    }

    public void flipHorizontal()
    {
        for (PartImage pi : parts.values())
        {
            pi.hFlip = !pi.hFlip;
        }
        this.flippedHorizontal = !this.flippedHorizontal;
        repaint();
    }

    public void flipHorizontal(CombineParts p)
    {
        if (parts.containsValue(p))
        {
            parts.get(p).hFlip = !parts.get(p).hFlip;
        }
        repaint();
    }

    public void flipVertical()
    {
        for (PartImage pi : parts.values())
        {
            pi.vFlip = !pi.vFlip;
        }
        this.flippedVertical = !this.flippedVertical;
        repaint();
    }

    public void flipVorizontal(CombineParts p)
    {
        if (parts.containsValue(p))
        {
            parts.get(p).vFlip = !parts.get(p).vFlip;
        }
        repaint();
    }

    @Override
    public javafx.scene.image.Image show()
    {
        return image;
    }

    private void repaint()
    {
        
        image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();
        PixelReader pr;

        if(background != null && background != Color.TRANSPARENT && parts.isEmpty()) {
            for (int y = 0; y < this.height; y++)
            {
                for (int x = 0; x < this.width; x++)
                {
                    pw.setColor(x, y, background);
                }
            }
        }
        
        for (CombineParts cp : sortList())
        {
            PartImage pi = parts.get(cp);
            pr = pi.image.getPixelReader();

            for (int y = 0; y < pi.image.getHeight(); y++)
            {
                for (int x = 0; x < pi.image.getWidth(); x++)
                {
                    if(background != null && background != Color.TRANSPARENT && pi == parts.values().toArray()[0]) {
                        pw.setColor(x, y, background);
                    }
                    
                    Color c = pr.getColor(x, y);

                    if ((int) Math.round(c.getRed() * 255) / 32 == 7 || c.getOpacity() < 1)
                    {
                        continue;
                    }

                    int r = (int) Math.round((c.getRed() * 255) / 32);

                    if (pi.recolors.length > r && pi.recolors[r] != null)
                    {
                        c = pi.recolors[r];
                    }

                    int paintX = (x + pi.x) * scale;
                    int paintY = (y + pi.y) * scale;

                    if (pi.hFlip)
                    {
                        paintX = width - paintX - 1;
                    }
                    if (pi.vFlip)
                    {
                        paintY = height - paintY - 1;
                    }
                    
                    for(int i = 0; i < scale; i++)
                    {
                        for(int nee = 0; nee < scale; nee++)
                        {
                            pw.setColor(paintX + nee, paintY + i, c);
                        }
                    }
                }
            }
        }
        
        for(borderSide bs : borderSide.values()) {
            switch(bs) {
                case TOP:
                    if(border.get(borderSide.TOP) == null || border.get(borderSide.TOP) == Color.TRANSPARENT)
                        break;
                    
                    for(int y = 0; y < 2; y++) {
                        for(int x = 0; x < this.width; x++) {
                            pw.setColor(x, y, border.get(borderSide.TOP));
                        }
                    }
                    break;
                case BOTTOM:
                    if(border.get(borderSide.BOTTOM) == null || border.get(borderSide.BOTTOM) == Color.TRANSPARENT)
                        break;
                    
                    for(int y = 0; y < 2; y++) {
                        for(int x = 0; x < this.width; x++) {
                            pw.setColor(x, height - 1 - y, border.get(borderSide.BOTTOM));
                        }
                    }
                    break;
                case LEFT:
                    if(border.get(borderSide.LEFT) == null || border.get(borderSide.LEFT) == Color.TRANSPARENT)
                        break;
                    for(int x = 0; x < 2; x++) {
                        for(int y = 0; y < this.height; y++) {
                            pw.setColor(x, y, border.get(borderSide.LEFT));
                        }
                    }
                    break;
                case RIGHT:
                    if(border.get(borderSide.RIGHT) == null || border.get(borderSide.RIGHT) == Color.TRANSPARENT)
                        break;
                    for(int x = 0; x < 2; x++) {
                        for(int y = 0; y < this.height; y++) {
                            pw.setColor(width - 1 - x, y, border.get(borderSide.RIGHT));
                        }
                    }
                    break;
            }
        }
    }
    
    public void addBorder(borderSide side, Color c, int size) {
        border.put(side, c);
        PixelWriter pw = image.getPixelWriter();
        switch(side) {
            case TOP:
                if(border.get(borderSide.TOP) == null || border.get(borderSide.TOP) == Color.TRANSPARENT)
                    break;

                for(int y = 0; y < size; y++) {
                    for(int x = 0; x < this.width; x++) {
                        pw.setColor(x, y, border.get(borderSide.TOP));
                    }
                }
                break;
            case BOTTOM:
                if(border.get(borderSide.BOTTOM) == null || border.get(borderSide.BOTTOM) == Color.TRANSPARENT)
                    break;

                for(int y = 0; y < 2; y++) {
                    for(int x = 0; x < this.width; x++) {
                        pw.setColor(x, height - 1 - y, border.get(borderSide.BOTTOM));
                    }
                }
                break;
            case LEFT:
                if(border.get(borderSide.LEFT) == null || border.get(borderSide.LEFT) == Color.TRANSPARENT)
                    break;
                for(int x = 0; x < 2; x++) {
                    for(int y = 0; y < this.height; y++) {
                        pw.setColor(x, y, border.get(borderSide.LEFT));
                    }
                }
                break;
            case RIGHT:
                if(border.get(borderSide.RIGHT) == null || border.get(borderSide.RIGHT) == Color.TRANSPARENT)
                    break;
                for(int x = 0; x < 2; x++) {
                    for(int y = 0; y < this.height; y++) {
                        pw.setColor(width - 1 - x, y, border.get(borderSide.RIGHT));
                    }
                }
                break;
            }
    }

    private CombineParts getParent(Parts t)
    {
        if (!t.isBody())
        {
            for (CombineParts cp : parts.keySet())
            {
                Parts p = cp.part;
                if (p.getPart() == t.getPart() && p.isBody())
                {
                    return cp;
                }
            }
        }
        return null;
    }

    private Point calculateNewSize(Parts t)
    {
        CombineParts parent = getParent(t);
        PartImage par = parts.get(parent);
        int x = 0;
        int y = 0;
        int val;
        
        if(par != null) {
            x = par.x - ( parent.part.getConnectX() - t.getConnectX() );
            y = par.y - ( parent.part.getConnectY() - t.getConnectY() );

            if(flippedHorizontal)
                x = par.x + ( parent.part.getConnectX() + t.getConnectX() );
            if(flippedVertical)
                y = par.y + ( parent.part.getConnectY() + t.getConnectY() );
        } else {
            x = t.getConnectX();
            y = t.getConnectY();
        }
        if(x < 0)
        {
            val = Math.abs(x);
            width += val;
            x += val;
            for(PartImage p : parts.values())
            {
                p.x += val;
            }
            offsetLeft = val;
        }
        if((x + t.getWidth() > width)) 
        {
            offsetRight = x + t.getWidth() - width;
            width = x + t.getWidth();                  
        }
        if(y < 0)
        {
            val = Math.abs(y);
            height += val;
            y += val;
            for(PartImage p : parts.values())
            {
                p.y += val;
            }
            offsetTop = val;
        }
        if(y + t.getHeight() > height)
        {
            offsetBottom = y + t.getHeight() - height;
            height = y + t.getHeight();
        }
        return new Point(x, y);
    }

    public void removeTexture(Sets it)
    {
        if(it instanceof Sets)
        {
            for(CombineParts p : ((Sets) it).parts)
                parts.remove(p);
        } 
        repaint();
    }
    
    public void Scale(int Scale)
    {
        scale = Scale;
        height = height * scale;
        width = width * scale;
    }
}
