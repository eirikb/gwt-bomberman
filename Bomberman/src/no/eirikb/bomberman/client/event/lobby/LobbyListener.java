/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.lobby;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import no.eirikb.bomberman.client.event.shared.PlayerJoinGameEvent;
import no.eirikb.bomberman.client.event.shared.PlayerQuitGameEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface LobbyListener extends RemoteEventListener {

    void createGame(GameCreateEvent gameCreateEvent);

    void playerJoin(PlayerJoinEvent playerJoinEvent);

    void playerQuitEvent(PlayerQuitEvent playerQuitEvent);

    void playerJoinGameEvent(PlayerJoinGameEvent playerJoinEvent);

    void playerQuitGameEvent(PlayerQuitGameEvent playerQuitGameEvent);
}
