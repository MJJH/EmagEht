package thegame.com.Menu;

import java.io.Serializable;
import java.util.Date;

/**
 * This class contains code for the Party Invite.
 *
 * @author laure
 */
public class PartyInvite implements Serializable {

    private final Date datetime;
    private final Party party;
    private final Account account;
    private final Account sender;

    /**
     * Creates a new invite with an account, sender and the party.
     *
     * @param account
     * @param party
     * @param sender
     */
    public PartyInvite(Account account, Party party, Account sender)
    {
        this.account = account;
        this.party = party;
        datetime = new Date();
        this.sender = sender;
    }

    /**
     * This method gets the party invite date.
     *
     * @return date
     */
    public Date getDate()
    {
        return datetime;
    }

    /**
     * This method gets the party.
     *
     * @return party
     */
    public Party getParty()
    {
        return party;
    }

    /**
     * This method gets the player that is invited.
     *
     * @return account
     */
    public Account getAccount()
    {
        return account;
    }

    /**
     * This method gets the player that send the invitation.
     *
     * @return account
     */
    public Account getSender()
    {
        return sender;
    }
}
