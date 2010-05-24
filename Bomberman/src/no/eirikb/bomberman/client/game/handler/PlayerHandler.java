/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.client.ui.game.GamePanel;
import no.eirikb.bomberman.client.game.Bomb;
import no.eirikb.bomberman.client.game.Game;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.game.Way;
import no.eirikb.bomberman.client.game.handler.helper.WalkHandler;
import no.eirikb.bomberman.client.GameService;
import no.eirikb.bomberman.client.GameServiceAsync;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class PlayerHandler extends Handler {

    private final int XDIFF = 0;
    private final int YDIFF = -4;
    private WalkHandler walkHandler;

    public PlayerHandler(Game game, GamePanel gamePanel) {
        super(game, gamePanel);
        walkHandler = new WalkHandler();
        for (Player player : game.getAlivePlayers()) {
            drawPlayer(player);
        }
    }

    private void drawPlayer(Player player) {
        player.setImage(new Image("img/md1.png"));
        gamePanel.add(player.getImage(), (int) (player.getX() + XDIFF), (int) (player.getY() + YDIFF));
    }

    public void handle() {
        for (Player player : game.getAlivePlayers()) {
            if (player.getWay() != Way.NONE) {
                if (walkHandler.walk(game, player)) {
                    gamePanel.setWidgetPosition(player.getImage(), (int) player.getX() + XDIFF, (int) player.getY() + YDIFF);
                }
                switch (player.getWay()) {
                    case LEFT:
                        animate(player, 'l');
                        break;
                    case UP:
                        animate(player, 'u');
                        break;
                    case RIGHT:
                        animate(player, 'r');
                        break;
                    case DOWN:
                        animate(player, 'd');
                        break;
                }
            }
        }
    }

    private void animate(Player player, char c) {
        int animation = player.animate();
        if (animation > 0) {
            player.getImage().setUrl("img/m" + c + animation + ".png");
        }
    }

    public void addSprite(Sprite sprite) {
        if (sprite instanceof Bomb) {
            Bomb bomb = (Bomb) sprite;
            Player player = bomb.getOwner();
            player.setBombAbount(player.getBombAbount() - 1);
        } else if (sprite instanceof Player) {
            drawPlayer((Player) sprite);
        }
    }

    public void removeSprite(Sprite sprite) {
        if (sprite instanceof Bomb) {
            Bomb bomb = (Bomb) sprite;
            bomb.getOwner().setBombAbount(bomb.getOwner().getBombAbount() + 1);
        }
    }

    public void bump(Player player, Sprite sprite) {
    }

    public void playerDie(Player player) {
        gamePanel.remove(player.getImage());
        if (player == gamePanel.getPlayer()) {
            GameServiceAsync gameService = GWT.create(GameService.class);
            gameService.died(new AsyncCallback() {

                public void onFailure(Throwable caught) {
                }

                public void onSuccess(Object result) {
                }
            });
        }
    }

    public void playerLive(Player player) {
        drawPlayer(player);
    }
}
