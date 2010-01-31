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
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.service.LobbyService;
import no.eirikb.bomberman.client.service.LobbyServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameListPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, GameListPanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    private Map<String, GameInfo> games;
    private GameJoinListener gameJoinListener;
    private LobbyServiceAsync bombermanService;
    @UiField
    ListBox gameList;
    @UiField
    Button joinGameButton;
    @UiField
    Label infoLabel;

    public GameListPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        games = new HashMap<String, GameInfo>();
        bombermanService = GWT.create(LobbyService.class);
        infoLabel.setText("Loading games...");

        bombermanService.getGames(new AsyncCallback<Map<String, GameInfo>>() {

            public void onFailure(Throwable thrwbl) {
                infoLabel.setText("Error: " + thrwbl);
            }

            public void onSuccess(Map<String, GameInfo> games) {
                infoLabel.setText(games.size() == 0 ? "No games on server" : "");
                for (GameInfo game : games.values()) {
                    addGame(game);
                }
            }
        });
    }

    public void addGame(GameInfo game) {
        games.put(game.getName(), game);
        gameList.addItem(game.getName() + " (" + game.getPlayerSize() + '/' + game.getMaxPlayers() + ')');
        infoLabel.setText("Update! New game: " + game.getName() + " (" + new Date() + ")");
    }

    @UiHandler("joinGameButton")
    void handleClick(ClickEvent e) {
        GameInfo game = getGame(gameList.getSelectedIndex());
        if (game != null) {
            bombermanService.joinGame(game.getName(), new AsyncCallback<GameInfo>() {

                public void onFailure(Throwable thrwbl) {
                    infoLabel.setText("Error: " + thrwbl);
                }

                public void onSuccess(GameInfo game) {
                    if (game != null) {
                        gameJoinListener.onJoin(game);
                    } else {
                        infoLabel.setText("Unable to join game, it's porbably full");
                    }
                }
            });
        }
    }

    public void setGameJoinListener(GameJoinListener gameJoinListener) {
        this.gameJoinListener = gameJoinListener;
    }

    public void playerJoinGame(GameInfo game, Player player) {
        for (int i = 0; i < gameList.getItemCount(); i++) {
            GameInfo g = getGame(i);
            if (g != null && g.getName().equals(game.getName())) {
                gameList.removeItem(i);
                addGame(game);
                break;
            }
        }
    }

    private GameInfo getGame(int i) {
        return getGame(gameList.getValue(i));
    }

    private GameInfo getGame(String gameName) {
        int space = gameName.lastIndexOf(' ');
        if (space >= 0) {
            gameName = gameName.substring(0, space);
        }
        return games.get(gameName);
    }
}
