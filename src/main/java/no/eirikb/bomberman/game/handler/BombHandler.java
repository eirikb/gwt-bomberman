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
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.game.Bomb;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.Sprite;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class BombHandler extends Handler {

    private final String BOMBURLPART = "img/bomb";

    public BombHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
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

    public void addSprite(Sprite sprite) {
        if (sprite instanceof Bomb) {
            addBomb((Bomb) sprite);
        }
    }

    public void removeSprite(Sprite sprite) {
    }

    public void bump(Player player, Sprite sprite) {
    }

    public void playerDie(Player player) {
    }

    public void playerLive(Player player) {
    }
}
