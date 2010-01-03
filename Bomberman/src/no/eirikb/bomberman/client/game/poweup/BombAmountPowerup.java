/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.poweup;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombAmountPowerup extends Powerup {

    private static final String IMAGEURL = "img/pu1.png";

    public BombAmountPowerup(int spriteX, int spriteY) {
        super(new Image(IMAGEURL), spriteX, spriteY);
    }

    @Override
    public void powerUp(Player player) {
        player.setBombAbount(player.getBombAbount());
    }
}
