package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.generator.engine.plan.Category;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.Objects;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FxCreatePatternDialog extends Dialog$ implements WorkspaceWindow {
  private final TextField a;
  
  private final TextField b;
  
  private final WorkspaceWindow c;
  
  private final Category d;
  
  private final ComboBox e = new ComboBox(Domain.b());
  
  public FxCreatePatternDialog(WorkspaceWindow paramWorkspaceWindow, Category paramCategory) {
    super(paramWorkspaceWindow);
    this.c = paramWorkspaceWindow;
    this.d = paramCategory;
    this.a = this.rx.t("patternField");
    this.b = this.rx.t("definitionField");
    this.e.setValue(paramCategory);
    this.e.setPromptText(this.rx.H("category.promptText"));
  }
  
  public Workspace getWorkspace() {
    return this.c.getWorkspace();
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l();
    gridPane$.a((Node)this.rx.e("nameLabel"), "0,0,r,c");
    gridPane$.a((Node)this.a, "1,0,f,c");
    gridPane$.a((Node)this.rx.e("categoryLabel"), "0,1,r,c");
    gridPane$.a((Node)this.e, "1,1,f,c");
    gridPane$.a((Node)this.rx.e("definitionLabel"), "0,2,r,c");
    gridPane$.a((Node)(new FlowPane$()).a(3.0D).a(new Node[] { (Node)this.b, (Node)this.rx.j("editPattern") }, ), "1,2,f,c");
    setInitFocusedNode((Node)this.a);
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    Button button = createOkButton();
    button.disableProperty().bind((ObservableValue)this.a.textProperty().isEmpty().or((ObservableBooleanValue)this.b.textProperty().isEmpty()).or((ObservableBooleanValue)this.e.itemsProperty().isNull()));
  }
  
  @Action
  public void editPattern() {
    Objects.requireNonNull(this.b);
    (new FxGeneratorPatternDialog(this, null, this.b.getText(), true)).showDialog().ifPresent(this.b::setText);
  }
  
  public boolean apply() {
    if (StringUtil.isEmpty(this.a.getText()) || StringUtil.isEmpty(this.b.getText())) {
      this.rx.c(getDialogScene(), "fillNameAndDefinition");
      return false;
    } 
    this.d.a(this.a.getText(), this.b.getText());
    return true;
  }
}
