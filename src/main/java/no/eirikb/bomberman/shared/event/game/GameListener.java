/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import no.eirikb.bomberman.shared.event.shared.PlayerJoinGameEvent;
import no.eirikb.bomberman.shared.event.shared.PlayerQuitGameEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public interface GameListener extends RemoteEventListener {

    void playerStartWalkingEvent(PlayerStartWalkingEvent event);

    void playerStopWalkingEvent(PlayerStopWalkingEvent event);

    void playerPlaceBombEvent(PlayerPlaceBombEvent event);

    void playerDieEvent(PlayerDieEvent event);

    void playerResurectEvent(PlayerResurectEvent event);

    void playerGotPowerupEvent(PlayerGotPowerupEvent event);

    void playerJoinGameEvent(PlayerJoinGameEvent playerJoinEvent);

    void playerQuitGameEvent(PlayerQuitGameEvent playerQuitGameEvent);
}
