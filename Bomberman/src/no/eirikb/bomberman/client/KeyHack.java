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

    private KeyHackCallback keyHackCallback;
    private Long lastKeyPress;
    private Integer nativeKeyCode;
    private boolean anotherKeyPresses;

    public KeyHack(KeyHackCallback keyHackCallback) {
        this.keyHackCallback = keyHackCallback;
    }

    public void setAnotherKeyPresses(boolean anotherKeyPresses) {
        this.anotherKeyPresses = anotherKeyPresses;
        lastKeyPress = null;
    }

    public void arrowKeyDown(KeyDownEvent event) {
        if (nativeKeyCode != null && event.getNativeKeyCode() != nativeKeyCode) {
            lastKeyPress = null;
        }
        if (lastKeyPress == null) {
            nativeKeyCode = event.getNativeKeyCode();
            keyHackCallback.arrowKeyDown(event);
        }
    }

    public void arrowKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == nativeKeyCode) {
            if (anotherKeyPresses) {
                anotherKeyPresses = false;
                keyHackCallback.arrowKeyUp();
            } else {
                lastKeyPress = System.currentTimeMillis();
            }
        }
    }

    public void callback() {
        if (lastKeyPress != null && System.currentTimeMillis() - lastKeyPress > 100) {
            lastKeyPress = null;
            keyHackCallback.arrowKeyUp();
        }
    }

    public void arrowKeyUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
