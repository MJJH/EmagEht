package thegame.com.Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Contains methods to create accounts
 *
 * @author robin
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 5539422509825657690L;

    private Party party;
    private int id;
    private final String name;
    private String password;
    private final ArrayList<FriendRequest> requests;
    private final ArrayList<Account> friends;
    private final ArrayList<PartyInvite> invites;

    /**
     * Creates a new account with a name and password
     *
     * @param id : the new id of the account
     * @param name : the name of the account
     * @param password : the password of the account
     */
    public Account(int id, String name, String password)
    {
        this.id = id;
        this.name = name;
        this.password = password;
        requests = new ArrayList<>();
        friends = new ArrayList<>();
        invites = new ArrayList<>();
    }

    /**
     * Creates a new account with only a name
     *
     * @param name : the name of the account
     */
    public Account(String name)
    {
        this.name = name;
        requests = new ArrayList<>();
        friends = new ArrayList<>();
        invites = new ArrayList<>();
    }

    /**
     * Changes the password of a account
     *
     * @param account : the instance of the account where the password change is
     * needed
     * @param oldP : the old password from the account
     * @param newP : the new password from the account
     * @return true if password is changed
     */
    public boolean changePassword(Account account, String oldP, String newP)
    {
        if (account.getPassword().equals(oldP))
        {
            account.password = newP;
            return true;
        }
        return false;
    }

    /**
     * Method to change the settings from a account
     *
     * @param setting : the name of the setting
     * @param value : the value of the setting
     */
    public void changeSetting(String setting, String value)
    {
        // TODO - implement Account.changeSetting
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the password from the account
     *
     * @return the password as a string
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Gets the username from the account
     *
     * @return the username as a string
     */
    public String getUsername()
    {
        return name;
    }

    /**
     * Method to send a friend request to another account
     *
     * @param to : the account where the request needs to go
     */
    public void sendFriendRequest(Account to)
    {
        FriendRequest request = new FriendRequest(this, to);
        to.sendFriendRequestToMe(request);
    }

    /**
     * Method to send a friend request to this account.
     *
     * @param request : the request
     * @return : did the friend request process true/false (Can't be friend,
     * already requested or null value's)
     */
    public boolean sendFriendRequestToMe(FriendRequest request)
    {
        if (request != null && request.getAccountDestination() == this && !friends.contains(request.getAccountSource()) && !requests.contains(request))
        {
            requests.add(request);
            return true;
        }
        return false;
    }

    /**
     * Method to accept a friend request
     *
     * @param request the friend request
     * @return true if request is accepted
     */
    public boolean acceptFriendRequest(FriendRequest request)
    {
        if (request != null)
        {
            addFriend(request.getAccountSource());
            request.getAccountSource().addFriend(this);
            this.requests.remove(request);
            return true;
        }
        return false;
    }

    /**
     * Method to decline a friend request
     *
     * @param request the friend request
     * @return : true if request is declined
     */
    public boolean declineFriendRequest(FriendRequest request)
    {
        if (request != null)
        {
            this.requests.remove(request);
            return true;
        }
        return false;
    }

    /**
     * Method to add a friend
     *
     * @param newFriend
     * @return : true if friend is added
     */
    public boolean addFriend(Account newFriend)
    {
        if (newFriend != null && !friends.contains(newFriend))
        {
            friends.add(newFriend);
            return true;
        }
        return false;
    }

    /**
     * Method to remove a friend.
     *
     * @param removeFriend
     * @return : true if friend is removed
     */
    public boolean removeFriend(Account removeFriend)
    {
        if (removeFriend != null && !friends.contains(removeFriend))
        {
            friends.remove(removeFriend);
            return true;
        }
        return false;
    }

    /**
     * Method to send a party invite to this account.
     *
     * @param partyInvite : the party invite
     * @return : true if the party invite was added to list
     */
    public boolean sendPartyInviteToMe(PartyInvite partyInvite)
    {
        if (partyInvite != null && partyInvite.getAccount() == this && party != partyInvite.getParty())
        {
            invites.add(partyInvite);
            return true;
        }
        return false;
    }

    /**
     * Method to accept a party invite
     *
     * @param invite the party invite
     * @return true if invite is accepted
     */
    public boolean acceptPartyInvite(PartyInvite invite)
    {
        if (invite != null && invites.contains(invite))
        {
            joinParty(invite.getParty());
            this.invites.remove(invite);
            return true;
        }
        return false;
    }

    /**
     * Method to decline a party invite
     *
     * @param invite the party invite
     * @return : true if invite is declined
     */
    public boolean declinePartyInvite(PartyInvite invite)
    {
        if (invite != null && invites.contains(invite))
        {
            this.invites.remove(invite);
            return true;
        }
        return false;
    }

    /**
     * Method to join a party
     *
     * @param newParty
     * @return : true if party is joined, false if already has a party or null
     */
    public boolean joinParty(Party newParty)
    {
        if (newParty != null && party != null)
        {
            party = newParty;
            return true;
        }
        return false;
    }

    /**
     * Method to leave party.
     *
     * @return : true if party is leaved
     */
    public boolean leaveParty()
    {
        if (party != null)
        {
            party = null;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Account)
        {
            Account account = (Account) o;
            return account.getUsername().equals(name);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
