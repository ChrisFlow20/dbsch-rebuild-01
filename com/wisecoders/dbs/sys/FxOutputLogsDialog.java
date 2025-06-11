package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.editors.text.StyledEditorHighlight;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.ToolBar$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

public class FxOutputLogsDialog extends ButtonDialog$ implements WorkspaceWindow {
  private final ProgressBar a = new ProgressBar();
  
  private final Label b = new Label();
  
  private final StyledEditor c = new StyledEditor();
  
  private long d;
  
  private Timer e;
  
  public FxOutputLogsDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    this.d = 0L;
    initModality(Modality.NONE);
    this.rx.a("flagLogSQL", Log::b);
    Objects.requireNonNull(Sys.B.logConnectivityDetails);
    this.rx.a("flagLogConnectivityDetails", Sys.B.logConnectivityDetails::b);
    this.rx.a("flagDumpJavaThreadsPeriodically", () -> (this.e != null));
    this.c.e(false);
    this.c.J.b.a(() -> {
          a();
          b();
        });
    b();
    setOnCloseRequest(paramDialogEvent -> Log.f());
    Log.f();
    showDialog();
  }
  
  public Node createContentPane() {
    setRegionPrefSize((Region)this.c, 1100.0D, 600.0D);
    this.a.setPrefSize(180.0D, 20.0D);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().setAll((Object[])new Node[] { (Node)this.a, (Node)this.b });
    ToolBar$ toolBar$ = new ToolBar$();
    MenuButton menuButton = this.rx.f("content", false);
    menuButton.getItems().addAll(this.rx.d(new String[] { "logConnectivityDetails", "logSQL", "dumpJavaThreadsPeriodically" }));
    menuButton.getItems().addAll(this.rx.e(new String[] { "separator", "dumpJavaThreads" }));
    toolBar$.getItems().addAll((Object[])new Node[] { (Node)menuButton, (Node)this.rx.j("clear"), (Node)this.rx.j("info"), (Node)new HGrowBox$(), (Node)this.rx.e("memoryLabel"), (Node)stackPane, (Node)this.rx.j("garbageCollect") });
    VBox$.setVgrow((Node)this.c, Priority.ALWAYS);
    return (Node)(new VBox$()).c(new Node[] { (Node)toolBar$, (Node)this.c });
  }
  
  public boolean apply() {
    return false;
  }
  
  @Action
  public void reportBug() {
    (new FxTechnicalSupportDialog(getDialogScene().getWindow())).showDialog();
  }
  
  public void createButtons() {
    createCloseButton();
    createActionButton("reportBug", ButtonBar.ButtonData.LEFT);
  }
  
  public void a() {
    for (LogRecord logRecord : Log.i()) {
      boolean bool = (logRecord.a > this.d) ? true : false;
      if (bool) {
        this.d = logRecord.a;
        this.c.a(String.valueOf(logRecord) + "\n", 
            (logRecord.c == LogLevel.e) ? StyledEditorHighlight.c : StyledEditorHighlight.a);
      } 
    } 
    Log.f();
    this.rx.b();
  }
  
  @Action(d = "flagLogSQL")
  public void logSQL() {
    Log.a(!Log.b());
  }
  
  @Action(d = "flagLogConnectivityDetails")
  public void logConnectivityDetails() {
    Sys.B.logConnectivityDetails.a(!Sys.B.logConnectivityDetails.b());
  }
  
  @Action
  public void info() {
    this.rx.a(getDialogScene(), "info", new String[0]);
  }
  
  @Action
  public void dumpJavaThreads() {
    Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
    StringBuilder stringBuilder = new StringBuilder("------- DUMP JAVA THREADS -------\n");
    for (Thread thread : map.keySet()) {
      StackTraceElement[] arrayOfStackTraceElement = map.get(thread);
      if (StackTraceHelper.b(arrayOfStackTraceElement)) {
        String str = StackTraceHelper.a(arrayOfStackTraceElement);
        if (StringUtil.isFilledTrim(str)) {
          stringBuilder.append(thread.getName()).append(" ").append(thread.getState()).append("\n");
          stringBuilder.append(str).append("\n");
        } 
      } 
    } 
    Log.c(stringBuilder.toString());
  }
  
  @Action(d = "flagDumpJavaThreadsPeriodically")
  public void dumpJavaThreadsPeriodically() {
    if (this.e == null) {
      this.e = new Timer();
      this.e.schedule(new FxOutputLogsDialog$1(this), 0L, 1000L);
    } else {
      this.e.cancel();
      this.e = null;
    } 
    this.rx.b();
  }
  
  @Action
  public void clear() {
    Log.c();
    this.c.x();
  }
  
  @Action
  public void garbageCollect() {
    System.gc();
  }
  
  private void b() {
    Runtime runtime = Runtime.getRuntime();
    long l = runtime.totalMemory();
    this.a.setProgress(1.0D * (runtime.totalMemory() - runtime.freeMemory()) / l);
    this.b.setText(String.format("%dMb of %dMb", new Object[] { Long.valueOf((runtime.totalMemory() - runtime.freeMemory()) / 1048576L), Long.valueOf(l / 1048576L) }));
  }
  
  public Workspace getWorkspace() {
    return null;
  }
}
