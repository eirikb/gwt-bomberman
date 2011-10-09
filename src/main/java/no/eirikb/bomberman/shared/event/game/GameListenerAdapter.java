/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

import com.google.gwt.core.client.GWT;
import de.novanic.eventservice.client.event.Event;
import no.eirikb.bomberman.shared.event.shared.PlayerJoinGameEvent;
import no.eirikb.bomberman.shared.event.shared.PlayerQuitGameEvent;
import no.eirikb.bomberman.shared.event.shared.SharedEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GameListenerAdapter implements GameListener {

    public void apply(Event event) {
        GWT.log("GameEvent: " + event, null);
        if (event instanceof GameEvent) {
            if (event instanceof PlayerStartWalkingEvent) {
                playerStartWalkingEvent((PlayerStartWalkingEvent) event);
            } else if (event instanceof PlayerStopWalkingEvent) {
                playerStopWalkingEvent((PlayerStopWalkingEvent) event);
            } else if (event instanceof PlayerPlaceBombEvent) {
                playerPlaceBombEvent((PlayerPlaceBombEvent) event);
            } else if (event instanceof PlayerDieEvent) {
                playerDieEvent((PlayerDieEvent) event);
            } else if (event instanceof PlayerResurectEvent) {
                playerResurectEvent((PlayerResurectEvent) event);
            } else if (event instanceof PlayerGotPowerupEvent) {
                playerGotPowerupEvent((PlayerGotPowerupEvent) event);
            }
        } else if (event instanceof SharedEvent) {
            if (event instanceof PlayerJoinGameEvent) {
                playerJoinGameEvent((PlayerJoinGameEvent) event);
            } else if (event instanceof PlayerQuitGameEvent) {
                playerQuitGameEvent((PlayerQuitGameEvent) event);
            }

        }
    }

    public void playerStartWalkingEvent(PlayerStartWalkingEvent event) {
    }

    public void playerStopWalkingEvent(PlayerStopWalkingEvent event) {
    }

    public void playerPlaceBombEvent(PlayerPlaceBombEvent event) {
    }

    public void playerDieEvent(PlayerDieEvent event) {
    }

    public void playerResurectEvent(PlayerResurectEvent event) {
    }

    public void playerGotPowerupEvent(PlayerGotPowerupEvent event) {
    }

    public void playerJoinGameEvent(PlayerJoinGameEvent playerJoinEvent) {
    }

    public void playerQuitGameEvent(PlayerQuitGameEvent event) {
    }
}
