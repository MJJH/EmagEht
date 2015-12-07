package thegame.com.Game.Objects.Characters;

/**
 * This constructor creates an enemy
 *
 * @author Nick Bijmoer
 */
public class Enemy extends CharacterGame {

    private static final long serialVersionUID = 6729685098267757690L;

    @Override
    public void createSkin()
    {
        return;
    }

    public void update(Enemy update)
    {
        setX(update.getX());
        setY(update.getY());
        updateHP(update.getHP());
    }
}
