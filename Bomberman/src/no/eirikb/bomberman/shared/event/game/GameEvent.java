/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

import de.novanic.eventservice.client.event.Event;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameEvent implements Event {

    public static final String GAME_DOMAIN = "domain_game";
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
