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
import no.eirikb.bomberman.game.Bomb;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.Way;
import no.eirikb.bomberman.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface GameServiceAsync {

    public void died(AsyncCallback callback);

    public void resurect(AsyncCallback callback);

    public void gotPowerup(Powerup powerup, AsyncCallback callback);

    public void getGame(String name, AsyncCallback<Game> callback);

    public void startWalking(Way way, double x, double y, AsyncCallback callback);

    public void stopWalking(double x, double y, AsyncCallback callback);

    void addBomb(Bomb bomb, AsyncCallback callback);
}
