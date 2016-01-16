package thegame.com.Menu;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import thegame.com.Game.Map;
import thegame.config;
import thegame.com.Game.Objects.Characters.Character;

/**
 * This class contains code for the game lobby. The game lobby contains
 * messages, map and players
 *
 * @author laure
 */
public class Lobby implements Serializable{

    private static final long serialVersionUID = 6529682158264757690L;
    private transient static int idCounter = 1;
    private int id;
    private ArrayList<Message> chat;
    private Map plays;
    private ArrayList<Account> accounts;
    private ArrayList<Account> ready;
    private HashMap<Account, Character> chosenCharacters;
    private boolean started;

    /**
     * This method creates a new gamelobby.
     */
    public Lobby()
    {
        id = idCounter++;
        chat = new ArrayList();
        accounts = new ArrayList();
        ready = new ArrayList<>();
        chosenCharacters = new HashMap<>();
        started = false;
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
                chosenCharacters.put(account, new Character("test", 0));
                return true;
            }
        }

        return false;
    }

    /**
     * This method generates a map.
     *
     * @throws java.rmi.RemoteException
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

    public boolean setReady(Account account)
    {
        if(ready.contains(account) || !chosenCharacters.containsKey(account))
        {
            return false;
        }
        ready.add(account);
        return true;
    }
    
    public boolean setNotReady (Account account)
    {
        if(!ready.contains(account))
        {
            return false;
        }
        ready.remove(account);
        return true;
    }

    public boolean readyToStart()
    {
        return started ||(accounts.size() == config.minimumRequiredPlayers && ready.containsAll(accounts));
    }
    
    public ArrayList<Character> getCharacters()
    {
        ArrayList<Character> characters = new ArrayList<>();
        for(Account account : accounts)
        {
            characters.add(chosenCharacters.get(account));
        }
        return characters;
    }
    
    public HashMap<Account,Character> getChosenCharacters()
    {
        return chosenCharacters;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Lobby)
        {
            Lobby lobby = (Lobby) o;
            return id == lobby.getID();
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    public void leaveLobby(Account removeAccount)
    {
        accounts.remove(removeAccount);
        ready.remove(removeAccount);
        chosenCharacters.remove(removeAccount);
    }

    public void setGameStarted(boolean b)
    {
        started = b;
    }
    
    public boolean getGameStarted()
    {
        return started;
    }
}
