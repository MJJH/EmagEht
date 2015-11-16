/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author laure
 */
public interface iCharacterGame {
    public java.util.Map dropItem(MapObject object);
    public void equipArmor(Armor armorAdd);
    public void unequipArmor(Armor armorDel);
}
