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
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.service.BombermanService;
import no.eirikb.bomberman.client.service.BombermanServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LoginPanel extends VerticalPanel {

    private TextBox nickBox;
    private Label infoLabel;
    private LoginPanelListener loginPanelListener;
    private BombermanServiceAsync bombermanService;

    public LoginPanel(LoginPanelListener loginPanelListener) {
        this.loginPanelListener = loginPanelListener;
        nickBox = new TextBox();
        add(new Label("Nickname:"));
        add(nickBox);
        add(new Button("Log in", new ClickHandler() {

            public void onClick(ClickEvent ce) {
                login();
            }
        }));
        nickBox.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent kpe) {
                if (kpe.getCharCode() == '\r') {
                    login();
                }
            }
        });
        add(infoLabel = new Label());
        bombermanService = GWT.create(BombermanService.class);
    }

    private void login() {
        if (nickBox.getText().length() != 0) {
            infoLabel.setText("Logging on...");
            bombermanService.join(nickBox.getText(), new AsyncCallback<Player>() {

                public void onFailure(Throwable thrwbl) {
                    infoLabel.setText("Could not log on: " + thrwbl);
                }

                public void onSuccess(Player player) {
                    if (player != null) {
                        loginPanelListener.onLogin(player);
                    } else {
                        infoLabel.setText("Nickname taken");
                        nickBox.selectAll();
                    }
                }
            });
        } else {
            nickBox.setFocus(true);
        }
    }

    public void setFocus() {
        nickBox.setFocus(true);
    }

    public interface LoginPanelListener {

        public void onLogin(Player player);
    }
}
