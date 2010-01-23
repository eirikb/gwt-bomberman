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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.Date;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, LobbyPanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    Label infoLabel;
    @UiField
    TabPanel tabPanel;
    @UiField
    GameListPanel gameListPanel;
    @UiField
    GameCreatePanel gameCreatePanel;

    public LobbyPanel(GameJoinListener gameJoinListener) {
        initWidget(uiBinder.createAndBindUi(this));
        gameListPanel.setGameJoinListener(gameJoinListener);
        gameCreatePanel.setGameJoinListener(gameJoinListener);
        tabPanel.selectTab(0);
    }

    public void playerJoin(Player player) {
        infoLabel.setText("Player join: " + player.getNick() + " (" + new Date() + ')');
    }

    public void addGame(GameInfo game) {
        infoLabel.setText("Game created: " + game.getName() + " (" + new Date() + ')');
        gameListPanel.addGame(game);
    }

    public void playerJoinGame(GameInfo game, Player player) {
        infoLabel.setText("Player " + player.getNick() + " join game: " + game.getName() + " (" + new Date() + ')');
        gameListPanel.playerJoinGame(game, player);
    }

    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    public void onJoin(GameInfo game) {
        infoLabel.setText("Waiting for players...");
        tabPanel.setVisible(false);
    }
}
