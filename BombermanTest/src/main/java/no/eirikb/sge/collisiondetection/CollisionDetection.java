/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.sge.collisiondetection;

import com.google.gwt.core.client.GWT;
import no.eirikb.sge.Point;
import java.util.ArrayList;
import java.util.List;
import no.eirikb.sge.item.Item;

/**
 *
 * @author eirikb
 */
public class CollisionDetection {

    public List<Collision> collisiondetection(Item item1, Item item2, List<CollisionListener> collisionListeners) {
        if (item1.getX() <= item2.getX() + item2.getWidth()
                && item1.getX() + item1.getWidth() >= item2.getX()
                && item1.getY() <= item2.getY() + item2.getHeight()
                && item1.getY() + item1.getHeight() >= item2.getY()) {
            if (item1.getLines() != null && item2.getLines() != null) {
                return intersectLines(item1, item2, collisionListeners);
            } else if (item1.getLines() == null && item2.getLines() == null) {
                List<Collision> collisions = new ArrayList<Collision>();
                int x1 = (int) item1.getX();
                int y1 = (int) item1.getY();
                int x1w = (int) (item1.getX() + item1.getWidth());
                int y1h = (int) (item1.getY() + item1.getHeight());
                int x2 = (int) item2.getX();
                int y2 = (int) item2.getY();
                int x2w = (int) (item2.getX() + item2.getWidth());
                int y2h = (int) (item2.getY() + item2.getHeight());
                if (x1w == x2) {
                    collisions.add(new Collision(item1, item2, new Point(x1w, y1), new Point(x1w, y1h),
                            new Point(x2, y2), new Point(x2, y2h), x1w, x2));
                }
                if (y1h == y2) {
                    collisions.add(new Collision(item1, item2, new Point(x1, y1h), new Point(x1w, y1h),
                            new Point(x2, y2), new Point(x2w, y2), y1h, y2));
                }
                if (y1 == y2h) {
                    collisions.add(new Collision(item1, item2, new Point(x1, y1), new Point(x1w, y1),
                            new Point(x2, y2h), new Point(x2w, y2h), y1, y2h));
                }
                if (x1 == x2w) {
                    collisions.add(new Collision(item1, item2, new Point(x1, y1), new Point(x1, y1h),
                            new Point(x2w, y2), new Point(x2w, y2h), x1, x2w));
                }
                for (CollisionListener collisionListener : collisionListeners) {
                    for (Collision collision : collisions) {
                        collisionListener.onCollision(collision);
                    }
                }
                return collisions;
            }
        }
        return null;
    }

    public List<Collision> intersectLines(Item item1, Item item2, List<CollisionListener> collisionListeners) {
        List<Collision> collisions = new ArrayList<Collision>();
        Point[] poly1 = item1.getLines();
        Point[] poly2 = item2.getLines();
        for (int i = 0; i < poly1.length; i++) {
            for (int j = 0; j < poly2.length; j++) {
                int nextPoly1 = i + 1;
                if (i == poly1.length - 1) {
                    nextPoly1 = 0;
                }
                int nextPoly2 = j + 1;
                if (j == poly2.length - 1) {
                    nextPoly2 = 0;
                }
                int[] inter = intersectLines(item1, item2, poly1[i], poly1[nextPoly1], poly2[j], poly2[nextPoly2]);
                if (inter != null) {
                    Collision collision = new Collision(item1, item2, poly1[i], poly1[nextPoly1], poly2[j], poly2[nextPoly2], inter[0], inter[1]);
                    if (collisionListeners != null) {
                        for (CollisionListener collisionListener : collisionListeners) {
                            collisionListener.onCollision(collision);
                        }
                    }
                    collisions.add(collision);
                }
            }
        }
        return collisions;
    }

    public int[] intersectLines(Item item1, Item item2, Point startLine1, Point endLine1, Point startLine2, Point endLine2) {
        double x1 = item1.getX();
        double y1 = item1.getY();
        double x2 = item2.getX();
        double y2 = item2.getY();
        return intersectLines(
                x1 + startLine1.getX(), y1 + startLine1.getY(),
                x1 + endLine1.getX(), y1 + endLine1.getY(),
                x2 + startLine2.getX(), y2 + startLine2.getY(),
                x2 + endLine2.getX(), y2 + endLine2.getY());
    }

    public int[] intersectLines(double x0, double y0, double x1, double y1,
            double x2, double y2, double x3, double y3) {

        int d = (int) ((y3 - y2) * (x1 - x0) - (x3 - x2) * (y1 - y0));

        int n_a = (int) ((x3 - x2) * (y0 - y2) - (y3 - y2) * (x0 - x2));

        int n_b = (int) ((x1 - x0) * (y0 - y2) - (y1 - y0) * (x0 - x2));

        if (d == 0) {
            return null;
        }

        int ua = (n_a << 14) / d;
        int ub = (n_b << 14) / d;

        if (ua >= 0 && ua <= (1 << 14) && ub >= 0 && ub <= (1 << 14)) {
            int hx = (int) x0 + ((ua * (int) (x1 - x0)) >> 14);
            int hy = (int) y0 + ((ua * (int) (y1 - y0)) >> 14);
            return new int[]{hx, hy};
        }
        return null;
    }
}
