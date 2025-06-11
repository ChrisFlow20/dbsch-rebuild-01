package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Node;

public class FxCalloutEditor extends ButtonDialog$ implements FxUnitEditor {
  private final Workspace a;
  
  private final Unit b;
  
  private final Unit c;
  
  private final FxCommentPanel d;
  
  public FxCalloutEditor(WorkspaceWindow paramWorkspaceWindow, Unit paramUnit) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramWorkspaceWindow.getWorkspace();
    this.b = paramUnit;
    this.d = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), false, paramUnit, true);
    String str = null;
    Unit unit = null;
    if (paramUnit instanceof Callout) {
      Callout callout = (Callout)paramUnit;
      if (callout.c != null) {
        unit = callout.c;
        str = callout.c.getSymbolicName() + " " + callout.c.getSymbolicName();
      } 
    } else if (paramUnit != null) {
      str = paramUnit.getName();
      unit = paramUnit;
    } 
    if (str != null)
      setDialogTitle(getTitle() + " on " + getTitle()); 
    this.c = unit;
    this.rx.a("flagHasOwner", () -> (this.c != null));
    this.rx.b();
    Objects.requireNonNull(this.d.a);
    Platform.runLater(this.d.a::requestFocus);
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1 });
    gridPane$.a((Node)this.rx.e("description"), "0,0,r,c");
    gridPane$.a((Node)this.d, "1,0,f,f");
    gridPane$.setPrefHeight(330.0D);
    return (Node)gridPane$;
  }
  
  @Action(b = "flagHasOwner")
  public void editOwner() {
    close();
    FxEditorFactory.a(getWorkspace(), this.c);
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean a() {
    String str = this.d.b();
    if (StringUtil.isEmptyTrim(str)) {
      showError("Please set the comment text.");
      this.d.a.requestFocus();
      return false;
    } 
    return true;
  }
  
  public boolean apply() {
    if (!a())
      return false; 
    this.d.a(this.b);
    return true;
  }
  
  public Unit getUnit() {
    return this.b;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
