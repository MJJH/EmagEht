package thegame.com.Game.Objects.Characters;

import display.Skin;
import java.rmi.RemoteException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import thegame.com.Game.Objects.*;

/**
 *
 * @author Laurens Adema
 */
public abstract class CharacterGame extends MapObject {

    protected int hp;
    protected final String name;
    protected float solid;

    protected Tool holding;
    protected long used;

    protected sides direction;

    protected java.util.Map<SkillType, Integer> skills;
    protected java.util.Map<ArmorType.bodyPart, Armor> armor;
    protected java.util.Map<MapObject, Integer> backpack;

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
    public CharacterGame(String name, int hp, java.util.Map<SkillType, Integer> skills, float x, float y, Skin skin, float height, float width, thegame.com.Game.Map map) throws RemoteException
    {
        super(x, y, skin, height, width, 1, map);
        this.name = name;
        this.hp = 100;
        this.skills = skills;
        backpack = new HashMap();
        armor = new HashMap();
        direction = sides.RIGHT;
        ToolType test = new ToolType("Zwaardje", 20, 1000, 1.5f, 1, ToolType.toolType.SWORD, 1, null, 1, 1);
        Tool equip = new Tool(test, map);
        equipTool(equip);

        used = System.currentTimeMillis();
    }

    public void walkRight()
    {
        direction = sides.RIGHT;
        if (hSpeed < 0)
        {
            hSpeed = 0.4f;
        } else if (hSpeed < 0.4)
        {
            hSpeed += 0.4;
        }
        
        playing.addToUpdate(this);
    }

    public void walkLeft()
    {
        direction = sides.LEFT;
        if (hSpeed > 0)
        {
            hSpeed = -0.4f;
        } else if (hSpeed > -0.4)
        {
            hSpeed -= 0.4;
        }
        
        playing.addToUpdate(this);
    }

    public void jump()
    {
        EnumMap<MapObject.sides, List<MapObject>> c = collision();
        if (!c.get(sides.BOTTOM).isEmpty())
        {
            vSpeed = 0.6f;
        }
        
        playing.addToUpdate(this);
    }

    public void knockBack(int kb, sides hitDirection)
    {
        switch (hitDirection)
        {
            case LEFT:
                hSpeed = -kb * 2;
                vSpeed = kb;
                break;
            case RIGHT:
                hSpeed = kb * 2;
                vSpeed = kb;
                break;
        }
        
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
     * @param change the ammount to change
     * @return the new HP
     */
    public int updateHP(int change)
    {
        hp -= change;

        if (hp > 100)
        {
            hp = 100;
        } else if (hp <= 0)
        {
            hp = 0;
            System.err.println("Ik ben dood!");
        }

        return hp;
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

    public boolean useTool(float x, float y)
    {
        MapObject click = playing.GetTile(x, y, this, false);
        if (click != null && holding != null && holding.type.range >= distance(click) && System.currentTimeMillis() - used >= holding.type.speed)
        {
            if (!(click instanceof Block))
            {
                if (!((xPosition <= x && direction == sides.RIGHT) || (xPosition >= x && direction == sides.LEFT)))
                {
                    return false;
                }
            }

            click.hit(holding, direction);
            used = System.currentTimeMillis();
            return true;
        } /*else if (click == null)
        {
            Block block = new Block(BlockType.Dirt, Math.round(x), Math.round(y), 1, playing);
            if(holding.type.range >= distance(block))
            {
                playing.addBlock(block, Math.round(x), Math.round(y));
                return true;
            }
            return false;
        }*/

        return false;
    }

    @Override
    public void hit(Tool used, sides hitDirection)
    {
        if (used.type.type != ToolType.toolType.SWORD && used.type.type != ToolType.toolType.FLINT)
        {
            return;
        }

        System.out.println("You hit me!");

        float armorStats = 0;
        for (Armor c : armor.values())
        {
            armorStats += c.getArmorType().multiplier;
        }

        if (updateHP((int) Math.ceil(used.type.strength - (used.type.strength * (armorStats / 100)))) == 0)
        {
            if(this instanceof Player)
            {
                this.xPosition = playing.getSpawnX();
                this.yPosition = playing.getSpawnY();
                updateHP(-100);
            }
            else
            {
                playing.removeMapObject(this);
            }
        } else
        {
            if (used.type.kb > 0)
            {
                knockBack(used.type.kb, hitDirection);
            }
        }
    }

    public sides getDirection()
    {
        return direction;
    }
}