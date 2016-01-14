package thegame.com.Game;

import thegame.GameClientToServerHandler;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.GameServerToClientHandler;
import thegame.com.Game.Objects.Background;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.Boss;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.engine.Collision;

/**
 * The class for the map of the game.
 *
 * @author laure
 */
public class Map implements Serializable {

    private static final long serialVersionUID = 5529685098267757690L;
    
    private Lobby lobby;

    private int mapObjectID;
    private int height;
    private int width;
    private int teamlifes;
    private int time;
    private Array[] seasons;
    private int level;
    private int spawnX;
    private int spawnY;
    private float gravity;

    private Block[][] blocks;
    private final List<MapObject> objects;
    private List<Enemy> enemies;
    private List<Player> players;

    private transient final List<MapObject> toUpdate;
    private transient final List<MapObject> toAdd;
    private transient final List<MapObject> toRemove;
    private transient final List<MapObject> toUpdatePlayers;

    private transient ExecutorService threadPool;
    private transient GameClientToServerHandler gameClientToServerHandler;
    private transient GameServerToClientHandler gameServerToClientHandler;

    /**
     * Creates a new instance of the map with height,width, spawnX and spawnY.
     *
     * @param lobby
     * @param gameServerToClientHandler
     * @param gameClientToServerHandler
     */
    public Map(Lobby lobby, GameServerToClientHandler gameServerToClientHandler, GameClientToServerHandler gameClientToServerHandler)
    {
        width = 500;
        height = 100;

        teamlifes = 4;
        gravity = .025f;
        
        this.lobby = lobby;

        objects = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        toUpdate = new ArrayList<>();
        toAdd = new ArrayList<>();
        toRemove = new ArrayList<>();
        toUpdatePlayers = new ArrayList<>();
        blocks = new Block[height][width];

        threadPool = Executors.newCachedThreadPool();
        this.gameClientToServerHandler = gameClientToServerHandler;
        this.gameServerToClientHandler = gameServerToClientHandler;
        generateMap();
    }

