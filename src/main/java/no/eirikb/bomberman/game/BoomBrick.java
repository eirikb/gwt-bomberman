/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game;

import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BoomBrick extends Brick {

    private transient final String IMAGEURL = "img/boombrick1.png";
    private int timer;

    public BoomBrick() {
    }

    public BoomBrick(Brick brick) {
        super(brick.getSpriteX(), brick.getSpriteY());
        setPowerup(brick.getPowerup());
        setImage(new Image(IMAGEURL));
    }

    @Override
    public void setAnimation(Animation animation) {
        super.setAnimation(animation);
        timer = animation.getAnimationSize() * animation.getAnimationTime();
    }

    public boolean remove() {
        return --timer <= 0;
    }
}
