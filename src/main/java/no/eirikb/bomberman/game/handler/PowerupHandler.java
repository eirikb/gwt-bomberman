/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.GameListener;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.game.powerup.Powerup;
import no.eirikb.bomberman.client.GameService;
import no.eirikb.bomberman.client.GameServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class PowerupHandler extends Handler implements GameListener {

    public PowerupHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
    }

    @Override
    protected void handle() {
    }

    public void addSprite(Sprite sprite) {
        if (sprite instanceof Powerup) {
            sprite.initImage();
            gamePanel.add(sprite.getImage(), sprite.getSpriteX() * game.getImgSize(), sprite.getSpriteY() * game.getImgSize());
        }
    }

    public void removeSprite(Sprite sprite) {
        if (sprite instanceof Powerup) {
            gamePanel.remove(sprite.getImage());
        }
    }

    public void bump(Player player, Sprite sprite) {
        if (player == gamePanel.getPlayer()) {
            if (sprite instanceof Powerup) {
                Powerup powerup = (Powerup) sprite;
                powerup.powerUp(player);
                game.removePowerup(powerup);
                GameServiceAsync gameService = GWT.create(GameService.class);
                gameService.gotPowerup(powerup, new AsyncCallback() {

                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(Object result) {
                    }
                });
            }
        }
    }

    public void playerDie(Player player) {
    }

    public void playerLive(Player player) {
    }
}
