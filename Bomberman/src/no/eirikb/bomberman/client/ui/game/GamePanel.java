/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.ui.game;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class GamePanel extends AbsolutePanel {

    private int imgSize;
    private Player player;

    public GamePanel(Player player, int imgSize, int width, int height) {
        this.player = player;
        this.imgSize = imgSize;
        setSize(width + "px", height + "px");
    }

    public Player getPlayer() {
        return player;
    }

    public void drawSprites(Sprite[][] sprites) {
        for (int x = 0; x < sprites.length; x++) {
            for (int y = 0; y < sprites[0].length; y++) {
                Sprite sprite = sprites[x][y];
                if (sprite != null) {
                    if (sprite.getImage() == null) {
                        sprite.initImage();
                    }
                    if (sprite.getImage() != null) {
                        add(sprite.getImage(), x * imgSize, y * imgSize);
                    } else {
                        GWT.log("OMM GG no IMAGE", null);
                    }
                }
            }
        }
    }
}
