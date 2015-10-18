package thegame.com.Menu;

import thegame.com.Game.Map;

/**
 * This class contains code for the game lobby.
 * The game lobby contains messages, map and players
 * @author laure
 */
public class Lobby {

    private int id;
    private Message[] chat;
    private Map plays;
    private Account[] accounts;

    /**
     * This method creates a new gamelobby.
     */
    public Lobby()
    {
        // TODO - implement Lobby.Lobby
        throw new UnsupportedOperationException();
    }

    /**
     * This method creates a new message in the lobby chat.
     * @param sender
     * @param message
     * @return Returns true if send, else false
     */
    public boolean sendMessage(Account sender, String message)
    {
        return false;
    }

    /**
     * This method adds an account to a lobby.
     * There cannot be more than 4 players (accounts)
     * @param account
     * @return Returns true if joined, else false.
     */
    public boolean joinLobby(Account account)
    {
        return false;
    }

    /**
     * This method generates a map.
     * @return Returns true if a map is generated, else false.
     */
    public boolean generateMap()
    {
        return false;
    }
}
