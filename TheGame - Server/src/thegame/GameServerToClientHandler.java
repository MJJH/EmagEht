/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Message;
import thegame.shared.IGameServerToClientListener;

/**
 *
 * @author laure
 */
public class GameServerToClientHandler {

    private Map map;
    private final HashMap<IGameServerToClientListener, Player> playerListenersTable;

    public GameServerToClientHandler()
    {
        playerListenersTable = new HashMap<>();
    }

    public void registerMap(Map map)
    {
        this.map = map;
    }

    public void joinPlayer(IGameServerToClientListener listener, Player listenerPlayer)
    {
        playerListenersTable.put(listener, listenerPlayer);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()) + " " + listenerPlayer.getName() + " has joined the game.");
    }

    public void leavePlayer(IGameServerToClientListener listener)
    {
        MapObject removePlayer = playerListenersTable.get(listener);
        playerListenersTable.remove(listener);
        map.removeMapObject(removePlayer);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()) + " Connection to " + ((Player) removePlayer).getName() + " has been lost");
    }

    public void sendGameChatMessage(Message message)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            try
            {
                listener.sendGameChatMessage(message);
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                leavePlayer(listener);
            }
        }
    }

    public void updatePlayer(Player toSendPlayer)
    {
        int direction = 1;
        if (toSendPlayer.getDirection() == MapObject.sides.LEFT)
        {
            for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
            {
                IGameServerToClientListener listener = entry.getKey();
                Player player = entry.getValue();

                if (toSendPlayer == player)
                {
                    continue;
                }

                try
                {
                    listener.updatePlayer(toSendPlayer.getID(), toSendPlayer.getX(), toSendPlayer.getY(), direction);
                } catch (RemoteException ex)
                {
                    Logger.getLogger(GameServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    leavePlayer(listener);
                }
            }
            direction = 0;
        }

    }

    public void addMapObject(MapObject mo)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            try
            {
                listener.addMapObject(mo);
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                leavePlayer(listener);
            }
        }
    }

    public void removeMapObject(int id, int type, float x, float y)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            try
            {
                listener.removeMapObject(id, type, x, y);
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                leavePlayer(listener);
            }
        }
    }

    public void updateHealthPlayer(Player toSendPlayer)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            try
            {
                listener.updateHealthPlayer(toSendPlayer.getID(), toSendPlayer.getHP());
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                leavePlayer(listener);
            }
        }
    }

    public void knockBackPlayer(Player toSendPlayer, float hSpeed, float vSpeed)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            Player player = entry.getValue();

            if (toSendPlayer != player)
            {
                continue;
            }

            try
            {
                listener.knockBackPlayer(hSpeed, vSpeed);
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                leavePlayer(listener);
            }
            break;
        }
    }

    public void updateObjects(List<MapObject> toSend)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            try
            {
                listener.updateObjects(toSend);
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                leavePlayer(listener);
            }
        }
    }
}
