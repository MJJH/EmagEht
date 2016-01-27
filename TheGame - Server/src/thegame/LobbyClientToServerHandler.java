/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
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
    private transient TheGameServer theGameServer;

    public LobbyClientToServerHandler()
    {
    }

    public void registerComponents(LobbyServerToClientHandler lobbyServerToClientHandler, GameServerToClientHandler gameServerToClientHandler, GameClientToServerHandler gameClientToServerHandler, TheGameServer theGameServer)
    {
        this.lobbyServerToClientHandler = lobbyServerToClientHandler;
        this.gameServerToClientHandler = gameServerToClientHandler;
        this.gameClientToServerHandler = gameClientToServerHandler;
        this.theGameServer = theGameServer;
    }

    @Override
    public boolean signIn(Account account, ILobbyServerToClientListener lobbyServerToClientListener)
    {
        if (lobbyServerToClientHandler.getOnlinePlayers().containsKey(account))
        {
            return false;
        }
        lobbyServerToClientHandler.getOnlinePlayers().put(account, lobbyServerToClientListener);
        theGameServer.changeConnectedPlayer(1);
        return true;
    }

    @Override
    public void signOut(Account account)
    {
        lobbyServerToClientHandler.getOnlinePlayers().remove(account);
        theGameServer.changeConnectedPlayer(-1);
    }

    @Override
    public Lobby findNewLobby(Account account) throws RemoteException
    {
        if (lobbyServerToClientHandler.getAccountsInLobbies().containsKey(account))
        {
            return null;
        }
        Lobby newLobby = new Lobby();
        newLobby.joinLobby(account);
        lobbyServerToClientHandler.getLobbies().add(newLobby);
        lobbyServerToClientHandler.getAccountsInLobbies().put(account, newLobby);
        theGameServer.changeLobbies(1);
        return newLobby;
    }

    @Override
    public Lobby findLobby(Account account) throws RemoteException
    {
        if (!lobbyServerToClientHandler.getAccountsInLobbies().containsKey(account))
        {
            for (Lobby lobby : lobbyServerToClientHandler.getLobbies())
            {
                if (lobby.getAccounts().size() < config.minimumRequiredPlayers)
                {
                    lobby.joinLobby(account);
                    lobbyServerToClientHandler.getAccountsInLobbies().put(account, lobby);
                    lobbyServerToClientHandler.updateLobby(lobby);
                    return lobby;
                }
            }
            return findNewLobby(account);
        }
        return null;
    }

    @Override
    public boolean checkReady(Account myAccount) throws RemoteException
    {
        Lobby lobby = lobbyServerToClientHandler.getAccountsInLobbies().get(myAccount);
        boolean returnValue = lobby.setReady(myAccount);
        lobbyServerToClientHandler.updateLobby(lobby);
        if (returnValue && lobby.readyToStart() && !lobby.getGameStarted())
        {
            lobby.setGameStarted(true);
            gameServerToClientHandler.startNewGame(lobby);
        } else if (returnValue && lobby.getGameStarted())
        {
            gameServerToClientHandler.joinPlayer(lobby, myAccount);
            lobbyServerToClientHandler.requestConnectToStartedGame(myAccount);
        }
        return returnValue;
    }

    @Override
    public void quitLobby(Account account) throws RemoteException
    {
        Lobby lobby = lobbyServerToClientHandler.getAccountsInLobbies().remove(account);
        if (lobby != null)
        {
            lobby.leaveLobby(account);
        }
        if(!lobbyServerToClientHandler.getAccountsInLobbies().containsValue(lobby))
        {
            lobbyServerToClientHandler.getLobbies().remove(lobby);
            theGameServer.changeLobbies(-1);
        }
    }
}
