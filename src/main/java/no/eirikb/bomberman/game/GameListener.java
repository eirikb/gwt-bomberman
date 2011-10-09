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
public interface GameListener {

    public void addSprite(Sprite sprite);

    public void removeSprite(Sprite sprite);

    public void bump(Player player, Sprite sprite);

    public void playerDie(Player player);

    public void playerLive(Player player);
}
