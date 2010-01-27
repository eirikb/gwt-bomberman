/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.game;

import de.novanic.eventservice.client.event.Event;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameListenerAdapter implements GameListener {

    public void apply(Event anEvent) {
        if (anEvent instanceof GameEvent) {
            if (anEvent instanceof PlayerStartWalkingEvent) {
                playerStartWalkingEvent((PlayerStartWalkingEvent) anEvent);
            } else if (anEvent instanceof PlayerStopWalkingEvent) {
                playerStopWalkingEvent((PlayerStopWalkingEvent) anEvent);
            } else if (anEvent instanceof PlayerPlaceBombEvent) {
                playerPlaceBombEvent((PlayerPlaceBombEvent) anEvent);
            }
        }
    }

    public void playerStartWalkingEvent(PlayerStartWalkingEvent event) {
    }

    public void playerStopWalkingEvent(PlayerStopWalkingEvent event) {
    }

    public void playerPlaceBombEvent(PlayerPlaceBombEvent event) {
    }
}
