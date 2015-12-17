/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.engine;

import java.util.EnumMap;
import java.util.List;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author laure
 */
public class Physics {

    public static boolean gravity(MapObject subject)
    {
        EnumMap<MapObject.sides, List<MapObject>> collision = Collision.collision(subject, false);

        if (collision.get(MapObject.sides.BOTTOM).isEmpty() && subject.getSY() > -1)
        {
            subject.setSY(subject.getSY() - .1f);
            return true;
        }
        return false;
    }
}
