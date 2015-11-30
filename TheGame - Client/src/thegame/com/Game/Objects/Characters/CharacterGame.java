package thegame.com.Game.Objects.Characters;

import display.Skin;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import thegame.com.Game.Objects.*;
import thegame.shared.iGameLogic;

/**
 *
 * @author Laurens Adema
 */
public abstract class CharacterGame extends MapObject {
    private static final long serialVersionUID = 5539685098267757690L;
    
    protected int hp;
    protected final String name;
    protected float solid;

    protected Tool holding;
    protected long used;

    protected sides direction;

    protected java.util.Map<SkillType, Integer> skills;
    protected java.util.Map<ArmorType.bodyPart, Armor> armor;
    protected java.util.Map<MapObject, Integer> backpack;
    
    protected boolean jumping = false;

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
     * @param map
     */
    public CharacterGame(String name, int hp, java.util.Map<SkillType, Integer> skills, float x, float y, Skin skin, float height, float width, thegame.com.Game.Map map)
    {
        super(x, y, skin, height, width, 1, map);
        this.name = name;
        this.hp = 100;
        this.skills = skills;
        backpack = new HashMap();
        armor = new HashMap();
        direction = sides.RIGHT;
        ToolType test = new ToolType("Zwaardje", 20, 1000, 3f, 1, ToolType.toolType.SWORD, 1, null, 1, 1);
        Tool equip = new Tool(test, map);
        equipTool(equip);

        used = System.currentTimeMillis();
    }

    public void walkRight()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        direction = sides.RIGHT;
        
        if(!c.get(sides.RIGHT).isEmpty())
            return;
        
        if (hSpeed < 0)
        {
            hSpeed = 0.1f;
        } else if (hSpeed < 0.5)
        {
            hSpeed += 0.1;
        }
        
        playing.addToUpdate(this);
    }

    public void walkLeft()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        direction = sides.LEFT;
        
        if(!c.get(sides.LEFT).isEmpty())
            return;
        
        if (hSpeed > 0)
        {
            hSpeed = -0.1f;
        } else if (hSpeed > -0.5)
        {
            hSpeed -= 0.1;
        }
        
        playing.addToUpdate(this);
    }

    public void jump()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        if ((!c.get(sides.BOTTOM).isEmpty() || jumping) && c.get(sides.TOP).isEmpty())
        {
            jumping = true;
            vSpeed += 0.2f;
        
            if(vSpeed >= 0.8f) {
                vSpeed = 0.8f;
                jumping = false;
            }
        }
        
        playing.addToUpdate(this);
    }
    
    public void stopJump() {
        jumping = false;
        
        playing.addToUpdate(this);
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
     * @param change the new hp
     */
    public void updateHP(int change)
    {
        hp = change;
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
