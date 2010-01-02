/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.game;

import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class Player extends Sprite {

    private String nick;
    private Way way;
    private Way lastWay;
    private int speed;
    private int bombPower;
    private int bombAbount;

    public Player(Image image, String nick, int speed, int bombPower, int bombAbount) {
        super(image);
        this.nick = nick;
        this.speed = speed;
        this.bombPower = bombPower;
        this.bombAbount = bombAbount;
        way = Way.NONE;
    }

    public Player(Image image, String nick) {
        super(image);
        this.nick = nick;
        way = Way.NONE;
        speed = 2;
        bombPower = 3;
    }

    public int getBombPower() {
        return bombPower;
    }

    public void setBombPower(int bombPower) {
        this.bombPower = bombPower;
    }

    public String getNick() {
        return nick;
    }

    public Way getWay() {
        return way;
    }

    public void setWay(Way way) {
        lastWay = way != Way.NONE ? way : lastWay;
        this.way = way;
        switch (way) {
            case LEFT:
            case RIGHT:
                setAnimation(new Animation(2, 10));
                break;
            case UP:
            case DOWN:
                setAnimation(new Animation(3, 10));
                break;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Way getLastWay() {
        return lastWay;
    }

    public int getBombAbount() {
        return bombAbount;
    }

    public void setBombAbount(int bombAbount) {
        this.bombAbount = bombAbount;
    }
}
