/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.shared.ILobbyServerToClientListener;

/**
 *
 * @author laure
 */
public class LobbyServerToClientHandler {

    private transient LobbyClientToServerHandler lobbyClientToServerHandler;
    private transient GameServerToClientHandler gameServerToClientHandler;
    private transient GameClientToServerHandler gameClientToServerHandler;

    private final transient HashMap<Account, ILobbyServerToClientListener> onlinePlayers;
    private final transient HashMap<Account, Lobby> lobbies;

    public LobbyServerToClientHandler()
    {
        this.onlinePlayers = new HashMap<>();
        this.lobbies = new HashMap<>();
    }

    public void registerComponents(LobbyClientToServerHandler lobbyClientToServerHandler, GameServerToClientHandler gameServerToClientHandler, GameClientToServerHandler gameClientToServerHandler)
    {
        this.lobbyClientToServerHandler = lobbyClientToServerHandler;
        this.gameServerToClientHandler = gameServerToClientHandler;
        this.gameClientToServerHandler = gameClientToServerHandler;
    }

    public HashMap<Account, ILobbyServerToClientListener> getOnlinePlayers()
    {
        return onlinePlayers;
    }

    public HashMap<Account, Lobby> getLobbies()
    {
        return lobbies;
    }

    public void requestConnectToGame(Lobby lobby)
    {
        ArrayList<Account> accountsInLobby = new ArrayList<>();
        for (Map.Entry<Account, Lobby> entry : lobbies.entrySet())
        {
            if (entry.getValue().equals(lobby))
            {
                accountsInLobby.add(entry.getKey());
            }
        }
        for (Account account : accountsInLobby)
        {
            try
            {
                onlinePlayers.get(account).requestConnectToGame();
            } catch (RemoteException ex)
            {
                Logger.getLogger(LobbyServerToClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
