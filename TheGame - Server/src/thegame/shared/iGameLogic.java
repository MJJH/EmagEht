/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

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
public interface iGameLogic extends IRemotePublisher {

    public Player joinPlayer(Account account) throws RemoteException;

    public void addToUpdate(MapObject update) throws RemoteException;

    public Map getMap() throws RemoteException;

    public void addObject(MapObject add) throws RemoteException;

    public int getMapObjectID() throws RemoteException;

    public void updateMapObject(MapObject toUpdate) throws RemoteException;

    public boolean useTool(int id, float x, float y) throws RemoteException;

    public void sendMyLoc(int id, float x, float y, int direction) throws RemoteException;

    public void sendMessage(Message chatMessage) throws RemoteException;
}
