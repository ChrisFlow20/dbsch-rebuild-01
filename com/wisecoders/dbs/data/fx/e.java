package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultPosition;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.Menu$;
import com.wisecoders.dbs.sys.fx.MenuItem$;
import java.util.List;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.WindowEvent;

class e extends FxTableViewCell {
  e(FxResultTableView paramFxResultTableView, ResultColumn paramResultColumn) {
    super(paramResultColumn, true);
    ContextMenu$ contextMenu$ = new ContextMenu$();
    contextMenu$.getItems().add(new MenuItem("Blind"));
    contextMenu$.setOnShowing(paramWindowEvent -> {
          paramContextMenu$.getItems().clear();
          paramContextMenu$.getItems().addAll(this.a.a.a.e(new String[] { "clipboardCopy" }));
          Menu$ menu$ = this.a.a.a.x("clipboardCopyAs");
          menu$.getItems().addAll(this.a.a.a.e(new String[] { "clipboardCopyCST", "clipboardCopyCSV", "clipboardCopyCSP", "clipboardCopyJSON", "clipboardCopyMD", "clipboardCopyXML" }));
          paramContextMenu$.getItems().addAll((Object[])new MenuItem[] { (MenuItem)menu$, (MenuItem)new SeparatorMenuItem() });
          MenuItem$ menuItem$1 = new MenuItem$("Edit Cell", ());
          MenuItem$ menuItem$2 = new MenuItem$("Edit Cell to NULL", ());
          menuItem$1.setDisable(!getTableColumn().isEditable());
          menuItem$2.setDisable(!getTableColumn().isEditable());
          paramContextMenu$.getItems().addAll((Object[])new MenuItem[] { menuItem$1, menuItem$2 });
        });
    setContextMenu(contextMenu$);
  }
}
