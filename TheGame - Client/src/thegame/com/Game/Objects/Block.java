package thegame.com.Game.Objects;

/**
 *
 * @author Mark
 */
public class Block extends MapObject {

    private static final long serialVersionUID = 5529685098267757690L;

    private int damage;
    private BlockType type;

    @Override
    public void createSkin()
    {
        skin = type.skin;
    }
    
    public BlockType getType() {
        return type;
    }
}
