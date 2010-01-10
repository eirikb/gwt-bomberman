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
import java.util.HashMap;
import java.util.Map;
import no.eirikb.bomberman.client.event.CreateGameEvent;
import no.eirikb.bomberman.client.event.GameEvent;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.service.BombermanService;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombermanServer extends RemoteEventServiceServlet implements BombermanService {

    private static final Domain GAME_DOMAIN;

    static {
        GAME_DOMAIN = DomainFactory.getDomain(GameEvent.GAME_DOMAIN);
    }
    private Map<String, Player> players;
    private Map<String, Game> games;

    public BombermanServer() {
        players = new HashMap<String, Player>();
        games = new HashMap<String, Game>();
    }

    public Player join(String nick) {
        if (players.get(nick) == null) {
            Player player = new Player(nick);
            players.put(nick, player);
            getThreadLocalRequest().getSession().setAttribute("nick", nick);
            return player;
        }
        return null;
    }

    public Game createGame(String name, Sprite[][] sprites, Settings settings) {
        Player player = players.get((String) getThreadLocalRequest().getSession().getAttribute("nick"));
        if (games.get(name) == null) {
            Game game = new Game(name, sprites, settings);
            games.put(name, game);
            addEvent(GAME_DOMAIN, new CreateGameEvent(game, player));
            return game;
        } else {
            return null;
        }
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public Game joinGame(String gameName) {
        Game game = games.get(gameName);
        Player player = players.get((String) getThreadLocalRequest().getSession().getAttribute("nick"));
        if (game != null && player != null && game.getPlayersSize() < game.getSettings().getMaxPlayers()) {
            game.addPlayer(player);
            return game;
        }
        return null;
    }
}
