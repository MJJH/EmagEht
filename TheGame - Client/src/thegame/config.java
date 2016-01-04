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

    config(float val)
    {
        this.val = val;
    }

    //public static String ip = "84.24.141.120";
    public static String ip = "localhost";
    public static String lobbyClientToServerName = "lobbyClientToServer";
    public static String gameClientToServerName = "gameClientToServer";
    public static int lobbyServerToClientPort = 1097;
    public static int lobbyClientToServerPort = 1098;
    public static int gameServerToClientPort = 1099;
    public static int gameClientToServerPort = 1100;
    public static int gameServerToClientListenerPort = 1101;
    public static int timeOutTime = 500;
}
