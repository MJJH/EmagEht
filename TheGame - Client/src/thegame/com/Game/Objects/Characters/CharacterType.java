package thegame.com.Game.Objects.Characters;

import thegame.com.Game.Objects.*;
import java.util.List;
import java.util.Map;

/**
 *
 * @author laure
 */
public class CharacterType {

    /**
     * Give the name of the character
     *
     * @Return name
     */
    public final String name;

    private int health;

    /**
     * Give the starting items of the character
     *
     * @Return startItems
     */
    public final List<MapObject> startItems;

    /**
     * Give the starting skill levels of the character
     *
     * @Return startSkills
     */
    public final Map<SkillType, Integer> startSkills;

    /**
     * Chreate a new charactertype with a name and starting options
     *
     * @param name
     * @param startItems
     * @param startSkills
     */
    public CharacterType(String name, List<MapObject> startItems, Map<SkillType, Integer> startSkills)
    {
        this.name = name;
        this.startItems = startItems;
        this.startSkills = startSkills;
        health = 100;
    }

    public String getName()
    {
        return name;
    }

    public List<MapObject> getStartItems()
    {
        return startItems;
    }

    public Map<SkillType, Integer> getStartSkills()
    {
        return startSkills;
    }

}
