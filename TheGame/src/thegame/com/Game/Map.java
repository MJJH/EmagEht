package thegame.com.Game;

import java.lang.reflect.Array;

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

    /**
     * Creates a new instance of the map with height,width, spawnX and spawnY.
     * @param height The height of the Map (y-value)
     * @param width The width of the Map (x-value)
     * @param spawnX The X coordinate of spawnpoint
     * @param spawnY The Y coordinate of the spawnpoint
     */
    public Map(int height, int width, int spawnX, int spawnY)
    {
    this.height= height;
    this.width=width;
    this.spawnX=spawnX;
    this.spawnY=spawnY;
    }

    /**
     *  Handles the redirect to the next map.
     */
    public void NextLevel()
    {
        
        throw new UnsupportedOperationException();
    }

}
