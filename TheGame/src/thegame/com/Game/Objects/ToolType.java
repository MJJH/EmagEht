package thegame.com.Game.Objects;

import javafx.scene.image.Image;
import java.util.*;

/**
 * A tooltype defines what a tool will be able to do
 * @author Martijn
 */
public class ToolType {

    /**
     *
     */
    public final String name;

    /**
     *
     */
    public final int strength;

    /**
     *
     */
    public final int speed;

    /**
     *
     */
    public final int range;

    /**
     *
     */
    public final int reqLvl;

    /**
     *
     */
    public final int kb;

    /**
     *
     */
    public final Image skin;

    /**
     *
     */
    public final float height;

    /**
     *
     */
    public final float width;
    
    /**
     *
     * @param name
     * @param strength
     * @param speed
     * @param range
     * @param reqLvl
     * @param kb
     * @param skin
     * @param height
     * @param width
     */
    public ToolType(String name, int strength, int speed, int range, int reqLvl, int kb, Image skin, float height, float width) {
        this.name = name;
        this.strength = strength;
        this.speed = speed;
        this.range = range;
        this.reqLvl = reqLvl;
        this.kb = kb;
        this.skin = skin;
        this.height = height;
        this.width = width;
    }

}
