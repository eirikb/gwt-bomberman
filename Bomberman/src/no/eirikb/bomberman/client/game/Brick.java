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
public class Brick extends Sprite {

    private transient final String INITURL = "img/brick.png";

    public Brick() {
    }

    public Brick(int spriteX, int spriteY) {
        super(spriteX, spriteY);
    }

    @Override
    public void initImage() {
        setImage(new Image(INITURL));
    }
}
