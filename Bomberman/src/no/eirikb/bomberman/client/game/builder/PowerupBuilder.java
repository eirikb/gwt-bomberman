/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.builder;

import no.eirikb.bomberman.client.game.poweup.BombAmountPowerup;
import no.eirikb.bomberman.client.game.poweup.BombPowerPowerup;
import no.eirikb.bomberman.client.game.poweup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class PowerupBuilder {

    private static final int POWERUPAMOUNT = 2;

    public static Powerup createPowerup(int spriteX, int spriteY) {
        switch ((int) (Math.random() * POWERUPAMOUNT)) {
            case 0:
                return new BombAmountPowerup(spriteX, spriteY);
            case 1:
                return new BombPowerPowerup(spriteX, spriteY);
        }
        return null;
    }
}
