/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.builder;

import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class SpriteArrayBuilder {

    public static Sprite[][] createSprites() {
        Settings s = Settings.getInstance();
        int w = (s.getMapWidth() / s.getImgSize()) + 1;
        int h = (s.getMapHeight() / s.getImgSize()) + 1;
        return new Sprite[w][h];
    }
}
