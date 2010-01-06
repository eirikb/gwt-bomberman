/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client;

import com.google.gwt.user.client.ui.AbsolutePanel;
import no.eirikb.bomberman.client.game.Sprite;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class GamePanel extends AbsolutePanel {

    private int imgSize;

    public GamePanel(int imgSize, int width, int height) {
        this.imgSize = imgSize;
        setSize(width + "px", height + "px");
    }

    public void drawSprites(Sprite[][] sprites) {
        for (int x = 0; x < sprites.length; x++) {
            for (int y = 0; y < sprites[0].length; y++) {
                Sprite sprite = sprites[x][y];
                if (sprite != null && sprite.getImage() != null) {
                    add(sprite.getImage(), x * imgSize, y * imgSize);
                }
            }
        }
    }
}
