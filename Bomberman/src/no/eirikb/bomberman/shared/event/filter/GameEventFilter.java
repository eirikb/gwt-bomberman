/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.filter;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.filter.EventFilter;
import no.eirikb.bomberman.shared.event.game.GameEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameEventFilter implements EventFilter {

    private String gameName;
    private String playerNick;

    public GameEventFilter(String gameName, String playerNick) {
        this.gameName = gameName;
        this.playerNick = playerNick;
    }

    public GameEventFilter() {
    }

    public boolean match(Event event) {
        if (event instanceof GameEvent) {
            GameEvent gameEvent = (GameEvent) event;
            return !(gameEvent.getGameName().equals(gameName) && !gameEvent.getPlayerNick().equals(playerNick));
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GameEventFilter gameEventFilter = (GameEventFilter) obj;
        return gameName.equals(gameEventFilter.gameName);
    }

    @Override
    public int hashCode() {
        return gameName.hashCode();
    }
}
