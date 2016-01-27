/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;

/**
 *
 * @author laure
 */
public interface ILobbyClientToServer extends Remote {

    public boolean signIn(Account account, ILobbyServerToClientListener lobbyServerToClientListener) throws RemoteException;
    
    public void signOut(Account account) throws RemoteException;

    public Lobby findNewLobby(Account account) throws RemoteException;

    public Lobby findLobby(Account account) throws RemoteException;

    public boolean checkReady(Account myAccount) throws RemoteException;

    public void quitLobby(Account account) throws RemoteException;

    public Lobby getLobby(Account myAccount) throws RemoteException;
}
