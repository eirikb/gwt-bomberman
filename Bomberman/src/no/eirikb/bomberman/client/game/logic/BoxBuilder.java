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
import no.eirikb.bomberman.client.game.Box;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class BoxBuilder {

    private static final String BOXURL = "img/box.png";

    public static Sprite[][] createBoxes(Sprite[][] sprites) {
        for (int x = 0; x < sprites.length; x++) {
            for (int y = 0; y < sprites[0].length; y++) {
                if (x % 2 != 0 && y % 2 != 0) {
                    Box box = new Box(new Image(BOXURL), x, y);
                    sprites[box.getX()][box.getY()] = box;
                }
            }
        }
        return sprites;
    }
}
