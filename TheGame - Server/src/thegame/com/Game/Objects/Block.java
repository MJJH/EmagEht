package thegame.com.Game.Objects;

import java.rmi.RemoteException;
import thegame.GameClientToServerHandler;
import thegame.com.Game.Map;

/**
 *
 * @author Mark
 */
public class Block extends MapObject {

    private static final long serialVersionUID = 5529685098267757690L;

    private int damage;
    private BlockType type;

    private boolean interaction;

    /**
     * Initiates this class
     *
     * @param type The type of the block
     * @param x the X-coordinate of its location
     * @param y the Y- coordinate of its location
     * @param map
     */
    public Block(BlockType type, float x, float y, Map map)
    {
        super(x, y, 1.0f, 1.0f, type.solid, map, false, true);
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

    public BlockType getBlockType()
    {
        return type;
    }

    @Override
    public Boolean call()
    {
        if (interaction)
        {
            if (damage > type.strength)
            {
                MapObject particleStack = null;
                float curDifX = 100;
                for (MapObject object : playing.getObjects(xPosition, yPosition, 1.1f))
                {
                    if (object instanceof Particle)
                    {
                        if (particleStack == null)
                        {
                            particleStack = object;
                        } else
                        {
                            if (curDifX > Math.abs(object.getX() - xPosition))
                            {
                                curDifX = Math.abs(object.getX() - xPosition);
                                particleStack = object;
                            }
                        }
                    }
                }
                if (particleStack != null)
                {
                    Particle particle = (Particle) particleStack;
                    if (particle.getObject() instanceof Block)
                    {
                        Block block = (Block) particle.getObject();
                        if (block.type == type)
                        {
                            particle.addObjectCount();
                            playing.addMapObject(new Background(BlockType.CaveStone, xPosition, yPosition, playing));
                            return false;
                        }
                    }
                }

                playing.addMapObject(new Particle(this, xPosition, yPosition, playing));
                playing.addMapObject(new Background(BlockType.CaveStone, xPosition, yPosition, playing));
                return false;
            }
            interaction = false;
            return false;
        } else
        {
            return false;
        }
    }

    @Override
    public void hit(Tool use, sides hitDirection)
    {
        if (type.reqTool == use.type.type || use.type.type == ToolType.toolType.FLINT)
        {
            damage += use.type.strength;
            interaction = true;
            playing.addToUpdate(this);
        }
    }

}
