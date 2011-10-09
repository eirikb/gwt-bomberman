/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

import com.google.gwt.user.client.Timer;
import no.eirikb.bomberman.game.GameHandler;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GameTimer {

    private int TICKDELAY = 50;
    private GameHandler gameHandler;

    public GameTimer(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public void run() {
        int ticktime = TICKDELAY;
        if (gameHandler.getGame().getTicktime() > 0) {
            ticktime = gameHandler.getGame().getTicktime();
        }

        new Timer() {

            @Override
            public void run() {
                gameHandler.execute();
            }
        }.scheduleRepeating(ticktime);
    }
}
