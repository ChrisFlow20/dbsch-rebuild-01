package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.notation.BarkerNotation;
import com.wisecoders.dbs.diagram.fx.notation.IENotation;
import com.wisecoders.dbs.diagram.fx.notation.IEWithArrowsNotation;
import com.wisecoders.dbs.diagram.fx.notation.Idef1XNotation;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.ArrangerMode;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.EntityIterable;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.ScriptAddOnFolder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.convert.model.ConversionDictionary;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sql.parser.DQLParser;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import groovy.lang.Binding;
import groovy.lang.Script;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@DoNotObfuscate
public class Project extends AbstractUnit {
  public final Folder schemas;
  
  public final Folder layouts = new Folder("Layouts", "Layout", this, Layout.class, true);
  
  public final ArrayList children = new ArrayList();
  
  private Connector a;
  
  private final Folder[] b;
  
  public int tickId;
  
  private String c;
  
  public boolean isFreshImported = false;
  
  private ProjectType d = ProjectType.a;
  
  private String e;
  
  private String f;
  
  private String g;
  
  private String h;
  
  private String i;
  
  protected File t;
  
  private Notation j;
  
  private String k = null;
  
  private boolean l = false;
  
  private boolean m = false;
  
  private static final String n = "NotationMongo";
  
  private static final String o = "Notation";
  
  private static final String p = "NotationStrict";
  
  public Project(String paramString) {
    this(paramString, "MySql");
  }
  
