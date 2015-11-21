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
import thegame.BasicPublisher;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.shared.IRemotePropertyListener;
import thegame.shared.iGameLogic;

/**
 *
 * @author laure
 */
public class GameLogic extends UnicastRemoteObject implements iGameLogic {

    private Map map;
    public static BasicPublisher publisher;
    private int mapObjectID;

    public GameLogic() throws RemoteException
    {
        this.mapObjectID = 0;
        publisher = new BasicPublisher(new String[]
        {
            "ServerUpdate"
        });

        map = new Map(publisher, this);
        
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
    public void addListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        publisher.addListener(listener, property);
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
        Player player = new Player(null, account.getUsername(), 100, null, null, map.getSpawnX(), map.getSpawnY(), null, 1, 1, map, this);
        map.addObject(player);
        return player;
    }

    @Override
    public synchronized void addToUpdate(MapObject update) throws RemoteException
    {
        map.addToUpdate(update);
        publisher.inform(this, "ServerUpdate", "addToUpdateServer", update);
    }

    @Override
    public Map getMap() throws RemoteException
    {
        return map;
    }

    @Override
    public void addObject(MapObject add) throws RemoteException
    {
        map.addObject(add);
    }

    @Override
    public synchronized int getMapObjectID() throws RemoteException
    {
        return mapObjectID++;
    }
}
