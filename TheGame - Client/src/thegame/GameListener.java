/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import thegame.com.Game.Map;
import thegame.shared.IRemotePropertyListener;

/**
 *
 * @author laure
 */
public class GameListener extends UnicastRemoteObject implements IRemotePropertyListener, Serializable {

    private Map map;

    public GameListener() throws RemoteException
    {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        map = (Map) evt.getNewValue();
    }
    
    public Map getMap()
    {
        return map;
    }
}
