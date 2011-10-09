/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.eirikb.bomberman.client.game.item;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.game.item.Brick;

/**
 *
 * @author eirikb
 */
public class GameBrick extends Brick implements GameItem {

    private Image image;

    public GameBrick(double x, double y, Image image) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    @Override
    public Animation getaAnimation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void animate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
