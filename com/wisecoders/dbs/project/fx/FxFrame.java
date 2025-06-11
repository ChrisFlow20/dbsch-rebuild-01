package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsLocation;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.fx.FxChooseDbmsDialog;
import com.wisecoders.dbs.dbms.connect.fx.FxChooseDbmsDialog$DbmsChoice;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorEditor;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorManagerDialog;
import com.wisecoders.dbs.dbms.driver.fx.FxDriversDialog;
import com.wisecoders.dbs.dbms.reverseEngineer.fx.FxCreateOrUpgradeSchemaInDatabaseTask;
import com.wisecoders.dbs.dbms.reverseEngineer.fx.FxDbSynchronizationTask;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.FxGetRenewedLicenseTask;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.FxImportSelectionTask;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.SyncMode;
import com.wisecoders.dbs.dbms.scripts.fx.FxExportSchemaAndDataDialog;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.fx.FxClipboardPasteSQLDialog;
import com.wisecoders.dbs.dbms.sync.fx.FxSyncSettingsDialog;
import com.wisecoders.dbs.dbms.sync.fx.SyncDispatcher;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.FxDiagramPerspectivePanel;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.fx.FxProjectLoaderTask;
import com.wisecoders.dbs.diagram.fx.print.FxPrintPreviewDialog;
import com.wisecoders.dbs.diagram.fx.print.printable.PrintableDiagram;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.diagram.model.ScriptAddOnFolder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.admin.fx.FxAmazonSESDialog;
import com.wisecoders.dbs.dialogs.admin.fx.FxPPGDialog;
import com.wisecoders.dbs.dialogs.admin.fx.FxSparkPostDialog;
import com.wisecoders.dbs.dialogs.admin.fx.FxTextToSpeech;
import com.wisecoders.dbs.dialogs.git.fx.FxGitDialog;
import com.wisecoders.dbs.dialogs.layout.DependencyNode;
import com.wisecoders.dbs.dialogs.layout.FxCalloutEditor;
import com.wisecoders.dbs.dialogs.layout.FxColumnEditor;
import com.wisecoders.dbs.dialogs.layout.FxCommentEditor;
import com.wisecoders.dbs.dialogs.layout.FxDependencyDialog;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.FxForeignKeyEditor;
import com.wisecoders.dbs.dialogs.layout.FxGridEditor;
import com.wisecoders.dbs.dialogs.layout.FxGroupEditor;
import com.wisecoders.dbs.dialogs.layout.FxGroupsEditor;
import com.wisecoders.dbs.dialogs.layout.FxIndexEditor;
import com.wisecoders.dbs.dialogs.layout.FxLayoutEditor;
import com.wisecoders.dbs.dialogs.layout.FxProjectEditor;
import com.wisecoders.dbs.dialogs.layout.FxSchemaMappingDialog;
import com.wisecoders.dbs.dialogs.layout.FxShapeEditor;
import com.wisecoders.dbs.dialogs.layout.FxTableEditor;
import com.wisecoders.dbs.dialogs.layout.FxTagsDialog;
import com.wisecoders.dbs.dialogs.layout.FxUserDataTypeEditor;
import com.wisecoders.dbs.dialogs.layout.FxViewEditor;
import com.wisecoders.dbs.dialogs.layout.FxVirtualForeignKeysDialog;
import com.wisecoders.dbs.dialogs.system.ActivationTask$Mode;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FloatingLicenseTask;
import com.wisecoders.dbs.dialogs.system.FxAboutDialog;
import com.wisecoders.dbs.dialogs.system.FxAutomationScriptsDialog;
import com.wisecoders.dbs.dialogs.system.FxChooseArticleDialog;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.dialogs.system.FxFindDialog;
import com.wisecoders.dbs.dialogs.system.FxRegistrationDialog;
import com.wisecoders.dbs.dialogs.system.FxShortcutsDialog;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.dialogs.system.Snapshot;
import com.wisecoders.dbs.dialogs.system.TechnicalSupportTask;
import com.wisecoders.dbs.dialogs.web.fx.FxVideoDialog;
import com.wisecoders.dbs.editors.csv.fx.CsvToolEditor;
import com.wisecoders.dbs.generator.fx.FxGeneratorEditor;
import com.wisecoders.dbs.generator.fx.FxGeneratorPatternDialog;
import com.wisecoders.dbs.generator.fx.FxGeneratorTableDialog;
import com.wisecoders.dbs.loader.fx.FxLoaderFileDialog;
import com.wisecoders.dbs.loader.model.LoaderMode;
import com.wisecoders.dbs.project.convert.fx.FxConversionDictionaryDialog;
import com.wisecoders.dbs.project.convert.fx.FxConvertImportSchemaDialog;
import com.wisecoders.dbs.project.convert.fx.FxNamingDictionaryDialog;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.governance.fx.FxInspectorDialog;
import com.wisecoders.dbs.project.governance.fx.FxValidationResultDialog;
import com.wisecoders.dbs.project.governance.model.InspectorRoot;
import com.wisecoders.dbs.project.governance.model.Mode;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.LayoutDepict;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.ProjectType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.fx.FxSqlEditor;
import com.wisecoders.dbs.sql.fx.FxSqlEditorOpenFileDialog;
import com.wisecoders.dbs.sql.generator.StatementType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Features;
import com.wisecoders.dbs.sys.FxOutputLogsDialog;
import com.wisecoders.dbs.sys.Keys;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Pro;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UpgradeObsoleteDrivers;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.CheckForUpdatesTask;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxCheckForUpdatesDialog;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.ToolBar$;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.DbsTips;
import com.wisecoders.dbs.tips.tips.QuickTour;
import com.wisecoders.dbs.tips.tips.QuickTour$Type;
import com.wisecoders.dbs.tips.tips.SQLTutorialTips;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class FxFrame extends Workspace {
  public final Rx a = new Rx(FxFrame.class, this);
  
  public final FxMenu b = new FxMenu(this);
  
  private final MenuBar p = new MenuBar();
  
  private final ToolBar$ q = new ToolBar$();
  
  public static final History c = new History("proj");
  
  private int r = 0;
  
  private final Stage s;
  
  private final FxDiagramPerspectivePanel t = new FxDiagramPerspectivePanel(this);
  
  private final FxHistoryPane u = new FxHistoryPane(this);
  
  private long v;
  
  public FxFrame(Stage paramStage, File paramFile) {
    this(paramStage);
    if (paramFile != null)
      if (paramFile.getName().endsWith(".dbs")) {
        License license = License.a();
        if (license.g() == Features.d || license.g() == Features.e)
          a(license.i() ? this.a.H("subscriptionExpired.text") : this.a.H("renewLicense"), this.a
              .H("renewNow"), license.d()); 
        if (license.f() && license.g() != Features.g) {
          a(this.a.H("proAdvantages"), this.a.H("goPro"), this.a.a("buy.url", new String[0]));
        } else {
          this.a.a(new FxProjectLoaderTask(this, paramFile));
        } 
      } else {
        d().a(paramFile);
      }  
  }
  
  public void a() {
    boolean bool = (this.f != null && this.f.is(UnitProperty.g).booleanValue()) ? true : false;
    this.b.a(this.p, bool);
    this.b.a(this.q, bool);
  }
  
  public Scene getDialogScene() {
    return this;
  }
  
  private void e(boolean paramBoolean) {
    if (this.s.isShowing() && !this.s.isIconified()) {
      a(paramBoolean);
      this.b.a();
      if (paramBoolean) {
        this.t.a();
        this.t.a(o());
        this.u.a();
        this.a.b();
        if (this.o) {
          this.n = System.currentTimeMillis();
          this.o = false;
        } 
        if (l() && Sys.B.autoRefresh.b() && m().getOperativeConnector() != null) {
          Connector connector = m().getOperativeConnector();
          if (connector.shouldAutoSynchronize() && !this.a.b(FxDbSynchronizationTask.class)) {
            connector.resetResyncFlag();
            this.a.a(new FxDbSynchronizationTask(this, m(), connector, SyncMode.a, false, null, true), false);
          } 
        } 
        if (!L().equals(this.s.getTitle()))
          this.s.setTitle(L()); 
        Q();
      } 
    } 
  }
  
  public synchronized void a(boolean paramBoolean) {
    K();
    if (this.f != null) {
      a(paramBoolean, this.f.tickId);
      if (paramBoolean) {
        if (this.i.a(this.f.tickId)) {
          this.i.a();
          G();
        } 
        this.f.tickId++;
      } 
    } 
  }
  
  public FxFrame(Stage paramStage) {
    this.v = 0L;
    this.y = true;
    this.z = Sys.f.resolve("1_Sakila.dbs").toFile();
    this.s = paramStage;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Log.c("==== " + simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + " DbSchema #" + Log.c + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " Java " + System.getProperty("java.vendor") + " " + System.getProperty("java.version") + " ===");
    UpgradeObsoleteDrivers.a();
    getStylesheets().addAll((Theme.a()).f);
    this.a.a("flagActiveProject", this::l);
    this.a.a("flagCanReorderColumns", () -> (this.f != null && !(Dbms.get(this.f.getDbId())).disableReorderColumns.b()));
    this.a.a("flagProjectHasFile", () -> (l() && m().hasFile()));
    this.a.a("flagActiveLayout", () -> (o() != null));
    this.a.a("flagActiveNotEmptyProject", () -> (l() && this.f.hasEntities()));
    this.a.a("flagOnline", this::t);
    this.a.a("flagOfflineOrLogicalDesign", () -> (l() && (!r() || this.f.is(UnitProperty.g).booleanValue())));
    Objects.requireNonNull(Sys.B.autoRefresh);
    this.a.a("flagAutoRefreshSchema", Sys.B.autoRefresh::b);
    this.a.a("flagShouldSave", () -> (l() && w()));
    this.a.a("flagShowSchemaName", () -> (f() && (o()).c.d().isShowSchemaName()));
    this.a.a("flagShowPhysicalName", () -> (f() && (o()).c.d().isShowPhysicalName()));
    this.a.a("flagShowPhysicalDictionaryName", () -> (f() && (o()).c.d().isShowPhysicalDictionaryName()));
    this.a.a("flagCatalogPrefixSchema", () -> (this.f != null && (Dbms.get(this.f.getDbId())).prefixSchemaWithCatalog.b()));
    this.a.a("flagSchemaPrefixTable", () -> (this.f != null && (Dbms.get(this.f.getDbId())).prefixTableWithSchema.b()));
    this.a.a("flagAutoLetterCases", () -> (this.f != null && (Dbms.get(this.f.getDbId())).autoLetterCases.b()));
    this.a.a("flagShowDataType", () -> (f() && (o()).c.d().isShowDataType()));
    this.a.a("flagShowFkName", () -> (f() && (o()).c.d().getLineTextType() == LineTextType.a));
    this.a.a("flagShowFkColumns", () -> (f() && (o()).c.d().getLineTextType() == LineTextType.b));
    this.a.a("flagShowFkCascade", () -> (f() && (o()).c.d().getLineTextType() == LineTextType.c));
    this.a.a("flagJoinedRouting", () -> (f() && (o()).c.d().isJoinedRouting()));
    this.a.a("flagDeducedFks", () -> (f() && (o()).c.d().isShowDeducedFks()));
    this.a.a("flagShowPageBorders", () -> (f() && (o()).c.d().isShowPageBorders()));
    this.a.a("flagNotationIEWithArrows", () -> (l() && Project.ieWithArrowsNotation == this.f.getNotation()));
    this.a.a("flagNotationBarker", () -> (l() && Project.barkerNotation == this.f.getNotation()));
    this.a.a("flagNotationIdef1x", () -> (l() && Project.idef1xNotation == this.f.getNotation()));
    this.a.a("flagNotationIE", () -> (l() && Project.ieNotation == this.f.getNotation()));
    this.a.a("flagNotationStrict", () -> (l() && this.f.isNotationStrict()));
    this.a.a("flagCanUndoDiagramEdit", () -> (f() && (o()).c.J()));
    this.a.a("flagCanRedoDiagramEdit", () -> (f() && (o()).c.K()));
    Objects.requireNonNull(Sys.B.generateSqlToClipboard);
    this.a.a("flagGenerateToClipboard", Sys.B.generateSqlToClipboard::b);
    this.a.a("flagCreateRelationMode", () -> (f() && (o()).c.L()));
    this.a.a("flagIsSystemFunction", () -> (C() instanceof Sql && ((Sql)C()).isSystem()));
    this.a.a("flagSupportUserDataType", () -> (l() && !(Dbms.get(this.f.getDbId())).userDataTypeDeclaration.q()));
    this.a.a("flagHasSelectedEntities", () -> !E().isEmpty());
    this.a.a("flagHasClipboard", () -> (this.f != null && Clipboard.getSystemClipboard().hasString()));
    this.g.getChildren().addAll((Object[])new Node[] { (Node)this.p, (Node)this.q, this.h = (Node)new FxWelcome(this) });
    this.q.getStyleClass().add("white-tool-bar");
    a();
    this.a.a(this.b.b);
    Log.a();
    if (License.a().c())
      this.a.a(new FxGetRenewedLicenseTask(this)); 
    getAccelerators().put(KeyCombination.keyCombination("shortcut+shift+F9"), () -> FxUtil.a(this));
    getAccelerators().put(KeyCombination.keyCombination("shortcut+shift+F10"), this::clearPreferences);
    getAccelerators().put(KeyCombination.keyCombination("shortcut+'+'"), () -> {
          if (o() != null)
            (o()).c.zoomIn(); 
        });
    getAccelerators().put(KeyCombination.keyCombination("shortcut+-"), () -> {
          if (o() != null)
            (o()).c.zoomOut(); 
        });
    getAccelerators().put(KeyCombination.keyCombination("shortcut+0"), () -> {
          if (o() != null)
            (o()).c.zoomNormal(); 
        });
    getAccelerators().put(KeyCombination.keyCombination("shortcut+1"), () -> {
          if (o() != null)
            (o()).c.showDestTable(); 
        });
    getAccelerators().put(KeyCombination.keyCombination("shortcut+shift+1"), () -> {
          if (o() != null)
            (o()).c.showSourceTable(); 
        });
    StageSizer.b(paramStage);
    paramStage.setScene(this);
    paramStage.getIcons().addAll((Object[])new Image[] { Rx.c("DbSchema32.png"), Rx.c("DbSchema48.png"), Rx.c("DbSchema64.png"), Rx.c("DbSchema128.png") });
    paramStage.setOnCloseRequest(paramWindowEvent -> {
          if (!e())
            paramWindowEvent.consume(); 
        });
    paramStage.show();
    paramStage.toFront();
    Timeline timeline = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(0.1D), paramActionEvent -> e((++this.r % 10 == 0)), new javafx.animation.KeyValue[0]) });
    timeline.setCycleCount(-1);
    timeline.play();
    QuickTour quickTour = new QuickTour(QuickTour$Type.b, false);
    if (quickTour.isShowTipsDialogOnStartup())
      Platform.runLater(() -> new FxTipsDialog(this, paramQuickTour)); 
    paramStage.addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (paramKeyEvent.isMetaDown() && paramKeyEvent.getCode() == KeyCode.Q && !e())
            paramKeyEvent.consume(); 
        });
  }
  
  private void K() {
    if (Keys.h.e() && System.currentTimeMillis() - this.v > 60000L) {
      this.v = System.currentTimeMillis();
      this.a.a(new FxFrame$1(this, this));
    } 
  }
  
  public Rx getRx() {
    return this.a;
  }
  
  protected Node b() {
    SplitPane splitPane1 = new SplitPane();
    splitPane1.setOrientation(Orientation.VERTICAL);
    splitPane1.getItems().addAll((Object[])new Node[] { (Node)this.i });
    if (this.f != null && !this.f.is(UnitProperty.g).booleanValue())
      splitPane1.getItems().addAll((Object[])new Node[] { (Node)this.u }); 
    splitPane1.getItems().addAll((Object[])new Node[] { (Node)this.t });
    splitPane1.setDividerPositions(new double[] { 0.7D, 0.85D });
    Dialog$.setRegionPrefWidth((Region)splitPane1, 220.0D);
    SplitPane.setResizableWithParent((Node)splitPane1, Boolean.FALSE);
    FxNewLayoutTab fxNewLayoutTab = new FxNewLayoutTab(this);
    this.j.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTab1, paramTab2) -> {
          if (paramTab2 == paramFxNewLayoutTab) {
            paramFxNewLayoutTab.a();
            if (this.j.getTabs().size() > 1)
              newLayout(); 
          } 
        });
    this.j.getTabs().add(fxNewLayoutTab);
    this.j.getStyleClass().add("underlined");
    SplitPane splitPane2 = new SplitPane();
    splitPane2.getItems().addAll((Object[])new Node[] { (Node)splitPane1, (Node)this.j });
    splitPane2.setDividerPositions(new double[] { 0.25D });
    Platform.runLater(() -> paramSplitPane.setDividerPositions(new double[] { 0.24D - 5.0E-5D * this.s.getWidth() }));
    VBox.setVgrow((Node)splitPane2, Priority.ALWAYS);
    return (Node)splitPane2;
  }
  
  public void b(boolean paramBoolean) {
    super.b(paramBoolean);
    this.i.a(this.f);
    this.a.G(this.f.getDbId());
    this.a.b();
    boolean bool = this.f.is(UnitProperty.g).booleanValue();
    this.b.a(this.p, bool);
    this.b.a(this.q, bool);
    this.b.c();
    this.b.d();
    a(true);
    if (this.f.hasFile())
      v(); 
    Keys.l.a(Boolean.valueOf(false));
  }
  
  public void c() {
    if (this.h instanceof FxWelcome) {
      this.g.getChildren().remove(this.h);
      this.g.getChildren().add(this.h = (Node)new FxWelcome(this));
    } 
    a();
  }
  
  public static void a(Project paramProject) {
    if (paramProject != null) {
      Stage stage = new Stage();
      FxFrame fxFrame = new FxFrame(stage);
      fxFrame.c(paramProject);
      fxFrame.b(true);
      for (Layout layout : paramProject.layouts)
        fxFrame.a(layout); 
      fxFrame.y();
      Objects.requireNonNull(stage);
      Platform.runLater(stage::toFront);
    } 
  }
  
  private String L() {
    return (l() ? (this.f.getTitle() + " - ") : "") + "DbSchema #" + (l() ? (this.f.getTitle() + " - ") : "") + Log.c;
  }
  
  private static String w = "Unknown";
  
  private DbmsLocation x;
  
  private boolean y;
  
  private final File z;
  
  public void a(DbmsLocation paramDbmsLocation) {
    this.x = paramDbmsLocation;
  }
  
  @Action
  public Task newProjectFromDb$() {
    return newProjectFromDb();
  }
  
  @Action
  public Task newProjectFromDb() {
    Log.d("Create project connected to database");
    Optional<FxChooseDbmsDialog$DbmsChoice> optional = (new FxChooseDbmsDialog(this, null)).showDialog();
    if (optional.isPresent()) {
      Optional<Connector> optional1 = (((FxChooseDbmsDialog$DbmsChoice)optional.get()).b() == null) ? (new FxConnectorEditor(this, ((FxChooseDbmsDialog$DbmsChoice)optional.get()).a(), this.x)).showDialog() : (new FxConnectorEditor(this, ((FxChooseDbmsDialog$DbmsChoice)optional.get()).b())).showDialog();
      if (optional1.isPresent()) {
        Connector connector = optional1.get();
        w = connector.dbId;
        Project project = new Project(connector.dbId, connector.dbId);
        connector.learnDbmsIfRequired();
        u();
        return new FxImportSelectionTask(this, project, connector);
      } 
    } 
    return null;
  }
  
  @Pro
  @Action
  public void newProjectOffline() {
    Log.d("Create offline project");
    (new FxProjectEditor(this, false, "physical")).showDialog();
  }
  
  @Pro
  @Action
  public void newProjectLogicalDesign() {
    Log.d("Create logical design project");
    (new FxProjectEditor(this, true, "logical")).showDialog();
  }
  
  @Action(b = "flagActiveLayout")
  public void print() {
    new FxPrintPreviewDialog(getWindow(), new PrintableDiagram((o()).d.getName(), (o()).c));
  }
  
  @Action
  public void appearance() {
    new FxAppearanceDialog(this);
  }
  
  @Action(b = "flagCanUndoDiagramEdit")
  public void undoDiagramEdit() {
    if (B())
      (o()).c.G(); 
  }
  
  @Action(b = "flagCanRedoDiagramEdit")
  public void redoDiagramEdit() {
    if (B())
      (o()).c.H(); 
  }
  
  @Action(b = "flagActiveLayout")
  public void zoomIn() {
    (o()).c.zoomIn();
  }
  
  @Action(b = "flagActiveLayout")
  public void zoomOut() {
    (o()).c.zoomOut();
  }
  
  @Action(b = "flagActiveLayout")
  public void zoomNormal() {
    (o()).c.zoomNormal();
  }
  
  @Action(b = "flagActiveLayout")
  public void zoomChoose() {
    (o()).c.zoomChoose();
  }
  
  @Action
  public void help() {
    WebBrowserExternal.a(this, "index.html");
  }
  
  @Action
  public void driversManager() {
    new FxDriversDialog(this);
  }
  
  @Action
  public void testTree() {
    (new FxLayoutEditor(this, m(), p())).showDialog();
  }
  
  @Action
  public void settings() {
    (new FxSettingsDialog(this)).showDialog();
  }
  
  @Pro
  @Action
  public void openProjectFile() {
    File file = FxFileChooser.a(this, "Open Project File", (String)null, FileOperation.a, new FileType[] { FileType.a, FileType.b });
    if (file != null) {
      FxFrame fxFrame = d();
      fxFrame.getRx().a(new FxProjectLoaderTask(fxFrame, file));
    } 
  }
  
  @Action
  public void openSqlFile() {
    File file = j() ? FxFileChooser.a(this, "Open Project File", (String)null, FileOperation.a, new FileType[] { FileType.i, FileType.D }) : FxFileChooser.a(this, "Open Project File", (String)null, FileOperation.a, new FileType[] { FileType.r, FileType.i, FileType.D });
    if (file != null)
      d().a(file); 
  }
  
  private void a(File paramFile) {
    boolean bool = !l() ? true : false;
    if (!l())
      b(new Project(paramFile.getName())); 
    FxLayoutPane fxLayoutPane = q();
    FxSqlEditor fxSqlEditor = fxLayoutPane.a(fxLayoutPane.d.createScript(paramFile.getName()));
    q().a(fxSqlEditor);
    this.a.a(fxSqlEditor.a(paramFile, () -> {
            if (paramBoolean)
              (new FxSqlEditorOpenFileDialog(paramFxLayoutPane, paramFxSqlEditor)).showDialog(); 
          }));
  }
  
  public FxFrame d() {
    return l() ? new FxFrame(new Stage()) : this;
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public Task saveProjectAs() {
    return c(true) ? N() : null;
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public Task saveProject() {
    return c(false) ? N() : null;
  }
  
  @Pro
  @Action(b = "flagShouldSave")
  public Task saveProjectIfNotSaved() {
    return c(false) ? N() : null;
  }
  
  private void M() {
    if (this.f != null)
      FxUtil.a(this.f.layouts); 
    for (Tab tab : this.j.getTabs()) {
      Node node = tab.getContent();
      if (node instanceof FxLayoutPane) {
        FxLayoutPane fxLayoutPane = (FxLayoutPane)node;
        fxLayoutPane.i();
        Pref.a("open:" + fxLayoutPane.j().getKey(), this.j.getTabs().indexOf(tab));
      } 
    } 
  }
  
  public boolean c(boolean paramBoolean) {
    if (l()) {
      M();
      boolean bool = (paramBoolean || this.f.getFile() == null || !this.f.getFile().canWrite()) ? true : false;
      try {
        if (Sys.B.validateBeforeSave.b() && !InspectorRoot.a(m()) && !(new FxValidationResultDialog(this, true, InspectorRoot.d)).showDialogIsResultOkDone())
          return false; 
      } catch (Exception exception) {
        Log.b(exception);
      } 
      Log.c("Save project" + (bool ? " as..." : ""));
      if (bool) {
        File file = FxFileChooser.a(this, "Save Project to File", (this.f.getFile() != null) ? this.f.getFile().getParent() : null, FileOperation.b, new FileType[] { FileType.a });
        if (file == null)
          return false; 
        this.f.setFile(file);
      } 
      return true;
    } 
    return false;
  }
  
  private Task N() {
    return new FxFrame$2(this);
  }
  
  @Action(b = "flagActiveProject")
  public void projectProperties() {
    FxEditorFactory.a(this, this.f);
  }
  
  @Action(b = "flagActiveLayout")
  public void printPng() {
    (o()).c.printPng();
  }
  
  @Action(b = "flagActiveLayout")
  public void printPdf() {
    (o()).c.printPdf();
  }
  
  @Action(b = "flagActiveLayout")
  public void printSVG() {
    (o()).c.printSVG();
  }
  
  @Action(b = "flagActiveLayout")
  public void printGif() {
    (o()).c.printGif();
  }
  
  @Action(b = "flagActiveLayout")
  public void printJpg() {
    (o()).c.printJpg();
  }
  
  @Action(b = "flagActiveLayout")
  public void exportDocumentation() {
    (o()).c.documentation();
  }
  
  @Pro
  @Action(b = "flagActiveLayout")
  public void dataGenerator() {
    new FxGeneratorEditor(this, p().getEntities());
  }
  
  @Action
  public void evaluatePro() {
    WebBrowserExternal.a(this, "https://dbschema.com/evaluate.html");
  }
  
  public boolean e() {
    if (Keys.l.d())
      switch (FxFrame$8.a[this.a.b(this, "introTutorials", new String[0]).c().ordinal()]) {
        case 1:
          Platform.runLater(() -> (new FxVideoDialog(this, null)).showDialog());
          return false;
        case 2:
          Platform.runLater(() -> this.a.a(SQLTutorial()));
          return false;
      }  
    boolean bool = (Pref.c("knw", 2) && License.a().g() != Features.a) ? true : false;
    if (bool)
      try {
        Snapshot.a(this);
        Rx.c.invokeAll(List.of(() -> {
                TechnicalSupportTask.b((this.f != null) ? this.f.getDbId() : w, "Feedback");
                return null;
              }));
      } catch (Throwable throwable) {} 
    M();
    StageSizer.a(this.s);
    if (this.f != null && this.f.getEntityCount() > 0 && License.a().e() && (this.f.getFile() == null || w()))
      switch (FxFrame$8.a[this.a.b(this, "saveProjectDialog", new String[0]).b("Save project '" + this.f.getName() + "' changes to file ? ").c().ordinal()]) {
        case 1:
          if (c(false))
            try {
              n();
            } catch (Throwable throwable) {
              Log.c(throwable);
              this.a.b(this, "Error saving file: " + throwable.getLocalizedMessage(), throwable);
              return false;
            }  
          break;
        case 2:
          break;
        default:
          return false;
      }  
    if (this.f != null) {
      J();
      byte b = 0;
      for (Connector connector : ConnectorManager.getConnectors(this.f.getDbId())) {
        if (connector.isProcessing())
          b++; 
      } 
      if (b > 0 && !this.a.a(this, "" + b + " database connections are active.\nProceed and close them ?"))
        return false; 
      for (Connector connector : ConnectorManager.getConnectors(this.f.getDbId())) {
        Objects.requireNonNull(connector);
        Rx.c.submit(connector::closeAllEnvoysAndSsh);
      } 
      this.a.e();
      c.a(this.f.getFile());
    } 
    if (Keys.j.e())
      try {
        FloatingLicenseTask.a(true);
      } catch (Exception exception) {
        Log.b(exception);
      }  
    this.j.getTabs().clear();
    b((Connector)null);
    a((FxLayoutPane)null);
    this.f = null;
    return true;
  }
  
  @Action
  public void exitDbSchema() {
    for (Window window : new ArrayList((Collection<?>)Window.getWindows())) {
      if (window instanceof Stage && window.getScene() instanceof FxFrame) {
        if (((FxFrame)window.getScene()).e()) {
          window.hide();
          continue;
        } 
        return;
      } 
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void closeProject() {
    if (Window.getWindows().size() == 1) {
      if (e()) {
        if (this.h != null)
          this.g.getChildren().remove(this.h); 
        this.g.getChildren().add(this.h = (Node)new FxWelcome(this));
      } 
    } else {
      this.s.close();
    } 
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public Task synchronizeFile() {
    M();
    File file = FxFileChooser.a(this, "Open Project File", FileOperation.a, this.f.getFile(), new FileType[] { FileType.a });
    if (file == null)
      return null; 
    return new FxFrame$3(this, this, file);
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void synchronizeCommit() {
    M();
    boolean bool = this.f.is(UnitProperty.g).booleanValue();
    Connector connector = a(this);
    if (connector != null) {
      if (!bool)
        b(connector); 
      if (Sys.B.readOnly.b() || connector.isReadOnly()) {
        this.a.c(getDialogScene(), "readOnlyConnectionWarning");
        return;
      } 
      Project project = m();
      if (bool)
        project = FxConversionDictionaryDialog.a(this, project, project.getDbId()); 
      if (project != null)
        new FxCreateOrUpgradeSchemaInDatabaseTask(this, project, connector); 
    } 
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void synchronizeSchemas() {
    Unit unit = C();
    if (unit instanceof Schema) {
      Schema schema = (Schema)unit;
      ArrayList arrayList = new ArrayList(this.f.schemas);
      arrayList.remove(schema);
      ChoiceDialog choiceDialog = new ChoiceDialog(null, arrayList);
      choiceDialog.initOwner(getWindow());
      this.a.a((Dialog)choiceDialog, "synchronizeSchemasChoice");
      Optional optional = choiceDialog.showAndWait();
      optional.ifPresent(paramSchema2 -> {
            SyncPair syncPair = new SyncPair(paramSchema1, paramSchema2);
            syncPair.filter(());
            Platform.runLater(());
          });
    } 
  }
  
  @Action(b = "flagActiveProject")
  public Task synchronizeRefresh() {
    return synchronizeCompare();
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public Task synchronizeCompare() {
    M();
    if (ConnectorManager.getConnectors(this.f.getDbId()).isEmpty() && this.f.hasEntities())
      switch (FxFrame$8.a[this.a.b(this, "refreshOrCreateSchemaDialog", new String[0]).c().ordinal()]) {
        case 1:
          synchronizeCommit();
          return null;
        case 2:
          break;
        default:
          return null;
      }  
    this.a.a(FxDbSynchronizationTask.class);
    Connector connector = a(this);
    return (connector != null && c(connector)) ? new FxDbSynchronizationTask(this, m(), connector, SyncMode.a, false, null, false) : null;
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public Task synchronizeCompareSelected() {
    return f(true);
  }
  
  private Task f(boolean paramBoolean) {
    M();
    Connector connector = a(this);
    if (connector != null && c(connector)) {
      ArrayList arrayList = new ArrayList(D());
      if (arrayList.isEmpty())
        arrayList.addAll(this.f.schemas); 
      this.a.a(FxDbSynchronizationTask.class);
      return new FxDbSynchronizationTask(this, m(), connector, SyncMode.a, paramBoolean, arrayList, false);
    } 
    return null;
  }
  
  @Action(b = "flagActiveProject")
  public Task synchronizeRefreshItem() {
    return f(false);
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void syncSettings() {
    (new FxSyncSettingsDialog(this)).showDialog();
  }
  
  private boolean c(Connector paramConnector) {
    if (m().is(UnitProperty.g).booleanValue())
      return true; 
    if (!m().getDbId().equals(paramConnector.dbId)) {
      if (this.a.a(this, String.format("The DbSchema project is defined for '%s' while the database is '%s'.\nFrom 'Model / Properties' you should first set the model correct database.\nContinue current operation ?", new Object[] { m().getDbId(), paramConnector.dbId })));
      return false;
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void exportSchemaScript() {
    M();
    new FxExportSchemaAndDataDialog(this, this.f);
  }
  
  @Action(b = "flagActiveProject")
  public void unitProperties() {
    Unit unit = C();
    FxEditorFactory.a(this, unit);
  }
  
  @Action(d = "flagAutoRefreshSchema", b = "flagActiveProject")
  public void autoRefreshSchema() {
    Sys.B.autoRefresh.a(!Sys.B.autoRefresh.b());
    Sys.B.root.j();
    this.a.b();
  }
  
  @Action(b = "flagActiveProject")
  public void newBrowseEditor() {
    FxEditorFactory.a(this, (q()).d.browses);
    u();
  }
  
  @Action(b = "flagActiveProject")
  public void newQueryEditor() {
    FxEditorFactory.a(this, (q()).d.queries);
    u();
  }
  
  @Action(b = "flagActiveProject")
  public void browseSelected() {
    q().a(D(), true);
    u();
  }
  
  @Action(b = "flagActiveProject")
  public void browseFromSelection() {
    q().a(D(), true);
    u();
  }
  
  @Action(b = "flagActiveProject")
  public void sqlSelected() {
    q().a(D(), StatementType.d, false);
    u();
  }
  
  @Action(b = "flagActiveProject")
  public void querySelected() {
    q().b(D(), true);
    u();
  }
  
  @Action(b = "flagActiveProject")
  public void queryFromSelection() {
    q().b(D(), true);
    u();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowFkColumns")
  public void showFkColumns() {
    (o()).c.showFkColumns();
    u();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowFkName")
  public void showFkName() {
    (o()).c.showFkName();
    u();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowFkCascade")
  public void showFkCascade() {
    (o()).c.showFkCascade();
    u();
  }
  
  @Action(b = "flagActiveLayout", d = "flagJoinedRouting")
  public void joinedRouting() {
    (o()).c.joinedRouting();
    u();
  }
  
  @Action(b = "flagActiveLayout", d = "flagDeducedFks")
  public void deducedFks() {
    (o()).c.deducedFks();
    u();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowPageBorders")
  public void showPageBorders() {
    (o()).c.showPageBorders();
    if ((o()).c.a.isShowPageBorders() && FxPrintPreviewDialog.a() < 10.0D)
      print(); 
    u();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowSchemaName")
  public void showSchemaName() {
    (o()).c.showSchemaName();
    u();
  }
  
  @Action(b = "flagActiveProject", d = "flagSchemaPrefixTable")
  public void schemaPrefixTable() {
    Dbms dbms = Dbms.get(this.f.getDbId());
    if (dbms.prefixTableWithSchema.b() && !this.a.a(this, "confirmDisablePrefixTable"))
      return; 
    dbms.prefixTableWithSchema.a();
    dbms.root.j();
  }
  
  @Action(b = "flagActiveProject", d = "flagCatalogPrefixSchema")
  public void catalogPrefixSchema() {
    Dbms dbms = Dbms.get(this.f.getDbId());
    dbms.prefixSchemaWithCatalog.a();
    dbms.root.j();
    this.i.a();
  }
  
  @Action(b = "flagActiveProject", d = "flagAutoLetterCases")
  public void autoLetterCases() {
    Dbms dbms = Dbms.get(this.f.getDbId());
    dbms.autoLetterCases.a();
    dbms.root.j();
  }
  
  @Action(b = "flagActiveProject")
  public void editTable() {
    editUnit();
  }
  
  @Action(b = "flagActiveProject")
  public void editUnit() {
    Unit unit = C();
    FxEditorFactory.a(this, unit);
  }
  
  @Action(b = "flagActiveProject")
  public void excludeFromLayout() {
    boolean bool = false;
    for (Unit unit : D()) {
      if (unit instanceof LayoutDepict) {
        LayoutDepict layoutDepict = (LayoutDepict)unit;
        layoutDepict.a.markForDeletion();
        bool = true;
      } 
    } 
    if (bool) {
      u();
      y();
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void editDescription() {
    Unit unit = C();
    (new FxCommentEditor(this, unit)).showDialog();
  }
  
  @Action(b = "flagOfflineOrLogicalDesign")
  public void gridEditor() {
    (new FxGridEditor(this, D())).showDialog();
  }
  
  @Action(b = "flagActiveLayout")
  public void showAllColumns() {
    if (o() != null) {
      for (Entity entity : E()) {
        Depict depict = (o()).c.a.getDepictFor(entity);
        if (depict != null)
          depict.showAllAttributes(); 
      } 
      (o()).c.k();
    } 
  }
  
  @Action(b = "flagActiveLayout")
  public void hideAllColumns() {
    if (o() != null) {
      for (Entity entity : E()) {
        Depict depict = (o()).c.a.getDepictFor(entity);
        if (depict != null)
          depict.hideAllAttributes(); 
      } 
      (o()).c.k();
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void deleteUnit() {
    List list = D();
    String str = SqlUtil.getUnitNames(list);
    if (this.a.a(this, "Drop " + str + " ?", "Drop"))
      a(list); 
  }
  
  private void a(List paramList) {
    try {
      boolean bool = false;
      for (Unit unit : paramList) {
        if (unit instanceof Column || unit instanceof Table || unit instanceof Schema) {
          bool = true;
          break;
        } 
      } 
      if (bool && t() && !this.a.a(this, "This operation may lead to data loss. Continue ?"))
        return; 
      FxFrame$4 fxFrame$4 = new FxFrame$4(this, paramList);
      this.a.a(fxFrame$4);
    } catch (Exception exception) {
      Log.a("Error deleting units", exception);
      this.a.a(this, exception);
    } 
    y();
  }
  
  @Action(b = "flagActiveProject")
  public void generateSelectSql() {
    q().a(D(), StatementType.d, true);
  }
  
  @Action(b = "flagActiveProject")
  public void generateMergeSql() {
    q().a(D(), StatementType.e, true);
  }
  
  @Action(b = "flagActiveLayout")
  public void generateDeleteSql() {
    q().a(D(), StatementType.c, true);
  }
  
  @Action(b = "flagActiveLayout")
  public void generateUpdateSql() {
    q().a(D(), StatementType.b, true);
  }
  
  @Action(b = "flagActiveLayout")
  public void generateCreateSql() {
    q().c(C());
  }
  
  @Action(d = "flagGenerateToClipboard")
  public void generateSqlToClipboard() {
    Sys.B.generateSqlToClipboard.a();
    Sys.B.root.j();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowDataType")
  public void showDataType() {
    (o()).c.showDataType();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowPhysicalName")
  public void showPhysicalName() {
    (o()).c.showPhysicalName();
  }
  
  @Action(b = "flagActiveLayout", d = "flagShowPhysicalDictionaryName")
  public void showPhysicalDictionaryName() {
    (o()).c.showPhysicalDictionaryName();
  }
  
  @Action(b = "flagActiveLayout")
  public void groupsManager() {
    (new FxGroupsEditor(this, this.f, (o()).d)).showDialog();
  }
  
  public boolean f() {
    return (o() != null);
  }
  
  @Action(b = "flagActiveProject")
  public void newLayout() {
    FxLayoutEditor fxLayoutEditor = new FxLayoutEditor(this, this.f, null);
    fxLayoutEditor.a(E());
    if (fxLayoutEditor.showDialogIsResultOkDone()) {
      Layout layout = (Layout)fxLayoutEditor.a();
      a(layout);
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void newEmptyLayout() {
    a(this.f.createLayout((String)null));
  }
  
  @Action(b = "flagActiveLayout")
  public void layoutProperties() {
    FxEditorFactory.a(this, p());
  }
  
  @Action(b = "flagActiveProject")
  public void activateLayoutChoose() {
    ChoiceDialog choiceDialog = new ChoiceDialog(null, this.f.layouts);
    this.a.a((Dialog)choiceDialog, "chooseLayoutDialog");
    choiceDialog.showAndWait().ifPresent(this::a);
  }
  
  @Action(b = "flagActiveProject")
  public void activateLayout() {
    Unit unit = C();
    if (unit instanceof Layout)
      a((Layout)unit); 
  }
  
  @Action(b = "flagActiveLayout")
  public void duplicateLayout() {
    Layout layout;
    if (C() instanceof Layout) {
      layout = (Layout)C();
    } else {
      layout = p();
    } 
    if (layout != null) {
      Layout layout1 = this.f.cloneLayout(layout);
      a(layout1);
    } 
  }
  
  @Action(b = "flagActiveLayout")
  public void documentation() {
    (o()).c.documentation();
  }
  
  @Action(b = "flagActiveLayout")
  public void autoplace() {
    (o()).c.autoplace();
    u();
  }
  
  @Action(b = "flagActiveNotEmptyProject")
  public void manageVirtualForeignKeys() {
    (new FxVirtualForeignKeysDialog(this, false)).showDialog();
  }
  
  @Action(d = "flagNotationBarker", b = "flagActiveProject")
  public void notationBarker() {
    this.f.setNotation(Project.barkerNotation);
    y();
    this.a.b();
    u();
  }
  
  @Action(d = "flagNotationIEWithArrows", b = "flagActiveProject")
  public void notationIEWithArrows() {
    this.f.setNotation(Project.ieWithArrowsNotation);
    y();
    this.a.b();
    u();
  }
  
  @Action(d = "flagNotationIdef1x", b = "flagActiveProject")
  public void notationIdef1x() {
    this.f.setNotation(Project.idef1xNotation);
    y();
    this.a.b();
    u();
  }
  
  @Action(d = "flagNotationIE", b = "flagActiveProject")
  public void notationIE() {
    this.f.setNotation(Project.ieNotation);
    y();
    this.a.b();
    u();
  }
  
  @Action(d = "flagNotationStrict", b = "flagActiveProject")
  public void notationStrict() {
    this.f.setNotationStrict(!this.f.isNotationStrict());
    y();
    this.a.b();
    u();
  }
  
  @Action(b = "flagActiveProject")
  public void newConnection() {
    (new FxConnectorEditor(this, (this.f != null) ? this.f.getDbId() : null, DbmsLocation.a)).d().showDialog().ifPresent(paramConnector -> {
          b(paramConnector);
          g();
        });
  }
  
  @Action(b = "flagOnline")
  public void editConnector() {
    (new FxConnectorEditor(this, s())).showDialog();
  }
  
  @Pro
  @Action(b = "flagOnline")
  public void editSchemaMapping() {
    (new FxSchemaMappingDialog(this, s())).showDialog();
  }
  
  @Action(b = "flagActiveProject")
  public void newTable() {
    FxTableEditor fxTableEditor = new FxTableEditor(this, this.f, t());
    fxTableEditor.setHeaderText(this.a.H("newTableHeader"));
    fxTableEditor.showDialog();
  }
  
  @Action(b = "flagActiveProject")
  public void newColumn() {
    Unit unit = C();
    if (unit instanceof Entity) {
      (new FxColumnEditor(this, (Entity)unit, t())).showDialog();
      y();
    } else {
      this.a.d(this, "Please select a table.");
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void addSubEntity() {
    Unit unit = C();
    if (unit instanceof Entity) {
      FxColumnEditor fxColumnEditor = new FxColumnEditor(this, (Entity)unit, t());
      fxColumnEditor.a(DbmsTypes.get("LogicalDesign").getDataType(4999544), 0);
      fxColumnEditor.showDialog();
      y();
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void addColumn() {
    newColumn();
  }
  
  @Action(b = "flagActiveProject")
  public void newFunction() {
    a(Function.class);
  }
  
  @Action(b = "flagActiveProject")
  public void newProcedure() {
    a(Procedure.class);
  }
  
  @Action(b = "flagActiveProject")
  public void newTrigger() {
    a(Trigger.class);
  }
  
  @Action(b = "flagActiveProject")
  public void newSequence() {
    a(Sequence.class);
  }
  
  @Action(b = "flagActiveLayout")
  public void newCallout() {
    (new FxCalloutEditor(this, C())).showDialog();
  }
  
  @Action(b = "flagActiveLayout")
  public void newShape() {
    (new FxShapeEditor(this, null)).showDialog();
  }
  
  private void a(Class<Trigger> paramClass) {
    if (this.f.schemas.isEmpty()) {
      this.a.c(this, "Please create at least one schema in the model");
    } else {
      Schema schema = null;
      if (this.f.schemas.size() == 1) {
        schema = (Schema)this.f.schemas.get(0);
      } else {
        ChoiceDialog choiceDialog = new ChoiceDialog(null, this.f.schemas);
        this.a.a((Dialog)choiceDialog, "chooseSchemaDialog");
        choiceDialog.initOwner(getWindow());
        Optional<Schema> optional = choiceDialog.showAndWait();
        if (optional.isPresent())
          schema = optional.get(); 
      } 
      if (schema != null)
        if (paramClass == Trigger.class) {
          FxEditorFactory.a(this, schema.triggers);
        } else if (paramClass == Procedure.class) {
          FxEditorFactory.a(this, schema.procedures);
        } else if (paramClass == Function.class) {
          FxEditorFactory.a(this, schema.functions);
        } else if (paramClass == Sequence.class) {
          FxEditorFactory.a(this, schema.sequences);
        }  
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void insertDocument() {
    generateInsertSql();
  }
  
  @Action(b = "flagActiveProject")
  public void createCollectionAndInsertDocument() {
    generateInsertSql();
  }
  
  @Action(b = "flagActiveProject")
  public void newView() {
    (new FxViewEditor(this, this.f, View.class)).showDialog();
  }
  
  @Action(b = "flagSupportUserDataType")
  public void newUserDataType() {
    (new FxUserDataTypeEditor(this, this.f)).showDialog();
  }
  
  @Action(b = "flagActiveProject")
  public void newSqlEditor() {
    FxEditorFactory.a(this, (q()).d.scripts);
  }
  
  @Action(b = "flagActiveProject")
  public void newSqlEditorNoText() {
    newSqlEditor();
  }
  
  @Action(b = "flagActiveProject")
  public void sqlFromSelection() {
    q().a(D(), StatementType.d, false);
  }
  
  public void g() {
    ButtonType buttonType1 = new ButtonType("Refresh Schema(s) from Database");
    ButtonType buttonType2 = new ButtonType("Create or Upgrade Schema(s) in Database");
    ButtonType buttonType3 = new ButtonType("Skip", ButtonBar.ButtonData.CANCEL_CLOSE);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.initOwner(getWindow());
    alert.getButtonTypes().setAll((Object[])new ButtonType[] { buttonType1, buttonType2, buttonType3 });
    this.a.a((Dialog)alert, "refreshSchemaDialog");
    alert.showAndWait().ifPresent(paramButtonType3 -> {
          if (paramButtonType3 == paramButtonType1) {
            this.a.a(synchronizeCompareSelected());
          } else if (paramButtonType3 == paramButtonType2) {
            synchronizeCommit();
          } 
        });
  }
  
  @Action(b = "flagActiveLayout")
  public void generateInsertSql() {
    o().a(D(), StatementType.a, true);
  }
  
  @Action(b = "flagActiveProject")
  public void manageDrivers() {
    new FxDriversDialog(this);
  }
  
  @Action(b = "flagActiveProject")
  public void manageConnections() {
    (new FxConnectorManagerDialog(this, false)).showDialog().ifPresent(this::a);
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void dataLoader() {
    Table table = (C() instanceof Table) ? (Table)C() : null;
    if (table == null && this.f != null && this.f.schemas.isEmpty()) {
      this.a.c(this, "Project should contain at least one schema");
      return;
    } 
    new FxLoaderFileDialog(this, (C() instanceof Table) ? (Table)C() : null, LoaderMode.a);
  }
  
  @Pro
  @Action
  public void importModel() {
    new FxLoaderFileDialog(this, null, LoaderMode.b);
  }
  
  @Action(b = "flagCanReorderColumns")
  public void orderColumnsAlphabetically() {
    Unit unit = C();
    if (unit instanceof Table)
      ((Table)unit).columns.orderAlphabetically(); 
    y();
  }
  
  @Action(b = "flagActiveProject")
  public void cloneUnit() {
    Unit unit = C();
    if (unit instanceof Table)
      SyncUtil.a(this, (Table)unit); 
  }
  
  @Action(b = "flagActiveProject")
  public void projectDependencies() {
    (new FxDependencyDialog(this, new DependencyNode(m()), false)).showDialog();
  }
  
  @Action(b = "flagActiveProject")
  public void dependencies() {
    Unit unit = C();
    if (unit instanceof Table)
      (new FxDependencyDialog(this, new DependencyNode(null, (AbstractTable)unit, true), false)).showDialog(); 
  }
  
  @Action(b = "flagActiveProject")
  public void addColumnToIndex() {
    ArrayList<Column> arrayList = new ArrayList();
    AbstractTable abstractTable = null;
    for (Unit unit : D()) {
      if (unit instanceof Column) {
        Column column = (Column)unit;
        if (column.getEntity() instanceof Table && (abstractTable == null || column.getEntity() == abstractTable)) {
          arrayList.add((Column)unit);
          abstractTable = column.getEntity();
        } 
      } 
    } 
    if (abstractTable != null && !arrayList.isEmpty())
      (new FxIndexEditor(this, (Table)abstractTable, arrayList, t())).showDialog(); 
  }
  
  @Action(b = "flagActiveProject")
  public void newIndex() {
    Unit unit = C();
    if (unit.getEntity() instanceof Table)
      (new FxIndexEditor(this, (Table)unit.getEntity(), true)).showDialog(); 
  }
  
  @Action(b = "flagActiveProject")
  public void newRelationFromEntity() {
    newRelation();
  }
  
  @Action(b = "flagActiveProject", d = "flagCreateRelationMode")
  public void newRelation() {
    Entity entity = (C() != null) ? C().getEntity() : null;
    if (entity instanceof Table) {
      FxForeignKeyEditor fxForeignKeyEditor = new FxForeignKeyEditor(this, (Table)entity, null, true);
      fxForeignKeyEditor.setHeaderText(this.a.H("newForeignKeyHeader"));
      fxForeignKeyEditor.showDialog();
    } else if (o() == null) {
      this.a.a(this, "Create foreign keys by dragging a column onto a primary key column.\nAlternatively you can double-click the table header to open the editing dialog, select the foreign key tab create it here.", new String[0]);
    } else {
      (o()).c.M();
      this.a.d(this, "createRelationBalloon");
    } 
  }
  
  @Action(b = "flagActiveLayout")
  public void unGroup() {
    o().a(E());
  }
  
  @Action(b = "flagActiveLayout")
  public void addTableToGroup() {
    o().a(E());
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void editGeneratorPattern() {
    Unit unit = C();
    if (unit instanceof Entity) {
      new FxGeneratorTableDialog(this, (Entity)unit, true);
    } else if (unit instanceof Column) {
      Column column = (Column)unit;
      Objects.requireNonNull(column);
      (new FxGeneratorPatternDialog(this, column, column.getOrGuessGeneratorPattern(), true)).showDialog().ifPresent(column::setGeneratorPattern);
    } 
  }
  
  @Action(b = "flagActiveProject")
  public void newLayoutFromGroup() {
    Unit unit = C();
    if (unit instanceof Group) {
      Group group = (Group)unit;
      String str = this.a.b(this, "Layout name", group.getName());
      if (str != null) {
        if (this.f.getLayout(str) != null) {
          this.a.a(this, "Layout '" + str + "' already exists !", new String[0]);
          return;
        } 
        Layout layout = this.f.createLayout(str);
        for (Depict depict : group.getDepicts())
          layout.diagram.attach(depict.getEntity(), depict.getPosition().a(), depict.getPosition().b()); 
        layout.refresh();
        a(layout);
      } 
    } 
  }
  
  @Action
  public void intabulare() {
    new FxRegistrationDialog(this);
  }
  
  @Action
  public Task detabulare() {
    Alert$ alert$ = this.a.b(this, "detabulareDialog", new String[0]);
    if (alert$.c() == ButtonBar.ButtonData.YES) {
      License license = License.a();
      if (license.l())
        return new FxFrame$5(this, ActivationTask$Mode.b); 
      O();
    } 
    return null;
  }
  
  @Action(b = "flagHasSelectedEntities")
  public void clipboardCopySQL() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Unit unit : D()) {
      if (unit instanceof AbstractUnit) {
        AbstractUnit abstractUnit = (AbstractUnit)unit;
        SyncPair syncPair = new SyncPair(null, abstractUnit);
        syncPair.setAlwaysIncludeFks(true);
        syncPair.synchronize();
        String str = "LogicalDesign".equals(this.f.getDbId()) ? "MySql" : this.f.getDbId();
        stringBuilder.append(syncPair.generateCommitScript(str, null, SyncSide.right)).append("\n\n");
      } 
    } 
    FxUtil.c(stringBuilder.toString());
    this.a.d(this, "SQL Definition saved to Clipboard");
  }
  
  @Action(b = "flagHasClipboard")
  public void clipboardPasteSQL() {
    (new FxClipboardPasteSQLDialog(this)).showDialog();
  }
  
  private void O() {
    License.b();
    c();
    this.a.a(this, "detabulareDone", new String[0]);
  }
  
  @Action
  public void aboutDialog() {
    new FxAboutDialog(getWindow());
  }
  
  @Action(b = "flagActiveLayout")
  public void newGroup() {
    (new FxGroupEditor(this, (p()).diagram, E())).showDialog();
  }
  
  @Action
  public void tipsTricks() {
    new FxTipsDialog(this, new DbsTips());
  }
  
  @Action
  public void outputLogs() {
    new FxOutputLogsDialog(this);
  }
  
  @Action
  public void shortcutsManager() {
    new FxShortcutsDialog(this, this.a);
  }
  
  @Action
  public void blogArticle() {
    (new FxChooseArticleDialog(this)).showDialog();
  }
  
  public void a(AbstractUnit paramAbstractUnit) {
    a(paramAbstractUnit, false, false);
  }
  
  public void a(AbstractUnit paramAbstractUnit, boolean paramBoolean1, boolean paramBoolean2) {
    boolean bool = false;
    if (paramAbstractUnit != null && B()) {
      if (paramBoolean1 && paramAbstractUnit instanceof AbstractTable) {
        (o()).c.a((AbstractTable)paramAbstractUnit);
        (o()).c.k();
      } 
      bool = (o()).c.c(paramAbstractUnit);
    } 
    if (!bool && paramBoolean2)
      for (Layout layout : this.f.layouts) {
        if (layout.diagram.getDepictFor(paramAbstractUnit) != null) {
          a(layout);
          (o()).c.c(paramAbstractUnit);
        } 
      }  
  }
  
  @Action(b = "flagActiveProject")
  public void search() {
    new FxFindDialog(this, this.f.schemas);
  }
  
  @Action(b = "flagActiveProject")
  public void findInPath() {
    new FxFindDialog(this, (C() != null) ? (TreeUnit)C() : this.f.schemas);
  }
  
  @Action(b = "flagActiveProject")
  public void newUnitInFolder() {
    Unit unit = C();
    if (unit instanceof Folder)
      FxEditorFactory.a(this, (Folder)unit); 
  }
  
  @Action(b = "flagActiveProject")
  public void convertProject() {
    a(FxConversionDictionaryDialog.a(this, this.f));
  }
  
  @Action(b = "flagActiveProject")
  public void conversionDictionary() {
    (new FxConversionDictionaryDialog(this, this.f)).showDialog();
  }
  
  @Action(b = "flagActiveProject")
  public void namingDictionary() {
    (new FxNamingDictionaryDialog(this, NamingDictionary.c)).showDialog();
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void modelValidation() {
    (new FxInspectorDialog(this, Mode.a)).showDialog();
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void modelExporter() {
    (new FxInspectorDialog(this, Mode.b)).showDialog();
  }
  
  @Action
  public void expandChildren() {
    this.i.e();
  }
  
  @Action
  public void quickTour() {
    new FxTipsDialog(this, P());
  }
  
  @Action
  public void videoTour() {
    (new FxVideoDialog(this, null)).showDialog();
  }
  
  private QuickTour P() {
    QuickTour$Type quickTour$Type = QuickTour$Type.b;
    if (this.f != null && this.f.is(UnitProperty.f).booleanValue())
      quickTour$Type = QuickTour$Type.a; 
    return new QuickTour(quickTour$Type, false);
  }
  
  @Action
  public void clearPreferences() {
    if ("clear".equalsIgnoreCase(this.a.b(this, "clearPreferencesDialog"))) {
      Pref.a();
      this.a.a(this, "Done", new String[0]);
    } 
  }
  
  @Action
  public void technicalSupport() {
    (new FxTechnicalSupportDialog(getWindow())).showDialog();
  }
  
  @Action
  public void renameUnit() {
    Unit unit = C();
    if (unit instanceof AbstractUnit) {
      String str = this.a.b(this, "renameUnit", unit.getName() + "_2");
      if (StringUtil.isFilledTrim(str))
        ((AbstractUnit)unit).rename(str); 
    } 
  }
  
  @Action
  public void virtualFkToNormal() {
    if (C() instanceof ForeignKey) {
      ForeignKey foreignKey = (ForeignKey)C();
      foreignKey.setVirtual(false);
      x();
    } 
  }
  
  @Action(d = "flagIsSystemFunction")
  public void markSystemFunction() {
    Unit unit = C();
    if (unit instanceof Sql) {
      Sql sql = (Sql)unit;
      sql.setIsSystem(!sql.isSystem());
    } 
  }
  
  @Action
  public void groovyScripts() {
    (new FxAutomationScriptsDialog(this)).showDialog();
  }
  
  @Action
  public void convertImportSchema() {
    (new FxConvertImportSchemaDialog(this)).showDialog();
  }
  
  public void h() {
    b((Connector)null);
    A();
    if (!License.a().e())
      getRx().a(this, "warnOfflineInFreeEdition", new String[0]); 
  }
  
  @Action
  public Task checkForUpdates() {
    return new CheckForUpdatesTask(this);
  }
  
  @Action
  public void checkForUpdatesSettings() {
    (new FxCheckForUpdatesDialog(this)).showDialog();
  }
  
  @Pro
  @Action
  public void git() {
    (new FxGitDialog(this, this.f)).showDialog();
  }
  
  @Action(b = "flagActiveProject")
  public void csvEditor() {
    q().a(new CsvToolEditor(this));
  }
  
  @Pro
  @Action
  public void ppgManager() {
    (new FxPPGDialog(getWindow())).showDialog();
  }
  
  @Pro
  @Action
  public void sparkPost() {
    (new FxSparkPostDialog(getWindow())).showDialog();
  }
  
  @Pro
  @Action
  public void amazonSES() {
    (new FxAmazonSESDialog(getWindow())).showDialog();
  }
  
  @Pro
  @Action
  public void textToSpeech() {
    (new FxTextToSpeech(getWindow())).showDialog();
  }
  
  @Action
  public Task refreshAddOnFolder() {
    if (getWorkspace().t()) {
      Unit unit = C();
      if (unit instanceof PropertyAddOnFolder) {
        PropertyAddOnFolder propertyAddOnFolder = (PropertyAddOnFolder)unit;
        propertyAddOnFolder.a(getWorkspace().s().startEnvoy("Load AddOn"), null, paramThrowable -> {
              this.a.a(this, paramThrowable);
              return null;
            });
      } else {
        unit = C();
        if (unit instanceof ScriptAddOnFolder) {
          ScriptAddOnFolder scriptAddOnFolder = (ScriptAddOnFolder)unit;
          scriptAddOnFolder.a(getWorkspace().s().startEnvoy("Load AddOn"), null, paramThrowable -> {
                this.a.a(this, paramThrowable);
                return null;
              });
        } 
      } 
    } 
    return null;
  }
  
  @Pro
  @Action(b = "flagActiveProject")
  public void tagManager() {
    (new FxTagsDialog(this, this)).showDialog();
  }
  
  @Action
  public void fullScreen() {
    this.s.setFullScreen(!this.s.isFullScreen());
  }
  
  private void Q() {
    if (this.k != null)
      for (Iterator<WatchEvent<?>> iterator = this.k.pollEvents().iterator(); iterator.hasNext(); ) {
        WatchEvent<Object> watchEvent = (WatchEvent)iterator.next();
        WatchEvent.Kind<Object> kind = watchEvent.kind();
        if (kind != StandardWatchEventKinds.OVERFLOW) {
          Path path = (Path)watchEvent.context();
          if (path instanceof Path) {
            Path path1 = path;
            path = this.f.getFile().getParentFile().toPath();
            if (this.f != null && this.f.getFile() != null && this.f.getFile().toPath().equals(path.resolve(path1)) && this.f.getFile().lastModified() > this.l && this.y) {
              this.y = false;
              Platform.runLater(() -> {
                    if (this.a.b(getDialogScene(), "reloadProjectFromFile", new String[0]).c() == ButtonBar.ButtonData.YES) {
                      File file = this.f.getFile();
                      if (e()) {
                        if (this.h != null)
                          this.g.getChildren().remove(this.h); 
                        this.a.a(new FxProjectLoaderTask(this, file));
                      } 
                      this.y = true;
                    } 
                  });
            } 
          } 
        } 
      }  
  }
  
  @Action
  public void resolveManyToManyRelation() {
    if (C() instanceof ForeignKey) {
      ((ForeignKey)C()).resolveManyToMany();
      y();
    } 
  }
  
  @Action
  public Task SQLTutorial() {
    Log.c("Open Sample Model");
    if (this.z.exists()) {
      R();
    } else {
      return new FxFrame$6(this, this);
    } 
    return null;
  }
  
  private void R() {
    if (m() != null && m().getType() == ProjectType.b) {
      new FxTipsDialog(this, new SQLTutorialTips());
    } else if (this.z.exists()) {
      this.a.a(new FxFrame$7(this, this, this.z));
    } 
  }
}
