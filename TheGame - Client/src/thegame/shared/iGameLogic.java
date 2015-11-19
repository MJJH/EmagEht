/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.RemoteException;

/**
 *
 * @author laure
 */
public interface iGameLogic extends IRemotePublisher{
    iMap[] getMap() throws RemoteException;
}
