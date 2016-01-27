package thegame.com.Game.Objects.Characters;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.*;
import thegame.engine.Calculate;
import thegame.engine.Collision;

/**
 *
 * @author Laurens Adema
 */
public abstract class CharacterGame extends MapObject {

    private static final long serialVersionUID = 5539685098267757690L;

    protected int hp;
    protected String name;
    protected int maxHP;
    protected List<MapObject> holding;
    protected sides direction;
    protected boolean jumping = false;

    protected java.util.Map<SkillType, Integer> skills;
    protected java.util.Map<ArmorType.bodyPart, Armor> armor;
    protected List<MapObject>[] backpack;

    protected transient long used;

    /**
     * This constructor creates a new in game character.
     *
     * @param name, Name of the character
     * @param hp, HP of the character
     * @param skills, Arraylist of the skills that the character got
     * @param x, X-Coordinate of the in game character
     * @param y, Y-Coordinate of the in game character
     * @param height, Height of the in game character
     * @param width, Width of the in game character
     * @param map
     */
    public CharacterGame(String name, int hp, java.util.Map<SkillType, Integer> skills, float x, float y, float height, float width, Map map)
    {
        super(x, y, height, width, 1, map, false, false);
        this.name = name;
        this.hp = 100;
        this.skills = skills;
        backpack = new ArrayList[30];
        armor = new HashMap();
        direction = sides.RIGHT;
        sXDecay = 0.04f;
        sXIncrease = 0.075f;
        sYIncrease = 0.05f;
        sXMax = 0.3f;
        sYMax = 0.3f;
    }

    public enum action implements Serializable {

        DROP, DROPALL, SELECT, CLICK
    }

    public abstract void knockBack(float kb, sides hitDirection);

    public void walkRight()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = Collision.collision(this, false);
        direction = sides.RIGHT;

        if (!c.get(sides.RIGHT).isEmpty())
        {
            return;
        }

