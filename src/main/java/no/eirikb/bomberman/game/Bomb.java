/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game;

import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class Bomb extends Sprite {

    private transient final String IMAGEURL = "img/bomb1.png";
    private int explodeTime;
    private int timer;
    private int power;
    private Player owner;

    public Bomb() {
    }

    public Bomb(int spriteX, int spriteY, Player owner, int explodeTime, int power) {
        super(spriteX, spriteY);
        this.owner = owner;
        this.explodeTime = explodeTime;
        this.power = power;
        setImage(new Image(IMAGEURL));
        setAnimation(new Animation(3, 10));
        setImage(new Image(IMAGEURL));
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
