/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.lobby;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.ui.GameLeaveListener;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameWaitPanel extends Composite {

    private static GameWaitPanelUiBinder uiBinder = GWT.create(GameWaitPanelUiBinder.class);

    interface GameWaitPanelUiBinder extends UiBinder<Widget, GameWaitPanel> {
    }
    private GameLeaveListener gameLeaveListener;
    @UiField
    Button returnButton;
    @UiField
    Button forceStartButton;
    @UiField
    StackPanel stackPanel;

    public GameWaitPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setGameLeaveListener(GameLeaveListener gameLeaveListener) {
        this.gameLeaveListener = gameLeaveListener;
    }

    @UiHandler("returnButton")
    public void createGameButtonClick(ClickEvent event) {
        if (gameLeaveListener != null) {
            gameLeaveListener.onLeave();
        }
    }

    public void playerJoinGame(GameInfo game, Player player) {
        stackPanel.add(new Label("Player: " + player.getNick()));
    }
}
