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
import no.eirikb.bomberman.game.Bomb;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.Way;
import no.eirikb.bomberman.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
@RemoteServiceRelativePath("gameservice")
public interface GameService extends RemoteService {

    void died();

    void resurect();

    void gotPowerup(Powerup powerup);

    Game getGame(String name);

    void startWalking(Way way, double x, double y);

    void stopWalking(double x, double y);

    void addBomb(Bomb bomb);
}
