/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.lobby;

import com.google.gwt.core.client.GWT;
import de.novanic.eventservice.client.event.Event;
import no.eirikb.bomberman.client.event.shared.PlayerJoinGameEvent;
import no.eirikb.bomberman.client.event.shared.PlayerQuitGameEvent;
import no.eirikb.bomberman.client.event.shared.SharedEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyListenerAdapter implements LobbyListener {

    public void apply(Event event) {
        GWT.log("LobbyEvent: " + event, null);
        if (event instanceof LobbyEvent) {
            if (event instanceof GameCreateEvent) {
                createGame((GameCreateEvent) event);
            } else if (event instanceof PlayerJoinEvent) {
                playerJoin((PlayerJoinEvent) event);
            } else if (event instanceof PlayerQuitEvent) {
                playerQuitEvent((PlayerQuitEvent) event);
            }
        } else if (event instanceof SharedEvent) {
            if (event instanceof PlayerJoinGameEvent) {
                playerJoinGameEvent((PlayerJoinGameEvent) event);
            } else if (event instanceof PlayerQuitGameEvent) {
                playerQuitGameEvent((PlayerQuitGameEvent) event);
            }
        }
    }

    public void createGame(GameCreateEvent gameCreateEvent) {
    }

    public void playerJoin(PlayerJoinEvent playerJoinEvent) {
    }

    public void playerQuitEvent(PlayerQuitEvent playerQuitEvent) {
    }

    public void playerJoinGameEvent(PlayerJoinGameEvent playerJoinEvent) {
    }

    public void playerQuitGameEvent(PlayerQuitGameEvent playerQuitGameEvent) {
    }
}
