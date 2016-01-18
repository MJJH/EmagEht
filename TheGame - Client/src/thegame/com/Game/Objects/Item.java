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
    private ItemType type;

    @Override
    public void createSkin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(MapObject update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
@Override
    public void setType() {
        this.type = ItemType.itemtypes.get(this.type.name);
    }

    @Override
    public ObjectType getType() {
        return type;
    }

    
}
