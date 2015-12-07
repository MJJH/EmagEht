package thegame.com.Game.Objects.Characters;

import java.util.HashMap;
import thegame.com.Game.Objects.*;

/**
 *
 * @author Laurens Adema
 */
public abstract class CharacterGame extends MapObject {

    private static final long serialVersionUID = 5539685098267757690L;

    protected int hp;
    protected String name;
    protected int maxHP;
    protected Tool holding;
    protected long used;

    protected sides direction;

    protected java.util.Map<SkillType, Integer> skills;
    protected java.util.Map<ArmorType.bodyPart, Armor> armor;
    protected java.util.Map<MapObject, Integer> backpack;

    protected boolean jumping = false;

    /**
     * This method will let you add an object to your backpack.
     *
     * @param object, the specific object you want to add to your backpack
     * @return
     */
    public boolean addToBackpack(MapObject object)
    {
        if (backpack.containsKey(object))
        {
            if (object.getClass() == Armor.class || object.getClass() == Tool.class)
            {
                backpack.put(object, 1);
            } else
            {
                if (backpack.get(object) < 99)
                {
                    backpack.put(object, backpack.get(object) + 1);
                } else
                {
                    return false;
                }
            }

        } else
        {
            if (backpack.size() < 30)
            {
                backpack.put(object, 1);
            } else
            {
                return false;
            }
        }

        return true;
    }

    /**
     * This method will drop an object from your backpack to the map.
     *
     * @param object, the object you want to drop
     * @return returns the object and value
     */
    public java.util.Map dropItem(MapObject object)
    {
        if (backpack.containsKey(object))
        {
            java.util.Map mapMapObject = new HashMap();
            mapMapObject.put(object, backpack.get(object));
            backpack.remove(object);
            return mapMapObject;
        } else
        {
            return null;
        }
    }

    /**
     * This method let the in game character equip an armor piece.
     *
     * @param armorAdd, the armor that you want to wear
     */
    public void equipArmor(Armor armorAdd)
    {
        if (armor.containsKey(armorAdd.getArmorType().bodypart))
        {
            unequipArmor(armor.get(armorAdd.getArmorType().bodypart));
            backpack.remove(armorAdd);
            armor.put(armorAdd.getArmorType().bodypart, armorAdd);
        } else
        {
            armor.put(armorAdd.getArmorType().bodypart, armorAdd);
            backpack.remove(armorAdd);
        }
    }

    /**
     * This method lets the in game character unequip an armor piece.
     *
     * @param armorDel
     */
    public void unequipArmor(Armor armorDel)
    {
        if (armor.containsValue(armorDel))
        {
            armor.remove(armorDel.getArmorType(), armorDel);
            addToBackpack(armorDel);
        }
    }

    /**
     * This method lets the in game character equip a tool.
     *
     * @param toolAdd tool to equip
     */
    private void equipTool(Tool toolAdd)
    {
        if (holding == null)
        {
            holding = toolAdd;
        } else if (!holding.equals(toolAdd))
        {
            holding = toolAdd;
        }
    }

    /**
     * This method lets the in game character unequip a tool.
     */
    public void unequipTool()
    {
        holding = null;
    }

    /**
     * This method changes the hp of the in game character
     *
     * @param newHP the new hp
     */
    public void updateHP(int newHP)
    {
        hp = newHP;

        if (this instanceof Player && this == playing.getMe() && hp == 0)
        {
            setX(((Player) this).getSpawnX());
            setY(((Player) this).getSpawnY());
        }
    }

    public int getMaxHP()
    {
        return 100;
    }

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
    public Tool getHolding()
    {
        return holding;
    }

    /**
     * This method return a map of backpack objects
     *
     * @return map of mapobjects
     */
    public java.util.Map<MapObject, Integer> getBackpackMap()
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
}
