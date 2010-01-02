/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.logic;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.client.game.Animation;
import no.eirikb.bomberman.client.game.BoomBrick;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BoomBrickBuilder {

    private static final String BOOMBRICKURL = "img/boombrick1.png";

    public static BoomBrick createBoomBrick(Sprite brick) {
        BoomBrick boomBrick = new BoomBrick(new Image(BOOMBRICKURL), brick.getSpriteX(), brick.getSpriteY());
        boomBrick.setAnimation(new Animation(2, 10));
        return boomBrick;
    }
}
