/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.List;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;

/**
 *
 * @author laure
 */
public interface iGameLogic extends IRemotePublisher {

    public int getHeight() throws RemoteException;

    public int getWidth() throws RemoteException;

    public int getTeamLifes() throws RemoteException;

    public int getTime() throws RemoteException;

    public Array[] getSeasons() throws RemoteException;

    public int getLevel() throws RemoteException;

    public int getSpawnX() throws RemoteException;

    public int getSpawnY() throws RemoteException;

    public List<MapObject> getObjects() throws RemoteException;

    public List<Enemy> getEnemies() throws RemoteException;

    public List<Player> getPlayers() throws RemoteException;

    public List<MapObject> getToUpdate() throws RemoteException;

    public List<MapObject> getBlocksAndObjects(int startX, int startY, int endX, int endY) throws RemoteException;

    public List<Block> getBlocks() throws RemoteException;
    
    public Player joinPlayer(Account account) throws RemoteException;
    
    public void addToUpdate (MapObject update) throws RemoteException;
    
    public Map getMap() throws RemoteException;
    
    public void addObject(MapObject add) throws RemoteException;
    
    public int getMapObjectID() throws RemoteException;
    
    public void updateMapObject(MapObject toUpdate) throws RemoteException;
    
    public boolean useTool (int id, float x, float y) throws RemoteException;

    public void sendMyLoc(int id, float x, float y) throws RemoteException;

    public void sendMessage(Message chatMessage) throws RemoteException;
}
