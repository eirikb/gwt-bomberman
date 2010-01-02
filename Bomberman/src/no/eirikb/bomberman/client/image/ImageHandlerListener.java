/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.bomberman.client.image;

import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public interface ImageHandlerListener {

    public void onStart(String url, int pos);

    public void onDone(Image image, String url, int pos);
}
