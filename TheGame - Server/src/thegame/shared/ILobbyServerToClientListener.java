/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import thegame.com.Menu.Lobby;

/**
 *
 * @author laure
 */
public interface ILobbyServerToClientListener extends Remote{

    public void requestConnectToGame() throws RemoteException;

    public void updateLobby(Lobby lobby) throws RemoteException;
    
}
