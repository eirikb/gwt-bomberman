/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.loading;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;
import no.eirikb.bomberman.client.ProgressBar;
import no.eirikb.bomberman.client.service.LobbyService;
import no.eirikb.bomberman.client.service.LobbyServiceAsync;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class LoadingPanel extends VerticalPanel {

    private Label loadingLabel;
    private Label totalLoadingLabel;
    private ProgressBar totalProgressBar;
    private LoadListener loadListener;
    private boolean done;
    private int lastLength;

    public LoadingPanel(LoadListener loadListener) {
        this.loadListener = loadListener;
        loadingLabel = new Label("Info: Loading images...");
        add(loadingLabel);
        totalLoadingLabel = new Label("Total progress: 0%");
        add(totalLoadingLabel);
        totalProgressBar = new ProgressBar(100);
        add(totalProgressBar);
    }

    public void initLoad() {
        LobbyServiceAsync lobbyService = GWT.create(LobbyService.class);

        lobbyService.getImages(new AsyncCallback<String[]>() {

            public void onFailure(Throwable caught) {
                GWT.log("Could not fetch images!", caught);
            }

            public void onSuccess(final String[] imageUrls) {
                if (imageUrls != null && imageUrls.length != lastLength) {
                    lastLength = imageUrls.length;
                    for (int i = 0; i < imageUrls.length; i++) {
                        imageUrls[i] = "../img/" + imageUrls[i];
                    }

                    ImageLoader.loadImages(imageUrls, new ImageLoader.CallBack() {

                        private int pos = 0;
                        private int percentage = 0;

                        public void onImagesLoaded(ImageElement[] imageElements) {
                            pos += imageElements.length;
                            int percentage2 = (int) (((double) pos / imageUrls.length) * 100);
                            if (percentage != percentage2) {
                                percentage = percentage2;
                                updateTotalLoading(percentage);
                                if (pos == imageUrls.length) {
                                    done = true;
                                    loadListener.complete();
                                }
                            }
                        }
                    });
                } else if (imageUrls.length == lastLength) {
                    loadListener.complete();
                } else {
                    loadingLabel.setText("Unable to fetch images! BREAKDOWN!");
                }
            }
        });
    }

    private void updateTotalLoading(final int percentage) {
        DeferredCommand.addCommand(new Command() {

            public void execute() {
                if (!done) {
                    totalLoadingLabel.setText("Total progress: " + percentage + '%');
                    totalProgressBar.setProgress(percentage);
                }
            }
        });
    }

    public boolean isDone() {
        return done;
    }
}
