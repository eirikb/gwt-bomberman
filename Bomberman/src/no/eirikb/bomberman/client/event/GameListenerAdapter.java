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
public class GameListenerAdapter implements GameListener {

    public void apply(Event anEvent) {
        if (anEvent instanceof GameEvent) {
            if (anEvent instanceof GameCreateEvent) {
                GameCreateEvent createGameEvent = (GameCreateEvent) anEvent;
                newGame(createGameEvent.getGame());
            } else if (anEvent instanceof GameJoinEvent) {
                GameJoinEvent gameJoinEvent = (GameJoinEvent) anEvent;
                joinGame(gameJoinEvent.getGame(), gameJoinEvent.getPlayer());
            }
        }
    }

    public void newGame(Game game) {
    }

    public void joinGame(Game game, Player player) {
    }
}
