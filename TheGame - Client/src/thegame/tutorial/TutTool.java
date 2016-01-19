/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.tutorial;

import thegame.com.Game.Objects.Tool;
import thegame.com.Game.Objects.ToolType;

/**
 *
 * @author Martijn
 */
public class TutTool extends Tool {
    public TutTool(ToolType t, Tutorial w) {
        this.type = t;
        
        this.playing = w;
        this.setX(0);
        this.setY(0);
        this.setH(0);
        this.setW(0);
        this.setS(1);
        this.placeable = false;
        this.stackable = false;
    }
}
