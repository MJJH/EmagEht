/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.engine;

import java.util.ArrayList;
import java.util.List;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author Laurens
 */
public class Calculate {

    public static float distance(MapObject m1, MapObject m2)
    {
        float x1 = m1.getX();
        float x2 = m2.getX();
        float y1 = m1.getY();
        float y2 = m2.getY();
        List<MapObject.sides> sides = new ArrayList<>();
        if (x1 < x2)
        {
            sides.add(MapObject.sides.RIGHT);
        }
        if (x1 > x2)
        {
            sides.add(MapObject.sides.LEFT);
        }
        if (y1 < y2)
        {
            sides.add(MapObject.sides.TOP);
        }
        if (y1 > y2)
        {
            sides.add(MapObject.sides.BOTTOM);
        }
        if(sides.size() == 4)
        {
            sides.clear();
            sides.add(MapObject.sides.CENTER);
        }

        for (MapObject.sides side : sides)
        {
            switch (side)
            {
                case TOP:
                    y2 -= m2.getH();
                    break;
                case BOTTOM:
                    y1 += m1.getH();
                    break;
                case LEFT:
                    x2 += m2.getW();
                    break;
                case RIGHT:
                    x1 += m1.getW();
                    break;
                case CENTER:
                    x1 += m1.getW() / 2;
                    x2 += m2.getW() / 2;
                    y1 += m1.getH() / 2;
                    y2 += m2.getH() / 2;
                    break;
            }
        }

        float result = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

        return result;
    }
}
