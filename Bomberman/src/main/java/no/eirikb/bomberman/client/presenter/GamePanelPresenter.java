/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import no.eirikb.bomberman.client.GameServiceAsync;
import no.eirikb.bomberman.client.LobbyServiceAsync;
import no.eirikb.bomberman.client.event.QuitGameEvent;
import no.eirikb.bomberman.client.ui.game.GamePanelContainer;
import no.eirikb.bomberman.client.view.GamePanelView;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GamePanelPresenter implements Presenter, GamePanelView.Presenter {

    private LobbyServiceAsync lobbyService;
    private GameServiceAsync gameService;
    private HandlerManager eventBus;
    private GamePanelView view;
    private Game game;
    private Player player;
    private GamePanelContainer gamePanelContainer;

    public GamePanelPresenter(LobbyServiceAsync lobbyService, GameServiceAsync gameService,
            HandlerManager eventBus, GamePanelView view, GameInfo gameInfo, Player player) {
        this.lobbyService = lobbyService;
        this.gameService = gameService;
        this.eventBus = eventBus;
        this.view = view;
        this.player = player;
        view.setPresenter(this);
        createGameContainer(gameInfo);
    }

    private void createGameContainer(GameInfo gameInfo) {
        gameService.getGame(gameInfo.getName(), new AsyncCallback<Game>() {

            @Override
            public void onFailure(Throwable caught) {
                view.setInfo("ERROR: " + caught);
            }

            @Override
            public void onSuccess(Game result) {
                if (result != null) {
                    game = result;
                    for (Player p : game.getAlivePlayers()) {
                        if (p.getNick().equals(player.getNick())) {
                            player = p;
                            break;
                        }
                    }
                    game.setMe(player);
                    RemoteEventService remoteEventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
                    gamePanelContainer = new GamePanelContainer(remoteEventService, game);
                    view.addWidget(gamePanelContainer);
                    gamePanelContainer.start();
                } else {
                    view.setInfo("Game was not found! oO");
                }
            }
        });
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
