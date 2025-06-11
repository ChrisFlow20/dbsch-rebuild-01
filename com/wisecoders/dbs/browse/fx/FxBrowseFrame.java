package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.browse.flow.FxFlowFrame;
import com.wisecoders.dbs.browse.model.BrowseDetailResult;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.browse.model.Cascade;
import com.wisecoders.dbs.data.fx.FxResultTreeView;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultPosition;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.data.model.result.ResultStatus;
import com.wisecoders.dbs.data.model.result.Spooler$Format;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.sql.fx.FxJsonInsertEditor;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.BlobInputStream;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Pro;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.tips.tips.Tips;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.PopOver;

public class FxBrowseFrame extends FxFlowFrame {
  public final BrowseTable h;
  
  final FxBrowseTableView i;
  
  private final FxResultTreeView j;
  
  private final Rx k = new Rx(FxBrowseFrame.class, this);
  
  private final Button l;
  
  private final ContextMenu m = new ContextMenu();
  
  private final Label n = new Label();
  
  private boolean o = true;
  
  private final FxBrowseEditor p;
  
  private final SplitMenuButton q = new SplitMenuButton();
  
  private final ContextMenu$ r;
  
  private final SimpleBooleanProperty s = new SimpleBooleanProperty(false);
  
  FxBrowseFrame(FxBrowseEditor paramFxBrowseEditor, FxBrowseFrame paramFxBrowseFrame, BrowseTable paramBrowseTable) {
    super(paramFxBrowseEditor.c);
    this.v = false;
    this.p = paramFxBrowseEditor;
    this.h = paramBrowseTable;
    this.j = new FxBrowseFrame$1(this, paramFxBrowseEditor.d, paramBrowseTable);
    this.i = new FxBrowseFrame$2(this, paramFxBrowseEditor.d, paramBrowseTable, paramFxBrowseEditor);
    this.i.setEditable(true);
    this.i.setRowFactory(paramTableView -> {
          TableRow tableRow = new TableRow();
          tableRow.setOnMouseClicked(());
          return tableRow;
        });
    this.i.focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue())
            Platform.runLater(()); 
        });
    this.j.focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue())
            Platform.runLater(()); 
        });
    a(paramBrowseTable.e.k() ? (Node)this.j : (Node)this.i);
    this.i.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramResultRow1, paramResultRow2) -> {
          ObservableList observableList = this.i.getSelectionModel().getSelectedIndices();
          Integer[] arrayOfInteger = (Integer[])observableList.toArray((Object[])new Integer[0]);
          if (arrayOfInteger.length > 0) {
            paramBrowseTable.e.a(arrayOfInteger);
            this.j.a(paramBrowseTable.e, paramBrowseTable.e.e(arrayOfInteger[0].intValue()));
          } else {
            this.j.setRoot(null);
          } 
          if (paramBrowseTable.e.j() == ResultStatus.d)
            this.i.a(Cascade.c); 
          ResultRow resultRow = (ResultRow)this.i.getSelectionModel().getSelectedItem();
          if (this.o && resultRow != null && resultRow.a())
            c(true); 
          this.o = false;
          h();
        });
    this.k.G(paramFxBrowseEditor.a.a.project.getDbId());
    this.k.a("flagHasData", () -> (paramBrowseTable.e.g() && (paramBrowseTable.e.j() == ResultStatus.d || paramBrowseTable.e.j() == ResultStatus.c)));
    this.k.a("flagLoaded", () -> (paramBrowseTable.e.j() == ResultStatus.d));
    this.k.a("flagPreviousPage", () -> (paramBrowseTable.e.s() > 0));
    this.k.a("flagPreviousRecord", () -> (paramBrowseTable.e.m() > 0));
    this.k.a("flagNextRecord", () -> (paramBrowseTable.e.n() < paramBrowseTable.e.A() - 1));
    Objects.requireNonNull(paramBrowseTable.e);
    this.k.a("flagNextPage", paramBrowseTable.e::t);
    Objects.requireNonNull(paramBrowseTable.e);
    this.k.a("flagOneRecordView", paramBrowseTable.e::k);
    this.k.a("flagUnlocked", () -> (paramBrowseTable.e.j() != ResultStatus.c && paramBrowseTable.e.j() != ResultStatus.b));
    this.k.a("flagCellEditable", () -> (paramBrowseTable.e.j() == ResultStatus.d && this.i.b() != null));
    this.k.a("flagColumnSelected", () -> (this.i.b() != null));
    this.q.setText(paramBrowseTable.getEntity().getName());
    this.q.setPadding(new Insets(0.0D));
    this.q.getStyleClass().add("transparent-split-menu-button");
    this.q.getItems().addAll((Object[])new MenuItem[] { (MenuItem)this.k.y("switchDisplay"), this.k.A("visibleColumns") });
    b((Node)this.q);
    Button button1 = this.k.k("insertRecordCommit");
    button1.visibleProperty().bind((ObservableValue)this.s);
    Dialog$.setManagedVisible(new Node[] { (Node)button1 });
    Button button2 = this.k.k("insertRecordCommitAddMore");
    button2.visibleProperty().bind((ObservableValue)this.s);
    Dialog$.setManagedVisible(new Node[] { (Node)button2 });
    Button button3 = this.k.k("insertRecordCancel");
    button3.visibleProperty().bind((ObservableValue)this.s);
    Dialog$.setManagedVisible(new Node[] { (Node)button3 });
    Button button4 = this.k.k("insertRecord");
    button4.visibleProperty().bind((ObservableValue)this.s.not());
    Dialog$.setManagedVisible(new Node[] { (Node)button4 });
    Button button5 = this.k.k("deleteRecord");
    button5.visibleProperty().bind((ObservableValue)this.s.not());
    Dialog$.setManagedVisible(new Node[] { (Node)button5 });
    this.c.getChildren().addAll((Object[])new Node[] { 
          (Node)button1, (Node)button2, (Node)button3, (Node)button4, (Node)button5, (Node)new HGrowBox$(), (Node)this.q, (Node)(this.l = this.k.k("join")), (Node)new HGrowBox$(), (Node)b(), 
          (Node)a() });
    this.d.getChildren().addAll((Object[])new Node[] { (Node)new HGrowBox$(), (Node)this.k.k("previousPage"), (Node)this.k.k("previousRecord"), (Node)this.n, (Node)this.k.k("nextRecord"), (Node)this.k.k("nextPage"), (Node)this.k.k("refreshWindow"), (Node)new HGrowBox$(), (Node)this.f });
    this.l.setContextMenu(this.m);
    m();
    setLayoutX(paramBrowseTable.k());
    setLayoutY(paramBrowseTable.l());
    this.e.setPrefWidth(paramBrowseTable.m());
    this.e.setPrefHeight(paramBrowseTable.n());
    this.r = (new ContextMenu$()).b(true).a(this.k, new String[] { 
          "join", "separator", "filter", "removeFilter", "removeAllFilters", "separator", "detailView", "editCell", "editCellToNull", "uploadCellFromFile", 
          "copyCellValue", "separator", "insertRecord", "insertRecordDuplicate", "deleteRecord", "separator", "resize", "resizeAll", "separator", "refreshWindow", 
          "maximizeMinimize" });
    this.c.setOnContextMenuRequested(paramContextMenuEvent -> {
          this.r.show((Node)this, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
          (new ContextMenu$()).a(this.k, t).show((Node)this, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
        });
    addEventFilter(MouseEvent.MOUSE_ENTERED, paramMouseEvent -> j());
    addEventFilter(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> {
          this.r.hide();
          this.i.e();
        });
    this.a.a(this);
    a(paramFxBrowseFrame);
  }
  
  private void m() {
    ContextMenu contextMenu = new ContextMenu();
    this.q.setContextMenu(contextMenu);
    this.q.setOnContextMenuRequested(paramContextMenuEvent -> {
          this.i.e();
          paramContextMenu.getItems().clear();
          paramContextMenu.setAutoHide(true);
          paramContextMenu.getItems().addAll(this.k.e(new String[] { "refreshWindow", "join", "removeAllFilters", "separator", "insertRecord", "deleteRecord" }));
          Menu menu = new Menu("Export Data...");
          menu.getItems().addAll(this.k.e(new String[] { "saveCsvComma", "saveCsvTab", "saveCsvPipe", "saveXml", "saveExcel2003", "saveExcel2007" }));
          paramContextMenu.getItems().add(menu);
          paramContextMenu.getItems().addAll(this.k.e(new String[] { "setAlias", "removeAllFilters", "separator", "maximizeMinimize" }));
          paramContextMenu.show((Node)this.q, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
        });
    this.i.setContextMenu(this.r);
    this.i.setOnContextMenuRequested(paramContextMenuEvent -> {
          this.i.e();
          this.r.show((Node)this.i, paramContextMenuEvent.getScreenX(), paramContextMenuEvent.getScreenY());
        });
  }
  
  private static final String[] t = new String[0];
  
  void h() {
    this.n.setText(this.h.e.l());
    this.k.b();
  }
  
  void a(BrowseTable paramBrowseTable) {
    FxBrowseFrame fxBrowseFrame = b(paramBrowseTable);
    for (BrowseTable browseTable : paramBrowseTable.d())
      fxBrowseFrame.a(browseTable); 
  }
  
  FxBrowseFrame b(BrowseTable paramBrowseTable) {
    return new FxBrowseFrame(this.p, this, paramBrowseTable);
  }
  
  public void f() {
    if (this.h.e.j() == ResultStatus.e)
      switch (FxBrowseFrame$6.a[this.k.b(getScene(), "commitInsertedRecord", new String[0]).c().ordinal()]) {
        case 1:
          insertRecordCommit();
          break;
        case 2:
          break;
        default:
          return;
      }  
    super.f();
    this.h.markForDeletion();
    this.p.a.a.refresh();
    this.p.k();
  }
  
  @Action
  public void visibleColumns() {
    (new FxBrowseVisibleDialog(this)).showDialog((Node)this);
  }
  
  @Pro
  @Action
  public void join() {
    this.i.e();
    this.m.getItems().clear();
    this.m.getItems().addAll(i());
    this.m.show((Node)this.l, Side.TOP, 0.0D, 0.0D);
  }
  
  List i() {
    ArrayList<MenuItem> arrayList = new ArrayList();
    if (License.a().e()) {
      for (Relation relation : this.h.c.getImportedRelations()) {
        if (!(relation instanceof com.wisecoders.dbs.schema.ChildEntityRelation)) {
          MenuItem menuItem = new MenuItem("Cascade " + relation.getEntity().getName() + " via " + relation.getName(), (Node)BootstrapIcons.arrow_90deg_right.glyph(new String[] { "glyph-blue" }));
          menuItem.setMnemonicParsing(false);
          menuItem.setOnAction(paramActionEvent -> {
                this.p.l();
                BrowseTable browseTable = this.h.a(paramRelation, true);
                FxBrowseFrame fxBrowseFrame = b(browseTable);
                fxBrowseFrame.i.a(Cascade.b);
                FxUtil.a((Node)fxBrowseFrame);
                fxBrowseFrame.i.requestFocus();
                this.p.getWorkspace().u();
              });
          arrayList.add(menuItem);
        } 
      } 
      boolean bool = true;
      for (Iterator<Relation> iterator = this.h.c.getRelations().iterator(); iterator.hasNext(); ) {
        Relation relation = iterator.next();
        if (!(relation instanceof com.wisecoders.dbs.schema.ChildEntityRelation)) {
          MenuItem menuItem = new MenuItem("Cascade " + relation.getTargetEntity().getName() + " from " + relation.getEntity().getName() + " via " + relation.getName(), (Node)BootstrapIcons.arrow_90deg_left.glyph(new String[] { "glyph-orange" }));
          menuItem.setMnemonicParsing(false);
          menuItem.setOnAction(paramActionEvent -> {
                this.p.l();
                BrowseTable browseTable = this.h.a(paramRelation, false);
                FxBrowseFrame fxBrowseFrame = b(browseTable);
                fxBrowseFrame.i.a(Cascade.b);
                FxUtil.a((Node)fxBrowseFrame);
                fxBrowseFrame.i.requestFocus();
                this.p.getWorkspace().u();
              });
          if (bool && !arrayList.isEmpty())
            arrayList.add(new SeparatorMenuItem()); 
          arrayList.add(menuItem);
          bool = false;
        } 
      } 
      if (arrayList.isEmpty())
        arrayList.add(new MenuItem(this.k.H("noFksToChildren"))); 
    } else {
      arrayList.add(new MenuItem(this.k.H("requirePro")));
    } 
    return arrayList;
  }
  
  @Action(b = "flagNextPage")
  public void nextPage() {
    this.i.h();
  }
  
  @Action(b = "flagNextRecord")
  public void nextRecord() {
    this.i.i();
  }
  
  @Action(b = "flagPreviousRecord")
  public void previousRecord() {
    this.i.j();
  }
  
  @Action(b = "flagPreviousPage")
  public void previousPage() {
    this.i.k();
  }
  
  @Action(b = "flagUnlocked", d = "flagOneRecordView")
  public void switchDisplay() {
    this.h.e.b(!this.h.e.k());
    a(this.h.e.k() ? (Node)this.j : (Node)this.i);
    this.k.b();
  }
  
  private void c(boolean paramBoolean) {
    this.h.e.b(paramBoolean);
    a(paramBoolean ? (Node)this.j : (Node)this.i);
    this.k.b();
  }
  
  @Action
  public Task insertRecordCommit() {
    return new FxBrowseFrame$CommitRecordTask(this, false);
  }
  
  @Action
  public Task insertRecordCommitAddMore() {
    return new FxBrowseFrame$CommitRecordTask(this, true);
  }
  
  @Action(b = "flagLoaded")
  public void insertRecordDuplicate() {
    a(true);
    this.s.set(true);
  }
  
  @Action(b = "flagCellEditable")
  public void editCell() {
    if (this.h.c()) {
      this.k.d(getScene(), "Ups ! Data cannot be edited. The table has no Primary Key or Unique Index.\nEditing data may lead to multiple row modifications.");
    } else {
      Platform.runLater(() -> this.i.edit(this.i.getSelectionModel().getSelectedIndex(), this.i.b()));
    } 
  }
  
  @Action(b = "flagLoaded")
  public void copyCellValue() {
    ResultRow resultRow = (ResultRow)this.i.getSelectionModel().getSelectedItem();
    if (resultRow != null && this.i.c() != null) {
      Object object = resultRow.a((this.i.c()).a);
      if (object != null) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(String.valueOf(object));
        clipboard.setContent((Map)clipboardContent);
      } 
    } 
  }
  
  @Action(b = "flagCellEditable")
  public void editCellToNull() {
    this.i.edit(-1, null);
    if (this.h.c()) {
      this.k.c(getScene(), "Ups ! Data cannot be edited. The table has no Primary Key or Unique Index.\nEditing data may lead to multiple row modifications.");
      return;
    } 
    if (this.i.c() != null) {
      ResultColumn resultColumn = (this.i.c()).a;
      if (resultColumn.a().isMandatory()) {
        this.k.a(getScene(), "Ups ! This column is mandatory ( NOT NULL ) and cannot make it empty !", new String[0]);
      } else {
        a(List.of(new ResultPosition(this.h.e.m(), resultColumn)), true, (Object)null);
      } 
    } 
  }
  
  @Action(b = "flagLoaded")
  public void insertRecord() {
    if (this.h.c.getSchema().is(UnitProperty.f).booleanValue()) {
      new FxJsonInsertEditor(this.p.getWorkspace(), this.p.d(), this.h.c);
      this.i.a(Cascade.b);
    } else {
      a(false);
      this.s.set(true);
    } 
  }
  
  public void a(boolean paramBoolean) {
    this.h.e.a(paramBoolean);
    this.h.e.a(ResultStatus.e, Cascade.a);
    this.i.a(2147483647);
    this.j.a(this.h.e, this.h.e.e(this.h.e.A() - 1));
  }
  
  @Action
  public void insertRecordCancel() {
    this.i.a(Cascade.b);
    c(false);
    this.s.set(false);
  }
  
  @Action(b = "flagLoaded")
  public void deleteRecord() {
    if (this.h.c() && !this.h.c.getSchema().is(UnitProperty.f).booleanValue()) {
      this.k.c(getScene(), "missingPkToEdit");
      return;
    } 
    if (this.k.a(getScene(), this.h.e.k() ? "Delete current record from the database ?" : "Delete selected record(s) from the database ?")) {
      Envoy envoy = this.p.a("Browse delete record");
      if (envoy != null) {
        FxBrowseFrame$3 fxBrowseFrame$3 = new FxBrowseFrame$3(this, envoy);
        (new Thread((Runnable)fxBrowseFrame$3)).start();
      } 
    } 
  }
  
  private boolean a(List paramList, boolean paramBoolean, Object paramObject) {
    boolean bool = true;
    if (paramBoolean && this.p.a.e()) {
      String str = MessageFormat.format("Update {0} cell(s) to {1} ?", new Object[] { Integer.valueOf(paramList.size()), (paramObject == null) ? "NULL" : StringUtil.cutOfWithDots(StringUtil.removeNewLine(paramObject.toString(), true), 40) });
      bool = this.k.a(getScene(), this.k.H("confirmUpdates.header"), str, "confirmUpdates");
    } 
    if (bool) {
      this.p.k();
      Envoy envoy = this.p.a("Browse update cell");
      if (envoy != null) {
        FxBrowseFrame$4 fxBrowseFrame$4 = new FxBrowseFrame$4(this, paramList, paramObject, envoy);
        (new Thread((Runnable)fxBrowseFrame$4)).start();
      } 
      return true;
    } 
    return false;
  }
  
  @Action(b = "flagColumnSelected")
  public void detailView() {
    if (this.i.c() != null) {
      ResultColumn resultColumn = (this.i.c()).a;
      new FxBrowseDetailsDialog(this.p.getWorkspace(), this.i, resultColumn.a());
    } 
  }
  
  public void a(Attribute paramAttribute, String paramString, boolean paramBoolean) {
    if (paramString == null)
      paramString = this.k.b(getScene(), "Which kind of document does this column store ?\nPlease enter the document extension ( doc, xls, html, gif, ...)."); 
    if (paramString != null && paramAttribute != null) {
      BrowseDetailResult browseDetailResult = new BrowseDetailResult(this.h, paramAttribute, paramString, paramBoolean);
      this.h.a(browseDetailResult);
      a(paramAttribute, browseDetailResult);
      this.i.a(Cascade.b);
    } 
  }
  
  public FxBrowseDetailFrame a(Attribute paramAttribute, BrowseDetailResult paramBrowseDetailResult) {
    FxBrowseDetailFrame fxBrowseDetailFrame = new FxBrowseDetailFrame(this.p, this.h, paramAttribute, paramBrowseDetailResult);
    this.a.a(fxBrowseDetailFrame);
    fxBrowseDetailFrame.a(this);
    return fxBrowseDetailFrame;
  }
  
  @Action(b = "flagLoaded")
  public void saveCsvComma() {
    a(Spooler$Format.b);
  }
  
  @Action(b = "flagLoaded")
  public void saveCsvTab() {
    a(Spooler$Format.c);
  }
  
  @Action(b = "flagLoaded")
  public void saveCsvPipe() {
    a(Spooler$Format.d);
  }
  
  @Action(b = "flagLoaded")
  public void saveXml() {
    a(Spooler$Format.a);
  }
  
  @Action(b = "flagLoaded")
  public void saveExcel2003() {
    a(Spooler$Format.h);
  }
  
  @Action(b = "flagLoaded")
  public void saveExcel2007() {
    a(Spooler$Format.i);
  }
  
  public void a(Spooler$Format paramSpooler$Format) {
    new FxBrowseFrame$5(this, this.p.getWorkspace(), this.h.e, paramSpooler$Format);
  }
  
  @Action(b = "flagColumnSelected")
  public void filter() {
    if (!this.h.e.k())
      this.i.d(); 
  }
  
  @Action(b = "flagColumnSelected")
  public void removeFilter() {
    this.i.f();
    this.i.a(Cascade.b);
  }
  
  @Action
  public void removeAllFilters() {
    this.h.i();
    this.i.a(Cascade.b);
  }
  
  @Action(b = "flagColumnSelected")
  public void resize() {
    FxBrowseTableViewColumn fxBrowseTableViewColumn = this.i.c();
    if (!this.h.e.k() && fxBrowseTableViewColumn != null)
      FxUtil.a(this.i, fxBrowseTableViewColumn, fxBrowseTableViewColumn.a, false); 
  }
  
  @Action
  public void resizeAll() {
    if (!this.h.e.k())
      for (TableColumn tableColumn : this.i.getColumns()) {
        if (tableColumn instanceof FxBrowseTableViewColumn)
          FxUtil.a(this.i, tableColumn, ((FxBrowseTableViewColumn)tableColumn).a, false); 
      }  
  }
  
  @Action(b = "flagUnlocked")
  public void refreshWindow() {
    this.i.a(Cascade.b);
  }
  
  @Action
  public void uploadCellFromFile() {
    File file = FxFileChooser.a(getScene(), "Choose File", FileOperation.a, new FileType[] { FileType.D });
    if (file != null && this.i.c() != null)
      try {
        a(List.of(new ResultPosition(this.h.e.m(), (this.i.c()).a)), false, new BlobInputStream(file));
      } catch (Exception exception) {
        this.k.a(getScene(), exception);
      }  
  }
  
  @Action
  public void setAlias() {
    String str = this.k.b(getScene(), "Table Alias", this.h.j());
    this.h.b(str);
    this.k.b();
  }
  
  @Action
  public void maximizeMinimize() {
    this.g.set(!this.g.get());
  }
  
  void j() {
    if (Tips.shouldShowTip("tipBrowseCascadeChildTables4"))
      this.k.a("balloonAfterCreate.text", (Node)this.l, PopOver.ArrowLocation.TOP_CENTER, new String[0]); 
  }
  
  protected void e() {
    this.h.a((int)getLayoutX(), (int)getLayoutY());
    this.h.b((int)this.e.getPrefWidth(), (int)this.e.getPrefHeight());
    this.p.getWorkspace().u();
  }
  
  private static final PseudoClass u = PseudoClass.getPseudoClass("selected");
  
  private boolean v;
  
  public void b(boolean paramBoolean) {
    this.v = paramBoolean;
    pseudoClassStateChanged(u, paramBoolean);
    this.p.b.b();
  }
  
  public boolean k() {
    return this.v;
  }
  
  public void l() {
    for (TableColumn tableColumn : this.i.getColumns()) {
      if (tableColumn instanceof FxBrowseTableViewColumn)
        ((FxBrowseTableViewColumn)tableColumn).a(); 
    } 
  }
  
  public void a(int paramInt1, int paramInt2) {
    this.h.a(paramInt1, paramInt2);
  }
  
  public void b(int paramInt1, int paramInt2) {
    this.h.b(paramInt1, paramInt2);
  }
}
