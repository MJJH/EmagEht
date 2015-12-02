package thegame.com.Game.Objects;

import thegame.com.Game.Objects.Characters.CharacterGame;

/**
 * A particle is a MapObject that belongs to nobody and has no use but to pick
 * up
 *
 * @author Martijn
 */
public class Particle extends MapObject {

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
