package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.FxDbDialog;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.loader.engine.AbstractLoader;
import com.wisecoders.dbs.loader.engine.CSVLoader;
import com.wisecoders.dbs.loader.model.LoaderColumn;
import com.wisecoders.dbs.loader.model.LoaderError;
import com.wisecoders.dbs.loader.model.LoaderErrorSample;
import com.wisecoders.dbs.loader.model.LoaderMeta;
import com.wisecoders.dbs.loader.model.LoaderMode;
import com.wisecoders.dbs.loader.tasks.LoaderPresetSeparators;
import com.wisecoders.dbs.loader.tasks.LoaderPreviewTask;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.WrappedTableCell;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.apache.commons.csv.CSVFormat;

public class FxLoaderDialog extends FxDbDialog {
  private final LoaderMeta f;
  
  protected final WorkspaceWindow a;
  
  private final File i;
  
  private final String j;
  
  private final boolean k;
  
  private final CheckBox l;
  
  private final ComboBox m = new ComboBox();
  
  public final ComboBox c = new ComboBox();
  
  private final ComboBox n = new ComboBox();
  
  public final ComboBox d = new ComboBox();
  
  private final TabPane o = new TabPane();
  
  public final TableView e = new TableView();
  
  private final TableView p = new TableView();
  
  private final TableView q = new TableView();
  
  private final Label r;
  
  private final FileType s;
  
  private CSVFormat.Builder t = CSVFormat.DEFAULT.builder();
  
  private final LoaderMode u;
  
  private final TableColumn v;
  
  private final TableColumn w;
  
  private final TableColumn x;
  
  private Connector y;
  
