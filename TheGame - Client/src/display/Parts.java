/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.util.HashMap;
import java.util.Map;
import thegame.com.Game.Objects.ItemType;

/**
 *
 * @author Martijn
 */
public class Parts implements iTexture {

    private final Part part;
    private final boolean body;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int connectX;
    private final int connectY;
    
    public static Map<String, Parts> parts = new HashMap<>();

    public Parts(String name, Part part, boolean body, int x, int y, int width, int height, int connectX, int connectY)
    {
        this.part = part;
        this.body = body;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.connectX = connectX;
        this.connectY = connectY;
        
        parts.put(name, this);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
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

    public Part getPart()
    {
        return part;
    }

    public boolean isBody()
    {
        return body;
    }

    int getConnectY()
    {
        return connectY;
    }

    int getConnectX()
    {
        return connectX;
    }
}
