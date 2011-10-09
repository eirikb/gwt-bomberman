/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.powerup;

import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public abstract class Powerup extends Sprite {

    private Player player;

    public Powerup() {
    }

    public Powerup(int spriteX, int spriteY) {
        super(spriteX, spriteY);
        setzAxis(0);
    }

    public void powerUp(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Powerup other = (Powerup) obj;
        return (other.getSpriteX() == getSpriteX() && other.getSpriteY() == getSpriteY());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}
