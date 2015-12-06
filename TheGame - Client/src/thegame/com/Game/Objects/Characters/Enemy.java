package thegame.com.Game.Objects.Characters;

import thegame.com.Game.Objects.MapObject;

/**
 * This constructor creates an enemy
 *
 * @author Nick Bijmoer
 */
public class Enemy extends CharacterGame {
    private static final long serialVersionUID = 6729685098267757690L;
    private final float distanceX = 30;
    private final float distanceY = 10;

    @Override
    public void createSkin() {
        return;
    }

    public void update(Enemy update)
    {
        setX(update.getX());
        setY(update.getY());
        updateHP(update.getHP());
    }
}
