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

    @Override
    public void createSkin()
    {
        this.skin = BlockType.blocktypes.get("CaveStone").skin;
    }

}
