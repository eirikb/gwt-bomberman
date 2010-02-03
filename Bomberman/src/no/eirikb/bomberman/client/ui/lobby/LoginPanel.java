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
import com.google.gwt.event.dom.client.KeyPressEvent;
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
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.service.LobbyService;
import no.eirikb.bomberman.client.service.LobbyServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LoginPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, LoginPanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    private LoginPanelListener loginPanelListener;
    @UiField
    TextBox nickBox;
    @UiField
    Button loginButton;
    @UiField
    Label infoLabel;

    public LoginPanel(LoginPanelListener loginPanelListener) {
        this.loginPanelListener = loginPanelListener;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("nickBox")
    void handleUserNameKeyPress(KeyPressEvent e) {
        if (e.getCharCode() == '\r') {
            handleClick(null);
        }
    }

    @UiHandler("loginButton")
    void handleClick(ClickEvent e) {
        if (nickBox.getText().length() == 0) {
            nickBox.setFocus(true);
        } else {
            infoLabel.setText("Loggin in...");

            DeferredCommand.addCommand(new Command() {

                public void execute() {
                    LobbyServiceAsync bombermanService = GWT.create(LobbyService.class);
                    bombermanService.join(nickBox.getText(), new AsyncCallback<Player>() {

                        public void onFailure(Throwable thrwbl) {
                            infoLabel.setText("Error: " + thrwbl);
                        }

                        public void onSuccess(Player player) {
                            if (player != null) {
                                loginPanelListener.onLogin(player);
                            } else {
                                infoLabel.setText("Nickname already in use");
                            }
                        }
                    });
                }
            });
        }
    }

    public void setFocus() {
        nickBox.setFocus(true);
    }

    public interface LoginPanelListener {

        public void onLogin(Player player);
    }
}
