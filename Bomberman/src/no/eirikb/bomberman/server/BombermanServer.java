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
import no.eirikb.bomberman.client.event.lobby.GameCreateEvent;
import no.eirikb.bomberman.client.event.game.GameEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerJoinGameEvent;
import no.eirikb.bomberman.client.event.lobby.LobbyEvent;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.service.BombermanService;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombermanServer extends RemoteEventServiceServlet implements BombermanService {

    private static final Domain GAME_DOMAIN = DomainFactory.getDomain(GameEvent.GAME_DOMAIN);
    private static final Domain LOBBY_DOMAIN = DomainFactory.getDomain(LobbyEvent.LOBBY_DOMAIN);
    private Map<String, Player> players;
    private Map<String, Game> games;
    private Map<String, GameInfo> gameInfos;

    public BombermanServer() {
        players = new HashMap<String, Player>();
        games = new HashMap<String, Game>();
        gameInfos = new HashMap<String, GameInfo>();
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

    public GameInfo createGame(String name, Sprite[][] sprites, Settings settings) {
        Player player = players.get((String) getThreadLocalRequest().getSession().getAttribute("nick"));
        if (games.get(name) == null) {
            Game game = new Game(name, sprites, settings);
            games.put(name, game);
            gameInfos.put(name, game.getGameInfo());
            addEvent(LOBBY_DOMAIN, new GameCreateEvent(player, game.getGameInfo()));
            return game.getGameInfo();
        } else {
            return null;
        }
    }

    public Map<String, GameInfo> getGames() {
        return gameInfos;
    }

    public GameInfo joinGame(String gameName) {
        Game game = games.get(gameName);
        Player player = players.get((String) getThreadLocalRequest().getSession().getAttribute("nick"));
        if (game != null && player != null && game.getPlayersSize() < game.getSettings().getMaxPlayers()) {
            game.addPlayer(player);
            addEvent(LOBBY_DOMAIN, new PlayerJoinGameEvent(player, game.getGameInfo()));
            return game.getGameInfo();
        }
        return null;
    }
}
