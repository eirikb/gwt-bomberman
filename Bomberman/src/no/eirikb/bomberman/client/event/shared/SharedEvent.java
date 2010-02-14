/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.shared;

import de.novanic.eventservice.client.event.Event;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class SharedEvent implements Event {

    public static final String SHARED_DOMAIN = "domain_shared";
    private Player player;

    public SharedEvent() {
    }

    public SharedEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
