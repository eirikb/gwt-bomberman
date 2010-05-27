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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GamePanelViewImpl extends Composite implements GamePanelView {

    @UiTemplate("GamePanelView.ui.xml")
    interface GamePanelViewUiBinder extends UiBinder<Widget, GamePanelViewImpl> {
    }
    private static GamePanelViewUiBinder uiBinder = GWT.create(GamePanelViewUiBinder.class);
    @UiField
    Label infoLabel;
    @UiField
    Button quitButton;
    @UiField
    SimplePanel containerPanel;
    private Presenter presenter;

    public GamePanelViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addWidget(Widget widget) {
        containerPanel.add(widget);
    }

    @Override
    public void setInfo(String info) {
        infoLabel.setText(info);
    }

    @UiHandler("quitButton")
    public void onQuitButtonClicked(ClickEvent event) {
        if (presenter != null) {
            presenter.onQuitButtonClicked();
        }
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
