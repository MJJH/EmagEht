/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.List;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;

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
}
