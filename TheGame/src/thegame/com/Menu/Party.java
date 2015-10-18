package thegame.com.Menu;

/**
 * This class contains code for the party. 
 * @author laure
 */
public class Party {

    private int id;
    private Account[] members;
    private Account owner;
    private Message[] chat;

    /**
     * This method creates a party with the owner as member and owner.
     * @param owner
     */
    public Party(Account owner)
    {
        this.owner = owner;
    }

    /**
     * This method creates a PartyInvite for an account by a sender.
     * @param invite
     * @param sender
     */
    public void sendInvite(Account invite, Account sender)
    {

    }
    
    /**
     * This method creates a message in the party chat.
     * @param sender
     * @param message
     * @return returns true if message is send
     */
    public boolean sendMessage(Account sender, String message)
    {
        return false;
    }
    
    /**
     * This method is used to leave a party. 
     * If the owner is leaving, the party will get a new owner.
     * @param leaveAccount
     * @return returns null if owner is the same, else new owner.
     */
    public Account leaveParty (Account leaveAccount)
    {
        return  null;
    }
}