    /**
     *
     */
    private void generateMap()
    {

        int y = height - 1;
        int x = 0;
        List<Integer> first = new ArrayList<>();

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

                            if (first.contains(x))
                            {
                                blocks[y][x] = new Background(BlockType.blocktypes.get("CaveBackground"), x, y, this);
                            }

                            break;
                        case 'x':
                            this.spawnX = x;
                            this.spawnY = y;
                            break;
                        case 'd':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Dirt"), x, y, this);
                            break;
                        case 's':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Stone"), x, y, this);
                            break;
                        case 'S':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Sand"), x, y, this);
                            break;
                        case 'O':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Obisidian"), x, y, this);
                            break;
                        case 'c':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Coal"), x, y, this);
                            break;
                        case 't':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Tin"), x, y, this);
                            break;
                        case 'i':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Iron"), x, y, this);
                            break;
                        case '|':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Wood"), x, y, this);
                            break;
                        case 'C':
                            blocks[y][x] = new Block(BlockType.blocktypes.get("Copper"), x, y, this);
                    }
                    if (b != '0' && b != 'x')
                    {
                        first.add(x);
                    }
                    x++;
                }

                y--;
            }
            
            for(Account account : lobby.getAccounts())
            {
                players.add(new Player(account, lobby.getChosenCharacters().get(account), account.getUsername(), 100, null, null, spawnX, spawnY, 2f, 1f, this));
            }

            addMapObject(new Enemy("Loser", 100, null, getWidth() - 10, 25, 1, 1, this));
            addMapObject(new Boss("TheBoss",1000,null,getWidth()-10,100,5,5,this));

        } catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public GameServerToClientHandler getGameServerToClientHandler()
    {
        return gameServerToClientHandler;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    /**
     * Handles the redirect to the next map.
     */
    public void NextLevel()
    {
        throw new UnsupportedOperationException();
    }

    public int getMapObjectID()
    {
        return mapObjectID++;
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

    int getTeamLifes()
    {
        return teamlifes;
    }

    public boolean decreaseLife()
    {
        if (teamlifes > 0)
        {
            teamlifes--;
            gameServerToClientHandler.setTeamLifes(teamlifes);
            return true;
        }
        return false;
    }

    public boolean increaseLife()
    {
        if (teamlifes < 4)
        {
            teamlifes++;
            gameServerToClientHandler.setTeamLifes(teamlifes);
            return true;
        }
        return false;
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

    public List<MapObject> getObjects(float x, float y, float range)
    {
        List<MapObject> toReturn = new ArrayList<>();

        synchronized (objects)
        {
            for (MapObject object : objects)
            {
                if (x >= object.getX() - range && x <= object.getX() + range)
                {
                    if (y >= object.getY() - range && y <= object.getY() + range)
                    {
                        toReturn.add(object);
                    }
                }
            }
        }

        return toReturn;
    }

    List<Enemy> getEnemies()
    {
        return enemies;
    }

    List<MapObject> getToUpdate()
    {
        return toUpdate;
    }

    public void addMapObject(MapObject mo)
    {
        synchronized (toAdd)
        {
            toAdd.add(mo);
            synchronized (toUpdate)
            {
                toUpdate.add(mo);
            }
        }
    }

    public void runAddMapObject()
    {
        synchronized (toAdd)
        {
            for (MapObject mo : toAdd)
            {
                gameServerToClientHandler.addMapObject(mo);

                if (mo instanceof Enemy)
                {
                    enemies.add((Enemy) mo);
                    addToUpdate(mo);
                } else if (mo instanceof Block)
                {
                    blocks[(int) mo.getY()][(int) mo.getX()] = (Block) mo;
                } else if (mo instanceof Player)
                {
                    players.add((Player) mo);
                } else
                {
                    objects.add(mo);
                }
            }
            toAdd.clear();
        }
    }

    public void removeMapObject(MapObject removeMapObject)
    {
        synchronized (toRemove)
        {
            toRemove.add(removeMapObject);
        }
    }

    public void runRemoveMapObjects()
    {
        synchronized (toRemove)
        {
            for (MapObject removeObject : toRemove)
            {
                int type = 0;
                if (removeObject instanceof Block)
                {
                    type = 1;
                } else if (removeObject instanceof Enemy)
                {
                    type = 2;
                } else if (removeObject instanceof Player)
                {
                    type = 3;
                } else if (removeObject instanceof MapObject)
                {
                    type = 4;
                }
                gameServerToClientHandler.removeMapObject(removeObject.getID(), type, removeObject.getX(), removeObject.getY());

                synchronized (toUpdate)
                {
                    toUpdate.remove(removeObject);
                }

                if (removeObject instanceof Block)
                {
                    try
                    {
                        blocks[(int) removeObject.getY()][(int) removeObject.getX()] = null;
                    } catch (Exception e)
                    {
                        System.err.println(e.getMessage());
                    }
                } else if (removeObject instanceof Enemy)
                {
                    enemies.remove((Enemy) removeObject);
                } else if (removeObject instanceof Player)
                {
                    players.remove((Player) removeObject);
                } else
                {
                    objects.remove(removeObject);
                }

                for (MapObject collision : Collision.collision(removeObject, true).get(MapObject.sides.TOP))
                {
                    if (!(collision instanceof CharacterGame))
                    {
                        synchronized (toUpdate)
                        {
                            toUpdate.add(collision);
                        }
                    }
                }
            }
            toRemove.clear();
        }
    }

    public MapObject GetTile(float x, float y, MapObject self)
    {
        // Find in enemies

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

        // Find in players
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

        // Find in objects
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

        // Find in blocks
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

        for (Enemy enemy : enemies)
        {
            if (enemy.getX() + enemy.getW() > startX && enemy.getX() < endX && enemy.getY() - enemy.getH() > startY && enemy.getY() < endY)
            {
                ret.add(enemy);
            }
        }

        for (Player player : players)
        {
            if (player.getX() + player.getW() > startX && player.getX() < endX && player.getY() - player.getH() > startY && player.getY() < endY)
            {
                ret.add(player);
            }
        }

        return ret;
    }

    public void addToUpdate(MapObject toUpdateMO)
    {
        if (toUpdate.contains(toUpdateMO))
        {
            return;
        }
        synchronized (toUpdate)
        {
            toUpdate.add(toUpdateMO);
        }
    }

    public void addToPlayerUpdate(Player player)
    {
        if (toUpdatePlayers.contains(player))
        {
            return;
        }
        synchronized (toUpdatePlayers)
        {
            toUpdatePlayers.add(player);
        }
    }

    public void update()
    {
        List<MapObject> toSend = new ArrayList<>();

        synchronized (toAdd)
        {
            if (toAdd.size() > 0)
            {
                runAddMapObject();
            }
        }
        synchronized (toRemove)
        {
            if (toRemove.size() > 0)
            {
                runRemoveMapObjects();
            }
        }
        synchronized (toUpdatePlayers)
        {
            if (toUpdatePlayers.size() > 0)
            {
                toSend.addAll(toUpdatePlayers);
                toUpdatePlayers.clear();
            }
        }

        HashMap<MapObject, Future<Boolean>> updateResults = new HashMap<>();

        synchronized (toUpdate)
        {
            for (MapObject update : toUpdate)
            {
                if (!(update instanceof Player) && !updateResults.containsKey(update))
                {
                    updateResults.put(update, threadPool.submit(update));
                }
            }
        }

        for (java.util.Map.Entry<MapObject, Future<Boolean>> entrySet : updateResults.entrySet())
        {
            try
            {
                MapObject key = entrySet.getKey();
                boolean value = entrySet.getValue().get();
                
                if (key instanceof Enemy)
                {
                    toSend.add(key);
                    continue;
                }

                if (value)
                {
                    toSend.add(key);
                    /*
                     for (java.util.Map.Entry<MapObject.sides, List<MapObject>> collision : Collision.collision(key, true).entrySet())
                     {
                     for (MapObject toUpdateMO : collision.getValue())
                     {
                     if (!(toUpdateMO instanceof CharacterGame))
                     {
                     synchronized (toUpdate)
                     {
                     toUpdate.add(toUpdateMO);
                     }
                     }
                     }
                     }
                     */
                } else
                {
                    synchronized (toUpdate)
                    {
                        toUpdate.remove(key);
                    }
                }
            } catch (InterruptedException | ExecutionException ex)
            {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!toSend.isEmpty())
        {
            gameServerToClientHandler.updateObjects(toSend);
        }
    }

    public float getGravity()
    {
        return gravity;
    }
}
