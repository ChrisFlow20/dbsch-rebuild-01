package com.wisecoders.dbs.data.filter.fx;

import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import javafx.scene.Node;

public class FxFilterEditor extends ButtonDialog$ {
  private final FxFilterPanel a;
  
  public FxFilterEditor(WorkspaceWindow paramWorkspaceWindow, Filterable paramFilterable, Attribute paramAttribute, String paramString) {
    super(paramWorkspaceWindow.getWorkspace());
    setHeaderText("Set filter on '" + String.valueOf(paramAttribute) + "'");
    this

      
      .a = paramAttribute.getDataType().isDate() ? new FxDateFilterPanel(paramFilterable, paramAttribute, paramString) : (paramAttribute.getDataType().isBoolean() ? new FxBooleanFilterPanel(paramFilterable, paramAttribute, paramString) : (paramAttribute.getDataType().isNumeric() ? new FxNumericFilterPanel(paramFilterable, paramAttribute, paramString) : new FxTextFilterPanel(paramFilterable, paramAttribute, paramString)));
    this.a.g();
  }
  
  public Node createContentPane() {
    return (Node)this.a;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    try {
      this.a.b();
    } catch (Throwable throwable) {
      this.rx.b(getDialogScene(), throwable.getLocalizedMessage(), throwable);
    } 
    return true;
  }
}
