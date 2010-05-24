/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import no.eirikb.bomberman.client.LobbyServiceAsync;
import no.eirikb.bomberman.client.view.GameListView;
import no.eirikb.bomberman.client.view.LobbyView;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyPresenter implements Presenter, LobbyView.Presenter {

    private LobbyServiceAsync lobbyService;
    private HandlerManager eventBus;
    private LobbyView view;

    public LobbyPresenter(LobbyServiceAsync lobbyService, HandlerManager eventBus, LobbyView view, GameListView gameListView) {
        this.lobbyService = lobbyService;
        this.eventBus = eventBus;
        this.view = view;
        view.setPresenter(this);
        view.addTab(gameListView.asWidget(), "Gamelist");
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }
}
