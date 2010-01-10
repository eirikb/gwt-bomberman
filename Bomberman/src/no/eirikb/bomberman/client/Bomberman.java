package no.eirikb.bomberman.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import no.eirikb.bomberman.client.event.GameEvent;
import no.eirikb.bomberman.client.event.GameListenerAdapter;
import no.eirikb.bomberman.client.event.filter.GameEventFilter;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Bomberman implements EntryPoint {

    private LoginPanel loginPanel;
    private LobbyPanel lobbyPanel;
    private static final Domain CONVERSATION_DOMAIN = DomainFactory.getDomain(GameEvent.GAME_DOMAIN);
    private RemoteEventService myRemoteEventService;

    public void onModuleLoad() {

        showLoginPanel();
        // showTabPanel();
    }

    private void showLoginPanel() {
        RootPanel.get().add(loginPanel = new LoginPanel(new LoginPanel.LoginPanelListener() {

            public void onLogin(Player player) {
                RootPanel.get().remove(loginPanel);
                startEventServiceListener();
                showLobbyPanel();
            }
        }));

        loginPanel.setFocus();
    }

    private void showLobbyPanel() {
        RootPanel.get().add(lobbyPanel = new LobbyPanel(new JoinGameListener() {

            public void onJoin(Game game) {
            }
        }));
    }

    private void startEventServiceListener() {
        final RemoteEventServiceFactory theRemoteEventHandlerFactory = RemoteEventServiceFactory.getInstance();
        myRemoteEventService = theRemoteEventHandlerFactory.getRemoteEventService();

        myRemoteEventService.addListener(CONVERSATION_DOMAIN, new DefaultGameListener(), new GameEventFilter(null));
    }

    private class DefaultGameListener extends GameListenerAdapter {

        @Override
        public void newGame(Game game) {
            lobbyPanel.addGame(game);
        }
    }
}
