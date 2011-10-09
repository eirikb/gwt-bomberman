/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.ui;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import no.eirikb.bomberman.client.game.item.GameItem;
import no.eirikb.bomberman.game.Game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public abstract class GamePanel extends FocusPanel {

    private int width;
    private int height;
    private Widget container;

    public GamePanel(Game game, Widget container, final KeyHack keyHack) {
        this.width = game.getMapWidth();
        this.height = game.getMapHeight();
        this.container = container;
        add(container);
        addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                event.preventDefault();
                event.stopPropagation();
                if (KeyDownEvent.isArrow(event.getNativeKeyCode())) {
                    keyHack.arrowKeyDown(event);
                } else if (event.getNativeKeyCode() == 32) {
                    if (keyHack != null) {
                        keyHack.setAnotherKeyPresses(true);
                    }
                }
            }
        });

        addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                event.preventDefault();
                event.stopPropagation();
                if (KeyUpEvent.isArrow(event.getNativeKeyCode())) {
                    keyHack.arrowKeyUp(event);
                }
            }
        });
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    protected Widget getContainer() {
        return container;
    }

    public void addGameItem(GameItem gameItem) {
        throw new UnsupportedOperationException("Adding GameItem to this panel is not supported");
    }

    public void setItemPosition(GameItem gameItem) {
        throw new UnsupportedOperationException("Setting GameItem position on this panel is not supported");
    }
}
