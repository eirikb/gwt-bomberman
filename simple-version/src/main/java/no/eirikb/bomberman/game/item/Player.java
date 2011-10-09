/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.item;

import no.eirikb.sge.item.Direction;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class Player extends BombermanItem {

    public Player(double x, double y, int width, int height) {
        super(x, y, width, height, 3, 1);
    }

    public void left() {
        start(-1, 0);
    }

    public void up() {
        start(0, -1);
    }

    public void right() {
        start(1, 0);
    }

    public void down() {
        start(0, 1);
    }

    protected void start(double cos, double sin) {
        setDirection(new Direction(cos, sin));
        setSpeed(defaultSpeed);
    }

    public void stop() {
        setSpeed(0);
    }
}
