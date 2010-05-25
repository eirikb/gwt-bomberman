/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.server;

import java.util.HashMap;
import java.util.Map;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameHandler {

    private static final GameHandler INSTANCE = new GameHandler();
    private Map<String, Player> players;
    private Map<String, Game> games;
    private Map<String, GameInfo> gameInfos;
    private Map<Player, Game> playerGames;
    private Map<String, Player> playerSession;

    private GameHandler() {
        players = new HashMap<String, Player>();
        games = new HashMap<String, Game>();
        gameInfos = new HashMap<String, GameInfo>();
        playerGames = new HashMap<Player, Game>();
        playerSession = new HashMap<String, Player>();
    }

    public Player getPlayer(String sessionId) {
        return players.get(sessionId);
    }

    public void addPlayer(String sessionId, Player player) {
        players.put(player.getNick(), player);
        playerSession.put(sessionId, player);
    }

    public Game getGame(String name) {
        return games.get(name);
    }

    public void addGame(Game game) {
        games.put(game.getGameInfo().getName(), game);
        gameInfos.put(game.getGameInfo().getName(), game.getGameInfo());
    }

    public Map<String, GameInfo> getGameInfos() {
        return gameInfos;
    }

    public static GameHandler getInstance() {
        return INSTANCE;
    }

    public void linkPlayerToGame(Player player, Game game) {
        playerGames.put(player, game);
    }

    public Game getGameByPlayer(Player player) {
        return playerGames.get(player);
    }

    public Player getPlayerBySessionId(String sessionId) {
        return playerSession.get(sessionId);
    }

    public void removePlayer(Player player) {
        players.remove(player.getNick());
        playerSession.remove(player.getNick());
        Game game = playerGames.get(player);
        if (game != null) {
            game.removePlayer(player);
        }
    }

    public Game removePlayerFromGame(Player player) {
        Game game = playerGames.remove(player);
        if (game != null) {
            game.removePlayer(player);
        }
        gameInfos.put(game.getGameInfo().getName(), game.getGameInfo());
        return game;
    }

    public void removeGame(Game game) {
        gameInfos.remove(game.getGameInfo().getName());
        for (Player player : game.getAlivePlayers()) {
            playerGames.remove(player);
        }
        for (Player player : game.getDeadPlayers()) {
            playerGames.remove(player);
        }
        games.remove(game.getGameInfo().getName());
    }
}
