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
import no.eirikb.bomberman.client.game.Explosion;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.GameListener;
import no.eirikb.bomberman.client.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BoomBrickHandler extends Handler {

    private final String BOOMBRICKURL = "img/boombrick";

    public BoomBrickHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
        final BoomBrickHandler boomBrickHandler = this;
        game.addGameListener(new GameListener() {

            public void addPlayer(Player player) {
            }

            public void addBomb(Bomb bomb) {
            }

            public void addExplosion(Explosion explosion) {
            }

            public void addBoomBrick(BoomBrick boomBrick) {
                boomBrickHandler.addBoomBrick(boomBrick);
            }

            public void removePlayer(Player player) {
            }

            public void removeBomb(Bomb bomb) {
            }
        });
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
        }
    }
}
