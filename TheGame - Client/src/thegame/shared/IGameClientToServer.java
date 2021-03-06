/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import thegame.com.Game.Crafting;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.com.Menu.Message;

/**
 *
 * @author laure
 */
public interface IGameClientToServer extends Remote {

    public Player getMe(IGameServerToClientListener listener, Account account) throws RemoteException;

    public void quitGame(IGameServerToClientListener listener) throws RemoteException;

    public void leaveGame(IGameServerToClientListener listener) throws RemoteException;

    public Map getMap(Lobby lobby) throws RemoteException;

    public void sendGameChatMessage(Message message) throws RemoteException;

    public void updatePlayer(int lobbyID, int id, float x, float y, int direction) throws RemoteException;

    public boolean useTool(int lobbyID, int id, float x, float y) throws RemoteException;

    public void pickUpParticle(int lobbyID, int particleID, float particleX, float particleY, int playerID) throws RemoteException;

    public boolean craft(int lobbyID, int playerID, Crafting to_craft) throws RemoteException;
    
    public void interactWithBackpack(int lobbyID, int playerID, int spot, CharacterGame.action action) throws RemoteException;
    
    public boolean unequipTool(int lobbyID, int playerID) throws RemoteException;
    
    public boolean unequipArmor(int lobbyID, int playerID, ArmorType.bodyPart partToUnequip) throws RemoteException;

    public Player resetMe(int lobbyID, int playerID) throws RemoteException;
}
