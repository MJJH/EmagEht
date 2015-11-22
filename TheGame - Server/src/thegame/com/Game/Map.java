package thegame.com.Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.BasicPublisher;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;

/**
 * The class for the map of the game.
 *
 * @author laure
 */
public class Map implements Serializable {

    private static final long serialVersionUID = 5529685098267757690L;

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

    private transient ExecutorService threadPool;
    private transient GameLogic gameLogic;
    private transient BasicPublisher publisher;
    private transient ReentrantLock toUpdateLock;
    private transient ReentrantLock objectsLock;
    private transient ReentrantLock enemiesLock;
    private transient ReentrantLock playersLock;
    private transient ReentrantLock blocksLock;

    /**
     * Creates a new instance of the map with height,width, spawnX and spawnY.
     *
     * @param publisher
     * @param gameLogic
     */
    public Map(BasicPublisher publisher, GameLogic gameLogic)
    {
        width = 300;
        height = 100;

        objects = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        toUpdate = new ArrayList<>();
        blocks = new Block[height][width];

        threadPool = Executors.newCachedThreadPool();
        this.gameLogic = gameLogic;
        this.publisher = publisher;
        toUpdateLock = new ReentrantLock();
        objectsLock = new ReentrantLock();
        enemiesLock = new ReentrantLock();
        playersLock = new ReentrantLock();
        blocksLock = new ReentrantLock();
        generateMap();
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
                            blocks[y][x] = new Block(BlockType.Dirt, x, y, 1, this, gameLogic);
                            break;
                        case 's':
                            blocks[y][x] = new Block(BlockType.Stone, x, y, 1, this, gameLogic);
                            break;
                        case 'S':
                            blocks[y][x] = new Block(BlockType.Sand, x, y, 1, this, gameLogic);
                            break;
                        case 'O':
                            blocks[y][x] = new Block(BlockType.Obsidian, x, y, 1, this, gameLogic);
                            break;
                        case 'c':
                            blocks[y][x] = new Block(BlockType.Coal, x, y, 1, this, gameLogic);
                            break;
                        case 't':
                            blocks[y][x] = new Block(BlockType.Tin, x, y, 1, this, gameLogic);
                            break;
                        case 'i':
                            blocks[y][x] = new Block(BlockType.Iron, x, y, 1, this, gameLogic);
                            break;
                    }

                    x++;
                }

                y--;
            }

            addMapObject(new Enemy("Loser", 100, null, getWidth() - 10, 25, null, 1, 1, this, gameLogic));

        } catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public void addMapObject(MapObject mo)
    {
        publisher.inform(this, "ServerUpdate", "addMapObject", mo);

        if (mo instanceof Enemy)
        {
            enemiesLock.lock();
            try
            {
                enemies.add((Enemy) mo);
            } finally
            {
                enemiesLock.unlock();
            }
            objectsLock.lock();
            try
            {
                this.objects.add(mo);
            } finally
            {
                objectsLock.unlock();
            }
            addToUpdate(mo);
        } else if (mo instanceof Block)
        {
            blocksLock.lock();
            try
            {
                blocks[(int) mo.getY()][(int) mo.getX()] = (Block) mo;
            } finally
            {
                blocksLock.unlock();
            }
        } else if (mo instanceof Player)
        {
            objectsLock.lock();
            try
            {
                this.objects.add(mo);
            } finally
            {
                objectsLock.unlock();
            }
            playersLock.lock();
            try
            {
                players.add((Player) mo);
            } finally
            {
                playersLock.unlock();
            }
        }
    }

    public void removeMapObject(MapObject removeObject)
    {
        publisher.inform(this, "ServerUpdate", "removeMapObject", removeObject);
        if (removeObject instanceof Block)
        {
            blocksLock.lock();
            try
            {
                blocks[(int) removeObject.getY()][(int) removeObject.getX()] = null;
            } catch (Exception e)
            {
            } finally
            {
                blocksLock.unlock();
            }
            toUpdateLock.lock();
            try
            {
                toUpdate.remove(removeObject);
            } finally
            {
                toUpdateLock.unlock();
            }
        } else if (removeObject instanceof Enemy)
        {
            objectsLock.lock();
            try
            {
                objects.remove(removeObject);
            } finally
            {
                objectsLock.unlock();
            }
            enemiesLock.lock();
            try
            {
                enemies.remove((Enemy) removeObject);
            } finally
            {
                enemiesLock.unlock();
            }
            toUpdateLock.lock();
            try
            {
                toUpdate.remove(removeObject);
            } finally
            {
                toUpdateLock.unlock();
            }
        } else if (removeObject instanceof Player)
        {
            objectsLock.lock();
            try
            {
                objects.remove(removeObject);
            } finally
            {
                objectsLock.unlock();
            }
            playersLock.lock();
            try
            {
                players.remove((Player) removeObject);
            } finally
            {
                playersLock.unlock();
            }
            toUpdateLock.lock();
            try
            {
                toUpdate.remove(removeObject);
            } finally
            {
                toUpdateLock.unlock();
            }
        }
    }

    public void updateMapObject(MapObject update)
    {
        if (update instanceof Player)
        {
            objectsLock.lock();
            try
            {
                objects.remove(update);
                objects.add(update);
            } finally
            {
                objectsLock.unlock();
            }
            playersLock.lock();
            try
            {
                players.remove((Player) update);
                players.add((Player) update);
            } finally
            {
                playersLock.unlock();
            }
        }
    }

    /**
     * Handles the redirect to the next map.
     */
    public void NextLevel()
    {
        throw new UnsupportedOperationException();
    }

    public int getSpawnX()
    {
        return spawnX;
    }

    public int getSpawnY()
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

    public List<MapObject> getBlocksAndObjects(int startX, int startY, int endX, int endY)
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

    public List<Block> getBlocks()
    {
        List<Block> ret = new ArrayList<>();

        for (int y = 0; y < height - 1; y++)
        {
            for (int x = 0; x < width - 1; x++)
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

    public void update()
    {
        toUpdateLock.lock();

        try
        {
            HashMap<MapObject, Future<Boolean>> updateResults = new HashMap<>();

            for (MapObject update : toUpdate)
            {
                if (!(update instanceof Player))
                {
                    updateResults.put(update, threadPool.submit(update));
                }
            }

            for (java.util.Map.Entry<MapObject, Future<Boolean>> entrySet : updateResults.entrySet())
            {
                MapObject key = entrySet.getKey();

                try
                {
                    boolean value = entrySet.getValue().get();
                    publisher.inform(this, "ServerUpdate", "updateMapObject", key);

                    if ((key instanceof Enemy))
                    {
                        continue;
                    }

                    if (value)
                    {
                        for (java.util.Map.Entry<MapObject.sides, List<MapObject>> collision : key.collision().entrySet())
                        {
                            for (MapObject toUpdateMO : collision.getValue())
                            {
                                toUpdate.add(toUpdateMO);

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
            }
        } finally
        {
            toUpdateLock.unlock();
        }

    }

    public void addToUpdate(MapObject toUpdateMO)
    {
        toUpdateLock.lock();
        try
        {
            toUpdate.add(toUpdateMO);
        } finally
        {
            toUpdateLock.unlock();
        }
    }

    int getTeamLifes()
    {
        return teamlifes;
    }

    int getTime()
    {
        return time;
    }

    Array[] getSeasons()
    {
        return seasons;
    }

    int getLevel()
    {
        return level;
    }

    List<MapObject> getObjects()
    {
        return objects;
    }

    List<Enemy> getEnemies()
    {
        return enemies;
    }

    List<MapObject> getToUpdate()
    {
        return toUpdate;
    }
}
