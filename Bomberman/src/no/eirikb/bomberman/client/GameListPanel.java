/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Date;
import java.util.Map;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.service.BombermanService;
import no.eirikb.bomberman.client.service.BombermanServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameListPanel extends VerticalPanel {

    private ListBox gameList;
    private Label infoLabel;
    private Map<String, Game> games;
    private JoinGameListener joinGameListener;

    public GameListPanel(JoinGameListener joinGameListener) {
        this.joinGameListener = joinGameListener;
        add(infoLabel = new Label("Retrieving games..."));
        BombermanServiceAsync bombermanService = GWT.create(BombermanService.class);
        bombermanService.getGames(new AsyncCallback<Map<String, Game>>() {

            public void onFailure(Throwable thrwbl) {
                infoLabel.setText("Error: " + thrwbl);
            }

            public void onSuccess(Map<String, Game> newGames) {
                infoLabel.setText("");
                games = newGames;
                init();
            }
        });
    }

    public void addGame(Game game) {
        games.put(game.getName(), game);
        gameList.addItem(game.getName());
        infoLabel.setText("Update! New game: " + game.getName() + " (" + new Date() + ")");
    }

    private void init() {
        add(new Label("Games:"));
        gameList = new ListBox();
        gameList.setWidth("100%");
        add(gameList);
        add(new Button("Join game", new ClickHandler() {

            public void onClick(ClickEvent e) {
                joinGame();
            }
        }));
        for (Game game : games.values()) {
            gameList.addItem(game.getName());
        }
    }

    private void joinGame() {
        String selected = gameList.getValue(gameList.getSelectedIndex());
        if (selected != null) {
            Game game = games.get(selected);
            if (game != null) {
                joinGameListener.onJoin(game);
            }
        }
    }
}
