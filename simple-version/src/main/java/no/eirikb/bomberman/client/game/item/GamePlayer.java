/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.item;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import java.util.ArrayList;
import java.util.Arrays;
import no.eirikb.bomberman.client.resources.GameResources;
import no.eirikb.bomberman.game.item.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GamePlayer extends Player implements GameItem {

    private Image image;
    private Animation walkLeftAnimation = new Animation(
            new ArrayList<ImageResource>(Arrays.asList(new ImageResource[]{
                GameResources.INSTANCE.playerLeft1(),
                GameResources.INSTANCE.playerLeft2(),
                GameResources.INSTANCE.playerLeft1(),
                GameResources.INSTANCE.playerLeft3()
            })), 3);
    private Animation walkUpAnimation = new Animation(
            new ArrayList<ImageResource>(Arrays.asList(new ImageResource[]{
                GameResources.INSTANCE.playerUp1(),
                GameResources.INSTANCE.playerUp2(),
                GameResources.INSTANCE.playerUp1(),
                GameResources.INSTANCE.playerUp3()
            })), 3);
    private Animation walkRightAnimation = new Animation(
            new ArrayList<ImageResource>(Arrays.asList(new ImageResource[]{
                GameResources.INSTANCE.playerRight1(),
                GameResources.INSTANCE.playerRight2(),
                GameResources.INSTANCE.playerRight1(),
                GameResources.INSTANCE.playerRight3()
            })), 3);
    private Animation walkDownAnimation = new Animation(
            new ArrayList<ImageResource>(Arrays.asList(new ImageResource[]{
                GameResources.INSTANCE.playerDown1(),
                GameResources.INSTANCE.playerDown2(),
                GameResources.INSTANCE.playerDown1(),
                GameResources.INSTANCE.playerDown3()
            })), 3);
    private Animation animation = walkRightAnimation;
    private boolean animate = false;

    public GamePlayer(double x, double y, Image image) {
        super(x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    @Override
    public void left() {
        super.left();
        animation = walkLeftAnimation;
    }

    @Override
    public void up() {
        super.up();
        animation = walkUpAnimation;
    }

    @Override
    public void right() {
        super.right();
        animation = walkRightAnimation;
    }

    @Override
    public void down() {
        super.down();
        animation = walkDownAnimation;

    }

    @Override
    public void stop() {
        super.stop();
        animate = false;
    }

    @Override
    protected void start(double cos, double sin) {
        super.start(cos, sin);
        animate = true;
    }

    @Override
    public Animation getaAnimation() {
        return animation;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void animate() {
        if (animate) {
            image.setResource(animation.animate());
        }
    }
}
