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
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
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

    private int getal = 1;
    private WritableImage image;
    private Map<Parts, PartImage> parts;
    private Color background;
    private Map<borderSide, Color> border;
    public enum borderSide { TOP, LEFT, BOTTOM, RIGHT };
    
    public Image(Image i) {
        image = (WritableImage) i.show();
        parts = i.getParts();
        width = i.getWidth();
        height = i.getHeight();
        border = new HashMap<>();
        background = i.getBackground();
        
        repaint();
    }
    
    public Image(int height, int width, Color background) {
        this.height = height;
        this.width = width;
        this.background = background;
        border = new HashMap<>();
        parts = new LinkedHashMap<>();
        
        repaint();
    }

    public Image(iTexture texture) throws IOException
    {
        this.border = new HashMap<>();
        this.height = texture.getHeight();
        this.width = texture.getWidth();

        parts = new LinkedHashMap<>();

        BufferedImage bI;
        bI = ImageIO.read(new File(iTexture.path));
        WritableImage i;

        if (texture instanceof Parts)
        {
            Parts t = (Parts) texture;
            i = SwingFXUtils.toFXImage(bI.getSubimage(t.getX(), t.getY(), t.getWidth(), t.getHeight()), null);
            parts.put(t, new PartImage(i, 0, 0));
            getal++;
        } else if (texture instanceof Sets)
        {

            Sets s = (Sets) texture;
            for (CombineParts cp : s.parts)
            {

                if (cp.part.equals(Parts.playerFrontLeg))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 2, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                }
                if (cp.part.equals(Parts.playerBackLeg))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 1, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                }
                if (cp.part.equals(Parts.playerTorso))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 0, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                }

                if (cp.part.equals(Parts.playerBackArm))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 8, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                } else if (cp.part.equals(Parts.playerFrontArm))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 9, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                } else
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, getal, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                }

            }
        }

        repaint();

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

    public void addTexture(iTexture texture) throws IOException
    {
        BufferedImage bI;
        bI = ImageIO.read(new File(iTexture.path));
        WritableImage i;

        if (texture instanceof Parts)
        {
            Parts t = (Parts) texture;

            calculateNewSize(t);
            Parts parent = getParent(t);

            if (texture.equals(Parts.Sword))
            {
                i = SwingFXUtils.toFXImage(bI.getSubimage(t.getX(), t.getY(), t.getWidth(), t.getHeight()), null);
                add((LinkedHashMap<Parts, PartImage>) parts, 7, t, new PartImage(i, parent.getConnectX() - t.getConnectX() + parts.get(parent).x, parent.getConnectY() - t.getConnectY() + parts.get(parent).y));
                getal++;
            } 
            else
            {
                i = SwingFXUtils.toFXImage(bI.getSubimage(t.getX(), t.getY(), t.getWidth(), t.getHeight()), null);
                parts.put(t, new PartImage(i, parent.getConnectX() - t.getConnectX() + parts.get(parent).x, parent.getConnectY() - t.getConnectY() + parts.get(parent).y));
                getal++;

            }

        } else if (texture instanceof Sets)
        {
            Sets s = (Sets) texture;
            for (CombineParts cp : s.parts)
            {

                calculateNewSize(cp.part);
                Parts parent = getParent(cp.part);

                if (cp.part.equals(Parts.armorFront))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 4, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                }
                if (cp.part.equals(Parts.armorBack))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 5, cp.part, new PartImage(i, cp.x, cp.y));
                    getal++;
                }
                if (cp.part.equals(Parts.armorBody))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 3, cp.part, new PartImage(i, parent.getConnectX() - cp.part.getConnectX() + parts.get(parent).x, parent.getConnectY() - cp.part.getConnectY() + parts.get(parent).y));
                    getal++;
                } else if (cp.part.equals(Parts.armorShoulderBack))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 10, cp.part, new PartImage(i, parent.getConnectX() - cp.part.getConnectX() + parts.get(parent).x, parent.getConnectY() - cp.part.getConnectY() + parts.get(parent).y));
                    getal++;
                } else if (cp.part.equals(Parts.armorShoulderFront))
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    add((LinkedHashMap<Parts, PartImage>) parts, 6, cp.part, new PartImage(i, parent.getConnectX() - cp.part.getConnectX() + parts.get(parent).x, parent.getConnectY() - cp.part.getConnectY() + parts.get(parent).y));
                    getal++;
                } else
                {
                    i = SwingFXUtils.toFXImage(bI.getSubimage(cp.part.getX(), cp.part.getY(), cp.part.getWidth(), cp.part.getHeight()), null);
                    parts.put(cp.part, new PartImage(i, parent.getConnectX() - cp.part.getConnectX() + parts.get(parent).x, parent.getConnectY() - cp.part.getConnectY() + parts.get(parent).y));
                    getal++;
                }

            }
        }

        repaint();

    }

    public Map<Parts, PartImage> getParts()
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

    public void recolour(Parts p, Color[] colors)
    {
        if (parts.containsValue(p))
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
        repaint();
    }

    public void flipHorizontal(Parts p)
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
        repaint();
    }

    public void flipVorizontal(Parts p)
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
        
        for (PartImage pi : parts.values())
        {
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

                    int paintX = x + pi.x;
                    int paintY = y + pi.y;

                    if (pi.hFlip)
                    {
                        paintX = width - paintX - 1;
                    }
                    if (pi.vFlip)
                    {
                        paintY = height - paintY - 1;
                    }

                    pw.setColor(paintX, paintY, c);
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

    private Parts getParent(Parts t)
    {
        if (t.getType() != Type.BODY)
        {
            for (Parts p : parts.keySet())
            {
                if (p.getPart() == t.getPart() && p.getType() == Type.BODY)
                {
                    return p;
                }
            }
        }
        return null;
    }

    private void calculateNewSize(Parts t)
    {
        Parts parent = getParent(t);

        int difTop = 0, difBot = 0, difLeft = 0, difRight = 0;
        if (parent != null)
        {
            if (parent.getConnectY() - t.getConnectY() < 0)
            {
                difTop = Math.abs(parent.getConnectY() - t.getConnectY());
            }
            if (parent.getY() + parent.getConnectY() + (t.getHeight() - t.getConnectY()) > height)
            {
                difBot = Math.abs(parent.getY() + parent.getConnectY() + (t.getHeight() - t.getConnectY()));
            }
            if (parent.getConnectX() - t.getConnectX() < 0)
            {
                difLeft = Math.abs(parent.getConnectX() - t.getConnectX());
            }
            if (parent.getX() + parent.getConnectX() + (t.getWidth() - t.getConnectX()) < width)
            {
                difRight = Math.abs(parent.getX() + parent.getConnectX() + (t.getWidth() - t.getConnectX()));
            }

            for (PartImage p : parts.values())
            {
                p.x += difLeft;
                p.y += difTop;
            }

            height += difTop + difBot;
            width += difLeft + difRight;
        }
    }

    public static void add(LinkedHashMap<Parts, PartImage> map, int index, Parts key, PartImage value)
    {
        assert (map != null);
        assert !map.containsKey(key);
        assert (index >= 0) && (index < map.size());

        int i = 0;
        List<Map.Entry<Parts, PartImage>> rest = new ArrayList<Map.Entry<Parts, PartImage>>();
        for (Map.Entry<Parts, PartImage> entry : map.entrySet())
        {
            if (i++ >= index)
            {
                rest.add(entry);
            }
        }
        map.put(key, value);
        for (int j = 0; j < rest.size(); j++)
        {
            Map.Entry<Parts, PartImage> entry = rest.get(j);
            map.remove(entry.getKey());
            map.put(entry.getKey(), entry.getValue());
        }
    }

    public void removeTexture(iTexture it)
    {
        parts.remove(it);
        repaint();
    }
}
