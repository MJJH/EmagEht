package thegame.com.Menu;

import thegame.com.Game.Map;

/**
 *
 * @author laure
 */
public class Lobby {

    private int id;
    private Message[] chat;
    private Map plays;
    private Account[] accounts;

    /**
     * This class implements the Game Lobby
     */
    public Lobby()
    {
        // TODO - implement Lobby.Lobby
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param sender
     * @param message
     * @return
     */
    public boolean sendMessage(Account sender, String message)
    {
        return false;
    }

    /**
     *
     * @param account
     * @return
     */
    public boolean joinLobby(Account account)
    {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean generateMap()
    {
        return false;
    }
}
