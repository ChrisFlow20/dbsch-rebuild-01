package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.fx.ProgressBarWithText;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class CsvToolEditor extends BorderPane implements FxToolEditor {
  private final CsvEditor a;
  
  private final ProgressBarWithText b = new ProgressBarWithText();
  
  public CsvToolEditor(WorkspaceWindow paramWorkspaceWindow) {
    this.a = new CsvEditor(paramWorkspaceWindow);
    setCenter((Node)this.a);
    setTop((Node)a());
    this.b.setVisible(false);
    this.a.a.a(this.b);
  }
  
  private ToolBar a() {
    ToolBar toolBar = new ToolBar();
    toolBar.getStyleClass().add("white-tool-bar");
    MenuButton menuButton1 = this.a.a.f("file", false);
    menuButton1.getItems().addAll(this.a.a.e(new String[] { "newCSV", "openFile", "separator", "saveFile", "saveAsFile" }));
    MenuButton menuButton2 = this.a.a.f("edit", false);
    menuButton2.getItems().addAll(this.a.a.e(new String[] { "undo", "redo", "separator", "find", "findReplace" }));
    MenuButton menuButton3 = this.a.a.f("rowColumn", false);
    menuButton3.getItems().addAll(this.a.a.e(new String[] { "insertColumnLeft", "insertColumnRight", "separator", "insertRowBefore", "insertRowAfter", "separator", "deleteRow", "deleteColumn" }));
    MenuButton menuButton4 = this.a.a.f("filterAndSort", false);
    menuButton4.getItems().addAll(this.a.a.e(new String[] { "filter", "dropFilters", "separator", "sortAsc", "sortDesc" }));
    toolBar.getItems().addAll((Object[])new Node[] { (Node)menuButton1, (Node)menuButton2, (Node)menuButton3, (Node)menuButton4, (Node)this.b });
    return toolBar;
  }
  
  public Workspace getWorkspace() {
    return this.a.getWorkspace();
  }
  
  public void f() {}
  
  public AbstractUnit j() {
    return null;
  }
  
  public String h() {
    return "CSV Editor";
  }
  
  public void g() {}
  
  public boolean e() {
    return false;
  }
  
  public boolean i() {
    return false;
  }
  
  public Glyph m() {
    return null;
  }
  
  public String n() {
    return "csv.png";
  }
  
  public void a(boolean paramBoolean, int paramInt) {}
  
  public boolean o() {
    return false;
  }
  
  public void p() {}
  
  public void a(boolean paramBoolean) {}
  
  public void q() {}
}
