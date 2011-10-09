/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.game.handler.helper;

import com.google.gwt.core.client.GWT;
import no.eirikb.bomberman.game.Game;
import no.eirikb.bomberman.game.Player;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.game.Way;
import no.eirikb.bomberman.game.powerup.Powerup;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class WalkHandler {

    private Game game;
    private Player player;
    private double newX;
    private double newY;
    private Sprite sprite1;
    private Sprite sprite2;
    private int imgSize;
    private Way way;

    public boolean walk(Game game, Player player) {
        this.game = game;
        this.player = player;
        imgSize = game.getImgSize();
        way = player.getWay();
        if (player.getSpeed() > 0) {
            if (movePlayer()) {
                player.setX(newX);
                player.setY(newY);
                return true;
            }
        }
        return false;
    }

    private boolean movePlayer() {
        newX = player.getX();
        newY = player.getY();
        switch (way) {
            case LEFT:
                newX -= player.getSpeed();
                break;
            case UP:
                newY -= player.getSpeed();
                break;
            case RIGHT:
                newX += player.getSpeed();
                break;
            case DOWN:
                newY += player.getSpeed();
                break;
        }
        if (newX >= 0 && newX + imgSize - 1 < game.getWidth() && newY >= 0 && newY + imgSize - 1 < game.getHeight()) {
            return moveInsideMap();
        } else {
            return snapToMapWall();
        }
    }

    private boolean moveInsideMap() {
        Sprite[][] sprites = game.getSprites();
        int spriteX = (int) (newX / imgSize);
        int spriteY = (int) (newY / imgSize);
        sprite1 = sprites[spriteX][spriteY];
        switch (way) {
            case LEFT:
                sprite2 = sprites[spriteX][(int) ((newY + imgSize - 1) / imgSize)];
                break;
            case UP:
                sprite2 = sprites[(int) ((newX + imgSize - 1) / imgSize)][spriteY];
                break;
            case RIGHT:
                sprite1 = sprites[spriteX + 1][spriteY];
                sprite2 = sprites[spriteX + 1][(int) ((newY + imgSize - 1) / imgSize)];
                break;
            case DOWN:
                sprite1 = sprites[spriteX][spriteY + 1];
                sprite2 = sprites[(int) ((newX + imgSize - 1) / imgSize)][spriteY + 1];
                break;
        }
        if (canWalk(sprite1) == null && canWalk(sprite2) == null) {
            if (way != player.getWay()) {
                snapTofloor();
            } else {
                if (sprite1 == sprite2) {
                    if (sprite1 instanceof Powerup) {
                        game.bump(player, sprite1);
                    }
                } else {
                    if (sprite1 instanceof Powerup) {
                        game.bump(player, sprite1);
                    } else if (sprite2 instanceof Powerup) {
                        game.bump(player, sprite1);
                    }
                }
            }
            return true;
        } else {
            return hit();
        }
    }

    private boolean hit() {
        if (snapToSprite()) {
            return true;
        } else {
            if (canWalk(sprite1) != null && canWalk(sprite2) != null) {
                return false;
            } else {
                Sprite sprite = canWalk(sprite1) != null ? sprite1 : sprite2;
                switch (way) {
                    case LEFT:
                    case RIGHT:
                        way = newY >= sprite.getSpriteY() * imgSize ? Way.DOWN : way.UP;
                        break;
                    case UP:
                    case DOWN:
                        way = newX >= sprite.getSpriteX() * imgSize ? Way.RIGHT : Way.LEFT;
                }
                return movePlayer();
            }
        }
    }

    private boolean snapToSprite() {
        Sprite snap = canWalk(sprite1) != null ? sprite1 : sprite2;
        switch (way) {
            case LEFT:
                newX = snap.getSpriteX() * imgSize + imgSize;
                break;
            case UP:
                newY = snap.getSpriteY() * imgSize + imgSize;
                break;
            case RIGHT:
                newX = snap.getSpriteX() * imgSize - imgSize;
                break;
            case DOWN:
                newY = snap.getSpriteY() * imgSize - imgSize;
                break;
        }
        if (newX != player.getX() || newY != player.getY()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean snapToMapWall() {
        switch (way) {
            case LEFT:
                newX = 0;
                break;
            case UP:
                newY = 0;
                break;
            case RIGHT:
                newX = game.getWidth() - imgSize;
                break;
            case DOWN:
                newY = game.getHeight() - imgSize;
                break;
        }
        return newX != player.getX() || newY != player.getY();
    }

    private void snapTofloor() {
        int spriteX = (int) (player.getX() / imgSize);
        int spriteY = (int) (player.getY() / imgSize);
        int newSpriteX = (int) (newX / imgSize);
        int newSpriteY = (int) (newY / imgSize);
        if (newSpriteX != spriteX || newSpriteY != spriteY) {
            newX = spriteX * imgSize;
            newY = spriteY * imgSize;
            switch (player.getWay()) {
                case LEFT:
                case RIGHT:
                    newY = way == Way.DOWN ? newSpriteY * imgSize : newY;
                    break;
                case UP:
                case DOWN:
                    newX = way == Way.RIGHT ? newSpriteX * imgSize : newX;
                    break;
            }
        }
    }

    private Sprite canWalk(Sprite sprite) {
        return sprite == null || sprite.getzAxis() < player.getzAxis() ? null : sprite;
    }
}
