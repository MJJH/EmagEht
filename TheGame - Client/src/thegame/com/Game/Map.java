package thegame.com.Game;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
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
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.shared.iGameLogic;

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

    private transient Account myAccount;
    private transient iGameLogic gameLogic;
    private transient ReentrantLock lock;

    /**
     * Creates a new instance of the map with height,width, spawnX and spawnY.
     *
     * @param height
     * @param width
     * @param teamlifes
     * @param time
     * @param seasons
     * @param level
     * @param spawnX
     * @param enemies
     * @param blocks
     * @param objects
     * @param spawnY
     * @param toUpdate
     * @param players
     */
    public Map(int height, int width, int teamlifes, int time, Array[] seasons, int level, int spawnX, int spawnY, List<Block> blocks, List<MapObject> objects, List<Enemy> enemies, List<Player> players, List<MapObject> toUpdate, Account myAccount, iGameLogic gameLogic)
    {
        this.height = height;
        this.width = width;
        this.teamlifes = teamlifes;
        this.time = time;
        this.seasons = seasons;
        this.level = level;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.objects = objects;
        this.enemies = enemies;
        this.players = players;
        this.toUpdate = toUpdate;
        this.blocks = new Block[height][width];
        this.myAccount = myAccount;
        this.gameLogic = gameLogic;

        for (Block block : blocks)
        {
            if (block.getSkin() == null)
            {
                block.createSkin();
            }
            this.blocks[(int) block.getY()][(int) block.getX()] = block;
        }

        threadPool = Executors.newCachedThreadPool();
        lock = new ReentrantLock();
    }

    public void loadAfterRecieve()
    {
        for (int y = 0; y < height - 1; y++)
        {
            for (int x = 0; x < width - 1; x++)
            {
                try
                {
                    Block cur = blocks[y][x];

                    if (cur != null)
                    {
                        if (cur.getSkin() == null)
                        {
                            cur.createSkin();
                        }
                    }
                } catch (NullPointerException e)
                {
                }
            }
        }

        threadPool = Executors.newCachedThreadPool();
        lock = new ReentrantLock();
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
                toUpdate.remove(removeObject);
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
        lock.lock();
        try
        {
            HashMap<MapObject, Future<Boolean>> updateResults = new HashMap<>();

            for (MapObject update : toUpdate)
            {
                if (update.getX() == -1 && update.getY() == -1)
                {
                    removeMapObject(update);
                    continue;
                }
                if (update.getNewObject())
                {
                    addObject(update);
                    update.setNewObject(false);
                    continue;
                }
                if (update instanceof Enemy)
                {
                    removeMapObject(update);
                    addObject(update);
                    continue;
                } else if (update instanceof Block)
                {
                    blocks[(int) update.getY()][(int) update.getX()] = (Block) update;
                    continue;
                } else if (update instanceof Player)
                {
                    if (!((Player) update).getName().equals(myAccount.getUsername()))
                    {
                        removeMapObject(update);
                        addObject(update);
                        continue;
                    }
                }

                updateResults.put(update, threadPool.submit(update));
            }

            for (java.util.Map.Entry<MapObject, Future<Boolean>> entrySet : updateResults.entrySet())
            {
                MapObject key = entrySet.getKey();
                boolean value = entrySet.getValue().get();

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
            }
        } catch (InterruptedException | ExecutionException ex)
        {
            Logger.getLogger(Map.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            lock.unlock();
        }
    }

    public synchronized void addToUpdate(MapObject toUpdateMO)
    {
        lock.lock();
        try
        {
            toUpdate.add(toUpdateMO);
        } finally
        {
            lock.unlock();
        }
        try
        {
            gameLogic.addToUpdate(toUpdateMO);

        } catch (RemoteException ex)
        {
            Logger.getLogger(Map.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void addToUpdateServer(MapObject toUpdateMO)
    {
        if (!toUpdate.contains(toUpdateMO))
        {
            lock.lock();
            try
            {
                toUpdate.add(toUpdateMO);
            } finally
            {
                lock.unlock();
            }
        }
    }

    public Account getAccount()
    {
        return myAccount;
    }
}
