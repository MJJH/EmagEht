package thegame.com.Game.Objects.Characters;

import java.util.List;
import thegame.com.Game.Crafting;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.ObjectType;
import thegame.com.Game.Objects.Tool;
import thegame.com.Game.Objects.ToolType;
import thegame.com.Menu.Account;

/**
 *
 * @author laure
 */
public class Player extends CharacterGame {

    private static final long serialVersionUID = 6629685098267757690L;
    private boolean connected;
    private float spawnX;
    private float spawnY;
    private Account account;
    private Character character;

    /**
     * This constructor will create an player
     *
     * @param account
     * @param character,
     * @param name, Name of the player
     * @param hp, HP of the player
     * @param skills, Skills of the player
     * @param attacks, Attacks of the player
     * @param x, X-coordinate of the player
     * @param y, Y-coordinate of the player
     * @param height, height of the player
     * @param width, width of the player
     * @param map
     */
    public Player(Account account, Character character, String name, int hp, java.util.Map<SkillType, Integer> skills, AttackType[] attacks, float x, float y, float height, float width, Map map)
    {
        super(name, hp, skills, x, y, height, width, map);

        spawnX = playing.getSpawnX();
        spawnY = playing.getSpawnY();
        this.character = character;
        this.account = account;

        ToolType flint = ToolType.tooltypes.get("Flint");
        Tool equip = new Tool(flint, map);
        this.addToBackpack(equip);
        equipTool(0);

        this.addToBackpack(new Tool(ToolType.tooltypes.get("Wooden Shovel"), playing));
        for (int c = 0; c < 103; c++)
        {
            this.addToBackpack(new Block(BlockType.blocktypes.get("Stone"), 0, 0, playing));
        }

        this.addToBackpack(new Armor(ArmorType.armortypes.get("Wooden Helmet"), 0, 0, playing));
        this.addToBackpack(new Armor(ArmorType.armortypes.get("Stone Helmet"), 0, 0, playing));
        this.addToBackpack(new Armor(ArmorType.armortypes.get("Steel Helmet"), 0, 0, playing));
        this.addToBackpack(new Armor(ArmorType.armortypes.get("Wooden Shield"), 0, 0, playing));
        this.addToBackpack(new Armor(ArmorType.armortypes.get("Wooden Greaves"), 0, 0, playing));
        this.addToBackpack(new Armor(ArmorType.armortypes.get("Wooden Chestplate"), 0, 0, playing));
        this.addToBackpack(new Tool(ToolType.tooltypes.get("Cheat Flint"), playing));
        this.addToBackpack(new Tool(ToolType.tooltypes.get("Wooden Axe"), playing));
        this.addToBackpack(new Tool(ToolType.tooltypes.get("Wooden Pickaxe"), playing));
        this.addToBackpack(new Tool(ToolType.tooltypes.get("Wooden Sword"), playing));
        
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

    @Override
    public Boolean call()
    {
        return false;
    }

    public void setCords(float x, float y)
    {
        setY(y);
        setX(x);
    }

    public void setDirection(sides directionSide)
    {
        direction = directionSide;
    }

    @Override
    public void knockBack(float kb, sides hitDirection)
    {
        float newHSpeed = 0;
        float newVSpeed = 0;

        switch (hitDirection)
        {
            case LEFT:
                newHSpeed = -kb;
                newVSpeed = kb;
                break;
            case RIGHT:
                newHSpeed = kb;
                newVSpeed = kb;
                break;
        }

        playing.getGameServerToClientHandler().knockBackPlayer(this, newHSpeed, newVSpeed);
    }

    @Override
    public int updateHP(int change)
    {
        hp -= change;

        if (hp > 100)
        {
            hp = 100;
        } else if (hp <= 0)
        {
            hp = 0;
        }

        return hp;
    }

    public void setSpawn(float x, float y)
    {
        spawnX = x;
        spawnY = y;
    }

    public Account getAccount()
    {
        return account;
    }

    public Character getCharacter()
    {
        return character;
    }

    // TODO @LaurensAdema hier is een tweede check
    public boolean Craft(Crafting to_craft)
    {
        java.util.Map<ObjectType, Integer> need = to_craft.recources;
        for (ObjectType ot : need.keySet())
        {
            int left = need.get(ot);
            int i = 0;
            while (left > 0 && i < 30)
            {
                if (backpack[i].get(0).getType() == ot)
                {
                    left -= backpack[i].size();
                }
                i++;
            }
            if (left > 0)
            {
                return false;
            }
        }
        // Success!
        for (ObjectType ot : need.keySet())
        {
            //this.
        }
        return true;
    }

    public void interactWithBackpack(int spot, action action)
    {
        if (backpack[spot] != null && !backpack[spot].isEmpty())
        {
            List<MapObject> content = backpack[spot];
            switch (action)
            {
                case CLICK:
                    if (content.get(0) instanceof Armor)
                    {
                        Armor armorPiece = equipArmor(spot);
                        if (armor != null)
                        {
                            playing.getGameServerToClientHandler().equipArmor(armorPiece, this);
                        }
                    } else
                    {
                        List<MapObject> objects = equipTool(spot);
                        if (objects != null || objects.size() > 0)
                        {
                            playing.getGameServerToClientHandler().equipTool(objects, this);
                        }
                    }
                    break;
                case DROP:
                    dropItem(spot, 1);
                    break;
                case DROPALL:
                    dropItem(spot, content.size());
                    break;
                case SELECT:
                    break;
            }
        }
    }
}
