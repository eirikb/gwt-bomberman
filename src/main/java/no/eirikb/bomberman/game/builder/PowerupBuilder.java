/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.builder;

import no.eirikb.bomberman.game.Brick;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.game.powerup.BombAmountPowerup;
import no.eirikb.bomberman.game.powerup.BombPowerPowerup;
import no.eirikb.bomberman.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class PowerupBuilder {

    private static final int POWERUPAMOUNT = 2;

    private static Powerup createPowerup(int spriteX, int spriteY) {
        switch ((int) (Math.random() * POWERUPAMOUNT)) {
            case 0:
                return new BombAmountPowerup(spriteX, spriteY);
            case 1:
                return new BombPowerPowerup(spriteX, spriteY);
        }
        return null;
    }

    public static Sprite[][] createPowerups(Sprite[][] sprites) {
        for (int x = 0; x < sprites.length; x++) {
            for (int y = 0; y < sprites[0].length; y++) {
                Sprite sprite = sprites[x][y];
                if (sprite != null && sprite instanceof Brick) {
                    if (Math.random() * 100 <= Settings.getInstance().getPercentagePowerup()) {
                        Powerup powerup = PowerupBuilder.createPowerup(sprite.getSpriteX(), sprite.getSpriteY());
                        if (powerup != null) {
                            ((Brick) sprite).setPowerup(powerup);
                        }
                    }
                }
            }
        }
        return sprites;
    }
}
