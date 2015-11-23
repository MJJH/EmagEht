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

    public UpdateListener(Map map, Account myAccount) throws RemoteException
    {
        this.map = map;
        this.myAccount = myAccount;
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
            float[] playerArray = (float[]) evt.getNewValue();
            int id = (int) playerArray[0];
            float x = playerArray[1];
            float y = playerArray[2];
            updatePlayer(id, x, y);
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

    private void updatePlayer(int id, float x, float y)
    {
        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                player.setCords(x, y);
                return;
            }
        }
    }
}
