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
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class GameHandler {

    private static final GameHandler INSTANCE = new GameHandler();
    private Map<String, Player> players;
    private Map<String, Game> games;
    private Map<String, GameInfo> gameInfos;

    private GameHandler() {
        players = new HashMap<String, Player>();
        games = new HashMap<String, Game>();
        gameInfos = new HashMap<String, GameInfo>();
    }

    public Player getPlayer(String nick) {
        return players.get(nick);
    }

    public void addPlayer(Player player) {
        players.put(player.getNick(), player);
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
}
