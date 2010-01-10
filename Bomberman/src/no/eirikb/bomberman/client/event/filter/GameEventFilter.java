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
import no.eirikb.bomberman.client.event.GameEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameEventFilter implements EventFilter {

    private String name;

    public GameEventFilter(String name) {
        this.name = name;
    }

    public GameEventFilter() {
        name = null;
    }

    public boolean match(Event anEvent) {
        if (anEvent instanceof GameEvent) {
            GameEvent gameEvent = (GameEvent) anEvent;
            return (gameEvent.getGame() != null && gameEvent.getGame().getName().equals(name));
        }
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
