/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.Map;
import no.eirikb.bomberman.client.game.GameInfo;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface BombermanServiceAsync {

    void join(String nick, AsyncCallback<Player> callback);

    void createGame(String name, Sprite[][] sprites, Settings settings, AsyncCallback<GameInfo> callback);

    void joinGame(String gameName, AsyncCallback<GameInfo> callback);

    void getGames(AsyncCallback<Map<String, GameInfo>> callback);
}
