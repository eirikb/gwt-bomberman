/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class Animation {

    private int animation;
    private int animationSize;
    private int animationTime;

    public Animation(int animationSize, int animationTime) {
        this.animationSize = animationSize;
        this.animationTime = animationTime;
        animation = -1;
    }

    public int getAnimationSize() {
        return animationSize;
    }

    public int getAnimationTime() {
        return animationTime;
    }

    public int animate() {
        animation++;
        if (animation >= animationSize * animationTime) {
            animation = 0;
        }
        if (animation % animationTime == 0) {
            return (animation / animationTime) + 1;
        }
        return -1;
    }
}
