/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.item;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class Brick extends BombermanItem {

    public Brick(double x, double y, int width, int height) {
        super(x, y, width, height, 0, 1);
    }
}
