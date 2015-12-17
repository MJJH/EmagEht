/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.engine;

import java.util.EnumMap;
import java.util.List;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author laure
 */
public class Movement {

    public static boolean moveH(MapObject subject)
    {
        EnumMap<MapObject.sides, List<MapObject>> collision = Collision.collision(subject,false);
        List<MapObject> found;

        if (subject.getSX() > 0)
        {
            subject.setSX(subject.getSX() - 0.04f);
            if (subject.getSX() <= 0)
            {
                subject.setSX(0);
            }

            found = collision.get(MapObject.sides.RIGHT);
            if (found.isEmpty())
            {
                subject.setX(subject.getX() + subject.getSX());

                found = Collision.collision(subject, false).get(MapObject.sides.RIGHT);
                if (!found.isEmpty())
                {
                    float minX = -1;
                    for (MapObject mo : found)
                    {
                        if (minX == -1 || mo.getX() < minX)
                        {
                            minX = mo.getX();
                        }
                    }

                    subject.setSX(0);
                    subject.setX(minX - subject.getW());
                }

                return true;
            } else
            {
                float minX = -1;
                for (MapObject mo : found)
                {
                    if (minX == -1 || mo.getX() < minX)
                    {
                        minX = mo.getX();
                    }
                }

                if (minX == -1)
                {
                    subject.setX(subject.getX() + subject.getSX());
                    return true;
                }

                subject.setSX(0);
                subject.setX(minX - subject.getW());
                return true;
            }
        } else if (subject.getSX() < 0)
        {
            subject.setSX(subject.getSX() + 0.04f);
            if (subject.getSX() >= 0)
            {
                subject.setSX(0);
            }

            found = collision.get(MapObject.sides.LEFT);
            if (found.isEmpty())
            {
                subject.setX(subject.getX() + subject.getSX());

                found = Collision.collision(subject, false).get(MapObject.sides.LEFT);
                if (!found.isEmpty())
                {
                    float maxX = -1;
                    for (MapObject mo : found)
                    {

                        if (maxX == -1 || mo.getX() + mo.getW() > maxX)
                        {
                            maxX = mo.getX() + mo.getW();
                        }
                    }

                    if (maxX == -1)
                    {
                        subject.setX(subject.getX() + subject.getSX());
                        return true;
                    }

                    subject.setSX(0);
                    subject.setX(maxX);
                }

                return true;
            } else
            {
                float maxX = -1;
                for (MapObject mo : found)
                {

                    if (maxX == -1 || mo.getX() + mo.getW() > maxX)
                    {
                        maxX = mo.getX() + mo.getW();
                    }
                }
                
                subject.setSX(0);
                subject.setX(maxX);
                return true;
            }
        }

        return false;
    }

    public static boolean moveV(MapObject subject)
    {
        EnumMap<MapObject.sides, List<MapObject>> collision = Collision.collision(subject, false);
        List<MapObject> found;

        if (subject.getSY() > 0)
        {
            found = collision.get(MapObject.sides.TOP);
            if (found.isEmpty())
            {
                subject.setY(subject.getY() + subject.getSY());

                found = Collision.collision(subject, false).get(MapObject.sides.TOP);
                if (!found.isEmpty())
                {
                    float minY = -1;
                    for (MapObject mo : found)
                    {

                        if (minY == -1 || mo.getY() < minY)
                        {
                            minY = mo.getY() - mo.getH();
                        }
                    }
                    
                    subject.setSY(0);
                    subject.setY(minY);
                }

                return true;
            } else
            {
                float minY = -1;
                for (MapObject mo : found)
                {

                    if (minY == -1 || mo.getY() < minY)
                    {
                        minY = mo.getY() - mo.getH();
                    }
                }

                if (minY == -1)
                {
                    subject.setY(subject.getY() + subject.getSY());
                    return true;
                }

                subject.setSY(0);
                subject.setY(minY);
                return true;
            }
        } else if (subject.getSY() < 0)
        {
            if (subject instanceof Enemy)
            {
                ((Enemy) subject).stopJump();
            }

            found = collision.get(MapObject.sides.BOTTOM);
            if (found.isEmpty())
            {
                subject.setY(subject.getY() + subject.getSY());

                found = Collision.collision(subject, false).get(MapObject.sides.BOTTOM);
                if (!found.isEmpty())
                {
                    float maxY = -1;
                    for (MapObject mo : found)
                    {

                        if (maxY == -1 || mo.getY() > maxY)
                        {
                            maxY = mo.getY() + subject.getH();
                        }
                    }

                    subject.setSY(0);
                    subject.setY(maxY);
                }

                return true;
            } else
            {
                float maxY = -1;
                for (MapObject mo : found)
                {

                    if (maxY == -1 || mo.getY() > maxY)
                    {
                        maxY = mo.getY() + subject.getH();
                    }
                }

                if (maxY == -1)
                {
                    subject.setY(subject.getY() + subject.getSY());
                    return true;
                }

                subject.setSY(0);
                subject.setY(maxY);

                return true;
            }
        }

        return false;
    }
}
