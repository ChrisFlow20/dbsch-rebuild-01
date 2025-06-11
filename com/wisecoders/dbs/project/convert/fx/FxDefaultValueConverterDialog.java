package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.convert.model.ConversionDictionary;
import com.wisecoders.dbs.project.convert.model.DefaultValueConverter;
import com.wisecoders.dbs.project.convert.model.DefaultValueConverterReplacement;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.Collection;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;

public class FxDefaultValueConverterDialog extends Dialog$ implements WorkspaceWindow {
  private final ConversionDictionary a;
  
  private final Workspace b;
  
  private final boolean c;
  
  private final DefaultValueConverter d;
  
  private final ComboBox e = new ComboBox();
  
  private final TextField f = new TextField();
  
  private final CheckBox i;
  
  private final TableView j = new TableView();
  
  public FxDefaultValueConverterDialog(Workspace paramWorkspace, ConversionDictionary paramConversionDictionary) {
    this(paramWorkspace, paramConversionDictionary, (DefaultValueConverter)null);
  }
  
  public FxDefaultValueConverterDialog(Workspace paramWorkspace, DefaultValueConverter paramDefaultValueConverter) {
    this(paramWorkspace, paramDefaultValueConverter.b, paramDefaultValueConverter);
  }
  
  private FxDefaultValueConverterDialog(Workspace paramWorkspace, ConversionDictionary paramConversionDictionary, DefaultValueConverter paramDefaultValueConverter) {
    super(paramWorkspace, Modality.APPLICATION_MODAL);
    this.b = paramWorkspace;
    this.c = (paramDefaultValueConverter == null);
    this.d = (paramDefaultValueConverter != null) ? paramDefaultValueConverter : paramConversionDictionary.b();
    this.a = paramConversionDictionary;
    this.i = this.rx.v("useRegExpCheckBox");
    this.rx.a("flagSelectedReplacement", () -> (this.j.getSelectionModel().getSelectedItem() != null));
    this.e.getItems().addAll((Collection)(DbmsTypes.get(paramConversionDictionary.b)).dataTypes);
    if (paramDefaultValueConverter != null) {
      this.e.setValue(paramDefaultValueConverter.d);
      this.f.setText(paramDefaultValueConverter.c);
      this.i.setSelected(paramDefaultValueConverter.e());
    } 
  }
  
  public Workspace getWorkspace() {
    return this.b;
  }
  
  public Node createContentPane() {
    TableColumn tableColumn1 = new TableColumn("Target DBMS");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((DefaultValueConverterReplacement)paramCellDataFeatures.getValue()).a));
    tableColumn1.setEditable(false);
    TableColumn tableColumn2 = new TableColumn("Text");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty(((DefaultValueConverterReplacement)paramCellDataFeatures.getValue()).c()));
    tableColumn2.setCellFactory(TextFieldTableCell.forTableColumn());
    tableColumn2.setEditable(true);
    tableColumn2.setOnEditCommit(paramCellEditEvent -> ((DefaultValueConverterReplacement)paramCellEditEvent.getTableView().getItems().get(paramCellEditEvent.getTablePosition().getRow())).a((String)paramCellEditEvent.getNewValue()));
    tableColumn2.setGraphic(Rx.a());
    this.j.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2 });
    this.j.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.j.setEditable(true);
    this.j.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramDefaultValueConverterReplacement1, paramDefaultValueConverterReplacement2) -> this.rx.b());
    this.j.setPrefHeight(400.0D);
    this.j.setItems(this.d.f);
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -2, -2, -2, -1 }).a((Node)this.rx.e("dataTypeLabel"), "0,0,r,c")
      .a((Node)this.e, "1,0,l,c")
      .a((Node)this.i, "1,1,l,c")
      .a((Node)this.rx.e("fromLabel"), "0,2,r,c")
      .a((Node)this.f, "1,2,l,c")
      .a((Node)this.rx.h("replacementSeparator"), "0,3,1,3,f,c")
      .a((Node)this.j, "0,4,1,4,f,c")
      .a((Node)(new FlowPane$()).a().a(this.rx.c(new String[] { "addReplacement", "dropReplacement" }, )), "0,5,1,5,l,c");
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    if (this.e.getValue() == null)
      return false; 
    this.d.b(this.f.getText());
    this.d.a((DataType)this.e.getValue());
    this.d.c(this.i.isSelected());
    this.d.a(false);
    return true;
  }
  
  public boolean cancel() {
    if (this.c)
      this.a.a(this.d); 
    return true;
  }
  
  @Action
  public void addReplacement() {
    ComboBox comboBox = new ComboBox();
    comboBox.getItems().add("-Any-");
    comboBox.getItems().addAll(Dbms.getKnownDbmsExclude(new String[] { this.a.b }));
    new AutoCompleteComboBox(comboBox);
    TextField textField = new TextField();
    Dialog dialog = new Dialog();
    this.rx.a(dialog, "addReplacementDialog");
    dialog.getDialogPane().getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.OK, ButtonType.CANCEL });
    dialog.initOwner(getDialogScene().getWindow());
    GridPane$ gridPane$ = (new GridPane$()).l();
    gridPane$.a((Node)this.rx.e("targetDbmsLabel"), "0,0,r,c");
    gridPane$.a((Node)comboBox, "1,0,l,c");
    gridPane$.a((Node)this.rx.e("textLabel"), "0,1,r,c");
    gridPane$.a((Node)textField, "1,1,l,c");
    dialog.getDialogPane().setContent((Node)gridPane$);
    dialog.setResultConverter(paramButtonType -> {
          if (paramComboBox.getValue() != null) {
            DefaultValueConverterReplacement defaultValueConverterReplacement = this.d.c((String)paramComboBox.getValue());
            defaultValueConverterReplacement.a(paramTextField.getText());
            this.d.a(false);
            return defaultValueConverterReplacement;
          } 
          return null;
        });
    dialog.showAndWait().ifPresent(paramDefaultValueConverterReplacement -> {
          Objects.requireNonNull(this.j);
          Platform.runLater(this.j::refresh);
        });
  }
  
  @Action(b = "flagSelectedReplacement")
  public void dropReplacement() {
    this.d.f.removeAll((Collection)this.j.getSelectionModel().getSelectedItems());
  }
}
