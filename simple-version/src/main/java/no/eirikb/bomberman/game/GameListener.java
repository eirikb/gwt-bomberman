/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.bomberman.game;

import no.eirikb.sge.item.Item;

/**
 *
 * @author Eirik Brandtzæg eirikb@eirikb.no
 */
public interface GameListener {

    public void tickStart();

    public void tickEnd();

    public void moveItem(Item item);

    public void tickItem(Item item);

    public void itemOutOfBounds(Item item);
}
