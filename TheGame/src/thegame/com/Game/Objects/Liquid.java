package thegame.com.Game.Objects;

import javafx.scene.image.Image;

/**
 * A liquid is a type of block that will devide its content around it
 * @author Martijn
 */
public class Liquid extends Block {


    private float volume;

    /**
     * Creates a new Liquid block in the world
     * @param blockType the type this block is
     * @param volume    the volume of the liquid still in this block
     * @param x         the horizontal position of this object
     * @param y         the vertical position of this object
     * @param height    the height of this object
     * @param width     the width of this object
     * @param solid     the density of this object
     */
    
    public Liquid(BlockType blockType, float volume, int x, int y, int height, int width, float solid)
    {
        super(blockType, x, y, height, width, solid);
        this.volume = volume;
    }
    
    /**
     * Set the volume of this liquid to a new value
     * @param volume volume must be between 0 and 1. 1 Will be a full block worth of liquid, 0 will be empty.
     */
    public void setVolume(float volume) {
        if(volume <= 0)
            volume = 0;
        if(volume >= 1)
            volume = 1;
        
        this.volume = volume;
    }
    
    /**
     * Change the volume relative to his current value
     * @param add
     */
    public void changeVolume(float add) {
        setVolume(this.volume + add);
    }
    
    /**
     * get the volume
     * @return the volume
     */
    public float getVolume() {
        return volume;
    }
}
