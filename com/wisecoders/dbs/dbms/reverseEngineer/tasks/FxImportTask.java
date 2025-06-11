package com.wisecoders.dbs.dbms.reverseEngineer.tasks;

import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.fx.FxTestConnectivityTask;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.ArrangerMode;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.sql.generator.StatementType;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.QuickTour;
import com.wisecoders.dbs.tips.tips.QuickTour$Type;
import java.util.ArrayList;
import java.util.Objects;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;

public class FxImportTask extends Task {
  protected final Workspace a;
  
  private final Rx b = new Rx(FxImportTask.class, this);
  
  private final Project c;
  
  private final TreeSelection d;
  
  private final Envoy e;
  
  private static final String f = "Main Layout";
  
  FxImportTask(Workspace paramWorkspace, Project paramProject, TreeSelection paramTreeSelection, Envoy paramEnvoy) {
    this.a = paramWorkspace;
    this.c = paramProject;
    this.e = paramEnvoy;
    this.d = paramTreeSelection;
    this.b.G(paramEnvoy.e());
    paramWorkspace.getRx().a(this);
  }
  
  protected Boolean a() {
    Thread.currentThread().setName("DbSchema: Reverse Engineer Task");
    Log.c("Import task from " + this.e.a.dbId);
    updateMessage("Reverse Engineer Schema");
    boolean bool = (new FxImportTask$1(this, this.c, this.d, this.e)).e();
    SyncUtil.a(this.c, this.d);
    Log.c("Import task done.");
    updateMessage("Creating the layouts...");
    return Boolean.valueOf(bool);
  }
  
  protected void failed() {
    Throwable throwable = getException();
    Log.a("Error in reverse engineer schema", throwable);
    String str = this.e.a.getHTMLMessageAndAdvice(throwable, null, null, ConnectivityTip.a) + " \n\nJDBC URL: " + this.e.a.getHTMLMessageAndAdvice(throwable, null, null, ConnectivityTip.a);
    this.b.b(this.a, str, throwable);
    this.e.c(false);
    this.e.a(throwable);
  }
  
  protected void succeeded() {
    b();
    Log.c("REVERSE ENGINEERED " + this.c.getEntityCount() + " TABLES FROM " + this.e.a.dbId + ".");
    this.c.isFreshImported = true;
    this.c.setOperativeConnector(this.e.a);
    this.a.b(this.c);
    this.e.c(false);
    this.e.close();
    if (!this.c.is(UnitProperty.f).booleanValue() && !"GoogleBigQuery".equalsIgnoreCase(this.c.getDbId())) {
      boolean bool = false;
      for (Schema schema : this.c.schemas) {
        if (schema.tables.size() > 4 && schema.getForeignKeyCount() == 0)
          bool = true; 
      } 
      if (bool)
        this.b.d(this.a, "virtualFksNotification"); 
    } 
    Objects.requireNonNull(this.a);
    Platform.runLater(this.a::F);
  }
  
  private void b() {
    Log.c("Create default layouts...");
    boolean bool1 = false;
    Schema schema = null;
    int i = -1;
    for (Schema schema1 : this.c.schemas) {
      if (schema1.hasEntities() && i < schema1.getRelationCount()) {
        schema = schema1;
        i = schema1.getRelationCount();
      } 
    } 
    boolean bool2 = true;
    for (Schema schema1 : this.c.schemas) {
      if (schema1.hasEntities()) {
        Log.c("Create layouts for schema '" + String.valueOf(schema1) + "'");
        bool1 = true;
        Layout layout = this.c.createLayout((this.c.schemas.size() == 1) ? "Main Layout" : schema1.getName());
        updateMessage("Arrange Tables in Layout '" + layout.getName() + "'");
        for (AbstractTable abstractTable : schema1.getEntities())
          layout.diagram.attachRecursive(abstractTable, layout.diagram.getFreePlacePoint(), false); 
        layout.diagram.hideFunctionalColumns();
        if (layout.is(UnitProperty.f).booleanValue()) {
          layout.diagram.setDefaultLineText(LineTextType.a);
          layout.diagram.setLineTextType(LineTextType.a);
        } 
        layout.diagram.hideColumnsOnLargeDepicts();
        layout.refresh();
        layout.diagram.autoArrange(ArrangerMode.KEEP_GROUPS);
        Depict depict = layout.diagram.getMostReferredDepict();
        if (bool2 && depict != null) {
          layout.diagram.createCallout(this.b
              .H("groupLayoutCallout"), new Point(depict
                .getPosition().a(), depict.getPosition().b() - 140.0D));
          bool2 = false;
        } 
      } 
    } 
    if (this.c.is(UnitProperty.f).booleanValue())
      if (bool1) {
        for (Layout layout : this.c.layouts) {
          for (Group group : layout.diagram.groups)
            group.rename("Collection '" + group.getName() + "'"); 
        } 
      } else {
        Layout layout = this.c.createLayout("Main Layout");
        Script script = layout.createScript("Sample");
        script.setText("db.sampleCollection.insert({\"SampleKey\":\"SampleValue\"});");
      }  
    if (schema != null)
      a(schema); 
    if (this.c.layouts.isEmpty()) {
      Layout layout = this.c.createLayout("Main Layout");
      layout.diagram.createCallout(this.b.H("emptyLayout"), new Point(50.0D, 50.0D));
    } 
  }
  
