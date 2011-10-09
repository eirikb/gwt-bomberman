/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.Map;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
@RemoteServiceRelativePath("lobbyservice")
public interface LobbyService extends RemoteService {

    String[] getImages();

    Player checkSession();

    GameInfo checkGame();

    Player login(String nick);

    GameInfo createGame(String name, Sprite[][] sprites, Settings settings);

    GameInfo joinGame(String gameName);

    Map<String, GameInfo> getGames();

    void quitGame();
}
