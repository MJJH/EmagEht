package thegame.com.Menu;

/**
 *  Contains methods to create accounts
 * @author laure
 */
public class Account {

    Party party;
    private int id;
    private String name;
    private String password;

    /**
     * Creates a new account with a name and password
     * @param name : the name of the account
     * @param password : the password of the account
     */
    public Account(String name, String password)
    {
        // TODO - implement Account.Account
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a new account with only a name
     * @param name : the name of the account
     */
    public Account(String name)
    {
        // TODO - implement Account.Account
        throw new UnsupportedOperationException();
    }

    /**
     * Changes the password of a account
     * @param account : the instance of the account where the password change is needed
     * @param oldP : the old password from the account
     * @param newP : the new passwrod from the account
     */
    public void changePassword(Account account, String oldP, String newP)
    {
        // TODO - implement Account.changePassword
        throw new UnsupportedOperationException();
    }

    /**
     * Method to send a freind request to another account
     * @param to : the account where the request needs to go
     */
    public void sendFriendRequest(Account to)
    {

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

}
