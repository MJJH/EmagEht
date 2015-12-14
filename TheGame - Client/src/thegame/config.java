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
    
    //public static String ip = "192.168.1.136";
    public static String ip = "145.93.66.114";
    //public static String ip = "localhost";
    public static String bindName = "//" + ip + ":1099/gameLogic";
    public static int reachGameLogicPort = 1099;
    public static int talkBackGameLogicPort = 1100;
    public static int updateListenerPort = 1101;
    public static int timeOutTime = 500;
}
