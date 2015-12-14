package thegame.com.Game.Objects.Characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import thegame.GameServerToClientListener;
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
    protected List<MapObject>[] backpack;

    protected boolean jumping = false;

    /**
     * This method will let you add an object to your backpack.
     *
     * @param object, the specific object you want to add to your backpack
     * @return
     */
    public boolean addToBackpack(MapObject object)
    {
        int spot = -1;
        
        for(int i = 0; i < backpack.length; i++) {
            List<MapObject> l = backpack[i];
            if(l == null)
                continue;
            
            if(!l.isEmpty() && l.get(0).getClass().equals(object.getClass()) && l.size() < 99) 
                spot = i;
        }
        
        if(spot > -1)
            return addToBackpack(object, spot);
        else
            return addToEmptyBackpack(object);
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
    public void equipArmor(int spot)
    {
        if(!backpack[spot].isEmpty() && backpack[spot].get(0) instanceof Armor) {
            Armor a = (Armor) backpack[spot];
            Armor old = armor.get(a.getArmorType().bodypart);
            
            backpack[spot].clear();
            armor.put(a.getArmorType().bodypart, a);
            addToBackpack(old);
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

        if (hp == 0 && this instanceof Player && this == playing.getMe())
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

    public boolean addToBackpack(MapObject object, int spot) {
        if(spot > backpack.length-1)
            return false;
        
        if(!backpack[spot].isEmpty() && !backpack[spot].get(0).getClass().equals(object.getClass()))
            return false;
        
        if(backpack[spot] == null || backpack[spot].isEmpty()){
            backpack[spot] = new ArrayList<>();
            backpack[spot].add(object);
            return true;
        }
        
        if(object instanceof Tool || object instanceof Armor) {
            return false;
        } 
        
        backpack[spot].add(object);
        return true;
    }

    public boolean addToEmptyBackpack(MapObject object) {
         for(int i = 0; i < backpack.length; i++) {
             if(backpack[i] == null || backpack[i].isEmpty()){
                backpack[i] = new ArrayList<>();
                backpack[i].add(object);
                return true;
             }
         } 
         return false;
    }
}
