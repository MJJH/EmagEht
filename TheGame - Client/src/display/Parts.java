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
    playerHead(Part.HEAD, Type.BODY, 0, 0, 15, 15,0,0),
    playerTorso(Part.TORSO, Type.BODY, 17, 0, 14, 16,0,0),
    playerFrontArm(Part.FRONTARM, Type.BODY, 0, 16, 8, 19,0,0),
    playerBackArm(Part.BACKARM, Type.BODY, 9, 16, 8, 17,0,0),
    playerBackLeg(Part.BACKLEG, Type.BODY, 0, 36, 12, 15,0,0),
    playerFrontLeg(Part.FRONTLEG, Type.BODY, 13, 36, 12, 15,0,0),

    // Clothes
    helmetHigh(Part.HEAD, Type.ARMOR, 0, 54, 16, 21,0,0),
    helmetEyes(Part.HEAD, Type.ARMOR, 17, 56, 18, 16,1,0),
    helmetFeather(Part.HEAD, Type.ARMOR, 36, 54, 10, 18,10,1),
    helmetOpen(Part.HEAD, Type.ARMOR, 47, 55, 19, 17,3,2),
    helmetHorn(Part.HEAD, Type.ARMOR, 68, 60, 7, 9,-2,5),

    tShirtBody(Part.TORSO, Type.ARMOR, 12, 76, 16, 17,0,0),
    tShirtBack(Part.BACKARM, Type.ARMOR, 2, 85, 7, 7,0,0),
    tShirtFront(Part.FRONTARM, Type.ARMOR, 1, 76, 9, 8,0,0),

    armorBody(Part.TORSO, Type.ARMOR, 1, 214, 16, 15,0,-1),
    armorShoulderBack(Part.BACKARM, Type.ARMOR, 35, 217, 8, 8,1,0),
    armorWristBack(Part.BACKARM, Type.ARMOR, 57, 218, 5, 3,-3,-9),
    armorShoulderFront(Part.FRONTARM, Type.ARMOR, 18, 216, 14, 10,3,1),
    armorWristFront(Part.FRONTARM, Type.ARMOR, 46, 217, 7, 6,1,-11),

    shortTop(Part.TORSO, Type.ARMOR, 1, 96, 14, 2,0,0),
    shortBack(Part.BACKLEG, Type.ARMOR, 8, 98, 8, 7,0,0),
    shortFront(Part.FRONTLEG, Type.ARMOR, 1, 98, 7, 8,0,0),

    armorTop(Part.TORSO, Type.ARMOR, 3, 185, 15, 2,0,0),
    armorBack(Part.BACKLEG, Type.ARMOR, 13, 189, 8, 9,-4,0),
    armorFront(Part.FRONTLEG, Type.ARMOR, 0, 189, 9, 9,0,0),
    armorShoeBack(Part.BACKLEG, Type.ARMOR, 15, 203, 13, 9,0,-6),
    armorShoeFront(Part.FRONTLEG, Type.ARMOR, 0, 203, 13, 9,0,-6),
    
    // Wieldables
    Shield(Part.BACKARM, Type.TOOL, 1, 110, 12, 21, 0, 0),
    PickAxe(Part.FRONTARM, Type.TOOL, 1, 134, 20, 15, -2, -8),
    Sword(Part.FRONTARM, Type.TOOL, 1, 150, 23, 5, -3, -13),
    Hatchet(Part.FRONTARM, Type.TOOL, 1, 158, 27, 9, -2, -11),
    Shovel(Part.FRONTARM, Type.TOOL, 1, 172, 26, 9, -3, -11),
    Flint(Part.FRONTARM, Type.TOOL, 35, 127, 12, 5, -4, -13),
    
    // Blocks
    Block(Part.FRONTARM, Type.BLOCK, 86, 0, 20, 20, 8, -7),
    Ore(Part.FRONTARM, Type.BLOCK, 106, 0, 20, 20, 9, -7),
    Liquid(null, Type.BLOCK, 126, 0, 20, 20, 0, 0);
    
    private final Part part;
    private final Type type;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int connectX;
    private final int connectY;

    Parts(Part part, Type type, int x, int y, int width, int height, int connectX, int connectY) {
        this.part = part;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.connectX = connectX;
        this.connectY = connectY;
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

    int getConnectY() {
        return connectY;
    }

    int getConnectX() {
        return connectX;
    }
}
