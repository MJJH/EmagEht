package thegame.com.Game.Objects.Characters;

import java.io.Serializable;

/**
 *
 * @author Nick Bijmoer
 */
public class Character implements Serializable {

    private int id;
    private String name;
    private int xp;

    /**
     * Creates a new character with a name and xp(Experience). (This is not an
     * in game character)
     *
     * @param name, Name of the character
     * @param xp, Total experience that a character got
     */
    public Character(String name, int xp)
    {
        // TODO - implement Character.Character
        setName(name);
        setXp(xp);
    }

    /**
     * In this method you level the character skill up by 1 level.
     *
     * @param skilltype, is the name of the skill you want to level up
     */
    public void levelUp(String skilltype)
    {
        // TODO - implement Character.LevelUp

    }

    /**
     *
     * @return
     */
    public int getXp()
    {
        return xp;
    }

    private void setName(String name)
    {
        if (name == null || name == "")
        {
            throw new IllegalArgumentException();
        } else
        {
            this.name = name;
        }
    }

    public String getName()
    {
        return name;
    }

    private void setXp(int xp)
    {
        if (xp < 0)
        {
            throw new IllegalArgumentException();
        } else
        {
            this.xp = xp;
        }

    }

}
