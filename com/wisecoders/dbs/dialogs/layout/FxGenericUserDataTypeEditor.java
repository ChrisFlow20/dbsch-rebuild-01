package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.VBox$;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;

public class FxGenericUserDataTypeEditor extends Dialog$ {
  private final WorkspaceWindow a;
  
  private final Schema b;
  
  private final boolean c;
  
  private final RadioButton d;
  
  private final RadioButton e;
  
  public FxGenericUserDataTypeEditor(WorkspaceWindow paramWorkspaceWindow, Schema paramSchema, boolean paramBoolean) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow;
    this.b = paramSchema;
    this.c = paramBoolean;
    this.d = this.rx.i("radioSimple", true);
    this.e = this.rx.i("radioHierarchical", false);
    this.d.getStyleClass().add("font-xl");
    this.e.getStyleClass().add("font-xl");
    (new ToggleGroup$(new javafx.scene.control.ToggleButton[0])).getToggles().addAll((Object[])new Toggle[] { (Toggle)this.e, (Toggle)this.d });
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (FxUnitEditor)getResult() : null);
  }
  
  public Node createContentPane() {
    return (Node)(new VBox$()).k().c(new Node[] { (Node)this.d, (Node)this.e });
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    setResult(this.e.isSelected() ? new FxTableEditor(this.a, this.b, UserDataType.class, this.c) : new FxUserDataTypeEditor(this.a, this.b));
    return true;
  }
}
