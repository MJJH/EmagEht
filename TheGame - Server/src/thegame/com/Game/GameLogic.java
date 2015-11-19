/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import thegame.BasicPublisher;
import thegame.shared.IRemotePropertyListener;
import thegame.shared.iGameLogic;
import thegame.shared.iMap;

/**
 *
 * @author laure
 */
public class GameLogic extends UnicastRemoteObject implements iGameLogic {

    private iMap[] map;
    private BasicPublisher publisher;

    public GameLogic() throws RemoteException
    {
        map = new iMap[]
        {
            new Map()
        };

        publisher = new BasicPublisher(new String[]
        {
            "map"
        });
    }

    @Override
    public iMap[] getMap() throws RemoteException
    {
        return map;
    }

    @Override
    public void addListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        publisher.addListener(listener, property);
        publisher.inform(this, property, null, getMap());
    }

    @Override
    public void removeListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        publisher.removeListener(listener, property);
    }

}
