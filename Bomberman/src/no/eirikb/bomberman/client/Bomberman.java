package no.eirikb.bomberman.client;

import no.eirikb.bomberman.client.event.game.PlayerQuitGameEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerQuitEvent;
import no.eirikb.bomberman.client.ui.LoginPanel;
import no.eirikb.bomberman.client.event.game.PlayerDieEvent;
import no.eirikb.bomberman.client.event.game.PlayerGotPowerupEvent;
import no.eirikb.bomberman.client.event.game.PlayerPlaceBombEvent;
import no.eirikb.bomberman.client.event.game.PlayerResurectEvent;
import no.eirikb.bomberman.client.event.lobby.GameCreateEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerJoinEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerJoinGameEvent;
import no.eirikb.bomberman.client.ui.lobby.LobbyPanel;
import no.eirikb.bomberman.client.ui.lobby.GameJoinListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.RemoteEventServiceFactory;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import no.eirikb.bomberman.client.event.filter.GameEventFilter;
import no.eirikb.bomberman.client.event.game.GameEvent;
import no.eirikb.bomberman.client.event.game.GameListenerAdapter;
import no.eirikb.bomberman.client.event.game.PlayerStartWalkingEvent;
import no.eirikb.bomberman.client.event.game.PlayerStopWalkingEvent;
import no.eirikb.bomberman.client.event.lobby.LobbyEvent;
import no.eirikb.bomberman.client.event.lobby.LobbyListenerAdapter;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.builder.BoxBuilder;
import no.eirikb.bomberman.client.game.builder.BrickBuilder;
import no.eirikb.bomberman.client.game.builder.PowerupBuilder;
import no.eirikb.bomberman.client.game.builder.SpriteArrayBuilder;
import no.eirikb.bomberman.client.loading.LoadListener;
import no.eirikb.bomberman.client.loading.LoadingPanel;
import no.eirikb.bomberman.client.service.GameService;
import no.eirikb.bomberman.client.service.GameServiceAsync;
import no.eirikb.bomberman.client.service.LobbyService;
import no.eirikb.bomberman.client.service.LobbyServiceAsync;
import no.eirikb.bomberman.client.ui.game.GamePanelContainer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Bomberman implements EntryPoint {

    private Player player;
    private GameInfo gameInfo;
    private LoginPanel loginPanel;
    private LobbyPanel lobbyPanel;
    private GamePanelContainer gamePanel;
    private LoadingPanel loadingPanel;
    private RemoteEventService remoteEventService;
    private static final Domain GAME_DOMAIN = DomainFactory.getDomain(GameEvent.GAME_DOMAIN);
    private static final Domain LOBBY_DOMAIN = DomainFactory.getDomain(LobbyEvent.LOBBY_DOMAIN);

    public void onModuleLoad() {
        showLoginPanel();
        //hack();
    }

    // TODO REMOVE!
    private void hack() {
        final LobbyServiceAsync lobbyService = GWT.create(LobbyService.class);
        final String random = Math.random() + "";
        lobbyService.join(random, new AsyncCallback<Player>() {

            public void onFailure(Throwable caught) {
                GWT.log("DOH! " + caught, caught);
            }

            public void onSuccess(Player result) {
                player = result;
                if (result != null) {
                    startEventServiceListener();
                    remoteEventService.addListener(LOBBY_DOMAIN, new DefaultLobbyListener());
                    Sprite[][] sprites = SpriteArrayBuilder.createSprites();
                    sprites = BoxBuilder.createBoxes(sprites);
                    sprites = BrickBuilder.createBricks(sprites);
                    sprites = PowerupBuilder.createPowerups(sprites);
                    Settings.getInstance().setMaxPlayers(1);
                    lobbyService.createGame(random, sprites, Settings.getInstance(), new AsyncCallback<GameInfo>() {

                        public void onFailure(Throwable caught) {
                            GWT.log("DOH! " + caught, caught);
                        }

                        public void onSuccess(GameInfo result) {
                            if (result != null) {
                                lobbyService.joinGame(random, new AsyncCallback<GameInfo>() {

                                    public void onFailure(Throwable caught) {
                                        GWT.log("DOH! " + caught, caught);
                                    }

                                    public void onSuccess(GameInfo result) {
                                        if (result != null) {
                                            gameInfo = result;
                                            startGame();
                                        } else {
                                            GWT.log("ERERROROROROR!", null);
                                        }
                                    }
                                });
                            } else {
                                GWT.log("ERRRORROROROR!  111111111", null);
                            }
                        }
                    });
                } else {
                    GWT.log("EEERRRORORORORR!", null);
                }
            }
        });
    }

    private void showLoginPanel() {

        LobbyServiceAsync lobbyService = GWT.create(LobbyService.class);
        final LoginPanel.LoginPanelListener loginPanelListener = new LoginPanel.LoginPanelListener() {

            public void onLogin(Player player2) {
                player = player2;
                if (loginPanel != null) {
                    RootPanel.get().remove(loginPanel);
                }
                startEventServiceListener();
                remoteEventService.addListener(LOBBY_DOMAIN, new DefaultLobbyListener());
                showLobbyPanel();
            }
        };

        lobbyService.checkSession(new AsyncCallback<Player>() {

            public void onFailure(Throwable caught) {
                GWT.log("ERRRRORR", caught);
            }

            public void onSuccess(Player result) {
                if (result != null) {
                    loginPanelListener.onLogin(result);
                } else {
                    RootPanel.get().add(loginPanel = new LoginPanel(loginPanelListener));
                    loginPanel.setFocus();
                }
            }
        });
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
        lobbyPanel.setInfoText("Welcome " + player.getNick());
    }

    private void startEventServiceListener() {
        final RemoteEventServiceFactory theRemoteEventHandlerFactory = RemoteEventServiceFactory.getInstance();
        remoteEventService = theRemoteEventHandlerFactory.getRemoteEventService();
    }

    private void startGame() {
        String gameName = gameInfo.getName();
        gameInfo = null;
        remoteEventService.removeListeners();
        if (lobbyPanel != null) {
            RootPanel.get().remove(lobbyPanel);
        }
        GameServiceAsync gameService = GWT.create(GameService.class);
        gameService.getGame(gameName, new AsyncCallback<Game>() {

            public void onFailure(Throwable caught) {
                Window.alert("OOMGGGG! " + caught);
            }

            public void onSuccess(final Game result) {
                if (result != null) {

                    if (loadingPanel == null) {
                        loadingPanel = new LoadingPanel(new LoadListener() {

                            public void complete() {
                                for (Player p : result.getAlivePlayers()) {
                                    if (p.getNick().equals(player.getNick())) {
                                        player = p;
                                    }
                                    p.setBombAbount(result.getSettings().getPlayerBombStartAmount());
                                    p.setBombPower(result.getSettings().getBombPower());
                                    p.setSpeed(result.getSettings().getPlayerSpeed());
                                }
                                gamePanel = new GamePanelContainer(result);
                                gamePanel.start(player);
                                RootPanel.get().remove(loadingPanel);
                                RootPanel.get().add(gamePanel);
                                remoteEventService.addListener(GAME_DOMAIN, new DefaultGameListener(),
                                        new GameEventFilter(result.getGameInfo().getName(), player.getNick()));
                            }
                        });
                    }
                    RootPanel.get().add(loadingPanel);
                    DeferredCommand.addCommand(new Command() {

                        public void execute() {
                            loadingPanel.initLoad();
                        }
                    });
                } else {
                    Window.alert("GAME WWWAAAS NULL! OMG!");
                }
            }
        });
    }

    private class DefaultGameListener extends GameListenerAdapter {

        @Override
        public void playerStartWalkingEvent(PlayerStartWalkingEvent event) {
            gamePanel.playerStartWalkingEvent(event);
        }

        @Override
        public void playerStopWalkingEvent(PlayerStopWalkingEvent event) {
            gamePanel.playerStopWalkingEvent(event);
        }

        @Override
        public void playerPlaceBombEvent(PlayerPlaceBombEvent event) {
            gamePanel.playerPlaceBombEvent(event);
        }

        @Override
        public void playerDieEvent(PlayerDieEvent event) {
            gamePanel.playerDieEvent(event);
        }

        @Override
        public void playerResurectEvent(PlayerResurectEvent event) {
            gamePanel.playerResurectEvent(event);
        }

        @Override
        public void playerGotPowerupEvent(PlayerGotPowerupEvent event) {
            gamePanel.playerGotPowerupEvent(event);
        }

        @Override
        public void playerQuitGameEvent(PlayerQuitGameEvent event) {
            gamePanel.playerQuitGameEvent(event);
        }
    }

    private class DefaultLobbyListener extends LobbyListenerAdapter {

        @Override
        public void createGame(GameCreateEvent gameCreateEvent) {
            if (lobbyPanel != null) {
                lobbyPanel.addGame(gameCreateEvent.getGame());
            }
        }

        @Override
        public void playerJoin(PlayerJoinEvent playerJoinEvent) {
            if (lobbyPanel != null) {
                lobbyPanel.playerJoin(playerJoinEvent.getPlayer());
            }
        }

        @Override
        public void playerJoinGame(PlayerJoinGameEvent gameJoinEvent) {
            if (lobbyPanel != null) {
                lobbyPanel.playerJoinGame(gameJoinEvent.getGame(), gameJoinEvent.getPlayer());
            }
            if (gameInfo != null && gameJoinEvent.getGame().getName().equals(gameInfo.getName())) {
                gameInfo = gameJoinEvent.getGame();
                if (gameInfo.getPlayerSize() == gameInfo.getMaxPlayers()) {
                    GWT.log("HERE WE GO!!!", null);
                    startGame();
                }
            }
        }

        @Override
        public void playerQuitEvent(PlayerQuitEvent playerQuitEvent) {
            if (lobbyPanel != null) {
                lobbyPanel.playerQuit(playerQuitEvent.getPlayer());
            }
        }
    }
}
