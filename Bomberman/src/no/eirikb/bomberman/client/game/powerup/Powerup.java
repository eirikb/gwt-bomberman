/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.powerup;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public abstract class Powerup extends Sprite {

    private Player player;

    public Powerup(String IMAGEURL, int spriteX, int spriteY) {
        super(spriteX, spriteY);
        setImage(new Image(IMAGEURL));
        setzAxis(0);
    }

    public void powerUp(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
