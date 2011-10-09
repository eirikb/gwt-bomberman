/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

import no.eirikb.bomberman.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class PlayerGotPowerupEvent extends GameEvent {

    private Powerup powerup;

    public PlayerGotPowerupEvent(String gameName, String playerNick, Powerup powerup) {
        super(gameName, playerNick);
        this.powerup = powerup;
    }

    public PlayerGotPowerupEvent() {
    }

    public Powerup getPowerup() {
        return powerup;
    }
}
