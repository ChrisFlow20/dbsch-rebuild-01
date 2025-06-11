package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.data.task.FxGroovyScriptTask;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.fx.FxSynchronizationDialog;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.dialogs.git.credentials.GitCredentials;
import com.wisecoders.dbs.dialogs.git.model.GitFile;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StackTraceHelper;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import groovy.lang.Binding;
import groovy.lang.Script;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.TransportCommand;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revplot.PlotCommit;
import org.eclipse.jgit.revwalk.RevCommit;

public class FxGitDialog extends Dialog$ implements WorkspaceWindow {
  public static final SimpleDateFormat a = new SimpleDateFormat("d-MMM-yyyy HH:mm:ss");
  
  private final Workspace e;
  
  private final Project f;
  
  private String i;
  
  private final TableView j = new TableView();
  
  private GitRepo k;
  
  private final ObservableList l = FXCollections.observableArrayList();
  
  private final SplitMenuButton m = new SplitMenuButton();
  
  private boolean n = false;
  
  private final Tab o = new Tab("Current Work");
  
  private final Tab p = new Tab("Revision");
  
  private final Label q = new Label();
  
  private final Label r = new Label();
  
  private final Label s = new Label();
  
  private final TabPane t = new TabPane();
  
  protected final FxGitTreeView b;
  
  protected final FxGitTreeView c;
  
  protected final FxGitTreeView d;
  
  private final TextArea u = this.rx.s("commitMessage");
  
  private FxGitDialog$PullType v = FxGitDialog$PullType.b;
  
  private final TextField w;
  
  private final TextField x;
  
  private static final String y = "gitAuthorName";
  
  private static final String z = "gitAuthorEmail";
  
  private boolean A;
  
  private static final int B = 22;
  
  private final PlotRenderer C;
  
