/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

import com.google.gwt.user.client.ui.Image;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class CoreExplosion extends Explosion {

    private List<Explosion> explosions;
    private List<int[]> hits; // {x, y}

    public CoreExplosion(Image image, int spriteX, int spriteY, ExplosionType explosionType) {
        super(image, spriteX, spriteY, explosionType);
        explosions = new ArrayList<Explosion>();
        hits = new ArrayList<int[]>();
        setAnimation(new Animation(4, 4));
    }

    public void addExplosion(Explosion explosion) {
        explosion.setAnimation(new Animation(4, 4));
        explosions.add(explosion);
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public void addHit(int x, int y) {
        hits.add(new int[]{x, y});
    }

    public List<int[]> getHits() {
        return hits;
    }
}
