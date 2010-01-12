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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.service.BombermanService;
import no.eirikb.bomberman.client.service.BombermanServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameListPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, GameListPanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    private Map<String, Game> games;
    private GameJoinListener gameJoinListener;
    private BombermanServiceAsync bombermanService;
    @UiField
    ListBox gameList;
    @UiField
    Button joinGameButton;
    @UiField
    Label infoLabel;

    public GameListPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        games = new HashMap<String, Game>();
        bombermanService = GWT.create(BombermanService.class);
        infoLabel.setText("Loading games...");

        bombermanService.getGames(new AsyncCallback<Map<String, Game>>() {

            public void onFailure(Throwable thrwbl) {
                infoLabel.setText("Error: " + thrwbl);
            }

            public void onSuccess(Map<String, Game> games2) {
                games = games2;
                infoLabel.setText(games.size() == 0 ? "No games on server" : "");
                for (Game game : games.values()) {
                    gameList.addItem(game.getName());
                }
            }
        });
    }

    public void addGame(Game game) {
        games.put(game.getName(), game);
        gameList.addItem(game.getName());
        infoLabel.setText("Update! New game: " + game.getName() + " (" + new Date() + ")");
    }

    @UiHandler("joinGameButton")
    void handleClick(ClickEvent e) {
        String selected = gameList.getValue(gameList.getSelectedIndex());
        if (selected != null) {
            Game game = games.get(selected);
            if (game != null) {
                bombermanService.joinGame(game.getName(), new AsyncCallback<Game>() {

                    public void onFailure(Throwable thrwbl) {
                        infoLabel.setText("Error: " + thrwbl);
                    }

                    public void onSuccess(Game game) {
                        if (game != null) {
                            gameJoinListener.onJoin(game);
                        } else {
                            infoLabel.setText("Unable to join game, it's porbably full");
                        }
                    }
                });
            }
        }
    }

    public void setGameJoinListener(GameJoinListener gameJoinListener) {
        this.gameJoinListener = gameJoinListener;
    }
}
