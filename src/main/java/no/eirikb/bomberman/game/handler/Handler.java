/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.handler;

import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.GameListener;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public abstract class Handler implements GameListener {

    protected Game game;
    protected GamePanel gamePanel;

    public Handler(Game game, GamePanel gamePanel) {
        this.game = game;
        this.gamePanel = gamePanel;
    }

    protected abstract void handle();


}
