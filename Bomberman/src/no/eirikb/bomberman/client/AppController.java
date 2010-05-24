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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import no.eirikb.bomberman.client.common.GameListColumnDefinitions;
import no.eirikb.bomberman.client.event.LoginEvent;
import no.eirikb.bomberman.client.event.LoginEventHandler;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.presenter.GameListPresenter;
import no.eirikb.bomberman.client.presenter.LobbyPresenter;
import no.eirikb.bomberman.client.presenter.LoginPresenter;
import no.eirikb.bomberman.client.presenter.Presenter;
import no.eirikb.bomberman.client.view.GameListViewImpl;
import no.eirikb.bomberman.client.view.LobbyViewImpl;
import no.eirikb.bomberman.client.view.LoginViewImpl;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class AppController implements Presenter, ValueChangeHandler<String> {

    private final HandlerManager eventBus;
    private final LobbyServiceAsync lobbyService;
    private HasWidgets container;
    private LoginViewImpl loginView = null;
    private LobbyViewImpl lobbyView = null;
    private GameListViewImpl<GameInfo> gameListView = null;

    public AppController(LobbyServiceAsync lobbyService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.lobbyService = lobbyService;
        bind();
    }

    private void bind() {
        History.addValueChangeHandler(this);
        eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {

            @Override
            public void onLogin(LoginEvent event) {
                doLogin(event.getPlayer());
            }
        });
    }

    private void doLogin(Player player) {
        History.newItem("login");
        if (lobbyView == null) {
            lobbyView = new LobbyViewImpl();
        }
        if (gameListView == null) {
            gameListView = new GameListViewImpl<GameInfo>();
        }
        new GameListPresenter(lobbyService, eventBus, gameListView, new GameListColumnDefinitions().getColumnDefnitions()).go(container);
        new LobbyPresenter(lobbyService, eventBus, lobbyView, gameListView).go(container);
    }

    @Override
    public void go(final HasWidgets container) {
        this.container = container;

        if ("".equals(History.getToken())) {
            History.newItem("login");
        } else {
            History.fireCurrentHistoryState();
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        if (token != null) {
            if (token.equals("login")) {
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
    }
}
