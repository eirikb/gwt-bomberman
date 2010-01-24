/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.server;

import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.domain.DomainFactory;
import de.novanic.eventservice.service.RemoteEventServiceServlet;
import no.eirikb.bomberman.client.event.game.GameEvent;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.service.GameService;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameServer extends RemoteEventServiceServlet implements GameService {

    private static final Domain GAME_DOMAIN = DomainFactory.getDomain(GameEvent.GAME_DOMAIN);
    private GameHandler gameHandler;

    public GameServer() {
        gameHandler = GameHandler.getInstance();
    }

    public Game getGame(String name) {
        Player player = gameHandler.getPlayer((String) getThreadLocalRequest().getSession().getAttribute("nick"));
        Game game = gameHandler.getGame(name);
        if (player != null && game != null) {
            for (Player p : game.getPlayers()) {
                if (p.getNick().equals(player.getNick())) {
                    return game;
                }
            }
        }
        return null;
    }
}
