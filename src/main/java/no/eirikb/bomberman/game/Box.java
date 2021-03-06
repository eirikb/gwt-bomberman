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

/**
 *
 * @author Eirik Brandtæg <eirikb@eirikb.no>
 */
public class Box extends Sprite {

    private transient final String IMAGEURL = "img/box.png";

    public Box() {
    }

    public Box(int spriteX, int spriteY) {
        super(spriteX, spriteY);
    }

    @Override
    public void initImage() {
        setImage(new Image(IMAGEURL));
    }
}
