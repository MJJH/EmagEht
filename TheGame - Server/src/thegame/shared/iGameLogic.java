/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import thegame.com.Game.Map;

/**
 *
 * @author laure
 */
public interface iGameLogic extends IRemotePublisher{
    Map getMap() throws RemoteException;
}
