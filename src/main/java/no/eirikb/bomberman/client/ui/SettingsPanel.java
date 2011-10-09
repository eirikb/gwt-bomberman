/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import no.eirikb.bomberman.game.Settings;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class SettingsPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, SettingsPanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    TextBox mapWidth;
    @UiField
    TextBox mapHeight;
    @UiField
    TextBox playerSpeed;
    @UiField
    TextBox playerBombStartAmount;
    @UiField
    TextBox bombTimer;
    @UiField
    TextBox bombPower;
    @UiField
    TextBox brickAmountPercentage;
    @UiField
    TextBox explosionHitPercentage;
    @UiField
    TextBox sleepTime;
    @UiField
    TextBox percentagePowerup;
    @UiField
    TextBox imageSize;
    @UiField
    TextBox maxPlayers;

    public SettingsPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        updateBoxes();
    }

    public void upateSettings() {
        Settings settings = Settings.getInstance();
        settings.setMapWidth(getDouble(mapWidth, settings.getMapWidth()).intValue());
        settings.setMapHeight(getDouble(mapHeight, settings.getMapHeight()).intValue());
        settings.setPlayerSpeed(getDouble(playerSpeed, settings.getPlayerSpeed()));
        settings.setPlayerBombStartAmount(getDouble(playerBombStartAmount, settings.getPlayerBombStartAmount()).intValue());
        settings.setBombTimer(getDouble(bombTimer, settings.getBombTimer()).intValue());
        settings.setBombPower(getDouble(bombPower, settings.getBombPower()).intValue());
        settings.setBrickAmountPercantage(getDouble(brickAmountPercentage, settings.getBrickAmountPercantage()).intValue());
        settings.setExplosionHitPercentage(getDouble(explosionHitPercentage, settings.getExplosionHitPercentage()).intValue());
        settings.setSleepTime(getDouble(sleepTime, settings.getSleepTime()).intValue());
        settings.setPercentagePowerup(getDouble(percentagePowerup, settings.getPercentagePowerup()).intValue());
        settings.setImgSize(getDouble(imageSize, settings.getImgSize()).intValue());
        settings.setMaxPlayers(getDouble(maxPlayers, settings.getMaxPlayers()).intValue());
    }

    private Double getDouble(TextBox textBox, double original) {
        try {
            return Double.parseDouble(textBox.getText());
        } catch (NumberFormatException e) {
        }
        return original;
    }

    private void updateBoxes() {
        Settings settings = Settings.getInstance();
        mapWidth.setText("" + settings.getMapWidth());
        mapHeight.setText("" + settings.getMapHeight());
        playerSpeed.setText("" + settings.getPlayerSpeed());
        playerBombStartAmount.setText("" + settings.getPlayerBombStartAmount());
        bombTimer.setText("" + settings.getBombTimer());
        bombPower.setText("" + settings.getBombPower());
        brickAmountPercentage.setText("" + settings.getBrickAmountPercantage());
        explosionHitPercentage.setText("" + settings.getExplosionHitPercentage());
        sleepTime.setText("" + settings.getSleepTime());
        percentagePowerup.setText("" + settings.getPercentagePowerup());
        imageSize.setText("" + settings.getImgSize());
        maxPlayers.setText("" + settings.getMaxPlayers());
    }
}
