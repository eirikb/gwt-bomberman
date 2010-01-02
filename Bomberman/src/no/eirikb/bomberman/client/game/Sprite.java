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
public class Sprite {

    private int spriteX;
    private int spriteY;
    private Image image;
    protected Animation animation;

    public Sprite(Image image, int spriteX, int spriteY) {
        this.image = image;
        this.spriteX = spriteX;
        this.spriteY = spriteY;
    }

    public Sprite(int spriteX, int spriteY) {
        this.spriteX = spriteX;
        this.spriteY = spriteY;
    }

    public Sprite(Image image) {
        this.image = image;
    }

    public int getSpriteX() {
        return spriteX;
    }

    public int getSpriteY() {
        return spriteY;
    }

    public Image getImage() {
        return image;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public int animate() {
        return animation.animate();
    }

    public void setSpriteX(int x) {
        this.spriteX = x;
    }

    public void setSpriteY(int y) {
        this.spriteY = y;
    }
}
