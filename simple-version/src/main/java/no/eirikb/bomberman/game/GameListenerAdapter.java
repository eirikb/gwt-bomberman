/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game;

import no.eirikb.sge.item.Item;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GameListenerAdapter implements GameListener {

    @Override
    public void tickStart() {
    }

    @Override
    public void tickEnd() {
    }

    @Override
    public void moveItem(Item item) {
    }

    @Override
    public void tickItem(Item item) {
    }

    @Override
    public void itemOutOfBounds(Item item) {
    }
}
