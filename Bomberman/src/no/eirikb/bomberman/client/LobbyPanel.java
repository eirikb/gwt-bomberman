/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import no.eirikb.bomberman.client.game.Game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyPanel extends VerticalPanel {

    private GameListPanel gameListPanel;

    public LobbyPanel(JoinGameListener joinGameListener) {
        DecoratedTabPanel tabPanel = new DecoratedTabPanel();
        tabPanel.setAnimationEnabled(true);
        tabPanel.setWidth("500px");
        tabPanel.add(gameListPanel = new GameListPanel(joinGameListener), "Game list");
        tabPanel.add(new CreateGamePanel(), "Create game");
        tabPanel.selectTab(0);
        RootPanel.get().add(tabPanel);
    }

    public void addGame(Game game) {
        gameListPanel.addGame(game);
    }
}
