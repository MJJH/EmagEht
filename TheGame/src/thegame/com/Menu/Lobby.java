package thegame.com.Menu;

import java.util.ArrayList;
import thegame.com.Game.Map;

/**
 * This class contains code for the game lobby. The game lobby contains
 * messages, map and players
 *
 * @author laure
 */
public class Lobby {

    private int id;
    private ArrayList<Message> chat;
    private Map plays;
    private ArrayList<Account> accounts;

    /**
     * This method creates a new gamelobby.
     */
    public Lobby()
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
        if(!message.isEmpty())
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
    public boolean generateMap()
    {
        plays = new Map();
    }
}
