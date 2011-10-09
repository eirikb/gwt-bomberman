/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import no.eirikb.bomberman.client.common.ColumnDefinition;
import no.eirikb.bomberman.client.view.GameListView.Presenter;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public class GameListViewImpl<T> extends Composite implements GameListView<T> {

    @UiTemplate("GameListView.ui.xml")
    interface GameListViewUiBinder extends UiBinder<Widget, GameListViewImpl> {
    }
    private static GameListViewUiBinder uiBinder = GWT.create(GameListViewUiBinder.class);
    @UiField
    Label infoLabel;
    @UiField
    FlexTable gameListTable;
    private Presenter<T> presenter;
    private List<ColumnDefinition<T>> columnDefinitions;
    private List<T> rowData;

    public GameListViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setColumnDefinitions(List<ColumnDefinition<T>> columnDefinitions) {
        this.columnDefinitions = columnDefinitions;
    }

    @Override
    public void setInfo(String text) {
        infoLabel.setText(text);
    }

    @Override
    public void setRowData(List<T> rowData) {
        gameListTable.removeAllRows();
        this.rowData = rowData;

        for (int i = 0; i < rowData.size(); ++i) {
            T t = rowData.get(i);
            for (int j = 0; j < columnDefinitions.size(); ++j) {
                ColumnDefinition<T> columnDefinition = columnDefinitions.get(j);
                gameListTable.setWidget(i, j, columnDefinition.render(t));
            }
        }
    }

    @Override
    public void addData(T data) {
        rowData.add(data);
        for (int i = 0; i < columnDefinitions.size(); ++i) {
            ColumnDefinition<T> columnDefinition = columnDefinitions.get(i);
            gameListTable.setWidget(rowData.size() - 1, i, columnDefinition.render(data));
        }
    }

    @Override
    public void removeData(T data) {
        int index = rowData.indexOf(data);
        if (index >= 0) {
            rowData.remove(data);
            gameListTable.removeRow(index);
        }
    }

    @UiHandler("gameListTable")
    void onTableClicked(ClickEvent event) {
        if (presenter != null) {
            EventTarget target = event.getNativeEvent().getEventTarget();
            Node node = Node.as(target);
            TableCellElement cell = findNearestParentCell(node);
            if (cell == null) {
                return;
            }

            TableRowElement tr = TableRowElement.as(cell.getParentElement());
            int row = tr.getSectionRowIndex();

            if (cell != null) {
                if (shouldFireClickEvent(cell)) {
                    if (row < rowData.size()) {
                        presenter.onItemClicked(rowData.get(row));
                    }
                }
            }
        }
    }

    private TableCellElement findNearestParentCell(Node node) {
        while ((node != null)) {
            if (Element.is(node)) {
                Element elem = Element.as(node);

                String tagName = elem.getTagName();
                if ("td".equalsIgnoreCase(tagName) || "th".equalsIgnoreCase(tagName)) {
                    return elem.cast();
                }
            }
            node = node.getParentNode();
        }
        return null;
    }

    private boolean shouldFireClickEvent(TableCellElement cell) {
        boolean shouldFireClickEvent = false;

        if (cell != null) {
            ColumnDefinition<T> columnDefinition =
                    columnDefinitions.get(cell.getCellIndex());

            if (columnDefinition != null) {
                shouldFireClickEvent = columnDefinition.isClickable();
            }
        }

        return shouldFireClickEvent;
    }

    @Override
    public Widget asWidget() {
        return this;
    }
}