  private void a(Schema paramSchema) {
    Log.c("Create layout with tools for schema '" + String.valueOf(paramSchema) + "'");
    Layout layout = this.c.createLayout("~Layout with Sample Tools");
    for (AbstractTable abstractTable : paramSchema.getEntities())
      layout.diagram.attachRecursive(abstractTable, layout.diagram.getFreePlacePoint(), false); 
    layout.diagram.setShowDataType(true);
    layout.diagram.hideFunctionalColumns();
    layout.diagram.hideColumnsOnLargeDepicts();
    layout.diagram.autoArrange(layout.is(UnitProperty.f).booleanValue() ? ArrangerMode.KEEP_GROUPS : ArrangerMode.REMOVE_GROUPS);
    Log.c("Find most referred depict...");
    Depict depict = layout.diagram.getMostReferredDepict();
    if (depict != null)
      a(layout, depict); 
  }
  
  private void a(Layout paramLayout, Depict paramDepict) {
    Log.c("Create sample tools in layout '" + String.valueOf(paramLayout) + "'");
    Relation relation = a(paramDepict.entity, (Entity)null);
    Browse browse = paramLayout.createBrowse("Relational Data Editor");
    BrowseTable browseTable = browse.a(paramDepict.entity);
    if (relation != null) {
      BrowseTable browseTable1 = browseTable.a(relation);
      Relation relation1 = a(browseTable1.c, paramDepict.entity);
      if (License.a().e() && relation1 != null)
        browseTable1.a(relation1); 
    } 
    browse.refresh();
    if (License.a().e()) {
      Query query = paramLayout.createQuery("Query Builder");
      QueryTable queryTable = query.a(paramDepict.entity).a();
      if (relation != null) {
        QueryTable queryTable1 = queryTable.a(relation).a();
        Relation relation1 = a(queryTable1.b, paramDepict.entity);
        if (relation1 != null)
          queryTable1.a(relation1).a(); 
      } 
    } 
    if (this.c.is(UnitProperty.f).booleanValue()) {
      Script script = paramLayout.createScript("Query Editor");
      ArrayList<Entity> arrayList = new ArrayList();
      arrayList.add(paramDepict.getEntity());
      String str = Dbms.get(paramLayout.project.getDbId()).getScriptGenerator(arrayList).a(StatementType.d) + "\n\n//OR\n\nUSE " + Dbms.get(paramLayout.project.getDbId()).getScriptGenerator(arrayList).a(StatementType.d) + ";\ndb." + paramDepict.getEntity().getSchema().ref() + ".find()\n\n//OR\n\nUSE " + paramDepict.getEntity().getName() + ";\ndb.getCollection('" + paramDepict.getEntity().getSchema().ref() + "').find()";
      script.setText(str);
      int i = this.c.getVirtualRelationCount();
      if (i > 0)
        this.a.getRx().a(this.a, this.b.a("messageRelationships.text", new String[] { "" + i }), paramActionEvent -> new FxTipsDialog(this.a, new QuickTour(QuickTour$Type.a, true))); 
    } else {
      Script script = paramLayout.createScript("SQL Editor");
      script.setText(Dbms.get(paramLayout.project.getDbId()).getScriptGenerator(paramDepict.entity).a(StatementType.d));
    } 
    if (FxTestConnectivityTask.d > 0)
      this.a.getRx().a(this.a, this.b.a("connectivityFeedback.text", new String[0]), paramActionEvent -> (new FxTechnicalSupportDialog(this.a.getWindow(), this.b.a("connectivityFeedback.title.text", new String[0]), null, true)).showDialog()); 
  }
  
  private Relation a(Entity paramEntity1, Entity paramEntity2) {
    Relation relation = null;
    int i = -1;
    for (Relation relation1 : paramEntity1.getRelations()) {
      if (!relation1.isAutoReference() && relation1.getTargetEntity() != paramEntity2 && !(relation1.getTargetEntity() instanceof com.wisecoders.dbs.schema.ChildEntity)) {
        int j = relation1.getTargetEntity().getRelations().size() + relation1.getTargetEntity().getImportedRelations().size();
        if (j > i) {
          relation = relation1;
          i = j;
        } 
      } 
    } 
    for (Relation relation1 : paramEntity1.getImportedRelations()) {
      if (!relation1.isAutoReference() && relation1.getEntity() != paramEntity2 && !(relation1.getEntity() instanceof com.wisecoders.dbs.schema.ChildEntity)) {
        int j = relation1.getTargetEntity().getRelations().size() + relation1.getTargetEntity().getImportedRelations().size();
        if (j > i) {
          relation = relation1;
          i = j;
        } 
      } 
    } 
    return relation;
  }
}
