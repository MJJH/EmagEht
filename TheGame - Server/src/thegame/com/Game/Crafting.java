/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import java.util.HashMap;
import thegame.com.Game.Objects.ObjectType;

/**
 *
 * @author Martijn
 */
public class Crafting {
    //WorkBench(ItemType.WorkBench, new HashMap<>(), 0, null);
    
    public final ObjectType crafting;
    public final HashMap<ObjectType, Integer> recources;
    public final int level;
    public final ObjectType near;
    
    Crafting(ObjectType object, HashMap<ObjectType, Integer> need, int level, ObjectType near)
    {
        this.crafting = object;
        this.recources = need;
        this.level = level;
        this.near = near;
    }

}
