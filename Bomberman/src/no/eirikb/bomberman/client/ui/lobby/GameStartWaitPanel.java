/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.lobby;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameStartWaitPanel extends VerticalPanel {

    private Label infolLabel;
    private Game game;

    public GameStartWaitPanel(Game game) {
        this.game = game;
        add(infolLabel = new Label("Waiting for players to connect..."));
    }

    public void joinGame(Game game, Player player) {
        this.game = game;
        infolLabel.setText("Player joined: " + player.getNick());
    }
}
