package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.project.fx.FxFrame;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;

public class FxSearchField extends TextField {
  private final FxFrame a;
  
  private final ContextMenu b;
  
  public FxSearchField(FxFrame paramFxFrame) {
    this.a = paramFxFrame;
    this.b = new ContextMenu();
    getStyleClass().add("search-field");
    addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.DOWN && this.b.isShowing())
            this.b.requestFocus(); 
        });
    textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          String str = getText();
          if (str == null || str.isEmpty()) {
            this.b.getItems().clear();
            this.b.hide();
          } else {
            List list = a(str.trim());
            if (list.isEmpty()) {
              this.b.hide();
            } else {
              a(str, list);
              if (!this.b.isShowing())
                this.b.show((Node)this, Side.BOTTOM, 0.0D, 0.0D); 
            } 
          } 
        });
    setOnAction(paramActionEvent -> {
          String str = getText();
          FxLayoutPane fxLayoutPane = paramFxFrame.o();
          if (StringUtil.isFilledTrim(str) && fxLayoutPane != null)
            for (Depict depict : (paramFxFrame.o()).c.a.depicts) {
              if (depict.getEntity() != null && depict.getEntity().getName().equalsIgnoreCase(str)) {
                fxLayoutPane.c.c(depict.getEntity());
                break;
              } 
            }  
        });
    focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> this.b.hide());
  }
  
  private List a(String paramString) {
    ArrayList<Entity> arrayList = new ArrayList();
    if (this.a.o() != null)
      for (Depict depict : (this.a.o()).c.a.depicts) {
        if (depict.getEntity() != null && depict.getEntity().getName().toLowerCase().contains(paramString.toLowerCase()))
          arrayList.add(depict.getEntity()); 
      }  
    return arrayList;
  }
  
  private static TextFlow a(Entity paramEntity, String paramString) {
    String str = paramEntity.getName();
    int i = str.toLowerCase().indexOf(paramString.toLowerCase());
    Label label1 = new Label(str.substring(0, i));
    Label label2 = new Label(str.substring(i + paramString.length()));
    Label label3 = new Label(str.substring(i, i + paramString.length()));
    label3.getStyleClass().setAll((Object[])new String[] { "text-red" });
    return new TextFlow(new Node[] { (Node)label1, (Node)label3, (Node)label2 });
  }
  
  private void a(String paramString, List<Entity> paramList) {
    this.b.getItems().clear();
    byte b1 = 40;
    int i = Math.min(paramList.size(), b1);
    for (byte b2 = 0; b2 < i; b2++) {
      Entity entity = paramList.get(b2);
      Label label = new Label();
      label.setGraphic((Node)a(entity, paramString));
      label.setPrefHeight(10.0D);
      MenuItem menuItem = new MenuItem("", (Node)label);
      menuItem.setOnAction(paramActionEvent -> a(paramEntity));
      this.b.getItems().add(menuItem);
    } 
  }
  
  private void a(Entity paramEntity) {
    FxLayoutPane fxLayoutPane = this.a.o();
    if (fxLayoutPane != null)
      fxLayoutPane.c.c(paramEntity); 
    setText(paramEntity.getName());
    positionCaret(paramEntity.getName().length());
    this.b.hide();
  }
}
