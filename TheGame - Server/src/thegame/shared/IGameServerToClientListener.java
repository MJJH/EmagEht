/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Message;

/**
 *
 * @author laure
 */
public interface IGameServerToClientListener extends Remote {

    public void sendGameChatMessage(Message message) throws RemoteException;

    public void knockBackPlayer(float hSpeed, float vSpeed) throws RemoteException;

    public void removeMapObject(int id, int type, float x, float y) throws RemoteException;

    public void addMapObject(MapObject mo) throws RemoteException;

    public void updateObjects(List<MapObject> toSend) throws RemoteException;

    public void addToBackpack(MapObject object, int spot) throws RemoteException;

    public void setTeamLifes(int lifes) throws RemoteException;

    public void respawnMe() throws RemoteException;

    public void stopGame() throws RemoteException;
}
