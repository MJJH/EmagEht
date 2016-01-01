/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects;

import thegame.com.Game.Map;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author Martijn
 */
public class Item extends MapObject {
    private String name;
    private MapObject[] inventory;

    public Item(ItemType type, Map playing) {
        super(1, 1, playing, true, true);
        this.inventory = new MapObject[0];
        this.name = name;
    }

    @Override
    public Boolean call() {
        return false;
    }

    @Override
    public void hit(Tool used, sides hitDirection) {
        // Break
    }
    
}
