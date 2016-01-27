/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.util.List;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.IGameClientToServer;
import thegame.shared.IGameServerToClientListener;

/**
 *
 * @author laure
 */
public class GameServerToClientListener implements IGameServerToClientListener {

    private transient Map map;
    private transient Account myAccount;
    private transient Player me;
    
    private transient IGameClientToServer gameClientToServer;

    public void loadAfterRecieve(Account myAccount, Map map, Player me, IGameClientToServer gameClientToServer) throws RemoteException
    {
        this.myAccount = myAccount;
        this.map = map;
        this.me = me;
        this.gameClientToServer = gameClientToServer;
    }

    @Override
    public void sendGameChatMessage(Message message) throws RemoteException
    {
        map.addChatMessage(message);
    }

    @Override
    public void knockBackPlayer(float hSpeed, float vSpeed) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        me.knockBack(hSpeed, vSpeed);
    }

    @Override
    public void removeMapObject(int id, int type, float x, float y) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        map.removeMapObject(type, id, Math.round(x), Math.round(y));
    }

    @Override
    public void addMapObject(MapObject mo) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        mo.setMap(map);
        mo.setType();
        map.addMapObject(mo);
    }

    @Override
    public void updateObjects(List<MapObject> toSend) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        for (MapObject mo : toSend)
        {
            mo.setMap(map);
            mo.setType();
            map.updateMapObject(mo);
        }
    }

    @Override
    public void addToBackpack(MapObject object, int spot) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        object.setMap(map);
        object.setType();
        if(!me.addToBackpack(object, spot))
        {
            Player meReset = gameClientToServer.resetMe(map.getLobby().getID(), me.getID());
            if(meReset != null)
            {
                me.reset(meReset);
                System.out.println("addToBackpack failed, but The Game managed to reset Player");
            }
            else
            {
                System.out.println("addToBackpack failed, but The Game could not retrieve a backup Player");
            }
        }
    }

    @Override
    public void setTeamLifes(int lifes) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        map.setLifes(lifes);
    }

    @Override
    public void respawnMe() throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        me.spawn();
    }

    @Override
    public void stopGame() throws RemoteException
    {
        if (map == null)
        {
            return;
        }
        map.getLobbyFX().stopGame();
    }

    @Override
    public void equipArmor(Armor equipArmor) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        equipArmor.setMap(map);
        equipArmor.setType();
        me.equipArmor(equipArmor);
    }

    @Override
    public void equipTool(List<MapObject> equipTool) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        for(MapObject object : equipTool)
        {
            object.setMap(map);
            object.setType();
        }
        me.equipTool(equipTool);
    }

    @Override
    public void removeFromBackpack(int spot, int amount) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
        
        me.removeFromBackpack(spot, amount);
    }

    @Override
    public void removeFromBackpack(int spot) throws RemoteException
    {
        if (map == null || me == null)
        {
            return;
        }
       
        me.removeFromBackpack(spot);
    }
}
