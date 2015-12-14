package thegame.com.Game.Objects;

import display.Image;
import display.Parts;
import display.Sets;
import display.Skin;
import java.io.IOException;


/**
 * A class representing the type of Armor used.
 * @author Mark 
 */
public class ArmorType {

    public final String name;
    public final int multiplier;
    public final int reqLvl;
    public Skin skin;
    public final bodyPart bodypart;

    Skin getSkin() throws IOException {
        if(skin == null)
            createSkin();
        
        return skin;
    }

    private void createSkin() throws IOException {
        switch(bodypart) {
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
    }

    public enum bodyPart { HELMET, CHESTPLATE, GREAVES, BOOTS, SHIELD }

    /**
     * Initiates an instance of this class with the following attributes
     * @param name The name of the ArmorType 
     * @param multiplier The multiplier of the Armortype
     * @param reqLvl The required level of the Armortype
     * @param skin The look of the Armortype
     * @param bodyPart The place on the body where to wear the Armor
     */
    public ArmorType(String name, int multiplier, int reqLvl, Skin skin, bodyPart bodyPart)
    {
     this.name = name;
     this.multiplier = multiplier;
     this.reqLvl = reqLvl;
     this.skin = skin;
     this.bodypart = bodyPart;
    }
    
}
