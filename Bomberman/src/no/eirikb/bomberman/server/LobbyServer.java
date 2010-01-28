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
import java.util.Map;
import no.eirikb.bomberman.client.event.lobby.GameCreateEvent;
import no.eirikb.bomberman.client.event.lobby.PlayerJoinGameEvent;
import no.eirikb.bomberman.client.event.lobby.LobbyEvent;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.service.LobbyService;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyServer extends RemoteEventServiceServlet implements LobbyService {

    private static final Domain LOBBY_DOMAIN = DomainFactory.getDomain(LobbyEvent.LOBBY_DOMAIN);
    private GameHandler gameHandler;

    public LobbyServer() {
        gameHandler = GameHandler.getInstance();
    }

    public Player join(String nick) {
        if (gameHandler.getPlayer(nick) == null) {
            Player player = new Player(nick);
            gameHandler.addPlayer(player);
            getThreadLocalRequest().getSession().setAttribute("nick", nick);
            return player;
        }
        return null;
    }

    public GameInfo createGame(String name, Sprite[][] sprites, Settings settings) {
        Player player = gameHandler.getPlayer((String) getThreadLocalRequest().getSession().getAttribute("nick"));
        if (gameHandler.getGame(name) == null) {
            Game game = new Game(name, sprites, settings);
            gameHandler.addGame(game);
            addEvent(LOBBY_DOMAIN, new GameCreateEvent(player, game.getGameInfo()));
            return game.getGameInfo();
        } else {
            return null;
        }
    }

    public Map<String, GameInfo> getGames() {
        return gameHandler.getGameInfos();
    }

    public GameInfo joinGame(String gameName) {
        Game game = gameHandler.getGame(gameName);
        Player player = gameHandler.getPlayer((String) getThreadLocalRequest().getSession().getAttribute("nick"));
        if (game != null && player != null && game.getPlayersSize() < game.getSettings().getMaxPlayers()) {
            game.addPlayer(player);
            switch (game.getPlayersSize()) {
                case 2:
                    player.setX(game.getWidth() - game.getImgSize());
                    break;
                case 3:
                    player.setY(game.getHeight() - game.getImgSize());
                    break;
                case 4:
                    player.setX(game.getWidth() - game.getImgSize());
                    player.setY(game.getHeight() - game.getImgSize());
                    break;
            }
            addEvent(LOBBY_DOMAIN, new PlayerJoinGameEvent(player, game.getGameInfo()));
            return game.getGameInfo();
        }
        return null;
    }

    public Player checkSession() {
        return gameHandler.getPlayer((String) getThreadLocalRequest().getSession().getAttribute("nick"));
    }
}
