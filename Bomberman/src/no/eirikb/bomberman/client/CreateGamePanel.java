/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.builder.BoxBuilder;
import no.eirikb.bomberman.client.game.builder.BrickBuilder;
import no.eirikb.bomberman.client.game.builder.SpriteArrayBuilder;
import no.eirikb.bomberman.client.service.BombermanService;
import no.eirikb.bomberman.client.service.BombermanServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class CreateGamePanel extends VerticalPanel {

    private Button createGameButton;

    public CreateGamePanel() {
        add(new Label("Create game"));
        HorizontalPanel h = new HorizontalPanel();
        h.add(new Label("Game name: "));
        final TextBox gameNameTextBox = new TextBox();
        h.add(gameNameTextBox);
        add(h);
        final SettingsPanel settingsPanel = new SettingsPanel(null);

        DisclosurePanel disclosurePanel = new DisclosurePanel("Show/Hide settings");
        disclosurePanel.setAnimationEnabled(true);
        disclosurePanel.add(settingsPanel);
        add(disclosurePanel);
        final Label infoLabel = new Label();
        createGameButton = new Button("Create game", new ClickHandler() {

            public void onClick(ClickEvent ce) {
                createGameButton.setEnabled(false);
                settingsPanel.upateSettings();
                infoLabel.setText("Creating game...");
                DeferredCommand.addCommand(new Command() {

                    public void execute() {
                        Sprite[][] sprites = SpriteArrayBuilder.createSprites();
                        infoLabel.setText("Building boxes...");
                        sprites = BoxBuilder.createBoxes(sprites);
                        infoLabel.setText("Building bricks...");
                        sprites = BrickBuilder.createBricks(sprites);
                        BombermanServiceAsync bombermanService = GWT.create(BombermanService.class);
                        bombermanService.createGame(gameNameTextBox.getText(), sprites, Settings.getInstance(), new AsyncCallback<Game>() {

                            public void onFailure(Throwable thrwbl) {
                                infoLabel.setText("Error: " + thrwbl);
                                createGameButton.setEnabled(true);
                            }

                            public void onSuccess(Game game) {
                                createGameButton.setEnabled(true);
                                if (game != null) {
                                    infoLabel.setText("GO!");
                                } else {
                                    infoLabel.setText("Name is already taken");
                                    gameNameTextBox.selectAll();
                                }
                            }
                        });

                    }
                });

            }
        });
        add(createGameButton);
        add(infoLabel);
    }
}
