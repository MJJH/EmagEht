/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

/**
 *
 * @author Martijn
 */
public enum Parts implements iTexture {
    // Body
    playerHead(Part.HEAD, Type.BODY, 0, 0, 15, 15),
    playerTorso(Part.TORSO, Type.BODY, 17, 0, 14, 16),
    playerFrontArm(Part.FRONTARM, Type.BODY, 0, 16, 8, 19),
    playerBackArm(Part.BACKARM, Type.BODY, 9, 16, 8, 17),
    playerBackLeg(Part.BACKLEG, Type.BODY, 0, 36, 12, 15),
    playerFrontLeg(Part.FRONTLEG, Type.BODY, 13, 36, 12, 15),
    
    // Clothes
    helmetHigh(Part.HEAD, Type.ARMOR, 0, 54, 16, 21),
    helmetEyes(Part.HEAD, Type.ARMOR, 17, 56, 18, 16),
    helmetFeather(Part.HEAD, Type.ARMOR, 36, 54, 10, 18),
    helmetOpen(Part.HEAD, Type.ARMOR, 47, 55, 19, 17),
    helmetHorn(Part.HEAD, Type.ARMOR, 68, 60, 7, 9),
    
    tShirtBody(Part.TORSO, Type.ARMOR, 12, 76, 16, 17),
    tShirtBack(Part.BACKARM, Type.ARMOR, 2, 58, 7, 6),
    tShirtFront(Part.FRONTARM, Type.ARMOR, 1, 76, 9, 8),
    
    armorBody(Part.TORSO, Type.ARMOR, 1, 214, 16, 15),
    armorShoulderBack(Part.BACKARM, Type.ARMOR, 35, 217, 8, 8),
    armorWristBack(Part.BACKARM, Type.ARMOR, 57, 218, 5, 3),
    armorShoulderFront(Part.FRONTARM, Type.ARMOR, 18, 216, 14, 10),
    armorWristFront(Part.FRONTARM, Type.ARMOR, 46, 217, 7, 6),
    
    shortTop(Part.TORSO, Type.ARMOR, 1, 96, 14, 2),
    shortBack(Part.BACKLEG, Type.ARMOR, 9, 101, 8, 7),
    shortFront(Part.FRONTLEG, Type.ARMOR, 1, 100, 7, 7),
    
    armorTop(Part.TORSO, Type.ARMOR, 3, 185, 15, 2),
    armorBack(Part.BACKLEG, Type.ARMOR, 13, 189, 8, 9),
    armorFront(Part.FRONTLEG, Type.ARMOR, 0, 189, 9, 9),
    armorShoeBack(Part.BACKLEG, Type.ARMOR, 15, 203, 13, 9),
    armorShoeFront(Part.FRONTLEG, Type.ARMOR, 0, 203, 19, 9),
    
    // Wieldables
    Shield(Part.BACKARM, Type.TOOL, 1, 110, 12, 21),
    PickAxe(Part.FRONTARM, Type.TOOL, 1, 134, 20, 15),
    Sword(Part.FRONTARM, Type.TOOL, 1, 150, 23, 5),
    Hatchet(Part.FRONTARM, Type.TOOL, 1, 158, 27, 9),
    Shovel(Part.FRONTARM, Type.TOOL, 1, 172, 26, 9),
    Flint(Part.FRONTARM, Type.TOOL, 35, 127, 12, 5),
    
    // Blocks
    Block(Part.FRONTARM, Type.BLOCK, 86, 0, 20, 20),
    Ore(Part.FRONTARM, Type.BLOCK, 106, 0, 20, 20),
    Liquid(null, Type.BLOCK, 126, 0, 20, 20);
    
    private final Part part;
    private final Type type;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    Parts(Part part, Type type, int x, int y, int width, int height) {
        this.part = part;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public Part getPart() {
        return part;
    }

    public Type getType() {
        return type;
    }
}
