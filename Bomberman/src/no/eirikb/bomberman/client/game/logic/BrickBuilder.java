/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.game.logic;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.client.game.Brick;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class BrickBuilder {

    private static final String BRICKURL = "img/brick.png";
    private static final int PERCENTAGE = 25;

    public static Sprite[][] createBricks(Sprite[][] sprites) {
        int total = ((sprites.length * sprites[0].length) / 100) * PERCENTAGE;
        for (int i = 0; i < total; i++) {
            final int x = (int) (Math.random() * sprites.length);
            final int y = (int) (Math.random() * sprites[0].length);
            if (sprites[x][y] == null) {

                final Brick brick = new Brick(new Image(BRICKURL), x, y);
                sprites[x][y] = brick;
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
