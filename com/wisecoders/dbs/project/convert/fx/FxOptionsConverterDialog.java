package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.convert.model.ConversionDictionary;
import com.wisecoders.dbs.project.convert.model.OptionsConverter;
import com.wisecoders.dbs.project.convert.model.OptionsConverterReplacement;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

public class FxOptionsConverterDialog extends Dialog$ implements WorkspaceWindow {
  private final ConversionDictionary a;
  
  private final Workspace b;
  
  private final boolean c;
  
  private final OptionsConverter d;
  
  private final TextField e = new TextField();
  
  private final TableView f = new TableView();
  
  public FxOptionsConverterDialog(Workspace paramWorkspace, ConversionDictionary paramConversionDictionary) {
    this(paramWorkspace, paramConversionDictionary, (OptionsConverter)null);
  }
  
  public FxOptionsConverterDialog(Workspace paramWorkspace, OptionsConverter paramOptionsConverter) {
    this(paramWorkspace, paramOptionsConverter.b, paramOptionsConverter);
  }
  
  private FxOptionsConverterDialog(Workspace paramWorkspace, ConversionDictionary paramConversionDictionary, OptionsConverter paramOptionsConverter) {
    super(paramWorkspace, Modality.APPLICATION_MODAL);
    this.b = paramWorkspace;
    this.c = (paramOptionsConverter == null);
    this.d = (paramOptionsConverter != null) ? paramOptionsConverter : paramConversionDictionary.c();
    this.a = paramConversionDictionary;
    this.rx.a("flagSelectedReplacement", () -> (this.f.getSelectionModel().getSelectedItem() != null));
    if (paramOptionsConverter != null) {
      this.e.setText(paramOptionsConverter.e());
      this.e.setEditable(false);
    } 
  }
  
  public Workspace getWorkspace() {
    return this.b;
  }
  
  public Node createContentPane() {
    TableColumn tableColumn1 = new TableColumn("Target DBMS");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((OptionsConverterReplacement)paramCellDataFeatures.getValue()).a));
    tableColumn1.setEditable(false);
    TableColumn tableColumn2 = new TableColumn("Text");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty(((OptionsConverterReplacement)paramCellDataFeatures.getValue()).c()));
    tableColumn2.setEditable(true);
    this.f.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2 });
    this.f.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.f.setEditable(true);
    this.f.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramOptionsConverterReplacement1, paramOptionsConverterReplacement2) -> this.rx.b());
    this.f.setPrefHeight(400.0D);
    this.f.setItems(this.d.c);
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -2, -2, -2, -1 }).a((Node)this.rx.e("fromLabel"), "0,0,r,c")
      .a((Node)this.e, "1,0,l,c")
      .a((Node)this.rx.h("replacementSeparator"), "0,1,1,1,f,c")
      .a((Node)this.f, "0,2,1,2,f,c")
      .a((Node)(new FlowPane$()).a().a(this.rx.c(new String[] { "addReplacement", "dropReplacement" }, )), "0,3,1,3,l,c");
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    this.d.b(this.e.getText());
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
            OptionsConverterReplacement optionsConverterReplacement = this.d.c((String)paramComboBox.getValue());
            optionsConverterReplacement.a(paramTextField.getText());
            this.d.a(false);
            return optionsConverterReplacement;
          } 
          return null;
        });
    dialog.showAndWait().ifPresent(paramOptionsConverterReplacement -> {
          Objects.requireNonNull(this.f);
          Platform.runLater(this.f::refresh);
        });
  }
  
  @Action(b = "flagSelectedReplacement")
  public void dropReplacement() {
    this.d.c.remove(this.f.getSelectionModel().getSelectedItem());
  }
}
