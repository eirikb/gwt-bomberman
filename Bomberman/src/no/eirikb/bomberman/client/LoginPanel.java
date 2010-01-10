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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.service.BombermanService;
import no.eirikb.bomberman.client.service.BombermanServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LoginPanel extends AbsolutePanel {

    private TextBox nickBox;
    private Label infoLabel;
    private LoginPanelListener loginPanelListener;
    private BombermanServiceAsync bombermanService;
    private Button loginButton;

    public LoginPanel(LoginPanelListener loginPanelListener) {
        this.loginPanelListener = loginPanelListener;
        Image image = new Image("img/bg.png");
        image.setSize("640px", "480px");
        image.getElement().getStyle().setBackgroundColor("lime");
        add(image);
        nickBox = new TextBox();
        add(new Label("Nickname:"), 200, 390);
        add(nickBox, 200, 410);
        add(loginButton = new Button("Log in", new ClickHandler() {

            public void onClick(ClickEvent ce) {
                login();
            }
        }), 200, 440);
        nickBox.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent kpe) {
                if (kpe.getCharCode() == '\r') {
                    login();
                }
            }
        });
        add(infoLabel = new Label(), 270, 448);
        bombermanService = GWT.create(BombermanService.class);
    }

    private void login() {
        if (nickBox.getText().length() != 0 && loginButton.isEnabled()) {
            loginButton.setEnabled(false);
            infoLabel.setText("Logging on...");

            DeferredCommand.addCommand(new Command() {

                public void execute() {
                    bombermanService.join(nickBox.getText(), new AsyncCallback<Player>() {

                        public void onFailure(Throwable thrwbl) {
                            infoLabel.setText("Could not log on: " + thrwbl);
                            loginButton.setEnabled(true);
                        }

                        public void onSuccess(Player player) {
                            loginButton.setEnabled(true);
                            if (player != null) {
                                loginPanelListener.onLogin(player);
                            } else {
                                infoLabel.setText("Nickname taken");
                                nickBox.selectAll();
                            }
                        }
                    });
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
