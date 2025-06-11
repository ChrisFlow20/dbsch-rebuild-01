package com.wisecoders.dbs.browse.store;

import com.wisecoders.dbs.browse.fx.FxBrowseTableView;
import com.wisecoders.dbs.browse.model.BrowseResult;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.browse.model.Cascade;
import com.wisecoders.dbs.data.model.result.ResultStatus;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.util.concurrent.Future;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

public class FxBrowseLoaderTask extends Task {
  private final FxBrowseTableView c;
  
  private final Workspace d;
  
  protected final BrowseResult a;
  
  public final Envoy b;
  
  private final Cascade e;
  
  private int f;
  
  private BrowseResult g;
  
  private boolean h = false;
  
  FxBrowseLoaderTask(Workspace paramWorkspace, FxBrowseTableView paramFxBrowseTableView, BrowseResult paramBrowseResult, Cascade paramCascade, Connector paramConnector) {
    this.a = paramBrowseResult;
    this.d = paramWorkspace;
    this.b = paramConnector.startEnvoy("Browse Loader");
    this.c = paramFxBrowseTableView;
    this.e = paramCascade;
    this.f = paramFxBrowseTableView.getSelectionModel().getSelectedIndex();
    paramBrowseResult.a(paramCascade);
    paramBrowseResult.a(ResultStatus.b, paramCascade);
  }
  
  public void a() {
    if (this.g != null && this.g.e.a()) {
      this.h = true;
      this.b.n();
    } 
  }
  
  public Void b() {
    Thread.currentThread().setName("DbSchema: Relational Data Editor Loader Task");
    if (!isCancelled())
      a(this.a, this.e); 
    return null;
  }
  
  private void a(BrowseResult paramBrowseResult, Cascade paramCascade) {
    if (paramCascade == Cascade.a || paramCascade == Cascade.b)
      a(paramBrowseResult); 
    if (paramCascade == Cascade.b || paramCascade == Cascade.c)
      for (BrowseTable browseTable : paramBrowseResult.c.d())
        a(browseTable.e, Cascade.b);  
  }
  
  private void a(BrowseResult paramBrowseResult) {
    if (!isCancelled()) {
      updateMessage("Browse " + String.valueOf(paramBrowseResult.c) + "...");
      this.g = paramBrowseResult;
      this.g.e.a((Future)this);
      if (paramBrowseResult.c.d == null || paramBrowseResult.c.b.e.g())
        this.g.e.a(this.b); 
    } 
  }
  
  protected void succeeded() {
    this.b.i();
    this.b.close();
    if (this.e != Cascade.c) {
      this.f = Math.min(Math.max(0, this.f), this.c.getItems().size() - 1);
      boolean bool = this.c.getSelectionModel().isSelected(this.f);
      this.c.getSelectionModel().select(this.f);
      if (!bool)
        this.c.scrollTo(this.f); 
    } 
    this.a.a(ResultStatus.d, Cascade.b);
    this.c.a();
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.b.a(throwable);
    BrowseResult browseResult = (this.g != null) ? this.g : this.a;
    if (this.h) {
      Log.a("Timeout in Browse Task", throwable);
      StringBuilder stringBuilder = new StringBuilder();
      browseResult.a(ResultStatus.f, Cascade.b);
      stringBuilder.append(throwable.getMessage());
      if (!this.b.a.isMongo()) {
        stringBuilder.append("\n\nTimeout when reading from '").append(browseResult.c).append("'. Query is slow.\n");
        stringBuilder.append("Please consider adding table indexes or increase the timeout in the configuration dialog.");
      } 
      this.b.e("Browse Timeout");
      (new Alert$(Alert.AlertType.ERROR)).a(this.d).a("Error").c(stringBuilder.toString()).showAndWait();
    } else {
      browseResult.a(ResultStatus.g, Cascade.b);
      Log.b(throwable);
      String str = this.b.a.getHTMLMessageAndAdvice(throwable, "Data Loading Error", "Error by reading from '" + String.valueOf(browseResult.c) + "'.\n", ConnectivityTip.a);
      this.b.e(str);
      (new Alert$(Alert.AlertType.ERROR)).a(this.d).a("Data Loading Error").c(str).a(throwable).showAndWait();
    } 
  }
}
