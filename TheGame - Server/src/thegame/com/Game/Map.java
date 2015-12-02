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
import thegame.com.Game.Objects.Characters.CharacterGame;
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

    private transient List<MapObject> toUpdate;

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
                            blocks[y][x] = new Block(BlockType.Dirt, x, y, this, gameLogic);
                            break;
                        case 's':
                            blocks[y][x] = new Block(BlockType.Stone, x, y, this, gameLogic);
                            break;
                        case 'S':
                            blocks[y][x] = new Block(BlockType.Sand, x, y, this, gameLogic);
                            break;
                        case 'O':
                            blocks[y][x] = new Block(BlockType.Obsidian, x, y, this, gameLogic);
                            break;
                        case 'c':
                            blocks[y][x] = new Block(BlockType.Coal, x, y, this, gameLogic);
                            break;
                        case 't':
                            blocks[y][x] = new Block(BlockType.Tin, x, y, this, gameLogic);
                            break;
                        case 'i':
                            blocks[y][x] = new Block(BlockType.Iron, x, y, this, gameLogic);
                            break;
                        case 'b':
                            blocks[y][x] = new Block(BlockType.Wood, x, y, this, gameLogic);
                    }

                    x++;
                }

                y--;
            }

            addMapObject(new Enemy("Loser", 100, null, getWidth() - 10, 25, 1, 1, this, gameLogic));

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
        toUpdateLock.lock();
        try
        {
            toUpdate.remove(removeObject);
        } finally
        {
            toUpdateLock.unlock();
        }

        int type = 0;
        if (removeObject instanceof Block)
        {
            type = 1;
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
            type = 2;
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
            type = 3;
            playersLock.lock();
            try
            {
                players.remove((Player) removeObject);
            } finally
            {
                playersLock.unlock();
            }
        }

        int[] toDelete = new int[4];
        toDelete[0] = type;
        toDelete[1] = removeObject.getID();
        toDelete[2] = (int) removeObject.getX();
        toDelete[3] = (int) removeObject.getY();
        publisher.inform(this, "ServerUpdate", "removeMapObject", toDelete);
    }

    public void updateMapObject(MapObject update)
    {
        if (update instanceof Player)
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

    public MapObject GetTile(float x, float y, MapObject self)
    {
        // Find in enemies
        enemiesLock.lock();
        try
        {
            for (Enemy mo : enemies)
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
        } finally
        {
            enemiesLock.unlock();
        }

        // Find in players
        playersLock.lock();
        try
        {
            for (Player mo : players)
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
        } finally
        {
            playersLock.unlock();
        }

        // Find in objects
        objectsLock.lock();
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
        } finally
        {
            objectsLock.unlock();
        }

        // Find in blocks
        blocksLock.lock();
        try
        {
            int bx = (int) Math.floor(x);
            int by = (int) Math.ceil(y);
            MapObject mo = blocks[by][bx];

            if (mo.getX() <= x && mo.getX() + mo.getW() >= x && mo.getY() >= y && mo.getY() - mo.getH() <= y)
            {
                return mo;
            }

        } catch (Exception e)
        {
        } finally
        {
            blocksLock.unlock();
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
                if (!(update instanceof Player) && !updateResults.containsKey(update))
                {
                    updateResults.put(update, threadPool.submit(update));
                }
            }

            for (java.util.Map.Entry<MapObject, Future<Boolean>> entrySet : updateResults.entrySet())
            {
                MapObject key = entrySet.getKey();

                boolean value = entrySet.getValue().get();

                //publisher.inform(this, "ServerUpdate", "updateMapObject", value);
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
                            if (!(toUpdateMO instanceof Block))
                            {
                                toUpdate.add(toUpdateMO);
                            }
                        }
                    }
                } else
                {
                    toUpdate.remove(key);
                }
            }
        } catch (InterruptedException | ExecutionException ex)
        {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            toUpdateLock.unlock();
        }

    }

    public void addToUpdate(MapObject toUpdateMO)
    {
        if (toUpdate.contains(toUpdateMO))
        {
            return;
        }

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

    public void sendUpdateHP(int type, int id, int hp)
    {
        int[] toSend =
        {
            type, id, hp
        };
        publisher.inform(this, "ServerUpdate", "updateHP", toSend);
    }

    public void sendRespawn(int id, float xPosition, float yPosition)
    {
        float[] toSend =
        {
            id, xPosition, yPosition
        };
        publisher.inform(this, "ServerUpdate", "respawnPlayer", toSend);
    }

    public void sendKnockBack(int id, float hSpeed, float vSpeed)
    {
        float[] toSend =
        {
            id, hSpeed, vSpeed
        };
        publisher.inform(this, "ServerUpdate", "knockBackPlayer", toSend);
    }
}
