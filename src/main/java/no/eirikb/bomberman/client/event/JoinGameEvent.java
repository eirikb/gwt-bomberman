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
import com.google.gwt.event.shared.GwtEvent.Type;
import no.eirikb.bomberman.game.GameInfo;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class JoinGameEvent extends GwtEvent<JoinGameEventHandler> {

    public static Type<JoinGameEventHandler> TYPE = new Type<JoinGameEventHandler>();
    private GameInfo GameInfo;

    public JoinGameEvent(GameInfo GameInfo) {
        this.GameInfo = GameInfo;
    }

    public GameInfo getGameInfo() {
        return GameInfo;
    }

    @Override
    public Type<JoinGameEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(JoinGameEventHandler handler) {
        handler.onJoinGame(this);
    }
}
