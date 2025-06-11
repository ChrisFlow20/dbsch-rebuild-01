package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.task.FxGroovyScriptTask;
import com.wisecoders.dbs.data.task.FxJavaScriptTask;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.fx.FxSplitWithTabPane;
import com.wisecoders.dbs.diagram.model.FxToolEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.geometry.Side;
import javafx.scene.control.Tab;

public abstract class FxAbstractSqlEditor extends FxSplitWithTabPane implements FxToolEditor {
  protected final Workspace a;
  
  private int g = 0;
  
  protected boolean b = true;
  
  protected Connector c;
  
  protected Envoy d;
  
  private final List h;
  
  int e;
  
  private static final String i = "ScriptIgnoreErrors";
  
  private static final String j = "ScriptAutoCommit";
  
  public void f() {
    u().a(Task.class);
    if (this.d != null) {
      this.d.close();
      this.d = null;
    } 
    this.c = null;
  }
  
  public Connector b(boolean paramBoolean) {
    if (this.c != null)
      return this.c; 
    if (this.a.t())
      return this.a.s(); 
    if (paramBoolean) {
      if (this.d != null) {
        this.d.n();
        this.d = null;
      } 
      return this.c = this.a.a(getWorkspace());
    } 
    return null;
  }
  
  public Connector a() {
    return this.c;
  }
  
  public Envoy a(String paramString, boolean paramBoolean) {
    if (this.d != null && !this.d.o())
      return this.d; 
    Connector connector = b(paramBoolean);
    if (connector != null)
      this.d = connector.startEnvoy(paramString); 
    return this.d;
  }
  
  public FxAbstractSqlEditor(Workspace paramWorkspace) {
    this.h = new ArrayList();
    this.e = 0;
    this.a = paramWorkspace;
    this.f.setSide(Side.BOTTOM);
  }
  
  protected void b() {
    for (FxResultPane fxResultPane : this.h) {
      fxResultPane.d.q();
      int i = fxResultPane.d.D();
      if (i > 0) {
        if (fxResultPane.d.H()) {
          fxResultPane.b("Saved " + i + ((i > 1) ? " rows" : " row") + " to file. No data shown on screen.");
          continue;
        } 
        fxResultPane.b("Read " + i + ((i > 1) ? " rows" : "row"));
      } 
    } 
  }
  
  public Task a(String paramString, FxResultPane paramFxResultPane) {
    Envoy envoy = a("Run Query", true);
    if (envoy != null && paramString != null) {
      if (envoy.a.isMongo())
        Log.c("Execute : " + paramString); 
      paramFxResultPane.d.x();
      paramFxResultPane.a(false);
      f(false);
      paramFxResultPane.c(paramString);
      paramFxResultPane.b("Processing...");
      this.h.add(paramFxResultPane);
      FxAbstractSqlEditor$1 fxAbstractSqlEditor$1 = new FxAbstractSqlEditor$1(this, envoy, paramString, paramFxResultPane);
      u().b();
      return fxAbstractSqlEditor$1;
    } 
    return null;
  }
  
  public Task c() {
    FxScriptResultPane fxScriptResultPane = d(false);
    Envoy envoy = a("Execute Script", true);
    if (envoy != null) {
      fxScriptResultPane.a(false);
      f(false);
      return new FxAbstractSqlEditor$2(this, fxScriptResultPane, envoy);
    } 
    return null;
  }
  
  protected Task a(Language paramLanguage, String paramString, FxScriptResultPane paramFxScriptResultPane) {
    Envoy envoy = a("Execute task", false);
    if (paramString != null) {
      paramFxScriptResultPane.a(false);
      f(false);
      paramFxScriptResultPane.a("Processing...");
      switch (FxAbstractSqlEditor$6.a[paramLanguage.ordinal()]) {
        case 1:
          return new FxGroovyScriptTask(this, envoy, paramString, paramFxScriptResultPane);
        case 2:
          return new FxJavaScriptTask(this, envoy, paramString, paramFxScriptResultPane);
      } 
    } 
    return null;
  }
  
