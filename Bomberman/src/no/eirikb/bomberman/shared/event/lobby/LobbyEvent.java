/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.lobby;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import no.eirikb.bomberman.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyEvent implements Event {

    public static final Domain LOBBY_DOMAIN = DomainFactory.getDomain("domain_lobby");
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
