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

    public SettingsPanel(Settings settings, ClickHandler clickHandler) {
        HorizontalPanel h = new HorizontalPanel();
        h.add(new Label("Map width: "));
        h.add(mapWidth = new TextBox());
        add(h);
        h = new HorizontalPanel();
        h.add(new Label("Map height: "));
        h.add(mapHeight = new TextBox());
        add(h);
        h = new HorizontalPanel();
        h.add(new Label("Player speed: "));
        h.add(playerSpeed = new TextBox());
        add(h);
        h = new HorizontalPanel();
        h.add(new Label("Player bomb start amount: "));
        h.add(playerBombStartAmount = new TextBox());
        add(h);
        h = new HorizontalPanel();
        h.add(new Label("BombTimer: "));
        h.add(bombTimer = new TextBox());
        add(h);
        h = new HorizontalPanel();
        h.add(new Label("Bomb power: "));
        h.add(bombPower = new TextBox());
        add(h);
        add(new Button("Restart", clickHandler));
        update(settings);
    }

    public Settings getSettings(Settings settings) {
        settings.setMapWidth(getInteger(mapWidth, settings.getMapWidth()));
        settings.setMapHeight(getInteger(mapHeight, settings.getMapHeight()));
        settings.setPlayerSpeed(getInteger(playerSpeed, settings.getPlayerSpeed()));
        settings.setPlayerBombStartAmount(getInteger(playerBombStartAmount, settings.getPlayerBombStartAmount()));
        settings.setBombTimer(getInteger(bombTimer, settings.getBombTimer()));
        settings.setBombPower(getInteger(bombPower, settings.getBombPower()));
        update(settings);
        return settings;
    }

    private Integer getInteger(TextBox textBox, int original) {
        try {
            return Integer.parseInt(textBox.getText());
        } catch (NumberFormatException e) {
        }
        return original;
    }

    private void update(Settings settings) {
        mapWidth.setText("" + settings.getMapWidth());
        mapHeight.setText("" + settings.getMapHeight());
        playerSpeed.setText("" + settings.getPlayerSpeed());
        playerBombStartAmount.setText("" + settings.getPlayerBombStartAmount());
        bombTimer.setText("" + settings.getBombTimer());
        bombPower.setText("" + settings.getBombPower());
    }
}
