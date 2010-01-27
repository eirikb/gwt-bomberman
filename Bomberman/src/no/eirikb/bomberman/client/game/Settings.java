/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

import java.io.Serializable;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class Settings implements Serializable {

    public static void inject(Settings settings) {
        INSTANCE = settings;
    }
    private int mapWidth = 656;
    private int mapHeight = 496;
    private double playerSpeed = 3;
    private int playerBombStartAmount = 1;
    private int bombTimer = 50;
    private int bombPower = 1;
    private int brickAmountPercantage = 25;
    private int explosionHitPercentage = 50;
    private int sleepTime = 50;
    private int percentagePowerup = 40;
    private int imgSize = 16;
    private int maxPlayers = 2;
    private static Settings INSTANCE = new Settings();

    private Settings() {
    }

    public static Settings getInstance() {
        return INSTANCE;
    }

    public int getImgSize() {
        return imgSize;
    }

    public void setImgSize(int imgSize) {
        this.imgSize = imgSize;
    }

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

    public double getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(double playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public int getBombPower() {
        return bombPower;
    }

    public void setBombPower(int bombPower) {
        this.bombPower = bombPower;
    }

    public int getBrickAmountPercantage() {
        return brickAmountPercantage;
    }

    public void setBrickAmountPercantage(int brickAmountPercantage) {
        this.brickAmountPercantage = brickAmountPercantage;
    }

    public int getExplosionHitPercentage() {
        return explosionHitPercentage;
    }

    public void setExplosionHitPercentage(int explosionHitPercentage) {
        this.explosionHitPercentage = explosionHitPercentage;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getPercentagePowerup() {
        return percentagePowerup;
    }

    public void setPercentagePowerup(int percentagePowerup) {
        this.percentagePowerup = percentagePowerup;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}

