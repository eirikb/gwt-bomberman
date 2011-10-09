/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.shared.event.game;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class PlayerStopWalkingEvent extends GameEvent {

    private double x;
    private double y;

    public PlayerStopWalkingEvent() {
    }

    public PlayerStopWalkingEvent(String gameName, String playerName, double x, double y) {
        super(gameName, playerName);
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
