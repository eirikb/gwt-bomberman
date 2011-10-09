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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class InfoPopup extends Composite {

    interface MyUiBinder extends UiBinder<Widget, InfoPopup> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    @UiField
    PopupPanel popupPanel;
    @UiField
    Image infoIcon;
    @UiField
    PushButton closeButton;
    @UiField
    Label infoLabel;

    public InfoPopup() {
        initWidget(uiBinder.createAndBindUi(this));
        popupPanel.show();
        popupPanel.hide();
    }

    @UiHandler("infoIcon")
    void openPopupClick(ClickEvent e) {
        popupPanel.setPopupPosition(infoIcon.getAbsoluteLeft() + (infoIcon.getWidth() * 2),
                infoIcon.getAbsoluteTop());
        popupPanel.show();
    }

    @UiHandler("closeButton")
    void closeButtonClick(ClickEvent e) {
        popupPanel.hide();
    }

    public void setInfoText(String infoText) {
        infoLabel.setText(infoText);
    }
}
