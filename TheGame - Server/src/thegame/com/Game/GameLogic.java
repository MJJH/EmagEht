/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import thegame.BasicPublisher;
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

    private transient Map map;
    public transient BasicPublisher publisher;
    private transient int mapObjectID;

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
    public Player joinPlayer(Account account) throws RemoteException
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()) + " " + account.getUsername() + " is trying to join.");
        Player player = new Player(null, account.getUsername(), 100, null, null, map.getSpawnX(), map.getSpawnY(), 2, 1, map, this);
        map.addMapObject(player);
        return player;
    }

    @Override
    public synchronized void addToUpdate(MapObject update) throws RemoteException
    {
        update.setMap(map);
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
        publisher.inform(this, "ServerUpdate", "updateMapObject", toUpdate);
        map.updateMapObject(toUpdate);
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
