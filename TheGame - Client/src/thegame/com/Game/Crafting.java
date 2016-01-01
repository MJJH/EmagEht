/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import java.util.HashMap;
import thegame.com.Game.Objects.Item;
import thegame.com.Game.Objects.ItemType;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.iObjectType;

/**
 *
 * @author Martijn
 */
public enum Crafting {
    WorkBench(ItemType.WorkBench, new HashMap<>(), 0, null);
    
    public final iObjectType crafting;
    public final HashMap<iObjectType, Integer> recources;
    public final int level;
    public final iObjectType near;
    
    Crafting(iObjectType object, HashMap<iObjectType, Integer> need, int level, iObjectType near)
    {
        this.crafting = object;
        this.recources = need;
        this.level = level;
        this.near = near;
    }

}
