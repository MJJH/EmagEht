package thegame.com.Game.Objects;

import thegame.com.Game.Objects.Characters.CharacterGame;

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
        //create skin particle
    }
}
