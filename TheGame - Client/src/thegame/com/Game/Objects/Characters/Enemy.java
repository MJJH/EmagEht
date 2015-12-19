package thegame.com.Game.Objects.Characters;

import thegame.com.Game.Objects.MapObject;

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

    @Override
    public void update(MapObject update)
    {
        if (update instanceof Enemy)
        {
            Enemy updateEnemy = (Enemy) update;
            setX(updateEnemy.getX());
            setY(updateEnemy.getY());
            updateHP(updateEnemy.getHP());
        }
    }
}
