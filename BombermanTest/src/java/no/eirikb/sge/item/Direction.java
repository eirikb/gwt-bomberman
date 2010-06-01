/*
 * "THE BEER-WARE LICENSE" (Revision 42):
 * =============================================================================
 * <eirikb@eirkb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.sge.item;

/**
 *
 * @author eirikb
 */
public class Direction {

    private double cos;
    private double sin;

    public Direction(double cos, double sin) {
        this.cos = cos;
        this.sin = sin;
    }

    public double getCos() {
        return cos;
    }

    public void setCos(double cos) {
        this.cos = cos;
    }

    public double getSin() {
        return sin;
    }

    public void setSin(double sin) {
        this.sin = sin;
    }
}
