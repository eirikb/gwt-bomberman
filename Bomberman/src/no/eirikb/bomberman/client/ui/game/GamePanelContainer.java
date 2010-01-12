/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.game;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameListener;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.Way;
import no.eirikb.bomberman.client.game.builder.BombBuilder;
import no.eirikb.bomberman.client.game.builder.PlayerBuilder;
import no.eirikb.bomberman.client.game.handler.GameHandler;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GamePanelContainer extends VerticalPanel implements KeyHackCallback {

    private TextBox textBox;
    private KeyHack keyHack;
    private Game game;
    private Player player;
    private GamePanel gamePanel;
    private GameHandler gameHandler;
    private CheckBox useKeyHack;

    public GamePanelContainer(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public void start() {

        textBox = new TextBox();

        textBox.addKeyDownHandler(new KeyDownHandler() {

            public void onKeyDown(KeyDownEvent event) {
                if (KeyDownEvent.isArrow(event.getNativeKeyCode())) {
                    if (keyHack != null) {
                        keyHack.arrowKeyDown(event);
                    } else {
                        arrowKeyDown(event);
                    }
                } else if (event.getNativeKeyCode() == 32) {
                    Bomb bomb = BombBuilder.createBomb(game.getSprites(), player);
                    if (bomb != null) {
                        game.addBomb(bomb);
                    }
                    if (keyHack != null) {
                        keyHack.setAnotherKeyPresses(true);
                    }
                }
            }
        });

        textBox.addKeyUpHandler(new KeyUpHandler() {

            public void onKeyUp(KeyUpEvent event) {
                textBox.setText("");
                if (KeyUpEvent.isArrow(event.getNativeKeyCode())) {
                    if (keyHack != null) {
                        keyHack.arrowKeyUp(event);
                    } else {
                        arrowKeyUp();
                    }
                }
            }
        });

        textBox.setFocus(true);
        startGame();
    }

    private void startGame() {
        Settings settings = Settings.getInstance();
        gamePanel = new GamePanel(game.getImgSize(), settings.getMapWidth(), settings.getMapHeight());
        gamePanel.getElement().getStyle().setBackgroundColor("#abcdef");
        gameHandler = new GameHandler(game, gamePanel);

        final GamePanelContainer gamePanelContainer = this;
        useKeyHack = new CheckBox("Use KeyHack");
        useKeyHack.setValue(true);
        useKeyHack.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                if (useKeyHack.getValue()) {
                    keyHack = new KeyHack(gamePanelContainer);
                    gameHandler.setKeyHackCallback(keyHack);
                } else {
                    gameHandler.setKeyHackCallback(null);
                    keyHack = null;
                }
                textBox.setFocus(true);
            }
        });
        add(useKeyHack);
        if (useKeyHack.getValue()) {
            keyHack = new KeyHack(this);
            gameHandler.setKeyHackCallback(keyHack);
        }
        BombAmountPanel bombAmountPanel = new BombAmountPanel(player);
        game.addGameListener(bombAmountPanel);
        add(bombAmountPanel);
        gameHandler.start();
        add(gamePanel);
        killCheck();
    }

    public void arrowKeyDown(KeyDownEvent event) {
        if (event.isLeftArrow()) {
            player.setWay(Way.LEFT);
        } else if (event.isUpArrow()) {
            player.setWay(Way.UP);
        } else if (event.isRightArrow()) {
            player.setWay(Way.RIGHT);
        } else if (event.isDownArrow()) {
            player.setWay(Way.DOWN);
        }
    }

    public void arrowKeyUp() {
        player.setWay(Way.NONE);
    }

    private void killCheck() {
        game.addGameListener(new GameListener() {

            public void addSprite(Sprite sprite) {
            }

            public void removeSprite(Sprite sprite) {
                if (sprite instanceof Player) {
                    if ((Player) sprite == player) {
                        gamePanel.remove(player.getImage());
                        final DialogBox dialogBox = new DialogBox();
                        dialogBox.setText("Oh noes!");
                        VerticalPanel v = new VerticalPanel();
                        v.add(new Image("img/ohnoes.jpg"));
                        v.add(new Label("Congratulations! You just died"));
                        Button resurectButton = new Button("Resurect!", new ClickHandler() {

                            public void onClick(ClickEvent event) {
                                player.setSpriteX(0);
                                player.setSpriteY(0);
                                gamePanel.add(player.getImage(), 0, 0);
                                game.addPlayer(player);
                                dialogBox.setVisible(false);
                                dialogBox.hide();
                                textBox.setFocus(true);
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
            }
        });

    }

    public void callback() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
