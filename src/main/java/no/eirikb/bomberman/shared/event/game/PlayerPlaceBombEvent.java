/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

import no.eirikb.bomberman.game.Bomb;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class PlayerPlaceBombEvent extends GameEvent {

    private Bomb bomb;

    public PlayerPlaceBombEvent() {
    }

    public PlayerPlaceBombEvent(String gameName, String playerNick, Bomb bomb) {
        super(gameName, playerNick);
        this.bomb = bomb;
    }

    public Bomb getBomb() {
        return bomb;
    }
}
