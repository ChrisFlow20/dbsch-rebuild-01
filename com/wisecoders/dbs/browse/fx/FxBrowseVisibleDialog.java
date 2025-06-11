package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.Dialog$;
import java.util.Collection;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Region;
import javafx.stage.WindowEvent;

public class FxBrowseVisibleDialog extends Dialog$ {
  private final ListView a = new ListView();
  
  private final FxBrowseFrame b;
  
  FxBrowseVisibleDialog(FxBrowseFrame paramFxBrowseFrame) {
    super(paramFxBrowseFrame.getScene().getWindow());
    this.b = paramFxBrowseFrame;
    setTitle("'" + String.valueOf(paramFxBrowseFrame.h) + "' Visible Columns");
    setRegionPrefSize((Region)this.a, 400.0D, 400.0D);
    this.a.setCellFactory(paramListView -> new b());
    this.a.getItems().addAll((Collection)paramFxBrowseFrame.i.getColumns());
    getDialogScene().getWindow().setOnCloseRequest(paramWindowEvent -> getDialogScene().getWindow());
  }
  
  public Node createContentPane() {
    return (Node)this.a;
  }
  
  public void createButtons() {
    createActionButton("showAll");
    createActionButton("hideAll");
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  private static String a(TableColumn paramTableColumn) {
    return (paramTableColumn instanceof FxBrowseTableViewColumn) ? ((FxBrowseTableViewColumn)paramTableColumn).a.c() : paramTableColumn.getText();
  }
  
  private static void a(TableColumn paramTableColumn, boolean paramBoolean) {
    paramTableColumn.setVisible(paramBoolean);
    String str = a(paramTableColumn);
    if (paramTableColumn instanceof FxBrowseTableViewColumn)
      str = ((FxBrowseTableViewColumn)paramTableColumn).a.b(); 
    Pref.a("browseVisible" + str, paramBoolean);
  }
  
  @Action
  public void showAll() {
    this.b.i.getColumns().forEach(paramTableColumn -> a(paramTableColumn, true));
    Objects.requireNonNull(this.a);
    Platform.runLater(this.a::refresh);
  }
  
  @Action
  public void hideAll() {
    this.b.i.getColumns().forEach(paramTableColumn -> a(paramTableColumn, false));
    Objects.requireNonNull(this.a);
    Platform.runLater(this.a::refresh);
  }
}
