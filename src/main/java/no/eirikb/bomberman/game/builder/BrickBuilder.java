/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.game.builder;

import no.eirikb.bomberman.game.Brick;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;

/**
 *
 * @author Eirik Brandtæg <eirikb@eirikb.no>
 */
public class BrickBuilder {

    private static final String BRICKURL = "img/brick.png";

    public static Sprite[][] createBricks(Sprite[][] sprites) {
        int total = ((sprites.length * sprites[0].length) / 100) * Settings.getInstance().getBrickAmountPercantage();
        for (int i = 0; i < total; i++) {
            final int spriteX = (int) (Math.random() * sprites.length);
            final int spriteY = (int) (Math.random() * sprites[0].length);
            if (sprites[spriteX][spriteY] == null) {

                final Brick brick = new Brick(spriteX, spriteY);
                sprites[spriteX][spriteY] = brick;
            }
        }

        sprites[0][0] = null;
        sprites[1][0] = null;
        sprites[0][1] = null;

        sprites[sprites.length - 1][0] = null;
        sprites[sprites.length - 2][0] = null;
        sprites[sprites.length - 1][1] = null;

        sprites[sprites.length - 1][sprites[0].length - 1] = null;
        sprites[sprites.length - 2][sprites[0].length - 1] = null;
        sprites[sprites.length - 1][sprites[0].length - 2] = null;

        sprites[0][sprites[0].length - 1] = null;
        sprites[1][sprites[0].length - 1] = null;
        sprites[0][sprites[0].length - 2] = null;
        return sprites;
    }
}
