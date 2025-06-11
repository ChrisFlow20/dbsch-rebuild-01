package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.generator.engine.generators.Generator;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

public class FxGeneratorSamplesDialog extends ButtonDialog$ {
  private static final int a = 20;
  
  private final TableView b = new TableView();
  
  private final ObservableList c = FXCollections.observableArrayList();
  
  private final GeneratorTable d;
  
  public FxGeneratorSamplesDialog(Workspace paramWorkspace, Table paramTable) {
    super(paramWorkspace);
    this.d = new GeneratorTable(paramTable, true);
    setTitle(paramTable.getSymbolicName() + " '" + paramTable.getSymbolicName() + "' Sample Data");
    this.b.setItems(this.c);
    for (Column column : paramTable.getAttributes()) {
      TableColumn tableColumn = new TableColumn(column.getName());
      tableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(Generator.convertValueToString(((Map)paramCellDataFeatures.getValue()).get(paramColumn))));
      this.b.getColumns().add(tableColumn);
    } 
    boolean bool = false;
    for (Attribute attribute : paramTable.getAttributes()) {
      if (attribute instanceof Column && ((Column)attribute).hasChildEntity())
        bool = true; 
    } 
    try {
      this.d.initialize();
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
    if (!bool)
      for (byte b = 0; b < 20; b++) {
        try {
          this.c.add(new HashMap<>(this.d.generateValuesSet()));
        } catch (Throwable throwable) {
          Log.h();
        } 
      }  
    this.rx.b();
    showDialog();
  }
  
  @Action
  public Task generate() {
    return new FxGeneratorSamplesDialog$1(this);
  }
  
  public Node createContentPane() {
    setRegionPrefSize((Region)this.b, 1000.0D, 600.0D);
    return (Node)this.b;
  }
  
  public void createButtons() {
    createOkButton();
  }
  
  public boolean apply() {
    return true;
  }
}
