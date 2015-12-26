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
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.Particle;
import thegame.com.Game.Objects.Tool;
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
        armor = new HashMap();
        
        sXDecay = 0.04f;
        sXIncrease = 0.075f;
        sYIncrease = 0.05f;
        sXMax = 0.3f;
        sYMax = 0.3f;
    }

    public void useTool(float x, float y) {
        if (holding instanceof Tool)
        {
            Tool h = (Tool) holding;
            if (System.currentTimeMillis() - used >= h.type.speed)
            {
                used = System.currentTimeMillis();
            }
        }
    }
    
}
