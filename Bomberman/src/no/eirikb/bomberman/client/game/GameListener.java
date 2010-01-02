/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface GameListener {

    public void addPlayer(Player player);

    public void removePlayer(Player player);

    public void addBomb(Bomb bomb);

    public void removeBomb(Bomb bomb);

    public void addExplosion(Explosion explosion);

    public void addBoomBrick(BoomBrick boomBrick);
}
