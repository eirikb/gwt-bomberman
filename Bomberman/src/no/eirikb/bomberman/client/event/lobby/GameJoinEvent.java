/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.lobby;

import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameJoinEvent extends LobbyEvent {

    private Game game;

    public GameJoinEvent() {
    }

    public GameJoinEvent(Player player, Game game) {
        super(player);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
