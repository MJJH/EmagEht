package thegame.com.Menu;

import java.util.ArrayList;

/**
 *  Contains methods to create accounts
 * @author robin
 */
public class Account {

    Party party;
    private int id;
    private int nextFriendRequestID = 1;
    private String name;
    private String password;
    private ArrayList<FriendRequest> requests;
    private ArrayList<Account> friends;

    /**
     * Creates a new account with a name and password
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
    }

    /**
     * Creates a new account with only a name
     * @param name : the name of the account
     */
    public Account(String name)
    {
        this.name = name;
        requests = new ArrayList<>();
    }

    /**
     * Changes the password of a account
     * @param account : the instance of the account where the password change is needed
     * @param oldP : the old password from the account
     * @param newP : the new password from the account
     * @return true if password is changed 
     */
    public boolean changePassword(Account account, String oldP, String newP)
    {
        if(account.getPassword().equals(oldP))
        {
            account.password = newP;
            return true;
        }
        return false;
    }

    /**
     * Method to send a friend request to another account
     * @param to : the account where the request needs to go
     */
    public void sendFriendRequest(Account to)
    {
        FriendRequest request = new FriendRequest(nextFriendRequestID, this, to);
        to.requests.add(nextFriendRequestID, request);
        nextFriendRequestID++;
    }

    /**
     * Method to change the settings from a account
     * @param setting : the name of the setting
     * @param value : the value of the setting
     */
    public void changeSetting(String setting, String value)
    {
        // TODO - implement Account.changeSetting
        throw new UnsupportedOperationException();
    }
    
    /**
     *Gets the password from the account
     * @return the password as a string
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * Gets the username from the account
     * @return the username as a string
     */
    public String getUsername()
    {
        return name;
    }
    
    /**
     * Method to accept a friend request
     * @param friendRequestID : the id of the request
     * @return true if request is accepted
     */
    public boolean acceptFriendRequest(int friendRequestID)
    {
        FriendRequest request = requests.get(friendRequestID);
        friends.add(request.getAccountSource());
        this.requests.remove(request);
        return true;
    }
    
    /**
     * Method to decline a friend request
     * @param friendRequestID : the id of the request
     * @return : true if request is declined
     */
    public boolean declineFriendRequest(int friendRequestID)
    {
        FriendRequest request = requests.get(friendRequestID);
        this.requests.remove(request);
        return true;
    }

}
