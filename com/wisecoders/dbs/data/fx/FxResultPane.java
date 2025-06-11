package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.data.model.result.Spooler;
import com.wisecoders.dbs.data.model.result.Spooler$Format;
import com.wisecoders.dbs.data.model.result.SqlResult;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.UniqueArrayList;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.FxUtil$ResizeMode;
import com.wisecoders.dbs.sys.fx.Menu$;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class FxResultPane extends Tab {
  protected final Rx a = new Rx(FxResultPane.class, this);
  
  protected String b;
  
  protected String c;
  
  private final FxMessageCombo i = new FxResultPane$1(this);
  
  private final Label j = new Label();
  
  public final SqlResult d = new SqlResult();
  
  protected final FxAbstractSqlEditor e;
  
  private final FxResultTableView k;
  
  protected final FxResultDetailsPane f;
  
  protected final SplitPane g = new SplitPane();
  
  final HBox h = new HBox();
  
  private final TextField l = new TextField();
  
  private boolean m;
  
  private boolean n;
  
  private boolean o;
  
  private boolean p;
  
  private boolean q;
  
  protected void a(ResultRow paramResultRow) {
    if (paramResultRow == null) {
      this.f.a();
    } else {
      this.f.a(this.d, paramResultRow);
    } 
  }
  
  protected void a(Object paramObject) {
    this.f.a(paramObject);
  }
  
  public void a(Node paramNode) {
    this.h.getChildren().add(this.h.getChildren().size() - 2, paramNode);
  }
  
  @Action(b = "flagIdle")
  public void clipboardCopy() {
    a(Spooler$Format.b);
  }
  
  @Action(b = "flagIdle")
  public void clipboardCopyJSON() {
    a(Spooler$Format.e);
  }
  
  @Action(b = "flagIdle")
  public void clipboardCopyCSV() {
    a(Spooler$Format.b);
  }
  
  @Action(b = "flagIdle")
  public void clipboardCopyCST() {
    a(Spooler$Format.c);
  }
  
  @Action(b = "flagIdle")
  public void clipboardCopyCSP() {
    a(Spooler$Format.d);
  }
  
  @Action(b = "flagIdle")
  public void clipboardCopyMD() {
    a(Spooler$Format.f);
  }
  
  @Action(b = "flagIdle")
  public void clipboardCopyXML() {
    a(Spooler$Format.a);
  }
  
  private void a(Spooler$Format paramSpooler$Format) {
    int i = -1;
    if (this.k != null) {
      StringWriter stringWriter = new StringWriter();
      Spooler spooler = new Spooler(paramSpooler$Format, new PrintWriter(stringWriter));
      UniqueArrayList uniqueArrayList = new UniqueArrayList();
      for (TablePosition tablePosition : this.k.getSelectionModel().getSelectedCells())
        uniqueArrayList.add((ResultColumn)((TableColumn)this.k.getColumns().get(tablePosition.getColumn())).getUserData()); 
      spooler.a();
      for (ResultColumn resultColumn : uniqueArrayList)
        spooler.a(resultColumn.a); 
      spooler.b();
      if (paramSpooler$Format == Spooler$Format.f) {
        spooler.a();
        for (ResultColumn resultColumn : uniqueArrayList)
          spooler.b(resultColumn.a); 
        spooler.b();
      } 
      boolean bool = false;
      for (TablePosition tablePosition : this.k.getSelectionModel().getSelectedCells()) {
        if (i != tablePosition.getRow()) {
          if (i != -1)
            spooler.b(); 
          spooler.a();
        } 
        TableColumn tableColumn = (TableColumn)this.k.getColumns().get(tablePosition.getColumn());
        ResultColumn resultColumn = (ResultColumn)tableColumn.getUserData();
        if (resultColumn != null) {
          spooler.a(resultColumn.a, tableColumn.getCellData(tablePosition.getRow()));
          bool = true;
        } 
        i = tablePosition.getRow();
      } 
      if (bool) {
        spooler.b();
      } else {
        spooler.a(this.d);
      } 
      ClipboardContent clipboardContent = new ClipboardContent();
      clipboardContent.putString(stringWriter.toString());
      Clipboard.getSystemClipboard().setContent((Map)clipboardContent);
    } 
  }
  
  public void a(String paramString) {
    setText(paramString);
  }
  
  public String a() {
    return "result";
  }
  
  void b(String paramString) {
    this.i.a(paramString);
  }
  
  public void a(String paramString, Throwable paramThrowable) {
    this.i.a(paramString, paramThrowable);
  }
  
  public void c(String paramString) {
    this.c = paramString;
  }
  
  @Action(b = "flagPreviousPage")
  public Task previousPage() {
    this.d.h(-1);
    return this.e.a(this.c, this);
  }
  
  @Action(b = "flagNextPage")
  public Task nextPage() {
    this.d.h(1);
    return this.e.a(this.c, this);
  }
  
  @Action(b = "flagIdle")
  public Task refresh() {
    return this.e.a(this.c, this);
  }
  
  @Action(b = "flagIdle")
  public void resizeFitColumnName() {
    FxUtil.a(this.k, FxUtil$ResizeMode.a);
  }
  
  @Action(b = "flagIdle")
  public void resizeFitContent() {
    FxUtil.a(this.k, FxUtil$ResizeMode.b);
  }
  
  @Action(d = "flagRowNumberVisible")
  public void showRowNumber() {
    this.k.b(!this.k.c());
    this.a.b();
  }
  
  @Action(d = "flagIsCellSelectionEnabled")
  public void enableCellSelection() {
    this.k.a(!this.k.b());
    this.a.b();
  }
  
  @Action(d = "flagFilterRegExp")
  public void filterRegExp() {
    this.m = !this.m;
    this.a.b();
  }
  
  @Action(d = "flagFilterCaseSensitive")
  public void filterCaseSensitive() {
    this.n = !this.n;
    this.a.b();
  }
  
  @Action(b = "flagIdle")
  public void saveCsvComma() {
    new d(this, Spooler$Format.b);
  }
  
  @Action(b = "flagIdle")
  public void saveCsvTab() {
    new d(this, Spooler$Format.c);
  }
  
  @Action(b = "flagIdle")
  public void saveCsvPipe() {
    new d(this, Spooler$Format.d);
  }
  
  @Action(b = "flagIdle")
  public void saveXML() {
    new d(this, Spooler$Format.a);
  }
  
  @Action(b = "flagIdle")
  public void saveJSON() {
    new d(this, Spooler$Format.e);
  }
  
  @Action(b = "flagIdle")
  public void saveMD() {
    new d(this, Spooler$Format.f);
  }
  
  @Action(b = "flagIdle")
  public void saveExcel2003() {
    new d(this, Spooler$Format.h);
  }
  
  @Action(b = "flagIdle")
  public void saveExcel2007() {
    new d(this, Spooler$Format.i);
  }
  
  FxResultPane(FxAbstractSqlEditor paramFxAbstractSqlEditor, String paramString) {
    this.o = true;
    this.p = true;
    this.q = true;
    setText(paramString);
    this.e = paramFxAbstractSqlEditor;
    this.f = new FxResultDetailsPane();
    this.k = new FxResultPane$2(this, this, paramFxAbstractSqlEditor);
    this.d.i(Dbms.get(paramFxAbstractSqlEditor.d()).getSqlRecordsPerPage());
    this.d.f.addListener(paramChange -> this.k.a());
    this.a.a("flagIdle", () -> this.q);
    this.a.a("flagPreviousPage", () -> this.o);
    this.a.a("flagNextPage", () -> this.p);
    Objects.requireNonNull(this.k);
    this.a.a("flagRowNumberVisible", this.k::c);
    Objects.requireNonNull(this.k);
    this.a.a("flagIsCellSelectionEnabled", this.k::b);
    this.a.a("flagFilterRegExp", () -> this.m);
    this.a.a("flagFilterCaseSensitive", () -> this.n);
    Menu$ menu$ = this.a.x("clipboardCopyAs");
    menu$.getItems().addAll(this.a.e(new String[] { "clipboardCopyCSV", "clipboardCopyJSON", "clipboardCopyXML", "clipboardCopyMD" }));
    MenuButton menuButton1 = this.a.f("saveCsvComma", false);
    menuButton1.setText(null);
    menuButton1.getItems().add(this.a.A("clipboardCopy"));
    menuButton1.getItems().add(menu$);
    menuButton1.getItems().addAll(this.a.e(new String[] { 
            "separator", "selectAll", "separator", "saveCsvComma", "saveCsvTab", "saveCsvPipe", "saveXML", "saveMD", "saveJSON", "separator", 
            "saveExcel2003", "saveExcel2007" }));
    this.i.getStyleClass().addAll((Object[])new String[] { "monospaced", "font-larger" });
    this.l.getStyleClass().add("search-field");
    this.l.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.k.e.setPredicate(()));
    this.l.setMinWidth(120.0D);
    MenuButton menuButton2 = this.a.f("settings", false);
    menuButton2.getItems().add(this.a.y("filterRegExp"));
    menuButton2.getItems().add(this.a.y("filterCaseSensitive"));
    menuButton2.getItems().addAll((Object[])new MenuItem[] { (MenuItem)new SeparatorMenuItem(), (MenuItem)this.a.y("enableCellSelection"), (MenuItem)this.a.y("showRowNumber") });
    menuButton2.getItems().addAll(this.a.e(new String[] { "separator", "resizeFitColumnName", "resizeFitContent" }));
    this.h.getStyleClass().add("tool-bar");
    this.i.setPrefWidth(2.147483647E9D);
    HBox.setHgrow((Node)this.i, Priority.ALWAYS);
    Button button1 = this.a.j("previousPage");
    Button button2 = this.a.j("nextPage");
    menuButton1.setMinWidth(Double.NEGATIVE_INFINITY);
    button1.setMinWidth(Double.NEGATIVE_INFINITY);
    button2.setMinWidth(Double.NEGATIVE_INFINITY);
    this.j.setMinWidth(Double.NEGATIVE_INFINITY);
    Button button3 = this.a.k("refresh");
    button3.setMinWidth(Double.NEGATIVE_INFINITY);
    this.h.getChildren().addAll((Object[])new Node[] { (Node)button3, (Node)menuButton1, (Node)this.l, (Node)menuButton2, (Node)button1, (Node)this.j, (Node)button2, (Node)this.i });
    this.g.getItems().addAll((Object[])new Node[] { (Node)this.k, (Node)this.f });
    this.g.setDividerPositions(new double[] { 0.8D });
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)this.h);
    borderPane.setCenter((Node)this.g);
    setContent((Node)borderPane);
    setContextMenu((new ContextMenu$()).a(this.a, new String[] { "newResultPane" }));
  }
  
  void a(boolean paramBoolean) {
    this.q = paramBoolean;
    this.o = (paramBoolean && this.d.s() > 0);
    this.p = (paramBoolean && this.d.t());
    this.j.setText(paramBoolean ? ("Page " + this.d.s() + 1) : "");
    this.a.b();
  }
  
  @Action(b = "flagIdle")
  public void selectAll() {
    this.k.getSelectionModel().selectAll();
  }
  
  @Action
  public void newResultPane() {
    this.e.e(true);
  }
}
