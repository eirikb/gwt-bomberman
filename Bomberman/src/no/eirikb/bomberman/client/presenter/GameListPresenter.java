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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import no.eirikb.bomberman.client.LobbyServiceAsync;
import no.eirikb.bomberman.client.common.ColumnDefinition;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.client.view.GameListView;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameListPresenter implements Presenter, GameListView.Presenter<GameInfo> {

    private LobbyServiceAsync lobbyService;
    private HandlerManager eventBus;
    private GameListView<GameInfo> view;
    private List<GameInfo> games;

    public GameListPresenter(LobbyServiceAsync lobbyService, HandlerManager eventBus, GameListView<GameInfo> view, List<ColumnDefinition<GameInfo>> columnDefinitions) {
        this.lobbyService = lobbyService;
        this.eventBus = eventBus;
        this.view = view;
        view.setPresenter(this);
        view.setColumnDefinitions(columnDefinitions);
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
    public void onItemSelected(GameInfo selectedItem) {
    }
}
