/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.game;

import com.google.gwt.user.client.ui.Image;
import java.io.Serializable;

/**
 *
 * @author Eirik Brandtæg <eirikb@eirikb.no>
 */
public class Sprite implements Serializable {

    private int spriteX;
    private int spriteY;
    private transient Image image;
    protected Animation animation;
    private int zAxis = 1;

    public Sprite(int spriteX, int spriteY) {
        this.spriteX = spriteX;
        this.spriteY = spriteY;
    }

    public Sprite() {
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
        return animation != null ? animation.animate() : 1;
    }

    public void setSpriteX(int x) {
        this.spriteX = x;
    }

    public void setSpriteY(int y) {
        this.spriteY = y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void initImage() {
    }

    public int getzAxis() {
        return zAxis;
    }

    public void setzAxis(int zAxis) {
        this.zAxis = zAxis;
    }
}
