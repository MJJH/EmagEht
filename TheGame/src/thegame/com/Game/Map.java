package thegame.com.Game;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;

/**
 * The class for the map of the game.
 * @author Mark
 */
public class Map {

    private int id;
    private int height;
    private int width;
    private int teamlifes;
    private int time;
    private Array[] seasons;
    private int level;
    private int spawnX;
    private int spawnY;
    
    private Block[][] blocks;

    /**
     * Creates a new instance of the map with height,width, spawnX and spawnY.
     */
    public Map()
    {
        generateMap();
    }
    
    /**
     *
     */
    public void generateMap()
    {
        width = 300;
        height = 100;
        
        this.spawnX = 0;
        this.spawnY = 4;
        
        blocks = new Block[100][300];
        
        BlockType dirt;
        BlockType stone;
        try {
            dirt = new BlockType("dirt", 1, 1, 0, 0);
            stone = new BlockType("stone", 1, 1, 0, 20);
        
            for(int x = 0; x < 5; x++) {
                blocks[2][x] = new Block(stone, x, 2, 1, 1, 1);
                blocks[1][x] = new Block(stone, x, 1, 1, 1, 1);
                blocks[0][x] = new Block(stone, x, 0, 1, 1, 1);
            }

            for(int x = 5; x < width; x++) {
                if(Math.random() > 0.5) {
                    blocks[3][x] = new Block(dirt, x, 3, 1, 1, 1);
                    blocks[2][x] = new Block(stone, x, 2, 1, 1, 1);
                    blocks[1][x] = new Block(stone, x, 1, 1, 1, 1);
                    blocks[0][x] = new Block(stone, x, 0, 1, 1, 1);
                } else {
                    blocks[2][x] = new Block(dirt, x, 2, 1, 1, 1);
                    blocks[1][x] = new Block(stone, x, 1, 1, 1, 1);
                    blocks[0][x] = new Block(stone, x, 0, 1, 1, 1);
                }
            }
        
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *  Handles the redirect to the next map.
     */
    public void NextLevel()
    {
        
        throw new UnsupportedOperationException();
    }

}