  FxLoaderDialog(WorkspaceWindow paramWorkspaceWindow, Table paramTable, boolean paramBoolean1, FileType paramFileType, File paramFile, String paramString, boolean paramBoolean2, LoaderMode paramLoaderMode) {
    super(paramWorkspaceWindow, true);
    this.v = new TableColumn("Error");
    this.w = new TableColumn("Occurrences");
    this.x = new TableColumn("Line");
    this.a = paramWorkspaceWindow;
    this.i = paramFile;
    this.j = paramString;
    this.f = new LoaderMeta(paramTable, paramBoolean1);
    this.s = paramFileType;
    this.k = paramBoolean2;
    this.l = this.rx.h("firstLineHeader", true);
    this.u = paramLoaderMode;
    this.rx.G((paramLoaderMode == LoaderMode.b) ? "model" : paramWorkspaceWindow.getWorkspace().m().getDbId());
    setDialogTitleAndHeader();
    a a = new a("Excel", CSVFormat.EXCEL);
    this.m.getItems().addAll((Object[])new a[] { 
          new a("Custom", CSVFormat.DEFAULT), a, new a("Oracle", CSVFormat.ORACLE), new a("MySql", CSVFormat.MYSQL), new a("PostgreSQL CSV", CSVFormat.POSTGRESQL_CSV), new a("PostgreSQL Text", CSVFormat.POSTGRESQL_TEXT), new a("MongoDb CSV", CSVFormat.MONGODB_CSV), new a("MongoDb TSV", CSVFormat.MONGODB_TSV), new a("Informix Unload", CSVFormat.INFORMIX_UNLOAD), new a("Informix Unload CSV", CSVFormat.INFORMIX_UNLOAD_CSV), 
          new a("Rfc4180", CSVFormat.RFC4180) });
    this.m.setValue(a);
    this.m.setOnAction(paramActionEvent -> {
          a(((a)this.m.getValue()).b.builder());
          refreshPreview();
        });
    this.r = this.rx.e("sheetLabel");
    this.r.setVisible(false);
    this.n.setVisible(false);
    if (paramLoaderMode == LoaderMode.a && (paramWorkspaceWindow.getWorkspace().m()).schemas.isEmpty()) {
      showError("Project should contain at least one schema");
      return;
    } 
    for (Locale locale : Locale.getAvailableLocales())
      this.c.getItems().add(locale); 
    this.c.setValue(Locale.getDefault());
    new AutoCompleteComboBox(this.c);
    for (String str : LoaderPresetSeparators.a)
      this.d.getItems().add(str); 
    this.d.setValue(LoaderPresetSeparators.a[0]);
    this.d.setEditable(true);
    c();
    d();
    this.p.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramLoaderError1, paramLoaderError2) -> this.q.setItems((paramLoaderError2 != null) ? paramLoaderError2.c : null));
    this.l.setOnAction(paramActionEvent -> refreshPreview());
    Dialog$.setRegionPrefSize((Region)this.o, 900.0D, 500.0D);
    this.rx.a(b());
    showDialog();
  }
  
  private Task b() {
    return new FxLoaderDialog$1(this, getDialogScene(), this.rx, this.i, f());
  }
  
  private void c() {
    TableColumn tableColumn1 = new TableColumn("File Column");
    TableColumn tableColumn2 = new TableColumn(this.rx.H("dbColumn"));
    TableColumn tableColumn3 = new TableColumn("Warnings");
    this.e.setItems(this.f.a);
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn1.setCellFactory(paramTableColumn -> new d());
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn2.setCellFactory(paramTableColumn -> new c(this));
    tableColumn2.setEditable(false);
    tableColumn2.setGraphic(Rx.a());
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((LoaderColumn)paramCellDataFeatures.getValue()).c()));
    tableColumn3.setCellFactory(paramTableColumn -> new WrappedTableCell());
    this.e.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2, tableColumn3 });
    Rx.a(this.e, new double[] { 0.35D, 0.35D, 0.3D });
    this.e.setEditable(true);
  }
  
  private void d() {
    this.p.setItems(this.f.b);
    this.p.setPlaceholder((Node)this.rx.e("errorTablePlaceholder"));
    this.q.setPlaceholder((Node)this.rx.e("sampleErrorTablePlaceholder"));
    this.v.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((LoaderError)paramCellDataFeatures.getValue()).a));
    this.v.setCellFactory(paramTableColumn -> new WrappedTableCell());
    this.w.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper("" + ((LoaderError)paramCellDataFeatures.getValue()).b));
    this.p.getColumns().addAll((Object[])new TableColumn[] { this.v, this.w });
    this.p.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
  
  private void e() {
    this.q.getColumns().clear();
    this.x.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper("" + ((LoaderErrorSample)paramCellDataFeatures.getValue()).a));
    this.q.getColumns().addAll((Object[])new TableColumn[] { this.x });
    for (Iterator<LoaderColumn> iterator = this.f.a.iterator(); iterator.hasNext(); ) {
      LoaderColumn loaderColumn = iterator.next();
      if (loaderColumn.a() != null) {
        TableColumn tableColumn = new TableColumn(loaderColumn.a().getName());
        tableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(String.valueOf(((LoaderErrorSample)paramCellDataFeatures.getValue()).b.get(paramLoaderColumn))));
        this.q.getColumns().add(tableColumn);
      } 
    } 
  }
  
  private AbstractLoader f() {
    switch (FxLoaderDialog$6.a[this.s.ordinal()]) {
      case 1:
      
      case 2:
      
      case 3:
      
      case 4:
      
    } 
    return 





      
      new CSVLoader(h(), this.i, this.j);
  }
  
  private boolean g() {
    if (this.d.getValue() == null) {
      showError("Please configure the date format.");
      return false;
    } 
    if (this.c.getValue() == null) {
      showError("Please configure the locale.");
      return false;
    } 
    try {
      String str = ((String)this.d.getValue()).trim();
      this.f.a(new SimpleDateFormat(str, (Locale)this.c.getValue()));
    } catch (IllegalArgumentException illegalArgumentException) {
      showError("Illegal date format. " + String.valueOf(illegalArgumentException));
      return false;
    } 
    return true;
  }
  
  @Action
  public void refreshPreview() {
    if (g())
      this.rx.a(new LoaderPreviewTask(this, this.f, f())); 
  }
  
  @Action
  public void editFormat() {
    (new FxLoaderFormatDialog(this, this.t)).showDialog().ifPresent(paramBuilder -> this.t = paramBuilder);
    refreshPreview();
  }
  
  private CSVFormat.Builder h() {
    if (this.l.isSelected()) {
      this.t.setHeader(new String[0]).setSkipHeaderRecord(true).setAllowMissingColumnNames(true);
    } else {
      try {
        this.t.setHeader((ResultSet)null);
      } catch (SQLException sQLException) {}
      this.t.setSkipHeaderRecord(false);
    } 
    return this.t;
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1, -2 }).l();
    if (this.s == FileType.g) {
      gridPane$.a((Node)this.rx.e("formatLabel"), "0,0,r,c");
      gridPane$.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.m, (Node)this.rx.j("editFormat") }, ), "1,0,l,c");
    } else {
      setManagedVisible(new Node[] { (Node)this.r, (Node)this.n });
      gridPane$.a((Node)this.r, "0,0,r,c");
      gridPane$.a((Node)this.n, "1,0,l,c");
    } 
    gridPane$.a((Node)this.l, "1,1,l,c");
    gridPane$.a((Node)this.rx.e("localeLabel"), "0,2,r,c");
    gridPane$.a((Node)this.c, "1,2,l,c");
    gridPane$.a((Node)this.rx.e("dateFormatLabel"), "0,3,r,c");
    gridPane$.a((Node)this.d, "1,3,l,c");
    gridPane$.a((Node)this.rx.j("refreshPreview"), "2,3,r,c");
    this.o.getTabs().add(new Tab(this.rx.H("columnSelection"), (Node)this.e));
    VBox$.setVgrow((Node)this.q, Priority.ALWAYS);
    VBox$ vBox$ = (new VBox$()).b(3).c(new Node[] { (Node)this.rx.e("samplesTitle"), (Node)this.q });
    SplitPane splitPane = new SplitPane(new Node[] { (Node)this.p, (Node)vBox$ });
    splitPane.setOrientation(Orientation.VERTICAL);
    this.o.getTabs().add(new Tab("Import Errors", (Node)splitPane));
    Rx.a(this.o);
    setRegionPrefSize((Region)this.o, 1000.0D, 600.0D);
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)gridPane$);
    borderPane.setCenter((Node)this.o);
    return (Node)borderPane;
  }
  
  public void createButtons() {
    createActionButton("ok");
    createCancelButton();
    createHelpButton("import-export-model.html");
  }
  
  @Action
  public Task ok() {
    this.f.b();
    e();
    if (this.f.a.isEmpty()) {
      showError("There is no data to load.");
      return null;
    } 
    if (!i()) {
      showError("Model has no columns set where to import data. Click the table header to set.");
      return null;
    } 
    if (this.u == LoaderMode.a) {
      this.y = this.a.getWorkspace().a(this);
      if (this.y == null)
        return null; 
      if (this.f.g() && !this.y.isMongo())
        return new FxLoaderDialog$2(this, this.a, this.f.d, this.y); 
    } 
    return createLoadTask();
  }
  
  private boolean i() {
    for (LoaderColumn loaderColumn : this.f.a) {
      if (loaderColumn.a() != null)
        return true; 
    } 
    return false;
  }
  
  @Action
  public Task createLoadTask() {
    this.f.e();
    if (g()) {
      this.o.getSelectionModel().select(1);
      if (this.u == LoaderMode.a)
        return new FxLoaderDialog$3(this, this.a, this.f, f(), this.y, this.k); 
      if (this.u == LoaderMode.b)
        return new FxLoaderDialog$4(this, this.f, f()); 
      if (this.u == LoaderMode.c)
        return new FxLoaderDialog$5(this, this.f, f()); 
    } 
    return null;
  }
  
  public boolean cancel() {
    j();
    close();
    return true;
  }
  
  private void j() {
    if (this.f.g()) {
      this.f.d.markForDeletion();
      this.f.d.schema.refresh();
    } 
  }
  
  public boolean validate() {
    return true;
  }
  
  public Unit getUnit() {
    return null;
  }
  
  public CSVFormat.Builder a() {
    return this.t;
  }
  
  public void a(CSVFormat.Builder paramBuilder) {
    if (paramBuilder != null)
      this.t = paramBuilder; 
  }
  
  public void a(List paramList) {
    this.n.getItems().setAll(paramList);
    if (!paramList.isEmpty())
      this.n.getSelectionModel().select(0); 
    if (paramList.size() > 1) {
      this.n.setVisible(true);
      this.r.setVisible(true);
    } 
    this.n.setOnAction(paramActionEvent -> refreshPreview());
  }
  
  public void applyChanges() {}
  
  public void saveSucceeded() {}
}
