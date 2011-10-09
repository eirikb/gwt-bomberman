/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class QuitGameEvent extends GwtEvent<QuitGameEventHandler> {

    public static Type<QuitGameEventHandler> TYPE = new Type<QuitGameEventHandler>();

    @Override
    public Type<QuitGameEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(QuitGameEventHandler handler) {
        handler.onQuitGame(this);
    }
}
