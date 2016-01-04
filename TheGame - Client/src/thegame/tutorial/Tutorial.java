/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.tutorial;

import display.IntColor;
import display.Parts;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.ToolType;

/**
 *
 * @author Martijn
 */
public class Tutorial extends Map 
{
    private steps step = steps.WOOD;
    enum steps { WOOD, WORKBENCH, STONE, PICKAXE, IRON, FURNACE, SWORD, ENEMY, READY }
    
    public Tutorial(int width, int height) 
    {
        this.width = width;
        this.height = height;

        teamlifes = 1;
        gravity = .025f;

        objects = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        blocks = new Block[height][width];

        generateMap();
    }
    
    public steps nextStep()
    {
        if(step != steps.READY)
            return step = steps.values()[step.ordinal()+1];
        
        return step;
    }
    
    public steps getStep()
    {
        return step;
    }
    
    public void generateMap() 
    {
        new BlockType("Wood", 1, 0, ToolType.toolType.AXE, 0, null, null);
        new BlockType("Iron", 60, 3, ToolType.toolType.PICKAXE, 1, Parts.Ore, new Color[]
        {
            null,
            IntColor.rgb(87, 87, 97),
            IntColor.rgb(120, 110, 130),
            IntColor.rgb(169, 159, 169),
            IntColor.rgb(130, 130, 180)
        });
        new BlockType("Dirt", 5, 3, ToolType.toolType.SHOVEL, 1, Parts.Block, new Color[]
        {
            null,
            IntColor.rgb(68, 46, 32),
            IntColor.rgb(108, 72, 51),
            IntColor.rgb(158, 106, 75)
        });
        new BlockType("Stone", 25, 3, ToolType.toolType.PICKAXE, 1, Parts.Block, new Color[]
        {
            null,
            IntColor.rgb(87, 87, 87),
            IntColor.rgb(110, 110, 110),
            IntColor.rgb(159, 159, 159)
        });
        new BlockType("Obsidian", 120, 3, ToolType.toolType.PICKAXE, 1, Parts.Block, new Color[]
        {
            null,
            IntColor.rgb(12, 0, 57),
            IntColor.rgb(18, 10, 60),
            IntColor.rgb(18, 10, 80)
        });
        
        
        for(int b = 0; b < 5; b++) {
            this.blocks[7-b][5] = new TutBlock(BlockType.blocktypes.get("Wood"), 5, 7-b, this, 0);
        }
        
        for(int i = 0; i < 3; i++) {
            this.blocks[4-i][9] = new TutBlock(BlockType.blocktypes.get("Iron"), 9, 4-i, this, 1);
        }
        
        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < this.blocks[0].length; x++)
            {
                BlockType t;
                switch(y) {
                    case 0: t = BlockType.blocktypes.get("Obsidian"); break;
                    case 1: t = BlockType.blocktypes.get("Stone"); break;
                    default: t = BlockType.blocktypes.get("Dirt"); break;
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
