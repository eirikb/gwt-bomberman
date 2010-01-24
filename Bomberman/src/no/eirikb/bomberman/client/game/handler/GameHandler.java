/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.game.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import java.util.ArrayList;
import java.util.List;
import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.client.ui.game.KeyHackCallback;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Settings;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class GameHandler {

    private List<Handler> handlers;
    private Timer timer;
    private KeyHackCallback keyHackCallback;

    public GameHandler(Game game, GamePanel gamePanel) {
        gamePanel.drawSprites(game.getSprites());
        handlers = new ArrayList<Handler>();
        handlers.add(new BombHandler(game, gamePanel));
        handlers.add(new ExplosionHandler(game, gamePanel));
        handlers.add(new BoomBrickHandler(game, gamePanel));
        handlers.add(new PlayerHandler(game, gamePanel));
        handlers.add(new PowerupHandler(game, gamePanel));

        for (Handler handler : handlers) {
            game.addGameListener(handler);
        }

        createTimer();
    }

    public void stop() {
        timer.cancel();
    }

    private void createTimer() {
        timer = new Timer() {

            @Override
            public void run() {
                if (keyHackCallback != null) {
                    keyHackCallback.callback();
                }
                for (Handler handler : handlers) {
                    handler.handle();
                }
            }
        };
    }

    public void start() {
        timer.scheduleRepeating(Settings.getInstance().getSleepTime());
    }

    public void setKeyHackCallback(KeyHackCallback keyHackCallback) {
        this.keyHackCallback = keyHackCallback;
    }
}
