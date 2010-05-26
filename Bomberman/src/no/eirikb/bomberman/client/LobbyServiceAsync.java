/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Map;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface LobbyServiceAsync {

    void getImages(AsyncCallback<String[]> callback);

    void checkSession(AsyncCallback<Player> callback);

    void checkGame(AsyncCallback<GameInfo> asyncCallback);

    void login(String nick, AsyncCallback<Player> callback);

    void createGame(String name, Sprite[][] sprites, Settings settings, AsyncCallback<GameInfo> callback);

    void joinGame(String gameName, AsyncCallback<GameInfo> callback);

    void getGames(AsyncCallback<Map<String, GameInfo>> callback);

    void quitGame(AsyncCallback asyncCallback);
}
