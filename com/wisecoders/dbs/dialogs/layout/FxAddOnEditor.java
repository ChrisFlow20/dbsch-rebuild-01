package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.PropertyAddOn;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FxAddOnEditor extends ButtonDialog$ implements FxUnitEditor {
  private final WorkspaceWindow a;
  
  private final PropertyAddOnFolder b;
  
  private final PropertyAddOn c;
  
  private final TextField d = new TextField();
  
  private final TableView e = new TableView();
  
  public FxAddOnEditor(WorkspaceWindow paramWorkspaceWindow, PropertyAddOnFolder paramPropertyAddOnFolder) {
    this(paramWorkspaceWindow, paramPropertyAddOnFolder, (PropertyAddOn)null);
  }
  
  public FxAddOnEditor(WorkspaceWindow paramWorkspaceWindow, PropertyAddOnFolder paramPropertyAddOnFolder, PropertyAddOn paramPropertyAddOn) {
    super(paramWorkspaceWindow);
    setDialogTitle("Edit " + paramPropertyAddOnFolder.getChildrenName());
    this.a = paramWorkspaceWindow;
    this.b = paramPropertyAddOnFolder;
    this.c = paramPropertyAddOn;
    Rx.b(this.d);
    boolean bool = paramWorkspaceWindow.getWorkspace().t();
    if (paramPropertyAddOn != null) {
      this.e.getItems().addAll(paramPropertyAddOn.a());
      this.d.setDisable(true);
      this.d.setText(paramPropertyAddOn.getName());
    } 
  }
  
  public Node createContentPane() {
    TableColumn tableColumn1 = new TableColumn("Name");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    TableColumn tableColumn2 = new TableColumn("Value");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(this.c.a((String)paramCellDataFeatures.getValue())));
    this.e.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2 });
    this.e.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.e.setPrefSize(700.0D, 500.0D);
    this.d.setPrefColumnCount(20);
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1 }).b(new int[] { -2, -1 }).e().g();
    gridPane$.a((Node)this.rx.e("nameLabel"), "0,0,r,c");
    gridPane$.a((Node)this.d, "1,0,f,c");
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,1,r,c");
    gridPane$.a((Node)this.e, "0,1,1,1,f,f");
    Dbms dbms = Dbms.get(this.b.b());
    if (dbms.autoLetterCases.b()) {
      LetterCase letterCase = dbms.getLetterCases();
      Rx.a(this.d, letterCase);
    } 
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html");
  }
  
  public AbstractUnit a() {
    return this.b.c;
  }
  
  public Workspace getWorkspace() {
    return this.a.getWorkspace();
  }
  
  public boolean apply() {
    return true;
  }
}
