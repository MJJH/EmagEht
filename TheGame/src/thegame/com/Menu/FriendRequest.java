package thegame.com.Menu;

/**
 * Contains methods to accept or decline friend requests
 * @author robin
 */
public class FriendRequest {

    private final int id;
    private final Account accountSource;
    private final Account accountDestination;

    /**
     * Creates a new friend request
     * @param id : the id of the new request
     * @param accountSource : the source of the friend request
     * @param accountDestination : the destination of the friend request
     */
    public FriendRequest(int id, Account accountSource, Account accountDestination)
    {
        this.id = id;
        this.accountSource = accountSource;
        this.accountDestination = accountDestination;
    }
    
    /**
     * Method to get the source account from the request
     * @return : the account
     */
    public Account getAccountSource()
    {
        return accountSource;
    }
    
    /**
     * Method to get the destination account from the request
     * @return : the account
     */
    public Account getAccountDestination()
    {
        return accountDestination;
    }
}
