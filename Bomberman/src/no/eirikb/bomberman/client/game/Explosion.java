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
public class Explosion extends Sprite {

    private static final String URLPART = "img/boom";
    private ExplosionType explosionType;
    private int timer;

    public Explosion() {
    }

    public Explosion(Image image, int spriteX, int spriteY, ExplosionType explosionType) {
        super(spriteX, spriteY);
        setImage(image);
        this.explosionType = explosionType;
    }

    @Override
    public void setAnimation(Animation animation) {
        super.setAnimation(animation);
        timer = animation.getAnimationSize() * animation.getAnimationTime();
    }

    public boolean remove() {
        return --timer <= 0;
    }

    public ExplosionType getExplosionType() {
        return explosionType;
    }

    public static Image getImageURL(ExplosionType explosionType) {
        return new Image(URLPART + explosionType + 1 + ".png");
    }

    public static String getImageURLPart(Explosion explosion) {
        return URLPART + explosion.getExplosionType();
    }
}
