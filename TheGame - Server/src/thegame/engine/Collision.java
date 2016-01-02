/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.engine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javafx.scene.shape.Rectangle;
import thegame.com.Game.Objects.Background;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author laure
 */
public class Collision {

    public static EnumMap<MapObject.sides, List<MapObject>> collision(MapObject subject, boolean withUnsolids)
    {
        EnumMap<MapObject.sides, List<MapObject>> collision = new EnumMap<>(MapObject.sides.class);
        collision.put(MapObject.sides.TOP, new ArrayList<>());
        collision.put(MapObject.sides.BOTTOM, new ArrayList<>());
        collision.put(MapObject.sides.LEFT, new ArrayList<>());
        collision.put(MapObject.sides.RIGHT, new ArrayList<>());
        collision.put(MapObject.sides.CENTER, new ArrayList<>());

        List<MapObject> mos = subject.getMap().getBlocksAndObjects((int) Math.round(subject.getX() - 1),
                (int) Math.round(subject.getY() - subject.getH() - 3),
                (int) Math.round(subject.getX() + subject.getW() + 1),
                (int) Math.round(subject.getY() + 2));

        for (MapObject mo : mos)
        {
            if (mo.equals(subject) || mo instanceof Background || (!withUnsolids && (mo.getS() == 0)))
            {
                continue;
            }

            List<MapObject.sides> found = collision(subject, mo);
            if (found != null)
            {
                for (MapObject.sides s : found)
                {
                    collision.get(s).add(mo);
                }
            }
        }

        return collision;
    }

    public static ArrayList<MapObject.sides> collision(MapObject subject, MapObject mo)
    {
        Rectangle r1 = new Rectangle(subject.getX(), subject.getY(), subject.getW(), subject.getH());
        Rectangle r2 = new Rectangle(mo.getX(), mo.getY(), mo.getW(), mo.getH());

        boolean right = (r2.getX() >= r1.getX() && r2.getX() <= r1.getX() + r1.getWidth());
        boolean left = (r2.getX() + r2.getWidth() >= r1.getX() && r2.getX() + r2.getWidth() <= r1.getX() + r1.getWidth());
        boolean top = (r2.getY() - r2.getHeight() <= r1.getY() && r2.getY() - r2.getHeight() >= r1.getY() - r1.getHeight());
        boolean bott = (r2.getY() >= r1.getY() - r1.getHeight() && r2.getY() <= r1.getY());

        if (right || left || top || bott)
        {
            ArrayList<MapObject.sides> ret = new ArrayList<>();
            if (bott && top && left && right)
            {
                ret.add(MapObject.sides.CENTER);
            }

            if (right && !(mo.getY() - mo.getH() >= subject.getY() || mo.getY() <= subject.getY() - subject.getH()))
            {
                ret.add(MapObject.sides.RIGHT);
            }
            if (left && !(mo.getY() - mo.getH() >= subject.getY() || mo.getY() <= subject.getY() - subject.getH()))
            {
                ret.add(MapObject.sides.LEFT);
            }
            if (top && !(mo.getX() + mo.getW() <= subject.getX() || mo.getX() >= subject.getX() + subject.getW()))
            {
                ret.add(MapObject.sides.TOP);
            }
            if (bott && !(mo.getX() + mo.getW() <= subject.getX() || mo.getX() >= subject.getX() + subject.getW()))
            {
                ret.add(MapObject.sides.BOTTOM);
            }

            return ret;
        } else
        {
            return null;
        }
    }
}
