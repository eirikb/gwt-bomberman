/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.handler;

import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import no.eirikb.bomberman.client.GamePanel;
import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.CoreExplosion;
import no.eirikb.bomberman.client.game.Explosion;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.builder.ExplosionBuilder;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class ExplosionHandler extends Handler {

    public ExplosionHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
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
            GWT.log("ExplosionHandler: instance of Explosion", null);
            addExplosion((Explosion) sprite);
        } else if (sprite instanceof CoreExplosion) {
            GWT.log("ExplosionHandler: instane of CoreExplosion", null);
            CoreExplosion coreExplosion = (CoreExplosion) sprite;
            for (Player player : game.getPlayers()) {
                int pSpriteX = player.getSpriteX();
                int pSpriteY = player.getSpriteY();
                int cSpriteX = coreExplosion.getSpriteX();
                int cSpriteY = coreExplosion.getSpriteY();
                if (pSpriteX == cSpriteX && pSpriteY == cSpriteY) {
                    game.removePlayer(player);
                } else {
                    if (Math.max(pSpriteX, cSpriteX) - Math.min(pSpriteX, cSpriteX) <= coreExplosion.getSize()
                            && Math.max(pSpriteY, cSpriteY) - Math.min(pSpriteY, cSpriteY) <= coreExplosion.getSize()) {
                        for (Explosion explosion : coreExplosion.getExplosions()) {
                            int ex = explosion.getSpriteX() * game.getImgSize();
                            int ey = explosion.getSpriteY() * game.getImgSize();
                            int px = player.getX();
                            int py = player.getY();
                            double w = Math.min(ex, px) + game.getImgSize() - Math.max(ex, px);
                            double h = Math.min(ey, py) + game.getImgSize() - Math.max(ey, py);
                            if (w >= 0 && h >= 0) {
                                double percentage = ((w * h) / (game.getImgSize() * game.getImgSize())) * 100;
                                if (percentage >= Settings.getInstance().getExplosionHitPercentage()) {
                                    game.removePlayer(player);
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public void removeSprite(Sprite sprite) {
        if (sprite instanceof Bomb) {
            CoreExplosion coreExplosion = ExplosionBuilder.createExplosions(game.getSprites(), (Bomb) sprite);
            game.addExplosion(coreExplosion);
            for (Explosion explosion : coreExplosion.getExplosions()) {
                game.addExplosion(explosion);
            }
        }
    }
}
