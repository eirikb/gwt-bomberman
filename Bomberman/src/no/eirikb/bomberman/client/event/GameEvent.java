/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event;

import de.novanic.eventservice.client.event.Event;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameEvent implements Event {

    public static final String GAME_DOMAIN = "domain_game";
    private Game game;
    private Player player;

    public GameEvent(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public GameEvent() {
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }
}
