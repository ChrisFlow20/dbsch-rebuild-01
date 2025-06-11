package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.dialogs.system.FxTextDialog;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class b extends TableCell {
  private b(CsvEditorTableColumn paramCsvEditorTableColumn) {}
  
  public void startEdit() {
    if (!isEmpty()) {
      super.startEdit();
      b((String)getItem());
    } 
  }
  
  public void a(String paramString) {
    if (getItem() == null) {
      if (paramString == null || "".equals(paramString))
        cancelEdit(); 
    } else if (((String)getItem()).equals(paramString)) {
      cancelEdit();
      return;
    } 
    super.commitEdit(paramString);
  }
  
  public void cancelEdit() {
    super.cancelEdit();
    c((String)getItem());
  }
  
  protected void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setText(null);
    setGraphic(null);
    if (isEditing()) {
      b(paramString);
    } else if (!paramBoolean && getTableRow() != null) {
      c(paramString);
    } 
  }
  
  private void b(String paramString) {
    setText(null);
    if (this.a.b().c()) {
      setGraphic((Node)a(paramString));
    } else {
      setGraphic((Node)d(paramString));
    } 
  }
  
  private void c(String paramString) {
    setGraphic(null);
    setText(paramString);
  }
  
  private TextField d(String paramString) {
    TextField textField = new TextField(paramString);
    textField.setMinWidth(getWidth() - getGraphicTextGap() * 2.0D);
    textField.setOnKeyPressed(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ENTER) {
            a(paramTextField.getText());
            paramKeyEvent.consume();
          } else if (paramKeyEvent.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
            this.a.a.c.a.refresh();
          } 
        });
    textField.focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (!paramBoolean2.booleanValue())
            a(paramTextField.getText()); 
        });
    return textField;
  }
  
  private ComboBox a(Object paramObject) {
    ComboBox comboBox = new ComboBox();
    comboBox.setPrefWidth(2.147483647E9D);
    comboBox.setEditable(true);
    if (paramObject != null)
      comboBox.getEditor().setText(paramObject.toString()); 
    comboBox.getEditor().selectAll();
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue())
          a(paramComboBox.getEditor().getText()); 
      };
    comboBox.setOnKeyPressed(paramKeyEvent -> {
          switch (CsvEditorTableColumn$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              paramComboBox.focusedProperty().removeListener(paramChangeListener);
              a(paramComboBox.getEditor().getText());
              paramKeyEvent.consume();
              break;
            case 2:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    comboBox.focusedProperty().addListener(changeListener);
    comboBox.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue()) {
            paramComboBox.focusedProperty().removeListener(paramChangeListener);
            Optional optional = (new FxTextDialog(getTableView().getScene().getWindow(), "Edit Cell", paramComboBox.getEditor().getText())).showAndWait();
            optional.ifPresent(());
          } 
        });
    comboBox.setMinWidth(getWidth() - getGraphicTextGap() * 2.0D);
    return comboBox;
  }
}
