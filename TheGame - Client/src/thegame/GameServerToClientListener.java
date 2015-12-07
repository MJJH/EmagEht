/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.util.List;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.IGameServerToClientListener;

/**
 *
 * @author laure
 */
public class GameServerToClientListener implements IGameServerToClientListener {

    private transient Map map;
    private transient Account myAccount;
    private transient Player me;
    
    public void loadAfterRecieve(Account myAccount, Map map, Player me) throws RemoteException
    {
        this.myAccount = myAccount;
        this.map = map;
        this.me = me;
    }

    @Override
    public void sendGameChatMessage(Message message) throws RemoteException
    {
        map.addChatMessage(message);
    }

    @Override
    public void updatePlayer(int id, float x, float y, int direction) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        MapObject.sides directionSide = MapObject.sides.RIGHT;
        if (direction == 0)
        {
            directionSide = MapObject.sides.LEFT;
        }

        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                player.setCords(x, y);
                player.setDirection(directionSide);
                return;
            }
        }
    }

    @Override
    public void updateHealthPlayer(int id, int newHealth) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                player.updateHP(newHealth);
                return;
            }
        }
    }

    @Override
    public void knockBackPlayer(float hSpeed, float vSpeed) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        me.knockBack(hSpeed, vSpeed);
    }

    @Override
    public void removeMapObject(int id, int type, float x, float y) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        map.removeMapObject(type, id, Math.round(x), Math.round(y));
    }

    @Override
    public void addMapObject(MapObject mo) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        map.addMapObject(mo);
    }

    @Override
    public void updateObjects(List<MapObject> toSend) throws RemoteException
    {
        for(MapObject mo : toSend)
        {
            map.updateMapObject(mo);
        }
    }
}