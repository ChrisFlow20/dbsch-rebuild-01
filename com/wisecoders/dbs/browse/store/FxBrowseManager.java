package com.wisecoders.dbs.browse.store;

import com.wisecoders.dbs.browse.fx.FxBrowseTableView;
import com.wisecoders.dbs.browse.model.BrowseResult;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.browse.model.Cascade;
import com.wisecoders.dbs.data.model.result.ResultStatus;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.UniqueCopyOnWriteArrayList;
import java.util.List;
import javafx.application.Platform;

public abstract class FxBrowseManager {
  private final Workspace a;
  
  protected final List c = new UniqueCopyOnWriteArrayList();
  
  private FxBrowseLoaderTask b;
  
  public FxBrowseManager(Workspace paramWorkspace) {
    this.a = paramWorkspace;
  }
  
  public String b() {
    return this.a.m().getDbId();
  }
  
  public void c() {
    if (this.b != null)
      if (this.b.isDone()) {
        this.b = null;
      } else {
        this.b.a();
      }  
    if (this.b == null && this.c.size() > 0) {
      a a = this.c.get(0);
      this.c.remove(a);
      Platform.runLater(() -> {
            Connector connector = a();
            if (connector != null) {
              a(parama.b, parama.c);
              this.b = new FxBrowseLoaderTask(this.a, parama.a, parama.b, parama.c, connector);
              this.a.getRx().a(this.b, false);
            } 
          });
    } 
  }
  
  private void a(BrowseResult paramBrowseResult, Cascade paramCascade) {
    if (paramCascade != Cascade.c) {
      paramBrowseResult.a(ResultStatus.c, Cascade.a);
      paramBrowseResult.x();
    } 
    if (paramCascade == Cascade.b || paramCascade == Cascade.c)
      for (BrowseTable browseTable : paramBrowseResult.c.d())
        a(browseTable.e, Cascade.b);  
  }
  
  public void a(FxBrowseTableView paramFxBrowseTableView, BrowseResult paramBrowseResult, Cascade paramCascade) {
    b(paramFxBrowseTableView, paramBrowseResult, paramCascade);
  }
  
  private void b(FxBrowseTableView paramFxBrowseTableView, BrowseResult paramBrowseResult, Cascade paramCascade) {
    if (this.b != null && !this.b.isDone() && this.b.a.c.a(paramBrowseResult.c))
      this.b.cancel(); 
    for (a a : this.c) {
      if (a.b == paramBrowseResult) {
        if (a.c == Cascade.b)
          paramCascade = Cascade.b; 
        this.c.remove(a);
      } 
    } 
    this.c.add(new a(paramFxBrowseTableView, paramBrowseResult, paramCascade));
  }
  
  public abstract Connector a();
}
