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
import no.eirikb.bomberman.shared.event.game.GameEvent;
import no.eirikb.bomberman.shared.event.game.PlayerDieEvent;
import no.eirikb.bomberman.shared.event.game.PlayerGotPowerupEvent;
import no.eirikb.bomberman.shared.event.game.PlayerPlaceBombEvent;
import no.eirikb.bomberman.shared.event.game.PlayerResurectEvent;
import no.eirikb.bomberman.shared.event.game.PlayerStartWalkingEvent;
import no.eirikb.bomberman.shared.event.game.PlayerStopWalkingEvent;
import no.eirikb.bomberman.game.Bomb;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Way;
import no.eirikb.bomberman.game.powerup.Powerup;
import no.eirikb.bomberman.client.GameService;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameServer extends RemoteEventServiceServlet implements GameService {

    public static final Domain GAME_DOMAIN = DomainFactory.getDomain(GameEvent.GAME_DOMAIN);
    private GameHandler gameHandler;

    public GameServer() {
        gameHandler = GameHandler.getInstance();
    }

    @Override
    public Game getGame(String name) {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        Game game = gameHandler.getGame(name);
        gameHandler.linkPlayerToGame(player, game);
        if (player != null && game != null) {
            for (Player p : game.getAlivePlayers()) {
                if (p.getNick().equals(player.getNick())) {
                    return game;
                }
            }
        }
        return null;
    }

    @Override
    public void startWalking(Way way, double x, double y) {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        player.setX(x);
        player.setY(y);
        Game game = gameHandler.getGameByPlayer(player);
        addEvent(GAME_DOMAIN, new PlayerStartWalkingEvent(game.getGameInfo().getName(), player.getNick(), way));
    }

    @Override
    public void stopWalking(double x, double y) {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        player.setX(x);
        player.setY(y);
        Game game = gameHandler.getGameByPlayer(player);
        addEvent(GAME_DOMAIN, new PlayerStopWalkingEvent(game.getGameInfo().getName(), player.getNick(), x, y));
    }

    @Override
    public void addBomb(Bomb bomb) {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        Game game = gameHandler.getGameByPlayer(player);
        addEvent(GAME_DOMAIN, new PlayerPlaceBombEvent(game.getGameInfo().getName(), player.getNick(), bomb));
    }

    @Override
    public void died() {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        Game game = gameHandler.getGameByPlayer(player);
        game.playerDie(player);
        addEvent(GAME_DOMAIN, new PlayerDieEvent(game.getGameInfo().getName(), player.getNick()));
    }

    @Override
    public void gotPowerup(Powerup powerup) {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        Game game = gameHandler.getGameByPlayer(player);
        powerup.powerUp(player);
        addEvent(GAME_DOMAIN, new PlayerGotPowerupEvent(game.getGameInfo().getName(), player.getNick(), powerup));
    }

    @Override
    public void resurect() {
        Player player = gameHandler.getPlayerBySessionId(getThreadLocalRequest().getSession().getId());
        Game game = gameHandler.getGameByPlayer(player);
        game.playerLive(player);
        addEvent(GAME_DOMAIN, new PlayerResurectEvent(game.getGameInfo().getName(), player.getNick()));
    }
}
