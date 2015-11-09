package thegame.com.Game.Objects;

import display.Skin;
import javafx.scene.image.Image;
import java.util.*;

/**
 * A tooltype defines what a tool will be able to do
 * @author Martijn
 */
public class ToolType {

    public final String name;
    public final int strength;
    public final float speed;
    public final float range;
    public final int reqLvl;
    public final int kb;
    public final Skin skin;
    public final float height;
    public final float width;
    public final toolType type; 
    
    public enum toolType { PICKAXE, AXE, SWORD, SHOVEL, FLINT }
    
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
    public ToolType(String name, int strength, float speed, float range, int reqLvl, toolType type, int kb, Skin skin, float height, float width) {
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
