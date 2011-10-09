/*
 * "THE BEER-WARE LICENSE" (Revision 42):
 * =============================================================================
 * <eirikb@eirkb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.sge.collisiondetection;

import no.eirikb.sge.Point;
import no.eirikb.sge.item.Item;

/**
 *
 * @author eirikb
 */
public class Collision {

    private Item item1;
    private Item item2;
    private Point startLine1;
    private Point endLine1;
    private Point startLine2;
    private Point endLine2;
    private int x;
    private int y;

    public Collision(Item item1, Item item2, Point startLine1, Point endLine1, Point startLine2, Point endLine2, int x, int y) {
        this.item1 = item1;
        this.item2 = item2;
        this.startLine1 = startLine1;
        this.endLine1 = endLine1;
        this.startLine2 = startLine2;
        this.endLine2 = endLine2;
        this.x = x;
        this.y = y;
    }

    public Point getEndLine1() {
        return endLine1;
    }

    public Point getEndLine2() {
        return endLine2;
    }

    public Item getItem1() {
        return item1;
    }

    public Item getItem2() {
        return item2;
    }

    public Point getStartLine1() {
        return startLine1;
    }

    public Point getStartLine2() {
        return startLine2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
