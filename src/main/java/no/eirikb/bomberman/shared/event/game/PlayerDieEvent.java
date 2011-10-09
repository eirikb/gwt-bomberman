/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class PlayerDieEvent extends GameEvent {

    public PlayerDieEvent() {
    }

    public PlayerDieEvent(String gameName, String playerNick) {
        super(gameName, playerNick);
    }
}
