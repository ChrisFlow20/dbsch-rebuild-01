package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.convert.model.ConversionDictionary;
import com.wisecoders.dbs.project.convert.model.DataTypeConverter;
import com.wisecoders.dbs.project.convert.model.DataTypeConverterReplacement;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

public class FxDataTypeConverterDialog extends Dialog$ implements WorkspaceWindow {
  private final WorkspaceWindow a;
  
  private final ConversionDictionary b;
  
  private final DataTypeConverter c;
  
  private final boolean d;
  
  private final Label e;
  
  private final Label f;
  
  private final Label i;
  
  private final Label j;
  
  private final ComboBox k = new ComboBox();
  
  private final ConverterRangeField l = new ConverterRangeField(DataTypeConverter.c);
  
  private final ConverterRangeField m = new ConverterRangeField(DataTypeConverter.c);
  
  private final ConverterRangeField n = new ConverterRangeField(DataTypeConverter.e);
  
  private final ConverterRangeField o = new ConverterRangeField(DataTypeConverter.e);
  
  private final TextField p = new TextField();
  
  private final TableView q = new TableView();
  
  public FxDataTypeConverterDialog(WorkspaceWindow paramWorkspaceWindow, ConversionDictionary paramConversionDictionary) {
    this(paramWorkspaceWindow, paramConversionDictionary, (DataTypeConverter)null);
  }
  
  public FxDataTypeConverterDialog(WorkspaceWindow paramWorkspaceWindow, DataTypeConverter paramDataTypeConverter) {
    this(paramWorkspaceWindow, paramDataTypeConverter.b, paramDataTypeConverter);
  }
  
  private FxDataTypeConverterDialog(WorkspaceWindow paramWorkspaceWindow, ConversionDictionary paramConversionDictionary, DataTypeConverter paramDataTypeConverter) {
    super(paramWorkspaceWindow, Modality.APPLICATION_MODAL);
    this.a = paramWorkspaceWindow;
    this.d = (paramDataTypeConverter == null);
    this.b = paramConversionDictionary;
    this.c = (paramDataTypeConverter != null) ? paramDataTypeConverter : paramConversionDictionary.a();
    this.k.getItems().setAll((Collection)DbmsTypes.get(paramConversionDictionary.b).getDataTypes());
    this.k.getItems().addAll(a(paramWorkspaceWindow.getWorkspace().m()));
    this.rx.a("flagSelectedReplacement", () -> (this.q.getSelectionModel().getSelectedItem() != null));
    if (!this.d) {
      this.k.setValue(paramDataTypeConverter.f);
      this.l.a(paramDataTypeConverter.g());
      this.m.a(paramDataTypeConverter.i());
      this.n.a(paramDataTypeConverter.h());
      this.o.a(paramDataTypeConverter.j());
      this.p.setText(paramDataTypeConverter.f());
    } 
    this.e = this.rx.e("dataTypeLabel");
    this.f = this.rx.e("precisionLabel");
    this.i = this.rx.e("decimalLabel");
    this.j = this.rx.e("optionsLabel");
    this.k.setEditable(false);
    this.k.setDisable((paramDataTypeConverter != null));
    this.k.setOnAction(paramActionEvent -> a());
    a();
  }
  
  private List a(Project paramProject) {
    ArrayList<UserDataType> arrayList = new ArrayList();
    for (Schema schema : paramProject.schemas) {
      arrayList.addAll(schema.userDataTypes);
      for (UserDataType userDataType : schema.userDataTypes) {
        for (Column column : userDataType.subTypes) {
          if (column.hasChildEntity())
            arrayList.add(new UserDataType(schema, column.getName())); 
        } 
      } 
    } 
    return arrayList;
  }
  
