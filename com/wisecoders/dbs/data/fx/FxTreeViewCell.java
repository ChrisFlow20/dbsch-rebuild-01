package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.browse.fx.FxBrowseFkDialog;
import com.wisecoders.dbs.browse.store.FxBrowseManager;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.model.result.ResultPosition;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.dialogs.system.FxTextDialog;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DataTypeUtil;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.DateConvertUtils;
import com.wisecoders.dbs.sys.fx.FxDateTimePicker;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FxTreeViewCell extends TreeTableCell {
  private FxDateTimePicker c;
  
  FxTreeViewCell() {
    this.d = new SimpleDateFormat(Sys.B.dateFormat.c_());
    this.e = new SimpleDateFormat(Sys.B.timeFormat.c_());
    this.f = new SimpleDateFormat(Sys.B.timestampFormat.c_());
    addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2 && (paramMouseEvent.isShiftDown() || paramMouseEvent.isControlDown()))
            startEdit(); 
        });
  }
  
  public void startEdit() {
    if (!isEmpty()) {
      super.startEdit();
      a();
    } 
  }
  
  public void a(Tuple paramTuple) {
    super.commitEdit(paramTuple);
    if (getItem() == null && paramTuple == null)
      cancelEdit(); 
    TreeTableView treeTableView = getTreeTableView();
    if (treeTableView instanceof FxUpdatableTableView) {
      FxUpdatableTableView fxUpdatableTableView = (FxUpdatableTableView)treeTableView;
      if (paramTuple != null && fxUpdatableTableView.a(List.of(new ResultPosition(0, paramTuple.a)), paramTuple.d)) {
        super.commitEdit(paramTuple);
        return;
      } 
    } 
    cancelEdit();
  }
  
  public void cancelEdit() {
    super.cancelEdit();
    b((Tuple)getItem());
  }
  
  public void a(Tuple paramTuple, boolean paramBoolean) {
    super.updateItem(paramTuple, paramBoolean);
    setText(null);
    setGraphic(null);
    getStyleClass().remove("text-gray");
    if (!paramBoolean)
      if (isEditing()) {
        a();
      } else {
        b(paramTuple);
      }  
  }
  
  static final NumberFormat a = NumberFormat.getInstance();
  
  static final NumberFormat b = NumberFormat.getIntegerInstance();
  
  private final SimpleDateFormat d;
  
  private final SimpleDateFormat e;
  
  private final SimpleDateFormat f;
  
  private void b(Tuple paramTuple) {
    setText(null);
    setGraphic(null);
    setAlignment(Pos.CENTER_LEFT);
    setGraphic(null);
    getStyleClass().remove("text-gray");
    if (paramTuple != null)
      if (paramTuple.d == null) {
        if (paramTuple.a.c) {
          setText("Autoincrement. Leave empty.");
        } else {
          setText("NULL");
        } 
        getStyleClass().add("text-gray");
      } else if (paramTuple.d instanceof Integer || paramTuple.d instanceof Long) {
        setText(b.format(paramTuple.d));
      } else if (paramTuple.d instanceof Number) {
        setText(a.format(paramTuple.d));
      } else if (paramTuple.d instanceof java.sql.Timestamp) {
        setText(this.f.format(paramTuple.d));
      } else if (paramTuple.d instanceof Time) {
        setText(this.e.format(paramTuple.d));
      } else if (paramTuple.d instanceof Date) {
        setText(this.d.format(paramTuple.d));
      } else {
        setText(paramTuple.d.toString());
      }  
  }
  
  private void a() {
    setText(null);
    setGraphic(null);
    Tuple tuple = (Tuple)getItem();
    getStyleClass().remove("no-padding");
    if (tuple != null) {
      getStyleClass().add("no-padding");
      if (getTreeTableView() instanceof FxResultTreeView && ((FxResultTreeView)
        getTreeTableView()).c != null && tuple.a != null) {
        Attribute attribute = tuple.a.a();
        if (attribute instanceof Column) {
          Column column = (Column)attribute;
          if (tuple.a.a().hasMarker(32)) {
            attribute = null;
            for (ForeignKey foreignKey : column.getEntity().getRelations()) {
              if (attribute == null)
                attribute = foreignKey.getTargetColumnFor(column); 
            } 
            if (attribute != null) {
              setGraphic((Node)a(((FxResultTreeView)getTreeTableView()).c, (Column)attribute, tuple));
              return;
            } 
          } 
          DataType dataType = column.getDataType();
          if (dataType instanceof UserDataType) {
            UserDataType userDataType = (UserDataType)dataType;
            if (userDataType.getEnumerationValues() != null) {
              setGraphic((Node)a(tuple, userDataType.getEnumerationValues()));
              return;
            } 
          } 
        } 
      } 
      if (tuple.a != null && tuple.a.a() instanceof Column && StringUtil.isFilledTrim(((Column)tuple.a.a()).getEnumeration()))
        setGraphic((Node)a(tuple, StringUtil.parseLOV(((Column)tuple.a.a()).getEnumeration()))); 
      if (tuple.d instanceof Time || (tuple.d == null && tuple.a != null && DataTypeUtil.isTime(tuple.a.b))) {
        setGraphic((Node)a(tuple, Time.class, Sys.B.timeFormat.c_()));
      } else if (tuple.d instanceof Date || (tuple.d == null && tuple.a != null && DataTypeUtil.isDate(tuple.a.b))) {
        setGraphic((Node)b(tuple, (tuple.a != null && DataTypeUtil.isTimestamp(tuple.a.b))));
      } else if (tuple.d instanceof Boolean || (tuple.d == null && tuple.a != null && DataTypeUtil.isBoolean(tuple.a.b))) {
        setGraphic((Node)c(tuple));
      } else if (tuple.d instanceof Number || (tuple.d == null && tuple.a != null && DataTypeUtil.isNumeric(tuple.a.b))) {
        setGraphic((Node)a(tuple, Double.class, "123..."));
      } else {
        setGraphic((Node)a(tuple, String.class));
      } 
    } 
  }
  
  protected ComboBox a(FxBrowseManager paramFxBrowseManager, Column paramColumn, Tuple paramTuple) {
    ComboBox comboBox = new ComboBox();
    comboBox.setValue(paramTuple.d);
    comboBox.getStyleClass().add("no-padding");
    comboBox.setPrefWidth(2.147483647E9D);
    comboBox.setMinHeight(20.0D);
    comboBox.setPrefHeight(20.0D);
    comboBox.setEditable(true);
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue()) {
          paramTuple.a(paramComboBox.getEditor().getText());
          a(paramTuple);
        } 
      };
    comboBox.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTreeViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 2:
              paramComboBox.focusedProperty().removeListener(paramChangeListener);
              paramTuple.a(paramComboBox.getEditor().getText());
              a(paramTuple);
              paramKeyEvent.consume();
              break;
            case 1:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    comboBox.focusedProperty().addListener(changeListener);
    comboBox.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue()) {
            paramComboBox.focusedProperty().removeListener(paramChangeListener);
            Optional optional = (new FxBrowseFkDialog(getScene().getWindow(), paramFxBrowseManager, paramColumn)).showDialog();
            optional.ifPresent(());
          } 
        });
    return comboBox;
  }
  
  private TextArea a(Tuple paramTuple, Class paramClass) {
    TextArea textArea = new TextArea();
    textArea.setPromptText("New Lines CTRL+ENTER");
    textArea.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (paramString2 != null) {
            int i = (paramString2.split("\r\n|\r|\n")).length;
            paramTextArea.setPrefHeight(Math.min(90, i * 16 + 10));
            getTableRow().requestLayout();
          } 
        });
    if (paramTuple.d != null)
      textArea.setText(String.valueOf(paramTuple.d)); 
    textArea.setPrefWidth(2.147483647E9D);
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue()) {
          paramTuple.d = FxTableViewCell.a((Node)this, paramTextArea.getText(), paramClass);
          a(paramTuple);
        } 
      };
    textArea.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTreeViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 2:
              if (paramKeyEvent.isShortcutDown()) {
                paramTextArea.insertText(paramTextArea.getCaretPosition(), "\n");
                break;
              } 
              paramTextArea.focusedProperty().removeListener(paramChangeListener);
              paramTuple.a(FxTableViewCell.a((Node)this, paramTextArea.getText(), paramClass));
              a(paramTuple);
              paramKeyEvent.consume();
              break;
            case 1:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    textArea.focusedProperty().addListener(changeListener);
    FxUtil.a(0.2D, paramActionEvent -> paramTextArea.requestFocus());
    return textArea;
  }
  
  private TextField a(Tuple paramTuple, Class paramClass, String paramString) {
    TextField textField = new TextField();
    textField.setPromptText(paramString);
    textField.setPrefWidth(2.147483647E9D);
    if (paramTuple.d != null)
      textField.setText(paramTuple.d.toString()); 
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue()) {
          paramTuple.a(FxTableViewCell.a((Node)this, paramTextField.getText(), paramClass));
          a(paramTuple);
        } 
      };
    textField.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTreeViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 2:
              paramTextField.focusedProperty().removeListener(paramChangeListener);
              paramTuple.a(FxTableViewCell.a((Node)this, paramTextField.getText(), paramClass));
              a(paramTuple);
              paramKeyEvent.consume();
              break;
            case 1:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    textField.focusedProperty().addListener(changeListener);
    FxUtil.a(0.2D, paramActionEvent -> paramTextField.requestFocus());
    return textField;
  }
  
  private ComboBox b(Tuple paramTuple, Class paramClass, String paramString) {
    ComboBox comboBox = new ComboBox();
    comboBox.getStyleClass().add("no-padding");
    comboBox.setPromptText(paramString);
    comboBox.setEditable(true);
    if (paramTuple.d != null)
      comboBox.getEditor().setText(paramTuple.d.toString()); 
    comboBox.getEditor().selectAll();
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue() && !paramComboBox.showingProperty().get()) {
          paramTuple.a(FxTableViewCell.a((Node)this, paramComboBox.getEditor().getText(), paramClass));
          a(paramTuple);
        } 
      };
    comboBox.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTreeViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 2:
              paramComboBox.getEditor().focusedProperty().removeListener(paramChangeListener);
              paramTuple.a(FxTableViewCell.a((Node)this, paramComboBox.getEditor().getText(), paramClass));
              a(paramTuple);
              paramKeyEvent.consume();
              break;
            case 1:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    comboBox.getEditor().focusedProperty().addListener(changeListener);
    comboBox.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue()) {
            paramComboBox.focusedProperty().removeListener(paramChangeListener);
            Optional optional = (new FxTextDialog(getScene().getWindow(), "Edit Cell", paramComboBox.getEditor().getText())).showAndWait();
            optional.ifPresent(());
          } 
        });
    Platform.runLater(() -> paramComboBox.getEditor().requestFocus());
    return comboBox;
  }
  
  private DatePicker b(Tuple paramTuple, boolean paramBoolean) {
    this.c = new FxDateTimePicker(paramBoolean ? Sys.B.timestampFormat.c_() : Sys.B.dateTimeFormat.c_());
    this.c.getStyleClass().add("no-padding");
    this.c.a(DateConvertUtils.b((Date)paramTuple.d));
    this.c.setEditable(true);
    this.c.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTreeViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              cancelEdit();
              paramKeyEvent.consume();
              break;
            case 2:
              paramTuple.a(paramBoolean ? DateConvertUtils.b(this.c.a()) : DateConvertUtils.a(this.c.a()));
              a(paramTuple);
              paramKeyEvent.consume();
              break;
          } 
        });
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue() && !this.c.showingProperty().get()) {
          paramTuple.a(paramBoolean ? DateConvertUtils.b(this.c.a()) : DateConvertUtils.a(this.c.a()));
          a(paramTuple);
        } 
      };
    this.c.focusedProperty().addListener(changeListener);
    return this.c;
  }
  
  private ComboBox c(Tuple paramTuple) {
    ComboBox comboBox = new ComboBox();
    comboBox.getItems().add(Boolean.TRUE);
    comboBox.getItems().add(Boolean.FALSE);
    if (paramTuple.d == null) {
      comboBox.setValue("NULL");
    } else {
      comboBox.setValue(paramTuple.d);
    } 
    comboBox.setOnAction(paramActionEvent -> {
          paramTuple.a("NULL".equals(paramComboBox.getValue()) ? null : paramComboBox.getValue());
          a(paramTuple);
        });
    comboBox.setOnKeyReleased(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
            paramKeyEvent.consume();
          } 
        });
    return comboBox;
  }
  
  private ComboBox a(Tuple paramTuple, List paramList) {
    ComboBox comboBox = new ComboBox();
    comboBox.setEditable(true);
    comboBox.getItems().addAll(paramList);
    if (paramTuple.d == null) {
      comboBox.setValue("NULL");
    } else {
      comboBox.setValue(paramTuple.d);
    } 
    comboBox.setOnAction(paramActionEvent -> {
          paramTuple.a("NULL".equals(paramComboBox.getValue()) ? null : paramComboBox.getValue());
          a(paramTuple);
        });
    comboBox.setOnKeyReleased(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
            paramKeyEvent.consume();
          } 
        });
    return comboBox;
  }
}
