/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.bomberman.game;

import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import no.eirikb.bomberman.client.game.ui.KeyHack;
import no.eirikb.sge.item.Item;
import no.eirikb.sge.zone.ZoneHandler;

/**
 *
 * @author Eirik Brandtzæg eirikb@eirikb.no
 */
public class GameHandler extends ZoneHandler {

    private Game game;
    private final List<GameListener> gameListeners;
    private KeyHack keyHack;

    public GameHandler(Game game, KeyHack keyHack) {
        super(game.getMapWidth(), game.getMapHeight());
        this.game = game;
        this.keyHack = keyHack;
        gameListeners = new ArrayList<GameListener>();
    }

    public Game getGame() {
        return game;
    }

    public void execute() {
        for (GameListener gameListener : gameListeners) {
            gameListener.tickStart();
        }

        for (Item item : getItems()) {
            int size = move(item).size();
            if (size > 0) {
                GWT.log("OMG: " + size);
            }

            if (item.getX() < 0 || item.getX() + item.getWidth() > game.getMapWidth()
                    || item.getY() < 0 || item.getY() + item.getHeight() > game.getMapHeight()) {
                for (GameListener gameListener : gameListeners) {
                    gameListener.itemOutOfBounds(item);
                }
            }
            for (GameListener gameListener : gameListeners) {
                gameListener.tickItem(item);
            }
        }

        for (GameListener gameListener : gameListeners) {
            gameListener.tickEnd();
        }
        if (keyHack != null) {
            keyHack.callback();
        }
    }

    public List<GameListener> getGameListeners() {
        return gameListeners;
    }

    public void addGameListener(GameListener gameListener) {
        gameListeners.add(gameListener);
    }

    public void removeGameListener(GameListener gameListener) {
        gameListeners.remove(gameListener);
    }
}
