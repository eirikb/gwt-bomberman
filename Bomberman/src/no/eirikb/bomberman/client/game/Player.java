/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.game;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class Player extends Sprite {

    private transient final String IMAGEURL = "img/md1.png";
    private String nick;
    private Way way;
    private Way lastWay;
    private double speed;
    private int bombPower;
    private int bombAbount;
    private double x;
    private double y;

    public Player() {
    }

    public Player(String nick) {
        this.nick = nick;
        way = Way.NONE;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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
        if (way != lastWay) {
            switch (way) {
                case LEFT:
                case RIGHT:
                    setAnimation(new Animation(4, 3));
                    break;
                case UP:
                case DOWN:
                    setAnimation(new Animation(4, 3));
                    break;
            }
        }
        lastWay = way != Way.NONE ? way : lastWay;
        this.way = way;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
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
