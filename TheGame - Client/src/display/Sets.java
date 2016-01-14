/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martijn
 */
public class Sets implements iTexture {

    public static Map<String, Sets> sets = new HashMap<>();
    
    private final String name;
    private final int height;
    private final int width;
    public final List<CombineParts> parts;

    public Sets(String name, List<CombineParts> parts)
    {
        this.parts = parts;
        int h = 0;
        int w = 0;
        for (CombineParts cp : parts)
        {
            if (cp.x + cp.part.getWidth() > w)
            {
                w = cp.x + cp.part.getWidth();
            }
            if (cp.y + cp.part.getHeight() > h)
            {
                h = cp.y + cp.part.getHeight();
            }
        }
        this.name = name;
        this.height = h;
        this.width = w;
        this.sets.put(name, this);
        
        
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }
}
