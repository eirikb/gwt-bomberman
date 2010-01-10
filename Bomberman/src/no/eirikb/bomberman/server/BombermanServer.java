/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.HashMap;
import java.util.Map;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.service.BombermanService;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombermanServer extends RemoteServiceServlet implements BombermanService {

    private Map<String, Player> players;

    public BombermanServer() {
        players = new HashMap<String, Player>();
    }

    public Player join(String nick) {
        if (players.get(nick) == null) {
            Player player = new Player(nick);
            players.put(nick, player);return player;
        }
        return null;
    }
}
