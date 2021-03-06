package thegame.com.Game.Objects;

import display.Image;
import display.Sets;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

/**
 * A class representing the type of Armor used.
 *
 * @author Mark
 */
public class ArmorType extends ObjectType {

    private static final long serialVersionUID = 5529685098264757690L;

    public static Map<String, ArmorType> armortypes = new HashMap<>();
    public int multiplier;
    public int reqLvl;
    public bodyPart bodypart;

    public ArmorType(String name, int multiplier, int reqLvl, bodyPart bodypart, Sets skin, Color[] colors) throws IOException
    {
        super(name, skin, colors);
        this.multiplier = multiplier;
        this.reqLvl = reqLvl;
        this.bodypart = bodypart;

        armortypes.put(name, this);
    }
    /*
     @Override
     protected void createSkin() throws IOException
     {
     switch (bodypart)
     {
     case CHESTPLATE:
     skin = new Image(Sets.bodyArmor);
     break;
     case GREAVES:
     skin = new Image(Sets.legArmor);
     break;
     case HELMET:
     skin = new Image(Sets.SpikeHelmet);
     break;
     }
     }*/

    public enum bodyPart implements Serializable{

        HELMET, CHESTPLATE, GREAVES, SHIELD
    }
}
