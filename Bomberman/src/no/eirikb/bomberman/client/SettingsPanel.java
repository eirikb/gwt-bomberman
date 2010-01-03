/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
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
    private TextBox sleepTime;
    private TextBox percentagePowerup;

    public SettingsPanel(ClickHandler clickHandler) {
        add("Map width (px): ", mapWidth = new TextBox(),
                "Width of the visible map in pixels");
        add("Map height (px): ", mapHeight = new TextBox(),
                "Height of the visible map in pixels");
        add("Player speed: ", playerSpeed = new TextBox(),
                "Speed of the player (man). Amount of pixels for each 'TICK' (one TICK ~ 50 ms). Can be decimal number, such as 0.5");
        add("Player bomb start amount: ", playerBombStartAmount = new TextBox(),
                "Amount of bombs the player can have on the map at one time");
        add("BombTimer: ", bombTimer = new TextBox(),
                "Amount of 'TICKs' before the bomb explodes (one TICK ~ 50 ms)");
        add("Bomb power: ", bombPower = new TextBox(),
                "Amount of sprites the bomb should exand out");
        add("Brick amount percentage: ", brickAmountPercentage = new TextBox(),
                "How much percentage of the map should be filled with bricks (entire map size in sprites)");
        add("Explosion hit percentage: ", explosionHitPercentage = new TextBox(),
                "How much percentage of the players body must be inside the flame before he/she is killed. Set this to 101 and you ARE GOD");
        add("Sleep time: ", sleepTime = new TextBox(),
                "How long each 'TICK' lasts. The interval between each calculation in milliseconds");
        add("Percentage powerup: ", percentagePowerup = new TextBox(),
                "Amount of chance that when the brick perishes there will be a powerup");
        add(new Button("Restart", clickHandler));
        update();
    }

    private void add(final String text, TextBox textBox, final String toolToolTipText) {
        HorizontalPanel h = new HorizontalPanel();
        final Image infoImage = new Image("img/infoicon.png");
        infoImage.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent ce) {
                final PopupPanel popupPanel = new PopupPanel(true);
                VerticalPanel v = new VerticalPanel();
                v.add(new PushButton("close", new ClickHandler() {

                    public void onClick(ClickEvent ce) {
                        popupPanel.hide();
                    }
                }));
                Label l = new Label(text);
                l.getElement().getStyle().setFontWeight(FontWeight.BOLD);
                v.add(l);
                v.add(new Label(toolToolTipText));
                popupPanel.setWidget(v);
                popupPanel.setPopupPosition(infoImage.getAbsoluteLeft() + infoImage.getWidth() + 10,
                        infoImage.getAbsoluteTop());
                popupPanel.show();
            }
        });
        h.add(infoImage);
        h.add(new Label(text));
        h.add(textBox);
        add(h);
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
    }

    private Double getDouble(TextBox textBox, double original) {
        try {
            return Double.parseDouble(textBox.getText());
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
        sleepTime.setText("" + settings.getSleepTime());
        percentagePowerup.setText("" + settings.getPercentagePowerup());
    }
}
