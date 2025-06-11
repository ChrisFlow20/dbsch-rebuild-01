package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPrioritizable;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.EntityIterable;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.ScriptAddOnFolder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

@DoNotObfuscate
public final class Schema extends AbstractUnit implements SyncPrioritizable, DbUnit {
  public final Project project;
  
  private String a;
  
  private String b;
  
  private String c;
  
  private String d;
  
  private String e;
  
  private long f;
  
  private int g;
  
  public final Folder tables;
  
  public final Folder userDataTypes;
  
  public final Folder views;
  
  public final Folder materializedViews;
  
  public final Folder sequences;
  
  public final Folder procedures;
  
  public final Folder functions;
  
  public final Folder triggers;
  
  public final Folder rules;
  
  public final ArrayList children;
  
  private final Folder[] h;
  
  public final NameFinder nameFinder;
  
  private boolean i;
  
  private Table j;
  
  public static final String PK_TABLE = "~pkTable~";
  
  public static final String PK_COLUMN = "~pkColumn~";
  
  public Schema(Project paramProject, String paramString1, String paramString2) {
    super(paramString1);
    ArrayList<? super Folder> arrayList;
    this.f = -1L;
    this.g = -1;
    this.views = new Folder("View", this, View.class);
    this.sequences = new Folder("Sequence", this, Sequence.class);
    this.procedures = new Folder("Procedure", this, Procedure.class);
    this.functions = new Folder("Function", this, Function.class);
    this.triggers = new Folder("Trigger", this, Trigger.class);
    this.rules = new Folder("Rule", this, Rule.class);
    this.children = new ArrayList();
    this.nameFinder = new NameFinder(this);
    this.i = false;
    if (paramProject == null)
      throw new NullPointerException(); 
    this.project = paramProject;
    this.a = paramString2;
    Dbms dbms = Dbms.get(paramProject.getDbId());
    this.tables = new Folder(dbms.tableAlias.c_(), this, Table.class);
    this.userDataTypes = new Folder(dbms.userDataTypeAlias.c_(), this, UserDataType.class, true);
    this.materializedViews = new Folder(dbms.materializedViewAlias.c_(), this, MaterializedView.class);
    switch (paramProject.getDbId()) {
      case "MongoDb":
        Collections.addAll(this.children, new Folder[] { this.tables, this.views });
        this.h = new Folder[] { this.tables, this.views };
        break;
      case "PostgreSQL":
        Collections.addAll(this.children, new Folder[] { this.tables, this.views, this.userDataTypes, this.materializedViews, this.sequences, this.procedures, this.functions, this.triggers, this.rules });
        this.h = new Folder[] { this.userDataTypes, this.sequences, this.tables, this.views, this.materializedViews, this.procedures, this.functions, this.triggers, this.rules };
        break;
      case "LogicalDesign":
        Collections.addAll(this.children, new Folder[] { this.tables, this.userDataTypes, this.sequences });
        this.h = new Folder[] { this.tables };
        break;
      default:
        Collections.addAll(this.children, new Folder[] { this.tables, this.materializedViews, this.views, this.userDataTypes, this.sequences, this.procedures, this.functions, this.triggers });
        arrayList = new ArrayList();
        Collections.addAll(arrayList, new Folder[] { this.userDataTypes, this.sequences, this.tables, this.materializedViews, this.views, this.procedures, this.functions, this.triggers });
        if (dbms.defaultMaterializedViewText.q()) {
          this.children.remove(this.materializedViews);
          arrayList.remove(this.materializedViews);
        } 
        if (dbms.userDataTypeDeclaration.q()) {
          this.children.remove(this.userDataTypes);
          arrayList.remove(this.userDataTypes);
        } 
        this.h = arrayList.<Folder>toArray(new Folder[0]);
        break;
    } 
    if (dbms.schemaScriptAddon1Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.schemaScriptAddon1Name.c_(), dbms.schemaScriptAddon1Load.c_(), dbms.schemaScriptAddon1Script.c_())); 
    if (dbms.schemaScriptAddon2Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.schemaScriptAddon2Name.c_(), dbms.schemaScriptAddon2Load.c_(), dbms.schemaScriptAddon2Script.c_())); 
    if (dbms.schemaScriptAddon3Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.schemaScriptAddon3Name.c_(), dbms.schemaScriptAddon3Load.c_(), dbms.schemaScriptAddon3Script.c_())); 
    if (dbms.schemaScriptAddon4Load.j())
      this.children.add(new ScriptAddOnFolder(this, dbms.schemaScriptAddon4Name.c_(), dbms.schemaScriptAddon4Load.c_(), dbms.schemaScriptAddon4Script.c_())); 
    if (dbms.schemaPropertyAddon1Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.schemaPropertyAddon1Name.c_(), dbms.schemaPropertyAddon1Load.c_())); 
    if (dbms.schemaPropertyAddon2Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.schemaPropertyAddon2Name.c_(), dbms.schemaPropertyAddon2Load.c_())); 
    if (dbms.schemaPropertyAddon3Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.schemaPropertyAddon3Name.c_(), dbms.schemaPropertyAddon3Load.c_())); 
    if (dbms.schemaPropertyAddon4Load.j())
      this.children.add(new PropertyAddOnFolder(this, dbms.schemaPropertyAddon4Name.c_(), dbms.schemaPropertyAddon4Load.c_())); 
  }
  
  public int getChildrenCount() {
    return this.children.size();
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.children.get(paramInt);
  }
  
  public Folder[] getSyncFolders() {
    return this.h;
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public String getCatalogName() {
    return this.a;
  }
  
  public String getCatalogName(SchemaMapping paramSchemaMapping) {
    if (paramSchemaMapping != null) {
      String str = paramSchemaMapping.getRemoteSchemaName(this);
      int i;
      if (str != null && (i = str.indexOf(".")) > 0)
        return str.substring(0, i); 
    } 
    return this.a;
  }
  
  public String getNameWithCatalog() {
    return (this.a != null) ? (this.a + "." + this.a) : getName();
  }
  
  public String getMDCatalog() {
    return (Dbms.get(getDbId())).schemaIsCatalogInDatabaseMetaData.b() ? getName() : this.a;
  }
  
  public String getMDSchema() {
    return (Dbms.get(getDbId())).schemaIsCatalogInDatabaseMetaData.b() ? this.a : getName();
  }
  
  public Table getTable(String paramString) {
    if (this.j != null && this.tables.contains(this.j) && this.j
      .getName().equalsIgnoreCase(paramString))
      return this.j; 
    return (Table)this.tables.getByName(paramString);
  }
  
  public Table getOrCreateTable(String paramString) {
    Table table = getTable(paramString);
    if (table == null)
      table = createTable(paramString); 
    return table;
  }
  
  public View getOrCreateView(String paramString) {
    View view = getView(paramString);
    if (view == null)
      view = createView(paramString); 
    return view;
  }
  
  public MaterializedView getOrCreateMaterializedView(String paramString) {
    MaterializedView materializedView = getMaterializedView(paramString);
    if (materializedView == null)
      materializedView = createMaterializedView(paramString); 
    return materializedView;
  }
  
  public Sequence getOrCreateSequence(String paramString) {
    Sequence sequence = (Sequence)this.sequences.getByName(paramString);
    if (sequence == null)
      sequence = createSequence(paramString); 
    return sequence;
  }
  
  public UserDataType getOrCreateUserDataType(String paramString) {
    UserDataType userDataType = (UserDataType)this.userDataTypes.getByName(paramString);
    if (userDataType == null)
      userDataType = createUserDataType(paramString); 
    return userDataType;
  }
  
  public Function getOrCreateFunction(String paramString) {
    Function function = (Function)this.functions.getByName(paramString);
    if (function == null)
      function = createFunction(paramString); 
    return function;
  }
  
  public Procedure getOrCreateProcedure(String paramString) {
    Procedure procedure = (Procedure)this.procedures.getByName(paramString);
    if (procedure == null)
      procedure = createProcedure(paramString); 
    return procedure;
  }
  
  public Trigger getOrCreateTrigger(String paramString) {
    Trigger trigger = (Trigger)this.triggers.getByName(paramString);
    if (trigger == null)
      trigger = createTrigger(paramString); 
    return trigger;
  }
  
  public AbstractUnit getOrCreate(String paramString, Class<Table> paramClass) {
    if (paramClass == Table.class)
      return getOrCreateTable(paramString); 
    if (paramClass == View.class)
      return getOrCreateView(paramString); 
    if (paramClass == MaterializedView.class)
      return getOrCreateMaterializedView(paramString); 
    if (paramClass == Sequence.class)
      return getOrCreateSequence(paramString); 
    if (paramClass == UserDataType.class)
      return getOrCreateUserDataType(paramString); 
    if (paramClass == Function.class)
      return getOrCreateFunction(paramString); 
    if (paramClass == Procedure.class)
      return getOrCreateProcedure(paramString); 
    if (paramClass == Trigger.class)
      return getOrCreateTrigger(paramString); 
    return null;
  }
  
  public View getView(String paramString) {
    return (View)this.views.getByName(paramString);
  }
  
  public MaterializedView getMaterializedView(String paramString) {
    return (MaterializedView)this.materializedViews.getByName(paramString);
  }
  
  public UserDataType getUserDataType(String paramString) {
    return (UserDataType)this.userDataTypes.getByName(paramString);
  }
  
  public AbstractTable getTableOrViewOrMatViewOrUDT(String paramString) {
    View view;
    UserDataType userDataType;
    Table table = getTable(paramString);
    if (table == null)
      view = getView(paramString); 
    if (view == null)
      view = getMaterializedView(paramString); 
    if (view == null)
      userDataType = getUserDataType(paramString); 
    return userDataType;
  }
  
  public Index getIndexByName(String paramString) {
    for (Table table : this.tables) {
      for (Index index : table.indexes) {
        if (index.getName().equalsIgnoreCase(paramString))
          return index; 
      } 
    } 
    return null;
  }
  
  public void refresh() {
    if (isMarkedForDeletion()) {
      for (Table table : this.tables)
        table.markForDeletion(); 
      for (View view : this.views)
        view.markForDeletion(); 
      for (MaterializedView materializedView : this.materializedViews)
        materializedView.markForDeletion(); 
      for (Procedure procedure : this.procedures)
        procedure.markForDeletion(); 
      for (Function function : this.functions)
        function.markForDeletion(); 
      for (Trigger trigger : this.triggers)
        trigger.markForDeletion(); 
      for (Rule rule : this.rules)
        rule.markForDeletion(); 
      for (Sequence sequence : this.sequences)
        sequence.markForDeletion(); 
      for (DataType dataType : this.userDataTypes)
        dataType.markForDeletion(); 
    } 
    this.f = -1L;
    for (Table table : this.tables) {
      for (Column column : table.getAttributes())
        column.resetMarkers(); 
      this.f = Math.max(table.getRowCount(), this.f);
    } 
    for (View view : this.views) {
      for (Column column : view.getAttributes())
        column.resetMarkers(); 
    } 
    this.j = null;
    this.userDataTypes.refresh();
    this.tables.refresh();
    this.views.refresh();
    this.materializedViews.refresh();
    this.sequences.refresh();
    this.procedures.refresh();
    this.functions.refresh();
    this.triggers.refresh();
    this.rules.refresh();
  }
  
  public String getSymbolicName() {
    return this.project.is(UnitProperty.f).booleanValue() ? "Database" : "Schema";
  }
  
  public String getSymbolicIcon() {
    return "schema.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.book;
  }
  
  public Folder getParent() {
    return this.project.schemas;
  }
  
  public Table createTable(String paramString) {
    Table table = new Table(this, paramString);
    this.tables.add(table);
    return table;
  }
  
  public UserDataType createUserDataType(String paramString) {
    UserDataType userDataType = new UserDataType(this, paramString);
    this.userDataTypes.add(userDataType);
    return userDataType;
  }
  
  public View createView(String paramString) {
    View view = new View(this, paramString);
    this.views.add(view);
    return view;
  }
  
  public MaterializedView createMaterializedView(String paramString) {
    MaterializedView materializedView = new MaterializedView(this, paramString);
    this.materializedViews.add(materializedView);
    return materializedView;
  }
  
  public Sequence createSequence(String paramString) {
    Sequence sequence = new Sequence(this, paramString);
    this.sequences.add(sequence);
    return sequence;
  }
  
  public Procedure createProcedure(String paramString) {
    Procedure procedure = new Procedure(this, paramString);
    this.procedures.add(procedure);
    return procedure;
  }
  
  public Rule createRule(String paramString) {
    Rule rule = new Rule(this, paramString);
    this.rules.add(rule);
    return rule;
  }
  
  public Rule createRule(String paramString, Table paramTable) {
    Rule rule = new Rule(this, paramString, paramTable);
    this.rules.add(rule);
    return rule;
  }
  
  public Function createFunction(String paramString) {
    Function function = new Function(this, paramString);
    this.functions.add(function);
    return function;
  }
  
  public Trigger createTrigger(String paramString) {
    Trigger trigger = new Trigger(this, paramString);
    this.triggers.add(trigger);
    return trigger;
  }
  
  public Trigger createTrigger(String paramString, Table paramTable) {
    Trigger trigger = new Trigger(this, paramString, paramTable);
    this.triggers.add(trigger);
    return trigger;
  }
  
  public EntityIterable getEntities() {
    return () -> new b(this);
  }
  
  public boolean hasEntities() {
    return (this.tables.size() + this.views.size() > 0);
  }
  
  public int getRelationCount() {
    int i = 0;
    for (Table table : this.tables)
      i += table.getRelations().size(); 
    return i;
  }
  
  public int getForeignKeyCount() {
    int i = 0;
    for (Table table : this.tables)
      i += table.getRelations().size(); 
    return i;
  }
  
  public List generateVirtualForeignKeysWhenReferredIsNotPrimaryKey(boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2) {
    ArrayList<VirtualForeignKeySuggestion> arrayList = new ArrayList();
    for (Table table : this.tables) {
      for (Column column : table.columns) {
        if ((!StringUtil.isFilledTrim(paramString1) || Pattern.compile(paramString1.trim().replaceFirst("~pkTable~", table.getName()), 2).matcher(column.getName()).matches()) && (!paramBoolean2 || (table
          .getPrimaryKeyOrUniqueIndex() != null && (table.getPrimaryKeyOrUniqueIndex()).columns.contains(column))))
          for (Table table1 : this.tables) {
            if (table != table1 && !SyncUtil.a(table, table1))
              for (Column column1 : table1.columns) {
                if (!"_id".equals(column1.getName())) {
                  boolean bool = true;
                  for (ForeignKey foreignKey : table1.foreignKeys) {
                    if (foreignKey.getSourceAttributes().contains(column1)) {
                      bool = false;
                      break;
                    } 
                  } 
                  if (bool && column1.isUsingSameDataType(column)) {
                    bool = false;
                    if (StringUtil.isFilledTrim(paramString2)) {
                      String str1 = paramString2.trim().replaceFirst("~pkColumn~", column.getName()).replaceFirst("~pkTable~", table.getName());
                      if (Pattern.compile(str1, 2).matcher(column1.getName()).matches())
                        bool = true; 
                      String str2 = StringUtil.getOppositePluralSingular(table.getName().toLowerCase());
                      str1 = paramString2.trim().replaceFirst("~pkColumn~", column.getName()).replaceFirst("~pkTable~", str2);
                      if (Pattern.compile(str1, 2).matcher(column1.getName()).matches())
                        bool = true; 
                    } else if (column.getName().equalsIgnoreCase(column1.getName())) {
                      bool = true;
                    } 
                    if (bool) {
                      ForeignKey foreignKey = table1.createRelation(table.schema.nameFinder.a(table1, table, column1));
                      foreignKey.setTargetEntity(table);
                      foreignKey.setVirtual(true);
                      foreignKey.addColumns(column1, column);
                      arrayList.add(new VirtualForeignKeySuggestion(null, foreignKey, paramBoolean1));
                    } 
                  } 
                } 
              }  
          }  
      } 
    } 
    return arrayList;
  }
  
  public String getDbId() {
    return this.project.getDbId();
  }
  
  public Schema getSchema() {
    return this;
  }
  
  public String getSpecificationOptions() {
    return this.b;
  }
  
  public String getOptions() {
    return this.c;
  }
  
  public void setSpecificationOptions(String paramString) {
    this.b = paramString;
  }
  
  public void setOptions(String paramString) {
    this.c = paramString;
  }
  
  public int compareTo(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit != null)
      return toString().compareTo(paramAbstractUnit.toString()); 
    return -1;
  }
  
  public void setCatalogName(String paramString) {
    this.a = paramString;
    TreeUnit.touch(this);
  }
  
  public String toString() {
    return StringUtil.isFilledTrim(this.a) ? (this.a + "." + this.a) : getName();
  }
  
  long a() {
    return this.f;
  }
  
  public boolean setPreScript(String paramString) {
    boolean bool = !StringUtil.areEqual(paramString, this.d) ? true : false;
    this.d = paramString;
    return bool;
  }
  
  public String getPreScript() {
    return this.d;
  }
  
  public boolean setPostScript(String paramString) {
    boolean bool = !StringUtil.areEqual(paramString, this.d) ? true : false;
    this.e = paramString;
    return bool;
  }
  
  public String getPostScript() {
    return this.e;
  }
  
  public String ref() {
    if (is(UnitProperty.f).booleanValue() && StringUtil.isInvalidJavaIdentifier(getName()))
      return "client.getDatabase('" + getName() + "')"; 
    Dbms dbms = Dbms.get(this);
    String str;
    if (this.project.hasOperativeConnector() && 
      (this.project.getOperativeConnector()).mapping.localSchemaHasCustomMapping(this) && (
      str = (this.project.getOperativeConnector()).mapping.getRemoteSchemaName(this)) != null) {
      int i = str.lastIndexOf(".");
      if (i > 0)
        return dbms.quote(str.substring(0, i)) + "." + dbms.quote(str.substring(0, i)); 
      return dbms.quote(str);
    } 
    if (StringUtil.isFilledTrim(getCatalogName()) && (Dbms.get(getDbId())).prefixSchemaWithCatalog.b())
      return dbms.quote(getCatalogName()) + "." + dbms.quote(getCatalogName()); 
    return dbms.quote(getName());
  }
  
  void a(boolean paramBoolean) {
    this.i = paramBoolean;
  }
  
  public boolean isInUse() {
    return this.i;
  }
  
  public void setSyncPriority(int paramInt) {
    this.g = paramInt;
  }
  
  public int getSyncPriority() {
    return this.g;
  }
  
  public int getDefaultSyncPriority() {
    return 700;
  }
  
  @GroovyMethod
  public Table cloneTable(Table paramTable, boolean paramBoolean) {
    Table table = createTable(this.nameFinder.a(paramTable.getName()));
    table.setComment(paramTable.getComment());
    table.setCommentTags(paramTable.getCommentTags());
    table.setOptions(paramTable.getOptions());
    for (Column column1 : paramTable.columns) {
      Column column2 = table.createColumn(column1.getName(), column1.getDataType());
      column2.cloneFrom(column1);
    } 
    for (Index index1 : paramTable.indexes) {
      Index index2 = table.createIndex(table.schema.nameFinder.a(index1.getName()));
      index2.setType(index1.getType());
      index2.setComment(index1.getComment());
      index2.setCommentTags(index1.getCommentTags());
      for (Column column : index1.getColumns())
        index2.addColumn((Column)table.columns.getByName(column.getName())); 
    } 
    for (ForeignKey foreignKey1 : paramTable.foreignKeys) {
      ForeignKey foreignKey2 = table.createRelation(table.schema.nameFinder.a(foreignKey1.getName()));
      foreignKey2.setTargetEntity(foreignKey1.getTargetEntity());
      foreignKey2.setVirtual(foreignKey1.isVirtual());
      foreignKey2.setUpdateAction(foreignKey1.getUpdateAction());
      foreignKey2.setDeleteAction(foreignKey1.getDeleteAction());
      foreignKey2.setRelationType(foreignKey1.getRelationType());
      foreignKey2.setComment(foreignKey1.getComment());
      foreignKey2.setCommentTags(foreignKey1.getCommentTags());
      for (Iterator<Column> iterator1 = foreignKey1.getSourceAttributes().iterator(), iterator2 = foreignKey1.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
        Column column = (Column)table.columns.getByName(((Column)iterator1.next()).getName());
        foreignKey2.addColumns(column, iterator2.next());
      } 
    } 
    if (paramBoolean)
      for (Relation relation : paramTable.importedRelations) {
        if (relation instanceof ForeignKey) {
          ForeignKey foreignKey = (ForeignKey)relation;
          if (relation.getTargetEntity() != table) {
            ArrayList<Column> arrayList1 = new ArrayList(), arrayList2 = new ArrayList();
            Iterator<Column> iterator1, iterator2;
            for (iterator1 = foreignKey.getSourceAttributes().iterator(), iterator2 = foreignKey.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
              arrayList1.add(iterator1.next());
              arrayList2.add((Column)table.columns.getByName(((Column)iterator2.next()).getName()));
            } 
            foreignKey.setTargetEntity(table);
            for (iterator1 = arrayList1.iterator(), iterator2 = arrayList2.iterator(); iterator1.hasNext() && iterator2.hasNext();)
              foreignKey.addColumns(iterator1.next(), iterator2.next()); 
          } 
        } 
      }  
    for (Constraint constraint1 : paramTable.constraints) {
      Constraint constraint2 = table.createConstraint(table.schema.nameFinder.a(constraint1.getName()));
      constraint2.setText(constraint1.getText());
      constraint2.setType(constraint1.getType());
      constraint2.setOptions(constraint1.getOptions());
      constraint2.setComment(constraint1.getComment());
      constraint2.setCommentTags(constraint1.getCommentTags());
    } 
    return table;
  }
}
