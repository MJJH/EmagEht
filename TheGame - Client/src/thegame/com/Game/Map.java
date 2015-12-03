package thegame.com.Game;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private transient ExecutorService playerUpdateThread;

    private transient Account myAccount;
    private transient Player me;
    private transient iGameLogic gameLogic;
    private transient ReentrantLock objectsLock;
    private transient ReentrantLock enemiesLock;
    private transient ReentrantLock playersLock;
    private transient ReentrantLock blocksLock;

    public void loadAfterRecieve(iGameLogic gameLogic, Account myAccount, Player me)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
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

        playerUpdateThread = Executors.newFixedThreadPool(1);
        objectsLock = new ReentrantLock();
        enemiesLock = new ReentrantLock();
        playersLock = new ReentrantLock();
        blocksLock = new ReentrantLock();
        this.gameLogic = gameLogic;
        this.myAccount = myAccount;
        this.me = me;

        players.remove(me);
        players.add(me);
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

    public List<Enemy> getEnemies()
    {
        enemiesLock.lock();
        try
        {
            return enemies;
        } finally
        {
            enemiesLock.unlock();
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

    public Account getAccount()
    {
        return myAccount;
    }

    public Player getMe()
    {
        return me;
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

        } else if (removeObject instanceof Enemy)
        {
            enemiesLock.lock();
            try
            {
                enemies.remove((Enemy) removeObject);
            } finally
            {
                enemiesLock.unlock();
            }
        } else if (removeObject instanceof Player)
        {
            playersLock.lock();
            try
            {
                players.remove((Player) removeObject);
            } finally
            {
                playersLock.unlock();
            }
        }
    }

    public void removeMapObject(int type, int id, int x, int y)
    {
        MapObject removeObject = null;
        switch (type)
        {
            case 1:
                //delete block
                blocksLock.lock();
                try
                {
                    blocks[y][x] = null;
                } finally
                {
                    blocksLock.unlock();
                }
                break;
            case 2:
                //delete enemy
                enemiesLock.lock();
                try
                {
                    for (Enemy enemy : enemies)
                    {
                        if (enemy.getID() == id)
                        {
                            removeObject = enemy;
                            break;
                        }
                    }
                    enemies.remove((Enemy) removeObject);
                } finally
                {
                    enemiesLock.unlock();
                }
                break;
            case 3:
                // delete player
                playersLock.lock();
                try
                {
                    for (Player player : players)
                    {
                        if (player.getID() == id)
                        {
                            removeObject = player;
                            break;
                        }
                    }
                    players.remove((Player) removeObject);
                } finally
                {
                    playersLock.unlock();
                }
                break;
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

                        if (cur != null
                                && (cur.getX() + cur.getW() >= startX && cur.getX() <= endX && cur.getY() - cur.getH() >= startY && cur.getY() <= endY))
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

        enemiesLock.lock();
        try
        {
            for (Enemy enemy : enemies)
            {
                if (enemy.getX() + enemy.getW() > startX && enemy.getX() < endX && enemy.getY() - enemy.getH() > startY && enemy.getY() < endY)
                {
                    ret.add(enemy);
                }
            }
        } finally
        {
            enemiesLock.unlock();
        }

        playersLock.lock();
        try
        {
            for (Player player : players)
            {
                if (player.getX() + player.getW() > startX && player.getX() < endX && player.getY() - player.getH() > startY && player.getY() < endY)
                {
                    ret.add(player);
                }
            }
        } finally
        {
            playersLock.unlock();
        }

        return ret;
    }

    public void update()
    {
        try
        {
            if (me.getToUpdate())
            {
                me.update();

                int direction = 1;
                if (me.getDirection() == MapObject.sides.LEFT)
                {
                    direction = 0;
                }
                gameLogic.sendMyLoc(me.getID(), me.getX(), me.getY(), direction);
            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