  public Task explain() {
    String str = k();
    if (!Dbms.get(this.a.m().getDbId()).canExplainPlan()) {
      u().a(getWorkspace(), d() + " does not support explain plan.", new String[0]);
    } else if (str == null) {
      a("Nothing to execute");
    } else {
      Envoy envoy = a("Explain Plan", true);
      if (envoy != null) {
        FxExplainResultPane fxExplainResultPane = g(false);
        fxExplainResultPane.a(false);
        f(false);
        fxExplainResultPane.a("Processing...");
        FxAbstractSqlEditor$3 fxAbstractSqlEditor$3 = new FxAbstractSqlEditor$3(this, envoy, str, fxExplainResultPane);
        u().b();
        return fxAbstractSqlEditor$3;
      } 
    } 
    return null;
  }
  
  @Action
  public void rollback() {
    c(false);
    if (this.d != null) {
      FxAbstractSqlEditor$4 fxAbstractSqlEditor$4 = new FxAbstractSqlEditor$4(this);
      u().a(fxAbstractSqlEditor$4);
      u().b();
    } 
  }
  
  @Action
  public void commit() {
    c(false);
    if (this.d != null) {
      FxAbstractSqlEditor$5 fxAbstractSqlEditor$5 = new FxAbstractSqlEditor$5(this);
      u().a(fxAbstractSqlEditor$5);
      u().b();
    } 
  }
  
  public String d() {
    return this.a.m().getDbId();
  }
  
  public FxScriptResultPane d(boolean paramBoolean) {
    if (!paramBoolean) {
      Tab tab = (Tab)this.f.getSelectionModel().getSelectedItem();
      if (tab instanceof FxScriptResultPane)
        tab.getTabPane().getTabs().remove(tab); 
    } 
    FxScriptResultPane fxScriptResultPane = new FxScriptResultPane(this, "Script " + ++this.g);
    b(fxScriptResultPane);
    return fxScriptResultPane;
  }
  
  public FxResultPane e(boolean paramBoolean) {
    if (!paramBoolean) {
      Tab tab = (Tab)this.f.getSelectionModel().getSelectedItem();
      if (tab instanceof FxResultPane)
        return (FxResultPane)tab; 
    } 
    FxResultPane fxResultPane = new FxResultPane(this, "Query " + ++this.g);
    b(fxResultPane);
    return fxResultPane;
  }
  
  private FxExplainResultPane g(boolean paramBoolean) {
    if (!paramBoolean) {
      Tab tab = (Tab)this.f.getSelectionModel().getSelectedItem();
      if (tab instanceof FxExplainResultPane)
        return (FxExplainResultPane)tab; 
    } 
    FxExplainResultPane fxExplainResultPane = new FxExplainResultPane(this, "Plan " + ++this.g);
    b(fxExplainResultPane);
    return fxExplainResultPane;
  }
  
  public void a(Tab paramTab) {
    this.f.getTabs().remove(paramTab);
  }
  
  public void f(boolean paramBoolean) {
    this.b = paramBoolean;
    u().b();
  }
  
  public static boolean v() {
    return Pref.b("ScriptIgnoreErrors", true);
  }
  
  public static boolean w() {
    return Pref.b("ScriptAutoCommit", true);
  }
  
  public void scriptIgnoreErrors() {
    Pref.a("ScriptIgnoreErrors", !v());
  }
  
  public void scriptAutoCommit() {
    Pref.a("ScriptAutoCommit", !w());
  }
  
  public Project x() {
    return this.a.m();
  }
  
  public abstract Task run();
  
  public abstract String k();
  
  public abstract void l();
  
  public abstract String r();
  
  public abstract void s();
  
  public abstract String t();
  
  public abstract void a(int paramInt, String paramString);
  
  public abstract void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString);
  
  public abstract void a(String paramString);
  
  public abstract void c(boolean paramBoolean);
  
  public abstract Rx u();
}
