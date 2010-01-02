/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.logic;

import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.CoreExplosion;
import no.eirikb.bomberman.client.game.Explosion;
import no.eirikb.bomberman.client.game.ExplosionType;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class ExplosionBuilder {

    public static CoreExplosion createExplosions(Game game, Bomb bomb) {
        CoreExplosion coreExplosion = new CoreExplosion(Explosion.getImageURL(ExplosionType.CORE), bomb.getSpriteX(), bomb.getSpriteY(), ExplosionType.CORE);
        branch(coreExplosion, game.getSprites(), bomb, 1, true);
        branch(coreExplosion, game.getSprites(), bomb, -1, true);
        branch(coreExplosion, game.getSprites(), bomb, 1, false);
        branch(coreExplosion, game.getSprites(), bomb, -1, false);
        for (Player player : game.getPlayers()) {
            int px = player.getSpriteX();
            int py = player.getSpriteY();
            int cx = coreExplosion.getSpriteX();
            int cy = coreExplosion.getSpriteY();
            if (px == cx && py == cy) {
                game.removePlayer(player);
            } else {
                if (Math.max(px, cx) - Math.min(px, cx) <= bomb.getPower()
                        && Math.max(py, cy) - Math.min(py, cy) <= bomb.getPower()) {
                    for (Explosion explosion : coreExplosion.getExplosions()) {
                        if (explosion.getSpriteX() == player.getSpriteX() && explosion.getSpriteY() == player.getSpriteY()) {
                            game.removePlayer(player);
                        }
                    }
                }
            }
        }
        return coreExplosion;
    }

    private static void branch(CoreExplosion coreExplosion, Sprite[][] sprites, Bomb bomb, int inc, boolean horizontal) {
        int start = horizontal ? bomb.getSpriteX() : bomb.getSpriteY();
        int distance = bomb.getPower() * inc;
        int end = horizontal ? bomb.getSpriteX() + distance : bomb.getSpriteY() + distance;
        while (start != end) {
            start += inc;
            Explosion explosion = createSide(sprites, bomb, start, horizontal);
            if (explosion != null) {
                if (start == end) {
                    ExplosionType endExplosionType = null;
                    if (horizontal) {
                        endExplosionType = end > bomb.getSpriteX() ? ExplosionType.RIGHTEND : ExplosionType.LEFTEND;
                    } else {
                        endExplosionType = end > bomb.getSpriteY() ? ExplosionType.DOWNEND : ExplosionType.UPEND;
                    }
                    coreExplosion.addExplosion(new Explosion(Explosion.getImageURL(endExplosionType), explosion.getSpriteX(), explosion.getSpriteY(), endExplosionType));
                } else {
                    coreExplosion.addExplosion(explosion);
                }
            } else {
                int x = horizontal ? start : bomb.getSpriteX();
                int y = horizontal ? bomb.getSpriteY() : start;
                coreExplosion.addHit(x, y);
                break;
            }
        }
    }

    private static Explosion createSide(Sprite[][] sprites, Bomb bomb, int pos, boolean horizontal) {
        int x = horizontal ? pos : bomb.getSpriteX();
        int y = horizontal ? bomb.getSpriteY() : pos;
        if (x >= 0 && y >= 0 && x < sprites.length - 1 && y < sprites[0].length - 1 && sprites[x][y] == null) {
            ExplosionType explosionType = horizontal ? ExplosionType.HORIZONTAL : ExplosionType.VERTICAL;
            return new Explosion(Explosion.getImageURL(explosionType), x, y, explosionType);
        } else {
            return null;
        }
    }
}