  public FxGitDialog(Workspace paramWorkspace, Project paramProject) {
    super(paramWorkspace);
    this.A = true;
    this.C = new PlotRenderer();
    initModality(Modality.NONE);
    this.e = paramWorkspace;
    this.f = paramProject;
    this.w = this.rx.t("authorNameTextField");
    this.x = this.rx.t("authorEmailTextField");
    this.b = new FxGitDialog$1(this, this, "Unstaged");
    this.c = new FxGitDialog$9(this, this, "Staged");
    this.d = new FxGitDialog$10(this, this, "Revision");
    Objects.requireNonNull(this.b);
    this.rx.a("flagCanAdd", this.b::c);
    this.rx.a("flagCanCommit", () -> !this.c.getRoot().getChildren().isEmpty());
    Objects.requireNonNull(this.c);
    this.rx.a("flagCanReset", this.c::c);
    this.rx.a("flagSelectedRevCommit", () -> (this.j.getSelectionModel().getSelectedItem() != null));
    this.rx.a("flagSelectedOne", () -> (this.j.getSelectionModel().getSelectedItems().size() == 1));
    this.rx.a("flagSelectedTwo", () -> (this.j.getSelectionModel().getSelectedItems().size() == 2 && this.j.getSelectionModel().getSelectedItems().get(0) != null && this.j.getSelectionModel().getSelectedItems().get(1) != null));
    this.rx.a("flagSelectedMany", () -> (this.j.getSelectionModel().getSelectedItems().size() > 1));
    this.rx.a("flagIsShowAllRevisions", this::c);
    this.rx.a("flagHasRepository", () -> (this.k != null));
    this.rx.a("flagPullFetchAll", () -> (this.v == FxGitDialog$PullType.a));
    this.rx.a("flagPullFastForwardIfPossible", () -> (this.v == FxGitDialog$PullType.b));
    this.rx.a("flagPullFastForwardOnly", () -> (this.v == FxGitDialog$PullType.c));
    this.rx.a("flagPullRebase", () -> (this.v == FxGitDialog$PullType.d));
    ContextMenu$ contextMenu$ = (new ContextMenu$()).a(this.rx, new String[] { "diffCurrentModel", "diffSelected", "downloadModelFile", "audit" });
    TableColumn tableColumn1 = new TableColumn("Branch/Tag");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn1.setCellFactory(paramTableColumn -> new c(this));
    TableColumn tableColumn2 = new TableColumn("Graph");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn2.setCellFactory(paramTableColumn -> new d(this));
    TableColumn tableColumn3 = new TableColumn("Revision");
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((PlotCommit)paramCellDataFeatures.getValue()).getName()));
    TableColumn tableColumn4 = new TableColumn("Author");
    tableColumn4.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((PlotCommit)paramCellDataFeatures.getValue()).getAuthorIdent().getName()));
    TableColumn tableColumn5 = new TableColumn("Time");
    tableColumn5.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(a.format(((PlotCommit)paramCellDataFeatures.getValue()).getAuthorIdent().getWhen())));
    TableColumn tableColumn6 = new TableColumn("Commit Message");
    tableColumn6.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper((paramCellDataFeatures.getValue() != null) ? StringUtil.removeNewLine(((PlotCommit)paramCellDataFeatures.getValue()).getFullMessage(), false) : null));
    this.m.setVisible(false);
    this.j.setContextMenu(contextMenu$);
    this.j.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2, tableColumn6 });
    this.j.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    setRegionPrefSize((Region)this.j, 1100.0D, 550.0D);
    Rx.a(this.j, new double[] { 0.2D, 0.2D, 0.6D });
    this.j.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.j.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramPlotCommit1, paramPlotCommit2) -> {
          this.t.getSelectionModel().select((paramPlotCommit2 == null) ? this.o : this.p);
          if (paramPlotCommit2 != null)
            a(paramPlotCommit2); 
          this.rx.b();
        });
    this.j.setItems(this.l);
    a();
    getDialogPane().focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue())
            a(); 
        });
  }
  
  private void a(PlotCommit paramPlotCommit) {
    try {
      this.d.b.getChildren().clear();
      this.q.setText(null);
      this.r.setText(null);
      if (this.k != null && paramPlotCommit != null) {
        for (GitFile gitFile : this.k.a((RevCommit)paramPlotCommit))
          this.d.b.getChildren().add(new TreeItem(gitFile)); 
        this.s.setText(paramPlotCommit.getFullMessage());
        this.s.setWrapText(true);
        this.q.setText("Author: " + paramPlotCommit.getAuthorIdent().getName() + " <" + paramPlotCommit.getAuthorIdent().getEmailAddress() + ">");
        this.r.setText("Commit Time: " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(Long.valueOf((paramPlotCommit.getCommitTime() + 1) * 1000L)));
      } 
    } catch (Throwable throwable) {
      this.rx.a(getDialogScene(), throwable);
    } 
  }
  
  public Node createContentPane() {
    getDialogPane().setPadding(FxUtil.a);
    SplitMenuButton splitMenuButton1 = this.rx.g("createReloadRevisionsAndStatusTask", true);
    splitMenuButton1.getItems().add(this.rx.y("showAllRevisions"));
    splitMenuButton1.getItems().addAll(this.rx.e(new String[] { "separator", "createReloadRevisionsAndStatusTask" }));
    SplitMenuButton splitMenuButton2 = this.rx.g("gitPull", true);
    splitMenuButton2.getItems().addAll((Object[])new MenuItem[] { (MenuItem)this.rx.z("gitPullFetchAll"), (MenuItem)new SeparatorMenuItem(), (MenuItem)this.rx.z("gitPullFastForwardIfPossible"), (MenuItem)this.rx.z("gitPullFastForwardOnly"), (MenuItem)this.rx.z("gitPullRebase") });
    SplitMenuButton splitMenuButton3 = this.rx.g("gitPush", true);
    splitMenuButton3.getItems().add(this.rx.A("setupGitPushTrigger"));
    HBox$ hBox$1 = (new HBox$()).e().a(new Node[] { (Node)this.rx.j("gitClone"), (Node)this.m, (Node)splitMenuButton1, (Node)new HGrowBox$(), (Node)splitMenuButton2, (Node)splitMenuButton3, (Node)this.rx.j("gitBranch"), (Node)this.rx.j("gitStash"), (Node)this.rx.j("gitPop"), (Node)new HGrowBox$() });
    HBox$ hBox$2 = (new HBox$()).e().a(new Node[] { (Node)this.rx.j("downloadModelFile"), (Node)this.rx.j("diffCurrentModel"), (Node)this.rx.j("diffSelected"), (Node)this.rx.j("audit") });
    this.u.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.rx.b());
    VBox.setVgrow((Node)this.u, Priority.ALWAYS);
    HBox$.setHgrow((Node)this.w, Priority.ALWAYS);
    HBox$.setHgrow((Node)this.x, Priority.ALWAYS);
    SplitPane splitPane1 = new SplitPane(new Node[] { (Node)this.c, (Node)(new VBox$()).b(2).c(new Node[] { (Node)this.u, (Node)(new HBox$()).a(2.0D).a(new Node[] { (Node)this.w, (Node)this.x }), (Node)(new HBox$()).e().a(new Node[] { (Node)this.rx.j("gitCommit"), (Node)this.rx.j("gitReset") }) }) });
    splitPane1.setOrientation(Orientation.VERTICAL);
    Platform.runLater(() -> paramSplitPane.setDividerPositions(new double[] { 0.7D }));
    SplitPane splitPane2 = new SplitPane(new Node[] { (Node)(new VBox$()).j().c(new Node[] { (Node)this.b, (Node)(new HBox$()).e().a((Node)this.rx.j("gitAdd")) }), (Node)splitPane1 });
    splitPane2.setOrientation(Orientation.VERTICAL);
    this.o.setContent((Node)splitPane2);
    this.o.setClosable(false);
    this.p.setClosable(false);
    this.p.setContent((Node)(new VBox$()).j().c(new Node[] { (Node)this.q, (Node)this.r, (Node)this.s, (Node)this.d }));
    this.t.getTabs().addAll((Object[])new Tab[] { this.o, this.p });
    SplitPane splitPane3 = new SplitPane(new Node[] { (Node)this.j, (Node)this.t });
    VBox$.setVgrow((Node)splitPane3, Priority.ALWAYS);
    Platform.runLater(() -> paramSplitPane.setDividerPositions(new double[] { 0.7D }));
    VBox$ vBox$ = (new VBox$()).b(5).c(new Node[] { (Node)hBox$1, (Node)splitPane3, (Node)hBox$2 });
    setRegionPrefSize((Region)vBox$, 1200.0D, 700.0D);
    return (Node)vBox$;
  }
  
  public void a() {
    Platform.runLater(() -> this.rx.a(createReloadRevisionsAndStatusTask()));
  }
  
  @Action(d = "flagPullFetchAll")
  public void gitPullFetchAll() {
    this.v = FxGitDialog$PullType.a;
  }
  
  @Action(d = "flagPullFastForwardIfPossible")
  public void gitPullFastForwardIfPossible() {
    this.v = FxGitDialog$PullType.b;
  }
  
  @Action(d = "flagPullFastForwardOnly")
  public void gitPullFastForwardOnly() {
    this.v = FxGitDialog$PullType.c;
  }
  
  @Action(d = "flagPullRebase")
  public void gitPullRebase() {
    this.v = FxGitDialog$PullType.d;
  }
  
  @Action
  public Task gitClone() {
    this.k = null;
    (new FxGitCloneDialog(getDialogScene().getWindow(), new GitCredentials())).showDialog();
    return createReloadRevisionsAndStatusTask();
  }
  
  @Action(b = "flagHasRepository")
  public Task gitPull() {
    if (a(false))
      return createReloadRevisionsAndStatusTask(); 
    if (this.v == FxGitDialog$PullType.a) {
      this.rx.d(getDialogScene(), "Fetch Done.");
      return createReloadRevisionsAndStatusTask();
    } 
    return new FxGitDialog$11(this);
  }
  
  private boolean a(boolean paramBoolean) {
    try {
      Git git = new Git(this.k.a);
      try {
        FetchCommand fetchCommand = git.fetch().setRemote("origin");
        this.k.c.a((TransportCommand)fetchCommand);
        fetchCommand.call();
        Status status = git.status().call();
        if (paramBoolean && status.getUncommittedChanges() != null && !status.getUncommittedChanges().isEmpty()) {
          StringBuilder stringBuilder = new StringBuilder();
          for (String str : status.getUncommittedChanges())
            stringBuilder.append("\n").append(str); 
          this.rx.d(getDialogScene(), "Commit your work first. Un-staged files: " + String.valueOf(stringBuilder));
          boolean bool = true;
          git.close();
          return bool;
        } 
        if (status.getConflicting() != null && !status.getConflicting().isEmpty()) {
          this.rx.d(getDialogScene(), "Solve the Conflicts.");
          boolean bool = true;
          git.close();
          return bool;
        } 
        git.close();
      } catch (Throwable throwable) {
        try {
          git.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (TransportException transportException) {
      Log.b((Throwable)transportException);
      (new FxGitCredentialsDialog(getDialogScene(), this.k.c)).showDialog().filter(paramButtonType -> (paramButtonType == ButtonType.OK)).ifPresent(paramButtonType -> a(paramBoolean));
    } catch (Throwable throwable) {
      Log.a("Error on installing watchers and fetch.", throwable);
      if (throwable.getLocalizedMessage() != null && throwable.getLocalizedMessage().contains("invalid privatekey")) {
        this.rx.b(getDialogScene(), "privateKeyException", throwable);
      } else {
        this.rx.a(getDialogScene(), throwable);
      } 
    } 
    return false;
  }
  
  @Action(b = "flagHasRepository")
  public Task gitPush() {
    if (a(false))
      return createReloadRevisionsAndStatusTask(); 
    return new FxGitDialog$12(this);
  }
  
  public void b() {
    File file = Sys.h().resolve("GITPushTrigger.groovy").toFile();
    if (file.exists()) {
      FileInputStream fileInputStream = new FileInputStream(file);
      try {
        String str = StringUtil.readStringFromInputStream(fileInputStream);
        if (StringUtil.isFilledTrim(str)) {
          Binding binding = new Binding();
          binding.setVariable("project", this.f);
          binding.setProperty("out", System.out);
          try {
            Script script = Groovy.a.b(binding, str);
            script.run();
          } catch (MultipleCompilationErrorsException multipleCompilationErrorsException) {
            FxGroovyScriptTask.a(multipleCompilationErrorsException);
          } catch (Exception exception) {
            throw new Exception(StackTraceHelper.a(exception));
          } 
        } 
        fileInputStream.close();
      } catch (Throwable throwable) {
        try {
          fileInputStream.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } 
  }
  
  @Action
  public void setupGitPushTrigger() {
    this.rx.a(getDialogScene(), "gitPushTriggerMessage", new String[0]);
  }
  
  @Action(b = "flagHasRepository")
  public Task gitStash() {
    return new FxGitDialog$13(this);
  }
  
  @Action(b = "flagHasRepository")
  public Task gitPop() {
    return new FxGitDialog$14(this);
  }
  
  @Action(b = "flagSelectedRevCommit")
  public void diffCurrentModel() {
    try {
      Project project = this.k.b(this.i, (RevCommit)this.j.getSelectionModel().getSelectedItem());
      SyncPair syncPair = new SyncPair(this.f, project);
      (new FxSynchronizationDialog(this.e.getWorkspace(), syncPair, true, "git", null)).showDialog();
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  @Action(b = "flagSelectedRevCommit")
  public void downloadModelFile() {
    try {
      File file = FxFileChooser.a(getDialogScene(), "Save Design Model to File", FileOperation.b, new FileType[] { FileType.a });
      if (file != null) {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
          this.k.a(this.i, (RevCommit)this.j.getSelectionModel().getSelectedItem(), fileOutputStream);
          fileOutputStream.close();
        } catch (Throwable throwable) {
          try {
            fileOutputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } 
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  @Action(b = "flagSelectedTwo")
  public void diffSelected() {
    try {
      ObservableList observableList = this.j.getSelectionModel().getSelectedItems();
      if (observableList.size() == 2) {
        Project project1 = this.k.b(this.i, (RevCommit)observableList.get(0));
        Project project2 = this.k.b(this.i, (RevCommit)observableList.get(1));
        SyncPair syncPair = new SyncPair(project1, project2);
        (new FxSynchronizationDialog(this.e.getWorkspace(), syncPair, true, "git", null)).showDialog();
      } 
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  @Action(b = "flagSelectedMany")
  public void audit() {
    AbstractUnit abstractUnit = null;
    try {
      FxGitAuditDialog fxGitAuditDialog = new FxGitAuditDialog(this);
      for (RevCommit revCommit : this.l) {
        if (revCommit != null && this.j.getSelectionModel().getSelectedItems().contains(revCommit)) {
          Project project = this.k.b(this.i, revCommit);
          if (abstractUnit != null) {
            SyncPair syncPair = new SyncPair(project, abstractUnit);
            fxGitAuditDialog.a(revCommit, syncPair);
          } 
          abstractUnit = project;
        } 
      } 
      fxGitAuditDialog.showDialog();
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
    this.rx.b();
  }
  
  public Workspace getWorkspace() {
    return this.e;
  }
  
  public void createButtons() {
    createCloseButton();
    createHelpButton("git.html");
  }
  
  public boolean apply() {
    return false;
  }
  
  public boolean c() {
    return this.n;
  }
  
  @Action(d = "flagIsShowAllRevisions")
  public Task showAllRevisions() {
    this.n = !this.n;
    return createReloadRevisionsAndStatusTask();
  }
  
  @Action(b = "flagHasRepository")
  public Task createReloadRevisionsAndStatusTask() {
    return new FxGitDialog$15(this);
  }
  
  @Action(b = "flagHasRepository")
  public Task gitBranch() {
    String str = this.rx.b(getDialogScene(), "BranchName");
    if (StringUtil.isFilled(str))
      return new FxGitDialog$16(this, str); 
    return null;
  }
  
  public Task a(String paramString) {
    if (a(false))
      return createReloadRevisionsAndStatusTask(); 
    return new FxGitDialog$2(this, paramString);
  }
  
  public Task a(Ref paramRef) {
    if (a(false))
      return createReloadRevisionsAndStatusTask(); 
    String str = this.rx.b(getDialogScene(), "mergeMessage");
    if (StringUtil.isEmpty(str))
      return null; 
    return new FxGitDialog$3(this, paramRef, str);
  }
  
  public Task b(Ref paramRef) {
    boolean bool;
    switch (FxGitDialog$8.b[this.rx.b(getDialogScene(), "gitDeleteForce", new String[] { paramRef.getName() }).c().ordinal()]) {
      case 1:
        bool = true;
        return new FxGitDialog$4(this, paramRef, bool);
      case 2:
        bool = false;
        return new FxGitDialog$4(this, paramRef, bool);
    } 
    return null;
  }
  
  public String d() {
    StringBuilder stringBuilder = new StringBuilder();
    for (RevCommit revCommit : this.l) {
      stringBuilder.append(a(revCommit)).append(": ");
      stringBuilder.append("\n");
    } 
    return stringBuilder.toString();
  }
  
  private String a(RevCommit paramRevCommit) {
    return paramRevCommit.getShortMessage();
  }
  
  public void b(String paramString) {
    try {
      if (this.k.e.containsKey(paramString)) {
        GitRepo$ConflictVersions gitRepo$ConflictVersions = (GitRepo$ConflictVersions)this.k.e.get(paramString);
        Project project = this.k.a(gitRepo$ConflictVersions.b());
        SyncPair syncPair = new SyncPair(this.e.m(), project);
        if ((new FxSynchronizationDialog(getWorkspace(), syncPair, false, "git", null)).showDialogIsResultOkDone() && this.rx
          .a(getDialogScene(), "saveModelAndMarkItResolved")) {
          this.e.n();
          a(paramString, "Synchronization Done");
        } 
      } else {
        DirCache dirCache = this.k.a.readDirCache();
        DirCacheEntry[] arrayOfDirCacheEntry = dirCache.getEntriesWithin(paramString);
        if (arrayOfDirCacheEntry != null && arrayOfDirCacheEntry.length > 0) {
          ObjectId objectId = arrayOfDirCacheEntry[0].getObjectId();
          Log.c("Synchronization compare workspace Project file with '" + String.valueOf(objectId) + "'");
          Project project = this.k.b(paramString, this.k.a.parseCommit((AnyObjectId)objectId));
          SyncPair syncPair = new SyncPair(this.e.m(), project);
          if ((new FxSynchronizationDialog(getWorkspace(), syncPair, false, "git", null)).showDialogIsResultOkDone() && this.rx
            .a(getDialogScene(), "saveModelAndMarkItResolved")) {
            this.e.n();
            a(paramString, "Synchronization Done");
          } 
        } else {
          this.rx.c(getDialogScene(), "cannotFindConflictObjectId");
        } 
      } 
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  public String e() {
    try {
      return "origin/" + this.k.a.getBranch();
    } catch (IOException iOException) {
      return "origin/master";
    } 
  }
  
  public void a(String paramString1, String paramString2, String paramString3) {
    try {
      byte[] arrayOfByte = this.k.b(paramString1 + ":" + paramString1);
      Files.write(this.k.a.getWorkTree().toPath().resolve(paramString2), arrayOfByte, new java.nio.file.OpenOption[0]);
      a(paramString2, paramString3);
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  public void a(String paramString1, String paramString2) {
    try {
      Git git = new Git(this.k.a);
      try {
        git.add().addFilepattern(paramString1).call();
        if (paramString2 != null)
          this.rx.d(getDialogScene(), paramString2); 
        a();
        git.close();
      } catch (Throwable throwable) {
        try {
          git.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (Exception exception) {
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  @Action(b = "flagCanCommit")
  public Task gitCommit() {
    return new FxGitDialog$5(this);
  }
  
  @Action(b = "flagCanAdd")
  public Task gitAdd() {
    return new FxGitDialog$6(this);
  }
  
  public static boolean a(Throwable paramThrowable) {
    return (paramThrowable instanceof TransportException || paramThrowable instanceof org.eclipse.jgit.errors.TransportException);
  }
  
  @Action(b = "flagCanReset")
  public Task gitReset() {
    return new FxGitDialog$7(this);
  }
}
