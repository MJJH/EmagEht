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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.com.Menu.Message;
import thegame.shared.IGameServerToClientListener;

/**
 *
 * @author laure
 */
public class GameServerToClientHandler {

    private transient LobbyServerToClientHandler lobbyServerToClientHandler;
    private transient LobbyClientToServerHandler lobbyClientToServerHandler;
    private transient GameClientToServerHandler gameClientToServerHandler;
    private transient TheGameServer theGameServer;

    private transient final ConcurrentHashMap<Lobby, Map> gameTable;
    private transient final ConcurrentHashMap<IGameServerToClientListener, Player> playerListenersTable;

    private transient final List<IGameServerToClientListener> connectionLossTable;
    private transient final List<IGameServerToClientListener> isSending;
    private transient ExecutorService threadPoolSend;

    public GameServerToClientHandler()
    {
        gameTable = new ConcurrentHashMap<>();
        playerListenersTable = new ConcurrentHashMap<>();
        connectionLossTable = new ArrayList<>();
        isSending = new ArrayList<>();
        threadPoolSend = Executors.newCachedThreadPool();
    }

    public void registerComponents(LobbyServerToClientHandler lobbyServerToClientHandler, LobbyClientToServerHandler lobbyClientToServerHandler, GameClientToServerHandler gameClientToServerHandler, TheGameServer theGameServer)
    {
        this.lobbyServerToClientHandler = lobbyServerToClientHandler;
        this.lobbyClientToServerHandler = lobbyClientToServerHandler;
        this.gameClientToServerHandler = gameClientToServerHandler;
        this.theGameServer = theGameServer;
    }

    public ConcurrentHashMap<Lobby, Map> getGameTable()
    {
        return gameTable;
    }

    public ConcurrentHashMap<IGameServerToClientListener, Player> getPlayerListenerTable()
    {
        return playerListenersTable;
    }

    public void startNewGame(Lobby lobby)
    {
        Map game = new Map(lobby, this, gameClientToServerHandler);
        gameTable.put(lobby, game);
        lobbyServerToClientHandler.requestConnectToGame(lobby);
        Timer update = new Timer("Game" + Integer.toString(lobby.getID()));
        update.schedule(new TimerTask() {

            @Override
            public void run()
            {
                game.update();
            }
        }, 0, 1000 / 60);
        theGameServer.changeGames(1);
    }

    public void joinPlayer(IGameServerToClientListener listener, Player listenerPlayer)
    {
        playerListenersTable.put(listener, listenerPlayer);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()) + " " + listenerPlayer.getName() + " has joined the game.");
    }

    public void connectionLossPlayer(IGameServerToClientListener listener)
    {
        connectionLossTable.add(listener);
        Player removePlayer = playerListenersTable.get(listener);
        Account removeAccount = removePlayer.getAccount();
        removePlayer.getMap().removeMapObject(removePlayer);
        Lobby lobby = lobbyServerToClientHandler.getAccountsInLobbies().get(removeAccount);
        lobby.leaveLobby(removeAccount);
        lobbyServerToClientHandler.getOnlinePlayers().remove(removeAccount);
        lobbyServerToClientHandler.getAccountsInLobbies().remove(removeAccount);
        playerListenersTable.remove(listener);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()) + " Connection to " + removeAccount.getUsername() + " has been lost");
        theGameServer.changeConnectedPlayer(-1);
    }

    public void sendGameChatMessage(Message message)
    {
        List<Player> toSendTo = gameTable.get(lobbyServerToClientHandler.getAccountsInLobbies().get(message.getSender())).getPlayers();
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();
            if (!toSendTo.contains(player))
            {
                continue;
            }

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
                    connectionLossPlayer(listener);
                }
            });
        }
    }

    public void addMapObject(MapObject mo)
    {
        List<Player> toSendTo = mo.getMap().getPlayers();
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();
            if (!toSendTo.contains(player))
            {
                continue;
            }

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
                    connectionLossPlayer(listener);
                }
            });
        }
    }

    public void removeMapObject(MapObject mo, int type)
    {
        List<Player> toSendTo = mo.getMap().getPlayers();
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();
            if (!toSendTo.contains(player))
            {
                continue;
            }

            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.removeMapObject(mo.getID(), type, mo.getX(), mo.getY());
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not removeMapObject(" + mo.getID() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    connectionLossPlayer(listener);
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
                    connectionLossPlayer(listener);
                }
            });
            break;
        }
    }

    public void updateObjects(List<MapObject> toSend)
    {
        if (toSend.size() < 1)
        {
            return;
        }
        List<Player> toSendTo = toSend.get(0).getMap().getPlayers();
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();
            if (!toSendTo.contains(player))
            {
                continue;
            }

            threadPoolSend.submit(() ->
            {
                try
                {
                    isSending.add(listener);
                    listener.updateObjects(toSend);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not updateObjects(" + toSend.toString() + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    connectionLossPlayer(listener);
                } finally
                {
                    isSending.remove(listener);
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
                    connectionLossPlayer(listener);
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
                    connectionLossPlayer(listener);
                }
            });
            break;
        }
    }

    public void setTeamLifes(Map map, int lifes)
    {
        List<Player> toSendTo = map.getPlayers();
        for (Entry<IGameServerToClientListener, Player> entry : playerListenersTable.entrySet())
        {
            IGameServerToClientListener listener = entry.getKey();
            if (connectionLossTable.contains(listener))
            {
                continue;
            }
            Player player = entry.getValue();
            if (!toSendTo.contains(player))
            {
                continue;
            }
            
            threadPoolSend.submit(() ->
            {
                try
                {
                    listener.setTeamLifes(lifes);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not setTeamLifes(" + lifes + ") to player " + player.getName() + " because:");
                    System.err.println(ex.getMessage());
                    connectionLossPlayer(listener);
                }
            });
        }
    }

    public void respawnPlayer(Player toSendPlayer)
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
                    listener.respawnMe();
                    player.updateHP(-100);
                } catch (RemoteException ex)
                {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("d-M-y HH:mm:ss");
                    System.err.println(sdf.format(cal.getTime()) + " Could not respawn " + toSendPlayer.getName() + " because:");
                    System.err.println(ex.getMessage());
                    connectionLossPlayer(listener);
                }
            });
            break;
        }
    }
}
