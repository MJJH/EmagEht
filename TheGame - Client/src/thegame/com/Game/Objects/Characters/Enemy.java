package thegame.com.Game.Objects.Characters;

import display.Skin;
import java.rmi.RemoteException;
import java.util.*;
import javafx.scene.image.Image;
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

    /**
     *
     * @param name, name of the enemy
     * @param hp, HP of the enemy
     * @param skills, Skills that the enemy has
     * @param x, X- Coordinate of the enemy
     * @param y, Y- Coordinate of the enemy
     * @param skin, Skin that the enemy has
     * @param height, Height of the enemy
     * @param width, Width of the enemy
     * @param map
     */
    public Enemy(String name, int hp, java.util.Map<SkillType, Integer> skills, float x, float y, Skin skin, float height, float width, thegame.com.Game.Map map) throws RemoteException
    {
        super(name, hp, skills, x, y, skin, height, width, map);
    }

    @Override
    public Boolean call(){return false;}

    @Override
    public void createSkin() {
        return;
    }
}
