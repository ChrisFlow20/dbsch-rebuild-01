package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.schema.CommentTag;
import com.wisecoders.dbs.sys.UniqueArrayList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

class c extends TableCell {
  private final ComboBox b;
  
  c(FxCommentPanel paramFxCommentPanel) {
    this.b = new ComboBox();
    this.b.setPrefWidth(Double.MAX_VALUE);
    this.b.setEditable(true);
    this.b.setOnShowing(paramEvent -> {
          this.b.getItems().clear();
          UniqueArrayList uniqueArrayList = new UniqueArrayList();
          this.a.a((String)this.a.j.getSelectionModel().getSelectedItem(), this.a.c.m(), uniqueArrayList);
          if (this.a.c.m() != null)
            for (CommentTag commentTag : Dbms.get(this.a.c.m().getDbId()).getTags()) {
              if (commentTag.getName().equals(this.a.j.getSelectionModel().getSelectedItem()) && commentTag.getCommaSeparatedValuesOrGroovyScript() != null)
                uniqueArrayList.a((Object[])commentTag.getCommaSeparatedValuesOrGroovyScript().split(",")); 
            }  
          this.b.getItems().addAll(uniqueArrayList);
        });
    this.b.setOnAction(paramActionEvent -> commitEdit(this.b.getValue()));
  }
  
  public void startEdit() {
    if (!isEmpty()) {
      super.startEdit();
      setText(null);
      setGraphic((Node)this.b);
      this.b.setValue(this.a.l.get(getTableRow().getItem()));
      Platform.runLater(() -> {
            this.b.getEditor().requestFocus();
            this.b.getEditor().selectAll();
          });
    } 
  }
  
  public void cancelEdit() {
    super.cancelEdit();
    setText((String)getItem());
    setGraphic(null);
  }
  
  public void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setGraphic(null);
    if (!paramBoolean)
      setText(paramString); 
  }
}
