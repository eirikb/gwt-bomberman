/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.lobby;

import de.novanic.eventservice.client.event.Event;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyEvent implements Event {

    public static final String LOBBY_DOMAIN = "domain_lobby";
    private Player player;

    public LobbyEvent() {
    }

    public LobbyEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
