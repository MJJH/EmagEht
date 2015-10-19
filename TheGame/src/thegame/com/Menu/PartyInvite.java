package thegame.com.Menu;

import java.util.Date;

/**
 * This class contains code for the Party Invite.
 * 
 * @author laure
 */
public class PartyInvite {

    private int id;
    private Date datetime;
    private Party party;
    private Account account;
    private Account sender;

    /**
     * Creates a new invite with an account, sender and the party.
     * @param account
     * @param party
     * @param sender
     */
    public PartyInvite(Account account, Party party, Account sender)
    {
        this.account = account;
        this.party = party;
        datetime = new Date();
    }
}
