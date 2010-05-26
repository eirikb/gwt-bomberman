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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import no.eirikb.bomberman.client.LobbyServiceAsync;
import no.eirikb.bomberman.client.event.QuitGameEvent;
import no.eirikb.bomberman.client.ui.game.GamePanelContainer;
import no.eirikb.bomberman.client.view.GamePanelView;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GamePanelPresenter implements Presenter, GamePanelView.Presenter {

    private LobbyServiceAsync lobbyService;
    private HandlerManager eventBus;
    private GamePanelView view;

    public GamePanelPresenter(LobbyServiceAsync lobbyService, HandlerManager eventBus, GamePanelView view) {
        this.lobbyService = lobbyService;
        this.eventBus = eventBus;
        this.view = view;
        view.setPresenter(this);
        //view.addWidget(new GamePanelContainer(null, null, null));
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    @Override
    public void onQuitButtonClicked() {
        lobbyService.quitGame(new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Object result) {
            }
        });
        eventBus.fireEvent(new QuitGameEvent());
    }
}
