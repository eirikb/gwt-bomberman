/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class Settings {

    private int mapWidth = 656;
    private int mapHeight = 496;
    private int playerSpeed = 3;
    private int playerBombStartAmount = 3;
    private int bombTimer = 50;
    private int bombPower = 3;

    public int getBombTimer() {
        return bombTimer;
    }

    public void setBombTimer(int bombTimer) {
        this.bombTimer = bombTimer;
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

    public int getPlayerBombStartAmount() {
        return playerBombStartAmount;
    }

    public void setPlayerBombStartAmount(int playerBombStartAmount) {
        this.playerBombStartAmount = playerBombStartAmount;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public int getBombPower() {
        return bombPower;
    }

    public void setBombPower(int bombPower) {
        this.bombPower = bombPower;
    }
}
