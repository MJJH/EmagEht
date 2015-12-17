/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.iTexture.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martijn
 */
public class Animation extends Skin {

    private Image image;
    private List<Frame> frames;

    private int current;

    private int speed;
    private int counter;

    public Animation(Image i, int speed)
    {
        this.image = i;
        this.height = i.getHeight();
        this.width = i.getWidth();
        this.frames = new ArrayList<>();
        this.speed = speed;
        counter = 0;
    }

    public void addFrame(Parts p, int angle)
    {
        Frame f = new Frame(image);
        f.getRotations().put(p, angle);
        f.repaint();
        frames.add(f);
    }

    public void addFrameByPart(Part p, int angle)
    {
        Frame f = new Frame(image);
        for (Parts pa : image.getParts().keySet())
        {
            if (pa.getPart() == p)
            {
                f.getRotations().put(pa, angle);
            }
        }
        f.repaint();
        frames.add(f);
    }

    @Override
    public javafx.scene.image.Image show()
    {
        if (frames.isEmpty())
        {
            return image.show();
        }

        counter++;
        if (counter % speed == 0)
        {
            current = (current + 1) % frames.size();
        }

        return frames.get(current).show();
    }

    public Frame getFrame()
    {
        if (frames == null || frames.size() >= current)
        {
            return null;
        }

        return frames.get(current);
    }
}
