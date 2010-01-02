/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.game.handler;

import com.google.gwt.user.client.Timer;
import java.util.ArrayList;
import java.util.List;
import no.eirikb.bomberman.client.Bomberman;
import no.eirikb.bomberman.client.GamePanel;
import no.eirikb.bomberman.client.KeyHackCallback;
import no.eirikb.bomberman.client.game.Game;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class GameHandler extends Handler {

    private List<Handler> handlers;
    private final int SLEEPTIME = 50;
    private int sleepTime;
    private long lastTime;
    private Timer timer;
    private KeyHackCallback keyHackCallback;

    public GameHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
        gamePanel.drawSprites(game.getSprites());
        handlers = new ArrayList<Handler>();
        handlers.add(new BombHandler(game, gamePanel));
        handlers.add(new ExplosionHandler(game, gamePanel));
        handlers.add(new BoomBrickHandler(game, gamePanel));
        handlers.add(new PlayerHandler(game, gamePanel));

        sleepTime = SLEEPTIME;
        lastTime = System.currentTimeMillis();

        createTimer();
    }

    private void createTimer() {
        timer = new Timer() {

            @Override
            public void run() {
                if (keyHackCallback != null) {
                    keyHackCallback.callback();
                }
//                TODO: Find out if this is needed
//                long timeDiff = System.currentTimeMillis() - lastTime;
//                if (timeDiff > SLEEPTIME) {
//                    sleepTime--;
//                } else if (timeDiff < SLEEPTIME) {
//                    sleepTime++;
//                }
//                lastTime = System.currentTimeMillis();
                for (Handler handler : handlers) {
                    handler.handle();
                }
//                  start();
            }
        };
    }

    public void start() {
        timer.scheduleRepeating(50);
    }

    @Override
    protected void handle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setKeyHackCallback(KeyHackCallback keyHackCallback) {
        this.keyHackCallback = keyHackCallback;
    }
}
