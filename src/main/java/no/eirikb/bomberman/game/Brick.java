/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.game;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtæg <eirikb@eirikb.no>
 */
public class Brick extends Sprite {

    private transient final String INITURL = "img/brick.png";
    private Powerup powerup;

    public Brick() {
    }

    public Brick(int spriteX, int spriteY) {
        super(spriteX, spriteY);
    }

    public Powerup getPowerup() {
        return powerup;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;
    }

    @Override
    public void initImage() {
        setImage(new Image(INITURL));
    }
}
