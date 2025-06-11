package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.fx.FxDiagramPane;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Line;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class FxLineEditor extends ButtonDialog$ implements FxUnitEditor {
  private final Workspace a;
  
  private final Line b;
  
  private final TextField c = new TextField();
  
  public FxLineEditor(WorkspaceWindow paramWorkspaceWindow, Line paramLine) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramWorkspaceWindow.getWorkspace();
    this.b = paramLine;
    this.rx.b();
    if (paramLine != null)
      this.c.setText(paramLine.getName()); 
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1 });
    gridPane$.a((Node)this.rx.e("nameLabel"), "0,0,r,c");
    gridPane$.a((Node)this.c, "1,0,f,f");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean a() {
    return true;
  }
  
  public boolean apply() {
    if (!a())
      return false; 
    if (this.a.o() != null) {
      FxDiagramPane fxDiagramPane = (this.a.o()).c;
      this.b.rename(this.c.getText());
      this.a.u();
    } 
    return true;
  }
  
  public Unit getUnit() {
    return this.b;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
