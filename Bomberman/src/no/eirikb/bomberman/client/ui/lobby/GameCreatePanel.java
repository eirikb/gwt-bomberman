/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.lobby;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.builder.BoxBuilder;
import no.eirikb.bomberman.client.game.builder.BrickBuilder;
import no.eirikb.bomberman.client.game.builder.PowerupBuilder;
import no.eirikb.bomberman.client.game.builder.SpriteArrayBuilder;
import no.eirikb.bomberman.client.service.LobbyService;
import no.eirikb.bomberman.client.service.LobbyServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameCreatePanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, GameCreatePanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    private GameJoinListener gameJoinListener;
    @UiField
    Label infoLabel;
    @UiField
    TextBox gameNameBox;
    @UiField
    SettingsPanel settingsPanel;
    @UiField
    Button createGameButton;

    public GameCreatePanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("createGameButton")
    public void createGameButtonClick(ClickEvent event) {
        String gameName = gameNameBox.getText();
        if (gameName != null && gameName.length() > 0) {
            createGameButton.setEnabled(false);
            DeferredCommand.addCommand(new Command() {

                public void execute() {
                    infoLabel.setText("Creating game...");
                }
            });
            settingsPanel.upateSettings();
            Sprite[][] sprites = SpriteArrayBuilder.createSprites();
            setInfoTextBackground("Building boxes...");
            sprites = BoxBuilder.createBoxes(sprites);
            setInfoTextBackground("Building bricks...");
            sprites = BrickBuilder.createBricks(sprites);
            setInfoTextBackground("Building powerups...");
            sprites = PowerupBuilder.createPowerups(sprites);
            final LobbyServiceAsync bombermanService = GWT.create(LobbyService.class);
            bombermanService.createGame(gameName, sprites, Settings.getInstance(), new AsyncCallback<GameInfo>() {

                public void onFailure(Throwable caught) {
                    infoLabel.setText("Error: " + caught);
                    createGameButton.setEnabled(true);
                }

                public void onSuccess(GameInfo result) {
                    createGameButton.setEnabled(true);
                    if (result != null) {
                        setInfoTextBackground("Game created! Joining...");
                        bombermanService.joinGame(result.getName(), new AsyncCallback<GameInfo>() {

                            public void onFailure(Throwable caught) {
                                infoLabel.setText("Error: " + caught);
                            }

                            public void onSuccess(GameInfo result) {
                                if (result != null) {
                                    gameJoinListener.onJoin(result);
                                } else {
                                    infoLabel.setText("Unkown error!");
                                }
                            }
                        });
                    } else {
                        infoLabel.setText("Game name already in use");
                        gameNameBox.selectAll();
                    }
                }
            });
        } else {
            infoLabel.setText("Please enter a name for your game");
            gameNameBox.setFocus(true);
        }
    }

    private void setInfoTextBackground(final String text) {
        DeferredCommand.addCommand(new Command() {

            public void execute() {
                infoLabel.setText(text);
            }
        });
    }

    public void setGameJoinListener(GameJoinListener gameJoinListener) {
        this.gameJoinListener = gameJoinListener;
    }
}
