package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.browse.fx.FxBrowseFkDialog;
import com.wisecoders.dbs.browse.fx.FxBrowseTableView;
import com.wisecoders.dbs.browse.store.FxBrowseManager;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultPosition;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.dialogs.system.FxTextDialog;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DataTypeUtil;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.DateConvertUtils;
import com.wisecoders.dbs.sys.fx.FxDateTimePicker;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.TooltipTableCell;
import java.io.File;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FxTableViewCell extends TooltipTableCell {
  public static final String b = "text-gray";
  
  private static final String a = "table-cell-edit";
  
  private final ResultColumn f;
  
  private final boolean g;
  
  private final List h;
  
  public FxTableViewCell(ResultColumn paramResultColumn, boolean paramBoolean) {
    this.h = new ArrayList();
    this.i = new SimpleDateFormat(Sys.B.dateFormat.c_());
    this.j = new SimpleDateFormat(Sys.B.timeFormat.c_());
    this.k = new SimpleDateFormat(Sys.B.timestampFormat.c_());
    this.l = false;
    this.f = paramResultColumn;
    this.g = paramBoolean;
    setContentDisplay(ContentDisplay.RIGHT);
    setGraphicTextGap(0.0D);
    addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2 && (paramMouseEvent.isShiftDown() || paramMouseEvent.isControlDown()))
            startEdit(); 
        });
  }
  
  public void startEdit() {
    if (!isEmpty()) {
      this.h.clear();
      this.h.add(new ResultPosition(getTableRow().getIndex(), this.f));
      super.startEdit();
      b();
    } 
  }
  
  public void commitEdit(Object paramObject) {
    getStyleClass().remove("table-cell-edit");
    if (getItem() == null) {
      if (paramObject == null || "".equals(paramObject))
        cancelEdit(); 
    } else if (getItem().equals(paramObject)) {
      cancelEdit();
      return;
    } 
    TableView tableView = getTableView();
    if (tableView instanceof FxUpdatableTableView) {
      FxUpdatableTableView fxUpdatableTableView = (FxUpdatableTableView)tableView;
      if (fxUpdatableTableView.a(this.h, paramObject)) {
        super.commitEdit(paramObject);
        getTableView().refresh();
        return;
      } 
    } 
    cancelEdit();
  }
  
  public void cancelEdit() {
    super.cancelEdit();
    a(getItem(), isSelected());
  }
  
  public void updateItem(Object paramObject, boolean paramBoolean) {
    super.updateItem(paramObject, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean)
      if (isEditing()) {
        b();
      } else {
        a(paramObject, isSelected());
      }  
  }
  
  private void b() {
    setText(null);
    setGraphic(null);
    a();
    Object object = getItem();
    getStyleClass().remove("text-gray");
    getStyleClass().add("table-cell-edit");
    Attribute attribute = this.f.a();
    if (attribute instanceof Column) {
      Column column = (Column)attribute;
      if (getTableView() instanceof FxBrowseTableView) {
        if (this.f.a().hasMarker(32)) {
          attribute = null;
          for (ForeignKey foreignKey : column.getEntity().getRelations()) {
            if (attribute == null)
              attribute = foreignKey.getTargetColumnFor(column); 
          } 
          if (attribute != null) {
            setGraphic((Node)a(((FxBrowseTableView)getTableView()).c, (Column)attribute, object));
            return;
          } 
        } 
        DataType dataType = column.getDataType();
        if (dataType instanceof UserDataType) {
          UserDataType userDataType = (UserDataType)dataType;
          if (userDataType.getEnumerationValues() != null) {
            setGraphic((Node)a(object, userDataType.getEnumerationValues()));
            return;
          } 
        } 
      } 
    } 
    if (this.f.a() instanceof Column && StringUtil.isFilledTrim(((Column)this.f.a()).getEnumeration())) {
      setGraphic((Node)a(object, StringUtil.parseLOV(((Column)this.f.a()).getEnumeration())));
    } else if (object instanceof Time || (object == null && DataTypeUtil.isTime(this.f.b))) {
      setGraphic((Node)b(object, Time.class, Sys.B.timeFormat.c_()));
    } else if (object instanceof Date || (object == null && DataTypeUtil.isDate(this.f.b))) {
      setGraphic((Node)a((Date)object, DataTypeUtil.isTimestamp(this.f.b)));
    } else if (object instanceof Boolean || (object == null && DataTypeUtil.isBoolean(this.f.b))) {
      setGraphic((Node)a((Boolean)object));
    } else if (object instanceof Number || (object == null && DataTypeUtil.isNumeric(this.f.b))) {
      setGraphic((Node)b(object, Double.class, "123..."));
    } else if (object instanceof UUID || (object == null && DataTypeUtil.isUUID(this.f.b))) {
      setGraphic((Node)b(object, UUID.class, "UUID..."));
    } else if (object instanceof java.io.InputStream || (object == null && DataTypeUtil.isBlob(this.f.b))) {
      setGraphic((Node)a(object));
    } else {
      setGraphic((Node)a(object, String.class, "New Lines CTRL+ENTER"));
    } 
    getTableRow().layout();
  }
  
  static final NumberFormat c = NumberFormat.getInstance();
  
  static final NumberFormat d = NumberFormat.getIntegerInstance();
  
  private final SimpleDateFormat i;
  
  private final SimpleDateFormat j;
  
  private final SimpleDateFormat k;
  
  public static final double e = 20.0D;
  
  private boolean l;
  
  private static final String m = "--Edit--";
  
  private void a(Object paramObject, boolean paramBoolean) {
    setText(null);
    setGraphic(null);
    getStyleClass().remove("text-gray");
    if (paramObject == null) {
      setText("NULL");
      getStyleClass().add("text-gray");
    } else if (paramObject instanceof Integer || paramObject instanceof Long || paramObject instanceof Short || paramObject instanceof Byte || paramObject instanceof java.math.BigInteger) {
      setAlignment(Pos.CENTER_RIGHT);
      setText((Sys.B.numberFormat.h() == 0) ? d.format(paramObject) : String.valueOf(paramObject));
    } else if (paramObject instanceof Number) {
      setAlignment(Pos.CENTER_RIGHT);
      setText(c.format(paramObject));
    } else if (paramObject instanceof java.sql.Timestamp) {
      setAlignment(Pos.CENTER_LEFT);
      setText(this.k.format(paramObject));
    } else if (paramObject instanceof Time) {
      setAlignment(Pos.CENTER_LEFT);
      setText(this.j.format(paramObject));
    } else if (paramObject instanceof Date) {
      setAlignment(Pos.CENTER_LEFT);
      setText(this.i.format(paramObject));
    } else if (paramObject instanceof Image) {
      setAlignment(Pos.CENTER_LEFT);
      setGraphic((Node)new ImageView((Image)paramObject));
    } else if (paramObject instanceof Boolean) {
      setAlignment(Pos.CENTER);
      CheckBox checkBox = new CheckBox();
      checkBox.setSelected(((Boolean)paramObject).booleanValue());
      checkBox.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
            paramCheckBox.setSelected(((Boolean)paramObject).booleanValue());
            startEdit();
          });
      checkBox.setAlignment(Pos.CENTER);
      setGraphic((Node)checkBox);
    } else if (paramObject instanceof java.util.Map) {
      setAlignment(Pos.CENTER_LEFT);
      setText(StringUtil.toJSON(paramObject));
    } else {
      setAlignment(Pos.CENTER_LEFT);
      String str = paramObject.toString();
      setText(this.g ? StringUtil.removeNewLine(paramObject.toString(), true) : str);
    } 
  }
  
  static {
    boolean bool = (Sys.B.numberFormat.h() == 1) ? true : false;
    NumberFormat numberFormat = c;
    if (numberFormat instanceof DecimalFormat) {
      DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
      if (bool) {
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setGroupingSize(0);
      } 
      decimalFormat.setMinimumFractionDigits(2);
      decimalFormat.setMaximumFractionDigits(10);
    } 
    if (bool) {
      numberFormat = d;
      if (numberFormat instanceof DecimalFormat) {
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setGroupingSize(0);
      } 
    } 
  }
  
  private TextArea a(Object paramObject, Class paramClass, String paramString) {
    TextArea textArea = new TextArea();
    textArea.setPromptText(paramString);
    if (paramObject != null)
      textArea.setText(String.valueOf(paramObject)); 
    this.l = false;
    textArea.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          this.l = true;
          if (paramString2 != null) {
            int i = (paramString2.split("\r\n|\r|\n")).length;
            paramTextArea.setPrefHeight(Math.min(90, i * 16 + 10));
            getTableRow().requestLayout();
          } 
        });
    textArea.setPrefWidth(2.147483647E9D);
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue() && this.l)
          commitEdit(a((Node)this, paramTextArea.getText(), paramClass)); 
      };
    textArea.addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          switch (FxTableViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              if (paramKeyEvent.isShortcutDown()) {
                paramTextArea.insertText(paramTextArea.getCaretPosition(), "\n");
                paramKeyEvent.consume();
                break;
              } 
              paramTextArea.focusedProperty().removeListener(paramChangeListener);
              if (this.l)
                commitEdit(a((Node)this, paramTextArea.getText(), paramClass)); 
              paramKeyEvent.consume();
              break;
            case 2:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    textArea.focusedProperty().addListener(changeListener);
    FxUtil.a(0.2D, paramActionEvent -> paramTextArea.requestFocus());
    return textArea;
  }
  
  private TextField b(Object paramObject, Class paramClass, String paramString) {
    TextField textField = new TextField();
    textField.setPromptText(paramString);
    textField.setPrefWidth(2.147483647E9D);
    if (paramObject != null)
      textField.setText(paramObject.toString()); 
    this.l = false;
    textField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.l = true);
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue() && this.l)
          commitEdit(a((Node)this, paramTextField.getText(), paramClass)); 
      };
    textField.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTableViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              paramTextField.focusedProperty().removeListener(paramChangeListener);
              if (this.l)
                commitEdit(a((Node)this, paramTextField.getText(), paramClass)); 
              paramKeyEvent.consume();
              break;
            case 2:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    textField.focusedProperty().addListener(changeListener);
    FxUtil.a(0.2D, paramActionEvent -> paramTextField.requestFocus());
    return textField;
  }
  
  private Button a(Object paramObject) {
    Button button = new Button("...");
    button.setPrefWidth(Double.MAX_VALUE);
    button.setOnAction(paramActionEvent -> {
          File file = FxFileChooser.a(getScene(), "Choose File", FileOperation.a, new com.wisecoders.dbs.dialogs.system.FileType[0]);
          if (file != null)
            commitEdit(file); 
        });
    return button;
  }
  
  private DatePicker a(Date paramDate, boolean paramBoolean) {
    FxDateTimePicker fxDateTimePicker = new FxDateTimePicker(paramBoolean ? Sys.B.timestampFormat.c_() : Sys.B.dateTimeFormat.c_());
    fxDateTimePicker.a(DateConvertUtils.b(paramDate));
    fxDateTimePicker.setEditable(true);
    fxDateTimePicker.setMinHeight(20.0D);
    fxDateTimePicker.setPrefHeight(20.0D);
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue() && !paramFxDateTimePicker.showingProperty().get())
          commitEdit(paramBoolean ? DateConvertUtils.b(paramFxDateTimePicker.a()) : DateConvertUtils.a(paramFxDateTimePicker.a())); 
      };
    fxDateTimePicker.focusedProperty().addListener(changeListener);
    fxDateTimePicker.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTableViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              paramFxDateTimePicker.focusedProperty().removeListener(paramChangeListener);
              commitEdit(paramBoolean ? DateConvertUtils.b(paramFxDateTimePicker.a()) : DateConvertUtils.a(paramFxDateTimePicker.a()));
              paramKeyEvent.consume();
              break;
            case 2:
              cancelEdit();
              paramKeyEvent.consume();
              break;
          } 
        });
    return fxDateTimePicker;
  }
  
  private ComboBox a(Object paramObject, List paramList) {
    ComboBox comboBox = new ComboBox();
    comboBox.setPrefWidth(2.147483647E9D);
    comboBox.getItems().addAll(paramList);
    comboBox.getItems().add("--Edit--");
    comboBox.getItems().add("NULL");
    comboBox.setValue(paramObject);
    comboBox.setOnAction(paramActionEvent -> {
          if ("--Edit--".equals(paramComboBox.getValue())) {
            Optional optional = (new FxTextDialog(getTableView().getScene().getWindow(), "Edit Cell", paramComboBox.getEditor().getText())).showAndWait();
            optional.ifPresent(this::commitEdit);
          } else {
            commitEdit(paramComboBox.getValue());
          } 
        });
    comboBox.setOnKeyReleased(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
            paramKeyEvent.consume();
          } 
        });
    comboBox.setMinHeight(20.0D);
    comboBox.setPrefHeight(20.0D);
    return comboBox;
  }
  
  private ComboBox a(Boolean paramBoolean) {
    ComboBox comboBox = new ComboBox();
    comboBox.getStyleClass().addAll((Object[])new String[] { "no-padding", "no-padding-negative" });
    comboBox.setPrefWidth(2.147483647E9D);
    comboBox.getItems().add(Boolean.TRUE);
    comboBox.getItems().add(Boolean.FALSE);
    comboBox.setValue((paramBoolean != null) ? paramBoolean : "NULL");
    comboBox.setOnAction(paramActionEvent -> commitEdit(paramComboBox.getValue()));
    comboBox.setOnKeyReleased(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ESCAPE) {
            cancelEdit();
            paramKeyEvent.consume();
          } 
        });
    comboBox.setMinHeight(20.0D);
    comboBox.setPrefHeight(20.0D);
    return comboBox;
  }
  
  protected ComboBox a(FxBrowseManager paramFxBrowseManager, Column paramColumn, Object paramObject) {
    ComboBox comboBox = new ComboBox();
    comboBox.setValue(paramObject);
    comboBox.setPrefWidth(2.147483647E9D);
    comboBox.setMinHeight(20.0D);
    comboBox.setPrefHeight(20.0D);
    comboBox.setEditable(true);
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue())
          commitEdit(a((Node)this, paramComboBox.getEditor().getText(), paramColumn.getDataType())); 
      };
    comboBox.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTableViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              paramComboBox.focusedProperty().removeListener(paramChangeListener);
              commitEdit(a((Node)this, paramComboBox.getEditor().getText(), paramColumn.getDataType()));
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
            Optional optional = (new FxBrowseFkDialog(getScene().getWindow(), paramFxBrowseManager, paramColumn)).showDialog();
            optional.ifPresent(());
          } 
        });
    return comboBox;
  }
  
  private ComboBox c(Object paramObject, Class paramClass, String paramString) {
    ComboBox comboBox = new ComboBox();
    comboBox.setPromptText(paramString);
    comboBox.setPrefWidth(2.147483647E9D);
    comboBox.setMinHeight(20.0D);
    comboBox.setPrefHeight(20.0D);
    comboBox.setEditable(true);
    if (paramObject != null)
      comboBox.getEditor().setText(paramObject.toString()); 
    ChangeListener changeListener = (paramObservableValue, paramBoolean1, paramBoolean2) -> {
        if (!paramBoolean2.booleanValue())
          commitEdit(a((Node)this, paramComboBox.getEditor().getText(), paramClass)); 
      };
    comboBox.setOnKeyPressed(paramKeyEvent -> {
          switch (FxTableViewCell$1.a[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              paramComboBox.focusedProperty().removeListener(paramChangeListener);
              commitEdit(a((Node)this, paramComboBox.getEditor().getText(), paramClass));
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
    FxUtil.a(0.2D, paramActionEvent -> paramComboBox.getEditor().requestFocus());
    return comboBox;
  }
  
  static Object a(Node paramNode, String paramString, Class<UUID> paramClass) {
    if (StringUtil.isFilled(paramString))
      try {
        Log.c("DEBUG BROWSE INSERT: convert='" + paramString + "' forClass=" + String.valueOf(paramClass));
        if (paramClass == UUID.class)
          return UUID.fromString(paramString.trim()); 
        if (paramClass == Double.class) {
          paramString = paramString.trim();
          try {
            return Integer.valueOf(Integer.parseInt(paramString));
          } catch (NumberFormatException numberFormatException) {
            try {
              return Long.valueOf(Long.parseLong(paramString));
            } catch (NumberFormatException numberFormatException1) {
              return Double.valueOf(Double.parseDouble(paramString));
            } 
          } 
        } 
        if (paramClass == Time.class) {
          paramString = paramString.trim();
          try {
            return new Time((new SimpleDateFormat(Sys.B.timeFormat.c_())).parse(paramString).getTime());
          } catch (Exception exception) {
            return Time.valueOf(paramString);
          } 
        } 
      } catch (Throwable throwable) {
        Log.b(throwable);
        (new Rx(paramNode.getClass(), paramNode)).a(paramNode.getScene(), throwable);
      }  
    return paramString;
  }
  
  static Object a(Node paramNode, String paramString, DataType paramDataType) {
    if (StringUtil.isFilled(paramString))
      try {
        Log.c("DEBUG BROWSE INSERT: convert='" + paramString + "' forDataType=" + String.valueOf(paramDataType));
        if (paramDataType.isUUID())
          return UUID.fromString(paramString.trim()); 
        if (paramDataType.isNumeric()) {
          paramString = paramString.trim();
          try {
            return Integer.valueOf(Integer.parseInt(paramString));
          } catch (NumberFormatException numberFormatException) {
            try {
              return Long.valueOf(Long.parseLong(paramString));
            } catch (NumberFormatException numberFormatException1) {
              return Double.valueOf(Double.parseDouble(paramString));
            } 
          } 
        } 
        if (paramDataType.isTime()) {
          paramString = paramString.trim();
          try {
            return new Time((new SimpleDateFormat(Sys.B.timeFormat.c_())).parse(paramString).getTime());
          } catch (Exception exception) {
            return Time.valueOf(paramString);
          } 
        } 
      } catch (Throwable throwable) {
        Log.b(throwable);
        (new Rx(paramNode.getClass(), paramNode)).a(paramNode.getScene(), throwable);
      }  
    return paramString;
  }
}
