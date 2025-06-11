package com.wisecoders.dbs.dbms.reverseEngineer.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.dialogs.layout.FxSchemaMappingDialog;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FxSchemaSelectionDialog;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.UniqueArrayList;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

public class FxImportSelectionDialog extends FxSchemaSelectionDialog {
  private final List i = new ArrayList();
  
  private final Envoy j;
  
  private final List k;
  
  private final boolean l;
  
  private final boolean m;
  
  private boolean n = false;
  
  private static final int o = 300;
  
  private boolean p;
  
  public FxImportSelectionDialog(WorkspaceWindow paramWorkspaceWindow, Envoy paramEnvoy, String paramString1, Project paramProject, List paramList, boolean paramBoolean1, String paramString2, boolean paramBoolean2) {
    super(paramWorkspaceWindow, paramString1, paramProject, paramString2);
    this.p = false;
    this.j = paramEnvoy;
    this.k = paramList;
    this.l = paramBoolean2;
    this.rx.a("flagShowAllSchemas", () -> this.n);
    c();
    this.m = true;
    Platform.runLater(() -> {
          HBox$ hBox$ = (new HBox$()).d();
          if ("SqlServer".equals(paramProject.getDbId()) || "SAPAdaptiveServer".equals(paramProject.getDbId()))
            hBox$.getChildren().add(this.rx.b("showOnlyDBOSchemas", this.n)); 
          if (paramBoolean)
            hBox$.getChildren().add(this.rx.j("editSchemaMapping")); 
          if (!hBox$.getChildren().isEmpty())
            this.f.getChildren().add(hBox$); 
        });
  }
  
  @Action(d = "flagShowAllSchemas")
  public void showOnlyDBOSchemas() {
    this.n = !this.n;
    a();
    this.rx.b();
  }
  
  @Action
  public void editSchemaMapping() {
    (new FxSchemaMappingDialog(this.a, this.j.a)).showDialog();
  }
  
  private void c() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (TreeUnit treeUnit : this.k) {
      Schema schema = SyncUtil.a(treeUnit);
      if (schema != null)
        uniqueArrayList.add(this.b.getSchema(schema.getCatalogName(), schema.getName())); 
    } 
    if ((Dbms.get(this.b.getDbId())).useDefaultSchema.b())
      uniqueArrayList.addAll(this.b.schemas); 
    if (!uniqueArrayList.isEmpty()) {
      FxImportSelectionDialog$1 fxImportSelectionDialog$1 = new FxImportSelectionDialog$1(this, this.j, uniqueArrayList, this, uniqueArrayList);
      this.rx.a(fxImportSelectionDialog$1);
      this.i.addAll(uniqueArrayList);
    } 
  }
  
  private void b(List paramList) {
    boolean bool = false;
    int i = 0;
    for (Schema schema : paramList) {
      i += schema.procedures.size();
      if (schema.procedures.size() > 300) {
        f(schema.procedures);
        bool = true;
      } 
      i += schema.functions.size();
      if (schema.functions.size() > 300) {
        f(schema.functions);
        bool = true;
      } 
    } 
    if (bool && this.l && !this.p) {
      this.p = true;
      this.rx.d(getDialogPane().getScene(), "For better a performance we un-ticked " + i + " procedures and functions.");
    } 
  }
  
  public void a(TreeUnit paramTreeUnit) {
    if (this.m) {
      Schema schema = g(paramTreeUnit);
      if (schema != null)
        a(schema); 
    } 
  }
  
  private Schema g(TreeUnit paramTreeUnit) {
    TreeUnit treeUnit = paramTreeUnit;
    while (treeUnit != null) {
      if (treeUnit instanceof Schema)
        return (Schema)treeUnit; 
      treeUnit = treeUnit.getParent();
    } 
    return null;
  }
  
  private void a(Schema paramSchema) {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    if (paramSchema == null) {
      uniqueArrayList.addAll(this.b.schemas);
    } else {
      uniqueArrayList.add(paramSchema);
    } 
    uniqueArrayList.removeAll(this.i);
    if (!uniqueArrayList.isEmpty()) {
      FxImportSelectionDialog$2 fxImportSelectionDialog$2 = new FxImportSelectionDialog$2(this, this.j, uniqueArrayList, this, uniqueArrayList);
      this.rx.a(fxImportSelectionDialog$2);
      this.i.addAll(uniqueArrayList);
    } 
  }
  
  protected boolean b(TreeUnit paramTreeUnit) {
    return (this.n && "SqlServer".equalsIgnoreCase(this.b.getDbId()) && paramTreeUnit instanceof Schema && !"dbo".equals(paramTreeUnit.getName()));
  }
}
