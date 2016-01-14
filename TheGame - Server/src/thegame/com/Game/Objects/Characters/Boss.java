/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects.Characters;

import java.util.Map;
import thegame.com.Game.Objects.Tool;
import thegame.com.Game.Objects.ToolType;

/**
 *
 * @author laure
 */
public class Boss extends Enemy {

    private static final long serialVersionUID = 5539225098267757690L;

    public Boss(String name, int hp, Map<SkillType, Integer> skills, float x, float y, float height, float width, thegame.com.Game.Map map)
    {
        super(name, hp, skills, x, y, height, width, map);
        sXMax = 0.2f;
        sYMax = 0.2f;
        ToolType test = new ToolType("Zwaardje", 20, 1000, 3f, 1, ToolType.toolType.SWORD, 0.3f);
        Tool equip = new Tool(test, map);
        equipTool(equip);
    }

}
