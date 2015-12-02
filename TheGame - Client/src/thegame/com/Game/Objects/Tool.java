package thegame.com.Game.Objects;

import thegame.com.Game.Map;

/**
 * A tool is an object that a character can use to mine, fight, dig or chop
 *
 * @author Martijn
 */
public class Tool extends MapObject {
    private static final long serialVersionUID = 6529585098267757690L;

    public ToolType type;

    @Override
    public void createSkin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
