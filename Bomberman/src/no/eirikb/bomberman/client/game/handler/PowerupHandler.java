/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.handler;

import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameListener;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
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
            gamePanel.add(sprite.getImage(), sprite.getSpriteX() * game.getImgSize(), sprite.getSpriteY() * game.getImgSize());
        }
    }

    public void removeSprite(Sprite sprite) {
        if (sprite instanceof Powerup) {
            gamePanel.remove(sprite.getImage());
        }
    }

    public void bump(Player player, Sprite sprite) {
        if (sprite instanceof Powerup) {
            Powerup powerup = (Powerup) sprite;
            powerup.powerUp(player);
            game.removePowerup(powerup);
        }
    }
}
