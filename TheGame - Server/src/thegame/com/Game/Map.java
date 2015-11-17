package thegame.com.Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.BasicPublisher;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.shared.iMap;

/**
 * The class for the map of the game.
 *
 * @author laure
 */
public class Map extends UnicastRemoteObject implements iMap {

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
    private List<MapObject> objects;
    private List<Enemy> enemies;
    private List<Player> players;

    private List<MapObject> toUpdate;

    private ExecutorService threadPool;
    
    private final BasicPublisher publisher;

    /**
     * Creates a new instance of the map with height,width, spawnX and spawnY.
     *
     * @throws java.rmi.RemoteException
     */
    public Map() throws RemoteException
    {
        width = 300;
        height = 100;

        objects = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        toUpdate = new ArrayList<>();
        blocks = new Block[height][width];

        threadPool = Executors.newCachedThreadPool();
        
        publisher = new BasicPublisher(new String[]
        {
            "update"
        });
    }

    /**
     *
     */
    public void generateMap()
    {

        int y = height - 1;
        int x = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(new File("src/resources/testMapI1.txt"))))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                x = 0;
                for (char b : line.toCharArray())
                {

                    switch (b)
                    {
                        case '0':
                            break;
                        case 'x':
                            this.spawnX = x;
                            this.spawnY = y;
                            break;
                        case 'd':
                            blocks[y][x] = new Block(BlockType.Dirt, x, y, 1, this);
                            break;
                        case 's':
                            blocks[y][x] = new Block(BlockType.Stone, x, y, 1, this);
                            break;
                        case 'S':
                            blocks[y][x] = new Block(BlockType.Sand, x, y, 1, this);
                            break;
                        case 'O':
                            blocks[y][x] = new Block(BlockType.Obsidian, x, y, 1, this);
                            break;
                        case 'c':
                            blocks[y][x] = new Block(BlockType.Coal, x, y, 1, this);
                            break;
                        case 't':
                            blocks[y][x] = new Block(BlockType.Tin, x, y, 1, this);
                            break;
                        case 'i':
                            blocks[y][x] = new Block(BlockType.Iron, x, y, 1, this);
                            break;
                    }

                    x++;
                }

                y--;
            }

            addObject(new Enemy("Loser", 100, null, getWidth() - 10, 25, null, 1, 1, this));

        } catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public void addObject(MapObject mo)
    {
        if (mo instanceof Enemy)
        {
            this.objects.add(mo);
            enemies.add((Enemy) mo);
        } else if (mo instanceof Block)
        {
            blocks[(int) mo.getY()][(int) mo.getX()] = (Block) mo;
        } else if (mo instanceof Player)
        {
            this.objects.add(mo);
            players.add((Player) mo);
        }
        toUpdate.add(mo);
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

    public MapObject GetTile(float x, float y, MapObject self, boolean relative)
    {
        // Find in blocks
        try
        {

            for (MapObject mo : objects)
            {
                if (mo.equals(self) || mo.getS() == 0)
                {
                    continue;
                }

                if (relative)
                {
                    if (x + .001f >= mo.getX() && x + .001f <= mo.getX() + mo.getW() && y + .001f >= mo.getY() && y + .001f <= mo.getY() + mo.getH())
                    {
                        return mo;
                    }
                } else
                {
                    if (x + .001f >= mo.getX() && x + .001f <= mo.getX() + mo.getW() && y + .001f <= mo.getY() && y + .001f >= mo.getY() - mo.getH())
                    {
                        return mo;
                    }
                }
            }

            int bx = (int) Math.floor(x);
            int by;
            if (relative)
            {
                by = (int) Math.floor(y);
            } else
            {
                by = (int) Math.ceil(y);
            }

            Block found = blocks[by][bx];

            if (relative)
            {
                if (x >= bx && x <= bx + found.getW() && y >= by && y <= by + found.getH())
                {
                    found.debug = true;
                    return found;
                }
            } else
            {
                if (x >= bx && x <= bx + found.getW() && y <= by && y >= by - found.getH())
                {
                    found.debug = true;
                    return found;
                }
            }
        } catch (Exception e)
        {
        }
        return null;
    }

    public List<MapObject> getObjects(int startX, int startY, int endX, int endY)
    {
        List<MapObject> ret = new ArrayList<>();

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

        for (MapObject mo : objects)
        {
            if (mo.getX() + mo.getW() > startX && mo.getX() < endX && mo.getY() - mo.getH() > startY && mo.getY() < endY)
            {
                ret.add(mo);
            }
        }

        return ret;
    }

    public void removeMapObject(MapObject removeObject)
    {
        if (removeObject instanceof Block)
        {
            try
            {
                blocks[(int) removeObject.getY()][(int) removeObject.getX()] = null;
            } catch (Exception e)
            {
            }
        } else if (removeObject instanceof Enemy)
        {
            objects.remove(removeObject);
            enemies.remove((Enemy) removeObject);
            toUpdate.remove(removeObject);
        } else if (removeObject instanceof Player)
        {
            objects.remove(removeObject);
            players.remove((Player) removeObject);
            toUpdate.remove(removeObject);
        }
    }

    public void update()
    {
        HashMap<MapObject, Future<Boolean>> updateResults = new HashMap<>();

        for (MapObject update : toUpdate)
        {
            updateResults.put(update, threadPool.submit(update));
        }

        for (java.util.Map.Entry<MapObject, Future<Boolean>> entrySet : updateResults.entrySet())
        {
            MapObject key = entrySet.getKey();

            if ((key instanceof Enemy))
            {
                continue;
            }
            Runnable task = () ->
            {
                try
                {
                    boolean value = entrySet.getValue().get();
                    
                    if (value)
                    {
                        for (java.util.Map.Entry<MapObject.sides, List<MapObject>> collision : key.collision().entrySet())
                        {
                            for (MapObject toUpdateMO : collision.getValue())
                            {
                                addToUpdate(toUpdateMO);
                            }
                        }
                    } else
                    {
                        toUpdate.remove(key);
                    }
                } catch (InterruptedException | ExecutionException ex)
                {
                    Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            
            threadPool.submit(task);
        }
        
        publisher.inform(this, "update", null, toUpdate);
    }

    public void addToUpdate(MapObject toUpdateMO)
    {
        if (!toUpdate.contains(toUpdateMO))
        {
            toUpdate.add(toUpdateMO);
        }
    }
}
