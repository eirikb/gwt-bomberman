/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

import java.io.Serializable;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameInfo implements Serializable {

    private String name;
    private int maxPlayers;
    private int playerSize;

    public GameInfo() {
    }

    public void setPlayerSize(int playerSize) {
        this.playerSize = playerSize;
    }

    public GameInfo(String name, int maxPlayers) {
        this.name = name;
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getName() {
        return name;
    }

    public int getPlayerSize() {
        return playerSize;
    }
}
