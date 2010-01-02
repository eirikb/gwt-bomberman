/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import no.eirikb.bomberman.client.game.Settings;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class SettingsPanel extends VerticalPanel {

    private TextBox mapWidth;
    private TextBox mapHeight;
    private TextBox playerSpeed;
    private TextBox playerBombStartAmount;
    private TextBox bombTimer;
    private TextBox bombPower;
    private TextBox brickAmountPercentage;
    private TextBox explosionHitPercentage;

    public SettingsPanel(ClickHandler clickHandler) {
        add("Map width: ", mapWidth = new TextBox());
        add("Map height: ", mapHeight = new TextBox());
        add("Player speed: ", playerSpeed = new TextBox());
        add("Player bomb start amount: ", playerBombStartAmount = new TextBox());
        add("BombTimer: ", bombTimer = new TextBox());
        add("Bomb power: ", bombPower = new TextBox());
        add("Brick amount percentage: ", brickAmountPercentage = new TextBox());
        add("Explosion hit percentage: ", explosionHitPercentage = new TextBox());
        add(new Button("Restart", clickHandler));
        update();
    }

    private void add(String text, TextBox textBox) {
        HorizontalPanel h = new HorizontalPanel();
        h.add(new Label(text));
        h.add(textBox);
        add(h);
    }

    public void upateSettings() {
        Settings settings = Settings.getInstance();
        settings.setMapWidth(getInteger(mapWidth, settings.getMapWidth()));
        settings.setMapHeight(getInteger(mapHeight, settings.getMapHeight()));
        settings.setPlayerSpeed(getInteger(playerSpeed, settings.getPlayerSpeed()));
        settings.setPlayerBombStartAmount(getInteger(playerBombStartAmount, settings.getPlayerBombStartAmount()));
        settings.setBombTimer(getInteger(bombTimer, settings.getBombTimer()));
        settings.setBombPower(getInteger(bombPower, settings.getBombPower()));
        settings.setBrickAmountPercantage(getInteger(brickAmountPercentage, settings.getBrickAmountPercantage()));
        settings.setExplosionHitPercentage(getInteger(explosionHitPercentage, settings.getExplosionHitPercentage()));
    }

    private Integer getInteger(TextBox textBox, int original) {
        try {
            return Integer.parseInt(textBox.getText());
        } catch (NumberFormatException e) {
        }
        return original;
    }

    private void update() {
        Settings settings = Settings.getInstance();
        mapWidth.setText("" + settings.getMapWidth());
        mapHeight.setText("" + settings.getMapHeight());
        playerSpeed.setText("" + settings.getPlayerSpeed());
        playerBombStartAmount.setText("" + settings.getPlayerBombStartAmount());
        bombTimer.setText("" + settings.getBombTimer());
        bombPower.setText("" + settings.getBombPower());
        brickAmountPercentage.setText("" + settings.getBrickAmountPercantage());
        explosionHitPercentage.setText("" + settings.getExplosionHitPercentage());
    }
}
