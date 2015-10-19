package thegame.com.Game.Objects;

import javafx.scene.image.Image;

/**
 *
 * @author Mark
 */
public class Block extends MapObject {

    private int damage;

    /**
     * Initiates this class
     * @param type The type of the block
     * @param x the X-coordinate of its location
     * @param y the Y- coordinate of its location
     * @param skin The look of this block
     * @param height The height of this block
     * @param width The width of this block
     * @param solid A Float representing its liquefide state.
     */
    public Block(BlockType type, float x, float y, Image skin, float height, float width, float solid)
    {
        super(x, y, type.skin, height, width,solid);
        
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
