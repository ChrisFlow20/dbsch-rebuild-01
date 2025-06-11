package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.data.fx.FxAbstractSqlEditor;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorManagerDialog;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.layout.FxCommentEditor;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxAutomationScriptsDialog;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.dialogs.system.FxShortcutsDialog;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.editors.text.StyledEditorCompletion;
import com.wisecoders.dbs.editors.text.lexers.JsonLexer;
import com.wisecoders.dbs.editors.text.lexers.SqlLexer;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.sql.completion.GroovyCompletion;
import com.wisecoders.dbs.sql.completion.SqlCompletion;
import com.wisecoders.dbs.sql.indenter.JsonIndent;
import com.wisecoders.dbs.sql.indenter.SqlIndent;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.ProgressBarWithText;
import com.wisecoders.dbs.sys.fx.ResourceCallback;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.SQLTips;
import java.io.File;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class FxSqlEditor extends FxAbstractSqlEditor implements FxToolEditor {
  private final Rx j = new Rx(FxSqlEditor.class, this);
  
  public static final String g = "last_save_dir";
  
  public final Sql h;
  
  public final StyledEditor i = new StyledEditor(true);
  
  private final SqlRange k = new SqlRange(this.i);
  
  private final SqlRange l = new SqlRange(this.i);
  
  private final MenuButton m = new MenuButton();
  
  private final Button n;
  
  private final ProgressBarWithText o = new ProgressBarWithText();
  
  private boolean p;
  
  private int q = 0;
  
  private final FxLayoutPane r;
  
  private final MenuButton s;
  
  private static final int t = 2000;
  
  public FxSqlEditor(Workspace paramWorkspace, FxLayoutPane paramFxLayoutPane, Sql paramSql) {
    super(paramWorkspace);
    this.h = paramSql;
    this.r = paramFxLayoutPane;
    this.i.f(paramSql.is(UnitProperty.f).booleanValue());
    this.s = this.j.f("connectorMenu", false);
    this.j.G(paramSql.getDbId());
    this.j.a("flagIdle", () -> this.b);
    Objects.requireNonNull(this.i.K.e);
    this.j.a("flagCanUndo", this.i.K.e::getValue);
    Objects.requireNonNull(this.i.K.f);
    this.j.a("flagCanRedo", this.i.K.f::getValue);
    this.j.a("flagCanRunSql", () -> (this.b && paramSql.getLanguage() == Language.a));
    this.j.a("flagScriptIgnoreErrors", FxAbstractSqlEditor::v);
    this.j.a("flagScriptAutoCommit", FxAbstractSqlEditor::w);
    this.j.a("flagLanguageSQL", () -> (paramSql.getLanguage() == Language.a));
    this.j.a("flagLanguageJavaScript", () -> (paramSql.getLanguage() == Language.c));
    this.j.a("flagIsSplitHorizontal", () -> (getOrientation() == Orientation.HORIZONTAL));
    this.j.a("flagHasResult", () -> (getItems().size() > 1));
    this.j.a("flagLanguageGroovy", () -> (paramSql.getLanguage() == Language.b));
    Objects.requireNonNull(this.i);
    this.j.a("flagAutoPopupCompletion", this.i::s);
    Objects.requireNonNull(this.i);
    this.j.a("flagCrInLineTerminator", this.i::n);
    Objects.requireNonNull(this.i.J);
    this.j.a("flagShowSpecialCharacters", this.i.J::m);
    this.j.b();
    this.i.b(paramSql.getText());
    a(paramSql.getLanguage());
    this.n = this.j.j("commit");
    this.i.J.b.a(() -> {
          if (this.p && ++this.q % 2 == 0) {
            this.n.getStyleClass().add("blink-button");
          } else {
            this.n.getStyleClass().remove("blink-button");
          } 
        });
    this.i.addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          KeyCombination keyCombination;
          if ((keyCombination = this.j.D("runScript")) != null && keyCombination.match(paramKeyEvent)) {
            this.j.a(c());
            paramKeyEvent.consume();
          } else if ((keyCombination = this.j.D("run")) != null && keyCombination.match(paramKeyEvent)) {
            paramWorkspace.getRx().a(run());
            paramKeyEvent.consume();
          } else if ((keyCombination = this.j.D("commit")) != null && keyCombination.match(paramKeyEvent)) {
            commit();
            paramKeyEvent.consume();
          } else if ((keyCombination = this.j.D("rollback")) != null && keyCombination.match(paramKeyEvent)) {
            rollback();
            paramKeyEvent.consume();
          } else if ((keyCombination = this.j.D("indent")) != null && keyCombination.match(paramKeyEvent)) {
            indent();
            paramKeyEvent.consume();
          } 
        });
    D();
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)A());
    borderPane.setCenter((Node)this.i);
    getItems().add(borderPane);
    Objects.requireNonNull(this.i);
    Platform.runLater(this.i::requestFocus);
  }
  
  public void a(Language paramLanguage) {
    this.i.a(paramLanguage);
    switch (FxSqlEditor$2.a[paramLanguage.ordinal()]) {
      case 1:
        this.i.J.a(new SqlCompletion(this.i, this.a.m()));
        return;
      case 2:
        this.i.J.a(new GroovyCompletion());
        return;
    } 
    this.i.J.a((StyledEditorCompletion)null);
  }
  
  private ToolBar A() {
    ToolBar toolBar = new ToolBar();
    toolBar.setFocusTraversable(true);
    SplitMenuButton splitMenuButton;
    MenuButton menuButton1, menuButton2, menuButton3;
    toolBar.getItems().addAll((Object[])new Node[] { 
          (Node)(menuButton1 = this.j.f("file", false)), 
          (Node)(menuButton2 = this.j.f("edit", false)), (Node)this.s, (Node)this.m, (Node)this.j

          
          .j("run"), 
          (Node)(splitMenuButton = this.j.g("runScript", true)), (Node)this.j
          .j("stop"), (Node)this.n, (Node)this.j
          
          .j("rollback"), (Node)this.j
          .j("explain"), 
          (Node)(menuButton3 = this.j.f("help", false)), (Node)this.o });
    this.j.a(this.o);
    splitMenuButton.getStyleClass().add("transparent-split-menu-button");
    this.m.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          this.m.getItems().clear();
          if (paramBoolean2.booleanValue())
            this.m.getItems().addAll((Object[])new MenuItem[] { (MenuItem)this.j.z("languageSQL"), (MenuItem)this.j.z("languageGroovy"), (MenuItem)this.j.z("languageJavaScript") }); 
        });
    this.s.getItems().addAll(this.j.e(new String[] { "inheritConnector", "chooseConnector" }));
    this.m.getStyleClass().add("menu-button-with-border");
    splitMenuButton.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          if (paramBoolean2.booleanValue())
            paramMenuButton.getItems().addAll((Object[])new MenuItem[] { this.j.A("runScript"), (MenuItem)new SeparatorMenuItem(), (MenuItem)this.j.y("scriptIgnoreErrors"), (MenuItem)this.j.y("scriptAutoCommit") }); 
        });
    menuButton1.getItems().addAll((Object[])new MenuItem[] { this.j
          .A("newSqlEditor"), this.j
          .A("openFile"), this.j
          .A("saveFile"), this.j
          .A("saveFileAs"), (MenuItem)new SeparatorMenuItem(), this.j
          
          .A("parseScript"), (MenuItem)new SeparatorMenuItem(), this.j
          
          .A("print") });
    menuButton2.getItems().addAll((Object[])new MenuItem[] { 
          this.j
          .A("comment"), (MenuItem)new SeparatorMenuItem(), this.j
          
          .A("indent"), this.j
          .A("toggleCase"), (MenuItem)this.j
          .y("autoPopupCompletion"), (MenuItem)new SeparatorMenuItem(), this.j
          
          .A("find"), this.j
          .A("replace"), this.j
          .A("goToLine"), (MenuItem)new SeparatorMenuItem(), 
          this.j
          
          .A("cut"), this.j
          .A("copy"), this.j
          .A("paste"), (MenuItem)new SeparatorMenuItem(), this.j
          
          .A("undo"), this.j
          .A("redo"), (MenuItem)new SeparatorMenuItem(), (MenuItem)this.j
          
          .y("useCrInLineTerminator"), (MenuItem)this.j
          .y("showSpecialCharacters"), (MenuItem)this.j
          .y("changeSplitOrientation"), 
          (MenuItem)new SeparatorMenuItem(), this.j
          
          .A("newResultPane") });
    menuButton3.getItems().addAll(this.j
        .e(new String[] { "help", "groovyScripts", "tipsTricks", "separator", "shortcutsManager" }));
    return toolBar;
  }
  
  @Action(b = "flagIdle")
  public Task run() {
    this.i.w();
    if (this.h instanceof com.wisecoders.dbs.schema.DbUnit)
      this.h.setText(this.i.t()); 
    if (this.h.getLanguage() == Language.a) {
      String str = k();
      if (str == null)
        a("Nothing to execute"); 
      return a(str, e(false));
    } 
    return a(this.h.getLanguage(), this.i.t(), d(true));
  }
  
  @Action(b = "flagCanRunSql")
  public void newResultPane() {
    e(true);
  }
  
  @Action(b = "flagCanRunSql")
  public Task runScript() {
    return c();
  }
  
  @Action(b = "flagIdle")
  public void commit() {
    super.commit();
  }
  
  @Action(b = "flagIdle")
  public void rollback() {
    super.rollback();
  }
  
  @Action(b = "flagCanRunSql")
  public Task explain() {
    return super.explain();
  }
  
  @Action(b = "flagIdle")
  public void comment() {
    (new FxCommentEditor(this.a, this.h)).showDialog();
  }
  
  @Action(d = "flagIsSplitHorizontal", b = "flagHasResult")
  public void changeSplitOrientation() {
    setOrientation((getOrientation() == Orientation.HORIZONTAL) ? Orientation.VERTICAL : Orientation.HORIZONTAL);
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
  
  @Action(b = "flagLanguageSQL")
  public Task parseScript() {
    if (x().getEntityCount() == 0)
      return new FxDDLParserTask(this, false); 
    switch (FxSqlEditor$2.b[this.j.b(getScene(), "parseDDLTarget", new String[0]).c().ordinal()]) {
      case 1:
      
      case 2:
      
    } 
    return 

      
      null;
  }
  
  public boolean e() {
    if (this.i.q() && !this.h.isConfirmed()) {
      this.h.markForDeletion();
      this.a.m().refresh();
      return true;
    } 
    if (this.i.o() || this.h.isFreshCreated()) {
      if (this.h.getFile() != null) {
        switch (FxSqlEditor$2.b[this.j.b(getScene(), "saveEditorFileMessage", new String[0]).c().ordinal()]) {
          case 1:
            this.j.a(g(false));
          case 2:
          
        } 
        return false;
      } 
      if (this.i.p() > 2000) {
        switch (FxSqlEditor$2.b[this.j.b(getScene(), "saveEditorToFileMessage", new String[0]).c().ordinal()]) {
          case 1:
            this.j.a(g(false));
          case 3:
            i();
          case 2:
          
        } 
        return false;
      } 
      if (this.h.isConfirmed()) {
        switch (FxSqlEditor$2.b[this.j.b(getScene(), "saveConfirmedEditorMessage", new String[0]).c().ordinal()]) {
          case 1:
          
          case 2:
          
        } 
        return false;
      } 
      switch (FxSqlEditor$2.b[this.j.b(getScene(), "saveEditorMessage", new String[0]).c().ordinal()]) {
        case 1:
          this.h.setConfirmed(true);
        case 2:
          if (this.h instanceof com.wisecoders.dbs.schema.Script) {
            this.h.markForDeletion();
            getWorkspace().m().refresh();
          } 
      } 
      return false;
    } 
    return true;
  }
  
  @Action(c = "flagIdle")
  public Task stop() {
    return new FxSqlEditor$1(this);
  }
  
  public void g() {
    f();
  }
  
  public boolean i() {
    return y();
  }
  
  public boolean y() {
    if (this.h.getFile() != null) {
      this.j.a(g(false));
      this.h.setFreshCreated(false);
    } else if (this.i.p() < 2000) {
      this.h.setText(this.i.t());
      this.h.setFreshCreated(false);
      this.i.c(false);
    } 
    return true;
  }
  
  public AbstractUnit j() {
    return this.h;
  }
  
  public String h() {
    return (this.h != null) ? this.h.getName() : "Sql editor";
  }
  
  public String t() {
    return null;
  }
  
  public void a(int paramInt, String paramString) {
    this.i.b(paramInt, paramString);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString) {
    this.i.a(paramInt1, paramInt2, paramInt4, paramString, true);
  }
  
  public void a(String paramString) {}
  
  public String d() {
    return this.h.getDbId();
  }
  
  public void c(boolean paramBoolean) {
    this.p = paramBoolean;
  }
  
  public void l() {
    this.k.f();
    if (!this.k.a())
      this.k.h(); 
    this.l.a.a(this.k.a);
    this.l.b.a(this.k.a);
    this.l.a(false);
  }
  
  public String r() {
    this.l.i();
    this.l.j();
    Dbms dbms = Dbms.get(this.h.getDbId());
    if (dbms.isPlSqlBlock(this.l.b()))
      this.l.a(dbms, this.l.a); 
    if (this.k.a(this.l)) {
      this.i.J.b(this.l.a, this.l.b);
      String str = this.l.b();
      this.l.a(true);
      return str;
    } 
    return null;
  }
  
  public String k() {
    B();
    return this.i.d();
  }
  
  public void s() {
    this.i.J.g();
  }
  
  private void B() {
    SqlRange sqlRange = new SqlRange(this.i);
    sqlRange.f();
    if (!sqlRange.a()) {
      sqlRange.b(this.i.i());
      Dbms dbms = Dbms.get(this.h.getDbId());
      if (sqlRange.a() && dbms.isPlSqlBlock(sqlRange.b()))
        sqlRange.a(dbms, this.i.i()); 
      if (sqlRange.a())
        this.i.J.a(sqlRange.a, sqlRange.b); 
    } 
  }
  
  private void C() {
    SqlRange sqlRange = new SqlRange(this.i);
    sqlRange.f();
    if (!sqlRange.a()) {
      sqlRange.h();
      if (sqlRange.a())
        this.i.J.a(sqlRange.a, sqlRange.b); 
    } 
  }
  
  @Action(d = "flagScriptIgnoreErrors")
  public void scriptIgnoreErrors() {
    super.scriptIgnoreErrors();
    this.j.b();
  }
  
  @Action(d = "flagAutoPopupCompletion")
  public void autoPopupCompletion() {
    this.i.d(!this.i.s());
    this.j.b();
  }
  
  @Action(d = "flagScriptAutoCommit")
  public void scriptAutoCommit() {
    super.scriptAutoCommit();
    this.j.b();
  }
  
  @Action
  public Task openFile() {
    File file = FxFileChooser.a(this.a, "Open File", FileOperation.a, new FileType[] { FileType.D, FileType.r, FileType.s, FileType.i });
    if (file != null) {
      if (this.i.q()) {
        if (this.h instanceof com.wisecoders.dbs.schema.Script)
          this.h.rename(file.getName()); 
        return a(file, (ResourceCallback)null);
      } 
      FxSqlEditor fxSqlEditor = this.r.a(this.r.d.createScript(file.getName()));
      this.r.a(fxSqlEditor);
      return fxSqlEditor.a(file, (ResourceCallback)null);
    } 
    return null;
  }
  
  public Task a(File paramFile, ResourceCallback paramResourceCallback) {
    return this.i.a(paramFile, () -> {
          this.h.setFile(paramFile);
          if (paramFile.getName().endsWith(".groovy")) {
            this.h.setLanguage(Language.b);
            a(Language.b);
          } 
          D();
          if (paramResourceCallback != null)
            paramResourceCallback.evaluate(); 
        });
  }
  
  private Task g(boolean paramBoolean) {
    if (this.h.getFile() == null || paramBoolean) {
      File file = FxFileChooser.a(this.a, "Save SQL File", FileOperation.b, (File)null, new FileType[] { FileType.r, FileType.s, FileType.i });
      if (file != null) {
        this.h.setFile(file);
        this.h.rename(this.h.getFile().getName());
      } 
    } 
    if (this.h.getFile() != null)
      return this.i.a(this.h.getFile()); 
    return null;
  }
  
  @Action
  public Task saveFile() {
    return g(false);
  }
  
  @Action
  public Task saveFileAs() {
    return g(true);
  }
  
  @Action
  public void print() {}
  
  @Action
  public void replace() {
    this.i.replace();
  }
  
  @Action
  public void goToLine() {
    this.i.goToLine();
  }
  
  @Action
  public void copy() {
    this.i.copy();
  }
  
  @Action
  public void paste() {
    this.i.paste();
  }
  
  @Action
  public void cut() {
    this.i.cut();
  }
  
  @Action(b = "flagCanUndo")
  public void undo() {
    this.i.undo();
  }
  
  @Action(b = "flagCanRedo")
  public void redo() {
    this.i.redo();
  }
  
  @Action
  public void find() {
    this.i.find();
  }
  
  @Action
  public void indent() {
    B();
    C();
    if (this.i.d() == null) {
      a("Nothing to indent");
    } else if ("MongoDb".equals(this.h.getDbId())) {
      this.i.a((new JsonIndent(new JsonLexer(), this.i.d())).toString());
    } else {
      this.i.a((new SqlIndent(new SqlLexer(), this.i.d())).toString());
    } 
  }
  
  @Action
  public void toggleCase() {
    this.i.toggleCase();
  }
  
  @Action
  public void shortcutsManager() {
    new FxShortcutsDialog(this.a, this.j);
  }
  
  @Action
  public void close() {
    i();
    z();
  }
  
  @Action
  public void closeAndDrop() {
    this.h.markForDeletion();
    this.a.m().refresh();
    z();
  }
  
  public void z() {}
  
  private void D() {
    this.m.setText(this.j.H("language" + String.valueOf(this.h.getLanguage())));
  }
  
  @Action(d = "flagLanguageGroovy")
  public void languageGroovy() {
    this.h.setLanguage(Language.b);
    a(Language.b);
    D();
    this.j.b();
  }
  
  @Action(d = "flagLanguageJavaScript")
  public void languageJavaScript() {
    this.h.setLanguage(Language.c);
    a(Language.c);
    D();
    this.j.b();
  }
  
  @Action(d = "flagLanguageSQL")
  public void languageSQL() {
    this.h.setLanguage(Language.a);
    a(Language.a);
    D();
    this.j.b();
  }
  
  @Action(d = "flagCrInLineTerminator")
  public void useCrInLineTerminator() {
    this.i.b(!this.i.n());
    this.j.b();
  }
  
  @Action(d = "flagShowSpecialCharacters")
  public void showSpecialCharacters() {
    this.i.J.a(!this.i.J.m());
    this.j.b();
  }
  
  @Action
  public void newSqlEditor() {
    FxEditorFactory.a(getWorkspace(), (getWorkspace().q()).d.scripts);
  }
  
  @Action
  public void groovyScripts() {
    (new FxAutomationScriptsDialog(this.a)).showDialog();
  }
  
  @Action
  public void inheritConnector() {
    if (this.c != null) {
      if (this.d != null) {
        this.d.n();
        this.d = null;
      } 
      this.c.closeAllEnvoysAndSsh();
      this.c = null;
    } 
  }
  
  @Action
  public void chooseConnector() {
    (new FxConnectorManagerDialog(getWorkspace(), false)).showDialog().ifPresent(paramConnector -> {
          if (this.d != null) {
            this.d.n();
            this.d = null;
          } 
          this.c = paramConnector;
        });
  }
  
  public Rx u() {
    return this.j;
  }
  
  @Action
  public void help() {
    WebBrowserExternal.a(getWorkspace(), "sql-editor.html");
  }
  
  @Action
  public void tipsTricks() {
    new FxTipsDialog(this.a, new SQLTips());
  }
  
  public Glyph m() {
    return BootstrapIcons.filetype_sql;
  }
  
  public String n() {
    return "sql.png";
  }
  
  public void a(boolean paramBoolean, int paramInt) {
    b();
    if (this.i.o())
      getWorkspace().u(); 
    String str = (a() == null) ? this.j.H("inherit") : a().getName();
    if (!StringUtil.areEqual(this.s.getText(), str))
      this.s.setText(str); 
  }
  
  public boolean o() {
    return false;
  }
  
  public void p() {}
  
  public void a(boolean paramBoolean) {
    this.i.setVisible(paramBoolean);
  }
  
  @Action
  public void statementTokenizer() {
    (new FxDDLParserPreviewDialog(this)).showDialog();
  }
  
  public void q() {}
}
