package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import thegame.com.Game.Objects.Characters.Player;

/**
 *
 * @author Gebruiker
 */
public interface IRemotePublisher extends Remote {

    /**
     * Subscribe a listener to a property
     *
     * @param listener Object implementing IRemotePropertyListener that wants to
     * subscribe to a property
     * @param property Property that listener wants to subscribe to
     * @param listenerPlayer
     * @throws RemoteException
     */
    void addListener(IRemotePropertyListener listener, String property, Player listenerPlayer) throws RemoteException;

    /**
     * Unsubscribe a listener from a property
     *
     * @param listener Object implementing IRemotePropertyListener that wants to
     * unsubscribe from a property
     * @param property Property that the listener wants to unsubscribe from
     * @throws RemoteException
     */
    void removeListener(IRemotePropertyListener listener, String property) throws RemoteException;
}
