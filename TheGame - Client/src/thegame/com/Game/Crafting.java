/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import thegame.com.Game.Objects.ObjectType;

/**
 *
 * @author Martijn
 */
public class Crafting implements Serializable {

    private static final long serialVersionUID = 6522685098267704690L;

    public static List<Crafting> recipes = new ArrayList<>();

    public final ObjectType crafting;
    public final HashMap<ObjectType, Integer> recources;
    public final int level;
    public final ObjectType near;

    public Crafting(ObjectType object, HashMap<ObjectType, Integer> need, int level, ObjectType near)
    {
        this.crafting = object;
        this.recources = need;
        this.level = level;
        this.near = near;

        recipes.add(this);
    }

}
