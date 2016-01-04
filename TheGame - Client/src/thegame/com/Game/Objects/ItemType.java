/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Martijn
 */
public class ItemType extends ObjectType {
    private static final long serialVersionUID = 6522685098267706690L;
    public static Map<String, ItemType> itemtypes = new HashMap<>();
    
    public final int width;
    public final int height;
    
    public ItemType(String name, int width, int height)
    {
        this.name = name;
        this.width = width;
        this.height = height;
        
        itemtypes.put(name, this);
    }

    @Override
    protected void createSkin() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
