/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.game.builder;

import com.google.gwt.user.client.ui.Image;
import no.eirikb.bomberman.client.game.Player;
import no.eirikb.bomberman.client.game.Settings;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class PlayerBuilder {

    private final static String PLAYERURL = "img/md1.png";

    public static Player createPlayer(Settings settings, String nick) {
        return new Player(new Image(PLAYERURL), nick, settings.getPlayerSpeed(), settings.getBombPower(), settings.getPlayerBombStartAmount());
    }
}
