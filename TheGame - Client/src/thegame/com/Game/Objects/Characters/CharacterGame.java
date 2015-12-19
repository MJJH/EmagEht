package thegame.com.Game.Objects.Characters;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import thegame.com.Game.Objects.*;
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
    protected MapObject holding;
    protected long used;

    protected sides direction;

    protected java.util.Map<SkillType, Integer> skills;
    protected java.util.Map<ArmorType.bodyPart, Armor> armor;
    protected List<MapObject>[] backpack;

    protected boolean jumping = false;

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
        EnumMap<MapObject.sides,List<MapObject>> c = Collision.collision(this, false);
        if ((!c.get(sides.BOTTOM).isEmpty() || jumping) && c.get(sides.TOP).isEmpty()) {
            jumping = true;
            vSpeed += 0.05f;

            if (vSpeed >= 0.3f)
            {
                vSpeed = 0.3f;
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
                    if (!b.getType().getName().equals(b2.getType().getName()))
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

    public void removeFromBackpack(MapObject object)
    {
        int spot = -1;

        for (int i = 0; i < backpack.length; i++)
        {
            List<MapObject> l = backpack[i];
            if (l == null)
            {
                continue;
            }

            if (!l.isEmpty() && l.get(0).getClass().equals(object.getClass()) && l.size() < 99)
            {
                if (l.contains(object))
                {
                    spot = i;
                }
            }
        }

        if (spot > -1)
        {
            backpack[spot].clear();
        }
    }

    /**
     * This method will drop an object from your backpack to the map.
     *
     * @param object, the object you want to drop
     * @return returns the object and value
     */
    public Particle dropItem(int spot)
    {
        //backpack[spot].get(backpack[spot].size()-1);
        return null;
    }

    /**
     * This method let the in game character equip an armor piece.
     *
     * @param armorAdd, the armor that you want to wear
     */
    public void equipArmor(Armor armorAdd)
    {
        if (armor.get(armorAdd.getArmorType().bodypart) == null)
        {
            armor.put(armorAdd.getArmorType().bodypart, armorAdd);
            removeFromBackpack(armorAdd);
        } else
        {
            if (addToBackpack(armor.get(armorAdd.getArmorType().bodypart)))
            {
                removeFromBackpack(armorAdd);
                armor.put(armorAdd.getArmorType().bodypart, armorAdd);
            }
        }

        /*if (this instanceof Player)
         {
         Player p = (Player) this;
         Image i = (Image) p.skins.get("standLeft");
         Image i2 = (Image) p.skins.get("standRight");

         for(Armor a : armor.values()) {
         for (iTexture it : ((display.Image) a.getSkin()).getParts().keySet())
         {
         try
         {
         i.addTexture(it);
         i.flipHorizontal((Parts) it);
         i2.addTexture(it);
         } catch (IOException ex)
         {
         }
         }
         }
         }*/
    }

    /**
     * This method lets the in game character unequip an armor piece.
     *
     * @param partToUnequip
     */
    public void unequipArmor(ArmorType.bodyPart partToUnequip)
    {
        if (armor.get(partToUnequip) != null)
        {
            addToBackpack(armor.get(partToUnequip));
            armor.put(partToUnequip, null);
        }
    }

    /**
     * This method lets the in game character equip a tool.
     *
     * @param toolAdd tool to equip
     */
    private void equipTool(MapObject toolAdd)
    {
        if (holding == null)
        {
            removeFromBackpack(toolAdd);
            holding = toolAdd;
        } else if (!holding.equals(toolAdd))
        {
            if (addToBackpack(holding))
            {
                removeFromBackpack(toolAdd);
                holding = toolAdd;
            }
        }

        /* if (this instanceof Player)
         {
         Player p = (Player) this;
         Image i = (Image) p.skins.get("standLeft");
         Image i2 = (Image) p.skins.get("standRight");

         for (iTexture it : ((display.Image) toolAdd.getSkin()).getParts().keySet())
         {
         try
         {
         i.addTexture(it);
         i.flipHorizontal((Parts) it);
         i2.addTexture(it);
         } catch (IOException ex)
         {
         }
         }
         }*/
    }

    /**
     * This method lets the in game character unequip a tool.
     */
    public void unequipTool()
    {
        if (holding != null)
        {
            addToBackpack(holding);

            /*if (this instanceof Player)
             {
             Player p = (Player) this;
             Image i = (Image) p.skins.get("standLeft");
             Image i2 = (Image) p.skins.get("standRight");

             for (iTexture it : ((display.Image) holding.getSkin()).getParts().keySet())
             {
             i.removeTexture(it);
             i2.removeTexture(it);
             }
             }*/
            holding = null;
        }

    }

    /**
     * This method changes the hp of the in game character
     *
     * @param newHP the new hp
     */
    public void updateHP(int newHP)
    {
        hp = newHP;
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
    public MapObject getHolding()
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

        if (!backpack[spot].isEmpty() && !backpack[spot].get(0).getClass().equals(object.getClass()))
        {
            return false;
        }

        if (backpack[spot] == null || backpack[spot].isEmpty())
        {
            backpack[spot] = new ArrayList<>();
            backpack[spot].add(object);
            return true;
        }

        if (object instanceof Tool || object instanceof Armor)
        {
            return false;
        }

        backpack[spot].add(object);
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
                return true;
            }
        }
        return false;
    }

    public void interactWithBackpack(int spot)
    {
        if (backpack[spot] != null && !backpack[spot].isEmpty())
        {
            List<MapObject> content = backpack[spot];
            if (content.get(0) instanceof Armor)
            {
                equipArmor((Armor) content.get(0));
            } else
            {
                equipTool(content.get(0));
            }
        }
    }
}
