/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.bomberman.game;

import java.io.Serializable;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class Game implements Serializable {

    private int mapWidth;
    private int mapHeight;
    private int ticktime;
    private double gameSpeed;

    public Game(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public Game() {
    }

    public int getTicktime() {
        return ticktime;
    }

    public void setTicktime(int ticktime) {
        this.ticktime = ticktime;
    }

    public double getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(double gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }
}
