/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import thegame.shared.ILobbyClientToServer;

/**
 *
 * @author laure
 */
public class LobbyClientToServerHandler implements ILobbyClientToServer{
    private final transient LobbyServerToClientHandler lobbyServerToClientHandler;
    
    public LobbyClientToServerHandler(LobbyServerToClientHandler lobbyServerToClientHandler)
    {
        this.lobbyServerToClientHandler = lobbyServerToClientHandler;
    }
    
}
