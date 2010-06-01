/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.ui;

import com.google.gwt.user.client.ui.AbsolutePanel;
import no.eirikb.bomberman.client.game.item.GameItem;
import no.eirikb.bomberman.game.Game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GamePanelAbsolute extends GamePanel {

    private AbsolutePanel container;

    public GamePanelAbsolute(Game game, KeyHack keyHack) {
        super(game, new AbsolutePanel(), keyHack);
        this.container = (AbsolutePanel) getContainer();
        container.setSize(getWidth() + "px", getHeight() + "px");
    }

    @Override
    public void addGameItem(GameItem gameItem) {
        container.add(gameItem.getImage(), (int) gameItem.getX(), (int) gameItem.getY());
    }

    @Override
    public void setItemPosition(GameItem gameItem) {
        container.setWidgetPosition(gameItem.getImage(), (int) gameItem.getX(), (int) gameItem.getY());
    }
}