        if (sX < 0)
        {
            sX = getSXIncrease();
        } else if (sX < getSXMax())
        {
            sX += getSXIncrease();
        }
    }

    public void walkLeft()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = Collision.collision(this, false);
        direction = sides.LEFT;

        if (!c.get(sides.LEFT).isEmpty())
        {
            return;
        }

        if (sX > 0)
        {
            sX = -getSXIncrease();
        } else if (sX > -getSXMax())
        {
            sX -= getSXIncrease();
        }
    }

    public void jump()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = Collision.collision(this, false);
        if ((!c.get(sides.BOTTOM).isEmpty() || jumping) && c.get(sides.TOP).isEmpty())
        {
            jumping = true;
            sY += getSYIncrease();

            if (sY >= getSYMax())
            {
                sY = getSYMax();
                jumping = false;
            }
        }
    }

    public void stopJump()
    {
        jumping = false;
    }

    /**
     * This method will let you add an object to your backpack.
     *
     * @param object, the specific object you want to add to your backpack
     * @return
     */
    public boolean addToBackpack(MapObject object)
    {
        int spot = -1;

        for (int i = 0; i < backpack.length; i++)
        {
            List<MapObject> l = backpack[i];
            if (l == null)
            {
                continue;
            }

            if (object instanceof Tool || object instanceof Armor)
            {
                continue;
            }

            if (!l.isEmpty() && l.get(0).getClass().equals(object.getClass()) && l.size() < 99)
            {
                if (l.get(0) instanceof Block && object instanceof Block)
                {
                    Block b = (Block) l.get(0);
                    Block b2 = (Block) object;
                    if (!b.getBlockType().getName().equals(b2.getBlockType().getName()))
                    {
                        continue;
                    }
                }

                spot = i;
            }
        }

        if (spot > -1)
        {
            return addToBackpack(object, spot);
        } else
        {
            return addToEmptyBackpack(object);
        }
    }

    /**
     * This method will drop an object from your backpack to the map.
     *
     * @param spot
     * @param ammount
     * @return
     */
    public boolean dropItem(int spot, int ammount)
    {
        if (spot < 0 || spot > backpack.length - 1 || backpack[spot] == null || ammount < 1)
        {
            return false;
        }
        for (MapObject removed : removeFromBackpack(spot, ammount))
        {
            playing.dropItem(removed, xPosition + (width/2), yPosition + (height / 2));
        }

        return true;
    }

    /**
     * This method let the in game character equip an armor piece.
     *
     */
    public Armor equipArmor(int spot)
    {
        if (spot < 0 || spot > backpack.length - 1 || backpack[spot] == null || !(backpack[spot].get(0) instanceof Armor))
        {
            return null;
        }

        Armor add = (Armor) removeFromBackpack(spot, 1).get(0);

        if (armor.get(add.getArmorType().bodypart) == null)
        {
            armor.put(add.getArmorType().bodypart, add);
        } else
        {
            if (addToBackpack(armor.get(add.getArmorType().bodypart)))
            {
                armor.put(add.getArmorType().bodypart, add);
            }
        }
        if (this instanceof Player)
        {
            playing.addToPlayerUpdate((Player) this);
        }

        return add;
    }

    /**
     * This method lets the in game character unequip an armor piece.
     *
     * @param partToUnequip
     */
    public boolean unequipArmor(ArmorType.bodyPart partToUnequip)
    {
        boolean returnValue = false;
        if (armor.get(partToUnequip) != null)
        {
            addToBackpack(armor.get(partToUnequip));
            armor.put(partToUnequip, null);
            returnValue = true;
            if (this instanceof Player)
            {
                playing.addToPlayerUpdate((Player) this);
            }
        }
        return returnValue;
    }

    /**
     * This method lets the in game character equip a tool.
     *
     * @param toolAdd tool to equip
     */
    public List<MapObject> equipTool(int spot)
    {
        if (spot < 0 || spot > backpack.length - 1 || backpack[spot] == null)
        {
            return null;
        }

        List<MapObject> add = removeFromBackpack(spot);

        if (holding == null || holding.size() <= 0)
        {
            holding = add;
        } else
        {
            boolean failed = false;
            for (MapObject mo : holding)
            {
                if (!addToBackpack(mo))
                {
                    failed = true;
                    break;
                }
            }
            if (failed)
            {
                for (MapObject object : holding)
                {
                    playing.dropItem(object, xPosition + (width / 2), yPosition + (height / 2));
                }
            }
            holding = add;
        }
        if (this instanceof Player)
        {
            playing.addToPlayerUpdate((Player) this);
        }
        return add;
    }

    /**
     * This method lets the in game character unequip a tool.
     */
    public boolean unequipTool()
    {
        boolean returnValue = false;
        if (holding != null)
        {
            for (MapObject mo : holding)
            {
                addToBackpack(mo);
            }
            holding = null;
            returnValue = true;
            if (this instanceof Player)
            {
                playing.addToPlayerUpdate((Player) this);
            }
        }
        return returnValue;
    }

    public List<MapObject> removeFromBackpack(ObjectType ot, int amount)
    {
        List<MapObject> removed = new ArrayList<>();
        for (int i = 0; i < backpack.length; i++)
        {
            List<MapObject> l = backpack[i];
            if (l == null)
            {
                continue;
            }

            if (!l.isEmpty() && l.get(0).getType() == ot)
            {
                while (amount > 0 && l.size() > 0)
                {
                    removed.add(l.remove(l.size() - 1));
                }
            }
            if (amount <= 0)
            {
                return removed;
            }
        }
        return removed;
    }

    public List<MapObject> removeFromBackpack(int spot, int amount)
    {
        List<MapObject> removed = new ArrayList<>();
        if (spot < 0 || spot > backpack.length - 1 || backpack[spot] == null)
        {
            return removed;
        }

        List<MapObject> l = backpack[spot];

        if (!l.isEmpty())
        {
            while (amount > 0 && l.size() > 0)
            {
                removed.add(l.remove(l.size() - 1));
                amount--;
            }
        }

        if (this instanceof Player)
        {
            playing.getGameServerToClientHandler().removeFromBackpack(spot, amount, (Player) this);
        }

        return removed;
    }

    public List<MapObject> removeFromBackpack(int spot)
    {
        List<MapObject> removed = new ArrayList<>();
        if (spot < 0 || spot > backpack.length - 1 || backpack[spot] == null)
        {
            return removed;
        }

        removed = backpack[spot];
        backpack[spot] = new ArrayList<>();
        if (this instanceof Player)
        {
            playing.getGameServerToClientHandler().removeFromBackpack(spot, (Player) this);
        }
        return removed;
    }

    /**
     * This method changes the hp of the in game character
     *
     * @param change the ammount to change
     * @return the new HP
     */
    public abstract int updateHP(int change);

    /**
     * This method gets the current hp
     *
     * @return HP
     */
    public int getHP()
    {
        return hp;
    }

    /**
     * This method gets the name
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * This method gets the list of skills
     *
     * @return list of skills
     */
    public java.util.Map<SkillType, Integer> getSkills()
    {
        return skills;
    }

    /**
     * This method gets the solid float
     *
     * @return solid float
     */
    public float getSolid()
    {
        return solid;
    }

    /**
     * This method gets a list of the current armor
     *
     * @return list of armor
     */
    public java.util.Map<ArmorType.bodyPart, Armor> getArmor()
    {
        return armor;
    }

    /**
     * This method gets the tool the character is currently holding
     *
     * @return tool
     */
    public List<MapObject> getHolding()
    {
        return holding;
    }

    /**
     * This method return a map of backpack objects
     *
     * @return map of mapobjects
     */
    public List<MapObject>[] getBackpackMap()
    {
        return backpack;
    }

    public sides getDirection()
    {
        return direction;
    }

    public void setDirection(sides direction)
    {
        this.direction = direction;
    }

    public boolean addToBackpack(MapObject object, int spot)
    {
        if (spot > backpack.length - 1)
        {
            return false;
        }

        if (backpack[spot] != null && !backpack[spot].isEmpty() && !backpack[spot].get(0).getClass().equals(object.getClass()))
        {
            return false;
        }

        if (backpack[spot] == null || backpack[spot].isEmpty())
        {
            backpack[spot] = new ArrayList<>();
            backpack[spot].add(object);
            if (this instanceof Player)
            {
                playing.getGameServerToClientHandler().addToBackpack(object, spot, (Player) this);
            }
            return true;
        }

        if (object instanceof Tool || object instanceof Armor)
        {
            return false;
        }

        backpack[spot].add(object);
        if (this instanceof Player)
        {
            playing.getGameServerToClientHandler().addToBackpack(object, spot, (Player) this);
        }
        return true;
    }

    public boolean addToEmptyBackpack(MapObject object)
    {
        for (int i = 0; i < backpack.length; i++)
        {
            if (backpack[i] == null || backpack[i].isEmpty())
            {
                backpack[i] = new ArrayList<>();
                backpack[i].add(object);
                if (this instanceof Player)
                {
                    playing.getGameServerToClientHandler().addToBackpack(object, i, (Player) this);
                }
                return true;
            }
        }
        return false;
    }

    public boolean useTool(float x, float y)
    {
        if (holding.get(0) instanceof Tool)
        {
            Tool h = (Tool) holding.get(0);
            if (System.currentTimeMillis() - used >= h.type.speed)
            {
                used = System.currentTimeMillis();
                MapObject click = playing.GetTile(x, y, this);
                if (click != null && h.type.range >= Calculate.distance(this, click))
                {
                    if (!(click instanceof Block))
                    {
                        if (!((xPosition <= x && direction == sides.RIGHT) || (xPosition >= x && direction == sides.LEFT)))
                        {
                            return false;
                        }
                    }

                    click.hit(h, direction);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void hit(Tool used, sides hitDirection)
    {
        if (used.type.type != ToolType.toolType.SWORD && used.type.type != ToolType.toolType.FLINT)
        {
            return;
        }

        float armorStats = 0;
        for (Armor c : armor.values())
        {
            armorStats += c.getArmorType().multiplier;
        }

        if (updateHP((int) Math.ceil(used.type.strength - (used.type.strength * (armorStats / 100)))) == 0)
        {
            if (this instanceof Player)
            {
                if (playing.decreaseLife())
                {
                    playing.getGameServerToClientHandler().respawnPlayer((Player) this);
                } else
                {
                    playing.getGameServerToClientHandler().stopGame(playing);
                }
            } else
            {
                playing.removeMapObject(this);
            }
        } else
        {
            if (used.type.kb > 0)
            {
                knockBack(used.type.kb / 10, hitDirection);
            }
        }

        if (this instanceof Player)
        {
            playing.addToPlayerUpdate((Player) this);
        }
    }

    @Override
    public ObjectType getType()
    {
        return null;
    }
}
