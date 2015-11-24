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
import thegame.com.Game.Objects.Characters.Player;
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
    private Player me;

    public UpdateListener(Map map, Account myAccount) throws RemoteException
    {
        this.map = map;
        this.myAccount = myAccount;
        this.me = me;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        if (evt.getOldValue() == null || !(evt.getOldValue() instanceof String))
        {
            return;
        }
        if (!(evt.getNewValue() instanceof MapObject))
        {
            switch((String) evt.getOldValue()){
                case "sendPlayerLoc":
                    updatePlayer((float[]) evt.getNewValue());
                    break;
                case "removeMapObject":
                    removeMapObject((int[]) evt.getNewValue());
                    break;
            }
            return;
        }
        MapObject toChange = (MapObject) evt.getNewValue();
        toChange.setMap(map);

        if (toChange.getSkin() == null)
        {
            toChange.createSkin();
        }

        switch ((String) evt.getOldValue())
        {
            case "addMapObject":
                map.addMapObject(toChange);
                break;
            case "removeMapObject":
                map.removeMapObject(toChange);
                break;
            case "updateMapObject":
                map.updateMapObject(toChange);
                break;
        }
    }

    private void updatePlayer(float [] playerArray)
    {
        int id = (int) playerArray[0];
        float x = playerArray[1];
        float y = playerArray[2];

        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                player.setCords(x, y);
                return;
            }
        }
    }

    private void removeMapObject(int[] a)
    {
        int type = a[0];
        int id = a[1];
        int x = a[2];
        int y = a[3];
        
        map.removeMapObject(type, id, x, y);
    }
}
