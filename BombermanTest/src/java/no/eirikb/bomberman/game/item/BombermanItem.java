/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.eirikb.bomberman.game.item;

import no.eirikb.sge.item.Item;

/**
 *
 * @author eirikb
 */
public abstract class BombermanItem extends Item {

    protected double defaultSpeed;
    protected int armor;

    public BombermanItem(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    public BombermanItem(double x, double y, int width, int height, double defaultSpeed, int armor) {
        super(x, y, width, height);
        this.defaultSpeed = defaultSpeed;
        this.armor = armor;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public double getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(double defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }
}
