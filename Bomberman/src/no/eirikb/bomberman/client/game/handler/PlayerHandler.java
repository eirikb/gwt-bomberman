/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.handler;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.client.GamePanel;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Way;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class PlayerHandler extends Handler {

    private int imgSize;
    private final int LEFTDIFF = 0;
    private final int TOPDIFF = -4;

    public PlayerHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
        imgSize = game.getImgSize();
        drawPlayers();
    }

    public void drawPlayers() {
        for (Player player : game.getPlayers()) {
            gamePanel.drawPlayer(player);
        }
    }

    public void addPlayer(Player player) {
        gamePanel.drawPlayer(player);
    }

    public void handle() {
        for (Player player : game.getPlayers()) {
            if (player.getWay() != Way.NONE) {
                Image man = player.getImage();
                int left = gamePanel.getWidgetLeft(man) - LEFTDIFF;
                int top = gamePanel.getWidgetTop(man) - TOPDIFF;
                int x = (left / imgSize);
                int y = (top / imgSize);
                int modX = left % imgSize;
                int modY = top % imgSize;

                int newLeft = left;
                int newTop = top;
                switch (player.getWay()) {
                    case LEFT:
                    case RIGHT:
                        animate(player, player.getWay() == Way.LEFT ? 'l' : 'r');
                        if (y % 2 == 0) {
                            if (modY == 0) {
                                newLeft += player.getWay() == Way.LEFT ? -player.getSpeed() : player.getSpeed();
                            } else if (modY - player.getSpeed() < 0) {
                                newTop -= 1;
                            } else {
                                newTop -= player.getSpeed();
                            }
                        } else {
                            if (modY + player.getSpeed() > imgSize) {
                                newTop += 1;
                            } else {
                                newTop += player.getSpeed();
                            }
                        }
                        break;
                    case UP:
                    case DOWN:
                        animate(player, player.getWay() == Way.UP ? 'u' : 'd');
                        if (x % 2 == 0) {
                            if (modX == 0) {
                                newTop += player.getWay() == Way.UP ? -player.getSpeed() : player.getSpeed();
                            } else if (modX - player.getSpeed() < 0) {
                                newLeft -= 1;
                            } else {
                                newLeft -= player.getSpeed();
                            }
                        } else {
                            if (modX + player.getSpeed() > imgSize) {
                                newLeft += 1;
                            } else {
                                newLeft += player.getSpeed();
                            }
                        }
                        break;
                }
                boolean canWalk = false;
                if (newLeft > left) {
                    canWalk = game.canWalk(newLeft + imgSize - 1, newTop);
                } else if (newTop > top) {
                    canWalk = game.canWalk(newLeft, newTop + imgSize - 1);
                } else if (newLeft < left || newTop < top) {
                    canWalk = game.canWalk(newLeft, newTop);
                }
                if (newLeft >= 0 && newTop >= 0 && newLeft < game.getWidth()
                        && newTop < game.getHeight() && canWalk) {
                    player.setX(x);
                    player.setY(y);
                    gamePanel.setWidgetPosition(man, newLeft + LEFTDIFF, newTop + TOPDIFF);
                }
            }
        }
    }

    private void animate(Player player, char c) {
        int animation = player.animate();
        if (animation >= 0) {
            player.getImage().setUrl("img/m" + c + animation + ".png");
        }
    }
}
