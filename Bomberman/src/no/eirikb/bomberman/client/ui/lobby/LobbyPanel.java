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
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import no.eirikb.bomberman.client.game.Game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, LobbyPanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
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

//
//    public LobbyPanel(GameJoinListener joinGameListener) {
//        DecoratedTabPanel tabPanel = new DecoratedTabPanel();
//        tabPanel.setAnimationEnabled(true);
//        tabPanel.setWidth("500px");
//        tabPanel.add(gameListPanel = new GameListPanel(joinGameListener), "Game list");
//        tabPanel.add(new CreateGamePanel(joinGameListener), "Create game");
//        tabPanel.selectTab(0);
//        RootPanel.get().add(tabPanel);
//    }
    public void addGame(Game game) {
        gameListPanel.addGame(game);
    }
}
