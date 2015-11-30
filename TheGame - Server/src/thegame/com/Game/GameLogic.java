/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.BasicPublisher;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.IRemotePropertyListener;
import thegame.shared.iGameLogic;

/**
 *
 * @author laure
 */
public class GameLogic implements iGameLogic {

    private Map map;
    public BasicPublisher publisher;
    private int mapObjectID;

    public GameLogic() throws RemoteException
    {
        this.mapObjectID = 0;
        publisher = new BasicPublisher(new String[]
        {
            "ServerUpdate"
        });

        map = new Map(publisher, this);
        publisher.registerMap(map);

        Timer update = new Timer("update");
        update.schedule(new TimerTask() {

            @Override
            public void run()
            {
                map.update();
            }
        }, 0, 1000 / 60);
    }

    @Override
    public void addListener(IRemotePropertyListener listener, String property, Player listenerPlayer) throws RemoteException
    {
        publisher.addListener(listener, property, listenerPlayer);
    }

    @Override
    public void removeListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        publisher.removeListener(listener, property);
    }

    @Override
    public int getHeight() throws RemoteException
    {
        return map.getHeight();
    }

    @Override
    public int getWidth() throws RemoteException
    {
        return map.getWidth();
    }

    @Override
    public int getTeamLifes() throws RemoteException
    {
        return map.getTeamLifes();
    }

    @Override
    public int getTime() throws RemoteException
    {
        return map.getTime();
    }

    @Override
    public Array[] getSeasons()
    {
        return map.getSeasons();
    }

    @Override
    public int getLevel()
    {
        return map.getLevel();
    }

    @Override
    public int getSpawnX()
    {
        return map.getSpawnX();
    }

    @Override
    public int getSpawnY()
    {
        return map.getSpawnY();
    }

    @Override
    public List<MapObject> getObjects()
    {
        return map.getObjects();
    }

    @Override
    public List<Enemy> getEnemies()
    {
        return map.getEnemies();
    }

    @Override
    public List<Player> getPlayers()
    {
        return map.getPlayers();
    }

    @Override
    public List<MapObject> getToUpdate()
    {
        return map.getToUpdate();
    }

    @Override
    public List<MapObject> getBlocksAndObjects(int startX, int startY, int endX, int endY) throws RemoteException
    {
        return map.getBlocksAndObjects(startX, startY, endX, endY);
    }

    @Override
    public List<Block> getBlocks() throws RemoteException
    {
        return map.getBlocks();
    }

    @Override
    public Player joinPlayer(Account account) throws RemoteException
    {
        Player player = new Player(null, account.getUsername(), 100, null, null, map.getSpawnX(), map.getSpawnY(), 2, 1, map, this);
        map.addMapObject(player);
        Player toSend = null;
        try
        {
            toSend = (Player) player.clone();
            toSend.setMap(null);
        } catch (CloneNotSupportedException ex)
        {
            Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toSend;
    }

    @Override
    public synchronized void addToUpdate(MapObject update) throws RemoteException
    {
        update.setMap(map);
        try
        {
            MapObject toSend = (MapObject) update.clone();
            toSend.setMap(null);
            map.addToUpdate(update);
            publisher.inform(this, "ServerUpdate", "addToUpdateServer", toSend);
        } catch (CloneNotSupportedException ex)
        {
            Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Map getMap() throws RemoteException
    {
        return map;
    }

    @Override
    public void addObject(MapObject add) throws RemoteException
    {
        add.setMap(map);
        map.addMapObject(add);
    }

    @Override
    public synchronized int getMapObjectID() throws RemoteException
    {
        return mapObjectID++;
    }

    @Override
    public void updateMapObject(MapObject toUpdate) throws RemoteException
    {
        toUpdate.setMap(map);
        try
        {
            MapObject toSend = (MapObject) toUpdate.clone();
            toSend.setMap(null);
            publisher.inform(this, "ServerUpdate", "updateMapObject", toSend);
            map.updateMapObject(toUpdate);
        } catch (CloneNotSupportedException ex)
        {
            Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean useTool(int id, float x, float y) throws RemoteException
    {
        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                return player.useTool(x, y);
            }
        }

        return false;
    }

    @Override
    public void sendMyLoc(int id, float x, float y, int direction) throws RemoteException
    {
        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                if (player.getX() == x && player.getY() == y)
                {
                    return;
                }
                player.setCords(x, y);
                MapObject.sides directionSide = MapObject.sides.RIGHT;
                if (direction == 0)
                {
                    directionSide = MapObject.sides.LEFT;
                }
                player.setDirection(directionSide);
                float[] toSend =
                {
                    id, x, y, direction
                };

                publisher.inform(this, "ServerUpdate", "sendPlayerLoc", toSend);
                return;
            }
        }
    }

    @Override
    public void sendMessage(Message chatMessage) throws RemoteException
    {
        publisher.inform(this, "ServerUpdate", "sendGameChatMessage", chatMessage);
    }
}
