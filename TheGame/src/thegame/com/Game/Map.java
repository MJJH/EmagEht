package thegame.com.Game;

import com.sun.javaws.exceptions.InvalidArgumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.MapObject;

/**
 * The class for the map of the game.
 *
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

        blocks = new Block[height][width];

        int y = height-1;
        int x = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/resources/testMapI1.txt")))) {
            String line;
            
            while((line = br.readLine()) != null) {
                x = 0;
                for(char b : line.toCharArray()) {
                    
                    switch(b) {
                        case '0': break;
                        case 'x': 
                            this.spawnX = x;
                            this.spawnY = y;
                            break;
                        case 'd':
                            blocks[y][x] = new Block(BlockType.Dirt, x, y, 1);
                            break;
                        case 's':
                            blocks[y][x] = new Block(BlockType.Stone, x, y, 1);
                            break;
                        case 'S':
                            blocks[y][x] = new Block(BlockType.Sand, x, y, 1);
                            break;
                        case 'O':
                            blocks[y][x] = new Block(BlockType.Obsidian, x, y, 1);
                            break;
                        case 'c':
                            blocks[y][x] = new Block(BlockType.Coal, x, y, 1);
                            break;
                        case 't':
                            blocks[y][x] = new Block(BlockType.Tin, x, y, 1);
                            break;
                        case 'i':
                            blocks[y][x] = new Block(BlockType.Iron, x, y, 1);
                            break;
                    }
                    
                    x++;
                }
                
                y--;
            }
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the redirect to the next map.
     */
    public void NextLevel()
    {
        throw new UnsupportedOperationException();
    }

    public float getSpawnX()
    {
        return spawnX;
    }

    public float getSpawnY()
    {
        return spawnY;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    
    
    public Block GetTile(float x, float y)
    {
        
        for (Block Blocka[] : blocks) {
         for(Block block : Blocka)
         {
            if(block.getX() == x && block.getY() == y)
            {
              //test
              return block;
            }
         }
        }
        return null;
    }
    public List<MapObject> getObjects(int startX, int startY, int endX, int endY)
    {
        List<MapObject> ret = new ArrayList<MapObject>();

        if (startX < 0 || startY < 0 || endX > width || endY > height || startX >= endX || startY >= endY)
        {
            throw new IllegalArgumentException("Wrong start and end parameters given");
        }

        for (int y = startY; y < endY; y++)
        {
            for (int x = startX; x < endX; x++)
            {
                try
                {
                    Block cur = blocks[y][x];

                    if (cur != null)
                    {
                        ret.add(cur);
                    }
                } catch (Exception e)
                {
                }
            }
        }

        return ret;
    }

}
