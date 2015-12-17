package thegame.com.Menu;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import thegame.com.Game.Map;

/**
 * This class contains code for the game lobby. The game lobby contains
 * messages, map and players
 *
 * @author laure
 */
public class Lobby extends UnicastRemoteObject {

    private int id;
    private ArrayList<Message> chat;
    private Map plays;
    private ArrayList<Account> accounts;

    /**
     * This method creates a new gamelobby.
     */
    public Lobby() throws RemoteException
    {
        // TODO - implement Lobby.Lobby
        chat = new ArrayList();
        accounts = new ArrayList();
    }

    /**
     * This method creates a new message in the lobby chat.
     *
     * @param sender
     * @param message
     * @return Returns true if send, else false
     */
    public boolean sendMessage(Account sender, String message)
    {
        if (!message.isEmpty())
        {
            chat.add(new Message(sender, message));
            return true;
        }

        return false;
    }

    /**
     * This method adds an account to a lobby. There cannot be more than 4
     * players (accounts)
     *
     * @param account
     * @return Returns true if joined, else false.
     */
    public boolean joinLobby(Account account)
    {
        if (accounts.size() < 4)
        {
            if (!accounts.contains(account))
            {
                accounts.add(account);
                return true;
            }
        }

        return false;
    }

    /**
     * This method generates a map.
     *
     */
    public void generateMap() throws RemoteException
    {
        //plays = new Map();
    }

    /**
     * This method gets chat messages
     *
     * @return messages in chat
     */
    public ArrayList<Message> getChat()
    {
        return chat;
    }

    /**
     * This method gets all joined players
     *
     * @return accounts in lobby
     */
    public ArrayList<Account> getAccounts()
    {
        return accounts;
    }

    /**
     * This method gets the map
     *
     * @return game map
     */
    public Map getMap()
    {
        return plays;
    }

    /**
     * This method gets the lobby ID
     *
     * @return lobby ID
     */
    public int getID()
    {
        return id;
    }
}
