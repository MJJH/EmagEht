/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import thegame.BasicPublisher;
import thegame.shared.IRemotePropertyListener;
import thegame.shared.iGameLogic;
import thegame.shared.iMap;

/**
 *
 * @author laure
 */
public class GameLogic extends UnicastRemoteObject implements iGameLogic {

    private iMap map;
    private BasicPublisher publisher;
    private Timer timer;

    public GameLogic() throws RemoteException
    {
        map = new Map();
        //setTimer();
        publisher = new BasicPublisher(new String[]
        {
            "map"
        });
    }
    
    private void setTimer()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run()
            {
                //iMap mapOld = map.
                /*
                for (iMap mapOld : map)
                {
                    map.;
                }
                */
                if (publisher == null)
                {
                    //System.out.println("publisher is null");
                } else
                {
                    System.out.println("publisher updated");
                    publisher.inform(this, "map", null, getMap());
                }
            }
        }, 0, 500);
    }

    @Override
    public iMap getMap()
    {
        return map;
    }

    @Override
    public void addListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(IRemotePropertyListener listener, String property) throws RemoteException
    {
        publisher.removeListener(listener, property);
    }

}
