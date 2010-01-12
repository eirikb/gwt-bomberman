/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.game;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.GameListener;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.poweup.BombAmountPowerup;
import no.eirikb.bomberman.client.game.poweup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class BombAmountPanel extends HorizontalPanel implements GameListener {

    private final String BOMBURL = "img/bomb1.png";
    private Player player;

    public BombAmountPanel(Player player) {
        this.player = player;
        add(new Label("Amount of bombs: "));
        for (int i = 0; i < player.getBombAbount(); i++) {
            add(new Image(BOMBURL));
        }
    }

    public void addBomb(Bomb bomb) {
        if (bomb.getOwner() == player) {
            remove(1);
        }
    }

    public void removeBomb(Bomb bomb) {
        if (bomb.getOwner() == player) {
            add(new Image(BOMBURL));
        }
    }

    public void addSprite(Sprite sprite) {
        if (sprite instanceof Bomb) {
            addBomb((Bomb) sprite);
        }
    }

    public void removeSprite(Sprite sprite) {
        if (sprite instanceof Bomb) {
            removeBomb((Bomb) sprite);
        } else if (sprite instanceof BombAmountPowerup) {
            if (((Powerup) sprite).getPlayer() == player) {
                add(new Image(BOMBURL));
            }
        }
    }
}
