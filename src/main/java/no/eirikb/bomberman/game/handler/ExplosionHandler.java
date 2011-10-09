/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.handler;

import java.util.ArrayList;
import java.util.List;
import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.game.Bomb;
import no.eirikb.bomberman.game.CoreExplosion;
import no.eirikb.bomberman.game.Explosion;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.game.builder.ExplosionBuilder;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
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
            addExplosion((Explosion) sprite);
            if (sprite instanceof CoreExplosion) {
                CoreExplosion coreExplosion = (CoreExplosion) sprite;
                int imgSize = Settings.getInstance().getImgSize();
                List<Player> toDie = new ArrayList<Player>();
                for (Player player : game.getAlivePlayers()) {
                    int pSpriteX = (int) (player.getX() / imgSize);
                    int pSpriteY = (int) (player.getY() / imgSize);
                    int cSpriteX = coreExplosion.getSpriteX();
                    int cSpriteY = coreExplosion.getSpriteY();
                    if (pSpriteX == cSpriteX && pSpriteY == cSpriteY) {
                        toDie.add(player);
                    } else {
                        if (Math.max(pSpriteX, cSpriteX) - Math.min(pSpriteX, cSpriteX) <= coreExplosion.getSize()
                                && Math.max(pSpriteY, cSpriteY) - Math.min(pSpriteY, cSpriteY) <= coreExplosion.getSize()) {
                            for (Explosion explosion : coreExplosion.getExplosions()) {
                                int ex = explosion.getSpriteX() * game.getImgSize();
                                int ey = explosion.getSpriteY() * game.getImgSize();
                                double px = player.getX();
                                double py = player.getY();
                                double w = Math.min(ex, px) + game.getImgSize() - Math.max(ex, px);
                                double h = Math.min(ey, py) + game.getImgSize() - Math.max(ey, py);
                                if (w >= 0 && h >= 0) {
                                    double percentage = ((w * h) / (game.getImgSize() * game.getImgSize())) * 100;
                                    if (percentage >= Settings.getInstance().getExplosionHitPercentage()) {
                                        toDie.add(player);
                                    }
                                }
                            }
                        }
                    }
                }
                for (Player player : toDie) {
                    game.playerDie(player);
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

    public void bump(Player player, Sprite sprite) {
    }

    public void playerDie(Player player) {
    }

    public void playerLive(Player player) {
    }
}
