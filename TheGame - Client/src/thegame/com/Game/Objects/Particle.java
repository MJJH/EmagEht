package thegame.com.Game.Objects;

import java.util.logging.Level;
import java.util.logging.Logger;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.config;

/**
 * A particle is a MapObject that belongs to nobody and has no use but to pick
 * up
 *
 * @author Martijn
 */
public class Particle extends MapObject {

    private static final long serialVersionUID = 5529682098267757690L;

    private MapObject object;
    private int count;

    private void setObject(MapObject object)
    {
        if (object == null
                || object instanceof CharacterGame
                || object instanceof Liquid)
        {
            throw new IllegalArgumentException("Illegal object as Particle");
        } else
        {
            this.object = object;
        }
    }

    @Override
    public void createSkin()
    {
        try
        {
            skin = object.getSkin().clone();
            skin.setHeight((int) (height * config.block.val));
            skin.setWidth((int) (height * config.block.val));
        } catch (CloneNotSupportedException ex)
        {
            Logger.getLogger(Particle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(MapObject update)
    {
        setX(update.getX());
        setY(update.getY());
    }
    
    @Override
    public void setMap(Map set)
    {
        this.playing = set;
        object.setMap(set);
    }
}
