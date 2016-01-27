package thegame.com.Game.Objects.Characters;

import display.Image;
import display.Sets;
import display.Skin;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import sound.Sound;
import thegame.Startup;
import thegame.com.Game.Crafting;
import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.ObjectType;
import thegame.com.Game.Objects.Particle;
import thegame.com.Game.Objects.Tool;
import thegame.com.Menu.Account;
import thegame.engine.Calculate;
import thegame.engine.Collision;
import thegame.engine.Movement;
import thegame.engine.Physics;
import thegame.shared.IGameClientToServer;

/**
 *
 * @author laure
 */
public class Player extends CharacterGame {

    private static final long serialVersionUID = 6629685098267757690L;
    private boolean connected;
    private float spawnX;
    private float spawnY;
    private Account account;
    private Character character;

    private transient boolean toUpdate;

    public void update()
    {
        EnumMap<sides, List<MapObject>> collision = Collision.collision(this, true);
        for (sides side : sides.values())
        {
            List<MapObject> collisionList = collision.get(side);
            for (MapObject collisionObject : collisionList)
            {
                if (collisionObject instanceof Particle)
                {
                    try
                    {
                        playing.getGameClientToServer().pickUpParticle(playing.getLobby().getID(), collisionObject.getID(), collisionObject.getX(), collisionObject.getY(), getID());
                    } catch (RemoteException ex)
                    {
                        System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                        Platform.runLater(() ->
                        {
                            playing.getLobbyFX().connectionLoss();
                        });
                    }
                }
            }
        }
        Physics.gravity(this);
        Movement.moveH(this);
        Movement.moveV(this);
        Movement.deglitch(this);
        while (!Collision.collision(this, false).get(sides.CENTER).isEmpty())
        {
            yPosition++;
        }
    }

    public void useTool(float x, float y, IGameClientToServer gameClientToServer)
    {
        if (holding != null && holding.get(0) instanceof Tool)
        {
            Tool h = (Tool) holding.get(0);
            if (System.currentTimeMillis() - used >= h.type.speed)
            {
                used = System.currentTimeMillis();
                MapObject click = playing.GetTile(x, y, this);
                if (click != null && h.type.range >= Calculate.distance(this, click))
                {
                    try
                    {
                        if (gameClientToServer.useTool(playing.getLobby().getID(), id, x, y))
                        {
                            Startup.hit.play();
                        }
                    } catch (RemoteException ex)
                    {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void setCords(float x, float y)
    {
        xPosition = x;
        yPosition = y;
    }

    public void knockBack(float hSpeed, float vSpeed)
    {
        this.sX = hSpeed;
        this.sY = vSpeed;
    }

    /**
     * This will spawn the player in the game on a location.
     */
    public void spawn()
    {
        setX(((Player) this).getSpawnX());
        setY(((Player) this).getSpawnY());
    }

    /**
     * THis method will level the Player up by 1 level.
     */
    public void LevelUp()
    {
        // TODO - implement Player.LevelUp
        throw new UnsupportedOperationException();
    }

    float getSpawnX()
    {
        return spawnX;
    }

    float getSpawnY()
    {
        return spawnY;
    }

    @Override
    public void update(MapObject update)
    {
        if (update instanceof Player)
        {
            Player updatePlayer = (Player) update;
            setCords(updatePlayer.getX(), updatePlayer.getY());
            setDirection(updatePlayer.getDirection());
            updateHP(updatePlayer.getHP());
            if (!getArmor().equals(updatePlayer.getArmor()) || !updatePlayer.getHolding().equals(getHolding()))
            {
                for (MapObject mo : updatePlayer.getHolding())
                {
                    mo.setMap(playing);
                    mo.setType();
                }
                for (Armor armorPiece : updatePlayer.getArmor().values())
                {
                    armorPiece.setMap(playing);
                    armorPiece.setType();
                }
                setHolding(updatePlayer.getHolding());
                setArmor(updatePlayer.getArmor());
                createSkin();
            }
        }
    }

    public boolean Craft(Crafting to_craft)
    {
        Map<ObjectType, Integer> need = to_craft.recources;
        for (ObjectType ot : need.keySet())
        {
            int left = need.get(ot);
            int i = 0;
            while (left > 0 && i < 30)
            {
                if (backpack[i] != null && backpack[i].size() > 0 && backpack[i].get(0).getType() == ot)
                {
                    left -= backpack[i].size();
                }
                i++;
            }
            if (left > 0)
            {
                return false;
            }
        }
        try
        {
            return playing.getGameClientToServer().craft(playing.getLobby().getID(), id, to_craft);
        } catch (RemoteException e)
        {
            return false;
        }
    }

    public void loadAfterRecieve(thegame.com.Game.Map play)
    {
        setMap(play);
        for (MapObject holdObject : holding)
        {
            holdObject.setType();
            holdObject.setMap(play);
        }
        for (List<MapObject> listBP : backpack)
        {
            if (listBP != null)
            {
                for (MapObject bpObject : listBP)
                {
                    bpObject.setType();
                    bpObject.setMap(play);
                }
            }
        }
    }

    public void reset(Player resetPlayer)
    {
        if (!getArmor().equals(resetPlayer.getArmor()) || !resetPlayer.getHolding().equals(getHolding()))
        {
            for (MapObject mo : resetPlayer.getHolding())
            {
                mo.setMap(playing);
                mo.setType();
            }
            for (Armor armorPiece : resetPlayer.getArmor().values())
            {
                armorPiece.setMap(playing);
                armorPiece.setType();
            }
            for (List<MapObject> bp : backpack)
            {
                for (MapObject bpObject : bp)
                {
                    bpObject.setMap(playing);
                    bpObject.setType();
                }
            }
            setHolding(resetPlayer.getHolding());
            setArmor(resetPlayer.getArmor());
            backpack = resetPlayer.getBackpackMap();
            createSkin();
        }
    }
}
