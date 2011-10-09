/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class LoginViewImpl extends Composite implements LoginView {

    @UiTemplate("LoginView.ui.xml")
    interface ContactsViewUiBinder extends UiBinder<Widget, LoginViewImpl> {
    }
    private static ContactsViewUiBinder uiBinder = GWT.create(ContactsViewUiBinder.class);
    @UiField
    TextBox nickBox;
    @UiField
    Button loginButton;
    @UiField
    Label infoLabel;
    private Presenter presenter;

    public LoginViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    @Override
    public String getNick() {
        return nickBox.getText();
    }

    @UiHandler("loginButton")
    void onAddButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onLoginButtonClicked();
        }
    }

    @UiHandler("nickBox")
    void onKeyPressed(KeyPressEvent event) {
        if (event.getCharCode() == 13) {
            onAddButtonClicked(null);
        }
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
