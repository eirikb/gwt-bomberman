package no.eirikb.bomberman.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.handler.GameHandler;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.loading.LoadingPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Bomberman implements EntryPoint {

    private LoadingPanel loadingPanel;
    private SettingsPanel settingsPanel;
    private Game game;
    private VerticalPanel panel;
    private GameHandler gameHandler;
    private LoginPanel loginPanel;

    public void onModuleLoad() {
        RootPanel.get().add(loginPanel = new LoginPanel(new LoginPanel.LoginPanelListener() {

            public void onLogin(Player player) {
                RootPanel.get().remove(loginPanel);
            }
        }));

        loginPanel.setFocus();
    }

    public void init(Sprite[][] sprites, int imgSize) {
        RootPanel.get().remove(loadingPanel);

        panel = new VerticalPanel();
        RootPanel.get().add(panel);

        settingsPanel = new SettingsPanel(new ClickHandler() {

            public void onClick(ClickEvent event) {
                gameHandler.stop();
                game.removeAllGameListeners();
                settingsPanel.upateSettings();
                RootPanel.get().remove(panel);
                RootPanel.get().add(loadingPanel);
            }
        });

        DisclosurePanel disclosurePanel = new DisclosurePanel("Show/Hide settings");
        disclosurePanel.setAnimationEnabled(true);
        disclosurePanel.add(settingsPanel);
        panel.add(disclosurePanel);

        panel.add(new Label("Put marker inside textbox and use arrows to move, press space for bomb:"));

    }
}
