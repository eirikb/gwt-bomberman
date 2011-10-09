/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.lobby;

import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GameCreateEvent extends LobbyEvent {

    private GameInfo game;

    public GameCreateEvent() {
    }

    public GameCreateEvent(Player player, GameInfo game) {
        super(player);
        this.game = game;
    }

    public GameInfo getGame() {
        return game;
    }
}
