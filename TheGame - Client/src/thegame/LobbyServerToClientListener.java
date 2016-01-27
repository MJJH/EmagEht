/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import gui.pages.LobbyFX;
import java.rmi.RemoteException;
import javafx.application.Platform;
import thegame.com.Menu.Lobby;
import thegame.shared.ILobbyServerToClientListener;


/**
 *
 * @author laure
 */
public class LobbyServerToClientListener implements ILobbyServerToClientListener{
    
    private transient LobbyFX lobbyFX;

    public LobbyServerToClientListener()
    {
    }
    
    public void setLobbyFX(LobbyFX lobbyFX)
    {
        this.lobbyFX = lobbyFX;
    }
    
    @Override
    public void requestConnectToGame() throws RemoteException
    {
        Platform.runLater(lobbyFX::connectToGame);
    }

    @Override
    public void updateLobby(Lobby lobby) throws RemoteException
    {
        if(lobbyFX != null && lobby != null)
        {
            lobbyFX.setLobby(lobby);
        }
    }
}
