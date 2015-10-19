package thegame.com.Game.Objects.Characters;

import java.awt.Image;
import java.util.ArrayList;
import thegame.com.Game.Objects.*;

/**
 *
 * @author Nick Bijmoer
 */
public class CharacterGame extends MapObject {
  
    private int hp;
    private String name;
    private ArrayList<SkillType> skills = new ArrayList<>();
    private int x;
    private int y;
    private Image skin;
    private int height;
    private int width;
    private float solid;
    

    /**
     * This constructor creates a new in game character.
     * @param name, Name of the character
     * @param hp, HP of the character
     * @param skills, Arraylist of the skills that the character got
     * @param x, X-Coordinate of the in game character
     * @param y, Y-Coordinate of the in game character
     * @param skin, Skin that the in game character got
     * @param height, Height of the in game character
     * @param width, Width of the in game character
     * @param solid, Density of the charactergame
     * @param parameter
     */
    public CharacterGame(String name, int hp, ArrayList<SkillType> skills, int x, int y, Image skin, int height, int width, float solid)
    {
        super(x, y, skin, height, width, solid);
        this.skills = skills;
        this.name = name;
        this.x = x;
        this.y = y;
        this.skin = skin;
        this.height = height;
        this.width = width;
        solid = 0;
        
    }

    /**
     * This method will let you add an object to your backpack.
     * @param object, the specific object you want to add to your backpack
     * @return 
     */
    public boolean addToBackpack(MapObject object)
    {
        return false;
    }

    /**
     * This method will drop an object from your backpack to the map.
     * @param object, the object you want to drop
     */
    public void dropItem(MapObject object)
    {

    }

    /**
     * This method let the in game character wear an armor piece.
     * @param armor, is the armor piece that you want to wear.
     */
    public void wearArmor(Armor armor)
    {

    }

}
