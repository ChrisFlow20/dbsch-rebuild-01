package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.data.filter.fx.FxFilterEditor;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FxTextDialog;
import com.wisecoders.dbs.editors.csv.model.CsvColumn;
import com.wisecoders.dbs.editors.csv.model.CsvModel;
import com.wisecoders.dbs.editors.csv.model.undo.CsvChange;
import com.wisecoders.dbs.editors.csv.model.undo.CsvDeleteColumnChange;
import com.wisecoders.dbs.editors.csv.model.undo.CsvDeleteRowsChange;
import com.wisecoders.dbs.editors.csv.model.undo.CsvEditChange;
import com.wisecoders.dbs.editors.csv.model.undo.CsvInsertRowChange;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;

public class CsvEditor extends Control implements WorkspaceWindow {
  private final WorkspaceWindow f;
  
  protected final Rx a = new Rx(CsvEditor.class, this);
  
  public final CsvModel b = new CsvModel();
  
  private File g = null;
  
  private Charset h = StandardCharsets.UTF_8;
  
  private boolean i = true;
  
  private char j = ',';
  
  private char k = '\'';
  
  private String l = "\n";
  
  public final CsvEditorSkin c = new CsvEditorSkin(this);
  
  private static final int m = 128;
  
  private final List n;
  
  private final List o;
  
  public final SimpleBooleanProperty d;
  
  public final SimpleBooleanProperty e;
  
  private boolean p;
  
  protected Skin createDefaultSkin() {
    return (Skin)this.c;
  }
  
  @Action
  public void newCSV() {
    this.b.c();
    this.b.b("New");
    this.b.a(0, new String[] { "" });
    this.c.a.g();
  }
  
  @Action
  public Task openFile() {
    CsvEditorFileChooser csvEditorFileChooser = new CsvEditorFileChooser(this.f, true);
    if (csvEditorFileChooser.showDialogIsResultOkDone()) {
      this.g = csvEditorFileChooser.b();
      this.k = csvEditorFileChooser.d();
      this.j = csvEditorFileChooser.f();
      this.h = csvEditorFileChooser.a();
      this.i = csvEditorFileChooser.g();
      return a(csvEditorFileChooser.b(), csvEditorFileChooser.a(), csvEditorFileChooser.f(), csvEditorFileChooser.d(), csvEditorFileChooser.g());
    } 
    return null;
  }
  
  public Task a(File paramFile, Charset paramCharset, char paramChar1, char paramChar2, boolean paramBoolean) {
    this.c.a.getColumns().clear();
    this.b.c();
    return new CsvEditor$1(this, paramFile, paramCharset, paramChar1, paramChar2, paramBoolean);
  }
  
  @Action(b = "flagHasFile")
  public Task saveFile() {
    if (this.g == null)
      return saveAsFile(); 
    return a();
  }
  
  @Action
  public Task saveAsFile() {
    CsvEditorFileChooser csvEditorFileChooser = new CsvEditorFileChooser(this.f, this.j, this.l, this.k, this.i);
    if (csvEditorFileChooser.showDialogIsResultOkDone()) {
      this.g = csvEditorFileChooser.b();
      this.k = csvEditorFileChooser.d();
      this.l = csvEditorFileChooser.e();
      this.j = csvEditorFileChooser.f();
      this.h = csvEditorFileChooser.a();
      this.i = csvEditorFileChooser.g();
      return a();
    } 
    return null;
  }
  
  public Task a() {
    return new CsvEditor$2(this);
  }
  
  @Action(b = "flagSelectedColumn")
  public Task insertColumnLeft() {
    return a(this.c.a.e());
  }
  
  @Action(b = "flagSelectedColumn")
  public Task insertColumnRight() {
    return a(this.c.a.e() + 1);
  }
  
  @Action(b = "flagSelectedRow")
  public Task insertRowBefore() {
    int i = this.c.a.getSelectionModel().getSelectedIndex();
    CsvChange csvChange = (new CsvInsertRowChange(i)).a(this);
    this.n.add(csvChange);
    d();
    return null;
  }
  
  @Action(b = "flagSelectedRow")
  public Task insertRowAfter() {
    int i = this.c.a.getSelectionModel().getSelectedIndex() + 1;
    CsvChange csvChange = (new CsvInsertRowChange(i)).a(this);
    this.n.add(csvChange);
    d();
    return null;
  }
  
  private Task a(int paramInt) {
    return new CsvEditor$3(this, paramInt);
  }
  
  @Action(b = "flagSelectedColumn")
  public void deleteColumn() {
    TableColumn tableColumn = this.c.a.f();
    if (tableColumn != null) {
      CsvChange csvChange = (new CsvDeleteColumnChange((CsvColumn)tableColumn.getUserData())).a(this);
      this.n.add(csvChange);
      d();
    } 
  }
  
