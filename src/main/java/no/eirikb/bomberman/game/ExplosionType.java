/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public enum ExplosionType {

    CORE, HORIZONTAL, VERTICAL, LEFTEND, UPEND, RIGHTEND, DOWNEND;

    @Override
    public String toString() {
        switch (this) {
            case CORE:
                return "core";
            case HORIZONTAL:
                return "sidehorizontal";
            case VERTICAL:
                return "sidevertical";
            case LEFTEND:
                return "endleft";
            case UPEND:
                return "endup";
            case RIGHTEND:
                return "endright";
            case DOWNEND:
                return "enddown";
        }
        return null;
    }
}
