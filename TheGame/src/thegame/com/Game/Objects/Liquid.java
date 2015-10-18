package thegame.com.Game.Objects;

import java.awt.Image;

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
     * @param skin      the texture of this object
     * @param height    the height of this object
     * @param width     the width of this object
     * @param solid     the density of this object
     */
    
    public Liquid(BlockType blockType, float volume, int x, int y, Image skin, int height, int width, float solid)
    {
        super(blockType, x, y, skin, height, width, solid);
        // TODO - implement Liquid.Liquid
        throw new UnsupportedOperationException();
    }

}
