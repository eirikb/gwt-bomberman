/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.eirikb.bomberman.client.game.item;

import com.google.gwt.resources.client.ImageResource;
import java.util.List;

/**
 *
 * @author eirikb
 */
public class Animation {

    private List<ImageResource> images;
    private int animationTime = 1;
    private int animationTimeCurrent = 0;
    private int currentImagePos;

    public Animation(List<ImageResource> images, int animationTime) {
        this.images = images;
        this.animationTime = animationTime;
    }

    public Animation() {
    }

    public void setAnimationTime(int animationTime) {
        this.animationTime = animationTime;
    }

    public ImageResource animate() {
        if (++animationTimeCurrent == animationTime) {
            currentImagePos = ++currentImagePos < images.size() ? currentImagePos : 0;
            animationTimeCurrent = 0;
        }
        return images.get(currentImagePos);
    }

    public ImageResource getImage() {
        return images.get(currentImagePos);
    }
}
