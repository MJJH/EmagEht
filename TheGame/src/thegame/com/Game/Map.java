package thegame.com.Game;

import com.sun.javaws.exceptions.InvalidArgumentException;
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

        this.spawnX = 0;
        this.spawnY = 4;

        blocks = new Block[height][width];

        BlockType dirt;
        BlockType stone;
        try
        {
            dirt = new BlockType("dirt", 1, 1, 0, 0);
            stone = new BlockType("stone", 1, 1, 0, 20);

            for (int x = 0; x < 5; x++)
            {
                blocks[2][x] = new Block(stone, x, 2, 1);
                blocks[1][x] = new Block(stone, x, 1, 1);
                blocks[0][x] = new Block(stone, x, 0, 1);
            }

            for (int x = 5; x < width; x++)
            {
                if (Math.random() > 0.5)
                {
                    blocks[3][x] = new Block(dirt, x, 3, 1);
                    blocks[2][x] = new Block(dirt, x, 2, 1);
                    blocks[1][x] = new Block(stone, x, 1, 1);
                    blocks[0][x] = new Block(stone, x, 0, 1);
                } else
                {
                    blocks[2][x] = new Block(dirt, x, 2, 1);
                    blocks[1][x] = new Block(stone, x, 1, 1);
                    blocks[0][x] = new Block(stone, x, 0, 1);
                }
            }

        } catch (IOException ex)
        {
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
