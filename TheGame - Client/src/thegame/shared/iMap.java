/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.util.List;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author Martijn
 */
public interface iMap {
    
    public List<MapObject> getObjects(int startX, int startY, int endX, int endY);
    
    public int getHeight();
    
    public int getWidth();
    
    public Map getMap();
    
    public Block[][] getBlocks();
}
