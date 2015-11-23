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
    private transient Player me;
    private transient iGameLogic gameLogic;
    private transient ReentrantLock toUpdateLock;
    private transient ReentrantLock objectsLock;
    private transient ReentrantLock enemiesLock;
    private transient ReentrantLock playersLock;
    private transient ReentrantLock blocksLock;

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
    public Map(int height, int width, int teamlifes, int time, Array[] seasons, int level, int spawnX, int spawnY, List<Block> blocks, List<MapObject> objects, List<Enemy> enemies, List<Player> players, List<MapObject> toUpdate, Account myAccount, iGameLogic gameLogic, Player me)
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
        this.me = me;

        for (Block block : blocks)
        {
            if (block.getSkin() == null)
            {
                block.createSkin();
            }
            this.blocks[(int) block.getY()][(int) block.getX()] = block;
        }

        threadPool = Executors.newCachedThreadPool();
        toUpdateLock = new ReentrantLock();
        objectsLock = new ReentrantLock();
        enemiesLock = new ReentrantLock();
        playersLock = new ReentrantLock();
        blocksLock = new ReentrantLock();
    }

    public void loadAfterRecieve(iGameLogic gameLogic, Account myAccount, Player me)
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
        toUpdateLock = new ReentrantLock();
        objectsLock = new ReentrantLock();
        enemiesLock = new ReentrantLock();
        playersLock = new ReentrantLock();
        blocksLock = new ReentrantLock();
        this.gameLogic = gameLogic;
        this.myAccount = myAccount;
        this.me = me;
    }

    public List<Player> getPlayers()
    {
        playersLock.lock();
        try
        {
            return players;
        } finally
        {
            playersLock.unlock();
        }
    }

    public void addMapObject(MapObject mo)
    {
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
        if (update instanceof Block)
        {
            blocksLock.lock();
            try
            {
                blocks[(int) update.getY()][(int) update.getX()] = (Block) update;
            } catch (Exception e)
            {
            } finally
            {
                blocksLock.unlock();
            }
        } else if (update instanceof Enemy)
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
            enemiesLock.lock();
            try
            {
                enemies.remove((Enemy) update);
                enemies.add((Enemy) update);
            } finally
            {
                enemiesLock.unlock();
            }
        } else if (update instanceof Player)
        {
            if (!update.equals(me))
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

    public MapObject GetTile(float x, float y, MapObject self)
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
                if (mo.getX() <= x && mo.getX() + mo.getW() >= x && mo.getY() >= y && mo.getY() - mo.getH() <= y)
                {
                    return mo;
                }

            }

            int bx = (int) Math.floor(x);
            int by = (int) Math.ceil(y);
            MapObject mo = blocks[by][bx];

            if (mo.getX() <= x && mo.getX() + mo.getW() >= x && mo.getY() >= y && mo.getY() - mo.getH() <= y)
            {
                return mo;
            }

        } catch (Exception e)
        {
        }
        return null;
    }

    public List<MapObject> getBlocksAndObjects(int startX, int startY, int endX, int endY)
    {
        List<MapObject> ret = new ArrayList<>();

        if (startX >= endX || startY >= endY)
        {
            throw new IllegalArgumentException("Wrong start and end parameters given");
        }

        if (startX < 0)
        {
            startX = 0;
        }
        if (startY < 0)
        {
            startY = 0;
        }
        if (endX > width)
        {
            endX = width;
        }
        if (endY > height)
        {
            endY = height;
        }

        blocksLock.lock();
        try
        {
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
        } finally
        {
            blocksLock.unlock();
        }

        objectsLock.lock();
        try
        {
            for (MapObject mo : objects)
            {
                if (mo.getX() + mo.getW() > startX && mo.getX() < endX && mo.getY() - mo.getH() > startY && mo.getY() < endY)
                {
                    ret.add(mo);
                }
            }
        } finally
        {
            objectsLock.unlock();
        }

        return ret;
    }

    public void update()
    {
        HashMap<MapObject, Future<Boolean>> updateResults = new HashMap<>();

        toUpdateLock.lock();
        try
        {
            for (MapObject update : toUpdate)
            {
                if(!updateResults.containsKey(update))
                    updateResults.put(update, threadPool.submit(update));
            }

            for (java.util.Map.Entry<MapObject, Future<Boolean>> entrySet : updateResults.entrySet())
            {
                MapObject key = entrySet.getKey();
                boolean value = entrySet.getValue().get();

                if (key == me)
                {
                    //gameLogic.updateMapObject(key);
                    gameLogic.movePlayer(me.getID(), me.getX(),me.getY());
                }

                if (value)
                {
                    for (java.util.Map.Entry<MapObject.sides, List<MapObject>> collision : key.collision().entrySet())
                    {
                        for (MapObject toUpdateMO : collision.getValue())
                        {
                            if (!(toUpdateMO instanceof Block))
                            {
                                //toUpdate.add(toUpdateMO);
                            }
                        }
                    }
                } else
                {
                    toUpdate.remove(key);
                }
            }
        } catch (InterruptedException | ExecutionException | RemoteException ex)
        {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);

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

    public Account getAccount()
    {
        return myAccount;
    }
}
