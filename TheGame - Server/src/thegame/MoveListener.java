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
import thegame.shared.IRemotePropertyListener;

/**
 *
 * @author Martijn
 */
public class MoveListener extends UnicastRemoteObject implements IRemotePropertyListener, Serializable {

    public MoveListener() throws RemoteException {
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        System.err.println("Is trying the move");
    }
}
