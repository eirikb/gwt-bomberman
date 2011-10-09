/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import no.eirikb.bomberman.client.LobbyServiceAsync;
import no.eirikb.bomberman.client.event.JoinGameEvent;
import no.eirikb.bomberman.client.event.JoinGameEventHandler;
import no.eirikb.bomberman.game.Settings;
import no.eirikb.bomberman.client.view.GameCreateView;
import no.eirikb.bomberman.game.GameInfo;
import no.eirikb.bomberman.game.Sprite;
import no.eirikb.bomberman.game.builder.BoxBuilder;
import no.eirikb.bomberman.game.builder.BrickBuilder;
import no.eirikb.bomberman.game.builder.PowerupBuilder;
import no.eirikb.bomberman.game.builder.SpriteArrayBuilder;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GameCreatePresenter implements Presenter, GameCreateView.Presenter {

    private LobbyServiceAsync lobbyService;
    private HandlerManager eventBus;
    private GameCreateView view;

    public GameCreatePresenter(LobbyServiceAsync lobbyService, HandlerManager eventBus, GameCreateView view) {
        this.lobbyService = lobbyService;
        this.eventBus = eventBus;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(view.asWidget());
    }

    @Override
    public void onCreateButtonClicked() {
        String gameName = view.getGameName();
        if (gameName != null && gameName.length() > 0) {
            Sprite[][] sprites = SpriteArrayBuilder.createSprites();
            sprites = BoxBuilder.createBoxes(sprites);
            sprites = BrickBuilder.createBricks(sprites);
            sprites = PowerupBuilder.createPowerups(sprites);
            lobbyService.createGame(gameName, sprites, Settings.getInstance(), new AsyncCallback<GameInfo>() {

                @Override
                public void onFailure(Throwable caught) {
                    view.setInfo("ERROR: " + caught);
                }

                @Override
                public void onSuccess(GameInfo result) {
                    if (result != null) {
                        lobbyService.joinGame(result.getName(), new AsyncCallback<GameInfo>() {

                            @Override
                            public void onFailure(Throwable caught) {
                                view.setInfo("ERROR: " + caught);
                            }

                            @Override
                            public void onSuccess(GameInfo result) {
                                if (result != null) {
                                    eventBus.fireEvent(new JoinGameEvent(result));
                                } else {
                                    view.setInfo("Unkown ERROR! :O");
                                }
                            }
                        });
                    } else {
                        view.setInfo("Game name already in use");
                    }
                }
            });
        } else {
            view.setInfo("Please enter a name for you game");
        }
    }
}
