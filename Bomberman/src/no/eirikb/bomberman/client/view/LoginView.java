/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.view;

import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface LoginView {

    public interface Presenter {

        void onLoginButtonClicked();
    }

    void setPresenter(Presenter presenter);

    void setInfoText(String text);

    String getNick();

    Widget asWidget();
}
