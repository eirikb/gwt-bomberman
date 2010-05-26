/*
 * -----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikdb@gmail.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * -----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.view;

import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import no.eirikb.bomberman.client.common.ColumnDefinition;

/**
 *
 * @author Eirik Brandtzæg <eirikdb@gmail.com>
 */
public interface GameListView<T> {

    public interface Presenter<T> {

        void onItemSelected(T selectedItem);
    }

    void setPresenter(Presenter<T> presenter);

    void setInfo(String text);

    void setColumnDefinitions(List<ColumnDefinition<T>> columnDefinitions);

    void setRowData(List<T> rowData);

    void addData(T data);

    void removeData(T data);

    Widget asWidget();
}
