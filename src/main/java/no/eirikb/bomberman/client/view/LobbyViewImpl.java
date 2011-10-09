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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyViewImpl extends Composite implements LobbyView {

    @UiTemplate("LobbyView.ui.xml")
    interface LobbyViewUiBinder extends UiBinder<Widget, LobbyViewImpl> {
    }
    private static LobbyViewUiBinder uiBinder = GWT.create(LobbyViewUiBinder.class);
    @UiField
    DecoratedTabPanel tabPanel;
    private Presenter presenter;

    public LobbyViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addTab(Widget tab, String tabText) {
        tabPanel.add(tab, tabText);
        if (tabPanel.getTabBar().getTabCount() == 1) {
            tabPanel.selectTab(0);
        }
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
