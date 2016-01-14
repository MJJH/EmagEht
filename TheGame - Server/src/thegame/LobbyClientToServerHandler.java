/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.util.HashMap;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.shared.ILobbyClientToServer;
import thegame.shared.ILobbyServerToClientListener;

/**
 *
 * @author laure
 */
public class LobbyClientToServerHandler implements ILobbyClientToServer {

    private transient LobbyServerToClientHandler lobbyServerToClientHandler;
    private transient GameServerToClientHandler gameServerToClientHandler;
    private transient GameClientToServerHandler gameClientToServerHandler;

    public LobbyClientToServerHandler()
    {
    }

    public void registerComponents(LobbyServerToClientHandler lobbyServerToClientHandler, GameServerToClientHandler gameServerToClientHandler, GameClientToServerHandler gameClientToServerHandler)
    {
        this.lobbyServerToClientHandler = lobbyServerToClientHandler;
        this.gameServerToClientHandler = gameServerToClientHandler;
        this.gameClientToServerHandler = gameClientToServerHandler;
    }

    @Override
    public boolean signIn(Account account, ILobbyServerToClientListener lobbyServerToClientListener)
    {
        if (lobbyServerToClientHandler.getOnlinePlayers().containsKey(account))
        {
            return false;
        }
        lobbyServerToClientHandler.getOnlinePlayers().put(account, lobbyServerToClientListener);
        return true;
    }

    @Override
    public Lobby findNewGame(Account account) throws RemoteException
    {
        if (lobbyServerToClientHandler.getLobbies().containsKey(account))
        {
            return null;
        }
        Lobby newLobby = new Lobby();
        newLobby.joinLobby(account);
        lobbyServerToClientHandler.getLobbies().put(account, newLobby);
        return newLobby;
    }

    @Override
    public boolean checkReady(Account myAccount) throws RemoteException
    {
        Lobby lobby = lobbyServerToClientHandler.getLobbies().get(myAccount);
        boolean returnValue = lobby.setReady(myAccount);
        if (returnValue && lobby.readyToStart())
        {
            gameServerToClientHandler.startNewGame(lobby);
        }
        return returnValue;
    }
}
