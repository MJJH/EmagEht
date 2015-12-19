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
import javafx.application.Platform;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.MapObject.sides;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.IGameClientToServer;

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
    private transient IGameClientToServer gameClientToServer;
    private transient thegame.TheGame theGame;
    private transient List<Message> chatMessages;

    public void loadAfterRecieve(IGameClientToServer gameClientToServer, Account myAccount, Player me, thegame.TheGame theGame)
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
        this.theGame = theGame;

        players.remove(me);
        players.add(me);

        chatMessages = new ArrayList<>();
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

    /*public void removeMapObject(MapObject removeObject)
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
     enemies.remove((Enemy) removeObject);
     } else if (removeObject instanceof Player)
     {
     players.remove((Player) removeObject);
     }
     }*/
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
                        me.updateHP(player.getHP());
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
                            && (cur.getX() + cur.getW() >= startX && cur.getX() <= endX && cur.getY() - cur.getH() >= startY && cur.getY() <= endY))
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
                gameClientToServer.updatePlayer(me.getID(), me.getX(), me.getY(), direction);
            } catch (RemoteException ex)
            {
                System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                Platform.runLater(() ->
                {
                    theGame.connectionLoss();
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
        theGame.chatNotiifcation();
    }

    public List getChatMessages()
    {
        return chatMessages;
    }

    public IGameClientToServer getGameClientToServer()
    {
        return gameClientToServer;
    }

    public thegame.TheGame getTheGame()
    {
        return theGame;
    }

    public void setLifes(int lifes)
    {
        this.teamlifes = lifes;
    }
}
