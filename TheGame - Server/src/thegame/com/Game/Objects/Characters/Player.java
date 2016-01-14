package thegame.com.Game.Objects.Characters;

import thegame.com.Game.Map;
import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
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

        ToolType test = new ToolType("Flintje", 20, 1000, 3f, 1, ToolType.toolType.FLINT, 0.3f);
        Tool equip = new Tool(test, map);
        equipTool(equip);

        this.addToBackpack(new Tool(new ToolType("Test", 0, 0, 0, 0, ToolType.toolType.AXE, 0), playing));
        for (int c = 0; c < 103; c++)
        {
            this.addToBackpack(new Block(BlockType.blocktypes.get("Stone"), 0, 0, playing));
        }

        /*this.addToBackpack(new Armor(new ArmorType("Helm", 10, 0, ArmorType.bodyPart.HELMET), 1, 1, playing));
        this.addToBackpack(new Armor(new ArmorType("ChestBrah", 1, 0, ArmorType.bodyPart.CHESTPLATE), 1, 1, playing));
        this.addToBackpack(new Armor(new ArmorType("Broekie!", 0, 0, ArmorType.bodyPart.GREAVES), 1, 1, playing));*/
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
        /*EnumMap<sides, List<MapObject>> collision = collision();
         Boolean ret = false;
         if(fall(collision))
         ret = true;
        
         if(moveH(collision))
         ret = true;
        
         if(moveV(collision))
         ret = true;
        
         return ret;*/

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
}
