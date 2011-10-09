package no.eirikb.bomberman.client.common;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import no.eirikb.bomberman.game.GameInfo;

public class GameListColumnDefinitions {

    List<ColumnDefinition<GameInfo>> columnDefinitions =
            new ArrayList<ColumnDefinition<GameInfo>>();

    public GameListColumnDefinitions() {
        columnDefinitions.add(new ColumnDefinition<GameInfo>() {

            @Override
            public Widget render(GameInfo gameInfo) {
                return new HTML(gameInfo.getName());
            }

            @Override
            public boolean isClickable() {
                return true;
            }
        });
    }

    public List<ColumnDefinition<GameInfo>> getColumnDefnitions() {
        return columnDefinitions;
    }
}
