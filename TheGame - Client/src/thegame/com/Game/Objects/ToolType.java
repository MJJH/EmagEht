package thegame.com.Game.Objects;

import display.Image;
import display.Parts;
import display.Skin;
import java.io.IOException;
import java.io.Serializable;

/**
 * A tooltype defines what a tool will be able to do
 *
 * @author Martijn
 */
public class ToolType extends ObjectType {

    private static final long serialVersionUID = 6522685098267757690L;

    public String name;
    public int strength;
    public float speed;
    public float range;
    public int reqLvl;
    public float kb;
    public float height;
    public float width;
    public toolType type;

    Skin getSkin() throws IOException
    {
        if (skin == null)
        {
            createSkin();
        }

        return skin;
    }

    private void createSkin() throws IOException
    {
        switch (type)
        {
            case AXE:
                skin = new Image(Parts.Hatchet);
                break;
            case FLINT:
                skin = new Image(Parts.Flint);
                break;
            case PICKAXE:
                skin = new Image(Parts.PickAxe);
                break;
            case SHOVEL:
                skin = new Image(Parts.Shovel);
                break;
            case SWORD:
                skin = new Image(Parts.Sword);
                break;
        }
    }

    public enum toolType {

        PICKAXE, AXE, SWORD, SHOVEL, FLINT
    }

    /**
     *
     * @param name
     * @param strength
     * @param speed
     * @param range
     * @param reqLvl
     * @param type
     * @param kb
     * @param skin
     * @param height
     * @param width
     */
    public ToolType(String name, int strength, float speed, float range, int reqLvl, toolType type, float kb, Skin skin, float height, float width)
    {
        this.name = name;
        this.strength = strength;
        this.speed = speed;
        this.range = range;
        this.reqLvl = reqLvl;
        this.kb = kb;
        this.skin = skin;
        this.height = height;
        this.width = width;
        this.type = type;
    }

}
