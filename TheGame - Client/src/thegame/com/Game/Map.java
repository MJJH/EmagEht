package thegame.com.Game;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.MapObject.sides;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.com.Menu.Message;
import thegame.shared.IGameClientToServer;

/**
 * The class for the map of the game.
 *
 * @author laure
 */
public class Map implements Serializable {

    private static final long serialVersionUID = 5529685098267757690L;
    
    private Lobby lobby;

    protected int id;
    protected int height;
    protected int width;
    protected int teamlifes;
    protected int time;
    protected Array[] seasons;
    protected int level;
    protected int spawnX;
    protected int spawnY;
    protected float gravity;

    protected Block[][] blocks;
    protected List<MapObject> objects;
    protected List<Enemy> enemies;
    protected List<Player> players;

    private transient ExecutorService playerUpdateThread;

    private transient Account myAccount;
    protected transient Player me;
    private transient IGameClientToServer gameClientToServer;
    private transient gui.pages.LobbyFX lobbyFX;
    private transient List<Message> chatMessages;

    public void loadAfterRecieve(IGameClientToServer gameClientToServer, Account myAccount, Player me, gui.pages.LobbyFX lobbyFX)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                try
                {
                    Block cur = blocks[y][x];
                    cur.setMap(this);
                } catch (NullPointerException e)
                {
                }
            }
        }

        for (MapObject object : objects)
        {
            object.setMap(this);
        }

        for (Player player : players)
        {
            player.setMap(this);
        }

        for (Enemy enemy : enemies)
        {
            enemy.setMap(this);
        }

        playerUpdateThread = Executors.newFixedThreadPool(1);
        this.gameClientToServer = gameClientToServer;
        this.myAccount = myAccount;
        this.me = me;
        this.lobbyFX = lobbyFX;

        players.remove(me);
        players.add(me);

        chatMessages = new ArrayList<>();
    }

    public Block getBlock(int y, int x)
    {
        try
        {
            return blocks[y][x];
        } catch (Exception e)
        {
            return null;
        }
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public List<Enemy> getEnemies()
    {
        return enemies;
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
            enemies.add((Enemy) mo);
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

    public void removeMapObject(int type, int id, int x, int y)
    {
        MapObject removeObject = null;
        switch (type)
        {
            case 1:
                //delete block
                blocks[y][x] = null;
                break;
            case 2:
                //delete enemy

                for (Enemy enemy : enemies)
                {
                    if (enemy.getID() == id)
                    {
                        removeObject = enemy;
                        break;
                    }
                }
                enemies.remove((Enemy) removeObject);
                break;
            case 3:
                // delete player
                for (Player player : players)
                {
                    if (player.getID() == id)
                    {
                        removeObject = player;
                        break;
                    }
                }
                players.remove((Player) removeObject);
                break;
            case 4:
                // delete objects
                for (MapObject object : objects)
                {
                    if (object.getID() == id)
                    {
                        removeObject = object;
                    }
                }
                objects.remove(removeObject);
                break;
        }
    }

    public void updateMapObject(MapObject update)
    {
        if (update instanceof Block)
        {
            try
            {
                update.createSkin();
                blocks[(int) update.getY()][(int) update.getX()] = (Block) update;
            } catch (Exception e)
            {
            }
        } else if (update instanceof Enemy)
        {
            for (Enemy enemy : enemies)
            {
                if (enemy.getID() == update.getID())
                {
                    enemy.update(update);
                    return;
                }
            }
        } else if (update instanceof Player)
        {
            for (Player player : players)
            {
                if (player.getID() == update.getID())
                {
                    if (player.getID() == me.getID())
                    {
                        me.updateHP(((Player) update).getHP());
                        return;
                    }
                    player.update(update);
                    return;
                }
            }
        } else
        {
            for (MapObject object : objects)
            {
                if (object.getID() == update.getID())
                {
                    object.update(update);
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

        for (int y = startY; y < endY; y++)
        {
            for (int x = startX; x < endX; x++)
            {
                try
                {
                    Block cur = blocks[y][x];

                    if (cur != null
                            && (cur.getX() >= startX && cur.getX() + cur.getW() <= endX && cur.getY() >= startY && cur.getY() - cur.getH() <= endY))
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
            if (player.getX() >= startX && player.getX() + player.getW() <= endX && player.getY() >= startY && player.getY() - player.getH() <= endY)
            {
                ret.add(player);
            }
        }

        return ret;
    }

    public void update()
    {
        //Movement
        float oldX = me.getX();
        float oldY = me.getY();
        sides oldSide = me.getDirection();

        me.update();

        int direction = 1;
        if (me.getDirection() == MapObject.sides.LEFT)
        {
            direction = 0;
        }

        if (oldX != me.getX() || oldY != me.getY() || oldSide != me.getDirection())
        {
            try
            {
                gameClientToServer.updatePlayer(lobby.getID(), me.getID(), me.getX(), me.getY(), direction);
            } catch (RemoteException ex)
            {
                System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                Platform.runLater(() ->
                {
                    lobbyFX.connectionLoss();
                });
            }
        }
    }

    public int getLifes()
    {
        return teamlifes;
    }

    public void decreaseLife()
    {
        if (teamlifes > 0)
        {
            teamlifes--;
        }
    }

    public void increaseLife()
    {
        if (teamlifes < 4)
        {
            teamlifes++;
        }
    }

    public void addChatMessage(Message message)
    {
        chatMessages.add(message);
    }

    public List getChatMessages()
    {
        return chatMessages;
    }

    public IGameClientToServer getGameClientToServer()
    {
        return gameClientToServer;
    }

    public void setLifes(int lifes)
    {
        this.teamlifes = lifes;
    }

    public float getGravity()
    {
        return gravity;
    }
    
    public gui.pages.LobbyFX getLobbyFX()
    {
        return lobbyFX;
    }

    public Lobby getLobby()
    {
        return lobby;
    }
}
