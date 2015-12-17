package thegame.com.Game.Objects.Characters;

import display.Animation;
import display.Image;
import display.Parts;
import display.Sets;
import display.Skin;
import display.iTexture;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.Particle;
import thegame.com.Game.Objects.Tool;
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
    private int spawnX;
    private int spawnY;
    private transient boolean toUpdate;

    public HashMap<String, Skin> skins;

    public void walkRight()
    {
        direction = sides.RIGHT;

        if (hSpeed < 0)
        {
            hSpeed = 0.075f;
        } else if (hSpeed < 0.3)
        {
            hSpeed += 0.075f;
        }
    }

    public void walkLeft()
    {
        direction = sides.LEFT;

        if (hSpeed > 0)
        {
            hSpeed = -0.075f;
        } else if (hSpeed > -0.3)
        {
            hSpeed -= 0.075f;
        }
    }

    public void jump() 
    {
        jumping = true;
        vSpeed += 0.05f;

        if (vSpeed >= 0.3f)
        {
            vSpeed = 0.3f;
            jumping = false;
        }
    }

    public void stopJump()
    {
        jumping = false;
    }

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
                        playing.getGameClientToServer().pickUpParticle(collisionObject.getID(), collisionObject.getX(), collisionObject.getY(), getID());
                    } catch (RemoteException ex)
                    {
                        System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                        Platform.runLater(() ->
                        {
                            playing.getTheGame().connectionLoss();
                        });
                    }
                }
            }
        }
        Physics.gravity(this);
        Movement.moveH(this);
        Movement.moveV(this);

        while (!Collision.collision(this, false).get(sides.CENTER).isEmpty())
        {
            yPosition++;
        }
    }

    public void useTool(float x, float y, IGameClientToServer gameLogic)
    {
        if (holding instanceof Tool)
        {
            Tool h = (Tool) holding;
            if (System.currentTimeMillis() - used >= h.type.speed)
            {
                try
                {
                    if (gameLogic.useTool(id, x, y))
                    {
                        //feedback
                    }
                } catch (RemoteException ex)
                {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
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

            Image d = new Image(Sets.player);

            d.recolour(h);
            //a.addFrameByPart(iTexture.Part.FRONTARM, 40);

            //a.addFrameByPart(iTexture.Part.FRONTARM, 0);
            //a.addFrameByPart(iTexture.Part.FRONTARM, 50);
            skins.put("standRight", d);

            Image d2 = new Image(Sets.player);
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
        this.hSpeed = hSpeed;
        this.vSpeed = vSpeed;
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

    public void updateValues(Player update)
    {
        setCords(update.getX(), update.getY());
        setDirection(update.getDirection());
        updateHP(update.getHP());
    }
}
