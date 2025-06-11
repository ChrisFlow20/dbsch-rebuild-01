package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.DbmsLocation;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorEditor;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorManagerDialog;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.fx.FxToolEditorTab;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.ActivationTask$Mode;
import com.wisecoders.dbs.dialogs.system.FxPurchaseDialog;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.ProjectType;
import com.wisecoders.dbs.schema.ScriptAddOn;
import com.wisecoders.dbs.schema.store.ProjectStore;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class Workspace extends Scene implements WorkspaceWindow {
  public static final String d = "-OperativeConnector";
  
  public static final String e = "-NonOperativeConnector";
  
  protected Project f;
  
  private FxLayoutPane a = null;
  
  protected final VBox g;
  
  protected Node h;
  
  final FxProjectStructure i = new FxProjectStructure(this);
  
  final TabPane j = new TabPane();
  
  private boolean b = false;
  
  private static WatchService c;
  
  protected WatchKey k;
  
  protected long l;
  
  protected long m = System.currentTimeMillis();
  
  protected long n = System.currentTimeMillis();
  
  protected boolean o;
  
  public Workspace() {
    super((Parent)new VBox(), 800.0D, 800.0D);
    this.o = false;
    this.g = (VBox)getRoot();
    Rx.b(this.j);
    this.j.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTab1, paramTab2) -> {
          if (paramTab1 != null)
            FxUtil.a(paramTab1.getContent(), true); 
          if (paramTab2 != null) {
            FxUtil.a(paramTab2.getContent(), false);
            if (paramTab2.getContent() instanceof FxLayoutPane)
              a((FxLayoutPane)paramTab2.getContent()); 
          } 
        });
    try {
      if (c == null)
        c = FileSystems.getDefault().newWatchService(); 
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
  }
  
  public boolean j() {
    return (this.f != null && this.f.is(UnitProperty.f).booleanValue());
  }
  
  public boolean k() {
    return (this.f != null && this.f.is(UnitProperty.g).booleanValue());
  }
  
  public boolean l() {
    return (this.f != null);
  }
  
  public Project m() {
    return this.f;
  }
  
  public Workspace i() {
    return l() ? new FxFrame(new Stage()) : this;
  }
  
  public void b(Project paramProject) {
    if (this.f != paramProject) {
      Workspace workspace = i();
      workspace.c(paramProject);
      workspace.b(true);
    } 
  }
  
  public void c(Project paramProject) {
    this.f = paramProject;
  }
  
  public void b(boolean paramBoolean) {
    Log.c("Use RDBMS: " + this.f.getDbId());
    if (l()) {
      if (!this.f.hasOperativeConnector())
        if (this.f.isFreshImported) {
          u();
        } else if (!this.f.is(UnitProperty.g).booleanValue()) {
          String str = Pref.c(this.f.getKey() + "-OperativeConnector", (String)null);
          if (str != null) {
            b(ConnectorManager.getConnectors(this.f.getDbId(), str));
          } else if (this.f.getType() == ProjectType.b && "H2".equals(this.f.getDbId())) {
            Connector connector = ConnectorManager.createH2LocalConnector("Sample", "~/.DbSchema/samples/Sakila");
            b(connector);
            a();
          } 
          v();
        }  
      TreeMap<Object, Object> treeMap = new TreeMap<>();
      if (this.f.hasFile()) {
        for (Layout layout : (m()).layouts) {
          int i;
          if ((i = FxUtil.a(layout)) > -1) {
            FxLayoutPane fxLayoutPane = new FxLayoutPane(this, layout, false);
            treeMap.put(Integer.valueOf(i), fxLayoutPane);
          } 
        } 
        for (Integer integer : treeMap.keySet())
          this.j.getTabs().add(new FxToolEditorTab((FxToolEditor)treeMap.get(integer), true)); 
        treeMap.clear();
        J();
        I();
      } 
      if (this.j.getTabs().isEmpty())
        a(); 
      if (this.h != null)
        this.g.getChildren().remove(this.h); 
      this.g.getChildren().add(this.h = b());
    } 
  }
  
  private void a() {
    byte b = 0;
    for (Layout layout : (m()).layouts) {
      if (b++ < Sys.B.reopenLayouts.a()) {
        FxLayoutPane fxLayoutPane = new FxLayoutPane(this, layout, true);
        this.j.getTabs().add(new FxToolEditorTab(fxLayoutPane, true));
      } 
    } 
  }
  
  public void n() {
    File file = this.f.getFile();
    Pref.a(this.f.getKey() + "-OperativeConnector", (this.f.getOperativeConnector() != null) ? this.f.getOperativeConnector().getName() : "Off");
    if (Sys.B.enableBackup.b() && this.f.getType() == ProjectType.a)
      try {
        String str = String.valueOf(file) + ".bak";
        File file1 = Paths.get(str, new String[0]).toFile();
        if (file.exists())
          if (file.length() == 0L) {
            FileUtils.b(file);
          } else {
            if (file1.exists())
              FileUtils.b(file1); 
            File file2 = new File(file.toURI());
            FileUtils.b(file2, file1);
          }  
      } catch (Exception exception) {
        Log.a("Error creating backup file", exception);
      }  
    J();
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    try {
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
      try {
        new ProjectStore(this.f, outputStreamWriter);
        outputStreamWriter.close();
      } catch (Throwable throwable) {
        try {
          outputStreamWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
      fileOutputStream.close();
    } catch (Throwable throwable) {
      try {
        fileOutputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    I();
    v();
    FxFrame.c.a(this.f.getName(), file);
  }
  
  protected Node b() {
    return null;
  }
  
  public void a(FxLayoutPane paramFxLayoutPane) {
    this.a = paramFxLayoutPane;
  }
  
  public FxLayoutPane o() {
    return this.a;
  }
  
  public Layout p() {
    return (this.a != null) ? this.a.d : null;
  }
  
  public FxLayoutPane q() {
    if (this.a == null || this.a.d.isMarkedForDeletion())
      this.a = a(this.f.createLayout((String)null)); 
    return this.a;
  }
  
  public boolean r() {
    return (this.f != null && this.f.hasOperativeConnector());
  }
  
  public Connector s() {
    return (this.f != null) ? this.f.getOperativeConnector() : null;
  }
  
  public boolean t() {
    return (this.f != null && !this.f.is(UnitProperty.g).booleanValue() && this.f.hasOperativeConnector());
  }
  
  void a(Connector paramConnector) {
    if (this.f != null) {
      boolean bool1 = (this.f.hasEntities() && !this.f.is(UnitProperty.g).booleanValue() && s() != null && !this.f.getDbId().equals((s()).dbId)) ? true : false;
      boolean bool2 = (paramConnector != null && !paramConnector.equals(s())) ? true : false;
      b(paramConnector);
      if (bool1) {
        getRx().a(this, "You have connected to " + (this.f.getOperativeConnector()).dbId + " while your model is set for " + this.f.getDbId() + "\nYou may convert the model as well to " + (this.f.getOperativeConnector()).dbId + " from Model Properties dialog.", new String[0]);
      } else if (bool2) {
        g();
      } 
    } 
  }
  
  public void b(Connector paramConnector) {
    A();
    if (this.f != null)
      this.f.setOperativeConnector(paramConnector); 
  }
  
  public Connector a(WorkspaceWindow paramWorkspaceWindow) {
    if (this.f != null) {
      Connector connector = this.f.getOperativeConnector();
      if (connector == null) {
        if (ConnectorManager.getConnectors(this.f.getDbId()).isEmpty()) {
          connector = (new FxConnectorEditor(paramWorkspaceWindow, this.f.getDbId(), DbmsLocation.a)).showDialog().orElse(null);
        } else {
          connector = (new FxConnectorManagerDialog(paramWorkspaceWindow, true)).showDialog().orElse(null);
        } 
      } else if (this.f.getOperativeConnector().needsEdit()) {
        connector = (new FxConnectorEditor(paramWorkspaceWindow, this.f.getOperativeConnector())).showDialog().orElse(null);
      } 
      return connector;
    } 
    return null;
  }
  
  public void u() {
    this.m = System.currentTimeMillis();
  }
  
  public void v() {
    this.o = true;
  }
  
  public boolean w() {
    return (this.f != null && this.m > this.n);
  }
  
  public FxLayoutPane a(Layout paramLayout) {
    FxLayoutPane fxLayoutPane = null;
    Tab tab = null;
    for (Tab tab1 : this.j.getTabs()) {
      if (tab1.getContent() instanceof FxLayoutPane && ((FxLayoutPane)tab1.getContent()).d == paramLayout) {
        fxLayoutPane = (FxLayoutPane)tab1.getContent();
        tab = tab1;
      } 
    } 
    if (fxLayoutPane == null) {
      fxLayoutPane = new FxLayoutPane(this, paramLayout, false);
      tab = new FxToolEditorTab(fxLayoutPane, true);
      this.j.getTabs().add(!this.j.getTabs().isEmpty() ? (this.j.getTabs().size() - 1) : 0, tab);
    } 
    this.j.getSelectionModel().select(tab);
    return fxLayoutPane;
  }
  
  public void x() {
    if (this.f != null)
      this.f.refresh(); 
    for (Tab tab : this.j.getTabs()) {
      if (tab.getContent() instanceof FxLayoutPane)
        ((FxLayoutPane)tab.getContent()).q(); 
    } 
  }
  
  public void y() {
    if (this.f != null)
      this.f.refresh(); 
    if (o() != null)
      o().q(); 
  }
  
  public List z() {
    ArrayList<FxLayoutPane> arrayList = new ArrayList();
    for (Tab tab : this.j.getTabs()) {
      if (tab.getContent() instanceof FxLayoutPane)
        arrayList.add((FxLayoutPane)tab.getContent()); 
    } 
    return arrayList;
  }
  
  public void A() {
    for (Tab tab : this.j.getTabs()) {
      if (tab.getContent() instanceof FxLayoutPane)
        ((FxLayoutPane)tab.getContent()).f(); 
    } 
  }
  
  public boolean B() {
    return (this.a != null);
  }
  
  public Unit C() {
    List<Unit> list = D();
    return (list.size() == 1) ? list.get(0) : null;
  }
  
  public List D() {
    if (!this.i.b() && B())
      return this.a.c.c.a; 
    return this.i.c();
  }
  
  public List E() {
    if (!this.i.g() && B())
      return this.a.c.c.b; 
    return this.i.d();
  }
  
  public void F() {
    int i;
    License license = License.a();
    switch (Workspace$3.a[license.g().ordinal()]) {
      case 1:
        if (license.k() < 5L)
          a(getRx().H("trialExpires").replaceAll("\\{xxx\\}", "" + license.k()), getRx().H("buyNow"), getRx().a("buy.url", new String[0])); 
        break;
      case 2:
      case 3:
        i = Pref.d("Eval_msg");
        if (i % 4 == 3)
          (new FxPurchaseDialog(this)).showDialog(); 
        break;
      case 4:
        if (license.k() < 14L && Sys.B.showRenewalMessage.b() && (license.k() > -5L || Pref.d("showRenewMsg") % 3 == 0)) {
          a(getRx().H("renewInfo"), getRx().H("buyRenewal"), license.d());
          break;
        } 
        if (license.m())
          getRx().a(new Workspace$1(this, ActivationTask$Mode.a)); 
        break;
      case 5:
      case 6:
        a(getRx().H("renewInfo"), getRx().H("buyRenewal"), license.d());
        break;
    } 
  }
  
  void a(String paramString1, String paramString2, String paramString3) {
    String str = getRx().H("licensingInfo");
    Matcher matcher;
    if (paramString1 != null && (matcher = Rx.e.matcher(paramString1)).matches()) {
      str = matcher.group(1);
      paramString1 = "<html>" + matcher.group(2);
    } 
    ButtonBar.ButtonData buttonData = (new Alert$()).a(ButtonType.OK, paramString2).a(ButtonType.CANCEL, getRx().H("skip")).a(paramString1, "DbSchema64.png", true).a(str).b(str).a(this).c();
    if (buttonData == ButtonBar.ButtonData.OK_DONE)
      WebBrowserExternal.a(this, paramString3); 
  }
  
  void G() {
    this.j.getTabs().removeIf(paramTab -> (paramTab.getContent() instanceof FxLayoutPane && ((FxLayoutPane)paramTab.getContent()).j().isMarkedForDeletion()));
    for (Tab tab : this.j.getTabs()) {
      Node node = tab.getContent();
      if (node instanceof FxLayoutPane) {
        FxLayoutPane fxLayoutPane = (FxLayoutPane)node;
        if (this.f != null) {
          fxLayoutPane.b();
          if (tab instanceof FxToolEditorTab)
            ((FxToolEditorTab)tab).a(fxLayoutPane.d.getName()); 
        } 
      } 
    } 
  }
  
  void a(boolean paramBoolean, int paramInt) {
    for (Tab tab : this.j.getTabs()) {
      Node node = tab.getContent();
      if (node instanceof FxLayoutPane) {
        FxLayoutPane fxLayoutPane = (FxLayoutPane)node;
        fxLayoutPane.a(paramBoolean, paramInt);
      } 
    } 
  }
  
  public Workspace getWorkspace() {
    return this;
  }
  
  public void d(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean H() {
    return this.b;
  }
  
  public void a(TreeUnit paramTreeUnit) {
    this.i.a(paramTreeUnit);
  }
  
  public void I() {
    if (c != null && this.f != null && this.f.hasFile())
      try {
        this.k = this.f.getFile().getParentFile().toPath().register(c, (WatchEvent.Kind<?>[])new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY });
        this.l = System.currentTimeMillis() + 500L;
      } catch (IOException iOException) {
        Log.b(iOException);
      }  
  }
  
  public void J() {
    if (this.k != null) {
      this.k.cancel();
      this.k = null;
    } 
  }
  
  public synchronized Task a(ScriptAddOn paramScriptAddOn) {
    Connector connector = s();
    if (connector != null) {
      Envoy envoy = connector.startEnvoy("Load " + paramScriptAddOn.b.getChildrenName() + " script");
      try {
        Workspace$2 workspace$2 = new Workspace$2(this, paramScriptAddOn, envoy);
        if (envoy != null)
          envoy.close(); 
        return workspace$2;
      } catch (Throwable throwable) {
        if (envoy != null)
          try {
            envoy.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } 
    return null;
  }
  
  public abstract void g();
  
  public abstract void synchronizeCommit();
  
  public abstract Rx getRx();
}
