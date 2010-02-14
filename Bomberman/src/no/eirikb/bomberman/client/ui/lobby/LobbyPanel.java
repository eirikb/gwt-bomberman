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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import de.novanic.eventservice.client.event.RemoteEventService;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import java.util.Date;
import no.eirikb.bomberman.client.event.lobby.GameCreateEvent;
import no.eirikb.bomberman.client.event.lobby.LobbyEvent;
import no.eirikb.bomberman.client.event.lobby.LobbyListenerAdapter;
import no.eirikb.bomberman.client.event.lobby.PlayerJoinEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerQuitEvent;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyPanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, LobbyPanel> {
    }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
    private static final Domain LOBBY_DOMAIN = DomainFactory.getDomain(LobbyEvent.LOBBY_DOMAIN);
    @UiField
    Label infoLabel;
    @UiField
    TabPanel tabPanel;
    @UiField
    GameListPanel gameListPanel;
    @UiField
    GameCreatePanel gameCreatePanel;

    public LobbyPanel(RemoteEventService remoteEventService, GameJoinListener gameJoinListener) {
        initWidget(uiBinder.createAndBindUi(this));
        gameListPanel.setGameJoinListener(gameJoinListener);
        gameCreatePanel.setGameJoinListener(gameJoinListener);
        tabPanel.selectTab(0);
        remoteEventService.addListener(LOBBY_DOMAIN, new DefaultLobbyListener());
    }

    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    private class DefaultLobbyListener extends LobbyListenerAdapter {

        @Override
        public void createGame(GameCreateEvent gameCreateEvent) {
            GameInfo game = gameCreateEvent.getGame();
            infoLabel.setText("Game created: " + game.getName() + " (" + new Date() + ')');
            gameListPanel.addGame(game);

        }

        @Override
        public void playerJoin(PlayerJoinEvent playerJoinEvent) {
            infoLabel.setText("Player join: " + playerJoinEvent.getPlayer().getNick());
        }

        @Override
        public void playerQuitEvent(PlayerQuitEvent playerQuitEvent) {
            Player player = playerQuitEvent.getPlayer();
            infoLabel.setText("Player quit: " + player.getNick() + " (" + new Date() + ')');
        }
    }
}