  @Action(b = "flagSelectedColumn", d = "flagAcceptCRLF")
  public void acceptCRLF() {
    this.c.a.c().b(!this.c.a.c().c());
  }
  
  @Action(b = "flagSelectedRow")
  public void deleteRow() {
    CsvChange csvChange = (new CsvDeleteRowsChange(this.b, (List)this.c.a.getSelectionModel().getSelectedItems())).a(this);
    this.n.add(csvChange);
    d();
  }
  
  public Node getStyleableNode() {
    return null;
  }
  
  @Action(b = "flagSelectedColumn")
  public Task sortAsc() {
    this.b.a(this.c.a.c(), Boolean.valueOf(true));
    return b();
  }
  
  @Action(b = "flagSelectedColumn")
  public Task sortDesc() {
    this.b.a(this.c.a.c(), Boolean.valueOf(false));
    return b();
  }
  
  @Action(b = "flagSelectedColumn")
  public Task sortDrop() {
    this.b.a(this.c.a.c(), (Boolean)null);
    return b();
  }
  
  @Action(b = "flagCanUndo")
  public void undo() {
    CsvChange csvChange;
    if (this.n.size() > 0 && (csvChange = this.n.get(this.n.size() - 1)) != null) {
      csvChange.b(this);
      this.n.remove(csvChange);
      this.o.add(csvChange);
      this.c.a.refresh();
    } 
  }
  
  @Action(b = "flagCanRedo")
  public void redo() {
    CsvChange csvChange;
    if (this.o.size() > 0 && (csvChange = this.o.get(this.o.size() - 1)) != null) {
      csvChange.b(this);
      this.o.remove(csvChange);
      this.n.add(csvChange);
      this.c.a.refresh();
    } 
  }
  
  @Action(b = "flagSelectedColumn")
  public void clipboardCopy() {
    this.c.c();
  }
  
  @Action(b = "flagSelectedColumn")
  public void clipboardPaste() {
    this.c.c();
  }
  
  public Task b() {
    return new CsvEditor$4(this);
  }
  
  @Action(b = "flagSelectedColumn")
  public void renameColumn() {
    CsvColumn csvColumn = this.c.a.c();
    Optional optional = (new FxTextDialog(getScene().getWindow(), "Rename Column", csvColumn.getName())).showAndWait();
    optional.ifPresent(paramString -> {
          paramCsvColumn.b(paramOptional.get());
          this.c.a.g();
        });
  }
  
  @Action(b = "flagSelectedColumn")
  public void filter() {
    CsvColumn csvColumn = this.c.a.c();
    (new FxFilterEditor(this, this.b, csvColumn, this.b.c(csvColumn))).showDialog();
    this.a.a(b());
  }
  
  @Action(b = "flagSelectedColumn")
  public Task dropFilters() {
    this.b.d();
    return b();
  }
  
  @Action(b = "flagSelectedColumn")
  public Task dropFilter() {
    this.b.a(this.c.a.c());
    return b();
  }
  
  @Action
  public void find() {
    this.c.b();
  }
  
  @Action
  public void findReplace() {
    this.c.e();
  }
  
  public Task c() {
    String str = this.a.b(getScene(), "updateColumnText");
    if (str != null)
      return new CsvEditor$5(this, str); 
    return null;
  }
  
  public CsvEditor(WorkspaceWindow paramWorkspaceWindow) {
    this.n = new ArrayList();
    this.o = new ArrayList();
    this.d = new SimpleBooleanProperty(false);
    this.e = new SimpleBooleanProperty(false);
    this.p = false;
    this.f = paramWorkspaceWindow;
    this.a.a("flagSelectedRow", () -> (this.c.a.getSelectionModel().getSelectedItem() != null));
    this.a.a("flagSelectedColumn", () -> (this.c.a.b() != null));
    this.a.a("flagHasFile", () -> (this.g != null));
    Objects.requireNonNull(this.d);
    this.a.a("flagCanUndo", this.d::get);
    Objects.requireNonNull(this.e);
    this.a.a("flagCanRedo", this.e::get);
    this.a.a("flagAcceptCRLF", () -> (this.c.a.c() != null && (this.c.a.c()).y));
    Platform.runLater(() -> this.a.a(openFile()));
  }
  
  public void a(int paramInt, CsvColumn paramCsvColumn, String paramString) {
    CsvChange csvChange = (new CsvEditChange(paramInt, paramCsvColumn, paramString)).a(this);
    this.n.add(csvChange);
    d();
  }
  
  private void d() {
    this.p = true;
    this.d.set((this.n.size() > 0));
    this.e.set((this.o.size() > 0));
    if (this.n.size() > 128)
      this.n.remove(0); 
    if (this.o.size() > 128)
      this.o.remove(0); 
    this.a.b();
  }
  
  public Workspace getWorkspace() {
    return this.f.getWorkspace();
  }
  
  public Scene getDialogScene() {
    return getScene();
  }
  
  public Rx getRx() {
    return this.a;
  }
}
