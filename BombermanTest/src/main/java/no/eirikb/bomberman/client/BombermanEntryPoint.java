/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import no.eirikb.bomberman.client.game.ui.GamePanel;
import no.eirikb.bomberman.client.game.ui.GamePanelAbsolute;
import no.eirikb.bomberman.client.game.GameTimer;
import no.eirikb.bomberman.client.game.item.GameBrick;
import no.eirikb.bomberman.client.game.item.GamePlayer;
import no.eirikb.bomberman.client.game.item.GameItem;
import no.eirikb.bomberman.client.game.ui.KeyHack;
import no.eirikb.bomberman.client.game.ui.KeyHackCallback;
import no.eirikb.bomberman.client.resources.GameResources;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.GameHandler;
import no.eirikb.bomberman.game.GameListenerAdapter;
import no.eirikb.sge.collisiondetection.Collision;
import no.eirikb.sge.collisiondetection.CollisionListener;
import no.eirikb.sge.item.Item;

/**
 * Main entry point.
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombermanEntryPoint implements EntryPoint {

    /**
     * Creates a new instance of BombermanEntryPoint
     */
    public BombermanEntryPoint() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        final GamePlayer me = new GamePlayer(10, 10, new Image(GameResources.INSTANCE.playerDown1()));
        KeyHack keyHack = new KeyHack(new KeyHackCallback() {

            @Override
            public void arrowKeyDown(KeyDownEvent event) {
                if (event.isLeftArrow()) {
                    me.left();
                } else if (event.isUpArrow()) {
                    me.up();
                } else if (event.isRightArrow()) {
                    me.right();
                } else if (event.isDownArrow()) {
                    me.down();
                }
            }

            @Override
            public void arrowKeyUp() {
                me.stop();
            }
        });
        GameBrick brick = new GameBrick(100, 10, new Image(GameResources.INSTANCE.brick()));
        Game game = new Game(640, 480);
        GameHandler gameHandler = new GameHandler(game, keyHack);
        GameTimer gameTimer = new GameTimer(gameHandler);
        final GamePanel gamePanel = new GamePanelAbsolute(game, keyHack);
        gameHandler.addItem(me);
        gamePanel.addGameItem(me);
        gameHandler.addItem(brick);
        gamePanel.addGameItem(brick);
        RootPanel.get().add(gamePanel);
        gameTimer.run();

        gameHandler.addGameListener(new GameListenerAdapter() {

            @Override
            public void tickItem(Item item) {
                GameItem gameItem = (GameItem) item;
                if (item.getSpeed() > 0) {
                    gameItem.animate();
                }
                gamePanel.setItemPosition(gameItem);
            }
        });

        me.addCollisionListener(new CollisionListener() {

            @Override
            public void onCollision(Collision collision) {
                GWT.log("WE HAVE A COLLISION! :O");
            }
        });

        brick.addCollisionListener(new CollisionListener() {

            @Override
            public void onCollision(Collision collision) {
                GWT.log("WE HAVE A COLLISION! :O");
            }
        });
    }
}
