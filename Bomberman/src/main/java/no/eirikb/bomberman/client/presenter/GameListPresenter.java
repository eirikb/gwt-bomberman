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
import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.listener.RemoteEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import no.eirikb.bomberman.client.LobbyServiceAsync;
import no.eirikb.bomberman.client.common.ColumnDefinition;
import no.eirikb.bomberman.client.event.JoinGameEvent;
import no.eirikb.bomberman.client.event.JoinGameEventHandler;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.client.view.GameListView;
import no.eirikb.bomberman.shared.event.lobby.GameCreateEvent;
import no.eirikb.bomberman.shared.event.lobby.GameRemoveEvent;
import no.eirikb.bomberman.shared.event.lobby.LobbyEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameListPresenter implements Presenter, GameListView.Presenter<GameInfo> {

    private LobbyServiceAsync lobbyService;
    private HandlerManager eventBus;
    private GameListView<GameInfo> view;
    private List<GameInfo> games;

    public GameListPresenter(LobbyServiceAsync lobbyService, HandlerManager eventBus, GameListView<GameInfo> view,
            List<ColumnDefinition<GameInfo>> columnDefinitions) {
        this.lobbyService = lobbyService;
        this.eventBus = eventBus;
        this.view = view;
        view.setPresenter(this);
        view.setColumnDefinitions(columnDefinitions);
        startRemoteService();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
        fetchGameDetails();
    }

    private void fetchGameDetails() {
        view.setInfo("Fetching games...");
        lobbyService.getGames(new AsyncCallback<Map<String, GameInfo>>() {

            @Override
            public void onFailure(Throwable caught) {
                view.setInfo("ERROR: " + caught);
            }

            @Override
            public void onSuccess(Map<String, GameInfo> result) {
                if (result != null) {
                    games = new ArrayList<GameInfo>(result.values());
                    view.setRowData(games);
                    if (result.isEmpty()) {
                        view.setInfo("No games found");
                    } else {
                        view.setInfo(result.size() + " games fetched");
                    }
                } else {
                    view.setInfo("No games found");
                }
            }
        });
    }

    @Override
    public void onItemClicked(GameInfo selectedItem) {
        lobbyService.joinGame(selectedItem.getName(), new AsyncCallback<GameInfo>() {

            @Override
            public void onFailure(Throwable caught) {
                view.setInfo("ERROR: " + caught);
            }

            @Override
            public void onSuccess(GameInfo result) {
                if (result != null) {
                    eventBus.fireEvent(new JoinGameEvent(result));
                } else {
                    view.setInfo("Unkown ERROR! :O");
                }
            }
        });
    }

    private void startRemoteService() {
        final RemoteEventService remoteEventService = RemoteEventServiceFactory.getInstance().getRemoteEventService();
        remoteEventService.addListener(LobbyEvent.LOBBY_DOMAIN, new RemoteEventListener() {

            @Override
            public void apply(Event anEvent) {
                if (anEvent instanceof GameCreateEvent) {
                    GameCreateEvent event = (GameCreateEvent) anEvent;
                    view.addData(event.getGame());
                    games.add(event.getGame());
                } else if (anEvent instanceof GameRemoveEvent) {
                    GameRemoveEvent event = (GameRemoveEvent) anEvent;
                    GameInfo game = null;
                    for (GameInfo g : games) {
                        if (g.getName().equals(event.getGame().getName())) {
                            game = g;
                            break;
                        }
                    }
                    view.removeData(game);
                }
            }
        });

        this.eventBus.addHandler(JoinGameEvent.TYPE, new JoinGameEventHandler() {

            @Override
            public void onJoinGame(JoinGameEvent event) {
                remoteEventService.removeListeners();
            }
        });
    }
}
