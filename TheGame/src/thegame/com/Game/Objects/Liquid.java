package thegame.com.Game.Objects;

import java.awt.Image;

/**
 *
 * @author laure
 */
public class Liquid extends Block {

    private float volume;

    /**
     *
     * @param blockType
     * @param volume
     * @param x
     * @param y
     * @param skin
     * @param height
     * @param width
     * @param solid
     */
    public Liquid(BlockType blockType, float volume, int x, int y, Image skin, int height, int width, float solid)
    {
        super(blockType, x, y, skin, height, width, solid);
        // TODO - implement Liquid.Liquid
        throw new UnsupportedOperationException();
    }

}
