package thegame.com.Game.Objects.Characters;

import display.Image;
import display.Sets;
import display.Skin;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
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
    protected List<MapObject> holding;
    protected sides direction;
    protected boolean jumping = false;

    protected java.util.Map<SkillType, Integer> skills;
    protected java.util.Map<ArmorType.bodyPart, Armor> armor;
    protected List<MapObject>[] backpack;

    public transient HashMap<String, Skin> skins;

    protected transient long used;

    public enum action implements Serializable {

        DROP, DROPALL, SELECT, CLICK
    }

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

        if (l.size()>0)
        {
            while (amount > 0 && l.size() > 0)
            {
                removed.add(l.remove(l.size() - 1));
                amount--;
            }
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

        for (MapObject mo : backpack[spot])
        {
            removed.add(mo);
        }
        backpack[spot] = new ArrayList<>();
        return removed;
    }

    /**
     * This method let the in game character equip an armor piece.
     *
     * @param armorAdd, the armor that you want to wear
     */
    public void equipArmor(Armor add)
    {
        for (Skin i : this.skins.values())
        {
            try
            {
                Image im = (Image) i;
                if (armor.get(add.getArmorType().bodypart) != null)
                {
                    im.removeTexture(armor.get(add.getArmorType().bodypart).getType().texture);
                }
                im.addTexture(add.getType().texture, add.getType().colorset);
            } catch (IOException ex)
            {
                Logger.getLogger(CharacterGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (armor.get(add.getArmorType().bodypart) == null)
        {
            armor.put(add.getArmorType().bodypart, add);
        } else
        {
            armor.put(add.getArmorType().bodypart, add);
        }

    }

    /**
     * This method lets the in game character unequip an armor piece.
     *
     * @param partToUnequip
     */
    public void unequipArmor(ArmorType.bodyPart partToUnequip)
    {
        try
        {
            if (armor.get(partToUnequip) != null && playing.getGameClientToServer().unequipArmor(playing.getLobby().getID(), id, partToUnequip))
            {
                for (Skin i : this.skins.values())
                {
                    Image im = (Image) i;
                    if (armor.get(partToUnequip) != null)
                    {
                        im.removeTexture(armor.get(partToUnequip).getType().texture);
                    }
                }
                armor.put(partToUnequip, null);
            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(CharacterGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method lets the in game character equip a tool.
     *
     * @param toolAdd tool to equip
     */
    public void equipTool(List<MapObject> add)
    {
        for (Skin i : this.skins.values())
        {
            try
            {
                Image im = (Image) i;
                if (holding != null && holding.size() > 0)
                {
                    im.removeTexture(holding.get(0).getType().texture);
                }
                im.addTexture(add.get(0).getType().texture, add.get(0).getType().colorset);
            } catch (IOException ex)
            {
                Logger.getLogger(CharacterGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (holding == null)
        {
            holding = add;
        } else
        {
            holding = add;
        }
    }

    /**
     * This method lets the in game character unequip a tool.
     */
    public void unequipTool()
    {
        try
        {
            if (holding != null && playing.getGameClientToServer().unequipTool(playing.getLobby().getID(), id))
            {
                holding = null;

                for (Skin i : this.skins.values())
                {
                    Image im = (Image) i;
                    if (holding != null && holding.size() > 0)
                    {
                        im.removeTexture(holding.get(0).getType().texture);
                    }
                }
            }
        } catch (RemoteException ex)
        {
            Logger.getLogger(CharacterGame.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setArmor(java.util.Map<ArmorType.bodyPart, Armor> armor)
    {
        this.armor = armor;
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

    public void interactWithBackpack(int spot, CharacterGame.action action)
    {
        if (backpack[spot] != null && !backpack[spot].isEmpty())
        {
            try
            {
                playing.getGameClientToServer().interactWithBackpack(playing.getLobby().getID(), id, spot, action);
            } catch (RemoteException ex)
            {
                Logger.getLogger(CharacterGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

            for (Armor armorPiece : this.getArmor().values())
            {
                for (Skin i : this.skins.values())
                {
                    try
                    {
                        Image im = (Image) i;
                        im.addTexture(armorPiece.getType().texture);
                        armorPiece.setType();
                        im.addTexture(armorPiece.getType().texture, armorPiece.getType().colorset);
                    } catch (IOException ex)
                    {
                        Logger.getLogger(CharacterGame.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            if (holding != null && holding.size() > 0)
            {
                for (Skin i : this.skins.values())
                {
                    holding.get(0).setType();
                    ((Image) i).addTexture(holding.get(0).getType().texture, holding.get(0).getType().colorset);
                }
            }

        } catch (IOException ex)
        {
            Logger.getLogger(Player.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setType()
    {

    }

    @Override
    public ObjectType getType()
    {
        return null;
    }

    public void setHolding(List<MapObject> holding)
    {
        this.holding = holding;
    }
}
