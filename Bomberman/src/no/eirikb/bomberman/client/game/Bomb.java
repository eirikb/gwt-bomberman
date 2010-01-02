/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class Bomb extends Sprite {

    private final int EXPLODETIME = 50;
    private int explodeTime;
    private int timer;
    private int power;
    private Player owner;

    public Bomb(Image image, int x, int y, Player owner, int explodeTime, int power) {
        super(image, x, y);
        this.owner = owner;
        this.explodeTime = explodeTime;
        this.power = power;
        setAnimation(new Animation(3, 10));
    }

    public Bomb(Image image, int x, int y, Player owner, int power) {
        super(image, x, y);
        this.owner = owner;
        this.power = power;
        this.explodeTime = EXPLODETIME;
        setAnimation(new Animation(3, 10));
    }

    public int getPower() {
        return power;
    }

    public boolean explode() {
        return ++timer >= explodeTime;
    }

    public void forceExplode() {
        timer = explodeTime;
    }

    public Player getOwner() {
        return owner;
    }
}
