package thegame.com.Menu;

import java.util.Date;

/**
 *
 * @author laure
 */
public class PartyInvite {

    private int id;
    private Date datetime;
    private Party party;
    private Account account;

    /**
     *
     * @param account
     * @param party
     */
    public PartyInvite(Account account, Party party)
    {
        this.account = account;
        this.party = party;
        datetime = new Date();
    }

    /**
     *
     * @return party
     */
    public Party accept()
    {
        // TODO - implement PartyInvite.accept
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    public void decline()
    {
        // TODO - implement PartyInvite.decline
        throw new UnsupportedOperationException();
    }

}