  public Project(String paramString1, String paramString2) {
    super(paramString1);
    this.schemas = new Folder((Dbms.get(paramString2)).schemaAlias.c_(), this, Schema.class);
    setDbId(paramString2);
    this.children.add(this.schemas);
    Dbms dbms = Dbms.get(paramString2);
    if (dbms.projectScriptAddon1Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.projectScriptAddon1Name.c_(), dbms.projectScriptAddon1Load.c_(), dbms.projectScriptAddon1Script.c_())); 
    if (dbms.projectScriptAddon2Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.projectScriptAddon2Name.c_(), dbms.projectScriptAddon2Load.c_(), dbms.projectScriptAddon2Script.c_())); 
    if (dbms.projectScriptAddon3Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.projectScriptAddon3Name.c_(), dbms.projectScriptAddon3Load.c_(), dbms.projectScriptAddon3Script.c_())); 
    if (dbms.projectScriptAddon4Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.projectScriptAddon4Name.c_(), dbms.projectScriptAddon4Load.c_(), dbms.projectScriptAddon4Script.c_())); 
    if (dbms.projectPropertyAddon1Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.projectPropertyAddon1Name.c_(), dbms.projectPropertyAddon1Load.c_())); 
    if (dbms.projectPropertyAddon2Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.projectPropertyAddon2Name.c_(), dbms.projectPropertyAddon2Load.c_())); 
    if (dbms.projectPropertyAddon3Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.projectPropertyAddon3Name.c_(), dbms.projectPropertyAddon3Load.c_())); 
    if (dbms.projectPropertyAddon3Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.projectPropertyAddon4Name.c_(), dbms.projectPropertyAddon4Load.c_())); 
    this.children.add(this.layouts);
    this.b = new Folder[] { this.schemas };
  }
  
  public String getDefaultKey() {
    return this.c + "-" + this.c;
  }
  
  public void setSyncProject(boolean paramBoolean) {
    this.m = paramBoolean;
  }
  
  public boolean isSyncProject() {
    return this.m;
  }
  
  public int getChildrenCount() {
    return this.children.size();
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.children.get(paramInt);
  }
  
  public Folder[] getSyncFolders() {
    return this.b;
  }
  
  public String getSymbolicName() {
    return "Project";
  }
  
  public String getSymbolicIcon() {
    return "db.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.database_fill;
  }
  
  public Schema getOrCreateSchema(String paramString) {
    Schema schema = getSchema(paramString);
    if (schema == null)
      schema = createSchema(paramString); 
    return schema;
  }
  
  public Schema createSchema(String paramString) {
    return createSchema(paramString, (String)null);
  }
  
  public Schema createSchema(String paramString1, String paramString2) {
    Schema schema = new Schema(this, paramString1, paramString2);
    this.schemas.add(schema);
    return schema;
  }
  
  public Schema getSimilarSchema(Schema paramSchema) {
    return getSchema(paramSchema.getCatalogName(), paramSchema.getName());
  }
  
  public Entity getSimilarEntity(Entity paramEntity) {
    Schema schema = getSimilarSchema(paramEntity.getSchema());
    if (schema != null) {
      if (paramEntity instanceof Table)
        return schema.getTable(paramEntity.getName()); 
      if (paramEntity instanceof ChildEntity) {
        ChildEntity childEntity = (ChildEntity)paramEntity;
        Table table = schema.getTable(paramEntity.getEntity().getName());
        Column column = (table != null) ? table.getColumnByNameOrPath(childEntity.ownerColumn.getNameWithPath()) : null;
        return (column != null) ? column.getChildEntity() : null;
      } 
      if (paramEntity instanceof MaterializedView)
        return schema.getMaterializedView(paramEntity.getName()); 
      if (paramEntity instanceof View)
        return schema.getView(paramEntity.getName()); 
      if (paramEntity instanceof UserDataType)
        return schema.getUserDataType(paramEntity.getName()); 
    } 
    return null;
  }
  
  public Schema getSchema(String paramString) {
    Schema schema = null;
    int i = -1;
    while (schema == null && (i = paramString.indexOf(".", i + 1)) > -1)
      schema = getSchema(paramString.substring(0, i), paramString.substring(i + 1)); 
    if (schema == null)
      schema = getSchema((String)null, paramString); 
    return schema;
  }
  
  @GroovyMethod
  public Schema getSchema(String paramString1, String paramString2) {
    for (Schema schema : this.schemas) {
      if (schema.getName().equalsIgnoreCase(paramString2) && (
        schema.getCatalogName() == null || paramString1 == null || schema.getCatalogName().equalsIgnoreCase(paramString1)))
        return schema; 
    } 
    return null;
  }
  
  public Schema getSchemaUsingMetaDataCatalogAndSchemaName(String paramString1, String paramString2) {
    return (Dbms.get(this.c)).schemaIsCatalogInDatabaseMetaData.b() ? 
      getSchema(paramString2, paramString1) : 
      getSchema(paramString1, paramString2);
  }
  
  public Layout createLayout(String paramString) {
    Layout layout = new Layout(this, this.layouts.proposeName(paramString));
    this.layouts.add(layout);
    return layout;
  }
  
  @GroovyMethod
  public Layout createLayoutIncludeAllTables(String paramString, boolean paramBoolean) {
    return createLayoutIncludeAllTables(paramString, paramBoolean, true);
  }
  
  @GroovyMethod
  public Layout createLayoutIncludeAllTables(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    Layout layout = new Layout(this, this.layouts.proposeName(paramString));
    this.layouts.add(layout);
    for (Schema schema : this.schemas) {
      for (AbstractTable abstractTable : schema.getEntities()) {
        if (paramBoolean2) {
          layout.diagram.attachRecursiveAndCreateGroupForHierarchicalEntities(abstractTable, new Point(0.0D, 0.0D));
          continue;
        } 
        layout.diagram.attach(abstractTable, 0.0D, 0.0D);
      } 
    } 
    layout.diagram.autoArrange(paramBoolean1 ? ArrangerMode.REMOVE_GROUPS : ArrangerMode.SIMPLE);
    return layout;
  }
  
  public Layout cloneLayout(Layout paramLayout) {
    Layout layout = createLayout(this.layouts.proposeName(paramLayout.getName()));
    layout.cloneLayout(paramLayout, 0, 0, true);
    return layout;
  }
  
  public Layout getLayout(String paramString) {
    return (Layout)this.layouts.getByName(paramString);
  }
  
  public File getFile() {
    return this.t;
  }
  
  public boolean hasFile() {
    return (this.t != null);
  }
  
  public void setFile(File paramFile) {
    this.t = paramFile;
  }
  
  public String getDbId() {
    return this.c;
  }
  
  public void setDbId(String paramString) {
    if (StringUtil.isEmptyTrim(paramString))
      throw new NullPointerException(); 
    this.c = paramString;
    this.e = getDefaultKey();
    setUnitProperty(UnitProperty.f, Boolean.valueOf("MongoDb".equals(paramString)));
    setUnitProperty(UnitProperty.g, Boolean.valueOf("LogicalDesign".equals(paramString)));
    String str = (Dbms.get(paramString)).schemaAlias.c_();
    this.schemas.rename(str + "s", str);
  }
  
  public boolean hasSchemesWithSameName() {
    return this.l;
  }
  
  public void refresh() {
    this.schemas.refresh();
    this.layouts.refresh();
    this.l = false;
    ArrayList<String> arrayList = new ArrayList();
    for (Schema schema : this.schemas) {
      if (arrayList.contains(schema.getName()))
        this.l = true; 
      arrayList.add(schema.getName());
    } 
    if (this.a != null && this.a.isMarkedForDeletion())
      this.a = null; 
  }
  
  public String getTitle() {
    String str = getName();
    if (this.t != null)
      str = str + " [ " + str + " ]"; 
    return str;
  }
  
  public String toString() {
    return getName() + " {" + getName() + "}";
  }
  
  public String getStatistics() {
    int i = 0, j = 0;
    for (Layout layout : this.layouts) {
      i += layout.browses.size();
      j += layout.scripts.size();
    } 
    return "Project '" + getName() + "' " + getDbId() + " " + getEntityCount() + " Tables, " + this.layouts
      .size() + " Layouts, " + i + " Browse, " + j + " SQL Editors";
  }
  
  public boolean hasOperativeConnector() {
    return (this.a != null);
  }
  
  public void setOperativeConnector(Connector paramConnector) {
    this.a = paramConnector;
  }
  
  public Connector getOperativeConnector() {
    return this.a;
  }
  
  public Schema createSchema(Schema paramSchema) {
    return createSchema(paramSchema.getName(), paramSchema.getCatalogName());
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public TreeUnit getParent() {
    return null;
  }
  
  public final EntityIterable getEntities() {
    return () -> new a(this);
  }
  
  public int getEntityCount() {
    int i = 0;
    for (Schema schema : this.schemas)
      i += schema.tables.size() + schema.views.size(); 
    return i;
  }
  
  public boolean hasEntities() {
    return (getEntityCount() > 0);
  }
  
  public List findSimilarUnitsAsInSelection(List paramList) {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (TreeUnit treeUnit : paramList) {
      Schema schema = SyncUtil.a(treeUnit);
      if (schema != null) {
        Schema schema1 = getSchema(schema.getCatalogName(), schema.getName());
        if (schema1 != null) {
          if (treeUnit instanceof Schema) {
            uniqueArrayList.add(schema1);
            continue;
          } 
          if (treeUnit instanceof Table) {
            uniqueArrayList.add(schema1.tables.getByName(treeUnit.getName()));
            continue;
          } 
          if (treeUnit instanceof View) {
            uniqueArrayList.add(schema1.views.getByName(treeUnit.getName()));
            continue;
          } 
          if (treeUnit instanceof Trigger) {
            uniqueArrayList.add(schema1.triggers.getByName(treeUnit.getName()));
            continue;
          } 
          if (treeUnit instanceof Procedure) {
            uniqueArrayList.add(schema1.procedures.getByName(treeUnit.getName()));
            continue;
          } 
          if (treeUnit instanceof Function) {
            uniqueArrayList.add(schema1.functions.getByName(treeUnit.getName()));
            continue;
          } 
          if (treeUnit instanceof Sequence) {
            uniqueArrayList.add(schema1.sequences.getByName(treeUnit.getName()));
            continue;
          } 
          if (treeUnit instanceof Folder && ((Folder)treeUnit).childClass == Table.class) {
            uniqueArrayList.add(schema1.tables);
            continue;
          } 
          if (treeUnit instanceof Folder && ((Folder)treeUnit).childClass == View.class) {
            uniqueArrayList.add(schema1.views);
            continue;
          } 
          if (treeUnit instanceof Folder && ((Folder)treeUnit).childClass == MaterializedView.class) {
            uniqueArrayList.add(schema1.materializedViews);
            continue;
          } 
          if (treeUnit instanceof Folder && ((Folder)treeUnit).childClass == Trigger.class) {
            uniqueArrayList.add(schema1.triggers);
            continue;
          } 
          if (treeUnit instanceof Folder && ((Folder)treeUnit).childClass == Procedure.class) {
            uniqueArrayList.add(schema1.procedures);
            continue;
          } 
          if (treeUnit instanceof Folder && ((Folder)treeUnit).childClass == Function.class) {
            uniqueArrayList.add(schema1.functions);
            continue;
          } 
          if (treeUnit instanceof Folder && ((Folder)treeUnit).childClass == Sequence.class)
            uniqueArrayList.add(schema1.sequences); 
        } 
      } 
    } 
    return uniqueArrayList;
  }
  
  public int getTickId() {
    return this.tickId;
  }
  
  public String getKey() {
    return this.e;
  }
  
  public void setKey(String paramString) {
    if (paramString != null)
      this.e = paramString; 
  }
  
  public ProjectType getType() {
    return this.d;
  }
  
  public void setType(ProjectType paramProjectType) {
    if (paramProjectType != null)
      this.d = paramProjectType; 
  }
  
  public boolean isElasticsearch() {
    return "Elasticsearch".equalsIgnoreCase(this.c);
  }
  
  public String getSyncFilter() {
    return this.f;
  }
  
  public void setSyncFilter(String paramString) {
    this.f = StringUtil.isFilledTrim(paramString) ? paramString : null;
  }
  
  public void setSyncInitScript(String paramString) {
    this.g = StringUtil.isFilledTrim(paramString) ? paramString : null;
  }
  
  public String getSyncInitScript() {
    return this.g;
  }
  
  public String getDDLPreScript() {
    return this.h;
  }
  
  public void setDDLPreScript(String paramString) {
    this.h = StringUtil.isFilledTrim(paramString) ? paramString : null;
  }
  
  public String getDDLPostScript() {
    return this.i;
  }
  
  public void setDDLPostScript(String paramString) {
    this.i = StringUtil.isFilledTrim(paramString) ? paramString : null;
  }
  
  public void injectDDLPrePostScripts(AlterScript paramAlterScript) {
    paramAlterScript.injectStatements(SqlUtil.splitScriptIntoStatements(this.h), true);
    paramAlterScript.injectStatements(SqlUtil.splitScriptIntoStatements(this.i), false);
  }
  
  public void setNotation(Notation paramNotation) {
    this.j = paramNotation;
    Pref.a(is(UnitProperty.f).booleanValue() ? "NotationMongo" : "Notation", paramNotation.toString());
  }
  
  public static Idef1XNotation idef1xNotation = new Idef1XNotation();
  
  public static IENotation ieNotation = new IENotation();
  
  public static IEWithArrowsNotation ieWithArrowsNotation = new IEWithArrowsNotation();
  
  public static BarkerNotation barkerNotation = new BarkerNotation();
  
  public Notation getNotation() {
    if (this.j == null) {
      String str = is(UnitProperty.f).booleanValue() ? Pref.c("NotationMongo", "Barker") : Pref.c("Notation", "IEWithArrows");
      switch (str) {
        case "Idef1x":
          this.j = idef1xNotation;
          return this.j;
        case "Information Engineering":
          this.j = ieNotation;
          return this.j;
        case "IEWithArrows":
          this.j = ieWithArrowsNotation;
          return this.j;
      } 
      this.j = barkerNotation;
    } 
    return this.j;
  }
  
  public void setNotationStrict(boolean paramBoolean) {
    Pref.a("NotationStrict", paramBoolean);
  }
  
  public boolean isNotationStrict() {
    return Pref.b("NotationStrict", is(UnitProperty.g).booleanValue());
  }
  
  public List getCatalogNames() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (Schema schema : this.schemas)
      uniqueArrayList.add(schema.getCatalogName()); 
    return uniqueArrayList;
  }
  
  @GroovyMethod
  public List deduceVirtualForeignKeysFromQuery(String paramString) {
    return (new DQLParser(this)).a(paramString);
  }
  
  public boolean isDbVersionHigherEqual(String paramString) {
    return !isDbVersionBelow(paramString);
  }
  
  public boolean isDbVersionBelow(String paramString) {
    if (this.k == null)
      return false; 
    String[] arrayOfString1 = this.k.split("\\.");
    String[] arrayOfString2 = paramString.split("\\.");
    for (byte b = 0; b < arrayOfString1.length && b < arrayOfString2.length; b++) {
      if (Integer.parseInt(arrayOfString1[b]) < Integer.parseInt(arrayOfString2[b]))
        return true; 
      if (Integer.parseInt(arrayOfString1[b]) > Integer.parseInt(arrayOfString2[b]))
        return false; 
    } 
    return false;
  }
  
  public void setDbVersion(String paramString) {
    this.k = paramString;
  }
  
  public int getVirtualRelationCount() {
    byte b = 0;
    for (Schema schema : this.schemas) {
      for (Table table : schema.tables) {
        for (Relation relation : table.getRelations()) {
          if (relation.isVirtual())
            b++; 
        } 
      } 
    } 
    return b;
  }
  
  public Schema getInUseSchema() {
    for (Schema schema : this.schemas) {
      if (schema.isInUse())
        return schema; 
    } 
    if (!this.schemas.isEmpty())
      return (Schema)this.schemas.get(0); 
    return null;
  }
  
  public void setInUseSchema(Schema paramSchema) {
    for (Schema schema : this.schemas)
      schema.a(false); 
    paramSchema.a(true);
  }
  
  public Schema getSchemaInUseCatalog(String paramString) {
    Schema schema = getInUseSchema();
    return getSchema((schema != null) ? schema.getCatalogName() : null, paramString);
  }
  
  public Project convert(String paramString) {
    ConversionDictionary conversionDictionary = ConversionDictionary.a(this.c);
    Project project = new Project(getName(), paramString);
    project.setSyncProject(true);
    SyncPair syncPair = new SyncPair(project, this);
    syncPair.mergeInto(SyncSide.left, null);
    for (Schema schema : project.schemas) {
      for (Trigger trigger : schema.triggers)
        trigger.markForDeletion(); 
      for (Procedure procedure : schema.procedures)
        procedure.markForDeletion(); 
      for (Function function : schema.functions)
        function.markForDeletion(); 
      for (Table table : schema.tables) {
        table.setPreScript((String)null);
        table.setPostScript((String)null);
      } 
    } 
    for (Layout layout1 : this.layouts) {
      Layout layout2 = project.createLayout(layout1.getName());
      layout2.cloneLayout(layout1, 0, 0, false);
      layout2.setConfirmed(true);
      layout2.attachManyToManyGeneratedTables();
    } 
    project.refresh();
    conversionDictionary.a(project, paramString, "LogicalDesign".equals(this.c));
    if ("LogicalDesign".equals(getDbId()) || "LogicalDesign".equals(paramString))
      NamingDictionary.c.a(project, is(UnitProperty.g).booleanValue()); 
    NamingDictionary.c.a(project);
    project.setSyncProject(false);
    project.refresh();
    if (Sys.B.conversionGroovyScript.j()) {
      Binding binding = new Binding();
      binding.setVariable("project", this);
      binding.setVariable("convertedProject", project);
      Script script = Groovy.a.b(binding, Sys.B.conversionGroovyScript.c_());
      script.run();
    } 
    return project;
  }
}
