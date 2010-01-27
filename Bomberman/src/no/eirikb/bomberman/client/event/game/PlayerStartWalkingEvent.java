/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.game;

import no.eirikb.bomberman.client.game.Way;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
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
