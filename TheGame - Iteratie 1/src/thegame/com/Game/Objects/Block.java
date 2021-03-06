package thegame.com.Game.Objects;

import thegame.com.Game.Map;

/**
 *
 * @author Mark
 */
public class Block extends MapObject {

    private int damage;
    private BlockType type;

    /**
     * Initiates this class
     *
     * @param type The type of the block
     * @param x the X-coordinate of its location
     * @param y the Y- coordinate of its location
     * @param map
     */
    public Block(BlockType type, float x, float y,  Map map)
    {
        super(x, y, type.skin, 1.0f, 1.0f, type.solid, map);
        this.type = type;
    }

    /**
     *
     * @param tool
     * @return
     */
    public int hitBlock(Tool tool)
    {
        return 0;
    }

    @Override
    public void update()
    {
    }

    @Override
    public void hit(Tool use, sides hitDirection)
    {
        playing.removeMapObject(this);
    }

}
