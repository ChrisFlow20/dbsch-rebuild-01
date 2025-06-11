package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.fx.FxDiagramPane;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.ShapeStyle;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FxColorPicker;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FxShapeEditor extends ButtonDialog$ implements FxUnitEditor {
  private final Workspace a;
  
  private final Shape b;
  
  private final ComboBox c = new ComboBox();
  
  private final FxColorPicker d = new FxColorPicker();
  
  private final TextArea e = new TextArea();
  
  public FxShapeEditor(WorkspaceWindow paramWorkspaceWindow, Shape paramShape) {
    super(paramWorkspaceWindow.getWorkspace());
    this.a = paramWorkspaceWindow.getWorkspace();
    this.b = paramShape;
    this.rx.b();
    this.d.a(this.a.m());
    this.c.getItems().addAll((Object[])ShapeStyle.values());
    this.c.setValue(ShapeStyle.a);
    if (paramShape != null) {
      this.e.setText(paramShape.getComment());
      this.c.setValue(paramShape.b());
      this.d.a(paramShape.c());
    } 
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1 });
    gridPane$.a((Node)this.rx.e("description"), "0,0,r,c");
    gridPane$.a((Node)this.e, "1,0,f,f");
    gridPane$.a((Node)this.rx.e("styleLabel"), "0,1,r,c");
    gridPane$.a((Node)this.c, "1,1,l,c");
    gridPane$.a((Node)this.rx.e("colorLabel"), "0,2,r,c");
    gridPane$.a((Node)this.d, "1,2,l,c");
    gridPane$.setPrefHeight(370.0D);
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean a() {
    String str = this.e.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showError("Please set the comment text.");
      this.e.requestFocus();
      return false;
    } 
    return true;
  }
  
  public boolean apply() {
    if (!a())
      return false; 
    if (this.a.o() != null) {
      FxDiagramPane fxDiagramPane = (this.a.o()).c;
      Shape shape = this.b;
      if (shape == null) {
        shape = fxDiagramPane.b(this.e.getText(), fxDiagramPane.v());
      } else {
        shape.setComment(this.e.getText());
      } 
      shape.a((ShapeStyle)this.c.getValue());
      shape.a(this.d.b());
      this.a.u();
      fxDiagramPane.k();
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
