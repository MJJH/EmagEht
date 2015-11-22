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
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.shared.IRemotePropertyListener;

/**
 *
 * @author laure
 */
public class UpdateListener extends UnicastRemoteObject implements IRemotePropertyListener, Serializable {

    private Map map;
    private Account myAccount;

    public UpdateListener(Map map, Account myAccount) throws RemoteException
    {
        this.map = map;
        this.myAccount = myAccount;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        if(evt.getOldValue() == null || !(evt.getOldValue() instanceof String))
        {
            return;
        }
        switch((String)evt.getOldValue()){
            case "addMapObject":
                map.addMapObject((MapObject)evt.getNewValue());
                break;
            case "removeMapObject":
                map.removeMapObject((MapObject)evt.getNewValue());
                break;
            case "updateMapObject":
                map.updateMapObject((MapObject)evt.getNewValue());
                break;
        }
        
    }
}
