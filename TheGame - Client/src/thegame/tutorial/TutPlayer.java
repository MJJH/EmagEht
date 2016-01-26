/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.tutorial;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.Particle;
import thegame.com.Game.Objects.Tool;
import thegame.com.Game.Objects.ToolType;
import thegame.engine.Collision;
import thegame.engine.Movement;
import thegame.engine.Physics;

/**
 *
 * @author Martijn
 */
public class TutPlayer extends Player {
    public TutPlayer(float x, float y, Tutorial t) {
        this.xPosition = x;
        this.yPosition = y;
        this.solid = 1;
        this.width = 1;
        this.height = 2;
        this.playing = t;
        this.hp = 100;
        this.maxHP = 100;
        
        
        backpack = new ArrayList[30];
        addToBackpack(new TutTool(ToolType.tooltypes.get("Flint"), t));
        this.addToBackpack(new TutArmor(ArmorType.armortypes.get("Wooden Helmet"), t));
        this.addToBackpack(new TutArmor(ArmorType.armortypes.get("Stone Helmet"), t));
        this.addToBackpack(new TutArmor(ArmorType.armortypes.get("Steel Helmet"), t));
        this.addToBackpack(new TutArmor(ArmorType.armortypes.get("Wooden Shield"), t));
        this.addToBackpack(new TutArmor(ArmorType.armortypes.get("Wooden Greaves"), t));
        this.addToBackpack(new TutArmor(ArmorType.armortypes.get("Wooden Chestplate"), t));
        
        armor = new HashMap();
        
        sXDecay = 0.04f;
        sXIncrease = 0.075f;
        sYIncrease = 0.05f;
        sXMax = 0.3f;
        sYMax = 0.3f;
    }
    
    public boolean addToBackpack(MapObject object)
    {
        int spot = -1;

        for (int i = 0; i < backpack.length; i++)
        {
            List<MapObject> l = backpack[i];
            if (l == null)
            {
                continue;
            }

            if (object instanceof Tool || object instanceof Armor)
            {
                continue;
            }

            if (!l.isEmpty() && l.get(0).getClass().equals(object.getClass()) && l.size() < 99)
            {
                if (l.get(0) instanceof Block && object instanceof Block)
                {
                    Block b = (Block) l.get(0);
                    Block b2 = (Block) object;
                    if (!b.getType().getName().equals(b2.getType().getName()))
                    {
                        continue;
                    }
                }

                spot = i;
            }
        }

        if (spot > -1)
        {
            return addToBackpack(object, spot);
        } else
        {
            return addToEmptyBackpack(object);
        }
    }
    
    public boolean addToEmptyBackpack(MapObject object)
    {
        for (int i = 0; i < backpack.length; i++)
        {
            if (backpack[i] == null || backpack[i].isEmpty())
            {
                backpack[i] = new ArrayList<>();
                backpack[i].add(object);
                return true;
            }
        }
        return false;
    }

    public void useTool(float x, float y) {
        if(holding == null || holding.isEmpty())
            return;
                    
        if (holding.get(0) instanceof Tool)
        {
            Tool h = (Tool) holding.get(0);
            if (System.currentTimeMillis() - used >= h.type.speed)
            {
                used = System.currentTimeMillis();
            }
        }
    }
    
}
