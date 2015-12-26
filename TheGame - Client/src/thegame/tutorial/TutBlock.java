/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.tutorial;

import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;

/**
 *
 * @author Martijn
 */
public class TutBlock extends Block {
    
    public TutBlock(BlockType type, float x, float y, Tutorial t, float solid) {
        this.type = type;
        this.xPosition = x;
        this.yPosition = y;
        this.solid = solid;
        this.width = 1;
        this.height = 1;
        this.playing = t;
    }
}
