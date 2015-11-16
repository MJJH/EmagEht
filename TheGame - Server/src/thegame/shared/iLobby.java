/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.util.ArrayList;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;

/**
 *
 * @author laure
 */
public interface iLobby extends Remote {

    public ArrayList<Account> getAccounts();

    public ArrayList<Message> getChat();

    public boolean joinLobby(Account account);

    public boolean sendMessage(Account sender, String message);
}
