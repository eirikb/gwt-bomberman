/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.view;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public interface GameCreateView {

    public interface Presenter {

        void onCreateButtonClicked();
    }

    void setPresenter(Presenter presenter);

    void setInfo(String text);

    String getGameName();

    Widget asWidget();
}
