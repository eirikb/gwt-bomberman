/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.game.builder;

import no.eirikb.bomberman.game.Box;
import no.eirikb.bomberman.game.Sprite;

/**
 *
 * @author Eirik Brandtæg <eirikb@eirikb.no>
 */
public class BoxBuilder {

    private static final String BOXURL = "img/box.png";

    public static Sprite[][] createBoxes(Sprite[][] sprites) {
        for (int spriteX = 0; spriteX < sprites.length; spriteX++) {
            for (int spriteY = 0; spriteY < sprites[0].length; spriteY++) {
                if (spriteX % 2 != 0 && spriteY % 2 != 0) {
                    Box box = new Box(spriteX, spriteY);
                    sprites[box.getSpriteX()][box.getSpriteY()] = box;
                }
            }
        }
        return sprites;
    }
}
