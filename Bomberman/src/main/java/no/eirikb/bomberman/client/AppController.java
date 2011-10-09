/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;
import java.util.Map;
import no.eirikb.bomberman.client.common.GameListColumnDefinitions;
import no.eirikb.bomberman.client.event.JoinGameEvent;
import no.eirikb.bomberman.client.event.JoinGameEventHandler;
import no.eirikb.bomberman.client.event.LoginEvent;
import no.eirikb.bomberman.client.event.LoginEventHandler;
import no.eirikb.bomberman.client.event.QuitGameEvent;
import no.eirikb.bomberman.client.event.QuitGameEventHandler;
import no.eirikb.bomberman.client.presenter.GameCreatePresenter;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.client.presenter.GameListPresenter;
import no.eirikb.bomberman.client.presenter.GamePanelPresenter;
import no.eirikb.bomberman.client.presenter.LobbyPresenter;
import no.eirikb.bomberman.client.presenter.LoginPresenter;
import no.eirikb.bomberman.client.presenter.Presenter;
import no.eirikb.bomberman.client.view.GameCreateView;
import no.eirikb.bomberman.client.view.GameCreateViewImpl;
import no.eirikb.bomberman.client.view.GameListViewImpl;
import no.eirikb.bomberman.client.view.GamePanelView;
import no.eirikb.bomberman.client.view.GamePanelViewImpl;
import no.eirikb.bomberman.client.view.LobbyViewImpl;
import no.eirikb.bomberman.client.view.LoginViewImpl;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class AppController implements Presenter {

    private final HandlerManager eventBus;
    private final LobbyServiceAsync lobbyService;
    private final GameServiceAsync gameService;
    private HasWidgets container;
    private LoginViewImpl loginView = null;
    private LobbyViewImpl lobbyView = null;
    private GameListViewImpl<GameInfo> gameListView = null;
    private GameCreateView gameCreateView = null;
    private GamePanelView gamePanelView = null;
    private Player player = null;

    public AppController(LobbyServiceAsync lobbyService, GameServiceAsync gameService,
            HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.lobbyService = lobbyService;
        this.gameService = gameService;
        bind();
    }

    private void bind() {
        eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {

            @Override
            public void onLogin(LoginEvent event) {
                doLogin(event.getPlayer());
            }
        });

        eventBus.addHandler(JoinGameEvent.TYPE, new JoinGameEventHandler() {

            @Override
            public void onJoinGame(JoinGameEvent event) {
                doJoinGame(player, event.getGameInfo());
            }
        });

        eventBus.addHandler(QuitGameEvent.TYPE, new QuitGameEventHandler() {

            @Override
            public void onQuitGame(QuitGameEvent event) {
                doLogin(player);
            }
        });
    }

    private void doLogin(Player player) {
        this.player = player;
        if (lobbyView == null) {
            lobbyView = new LobbyViewImpl();
        }
        if (gameListView == null) {
            gameListView = new GameListViewImpl<GameInfo>();
        }
        if (gameCreateView == null) {
            gameCreateView = new GameCreateViewImpl();
        }
        new GameListPresenter(lobbyService, eventBus, gameListView, new GameListColumnDefinitions().getColumnDefnitions()).go(container);
        new GameCreatePresenter(lobbyService, eventBus, gameCreateView).go(container);
        Map<String, Widget> tabs = new HashMap<String, Widget>();
        tabs.put("Gamelist", gameListView.asWidget());
        tabs.put("Create game", gameCreateView.asWidget());
        new LobbyPresenter(lobbyService, eventBus, lobbyView, tabs).go(container);
    }

    private void doJoinGame(Player player, GameInfo gameInfo) {
        if (gamePanelView == null) {
            gamePanelView = new GamePanelViewImpl();
        }
        new GamePanelPresenter(lobbyService, gameService, eventBus, gamePanelView, gameInfo, player).go(container);
    }

    @Override
    public void go(final HasWidgets container) {
        this.container = container;

        lobbyService.checkSession(new AsyncCallback<Player>() {

            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Player result) {
                if (result != null) {
                    player = result;
                    lobbyService.checkGame(new AsyncCallback<GameInfo>() {

                        @Override
                        public void onFailure(Throwable caught) {
                        }

                        @Override
                        public void onSuccess(GameInfo game) {
                            if (game != null) {
                                eventBus.fireEvent(new JoinGameEvent(game));
                            } else {
                                doLogin(player);
                            }
                        }
                    });
                } else {
                    if (loginView == null) {
                        loginView = new LoginViewImpl();
                    }
                    new LoginPresenter(lobbyService, eventBus, loginView).go(container);
                }
            }
        });
    }
}
