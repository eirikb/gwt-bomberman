/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event;

import com.google.gwt.event.shared.GwtEvent;
import no.eirikb.bomberman.game.Player;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {

    public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
    private Player player;

    public LoginEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Type<LoginEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoginEventHandler handler) {
        handler.onLogin(this);
    }
}
