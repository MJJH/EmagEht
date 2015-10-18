package thegame.com.Game.Objects.Characters;

/**
 * Contains an enum for all the skilltypes in the game.
 * Each skill does have a name of the skill and a level of for that skill
 * @author laure
 */
public enum SkillType {
    
    ATTACK(10),SPEED(10);
    
    private final int lvl;

    SkillType(int lvl)
    {
        this.lvl = lvl;
    }
    
    public int getValue()
    {
        return lvl;
    }
}
