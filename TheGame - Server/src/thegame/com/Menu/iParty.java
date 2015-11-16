/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Menu;

import java.rmi.Remote;
import java.util.ArrayList;


/**
 *
 * @author nickbijmoer
 */
public interface iParty extends Remote{
   
    public ArrayList<Message> getChat();
    
    public ArrayList<Account> getMembers();
    
    public Account getOwner();
    
    public Account leaveParty (Account leaveAccount);
    
    public void sendInvite(Account invite, Account sender);
    
    public boolean sendMessage(Account sender, String message);
    
    public boolean joinParty(Account account);





    
    
}
