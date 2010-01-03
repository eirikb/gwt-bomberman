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
import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.BoomBrick;
import no.eirikb.bomberman.client.game.Brick;
import no.eirikb.bomberman.client.game.CoreExplosion;
import no.eirikb.bomberman.client.game.Explosion;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameListener;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.logic.BoomBrickBuilder;
import no.eirikb.bomberman.client.game.logic.ExplosionBuilder;
import no.eirikb.bomberman.client.game.poweup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombHandler extends Handler {

    private final String BOMBURLPART = "img/bomb";

    public BombHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
        final BombHandler bombHandler = this;
        game.addGameListener(new GameListener() {

            public void addPlayer(Player player) {
            }

            public void addBomb(Bomb bomb) {
                bombHandler.addBomb(bomb);
            }

            public void addExplosion(Explosion explosion) {
            }

            public void addBoomBrick(BoomBrick boomBrick) {
            }

            public void removePlayer(Player player) {
            }

            public void removeBomb(Bomb bomb) {
            }

            public void addPowerup(Powerup powerup) {
            }

            public void removePowerup(Powerup powerup) {
            }
        });
    }

    public void addBomb(Bomb bomb) {
        gamePanel.add(bomb.getImage(), bomb.getSpriteX() * game.getImgSize(), bomb.getSpriteY() * game.getImgSize());
    }

    public void handle() {
        List<Bomb> toRemove = new ArrayList<Bomb>();
        for (Bomb bomb : game.getBombs()) {
            if (bomb.explode()) {
                toRemove.add(bomb);
                gamePanel.remove(bomb.getImage());
                CoreExplosion coreExplosion = ExplosionBuilder.createExplosions(game, bomb);
                game.addExplosion(coreExplosion);
                for (Explosion explosion : coreExplosion.getExplosions()) {
                    game.addExplosion(explosion);
                }
                for (int[] hit : coreExplosion.getHits()) {
                    createBoomBrick(hit[0], hit[1]);
                }
                bomb.getOwner().setBombAbount(bomb.getOwner().getBombAbount() + 1);
            } else {
                int animation = bomb.animate();
                if (animation >= 0) {
                    bomb.getImage().setUrl(BOMBURLPART + animation + ".png");
                }
            }
        }
        for (Bomb bomb : toRemove) {
            game.removeBomb(bomb);
        }
    }

    private void createBoomBrick(int spriteX, int spriteY) {
        Sprite[][] sprites = game.getSprites();
        if (spriteX >= 0 && spriteX < sprites.length && spriteY >= 0 && spriteY < sprites[0].length) {
            Sprite sprite = sprites[spriteX][spriteY];
            if (sprite instanceof Brick) {
                gamePanel.remove(sprite.getImage());
                BoomBrick boomBrick = BoomBrickBuilder.createBoomBrick(sprite);
                game.addBoomBrick(boomBrick);
            } else if (sprite instanceof Bomb) {
                Bomb bomb = (Bomb) sprite;
                bomb.forceExplode();
            }
        }
    }
}
