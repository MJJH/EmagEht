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
import thegame.shared.IRemotePropertyListener;
import thegame.shared.iGameLogic;

/**
 *
 * @author laure
 */
public class GameLogic extends UnicastRemoteObject implements iGameLogic {

    private Map map;
    private BasicPublisher publisher;
    private Timer timer;

    public GameLogic() throws RemoteException
    {
        map = new Map();
        //setTimer();
        publisher = new BasicPublisher(new String[]
        {
            "map"
        });
    }
    
    private void setTimer()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run()
            {
                //iMap mapOld = map.
                /*
                for (iMap mapOld : map)
                {
                    map.;
                }
                */
                if (publisher == null)
                {
                    //System.out.println("publisher is null");
                } else
                {
                    System.out.println("publisher updated");
                    //publisher.inform(this, "map", null, getMap());
                }
            }
        }, 0, 500);
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
}
