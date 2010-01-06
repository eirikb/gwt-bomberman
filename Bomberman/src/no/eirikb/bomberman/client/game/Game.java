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

    public void removeAllGameListeners() {
        for (int i = 0; i < gameListeners.size(); i++) {
            gameListeners.remove(0);
        }
    }

    private void addSpriteInvisible(Sprite sprite) {
        for (GameListener gameListener : gameListeners) {
            gameListener.addSprite(sprite);
        }
    }

    private void addSprite(Sprite sprite) {
        sprites[sprite.getSpriteX()][sprite.getSpriteY()] = sprite;
        addSpriteInvisible(sprite);
    }

    private void removeSpriteInvisible(Sprite sprite) {
        for (GameListener gameListener : gameListeners) {
            gameListener.removeSprite(sprite);
        }
    }

    private void removeSprite(Sprite sprite) {
        sprites[sprite.getSpriteX()][sprite.getSpriteY()] = null;
        removeSpriteInvisible(sprite);
    }

    public void addPlayer(Player player) {
        players.put(player.getNick(), player);
        addSpriteInvisible(player);
    }

    public void removePlayer(Player player) {
        players.remove(player.getNick());
        removeSpriteInvisible(player);
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
        addSprite(bomb);
    }

    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
        removeSprite(bomb);
    }

    public void addExplosion(Explosion explosion) {
        explosions.add(explosion);
        addSprite(explosion);
    }

    public void removeExplosion(Explosion explosion) {
        explosions.remove(explosion);
        removeSprite(explosion);
    }

    public void addBoomBrick(BoomBrick boomBrick) {
        boomBricks.add(boomBrick);
        addSprite(boomBrick);
    }

    public void removeBoomBrick(BoomBrick boomBrick) {
        boomBricks.remove(boomBrick);
        removeSprite(boomBrick);
    }

    public void addPowerup(Powerup powerup) {
        powerups.add(powerup);
        addSprite(powerup);
    }

    public void removePowerup(Powerup powerup) {
        powerups.remove(powerup);
        removeSprite(powerup);
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

    public void addGameListener(GameListener gameListener) {
        gameListeners.add(gameListener);
    }

    public void removeGameListener(GameListener gameListener) {
        gameListeners.remove(gameListener);
    }
}
