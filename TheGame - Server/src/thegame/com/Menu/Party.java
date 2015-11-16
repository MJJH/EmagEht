package thegame.com.Menu;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * This class contains code for the party. 
 * @author laure
 */
public class Party extends UnicastRemoteObject{

    private int id;
    private final ArrayList<Account> members;
    private Account owner;
    private final ArrayList<Message> chat;

    /**
     * This method creates a party with the owner as member and owner.
     * @param owner
     */
    public Party(Account owner)throws RemoteException
    {
        this.owner = owner;
        members = new ArrayList();
        chat = new ArrayList();
        
    }

    /**
     * This method creates a PartyInvite for an account by a sender.
     * @param invite
     * @param sender
     */
    public void sendInvite(Account invite, Account sender)
    {
        if(invite != null && sender != null && !invite.getUsername().equals(sender.getUsername()))
        {
            invite.sendPartyInviteToMe(new PartyInvite(invite, this, sender));
        }
    }
    
    /**
     * This method creates a message in the party chat.
     * @param sender
     * @param message
     * @return returns true if message is send
     */
    public boolean sendMessage(Account sender, String message)
    {
        if(!message.isEmpty())
        {
            chat.add(new Message(sender, message));
            return true;
        }
        
        return false;
    }
    
    /**
     * This method is used to leave a party. 
     * If the owner is leaving, the party will get a new owner.
     * @param leaveAccount
     * @return returns null if party is empty and owner is leaving, else new/current owner.
     */
    public Account leaveParty (Account leaveAccount)
    {
        if(leaveAccount.equals(owner))
        {
            if(members.size() > 1)
            {
                members.remove(leaveAccount);
                leaveAccount.leaveParty();
                owner = members.get(0);
                return owner;
            }
            else
            {
                return null;
            }
        }
        else
        {
            members.remove(leaveAccount);
            leaveAccount.leaveParty();
        }
        return  owner;
    }
    
    /**
     * This method gets the members of a party
     * @return list of accounts
     */
    public ArrayList<Account> getMembers()
    {
        return members;
    }
    
    /**
     * This method gets the party owner
     * @return account
     */
    public Account getOwner()
    {
        return owner;
    }
    
    /**
     * This method gets party chat
     * @return list of messages
     */
    public ArrayList<Message> getChat()
    {
        return chat;
    }
}
