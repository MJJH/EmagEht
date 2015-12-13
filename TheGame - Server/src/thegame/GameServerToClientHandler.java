/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    private transient Map map;
    private transient final ConcurrentHashMap<IGameServerToClientListener, Player> playerListenersTable;
    private transient final List<IGameServerToClientListener> connectionLossTable;
    private transient final ConcurrentHashMap<IGameServerToClientListener, String> isSending;
    private transient ExecutorService threadPoolSend;

    public GameServerToClientHandler()
    {
        playerListenersTable = new ConcurrentHashMap<>();
        connectionLossTable = new ArrayList<>();
        isSending = new ConcurrentHashMap<>();
        threadPoolSend = Executors.newCachedThreadPool();
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
        connectionLossTable.add(listener);
        map.removeMapObject(removePlayer);
        playerListenersTable.remove(listener);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()) + " Connection to " + ((Player) removePlayer).getName() + " has been lost");
    }

    public void sendGameChatMessage(Message message)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();

            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.sendGameChatMessage(message);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not sendGameChatMessage(" + message.getSender().getUsername() + ": " + message.getText() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                }
            });
        }
    }

    public void updatePlayer(Player toSendPlayer)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener) || (isSending.contains(listener) && isSending.get(listener).equals("updatePlayer")))
            {
                continue;
            }
            Player player = entry.getValue();

            if (toSendPlayer == player)
            {
                continue;
            }

            threadPoolSend.submit(() ->
            {
                int direction = 1;
                if (toSendPlayer.getDirection() == MapObject.sides.LEFT)
                {
                    direction = 0;
                }
                try
                {
                    isSending.put(listener, "updatePlayer");
                    listener.updatePlayer(toSendPlayer.getID(), toSendPlayer.getX(), toSendPlayer.getY(), direction);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not updatePlayer(" + toSendPlayer.getName() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                } finally
                {
                    isSending.remove(listener, "updatePlayer");
                }
            });
        }
    }

    public void addMapObject(MapObject mo)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();

            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.addMapObject(mo);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not addMapObject(" + mo.getID() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                }
            });
        }
    }

    public void removeMapObject(int id, int type, float x, float y)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();
            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.removeMapObject(id, type, x, y);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not removeMapObject(" + id + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                }
            });
        }
    }

    public void updateHealthPlayer(Player toSendPlayer)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();
            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.updateHealthPlayer(toSendPlayer.getID(), toSendPlayer.getHP());
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not updateHealthPlayer(" + toSendPlayer.getName() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                }
            });
        }
    }

    public void knockBackPlayer(Player toSendPlayer, float hSpeed, float vSpeed)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();

            if (toSendPlayer != player)
            {
                continue;
            }

            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.knockBackPlayer(hSpeed, vSpeed);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not knockBackPlayer(" + toSendPlayer.getName() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                }
            });
            break;
        }
    }

    public void updateObjects(List<MapObject> toSend)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener) || (isSending.contains(listener) && isSending.get(listener).equals("updateObjects")))
            {
                continue;
            }
            Player player = entry.getValue();
            threadPoolSend.submit(() ->
            {
                try
                {
                    isSending.put(listener, "updateObjects");
                    listener.updateObjects(toSend);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not updateObjects(" + toSend.toString() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                } finally
                {
                    isSending.remove(listener, "updateObjects");
                }
            });
        }
    }

    public void addToBackpack(MapObject object, int spot, Player toSendPlayer)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();

            if (toSendPlayer != player)
            {
                continue;
            }

            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.addToBackpack(object, spot);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not addToBackpack(" + object.getClass() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                }
            });
            break;
        }
    }

    public void addToEmptyBackpack(MapObject object, Player toSendPlayer)
    {
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();

            if (toSendPlayer != player)
            {
                continue;
            }

            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.addToEmptyBackpack(object);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not addToEmptyBackpack(" + object.getClass() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    leavePlayer(listener);
                }
            });
            break;
        }
    }
}
