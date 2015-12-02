package thegame.com.Game.Objects.Characters;

import display.Animation;
import display.Skin;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import thegame.com.Game.Objects.MapObject;
import thegame.shared.iGameLogic;

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

    private HashMap<String, Skin> skins;

    public void walkRight()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        direction = sides.RIGHT;

        if (!c.get(sides.RIGHT).isEmpty())
        {
            return;
        }

        if (hSpeed < 0)
        {
            hSpeed = 0.1f;
        } else if (hSpeed < 0.5)
        {
            hSpeed += 0.1;
        }

        setToUpdate(true);
    }

    public void walkLeft()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        direction = sides.LEFT;

        if (!c.get(sides.LEFT).isEmpty())
        {
            return;
        }

        if (hSpeed > 0)
        {
            hSpeed = -0.1f;
        } else if (hSpeed > -0.5)
        {
            hSpeed -= 0.1;
        }

        setToUpdate(true);
    }

    public void jump()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        if ((!c.get(sides.BOTTOM).isEmpty() || jumping) && c.get(sides.TOP).isEmpty())
        {
            jumping = true;
            vSpeed += 0.2f;

            if (vSpeed >= 0.8f)
            {
                vSpeed = 0.8f;
                jumping = false;
            }
        }

        setToUpdate(true);
    }

    public void stopJump()
    {
        jumping = false;

        setToUpdate(true);
    }

    private boolean fall(EnumMap<sides, List<MapObject>> collision)
    {
        if (collision.get(sides.BOTTOM).isEmpty() && vSpeed > -1)
        {
            vSpeed -= .1f;
            return true;
        }
        return false;
    }

    private boolean moveH(EnumMap<sides, List<MapObject>> collision)
    {
        List<MapObject> found;

        if (hSpeed > 0)
        {
            hSpeed -= 0.05f;
            if (hSpeed <= 0)
            {
                hSpeed = 0;
            }

            found = collision.get(sides.RIGHT);
            if (found.isEmpty())
            {
                setX(xPosition + hSpeed);
                return true;
            } else
            {
                float minX = -1;
                for (MapObject mo : found)
                {
                    if (minX == -1 || mo.getX() < minX)
                    {
                        minX = mo.getX();
                    }
                }

                if (minX == -1)
                {
                    setX(xPosition + hSpeed);
                    return true;
                }

                hSpeed = 0;
                setX(minX - width);
                return true;
            }
        } else if (hSpeed < 0)
        {
            hSpeed += 0.05f;
            if (hSpeed >= 0)
            {
                hSpeed = 0;
            }

            found = collision.get(sides.LEFT);
            if (found.isEmpty())
            {
                setX(xPosition + hSpeed);
                return true;
            } else
            {
                float maxX = -1;
                for (MapObject mo : found)
                {

                    if (maxX == -1 || mo.getX() + mo.getW() > maxX)
                    {
                        maxX = mo.getX() + mo.getW();
                    }
                }

                if (maxX == -1)
                {
                    setX(xPosition + hSpeed);
                    return true;
                }

                hSpeed = 0;
                setX(maxX);
                return true;
            }
        }

        return false;
    }

    private boolean moveV(EnumMap<sides, List<MapObject>> collision)
    {
        List<MapObject> found;

        if (vSpeed > 0)
        {
            found = collision.get(sides.TOP);
            if (found.isEmpty())
            {
                setY(yPosition + vSpeed);

                found = collision.get(sides.TOP);
                if (!found.isEmpty())
                {
                    float minY = -1;
                    for (MapObject mo : found)
                    {

                        if (minY == -1 || mo.getY() < minY)
                        {
                            minY = mo.getY() - mo.getH();
                        }
                    }

                    if (minY == -1)
                    {
                        setY(yPosition + vSpeed);
                        return true;
                    }

                    vSpeed = 0;
                    setY(minY);
                }

                return true;
            } else
            {
                float minY = -1;
                for (MapObject mo : found)
                {

                    if (minY == -1 || mo.getY() < minY)
                    {
                        minY = mo.getY() - mo.getH();
                    }
                }

                if (minY == -1)
                {
                    setY(yPosition + vSpeed);
                    return true;
                }

                vSpeed = 0;
                setY(minY);
                return true;
            }
        } else if (vSpeed < 0)
        {
            found = collision.get(sides.BOTTOM);
            if (found.isEmpty())
            {
                setY(yPosition + vSpeed);

                found = collision().get(sides.BOTTOM);
                if (!found.isEmpty())
                {
                    float maxY = -1;
                    for (MapObject mo : found)
                    {

                        if (maxY == -1 || mo.getY() > maxY)
                        {
                            maxY = mo.getY() + height;
                        }
                    }

                    if (maxY == -1)
                    {
                        setY(yPosition + vSpeed);
                        return true;
                    }

                    vSpeed = 0;
                    setY(maxY);
                }

                return true;
            } else
            {
                float maxY = -1;
                for (MapObject mo : found)
                {

                    if (maxY == -1 || mo.getY() > maxY)
                    {
                        maxY = mo.getY() + height;
                    }
                }

                if (maxY == -1)
                {
                    setY(yPosition + vSpeed);
                    return true;
                }

                vSpeed = 0;
                setY(maxY);

                return true;
            }
        }

        return false;
    }

    private EnumMap<sides, List<MapObject>> collision()
    {
        EnumMap<sides, List<MapObject>> collision = new EnumMap<>(sides.class);
        collision.put(sides.TOP, new ArrayList<>());
        collision.put(sides.BOTTOM, new ArrayList<>());
        collision.put(sides.LEFT, new ArrayList<>());
        collision.put(sides.RIGHT, new ArrayList<>());
        collision.put(sides.CENTER, new ArrayList<>());

        List<MapObject> mos = playing.getBlocksAndObjects((int) Math.round(xPosition - 2),
                (int) Math.round(yPosition - height - 3),
                (int) Math.round(xPosition + width + 1),
                (int) Math.round(yPosition + 2));

        for (MapObject mo : mos)
        {
            if (mo == this)
            {
                continue;
            }

            List<sides> found = this.collision(mo);
            if (found != null)
            {
                for (sides s : found)
                {
                    collision.get(s).add(mo);
                }
            }
        }

        return collision;
    }

    public ArrayList<sides> collision(MapObject mo)
    {
        if (this.solid == 0 || mo.getS() == 0)
        {
            return null;
        }

        Rectangle r1 = new Rectangle(xPosition, yPosition, width, height);
        Rectangle r2 = new Rectangle(mo.getX(), mo.getY(), mo.getW(), mo.getH());

        boolean right = (r2.getX() >= r1.getX() && r2.getX() <= r1.getX() + r1.getWidth());
        boolean left = (r2.getX() + r2.getWidth() >= r1.getX() && r2.getX() + r2.getWidth() <= r1.getX() + r1.getWidth());
        boolean top = (r2.getY() - r2.getHeight() <= r1.getY() && r2.getY() - r2.getHeight() >= r1.getY() - r1.getHeight());
        boolean bott = (r2.getY() >= r1.getY() - r1.getHeight() && r2.getY() <= r1.getY());

        if (right || left || top || bott)
        {
            ArrayList<sides> ret = new ArrayList<>();
            if ((bott && top) && (left && right))
            {
                ret.add(sides.CENTER);
            }

            if (right && !(mo.getY() - mo.getH() >= yPosition || mo.getY() <= yPosition - height))
            {
                ret.add(sides.RIGHT);
            }
            if (left && !(mo.getY() - mo.getH() >= yPosition || mo.getY() <= yPosition - height))
            {
                ret.add(sides.LEFT);
            }
            if (top && !(mo.getX() + mo.getW() <= xPosition || mo.getX() >= xPosition + width))
            {
                ret.add(sides.TOP);
            }
            if (bott && !(mo.getX() + mo.getW() <= xPosition || mo.getX() >= xPosition + width))
            {
                ret.add(sides.BOTTOM);
            }

            return ret;
        } else
        {
            return null;
        }
    }

    public void update()
    {
        EnumMap<sides, List<MapObject>> collision = collision();
        Boolean newToUpdate = false;
        if(fall(collision))
            newToUpdate = true;
        
        if(moveH(collision))
            newToUpdate = true;
        
        if(moveV(collision))
            newToUpdate = true;
        
        while(!collision.get(sides.CENTER).isEmpty()) {
            yPosition++;
            collision = collision();
            newToUpdate = true;
        }
        
        setToUpdate(newToUpdate);
    }

    public void useTool(float x, float y, iGameLogic gameLogic)
    {
        if (System.currentTimeMillis() - used >= holding.type.speed)
        {
            try
            {
                if (gameLogic.useTool(id, x, y))
                {
                    setToUpdate(true);
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
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
        if (skins == null)
        {
            return null;
        }

        if (direction == sides.RIGHT)
        {
            return skins.get("standRight");
        } else
        {
            return skins.get("standLeft");
        }
    }

    @Override
    public void createSkin()
    {
        try
        {
            skins = new HashMap<>();
            display.Image i = new display.Image(43, 24);
            String pathname = "src/resources//player.png";
            i.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -40);
            i.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            display.Image i3 = new display.Image(43, 24);
            i3.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i3.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i3.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i3.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i3.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -50);
            i3.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i3.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            display.Image i4 = new display.Image(43, 24);
            i4.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i4.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i4.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i4.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i4.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -40);
            i4.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i4.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            display.Image i5 = new display.Image(43, 24);
            i5.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i5.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i5.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i5.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i5.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -30);
            i5.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i5.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            Animation d = new Animation(3);
            d.addFrame(i);
            d.addFrame(i3);
            d.addFrame(i4);
            d.addFrame(i5);
            skins.put("standRight", d);

            display.Image i6 = new display.Image(43, 24);
            i6.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i6.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i6.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i6.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i6.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -40);
            i6.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i6.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            display.Image i7 = new display.Image(43, 24);
            i7.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i7.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i7.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i7.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i7.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -50);
            i7.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i7.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            display.Image i8 = new display.Image(43, 24);
            i8.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i8.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i8.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i8.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i8.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -40);
            i8.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i8.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            display.Image i9 = new display.Image(43, 24);
            i9.addPart("legBehind", pathname, 0, 36, 12, 15, 8, 28, true);
            i9.addPart("armBehind", pathname, 9, 16, 8, 17, 16, 12, true);
            i9.addPart("torso", pathname, 17, 0, 14, 16, 4, 12, true);
            i9.addPart("legFront", pathname, 13, 36, 11, 15, 3, 28, true);
            i9.addPart("armFront", pathname, 0, 16, 8, 19, 0, 12, true, -30);
            i9.addPart("head", pathname, 0, 0, 15, 15, 4, 0, true);

            i9.recolour(new Color[]
            {
                new Color(0, 0, 0, 1),
                new Color(0.52, 0.30, 0.13, 1),
                new Color(0.72, 0.41, 0.19, 1),
                new Color(0.84, 0.58, 0.39, 1),
                new Color(0.89, 0.71, 0.58, 1),
                new Color(1, 0, 0, 1)
            });

            Animation d2 = new Animation(3);
            i6.flipHorizontal();
            i7.flipHorizontal();
            i8.flipHorizontal();
            i9.flipHorizontal();

            d2.addFrame(i6);
            d2.addFrame(i7);
            d2.addFrame(i8);
            d2.addFrame(i9);
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
        setToUpdate(true);
    }

    public boolean getToUpdate()
    {
        return toUpdate;
    }

    public void setToUpdate(boolean value)
    {
        toUpdate = value;
    }

    /**
     * This will spawn the player in the game on a location.
     */
    public void spawn()
    {
        // TODO - implement Player.spawn
        throw new UnsupportedOperationException();
    }

    /**
     * THis method will level the Player up by 1 level.
     */
    public void LevelUp()
    {
        // TODO - implement Player.LevelUp
        throw new UnsupportedOperationException();
    }
}
