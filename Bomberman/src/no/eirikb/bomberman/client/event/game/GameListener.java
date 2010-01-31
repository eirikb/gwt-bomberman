/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.game;

import de.novanic.eventservice.client.event.listener.RemoteEventListener;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface GameListener extends RemoteEventListener {

    void playerStartWalkingEvent(PlayerStartWalkingEvent event);

    void playerStopWalkingEvent(PlayerStopWalkingEvent event);

    void playerPlaceBombEvent(PlayerPlaceBombEvent event);

    void playerDieEvent(PlayerDieEvent event);

    void playerResurectEvent(PlayerResurectEvent event);

    void playerGotPowerupEvent(PlayerGotPowerupEvent event);
}
