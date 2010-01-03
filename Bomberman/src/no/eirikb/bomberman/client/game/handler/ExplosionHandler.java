/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.handler;

import java.util.ArrayList;
import java.util.List;
import no.eirikb.bomberman.client.GamePanel;
import no.eirikb.bomberman.client.game.Explosion;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameListener;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class ExplosionHandler extends Handler implements GameListener {

    public ExplosionHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
        game.addGameListener(this);
    }

    private void addExplosion(Explosion explosion) {
        explosion.getImage().setUrl(Explosion.getImageURLPart(explosion) + "1.png");
        gamePanel.add(explosion.getImage(), explosion.getSpriteX() * game.getImgSize(), explosion.getSpriteY() * game.getImgSize());
    }

    public void handle() {
        List<Explosion> toRemove = new ArrayList<Explosion>();
        for (Explosion explosion : game.getExplosions()) {
            if (explosion.remove()) {
                toRemove.add(explosion);
                gamePanel.remove(explosion.getImage());
            } else {
                int animation = explosion.animate();
                if (animation > 0) {
                    explosion.getImage().setUrl(Explosion.getImageURLPart(explosion) + animation + ".png");
                }
            }
        }
        for (Explosion explosion : toRemove) {
            game.removeExplosion(explosion);
        }
    }

    public void addSprite(Sprite sprite) {
        if (sprite instanceof Explosion) {
            addExplosion((Explosion) sprite);
        }
    }

    public void removeSprite(Sprite sprite) {
    }
}
