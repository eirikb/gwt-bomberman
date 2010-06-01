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
import no.eirikb.sge.item.Item;

/**
 *
 * @author eirikb
 */
public class Zone {

    private int x;
    private int y;
    private List<Item> items;

    public Zone(int x, int y) {
        this.x = x;
        this.y = y;
        items = new ArrayList<Item>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (!items.contains(item)) {
            items.add(item);
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
