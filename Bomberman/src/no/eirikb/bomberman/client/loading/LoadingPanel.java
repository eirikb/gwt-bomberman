/*
 * =============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * =============================================================================
 */
package no.eirikb.bomberman.client.loading;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.reveregroup.gwt.imagepreloader.ImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.ImageLoadHandler;
import com.reveregroup.gwt.imagepreloader.ImagePreloader;
import no.eirikb.bomberman.client.ProgressBar;
import no.eirikb.bomberman.client.game.Settings;
import no.eirikb.bomberman.client.game.Sprite;
import no.eirikb.bomberman.client.image.ImageHandler;
import no.eirikb.bomberman.client.image.ImageHandlerListener;
import no.eirikb.bomberman.client.game.logic.BoxBuilder;
import no.eirikb.bomberman.client.game.logic.BrickBuilder;

/**
 *
 * @author Eirik Brandtæg <eirikdb@gmail.com>
 */
public class LoadingPanel extends VerticalPanel {

    private Label loadingLabel;
    private Label totalLoadingLabel;
    private ProgressBar totalProgressBar;
    private Sprite[][] sprites;
    private LoadListener loadListener;
    private Settings settings;
    private int imgSize;

    public LoadingPanel(LoadListener loadListener) {
        this.settings = Settings.getInstance();
        this.loadListener = loadListener;
        loadingLabel = new Label("Info: Loading images...");
        add(loadingLabel);
        totalLoadingLabel = new Label("Total progress: 0%");
        add(totalLoadingLabel);
        totalProgressBar = new ProgressBar(50);
        add(totalProgressBar);
    }

    public void initLoad() {
        ImagePreloader.load("img/box.png", new ImageLoadHandler() {

            public void imageLoaded(ImageLoadEvent event) {
                imgSize = event.getDimensions().getWidth();
                loadImages();
            }
        });
    }

    public void afterLoad() {
        loadingLabel.setText("Images already loaded...");
        totalProgressBar.setProgress(80);
        DeferredCommand.addCommand(new Command() {

            public void execute() {
                buildBoxes();
            }
        });
    }

    private void loadImages() {
        final String[] imageUrls = {"brick", "pu1", "boomcore4", "boomenddown3",
            "boomsidevertical4", "ml2", "md1", "boomendleft1", "boomcore3", "mu2",
            "boomsidevertical2", "boomsidehorizontal1", "boomenddown1", "boomsidehorizontal3",
            "boomcore2", "mu1", "boombrick2", "boomsidehorizontal2", "bomb3", "boombrick1",
            "md2", "bg", "boomendleft3", "boomendleft4", "mr2", "boomendright4", "boomendup2",
            "boomenddown2", "bomb1", "boomendup3", "boomendleft2", "boomenddown4", "ml3",
            "boomendright1", "boomendup4", "boomcore1", "box", "boomsidevertical3", "mr1",
            "boomsidevertical1", "boomendright2", "boomsidehorizontal4", "ml1", "bomb2",
            "md3", "mu3", "boomendright3", "boomendup1"};

        for (int i = 0; i < imageUrls.length; i++) {
            imageUrls[i] = "img/" + imageUrls[i] + ".png";
        }

        new ImageHandler().loadImages(new ImageHandlerListener() {

            public void onStart(String url, int pos) {
            }

            public void onDone(Image image, String url, int pos) {
                int percentage = (int) (((double) (pos + 2) / imageUrls.length) * 80);
                updateTotalLoading(percentage);
                if (pos == imageUrls.length - 1) {
                    DeferredCommand.addCommand(new Command() {

                        public void execute() {
                            buildBoxes();
                        }
                    });
                }
            }
        }, imageUrls);

    }

    private void buildBoxes() {
        sprites = new Sprite[settings.getMapWidth() / imgSize][settings.getMapHeight() / imgSize];
        loadingLabel.setText("Info: Building boxes...");
        sprites = BoxBuilder.createBoxes(sprites);
        updateTotalLoading(90);
        DeferredCommand.addCommand(new Command() {

            public void execute() {
                buildBricks();
            }
        });
    }

    private void buildBricks() {
        loadingLabel.setText("Info: Building bricks...");
        sprites = BrickBuilder.createBricks(sprites);
        updateTotalLoading(100);
        loadListener.complete(sprites, imgSize);
    }

    private void updateTotalLoading(int percentage) {
        totalLoadingLabel.setText("Total progress: " + percentage + '%');
        totalProgressBar.setProgress(percentage);
    }
}
