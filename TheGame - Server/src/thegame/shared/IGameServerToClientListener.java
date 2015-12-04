/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Message;

/**
 *
 * @author laure
 */
public interface IGameServerToClientListener extends Remote, EventListener {

    public void sendGameChatMessage(Message message) throws RemoteException;

    public void updatePlayer(int id, float x, float y, int direction) throws RemoteException;

    public void updateHealthPlayer(int id, int newHealth) throws RemoteException;

    public void knockBackPlayer(float hSpeed, float vSpeed) throws RemoteException;

    public void removeMapObject(int id, int type, float x, float y) throws RemoteException;

    public void addMapObject(MapObject mo) throws RemoteException;
}
