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
import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.Way;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombBuilder {

    private static final String BOMBURL = "img/bomb1.png";

    public static Bomb createBomb(Sprite[][] sprites, Settings settings, Player player) {
        if (player.getBombAbount() > 0) {
            Way way = player.getWay() != Way.NONE ? player.getWay() : player.getLastWay();
            if (way != null) {
                int spriteX = player.getSpriteX();
                int spriteY = player.getSpriteY();
                switch (way) {
                    case LEFT:
                        spriteX += 1;
                        break;
                    case UP:
                        spriteY += 1;
                        break;
                }

                Bomb bomb = new Bomb(new Image(BOMBURL), spriteX, spriteY, player, settings.getBombTimer(), player.getBombPower());
                if (bomb != null && bomb.getSpriteX() >= 0 && bomb.getSpriteY() >= 0
                        && bomb.getSpriteX() < sprites.length && bomb.getSpriteY() < sprites[0].length
                        && sprites[bomb.getSpriteX()][bomb.getSpriteY()] == null) {
                    player.setBombAbount(player.getBombAbount() - 1);
                    return bomb;
                }
            }
        }
        return null;
    }
}
