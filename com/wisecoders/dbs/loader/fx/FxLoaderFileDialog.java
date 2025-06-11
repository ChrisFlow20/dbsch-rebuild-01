package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.loader.model.LoaderMode;
import com.wisecoders.dbs.loader.model.MetaLoaderProject;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import org.mozilla.universalchardet.UniversalDetector;

public class FxLoaderFileDialog extends ButtonDialog$ {
  protected final WorkspaceWindow a;
  
  private final History b = new History("DataLoader");
  
  private final ComboBox c = new ComboBox();
  
  private final ComboBox d = new ComboBox();
  
  private final ComboBox e = new ComboBox();
  
  private final ComboBox f = new ComboBox();
  
  private final ComboBox i = new ComboBox();
  
  private final TextField j = new TextField();
  
  private final CheckBox k;
  
  private final RadioButton l;
  
  private final RadioButton m;
  
  private final LoaderMode n;
  
  private final Table o;
  
  private static final String p = "IMP-SCH-";
  
  private static final String q = "IMP-TABLE-";
  
  public FxLoaderFileDialog(WorkspaceWindow paramWorkspaceWindow, Table paramTable, LoaderMode paramLoaderMode) {
    super(paramWorkspaceWindow, Modality.WINDOW_MODAL);
    this.a = paramWorkspaceWindow;
    this.n = paramLoaderMode;
    this.o = paramTable;
    this.rx.G((paramLoaderMode == LoaderMode.b) ? "model" : paramWorkspaceWindow.getWorkspace().m().getDbId());
    setDialogTitleAndHeader();
    this.k = this.rx.h("truncateTableCheck", false);
    this.l = this.rx.w("radioIntoNew");
    this.m = this.rx.w("radioIntoExisting");
    this.l.setOnAction(paramActionEvent -> c());
    this.m.setOnAction(paramActionEvent -> c());
    this.i.setOnAction(paramActionEvent -> c());
    this.rx.a("flagNext", () -> 
        (StringUtil.isFilledTrim(this.d.getValue()) && (paramLoaderMode == LoaderMode.b || (this.m.isSelected() && this.i.getValue() != null) || (this.l.isSelected() && StringUtil.isFilledTrim(this.j.getText())))));
    for (Charset charset1 : Charset.availableCharsets().values())
      this.f.getItems().add(charset1); 
    Charset charset = Charset.defaultCharset();
    this.f.setValue(charset);
    new AutoCompleteComboBox(this.f);
    if (paramWorkspaceWindow.getWorkspace().m() != null) {
      for (Schema schema : (paramWorkspaceWindow.getWorkspace().m()).schemas)
        this.e.getItems().add(schema); 
      if (this.e.getItems().size() == 1)
        this.e.setValue(this.e.getItems().get(0)); 
      if ((Dbms.get(paramWorkspaceWindow.getWorkspace().m().getDbId())).autoLetterCases.b())
        Rx.a(this.j, Dbms.get(paramWorkspaceWindow.getWorkspace().m().getDbId()).getLetterCases()); 
    } 
    this.c.getItems().addAll(Dbms.getKnownDbmsExclude(new String[0]));
    this.c.setValue("MySql");
    this.d.setEditable(true);
    for (HistoryFile historyFile : this.b.c())
      this.d.getItems().add(historyFile.b.getAbsolutePath()); 
    this.d.setValue("");
    this.d.setOnAction(paramActionEvent -> {
          h();
          f();
          this.rx.b();
        });
    this.d.setCellFactory(paramListView -> new e());
    d();
    this.e.setOnAction(paramActionEvent -> d());
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.m, (ToggleButton)this.l });
    this.i.setCellFactory(paramListView -> new f());
    if (paramTable == null) {
      this.l.setSelected(true);
    } else {
      this.i.setValue(paramTable);
      this.m.setSelected(true);
    } 
    new AutoCompleteComboBox(this.i);
    this.j.addEventFilter(KeyEvent.KEY_TYPED, paramKeyEvent -> this.rx.b());
    c();
    showDialog();
  }
  
  private String b() {
    return (this.o != null) ? this.o.getDbId() : (String)this.c.getValue();
  }
  
  private void c() {
    this.i.setDisable(!this.m.isSelected());
    this.j.setDisable(!this.l.isSelected());
    this.k.setDisable(!this.m.isSelected());
    this.rx.b();
  }
  
  private void d() {
    Schema schema = (Schema)this.e.getValue();
    if (schema != null) {
      String str = StringUtil.valueOfEmptyIfNull(this.i.getValue());
      this.i.getItems().clear();
      for (Table table : schema.tables) {
        this.i.getItems().add(table);
        if (table.getName().equals(str))
          this.i.setValue(table); 
      } 
    } 
    this.rx.b();
  }
  
  private String a(String paramString) {
    return StringUtil.cutOfMiddleWithDots(paramString + paramString, 79);
  }
  
  private void e() {
    if (this.m.isSelected() && this.e.getValue() != null && this.i.getValue() != null) {
      Pref.a(a("IMP-SCH-"), ((Schema)this.e.getValue()).getNameWithCatalog());
      Pref.a(a("IMP-TABLE-"), ((Table)this.i.getValue()).getName());
    } 
  }
  
  private void f() {
    if (StringUtil.isFilledTrim(this.d.getValue()) && this.m.isSelected()) {
      String str = Pref.c(a("IMP-SCH-"), (String)null);
      Schema schema;
      if (str != null && this.a.getWorkspace().m() != null && (schema = this.a.getWorkspace().m().getSchema(str)) != null) {
        this.e.getSelectionModel().select(schema);
        String str1 = Pref.c(a("IMP-TABLE-"), (String)null);
        Table table;
        if (str1 != null && (table = schema.getTable(str1)) != null)
          this.i.getSelectionModel().select(table); 
      } 
    } 
  }
  
  public Node createContentPane() {
    this.d.setMaxWidth(Double.MAX_VALUE);
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1, -2 });
    gridPane$.a((Node)this.rx.h("inputSeparator"), "0,0,2,0,f,c");
    gridPane$.a((Node)this.rx.e("fileLabel"), "0,1,r,c");
    gridPane$.a((Node)this.d, "1,1,f,c");
    gridPane$.a((Node)this.rx.j("chooseFile"), "2,1,l,c");
    gridPane$.a((Node)this.rx.e("charsetLabel"), "0,2,r,c");
    gridPane$.a((Node)this.f, "1,2,l,c");
    if (this.n == LoaderMode.a) {
      gridPane$.a((Node)this.rx.h("targetSeparator"), "0,3,2,3,f,c");
      gridPane$.a((Node)this.rx.e("schemaLabel"), "0,4,r,c");
      gridPane$.a((Node)this.e, "1,4,l,c");
      gridPane$.a((Node)this.l, "0,5,l,c");
      gridPane$.a((Node)this.j, "1,5,f,c");
      gridPane$.a((Node)this.m, "0,6,r,c");
      gridPane$.a((Node)this.i, "1,6,f,c");
      gridPane$.a((Node)this.k, "2,6,l,c");
    } else if (this.n == LoaderMode.b) {
      gridPane$.a((Node)this.rx.e("targetLabel"), "0,3,r,c");
      gridPane$.a((Node)(new FlowPane$()).g().a(new Node[] { (Node)this.m, (Node)this.l }, ), "1,3,l,c");
      gridPane$.a((Node)this.rx.e("dbmsLabel"), "0,4,r,c");
      gridPane$.a((Node)this.c, "1,4,l,c");
      if (this.a.getWorkspace().m() == null) {
        this.l.setSelected(true);
        this.m.setDisable(true);
      } 
      this.c.disableProperty().bind((ObservableValue)this.m.selectedProperty());
    } else {
      gridPane$.a((Node)this.rx.e("dbmsLabel"), "0,3,r,c");
      gridPane$.a((Node)this.c, "1,3,l,c");
    } 
    return (Node)gridPane$;
  }
  
  @Action
  public void chooseFile() {
    File file = FxFileChooser.a(getDialogScene(), "Choose Excel, JSon, Csv or text file", FileOperation.a, new FileType[] { FileType.D });
    if (file != null)
      this.d.setValue(file.getAbsolutePath()); 
  }
  
  @Action(b = "flagNext")
  public void ok() {
    if (a()) {
      close();
      e();
      this.b.a(null, g());
      this.b.a(g());
      FileType fileType1 = FileType.a(g().getName());
      if ("MongoDb".equals(b()) && fileType1 == FileType.g)
        try {
          BufferedReader bufferedReader = new BufferedReader(new FileReader(g()));
          try {
            String str = bufferedReader.readLine();
            if (str != null && str.trim().startsWith("{"))
              fileType1 = FileType.p; 
            bufferedReader.close();
          } catch (Throwable throwable) {
            try {
              bufferedReader.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            } 
            throw throwable;
          } 
        } catch (IOException iOException) {
          Log.b(iOException);
        }  
      FileType fileType2 = fileType1;
      if (this.n == LoaderMode.b) {
        Table table;
        if (this.m.isSelected()) {
          table = MetaLoaderProject.a(this.a.getWorkspace().m());
        } else {
          table = (new MetaLoaderProject((String)this.c.getValue())).a;
        } 
        Platform.runLater(() -> new FxLoaderDialog(this.a, paramTable, false, paramFileType, g(), String.valueOf(this.f.getValue()), false, this.n));
      } else if (this.m.isSelected()) {
        Platform.runLater(() -> new FxLoaderDialog(this.a, (Table)this.i.getValue(), false, paramFileType, g(), String.valueOf(this.f.getValue()), this.k.isSelected(), this.n));
      } else {
        Schema schema = (Schema)this.e.getValue();
        Table table = schema.createTable(this.j.getText());
        Platform.runLater(() -> new FxLoaderDialog(this.a, paramTable, true, paramFileType, g(), String.valueOf(this.f.getValue()), false, this.n));
      } 
    } 
  }
  
  public void createButtons() {
    createActionButton("ok");
    createCancelButton();
    createHelpButton("import-export-model.html");
  }
  
  private File g() {
    String str = (String)this.d.getValue();
    if (StringUtil.isFilledTrim(str)) {
      File file = new File(str);
      if (file.exists())
        return file; 
    } 
    return null;
  }
  
  public boolean a() {
    Schema schema = (Schema)this.e.getValue();
    if (this.l.isSelected() && schema != null && schema.tables.getByName(this.j.getText()) != null) {
      showError("Table '" + this.j.getText() + "' already exists.");
      this.j.requestFocus();
      return false;
    } 
    if (g() == null) {
      showError("Cannot find file '" + (String)this.d.getValue() + "'.");
      this.d.requestFocus();
      return false;
    } 
    if (this.n == LoaderMode.a && this.e.getValue() == null) {
      showError("Please select a schema.");
      return false;
    } 
    return true;
  }
  
  public boolean apply() {
    return a();
  }
  
  private void h() {
    File file = g();
    if (file != null)
      try {
        String str = UniversalDetector.detectCharset(file);
        if (str != null)
          this.f.setValue(Charset.forName(str)); 
      } catch (IOException iOException) {} 
  }
}
