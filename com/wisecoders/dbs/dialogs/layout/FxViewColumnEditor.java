package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FxViewColumnEditor extends FxDbDialog implements FxUnitEditor {
  private final Column a;
  
  private final FxCommentPanel c;
  
  private final TextField d = new TextField();
  
  private final SplitMenuButton e = this.rx.g("toDo", false);
  
  private final View f;
  
  private short i = -1;
  
  public FxViewColumnEditor(WorkspaceWindow paramWorkspaceWindow, View paramView, Column paramColumn) {
    super(paramWorkspaceWindow, true);
    setDialogTitle("View Column " + String.valueOf(paramColumn));
    this.a = paramColumn;
    this.f = paramView;
    this.d.setText(paramColumn.getName());
    this.d.setEditable(false);
    this.i = paramColumn.getToDoFlag();
    this.c = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), paramColumn);
    b();
  }
  
  public Node createContentPane() {
    Dbms dbms = Dbms.get(this.f);
    if (dbms.autoLetterCases.b())
      Rx.a(this.d, dbms.getLetterCases()); 
    this.d.setText(this.a.getName());
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1 }).b(new int[] { -2, -1 }).l();
    gridPane$.a((Node)this.rx.e("nameLabel"), "0,0,r,c");
    gridPane$.a((Node)this.d, "1,0,f,c");
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,1,r,c");
    gridPane$.a((Node)this.c, "1,1,f,f");
    this.e.getItems().addAll(this.rx.e(new String[] { "toDoNo", "toDo0", "toDo1", "toDo2" }));
    this.c.a((Node)this.e);
    setInitFocusedNode((Node)this.d);
    return (Node)gridPane$;
  }
  
  public boolean validate() {
    return true;
  }
  
  public void applyChanges() {
    this.a.setComment(this.c.b());
    this.a.setCommentTags(this.c.a());
    this.a.setToDoFlag(this.i);
  }
  
  public AbstractUnit a() {
    return this.a;
  }
  
  public void saveSucceeded() {
    this.c.a(this.a);
    getWorkspace().y();
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#view");
  }
  
  @Action
  public void toDo0() {
    this.i = 0;
    b();
  }
  
  @Action
  public void toDo1() {
    this.i = 1;
    b();
  }
  
  @Action
  public void toDo2() {
    this.i = 2;
    b();
  }
  
  @Action
  public void toDoNo() {
    this.i = -1;
    b();
  }
  
  private void b() {
    switch (this.i) {
      case -1:
        this.e.setGraphic((Node)new ImageView(Rx.c("todo_no.png")));
        break;
      case 0:
        this.e.setGraphic((Node)new ImageView(Rx.c("todo0.png")));
        break;
      case 1:
        this.e.setGraphic((Node)new ImageView(Rx.c("todo1.png")));
        break;
      case 2:
        this.e.setGraphic((Node)new ImageView(Rx.c("todo2.png")));
        break;
    } 
  }
}
