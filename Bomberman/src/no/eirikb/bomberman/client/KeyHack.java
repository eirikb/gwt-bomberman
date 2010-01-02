/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public class KeyHack implements KeyHackCallback {

    private Bomberman bomberman;
    private Long lastKeyPress;
    private Integer nativeKeyCode;
    private KeyUpEvent lastUpEvent;

    public KeyHack(Bomberman bomberman) {
        this.bomberman = bomberman;
    }

    public void arrowKeyDown(KeyDownEvent event) {
        if (nativeKeyCode != null && event.getNativeKeyCode() != nativeKeyCode) {
            lastKeyPress = null;
        }
        if (lastKeyPress == null) {
            nativeKeyCode = event.getNativeKeyCode();
            bomberman.arrowKeyDown(event);
        }
    }

    public void arrowKeyUp(KeyUpEvent event) {
        lastKeyPress = System.currentTimeMillis();
        lastUpEvent = event;
    }

    public void callback() {
        if (lastKeyPress != null && System.currentTimeMillis() - lastKeyPress > 100) {
            lastKeyPress = null;
            bomberman.arrowKeyUp(lastUpEvent);
        }
    }
}
