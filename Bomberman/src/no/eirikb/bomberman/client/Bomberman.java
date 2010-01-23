package no.eirikb.bomberman.client;

import no.eirikb.bomberman.client.event.lobby.GameCreateEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerJoinEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerJoinGameEvent;
import no.eirikb.bomberman.client.ui.lobby.LobbyPanel;
import no.eirikb.bomberman.client.ui.lobby.GameJoinListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import no.eirikb.bomberman.client.event.game.GameEvent;
import no.eirikb.bomberman.client.event.game.GameListenerAdapter;
import no.eirikb.bomberman.client.event.lobby.LobbyEvent;
import no.eirikb.bomberman.client.event.lobby.LobbyListenerAdapter;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Bomberman implements EntryPoint {

    private Player player;
    private GameInfo gameInfo;
    private LoginPanel loginPanel;
    private LobbyPanel lobbyPanel;
    private RemoteEventService remoteEventService;
    private static final Domain GAME_DOMAIN = DomainFactory.getDomain(GameEvent.GAME_DOMAIN);
    private static final Domain LOBBY_DOMAIN = DomainFactory.getDomain(LobbyEvent.LOBBY_DOMAIN);

    public void onModuleLoad() {
        showLoginPanel();
    }

    private void showLoginPanel() {
        RootPanel.get().add(loginPanel = new LoginPanel(new LoginPanel.LoginPanelListener() {

            public void onLogin(Player player2) {
                player = player2;
                RootPanel.get().remove(loginPanel);
                startEventServiceListener();
                remoteEventService.addListener(LOBBY_DOMAIN, new DefaultLobbyListener());
                showLobbyPanel();
            }
        }));

        loginPanel.setFocus();
    }

    private void showLobbyPanel() {
        RootPanel.get().add(lobbyPanel = new LobbyPanel(new GameJoinListener() {

            public void onJoin(GameInfo game2) {
                gameInfo = game2;
                lobbyPanel.onJoin(game2);
                if (gameInfo.getPlayerSize() == gameInfo.getMaxPlayers()) {
                    startGame();
                }
            }
        }));
    }

    private void startEventServiceListener() {
        final RemoteEventServiceFactory theRemoteEventHandlerFactory = RemoteEventServiceFactory.getInstance();
        remoteEventService = theRemoteEventHandlerFactory.getRemoteEventService();
    }

    private void startGame() {
        gameInfo = null;
        remoteEventService.removeListeners();
        RootPanel.get().remove(loginPanel);
    }

    private class DefaultGameListener extends GameListenerAdapter {
    }

    private class DefaultLobbyListener extends LobbyListenerAdapter {

        @Override
        public void createGame(GameCreateEvent gameCreateEvent) {
            lobbyPanel.addGame(gameCreateEvent.getGame());
        }

        @Override
        public void playerJoin(PlayerJoinEvent playerJoinEvent) {
            lobbyPanel.playerJoin(playerJoinEvent.getPlayer());
        }

        @Override
        public void playerJoinGame(PlayerJoinGameEvent gameJoinEvent) {
            lobbyPanel.playerJoinGame(gameJoinEvent.getGame(), gameJoinEvent.getPlayer());
            if (gameInfo != null && gameJoinEvent.getGame().getName().equals(gameInfo.getName())) {
                gameInfo = gameJoinEvent.getGame();
                if (gameInfo.getPlayerSize() == gameInfo.getMaxPlayers()) {
                    GWT.log("HERE WE GO!!!", null);
                    startGame();
                }
            }
        }
    }
}
