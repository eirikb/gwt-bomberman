/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.domain.Domain;
import no.eirikb.bomberman.shared.event.filter.GameEventFilter;
import no.eirikb.bomberman.shared.event.game.GameEvent;
import no.eirikb.bomberman.shared.event.game.GameListenerAdapter;
import no.eirikb.bomberman.shared.event.game.PlayerDieEvent;
import no.eirikb.bomberman.shared.event.game.PlayerGotPowerupEvent;
import no.eirikb.bomberman.shared.event.game.PlayerPlaceBombEvent;
import no.eirikb.bomberman.shared.event.game.PlayerResurectEvent;
import no.eirikb.bomberman.shared.event.game.PlayerStartWalkingEvent;
import no.eirikb.bomberman.shared.event.game.PlayerStopWalkingEvent;
import no.eirikb.bomberman.shared.event.shared.PlayerQuitGameEvent;
import no.eirikb.bomberman.game.Bomb;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.GameListener;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.game.Way;
import no.eirikb.bomberman.game.builder.BombBuilder;
import no.eirikb.bomberman.game.handler.GameHandler;
import no.eirikb.bomberman.game.powerup.Powerup;
import no.eirikb.bomberman.client.GameService;
import no.eirikb.bomberman.client.GameServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GamePanelContainer extends VerticalPanel implements KeyHackCallback {

    public interface QuitListener {

        void onQuit();
    }
    private static final Domain GAME_DOMAIN = GameEvent.GAME_DOMAIN;
    private FocusPanel focusPanel;
    private KeyHack keyHack;
    private Game game;
    private GamePanel gamePanel;
    private GameHandler gameHandler;
    private GameServiceAsync gameService;

    public GamePanelContainer(RemoteEventService remoteEventService, Game game) {
        this.game = game;
        gameService = GWT.create(GameService.class);
        remoteEventService.addListener(GAME_DOMAIN, new DefaultGameListener(), new GameEventFilter(game.getGameInfo().getName(), game.getMe().getNick()));
    }

    public void start() {
        focusPanel = new FocusPanel();
        focusPanel.addKeyDownHandler(new KeyDownHandler() {

            @Override
            public void onKeyDown(KeyDownEvent event) {
                event.preventDefault();
                event.stopPropagation();
                if (KeyDownEvent.isArrow(event.getNativeKeyCode())) {
                    if (keyHack != null) {
                        keyHack.arrowKeyDown(event);
                    } else {
                        arrowKeyDown(event);
                    }
                } else if (event.getNativeKeyCode() == 32) {
                    Bomb bomb = BombBuilder.createBomb(game.getSprites(), game.getMe());
                    if (bomb != null) {
                        game.addBomb(bomb);
                        gameService.addBomb(bomb, new AsyncCallback() {

                            public void onFailure(Throwable caught) {
                            }

                            public void onSuccess(Object result) {
                            }
                        });
                    }
                    if (keyHack != null) {
                        keyHack.setAnotherKeyPresses(true);
                    }
                }
            }
        });

        focusPanel.addKeyUpHandler(new KeyUpHandler() {

            public void onKeyUp(KeyUpEvent event) {
                if (KeyUpEvent.isArrow(event.getNativeKeyCode())) {
                    if (keyHack != null) {
                        keyHack.arrowKeyUp(event);
                    } else {
                        arrowKeyUp();
                    }
                }
            }
        });
        startGame();
    }

    private void startGame() {
        Settings.inject(game.getSettings());
        Settings settings = Settings.getInstance();
        gamePanel = new GamePanel(game.getMe(), game.getImgSize(), settings.getMapWidth(), settings.getMapHeight());
        gamePanel.getElement().getStyle().setBackgroundColor("#abcdef");
        gameHandler = new GameHandler(game, gamePanel);
        final GamePanelContainer gamePanelContainer = this;
        keyHack = new KeyHack(this);
        gameHandler.setKeyHackCallback(keyHack);

        BombAmountPanel bombAmountPanel = new BombAmountPanel(gamePanel.getPlayer());
        game.addGameListener(bombAmountPanel);
        add(bombAmountPanel);
        gameHandler.start();
        add(focusPanel);
        focusPanel.add(gamePanel);
        killCheck();
        DeferredCommand.addCommand(new Command() {

            @Override
            public void execute() {
                focusPanel.setFocus(true);
            }
        });
    }

    @Override
    public void arrowKeyDown(KeyDownEvent event) {
        Player player = gamePanel.getPlayer();
        if (event.isLeftArrow()) {
            player.setWay(Way.LEFT);
        } else if (event.isUpArrow()) {
            player.setWay(Way.UP);
        } else if (event.isRightArrow()) {
            player.setWay(Way.RIGHT);
        } else if (event.isDownArrow()) {
            player.setWay(Way.DOWN);
        }

        gameService.startWalking(player.getWay(), player.getX(), player.getY(), new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Object result) {
            }
        });
    }

    @Override
    public void arrowKeyUp() {
        Player player = gamePanel.getPlayer();
        player.setWay(Way.NONE);
        gameService.stopWalking(player.getX(), player.getY(), new AsyncCallback() {

            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Object result) {
            }
        });
    }

    private void killCheck() {
        game.addGameListener(new GameListener() {

            @Override
            public void addSprite(Sprite sprite) {
            }

            @Override
            public void removeSprite(Sprite sprite) {
            }

            @Override
            public void bump(Player player, Sprite sprite) {
            }

            @Override
            public void playerDie(final Player player) {
                if (player == gamePanel.getPlayer()) {
                    gameService.died(new AsyncCallback() {

                        @Override
                        public void onFailure(Throwable caught) {
                        }

                        @Override
                        public void onSuccess(Object result) {
                        }
                    });
                    gamePanel.remove(player.getImage());
                    final DialogBox dialogBox = new DialogBox();
                    dialogBox.setText("Oh noes!");
                    VerticalPanel v = new VerticalPanel();
                    v.add(new Image("img/ohnoes.jpg"));
                    v.add(new Label("Congratulations! You just died"));
                    Button resurectButton = new Button("Resurect!", new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                            gameService.resurect(new AsyncCallback() {

                                @Override
                                public void onFailure(Throwable caught) {
                                }

                                @Override
                                public void onSuccess(Object result) {
                                }
                            });
                            player.setX(player.getStartX());
                            player.setY(player.getStartY());
                            game.playerLive(player);
                            dialogBox.setVisible(false);
                            dialogBox.hide();
                            focusPanel.setFocus(true);
                        }
                    });
                    v.add(resurectButton);
                    dialogBox.setWidget(v);
                    dialogBox.setAnimationEnabled(true);
                    dialogBox.setPopupPosition(gamePanel.getAbsoluteLeft()
                            + (Settings.getInstance().getMapWidth() / 4),
                            gamePanel.getAbsoluteTop() + (Settings.getInstance().getMapHeight() / 4));
                    dialogBox.show();
                    resurectButton.setFocus(true);
                }
            }

            @Override
            public void playerLive(Player player) {
            }
        });

    }

    @Override
    public void callback() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class DefaultGameListener extends GameListenerAdapter {

        @Override
        public void playerStartWalkingEvent(PlayerStartWalkingEvent event) {
            Player player = game.getAlivePlayer(event.getPlayerNick());
            if (player != null && player != gamePanel.getPlayer()) {
                player.setWay(event.getWay());
            }
        }

        @Override
        public void playerStopWalkingEvent(PlayerStopWalkingEvent event) {
            Player player = game.getAlivePlayer(event.getPlayerNick());
            if (player != null && player != gamePanel.getPlayer()) {
                player.setWay(Way.NONE);
                player.setX(event.getX());
                player.setY(event.getY());
            }
        }

        @Override
        public void playerPlaceBombEvent(PlayerPlaceBombEvent event) {
            Bomb b = event.getBomb();
            Player player = game.getAlivePlayer(event.getPlayerNick());
            if (player != null && player != gamePanel.getPlayer()) {
                game.addBomb(new Bomb(b.getSpriteX(), b.getSpriteY(), player, Settings.getInstance().getBombTimer(), b.getPower()));
            }
        }

        @Override
        public void playerDieEvent(PlayerDieEvent event) {
            Player player = game.getAlivePlayer(event.getPlayerNick());
            if (player != null && player != gamePanel.getPlayer()) {
                game.playerDie(player);
            }
        }

        @Override
        public void playerResurectEvent(PlayerResurectEvent event) {
            Player player = game.getDeadPlayer(event.getPlayerNick());
            if (player != null && player != gamePanel.getPlayer()) {
                player.setX(player.getStartX());
                player.setY(player.getStartY());
                game.playerLive(player);
            }
        }

        @Override
        public void playerGotPowerupEvent(PlayerGotPowerupEvent event) {
            Player player = game.getAlivePlayer(event.getPlayerNick());
            if (player != null && player != gamePanel.getPlayer()) {
                Powerup powerup = event.getPowerup();
                event.getPowerup().powerUp(player);
                game.removePowerup(powerup);
            }
        }

        @Override
        public void playerQuitGameEvent(PlayerQuitGameEvent event) {
            Player player = game.getAlivePlayer(event.getPlayer().getNick());
            player = player == null ? game.getDeadPlayer(event.getPlayer().getNick()) : player;
            GWT.log("A player quit inside my game! :O   (" + player + ')', null);
        }
    }
}
