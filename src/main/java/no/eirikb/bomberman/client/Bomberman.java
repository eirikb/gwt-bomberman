/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class Bomberman implements EntryPoint {

    private final String VERSION = "0.20";

    @Override
    public void onModuleLoad() {

        RootPanel version = RootPanel.get("version");
        if (version != null) {
            version.getElement().setInnerHTML(VERSION);
        }

        LobbyServiceAsync lobbyService = GWT.create(LobbyService.class);
        GameServiceAsync gameService = GWT.create(GameService.class);
        HandlerManager eventBus = new HandlerManager(null);
        AppController appViewer = new AppController(lobbyService, gameService, eventBus);
        appViewer.go(RootPanel.get("bombermanContent"));
    }
}
