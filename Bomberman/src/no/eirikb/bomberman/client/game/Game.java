/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.eirikb.bomberman.client.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class Game implements Serializable {

    private GameInfo gameInfo;
    private Sprite[][] sprites;
    private Map<String, Player> alivePlayers;
    private Map<String, Player> deadPlayers;
    private List<Bomb> bombs;
    private List<Explosion> explosions;
    private List<BoomBrick> boomBricks;
    private List<Powerup> powerups;
    private List<GameListener> gameListeners;
    private Settings settings;

    public Game() {
    }

    public Game(String name, Sprite[][] sprites, Settings settings) {
        this.gameInfo = new GameInfo(name, settings.getMaxPlayers());
        this.sprites = sprites;
        this.settings = settings;
        alivePlayers = new HashMap<String, Player>();
        deadPlayers = new HashMap<String, Player>();
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
        alivePlayers.put(player.getNick(), player);
        gameInfo.setPlayerSize(alivePlayers.size());
        addSpriteInvisible(player);
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

    public void playerDie(Player player) {
        alivePlayers.remove(player.getNick());
        deadPlayers.put(player.getNick(), player);
        for (GameListener gameListener : gameListeners) {
            gameListener.playerDie(player);
        }
    }

    public Iterable<Player> getAlivePlayers() {
        return alivePlayers.values();
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
        return settings.getMapHeight();
    }

    public int getImgSize() {
        return settings.getImgSize();
    }

    public int getWidth() {
        return settings.getMapWidth();
    }

    public Settings getSettings() {
        return settings;
    }

    public void addGameListener(GameListener gameListener) {
        gameListeners.add(gameListener);
    }

    public void removeGameListener(GameListener gameListener) {
        gameListeners.remove(gameListener);
    }

    public int getAlivePlayersSize() {
        return alivePlayers.size();
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void bump(Player player, Sprite sprite) {
        if (sprites[sprite.getSpriteX()][sprite.getSpriteY()] != null) {
            for (GameListener gameListener : gameListeners) {
                gameListener.bump(player, sprite);
            }
        }
    }

    public Player getAlivePlayer(String nick) {
        return alivePlayers.get(nick);
    }

    public Player getDeadPlayer(String playerNick) {
        return deadPlayers.get(playerNick);
    }

    public void playerLive(Player player) {
        alivePlayers.put(player.getNick(), player);
        deadPlayers.remove(player.getNick());
        for (GameListener gameListener : gameListeners) {
            gameListener.playerLive(player);
        }
    }
}
