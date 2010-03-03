/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event.shared;

import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class PlayerQuitGameEvent extends SharedEvent {

    private GameInfo gameInfo;

    public PlayerQuitGameEvent() {
    }

    public PlayerQuitGameEvent(Player player, GameInfo gameInfo) {
        super(player);
        this.gameInfo = gameInfo;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }
}
