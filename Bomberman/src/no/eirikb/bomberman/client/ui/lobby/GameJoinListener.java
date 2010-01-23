/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.lobby;

import no.eirikb.bomberman.client.game.GameInfo;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface GameJoinListener {

    public void onJoin(GameInfo game);
}
