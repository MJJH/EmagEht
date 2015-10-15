package thegame.com.Game.Objects;

import java.awt.Image;

/**
 *
 * @author laure
 */
public class Block extends MapObject {

    private int damage;

    /**
     *
     * @param type
     * @param x
     * @param y
     * @param skin
     * @param height
     * @param width
     * @param solid
     */
    public Block(BlockType type, int x, int y, Image skin, int height, int width, float solid)
    {
        super(x, y, skin, height, width, solid);
    }
    
    /**
     *
     * @param tool
     * @return
     */
    public int hitBlock (Tool tool){
        return 0;
    }
}
