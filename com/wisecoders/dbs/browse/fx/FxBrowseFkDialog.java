package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.browse.store.FxBrowseManager;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.Dialog$;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

public class FxBrowseFkDialog extends Dialog$ {
  private final FxBrowseTableView a;
  
  public FxBrowseFkDialog(Window paramWindow, FxBrowseManager paramFxBrowseManager, Column paramColumn) {
    super(paramWindow);
    setTitle(paramColumn.getEntity().getNameWithSchemaName());
    initOwner(paramWindow);
    setResizable(true);
    this.a = new FxBrowseFkDialog$1(this, paramFxBrowseManager, paramColumn.getEntity());
    this.a.setPrefSize(550.0D, 550.0D);
    this.rx.a("flagHasPreviousPage", () -> (this.a.d.e.s() > 0));
    Objects.requireNonNull(this.a.d.e);
    this.rx.a("flagHasNextPage", this.a.d.e::t);
    setResultConverter(paramButtonType -> {
          if (paramButtonType == ButtonType.OK) {
            ResultRow resultRow = (ResultRow)this.a.getSelectionModel().getSelectedItem();
            if (resultRow != null)
              return resultRow.a(this.a.d.e.b(paramColumn)); 
          } 
          return null;
        });
  }
  
  public Node createContentPane() {
    return (Node)this.a;
  }
  
  public void createButtons() {
    createActionButton("previousPage");
    createActionButton("nextPage");
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action(b = "flagHasPreviousPage")
  public void previousPage() {
    this.a.k();
    this.rx.b();
  }
  
  @Action(b = "flagHasNextPage")
  public void nextPage() {
    this.a.h();
    this.rx.b();
  }
}
