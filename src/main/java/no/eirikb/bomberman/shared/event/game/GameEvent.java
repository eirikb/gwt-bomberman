/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GameEvent implements Event {

    public static final Domain GAME_DOMAIN = DomainFactory.getDomain("domain_game");
    private String gameName;
    private String playerNick;

    public GameEvent() {
    }

    public GameEvent(String gameName, String playerNick) {
        this.gameName = gameName;
        this.playerNick = playerNick;
    }

    public String getGameName() {
        return gameName;
    }

    public String getPlayerNick() {
        return playerNick;
    }
}
