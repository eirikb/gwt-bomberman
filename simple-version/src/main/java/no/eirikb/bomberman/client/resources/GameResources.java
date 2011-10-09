/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public interface GameResources extends ClientBundle {

    public static final GameResources INSTANCE = GWT.create(GameResources.class);

    @Source("brick.png")
    public ImageResource brick();

    @Source("pd1.png")
    public ImageResource playerDown1();

    @Source("pd2.png")
    public ImageResource playerDown2();

    @Source("pd3.png")
    public ImageResource playerDown3();

    @Source("pl1.png")
    public ImageResource playerLeft1();

    @Source("pl2.png")
    public ImageResource playerLeft2();

    @Source("pl3.png")
    public ImageResource playerLeft3();

    @Source("pr1.png")
    public ImageResource playerRight1();

    @Source("pr2.png")
    public ImageResource playerRight2();

    @Source("pr3.png")
    public ImageResource playerRight3();

    @Source("pu1.png")
    public ImageResource playerUp1();

    @Source("pu2.png")
    public ImageResource playerUp2();

    @Source("pu3.png")
    public ImageResource playerUp3();
}
