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
import de.novanic.eventservice.service.RemoteEventServiceServlet;
import de.novanic.eventservice.service.UserTimeoutListener;
import de.novanic.eventservice.service.registry.user.UserActivityScheduler;
import de.novanic.eventservice.service.registry.user.UserInfo;
import de.novanic.eventservice.service.registry.user.UserManager;
import de.novanic.eventservice.service.registry.user.UserManagerFactory;
import java.io.File;
import java.util.Date;
import java.util.Map;
import no.eirikb.bomberman.shared.event.game.GameEvent;
import no.eirikb.bomberman.shared.event.lobby.GameCreateEvent;
import no.eirikb.bomberman.shared.event.shared.PlayerJoinGameEvent;
import no.eirikb.bomberman.shared.event.lobby.LobbyEvent;
import no.eirikb.bomberman.shared.event.lobby.PlayerQuitEvent;
import no.eirikb.bomberman.shared.event.shared.PlayerQuitGameEvent;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.client.LobbyService;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class LobbyServer extends RemoteEventServiceServlet implements LobbyService {

    private static final Domain LOBBY_DOMAIN = LobbyEvent.LOBBY_DOMAIN;
    private static final Domain GAME_EVENT = GameEvent.GAME_DOMAIN; // Shared
    private GameHandler gameHandler;

    public LobbyServer() {
        gameHandler = GameHandler.getInstance();
        UserManager userManager = UserManagerFactory.getInstance().getUserManager(40000);
        UserActivityScheduler userActivityScheduler = userManager.getUserActivityScheduler();
        userActivityScheduler.addTimeoutListener(new UserTimeoutListener() {

            @Override
            public void onTimeout(UserInfo ui) {
                Player player = gameHandler.getPlayerBySessionId(ui.getUserId());
                addEvent(LOBBY_DOMAIN, new PlayerQuitEvent(gameHandler.getPlayerBySessionId(ui.getUserId())));
                Game game = gameHandler.getGameByPlayer(player);
                if (game != null) {
                    addEvent(GameServer.GAME_DOMAIN, new PlayerQuitGameEvent(player, game.getGameInfo()));
                }
                System.out.println("TIMEOUT! " + new Date() + " - " + player.getNick() + " (" + game + ')');
                gameHandler.removePlayer(player);
                if (game.getPlayerSize() == 0) {
                    gameHandler.removeGame(game);
                }
            }
        });
    }

    @Override
    public Player login(String nick) {
        if (gameHandler.getPlayer(nick) == null) {
            Player player = new Player(nick);
            gameHandler.addPlayer(getThreadLocalRequest().getSession().getId(), player);
            return player;
        }
        return null;
    }

    @Override
    public GameInfo createGame(String name, Sprite[][] sprites, Settings settings) {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        if (gameHandler.getGame(name) == null) {
            Game game = new Game(name, sprites, settings);
            gameHandler.addGame(game);
            addEvent(LOBBY_DOMAIN, new GameCreateEvent(player, game.getGameInfo()));
            return game.getGameInfo();
        } else {
            return null;
        }
    }

    @Override
    public Map<String, GameInfo> getGames() {
        return gameHandler.getGameInfos();
    }

    @Override
    public GameInfo joinGame(String gameName) {
        Game game = gameHandler.getGame(gameName);
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        if (game != null && player != null && game.getAlivePlayersSize() < game.getSettings().getMaxPlayers()) {
            game.addPlayer(player);
            switch (game.getAlivePlayersSize()) {
                case 2:
                    player.setStartX(game.getWidth() - game.getImgSize());
                    break;
                case 3:
                    player.setStartY(game.getHeight() - game.getImgSize());
                    break;
                case 4:
                    player.setStartX(game.getWidth() - game.getImgSize());
                    player.setStartY(game.getHeight() - game.getImgSize());
                    break;
            }
            addEvent(LOBBY_DOMAIN, new PlayerJoinGameEvent(player, game.getGameInfo()));
            gameHandler.linkPlayerToGame(player, game);
            return game.getGameInfo();
        }
        return null;
    }

    @Override
    public Player checkSession() {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
//        if (gameHandler.getGameByPlayer(player) != null) {
//            Game game = gameHandler.removePlayerFromGame(player);
//            if (game.getPlayerSize() == 0) {
//                gameHandler.removeGame(game);
//            }
//            addEvent(LOBBY_DOMAIN, new PlayerQuitGameEvent(player, game.getGameInfo()));
//            addEvent(GAME_EVENT, new PlayerQuitGameEvent(player, game.getGameInfo()));
//        }
        return player;
    }

    @Override
    public GameInfo checkGame() {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        Game game = gameHandler.getGameByPlayer(player);
        if (game != null) {
            return game.getGameInfo();
        }
        return null;
    }

    @Override
    public String[] getImages() {
        File imageDir = new File("war/img/");
        if (imageDir != null && imageDir.isDirectory()) {
            return imageDir.list();
        }
        return null;
    }

    @Override
    public void quitGame() {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        Game game = gameHandler.removePlayerFromGame(player);
        if (game.getPlayerSize() == 0) {
            gameHandler.removeGame(game);
        }
        addEvent(LOBBY_DOMAIN, new PlayerQuitGameEvent(player, game.getGameInfo()));
        addEvent(GAME_EVENT, new PlayerQuitGameEvent(player, game.getGameInfo()));
    }
}
