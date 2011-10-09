/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.ui.game;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowCloseListener;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public final class Keyboard {

    public interface HackishKeyListener {

        void onKeyDown(char keyCode);

        void onKeyPress(char keyCode);

        void onKeyUp(char keyCode);
    }
    private static HackishKeyListener hackishKeyListener;

    private static final class WindowCloseListenerImpl implements WindowCloseListener {

        public native void onWindowClosed() /*-{ $doc.onkeydown = null; $doc.onkeypress = null; $doc.onkeyup = null; }-*/;

        public String onWindowClosing() {
            return null;
        }

        private native void init() /*-{ $doc.onkeydown = function(evt)  {
        @no.eirikb.bomberman.client.ui.game.Keyboard::onKeyDown(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event);
        }
        $doc.onkeypress = function(evt) {
        @no.eirikb.bomberman.client.ui.game.Keyboard::onKeyPress(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event);
        }
        $doc.onkeyup = function(evt) {
        @no.eirikb.bomberman.client.ui.game.Keyboard::onKeyUp(Lcom/google/gwt/user/client/Event;)(evt || $wnd.event);
        } }-*/;
    }

    static {
        WindowCloseListenerImpl closeListener = new WindowCloseListenerImpl();
        Window.addWindowCloseListener(closeListener);
        closeListener.init();
    }

    /** * Can be called from your code to force installation of * the event handling hooks. */
    public static void forceStaticInit(HackishKeyListener hackishKeyListener2) {
        hackishKeyListener = hackishKeyListener2;
    }

    private static void onKeyDown(Event event) {
        char keyCode = (char) DOM.eventGetKeyCode(event);
        hackishKeyListener.onKeyDown(keyCode);
    }

    private static void onKeyPress(Event event) {
        char keyCode = (char) DOM.eventGetKeyCode(event);
        hackishKeyListener.onKeyPress(keyCode);
    }

    private static void onKeyUp(Event event) {
        char keyCode = (char) DOM.eventGetKeyCode(event);
        hackishKeyListener.onKeyUp(keyCode);
    }

    /** * Prevent instantiation. */
    private Keyboard() {
    }
}
