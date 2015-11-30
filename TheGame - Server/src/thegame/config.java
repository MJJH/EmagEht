/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

/**
 *
 * @author Martijn
 */
public enum config {
    block(20);
    
    public final float val;
    
    config(float val) {
        this.val = val;
    }
    
    public static String bindName = "//192.168.1.101:1099/gameLogic";
    //public static String ip = "192.168.1.136";
    public static String ip = "84.24.141.120";
    public static int port = 1099;
}
