package thegame.com.Menu;

/**
 *
 * @author laure
 */
public class Party {

    private int id;
    private Account[] members;
    private Account owner;
    private Message[] chat;

    /**
     *
     * @param owner
     */
    public Party(Account owner)
    {
        this.owner = owner;
    }

    /**
     *
     * @param account
     */
    public void sendInvite(Account account)
    {

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
}
