package no.eirikb.bomberman.client;

import no.eirikb.bomberman.client.ui.LoginPanel;
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
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
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
    private final String VERSION = "0.14";

    public void onModuleLoad() {
        RootPanel.get("version").getElement().setInnerHTML(VERSION);
        showLoginPanel();
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
        RootPanel.get().add(lobbyPanel = new LobbyPanel(remoteEventService, new GameJoinListener() {

            public void onJoin(GameInfo game2) {
                gameInfo = game2;
                startGame();
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
                                        result.setMe(p);
                                    }
                                    p.setBombAbount(result.getSettings().getPlayerBombStartAmount());
                                    p.setBombPower(result.getSettings().getBombPower());
                                    p.setSpeed(result.getSettings().getPlayerSpeed());
                                }
                                gamePanel = new GamePanelContainer(remoteEventService, result, new GamePanelContainer.QuitListener() {

                                    public void onQuit() {
                                        LobbyServiceAsync lobbyService = GWT.create(LobbyService.class);
                                        lobbyService.quitGame(new AsyncCallback() {

                                            public void onFailure(Throwable caught) {
                                            }

                                            public void onSuccess(Object result) {
                                            }
                                        });
                                        RootPanel.get().remove(gamePanel);
                                        RootPanel.get().add(lobbyPanel);
                                    }
                                });
                                gamePanel.start();
                                RootPanel.get().remove(loadingPanel);
                                RootPanel.get().add(gamePanel);
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
}
