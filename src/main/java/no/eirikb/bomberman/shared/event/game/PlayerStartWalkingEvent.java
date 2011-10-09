/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

import no.eirikb.bomberman.game.Way;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class PlayerStartWalkingEvent extends GameEvent {

    private Way way;

    public PlayerStartWalkingEvent() {
    }

    public PlayerStartWalkingEvent(String gameName, String playerName, Way way) {
        super(gameName, playerName);
        this.way = way;
    }

    public Way getWay() {
        return way;
    }
}
