/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import thegame.com.Game.Crafting;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.Particle;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.com.Menu.Message;
import thegame.shared.IGameClientToServer;
import thegame.shared.IGameServerToClientListener;

/**
 *
 * @author laure
 */
public class GameClientToServerHandler implements IGameClientToServer {
    private transient LobbyServerToClientHandler lobbyServerToClientHandler;
    private transient LobbyClientToServerHandler lobbyClientToServerHandler;
    private transient GameServerToClientHandler gameServerToClientHandler;
    private transient TheGameServer theGameServer;

    public GameClientToServerHandler()
    {
    }
    
    public void registerComponents(LobbyServerToClientHandler lobbyServerToClientHandler, LobbyClientToServerHandler lobbyClientToServerHandler, GameServerToClientHandler gameServerToClientHandler, TheGameServer theGameServer)
    {
        this.lobbyServerToClientHandler = lobbyServerToClientHandler;
        this.lobbyClientToServerHandler = lobbyClientToServerHandler;
        this.gameServerToClientHandler = gameServerToClientHandler;
        this.theGameServer = theGameServer;
    }
    
    @Override
    public Player getMe(IGameServerToClientListener listener, Account account) throws RemoteException
    {
        Lobby lobby = lobbyServerToClientHandler.getAccountsInLobbies().get(account);
        Map map = gameServerToClientHandler.getGameTable().get(lobby);
        for(Player player : map.getPlayers())
        {
            if(player.getAccount().getUsername().equals(account.getUsername()))
            {
                gameServerToClientHandler.getPlayerListenerTable().put(listener, player);
                return player;
            }
        }
        return null;
    }
    
    @Override
    public void quitGame(IGameServerToClientListener listener) throws RemoteException
    {
        gameServerToClientHandler.connectionLossPlayer(listener);
    }
    
    @Override
    public void leaveGame(IGameServerToClientListener listener) throws RemoteException
    {
        gameServerToClientHandler.leaveGame(listener);
    }

    @Override
    public Map getMap(Lobby lobby) throws RemoteException
    {
        return gameServerToClientHandler.getGameTable().get(lobby);
    }

    @Override
    public boolean useTool(int lobbyID, int playerID, float x, float y) throws RemoteException
    {
        Map map = null;
        for(Lobby lobby : gameServerToClientHandler.getGameTable().keySet())
        {
            if(lobby.getID() == lobbyID)
            {
                map = gameServerToClientHandler.getGameTable().get(lobby);
                break;
            }
        }
        if(map == null)
        {
            return false;
        }
        
        for (Player player : map.getPlayers())
        {
            if (player.getID() == playerID)
            {
                return player.useTool(x, y);
            }
        }

        return false;
    }

    @Override
    public void sendGameChatMessage(Message message) throws RemoteException
    {
        gameServerToClientHandler.sendGameChatMessage(message);
    }
    @Override
    public void updatePlayer(int lobbyID, int playerID, float x, float y, int direction) throws RemoteException
    {
        Map map = null;
        for(Lobby lobby : gameServerToClientHandler.getGameTable().keySet())
        {
            if(lobby.getID() == lobbyID)
            {
                map = gameServerToClientHandler.getGameTable().get(lobby);
                break;
            }
        }
        if(map == null)
        {
            return;
        }
        
        for (Player player : map.getPlayers())
        {
            if (player.getID() == playerID)
            {
                if (player.getX() == x && player.getY() == y)
                {
                    return;
                }
                player.setCords(x, y);
                MapObject.sides directionSide = MapObject.sides.RIGHT;
                if (direction == 0)
                {
                    directionSide = MapObject.sides.LEFT;
                }
                player.setDirection(directionSide);

                map.addToPlayerUpdate(player);
                return;
            }
        }
    }

    @Override
    public void pickUpParticle(int lobbyID, int particleID, float particleX, float particleY, int playerID) throws RemoteException
    {
        Map map = null;
        for(Lobby lobby : gameServerToClientHandler.getGameTable().keySet())
        {
            if(lobby.getID() == lobbyID)
            {
                map = gameServerToClientHandler.getGameTable().get(lobby);
                break;
            }
        }
        if(map == null)
        {
            return;
        }
        
        MapObject particleMO = null;
        Particle particle = null;
        for (MapObject mo : map.getObjects(particleX, particleY, 0))
        {
            if (mo instanceof Particle)
            {
                particleMO = mo;
                particle = (Particle) particleMO;
                if (particle.getPickedUp())
                {
                    return;
                }
                break;
            }
        }
        if (particleMO == null || particle == null)
        {
            return;
        }
        for (Player player : map.getPlayers())
        {
            if (player.getID() == playerID)
            {
                for (int i = 1; i <= particle.getObjectCount(); i++)
                {

                    player.addToBackpack(particle.getObject());
                }
                particle.setPickedUp(true);
                map.removeMapObject(particle);
                break;
            }
        }
    }

    @Override
    public boolean craft(int lobbyID, int playerID, Crafting to_craft) throws RemoteException
    {
        Map map = null;
        for(Lobby lobby : gameServerToClientHandler.getGameTable().keySet())
        {
            if(lobby.getID() == lobbyID)
            {
                map = gameServerToClientHandler.getGameTable().get(lobby);
                break;
            }
        }
        if(map == null)
        {
            return false;
        }
        for(Player player : map.getPlayers())
        {
            if(player.getID() == playerID)
            {
                return player.Craft(to_craft);
            }
        }
        return false;
    }

    @Override
    public boolean interactWithBackpack(int lobbyID, int playerID, int spot, CharacterGame.action action) throws RemoteException
    {
        Map map = null;
        for(Lobby lobby : gameServerToClientHandler.getGameTable().keySet())
        {
            if(lobby.getID() == lobbyID)
            {
                map = gameServerToClientHandler.getGameTable().get(lobby);
                break;
            }
        }
        if(map == null)
        {
            return false;
        }
        for(Player player : map.getPlayers())
        {
            if(player.getID() == playerID)
            {
                return player.interactWithBackpack(spot, action);
            }
        }
        return false;
    }

    @Override
    public boolean unequipTool(int lobbyID, int playerID) throws RemoteException
    {
        Map map = null;
        for(Lobby lobby : gameServerToClientHandler.getGameTable().keySet())
        {
            if(lobby.getID() == lobbyID)
            {
                map = gameServerToClientHandler.getGameTable().get(lobby);
                break;
            }
        }
        if(map == null)
        {
            return false;
        }
        for(Player player : map.getPlayers())
        {
            if(player.getID() == playerID)
            {
                return player.unequipTool();
            }
        }
        return false;
    }

    @Override
    public boolean unequipArmor(int lobbyID, int playerID, ArmorType.bodyPart partToUnequip) throws RemoteException
    {
        Map map = null;
        for(Lobby lobby : gameServerToClientHandler.getGameTable().keySet())
        {
            if(lobby.getID() == lobbyID)
            {
                map = gameServerToClientHandler.getGameTable().get(lobby);
                break;
            }
        }
        if(map == null)
        {
            return false;
        }
        for(Player player : map.getPlayers())
        {
            if(player.getID() == playerID)
            {
                return player.unequipArmor(partToUnequip);
            }
        }
        return false;
    }
}
