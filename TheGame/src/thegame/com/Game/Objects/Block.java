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
     * @param solid A Float representing its liquefide state.
     */
    public Block(BlockType type, float x, float y, float solid)
    {
        super(x, y, type.skin, 1.0f, 1.0f, solid);
        
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
