/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.IGameClientToServer;
import thegame.shared.IGameServerToClientListener;

/**
 *
 * @author laure
 */
public class GameClientToServerHandler implements IGameClientToServer {

    private transient Map map;
    private final transient GameServerToClientHandler gameServerToClientHandler;

    public GameClientToServerHandler() throws RemoteException
    {
        gameServerToClientHandler = new GameServerToClientHandler();

        map = new Map(gameServerToClientHandler, this);
        gameServerToClientHandler.registerMap(map);

        Timer update = new Timer("update");
        update.schedule(new TimerTask() {

            @Override
            public void run()
            {
                map.update();
            }
        }, 0, 1000 / 60);
    }

    @Override
    public Player joinPlayer(Account account, IGameServerToClientListener listener) throws RemoteException
    {
        Player player = new Player(null, account.getUsername(), 100, null, null, map.getSpawnX(), map.getSpawnY(), 2, 1, map);
        map.addMapObject(player);
        gameServerToClientHandler.joinPlayer(listener, player);
        return player;
    }

    @Override
    public Map getMap() throws RemoteException
    {
        return map;
    }

    @Override
    public boolean useTool(int id, float x, float y) throws RemoteException
    {
        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
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
    public void leavePlayer(IGameServerToClientListener listener) throws RemoteException
    {
        gameServerToClientHandler.leavePlayer(listener);
    }

    @Override
    public void updatePlayer(int id, float x, float y, int direction) throws RemoteException
    {
        for (Player player : map.getPlayers())
        {
            if (player.getID() == id)
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

                gameServerToClientHandler.updatePlayer(player);
                return;
            }
        }
    }
}
