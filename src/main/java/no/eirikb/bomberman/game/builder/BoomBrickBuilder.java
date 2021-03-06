/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.builder;

import no.eirikb.bomberman.game.Animation;
import no.eirikb.bomberman.game.BoomBrick;
import no.eirikb.bomberman.game.Brick;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class BoomBrickBuilder {

    public static BoomBrick createBoomBrick(Brick brick) {
        BoomBrick boomBrick = new BoomBrick(brick);
        boomBrick.setAnimation(new Animation(2, 10));
        return boomBrick;
    }
}
