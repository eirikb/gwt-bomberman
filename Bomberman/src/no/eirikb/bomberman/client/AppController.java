/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
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
import no.eirikb.bomberman.client.presenter.GameCreatePresenter;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.client.presenter.GameListPresenter;
import no.eirikb.bomberman.client.presenter.LobbyPresenter;
import no.eirikb.bomberman.client.presenter.LoginPresenter;
import no.eirikb.bomberman.client.presenter.Presenter;
import no.eirikb.bomberman.client.view.GameCreateView;
import no.eirikb.bomberman.client.view.GameCreateViewImpl;
import no.eirikb.bomberman.client.view.GameListViewImpl;
import no.eirikb.bomberman.client.view.LobbyViewImpl;
import no.eirikb.bomberman.client.view.LoginViewImpl;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class AppController implements Presenter {

    private final HandlerManager eventBus;
    private final LobbyServiceAsync lobbyService;
    private HasWidgets container;
    private LoginViewImpl loginView = null;
    private LobbyViewImpl lobbyView = null;
    private GameListViewImpl<GameInfo> gameListView = null;
    private GameCreateView gameCreateView = null;

    public AppController(LobbyServiceAsync lobbyService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.lobbyService = lobbyService;
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
                doJoinGame(event.getGameInfo());
            }
        });
    }

    private void doLogin(Player player) {
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

    private void doJoinGame(GameInfo gameInfo) {
        GWT.log("JOIN GAME: " + gameInfo.getName());
    }

    @Override
    public void go(final HasWidgets container) {
        this.container = container;
        GWT.runAsync(new RunAsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess() {
                lobbyService.checkSession(new AsyncCallback<Player>() {

                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(Player result) {
                        if (result != null) {
                            doLogin(result);
                        } else {
                            if (loginView == null) {
                                loginView = new LoginViewImpl();
                            }
                            new LoginPresenter(lobbyService, eventBus, loginView).go(container);
                        }
                    }
                });
            }
        });
    }
}
