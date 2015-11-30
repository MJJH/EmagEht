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
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.IRemotePropertyListener;

/**
 *
 * @author laure
 */
public class UpdateListener extends UnicastRemoteObject implements IRemotePropertyListener, Serializable {

    private Map map;
    private Account myAccount;
    private Player me;

    public UpdateListener(Map map, Account myAccount, Player me) throws RemoteException
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
        if (evt.getNewValue() instanceof Message)
        {
            Message chatMessage = (Message) evt.getNewValue();
            
            switch ((String) evt.getOldValue())
            {
                case "sendGameChatMessage":
                    System.out.println(chatMessage.getSender().getUsername() + ": " + chatMessage.getText());
                    break;
            }
        }
        else if (evt.getNewValue() instanceof MapObject)
        {
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
        } else if (!(evt.getNewValue() instanceof MapObject))
        {
            switch ((String) evt.getOldValue())
            {
                case "sendPlayerLoc":
                    updatePlayer((float[]) evt.getNewValue());
                    break;
                case "removeMapObject":
                    removeMapObject((int[]) evt.getNewValue());
                    break;
                case "updateHP":
                    updateHP((int[]) evt.getNewValue());
                    break;
                case "respawnPlayer":
                    respawnPlayer((float[]) evt.getNewValue());
                    break;
                case "knockBackPlayer":
                    knockBackPlayer((float[]) evt.getNewValue());
                    break;
            }
        }
    }

    private void updatePlayer(float[] playerArray)
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

    private void updateHP(int[] i)
    {
        int type = i[0];
        int id = i[1];
        int hp = i[2];

        switch (type)
        {
            case 1:
                //Player
                for (Player player : map.getPlayers())
                {
                    if (player.getID() == id)
                    {
                        player.updateHP(hp);
                        return;
                    }
                }
                break;
            case 2:
                //Enemy
                for (Enemy enemy : map.getEnemies())
                {
                    if (enemy.getID() == id)
                    {
                        enemy.updateHP(hp);
                        return;
                    }
                }
                break;
        }
    }

    private void respawnPlayer(float[] f)
    {
        int id = Math.round(f[0]);
        float x = f[1];
        float y = f[2];

        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                player.setCords(x, y);
                if (player == me)
                {
                    map.addToUpdate(player);
                }
                return;
            }
        }
    }

    private void knockBackPlayer(float[] f)
    {
        int id = Math.round(f[0]);
        float hSpeed = f[1];
        float vSpeed = f[2];

        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
            {
                player.knockBack(hSpeed, vSpeed);
                return;
            }
        }
    }
}
