/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import no.eirikb.bomberman.client.LobbyServiceAsync;
import no.eirikb.bomberman.client.event.LoginEvent;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.client.view.LoginView;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class LoginPresenter implements Presenter, LoginView.Presenter {

    private LobbyServiceAsync lobbyService;
    private HandlerManager eventBus;
    private LoginView view;

    public LoginPresenter(LobbyServiceAsync lobbyService, HandlerManager eventBus, LoginView view) {
        this.lobbyService = lobbyService;
        this.eventBus = eventBus;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    @Override
    public void onLoginButtonClicked() {
        view.setInfoText("Logging in...");
        String nick = view.getNick();
        lobbyService.login(nick, new AsyncCallback<Player>() {

            @Override
            public void onFailure(Throwable caught) {
                view.setInfoText("ERROR: " + caught);
            }

            @Override
            public void onSuccess(Player result) {
                if (result != null) {
                    eventBus.fireEvent(new LoginEvent(result));
                } else {
                    view.setInfoText("Nickname taken");
                }
            }
        });
    }
}
