package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.model.OptionsProperty;
import com.wisecoders.dbs.config.model.SyntaxOption;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;

public class FxSyntaxOptionPane extends SplitPane {
  public final StyledEditor a = new StyledEditor(Language.a, false);
  
  private final Workspace b;
  
  private final ListView c = new ListView();
  
  public FxSyntaxOptionPane(Workspace paramWorkspace, OptionsProperty paramOptionsProperty) {
    this.b = paramWorkspace;
    setOrientation(Orientation.HORIZONTAL);
    Rx rx = new Rx(FxSyntaxOptionPane.class, this);
    rx.a("flagEnabled", () -> (paramOptionsProperty != null && paramOptionsProperty.h() != null));
    rx.a("flagSelectedOption", () -> (this.c.getSelectionModel().getSelectedItem() != null));
    this.a.setPrefWidth(500.0D);
    getItems().add(this.a);
    if (paramOptionsProperty != null && paramOptionsProperty.h() != null && paramOptionsProperty.h().a()) {
      paramOptionsProperty.h().a(false);
      this.c.setCellFactory(paramListView -> new d(this));
      this.c.getItems().addAll(paramOptionsProperty.h().b());
      this.c.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramSyntaxOption1, paramSyntaxOption2) -> paramRx.b());
      GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1 }).b(new int[] { -2, -1 });
      gridPane$.a((Node)rx.e("rightTitle"), "0,0,1,0,c,c");
      Button button = rx.j("add");
      button.setMinWidth(Double.NEGATIVE_INFINITY);
      gridPane$.a((Node)button, "0,1,l,c");
      gridPane$.a((Node)this.c, "1,1,f,f");
      getItems().add(gridPane$);
      setResizableWithParent((Node)gridPane$, Boolean.valueOf(false));
      gridPane$.setPrefWidth(400.0D);
    } 
    rx.b();
  }
  
  @Action(b = "flagSelectedOption")
  public void add() {
    for (SyntaxOption syntaxOption : this.c.getSelectionModel().getSelectedItems()) {
      syntaxOption.a(true);
      String str = syntaxOption.e();
      if (StringUtil.isFilled(str))
        this.a.d((this.a.t().endsWith("\n") ? "" : "\n") + (this.a.t().endsWith("\n") ? "" : "\n")); 
    } 
  }
}
