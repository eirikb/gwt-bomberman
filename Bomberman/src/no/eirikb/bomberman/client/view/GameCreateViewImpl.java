/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import no.eirikb.bomberman.client.ui.SettingsPanel;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameCreateViewImpl extends Composite implements GameCreateView {

    @UiTemplate("GameCreateView.ui.xml")
    interface GameCreateViewUiBinder extends UiBinder<Widget, GameCreateViewImpl> {
    }
    private static GameCreateViewUiBinder uiBinder = GWT.create(GameCreateViewUiBinder.class);
    private Presenter presenter;
    @UiField
    Label infoLabel;
    @UiField
    TextBox gameName;
    @UiField
    SettingsPanel settingsPanel;
    @UiField
    Button createGameButton;

    public GameCreateViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setInfo(String text) {
        infoLabel.setText(text);
    }

    @UiHandler("createGameButton")
    void onCreateGameButtonClick(ClickEvent event) {
        settingsPanel.upateSettings();
        if (presenter != null) {
            presenter.onCreateButtonClicked();
        }
    }

    @Override
    public String getGameName() {
        return gameName.getText();
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
