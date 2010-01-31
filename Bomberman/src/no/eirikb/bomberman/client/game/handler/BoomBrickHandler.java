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
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.BoomBrick;
import no.eirikb.bomberman.client.game.Brick;
import no.eirikb.bomberman.client.game.CoreExplosion;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.builder.BoomBrickBuilder;
import no.eirikb.bomberman.client.game.builder.PowerupBuilder;
import no.eirikb.bomberman.client.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BoomBrickHandler extends Handler {

    private final String BOOMBRICKURL = "img/boombrick";

    public BoomBrickHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
    }

    public void addBoomBrick(BoomBrick boomBrick) {
        gamePanel.add(boomBrick.getImage(), boomBrick.getSpriteX() * game.getImgSize(), boomBrick.getSpriteY() * game.getImgSize());
    }

    @Override
    protected void handle() {
        List<BoomBrick> toRemove = new ArrayList<BoomBrick>();
        for (BoomBrick boomBrick : game.getBoomBricks()) {
            if (boomBrick.remove()) {
                toRemove.add(boomBrick);
                gamePanel.remove(boomBrick.getImage());
            } else {
                int animation = boomBrick.animate();
                if (animation > 0) {
                    boomBrick.getImage().setUrl(BOOMBRICKURL + animation + ".png");
                }
            }
        }
        for (BoomBrick boomBrick : toRemove) {
            game.removeBoomBrick(boomBrick);
            Powerup powerup = boomBrick.getPowerup();
            if (powerup != null) {
                game.addPowerup(powerup);
            }
        }
    }

    public void addSprite(Sprite sprite) {
        if (sprite instanceof BoomBrick) {
            addBoomBrick((BoomBrick) sprite);
        } else if (sprite instanceof CoreExplosion) {
            CoreExplosion coreExplosion = (CoreExplosion) sprite;
            for (int[] hit : coreExplosion.getHits()) {
                createBoomBrick(hit[0], hit[1]);
            }
        }
    }

    public void removeSprite(Sprite sprite) {
    }

    private void createBoomBrick(int spriteX, int spriteY) {
        Sprite[][] sprites = game.getSprites();
        if (spriteX >= 0 && spriteX < sprites.length && spriteY >= 0 && spriteY < sprites[0].length) {
            Sprite sprite = sprites[spriteX][spriteY];
            if (sprite instanceof Brick) {
                gamePanel.remove(sprite.getImage());
                BoomBrick boomBrick = BoomBrickBuilder.createBoomBrick((Brick) sprite);
                game.addBoomBrick(boomBrick);
            } else if (sprite instanceof Bomb) {
                Bomb bomb = (Bomb) sprite;
                bomb.forceExplode();
            }
        }
    }

    public void bump(Player player, Sprite sprite) {
    }
}
