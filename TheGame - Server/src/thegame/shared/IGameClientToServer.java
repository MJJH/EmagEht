/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;

/**
 *
 * @author laure
 */
public interface IGameClientToServer extends Remote {

    public Player joinPlayer(Account account, IGameServerToClientListener listener) throws RemoteException;

    public void leavePlayer(IGameServerToClientListener listener) throws RemoteException;

    public Map getMap() throws RemoteException;

    public void sendGameChatMessage(Message message) throws RemoteException;

    public void updatePlayer(int id, float x, float y, int direction) throws RemoteException;

    public boolean useTool(int id, float x, float y) throws RemoteException;
    
    public void pickUpParticle (int particleID, float particleX, float particleY, int playerID) throws RemoteException;
}
