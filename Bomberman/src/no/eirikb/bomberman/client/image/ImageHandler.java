/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.bomberman.client.image;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class ImageHandler {

    private int position;

    public void loadImages(final ImageHandlerListener imageHandlerListener, final String[] urls) {
        position = 0;
        loadNext(imageHandlerListener, urls, 0);
    }

    private void loadNext(final ImageHandlerListener imageHandlerListener, final String[] urls, final int pos) {
        ImageLoader.loadImages(urls, new ImageLoader.CallBack() {

            public void onImagesLoaded(ImageElement[] imageElements) {
                for (ImageElement element : imageElements) {
                    imageHandlerListener.onDone(element.getSrc(), ++position);
                }
            }
        });
    }
}
