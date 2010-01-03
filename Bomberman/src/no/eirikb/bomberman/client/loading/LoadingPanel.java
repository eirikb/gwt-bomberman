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
import no.eirikb.bomberman.client.game.builder.BoxBuilder;
import no.eirikb.bomberman.client.game.builder.BrickBuilder;

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
        final String[] imageUrls = {
            "bg", "bomb1", "bomb2", "bomb3", "boombrick1", "boombrick2", "boomcore1",
            "boomcore2", "boomcore3", "boomcore4", "boomenddown1", "boomenddown2",
            "boomenddown3", "boomenddown4", "boomendleft1", "boomendleft2", "boomendleft3",
            "boomendleft4", "boomendright1", "boomendright2", "boomendright3", "boomendright4",
            "boomendup1", "boomendup2", "boomendup3", "boomendup4", "boomsidehorizontal1",
            "boomsidehorizontal2", "boomsidehorizontal3", "boomsidehorizontal4", "boomsidevertical1",
            "boomsidevertical2", "boomsidevertical3", "boomsidevertical4", "box", "brick",
            "md1", "md2", "md3", "md4", "ml1", "ml2", "ml3", "ml4", "mr1", "mr2", "mr3", "mr4",
            "mu1", "mu2", "mu3", "mu4", "pu1", "pu2", "pu3", "pu4"
        };

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
