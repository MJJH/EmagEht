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
    public transient HashMap<String, Skin> skins;

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
                            playing.getGameFX().connectionLoss();
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
        if (holding.get(0) instanceof Tool)
        {
            Tool h = (Tool) holding.get(0);
            if (System.currentTimeMillis() - used >= h.type.speed)
            {
                used = System.currentTimeMillis();
                used = System.currentTimeMillis();
                MapObject click = playing.GetTile(x, y, this);
                float distance = Calculate.distance(this,click);
                if (click != null && h.type.range > distance)
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

    @Override
    public Skin getSkin()
    {
        try
        {
            if (skins == null)
            {
                createSkin();
            }

            if (direction == sides.RIGHT)
            {
                return skins.get("standRight");
            } else
            {
                return skins.get("standLeft");
            }
        } catch (Exception exc)
        {
            return null;
        }
    }

    @Override
    public void createSkin()
    {
        try
        {
            skins = new HashMap<>();
            Color[] h = new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.26, 0.15, 0.065, 1),
                new Color(0.36, 0.205, 0.095, 1),
                new Color(0.42, 0.29, 0.195, 1),
                new Color(0.445, 0.355, 0.29, 1),
                new Color(1, 1, 1, 1)
            };

            Image d = new Image(Sets.sets.get("player"));

            d.recolour(h);
            //a.addFrameByPart(iTexture.Part.FRONTARM, 40);

            //a.addFrameByPart(iTexture.Part.FRONTARM, 0);
            //a.addFrameByPart(iTexture.Part.FRONTARM, 50);
            skins.put("standRight", d);

            Image d2 = new Image(Sets.sets.get("player"));
            d2.recolour(h);
            d2.flipHorizontal();

            skins.put("standLeft", d2);

        } catch (IOException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if (backpack[i].get(0).getType() == ot)
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
}
