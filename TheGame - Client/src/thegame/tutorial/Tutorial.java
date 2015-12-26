/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.tutorial;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author Martijn
 */
public class Tutorial extends Map 
{
    public Tutorial(int width, int height) 
    {
        this.width = width;
        this.height = height;
        
        teamlifes = 4;
        gravity = .025f;

        objects = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        blocks = new Block[height][width];

        generateMap();
    }
    
    public void generateMap() 
    {
        for(int b = 0; b < 5; b++) {
            this.blocks[7-b][5] = new TutBlock(BlockType.Wood, 5, 7-b, this, 0);
        }
        
        for(int i = 0; i < 3; i++) {
            this.blocks[4-i][9] = new TutBlock(BlockType.Iron, 9, 4-i, this, 1);
        }
        
        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < this.blocks[0].length; x++)
            {
                BlockType t;
                switch(y) {
                    case 0: t = BlockType.Obsidian; break;
                    case 1: t = BlockType.Stone; break;
                    default: t = BlockType.Dirt; break;
                }
                this.blocks[y][x] = new TutBlock(t, x, y, this, 1);
            }
        }
        
        this.spawnX = 3;
        this.spawnY = 9;
    }
    
    public void setMe(Player me) {
        this.addMapObject(me);
        this.me = me;
    }
    
    @Override
    public void update()
    {
        //Movement
        me.update();

    }
}
