package thegame.com.Game.Objects.Characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import thegame.com.Game.Objects.*;

/**
 *
 * @author Nick Bijmoer
 */
public class CharacterGame extends MapObject {

    private int hp;
    private final String name;
    private ArrayList<SkillType> skills = new ArrayList<>();
    private float solid;

    private ArrayList<Armor> armor;
    private Tool holding;

    private java.util.Map<MapObject, Integer> backpack;

    /**
     * This constructor creates a new in game character.
     *
     * @param name, Name of the character
     * @param hp, HP of the character
     * @param skills, Arraylist of the skills that the character got
     * @param x, X-Coordinate of the in game character
     * @param y, Y-Coordinate of the in game character
     * @param skin, Skin that the in game character got
     * @param height, Height of the in game character
     * @param width, Width of the in game character
     */
    public CharacterGame(String name, int hp, ArrayList<SkillType> skills, float x, float y, Image skin, float height, float width)
    {
        super(x, y, skin, height, width, 1);
        this.skills = skills;
        this.name = name;
        this.hp = 100;

        armor = new ArrayList();
        backpack = new HashMap();
    }

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
            if (backpack.get(object) > 99)
            {
                backpack.put(object, backpack.get(object) + 1);
            } else
            {
                return false;
            }
        } else
        {
            if (backpack.size() > 30)
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
        if (!armor.contains(armorAdd))
        {
            armor.add(armorAdd);
        }
    }

    /**
     * This method lets the in game character unequip an armor piece.
     *
     * @param armorDel
     */
    public void unequipArmor(Armor armorDel)
    {
        if (armor.contains(armorDel))
        {
            armor.remove(armorDel);
        }
    }

    /**
     * This method lets the in game character equip a tool.
     * @param toolAdd tool to equip
     */
    public void equipTool(Tool toolAdd)
    {
        if (!holding.equals(toolAdd))
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
     * @param change the ammount to change
     * @return the new HP
     */
    public int updateHP(int change)
    {
        hp += change;

        if (hp > 100)
        {
            hp = 100;
        }
        if (hp < 0)
        {
            hp = 0;
        }

        return hp;
    }

    /**
     * This method gets the current hp
     * @return HP
     */
    public int getHP()
    {
        return hp;
    }

    /**
     * This method gets the name
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * This method gets the list of skills
     * @return list of skills
     */
    public ArrayList<SkillType> getSkills()
    {
        return skills;
    }

    /**
     * This method gets the solid float
     * @return solid float
     */
    public float getSolid()
    {
        return solid;
    }

    /**
     * This method gets a list of the current armor
     * @return list of armor
     */
    public ArrayList<Armor> getArmor()
    {
        return armor;
    }
    
    /**
     * This method gets the tool the character is currently holding
     * @return tool
     */
    public Tool getHolding ()
    {
        return holding;
    }
    
    /**
     * This method return a map of backpack objects
     * @return map of mapobjects
     */
    public java.util.Map<MapObject, Integer> getBackpackMap ()
    {
        return backpack;
    }
}
