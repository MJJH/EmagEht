package thegame.com.Game.Objects;

import display.Image;
import display.Sets;
import display.Skin;
import java.io.IOException;
import java.io.Serializable;

/**
 * A class representing the type of Armor used.
 *
 * @author Mark
 */
public class ArmorType implements Serializable {

    private static final long serialVersionUID = 5529685098264757690L;

    public String name;
    public int multiplier;
    public int reqLvl;
    public transient Skin skin;
    public bodyPart bodypart;

    Skin getSkin() throws IOException
    {
        if (skin == null)
        {
            createSkin();
        }

        return skin;
    }

    private void createSkin() throws IOException
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
    }

    public enum bodyPart {

        HELMET, CHESTPLATE, GREAVES, SHIELD
    }
}
