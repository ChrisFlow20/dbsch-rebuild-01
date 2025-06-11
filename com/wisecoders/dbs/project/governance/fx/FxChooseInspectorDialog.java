package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.governance.model.InspectorDefinition;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class FxChooseInspectorDialog extends Dialog$ {
  private final ListView a = new ListView();
  
  public FxChooseInspectorDialog(WorkspaceWindow paramWorkspaceWindow, List paramList, String paramString) {
    super(paramWorkspaceWindow);
    if (paramString != null) {
      this.rx.G(paramString);
      setDialogTitleAndHeader();
    } 
    this.rx.a("flagSelectedInspector", () -> (this.a.getSelectionModel().getSelectedItem() != null));
    this.a.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramInspectorDefinition1, paramInspectorDefinition2) -> this.rx.b());
    this.a.setPrefSize(280.0D, 200.0D);
    this.a.setCellFactory(paramListView -> new a());
    this.a.getItems().addAll(paramList);
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (InspectorDefinition)getResult() : null);
  }
  
  @Action(b = "flagSelectedInspector")
  public void ok() {
    setResult(this.a.getSelectionModel().getSelectedItem());
    hide();
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1, -2 }).a((Node)this.rx.e("nameLabel"), "0,0,r,c")
      .a((Node)this.a, "1,0,f,f");
  }
  
  public void createButtons() {
    createActionButton("ok");
    createCancelButton();
  }
  
  public boolean apply() {
    return true;
  }
}
