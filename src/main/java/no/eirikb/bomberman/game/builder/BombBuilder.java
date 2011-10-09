/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.builder;

import no.eirikb.bomberman.game.Bomb;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.game.Way;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombBuilder {

    public static Bomb createBomb(Sprite[][] sprites, Player player) {
        Settings settings = Settings.getInstance();
        if (player.getBombAbount() > 0) {
            Way way = player.getWay() != Way.NONE ? player.getWay() : player.getLastWay();
            if (way != null) {
                int spriteX = (int) ((player.getX() + (settings.getImgSize() / 2)) / settings.getImgSize());
                int spriteY = (int) ((player.getY() + (settings.getImgSize() / 2)) / settings.getImgSize());

                // Olav want to drop the bombs in front, Benjamin wants to just drop em down.
                // I say fuck this shit...
//                switch (player.getLastWay()) {
//                    case LEFT:
//                        spriteX--;
//                        break;
//                    case UP:
//                        spriteY--;
//                        break;
//                    case RIGHT:
//                        spriteX++;
//                        break;
//                    case DOWN:
//                        spriteY++;
//                        break;
//                }

                if (spriteX >= 0 && spriteX < sprites.length && spriteY >= 0 && spriteY < sprites[0].length) {
                    if (sprites[spriteX][spriteY] != null) {
                        spriteX = (int) ((player.getX() + (settings.getImgSize() / 2)) / settings.getImgSize());
                        spriteY = (int) ((player.getY() + (settings.getImgSize() / 2)) / settings.getImgSize());
                    }
                    if (sprites[spriteX][spriteY] == null) {
                        Bomb bomb = new Bomb(spriteX, spriteY, player, settings.getBombTimer(), player.getBombPower());
                        if (bomb != null && bomb.getSpriteX() >= 0 && bomb.getSpriteY() >= 0
                                && bomb.getSpriteX() < sprites.length && bomb.getSpriteY() < sprites[0].length) {
                            return bomb;
                        }
                    }
                }
            }
        }
        return null;
    }
}
