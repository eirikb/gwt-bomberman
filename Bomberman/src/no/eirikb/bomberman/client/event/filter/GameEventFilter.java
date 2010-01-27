/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.filter;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.filter.EventFilter;
import no.eirikb.bomberman.client.event.game.GameEvent;
import no.eirikb.bomberman.client.game.Game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameEventFilter implements EventFilter {

    private Game game;

    public GameEventFilter(Game game) {
        this.game = game;
    }

    public GameEventFilter() {
    }

    public boolean match(Event event) {
        if (event instanceof GameEvent) {
            GameEvent gameEvent = (GameEvent) event;
            return gameEvent.getGameName() != null && game != null && gameEvent.getGameName().equals(game.getGameInfo().getName());
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameEventFilter other = (GameEventFilter) obj;
        if (this.game != other.game && (this.game == null || !this.game.equals(other.game))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return game != null ? game.getGameInfo().getName().hashCode() : 0;
    }
}
