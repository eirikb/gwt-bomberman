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

    private int x;
    private int y;
    private Image image;
    protected Animation animation;

    public Sprite(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Sprite(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
