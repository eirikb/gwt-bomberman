/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.sge.item;

import java.util.ArrayList;
import java.util.List;
import no.eirikb.sge.Point;
import no.eirikb.sge.collisiondetection.CollisionListener;

/**
 *
 * @author eirikb
 */
public class Item {

    private double x;
    private double y;
    private double z;
    private int width;
    private int height;
    private Point[] lines;
    private Direction direction;
    private double speed;
    private List<CollisionListener> collisionListeners;

    public Item(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        collisionListeners = new ArrayList<CollisionListener>();
    }

    /**
     * Create a direction based on a point relative to the item
     * @param x example mouse.x
     * @param y example mouse.y
     */
    public void setDirection(double x2, double y2) {
        int a = (int) (y2 - y);
        int b = (int) (x2 - x);
        double h = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double sine = a / h;
        double cosine = b / h;
        direction = new Direction(cosine, sine);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point[] getLines() {
        return lines;
    }

    public void setLines(Point[] lines) {
        this.lines = lines;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addCollisionListener(CollisionListener collisionListener) {
        collisionListeners.add(collisionListener);
    }

    public void removeCollisionListener(CollisionListener collisionListener) {
        collisionListeners.remove(collisionListener);
    }

    public List<CollisionListener> getCollisionListeners() {
        return collisionListeners;
    }
}
