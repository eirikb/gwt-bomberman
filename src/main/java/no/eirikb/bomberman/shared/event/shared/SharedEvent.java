/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.shared;

import de.novanic.eventservice.client.event.Event;
import no.eirikb.bomberman.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
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
