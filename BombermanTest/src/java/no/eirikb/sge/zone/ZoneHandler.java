/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.sge.zone;

import java.util.ArrayList;
import java.util.List;
import no.eirikb.sge.collisiondetection.Collision;
import no.eirikb.sge.collisiondetection.CollisionListener;
import no.eirikb.sge.collisiondetection.CollisionDetection;
import no.eirikb.sge.item.Item;

/**
 *
 * @author eirikb
 */
public class ZoneHandler {

    private int width;
    private int height;
    private Zone[][] zones;
    private int zoneSize;
    private List<Zone> usedZones;

    public ZoneHandler(int width, int height) {
        this.width = width;
        this.height = height;
        zones = new Zone[0][0];
        zoneSize = 0;
        usedZones = new ArrayList<Zone>();
    }

    /**
     * Add item to the game.
     * If the size of the item is larger than the max set size,
     * all the zones will be alternated to fit the new size O(m*n)
     * where m is zones and n is items
     * @param item
     * @return false if the item is outside the boundary (width and height)
     */
    public boolean addItem(Item item) {
        if (item.getX() < 0 || item.getY() < 0
                || item.getX() + item.getWidth() > width
                || item.getY() + item.getHeight() > height) {
            return false;
        }
        int size = Math.max(item.getWidth(), item.getHeight());
        if (size > zoneSize) {
            expandZones(size + 1);
        }
        addItemToZones(item);
        return true;
    }

    /**
     * Remove a item
     * This method does _NOT_ fix the size of the the zones.
     * This is because that is a O(m*n) operation.
     * addItem also have ae O(m*n) - but ONLY on adding a bigger item.
     * @param item
     */
    public void removeItem(Item item) {
        for (Zone zone : getZones(item)) {
            zone.removeItem(item);
        }
    }

    /**
     * Get all items that is around the given item.
     * This will be all the items in all the zones the given item is inside.
     * @param item
     * @return all items around give item
     */
    public List<Item> getItems(Item item) {
        List<Item> items = new ArrayList<Item>();

        for (Zone z : getZones(item)) {
            synchronized (z.getItems()) {
                for (Item i : z.getItems()) {
                    if (i != item && !items.contains(i)) {
                        items.add(i);
                    }
                }
            }
        }
//        for (int i = 0; i < zones.length; i++) {
//            for (int j = 0; j < zones[0].length; j++) {
//                for (Item itm : zones[i][j].getItems()) {
//                    if (itm != item && !items.contains(itm)) {
//                        items.add(itm);
//                    }
//                }
//            }
//        }
        return items;
    }

    /**
     * Get all items.
     * Only look in known used zones
     * @return 
     */
    public List<Item> getItems() {
        List<Item> items = new ArrayList<Item>();
        for (Zone zone : usedZones) {
            for (Item item : zone.getItems()) {
                if (!items.contains(item)) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    public List<Collision> move(Item item, double x, double y, List<CollisionListener> collisionListeners) {
        double origX = item.getX();
        double origY = item.getY();
        for (Zone z : getZones(zones, zoneSize, item)) {
            z.removeItem(item);
            if (z.getItems().isEmpty()) {
                usedZones.remove(z);
            }
        }
        List<Collision> collisions = new ArrayList<Collision>();
        if ((x != origX || y != origY)) {
            item.setX(x);
            item.setY(y);
            CollisionDetection cd = new CollisionDetection();
            for (Item i : getItems(item)) {
                List<Collision> cols = cd.collisiondetecion(item, i, collisionListeners);
                if (cols != null && cols.size() > 0) {
                    collisions.addAll(cols);
                }
            }
        }
        addItemToZones(item);
        return collisions;
    }

    public List<Collision> move(Item item) {
        return move(item, item.getCollisionListeners());
    }

    public List<Collision> move(Item item, List<CollisionListener> collisionListeners) {
        List<Collision> collisions = new ArrayList<Collision>();
        if (item.getDirection() != null) {
            double x = item.getX();
            double y = item.getY();
            double x2 = item.getX() + item.getDirection().getCos() * item.getSpeed();
            double y2 = item.getY() + item.getDirection().getSin() * item.getSpeed();
            int xDiff = x < x2 ? 1 : -1;
            int yDiff = y < y2 ? 1 : -1;
            while ((int) x != (int) x2 || (int) y != (int) y2) {
                x += (int) x != (int) x2 ? xDiff : 0;
                y += (int) y != (int) y2 ? yDiff : 0;
                collisions.addAll(move(item, x, y, collisionListeners));

            }
            collisions.addAll(move(item, x2, y2, collisionListeners));
        }
        return collisions;
    }

    /**
     * Get all the zones
     * @return Zones
     */
    public List<Zone> getZones() {
        List<Zone> z = new ArrayList<Zone>();
        for (int x = 0; x < zones.length; x++) {
            for (int y = 0; y < zones[0].length; y++) {
                z.add(zones[x][y]);
            }
        }
        return z;
    }

    public List<Zone> getUsedZones() {
        return usedZones;
    }

    public int getZoneSize() {
        return zoneSize;
    }

    private void expandZones(int size) {
        Zone[][] newZones = new Zone[(width / size) + 1][(height / size) + 1];
        for (int x = 0; x < newZones.length; x++) {
            for (int y = 0; y < newZones[0].length; y++) {
                newZones[x][y] = new Zone(x, y);
            }
        }

        for (int x = 0; x < zones.length; x++) {
            for (int y = 0; y < zones[0].length; y++) {
                Zone z = zones[x][y];
                for (Item item : z.getItems()) {
                    addItemToZones(newZones, size, item);
                }
            }
        }
        zones = newZones;
        for (int x = 0; x < zones.length; x++) {
            for (int y = 0; y < zones[0].length; y++) {
                zones[x][y] = zones[x][y] == null ? new Zone(x, y) : zones[x][y];
            }
        }
        zoneSize = size;
    }

    private List<Zone> getZones(Item item) {
        return getZones(zones, zoneSize, item);
    }

    private List<Zone> getZones(Zone[][] zones2, int zoneSize2, Item item) {
        List<Zone> z = new ArrayList<Zone>();
        int x1 = (int) (item.getX() / zoneSize2);
        int x2 = (int) ((item.getX() + item.getWidth()) / zoneSize2);
        int y1 = (int) (item.getY() / zoneSize2);
        int y2 = (int) ((item.getY() + item.getHeight()) / zoneSize2);
        if (x1 >= 0 && x1 < zones.length && y1 >= 0 && y1 < zones[0].length
                && x2 >= 0 && x2 < zones.length && y2 >= 0 && y2 < zones[0].length) {
            z.add(zones2[x1][y1]);
            if (x1 != x2) {
                z.add(zones2[x2][y1]);
            }
            if (y1 != y2) {
                z.add(zones2[x1][y2]);
            }
            if (x1 != x2) {
                z.add(zones2[x2][y2]);
            }
        }
        return z;
    }

    private void addItemToZones(Item item) {
        addItemToZones(zones, zoneSize, item);
    }

    private void addItemToZones(Zone[][] zones2, int zoneSize2, Item item) {
        for (Zone z : getZones(zones2, zoneSize2, item)) {
            z.addItem(item);
            if (!usedZones.contains(z)) {
                usedZones.add(z);
            }
        }
    }
}
