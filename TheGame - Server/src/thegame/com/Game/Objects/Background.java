/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects;

import thegame.com.Game.Map;

/**
 *
 * @author Martijn
 */
public class Background extends Block {

    private static final long serialVersionUID = 5529681028267757690L;

    public Background(BlockType type, float x, float y, Map map)
    {
        super(type, x, y, map);
    }

    @Override
    public Boolean call()
    {
        return false;
    }

    @Override
    public void hit(Tool used, sides hitDirection)
    {
    }

}
