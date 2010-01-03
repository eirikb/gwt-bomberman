/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.eirikb.bomberman.client.game.poweup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class Game {

    private Sprite[][] sprites;
    private Map<String, Player> players;
    private List<Bomb> bombs;
    private List<Explosion> explosions;
    private List<BoomBrick> boomBricks;
    private List<Powerup> powerups;
    private int width;
    private int height;
    private int imgSize;
    private List<GameListener> gameListeners;

    public Game(Sprite[][] sprites, int width, int height, int imgSize) {
        this.sprites = sprites;
        this.width = width;
        this.height = height;
        this.imgSize = imgSize;
        players = new HashMap<String, Player>();
        bombs = new ArrayList<Bomb>();
        explosions = new ArrayList<Explosion>();
        boomBricks = new ArrayList<BoomBrick>();
        powerups = new ArrayList<Powerup>();
        gameListeners = new ArrayList<GameListener>();
    }

    public Sprite[][] getSprites() {
        return sprites;
    }

    public void addPlayer(Player player) {
        players.put(player.getNick(), player);
        for (GameListener gameListener : gameListeners) {
            gameListener.addPlayer(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getNick());
        for (GameListener gameListener : gameListeners) {
            gameListener.removePlayer(player);
        }
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
        sprites[bomb.getSpriteX()][bomb.getSpriteY()] = bomb;
        for (GameListener gameListener : gameListeners) {
            gameListener.addBomb(bomb);
        }
    }

    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
        sprites[bomb.getSpriteX()][bomb.getSpriteY()] = null;
        for (GameListener gameListener : gameListeners) {
            gameListener.removeBomb(bomb);
        }
    }

    public void addExplosion(Explosion explosion) {
        explosions.add(explosion);
        sprites[explosion.getSpriteX()][explosion.getSpriteY()] = explosion;
        for (GameListener gameListener : gameListeners) {
            gameListener.addExplosion(explosion);
        }
    }

    public void removeExplosion(Explosion explosion) {
        explosions.remove(explosion);
        sprites[explosion.getSpriteX()][explosion.getSpriteY()] = null;
    }

    public void addPowerup(Powerup powerup) {
        powerups.add(powerup);
        sprites[powerup.getSpriteX()][powerup.getSpriteY()] = powerup;
        for (GameListener gameListener : gameListeners) {
            gameListener.addPowerup(powerup);
        }
    }

    public void addBoomBrick(BoomBrick boomBrick) {
        boomBricks.add(boomBrick);
        sprites[boomBrick.getSpriteX()][boomBrick.getSpriteY()] = boomBrick;
        for (GameListener gameListener : gameListeners) {
            gameListener.addBoomBrick(boomBrick);
        }
    }

    public void removeBoomBrick(BoomBrick boomBrick) {
        boomBricks.remove(boomBrick);
        sprites[boomBrick.getSpriteX()][boomBrick.getSpriteY()] = null;
    }

    public Iterable<Player> getPlayers() {
        return players.values();
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public List<BoomBrick> getBoomBricks() {
        return boomBricks;
    }

    public int getHeight() {
        return height;
    }

    public int getImgSize() {
        return imgSize;
    }

    public int getWidth() {
        return width;
    }

    public boolean canWalk(int newLeft, int newTop) {
        int x = newLeft / imgSize;
        int y = newTop / imgSize;
        if (x >= 0 && x < sprites.length && y >= 0 && y < sprites[0].length) {
            return sprites[newLeft / imgSize][newTop / imgSize] == null;
        }
        return false;
    }

    public void addGameListener(GameListener gameListener) {
        gameListeners.add(gameListener);
    }

    public void removeGameListener(GameListener gameListener) {
        gameListeners.remove(gameListener);
    }
}