  private void a() {
    DataType dataType = (DataType)this.k.getValue();
    this.f.setDisable((dataType == null || !dataType.getPrecision().usesLength()));
    this.l.setDisable((dataType == null || !dataType.getPrecision().usesLength()));
    this.n.setDisable((dataType == null || !dataType.getPrecision().usesLength()));
    this.i.setDisable((dataType == null || !dataType.getPrecision().usesDecimal()));
    this.m.setDisable((dataType == null || !dataType.getPrecision().usesDecimal()));
    this.o.setDisable((dataType == null || !dataType.getPrecision().usesDecimal()));
  }
  
  public Workspace getWorkspace() {
    return this.a.getWorkspace();
  }
  
  public Node createContentPane() {
    TableColumn tableColumn1 = new TableColumn("Target DBMS");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((DataTypeConverterReplacement)paramCellDataFeatures.getValue()).a));
    tableColumn1.setEditable(false);
    TableColumn tableColumn2 = new TableColumn("Data Type");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn2.setEditable(true);
    tableColumn2.setGraphic(Rx.a());
    tableColumn2.setCellFactory(paramTableColumn -> new FxDataTypeConverterDialog$1(this));
    this.q.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2 });
    this.q.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.q.setEditable(true);
    this.q.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramDataTypeConverterReplacement1, paramDataTypeConverterReplacement2) -> this.rx.b());
    this.q.setPrefHeight(400.0D);
    this.q.setItems(this.c.g);
    this.q.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2)
            (new FxDataTypeEditConverterDialog(getDialogScene().getWindow(), this.c, (DataTypeConverterReplacement)this.q.getSelectionModel().getSelectedItem())).showDialog().ifPresent(()); 
        });
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -2, -2, -2, -2, -2, -2, -1 }).a((Node)this.rx.h("fromSeparator"), "0,0,1,0,f,c")
      .a((Node)this.e, "0,1,r,c")
      .a((Node)this.k, "1,1,l,c")
      .a((Node)this.f, "0,2,r,c")
      .a((Node)(new HBox$()).a(5.0D).a(new Node[] { (Node)this.l, (Node)new Label("-"), (Node)this.n }, ), "1,2,l,c")
      .a((Node)this.i, "0,3,r,c")
      .a((Node)(new HBox$()).a(5.0D).a(new Node[] { (Node)this.m, (Node)new Label("-"), (Node)this.o }, ), "1,3,l,c")
      .a((Node)this.j, "0,4,r,c")
      .a((Node)this.p, "1,4,l,c")
      
      .a((Node)this.rx.h("toSeparator"), "0,5,1,5,f,c")
      .a((Node)this.q, "0,6,1,6,f,c")
      .a((Node)(new FlowPane$()).a().a(this.rx.c(new String[] { "addReplacement", "editReplacement", "dropReplacement" }, )), "0,7,1,7,l,c");
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    if (this.k.getValue() == null)
      return false; 
    this.c.a((DataType)this.k.getValue());
    this.c.a(this.l.a());
    this.c.b(this.n.a());
    this.c.c(this.m.a());
    this.c.d(this.o.a());
    this.c.d(this.p.getText());
    this.c.a(false);
    return true;
  }
  
  public boolean cancel() {
    if (this.d)
      this.b.a(this.c); 
    return true;
  }
  
  @Action
  public void addReplacement() {
    (new FxDataTypeEditConverterDialog(getDialogScene().getWindow(), this.c, null)).showDialog().ifPresent(paramDataTypeConverterReplacement -> this.q.refresh());
  }
  
  @Action(b = "flagSelectedReplacement")
  public void editReplacement() {
    (new FxDataTypeEditConverterDialog(getDialogScene().getWindow(), this.c, (DataTypeConverterReplacement)this.q.getSelectionModel().getSelectedItem())).showDialog().ifPresent(paramDataTypeConverterReplacement -> this.q.refresh());
  }
  
  @Action(b = "flagSelectedReplacement")
  public void dropReplacement() {
    this.c.g.remove(this.q.getSelectionModel().getSelectedItem());
  }
}
