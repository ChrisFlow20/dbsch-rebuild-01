package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.List;

@DoNotObfuscate
public final class Table extends AbstractTable implements DbUnit {
  public final Schema schema;
  
  public final Folder columns;
  
  public final Folder foreignKeys;
  
  public final Folder indexes = new Folder("Primary Keys & Indexes", "Primary Key or Index", this, Index.class, false);
  
  public final Folder constraints = new Folder("Constraints", "Constraint", this, Constraint.class, false);
  
  public final Folder triggers = new Folder("Triggers", "Trigger", this, Trigger.class, false);
  
  private final Folder[] a;
  
  private final Folder[] b;
  
  private Table$TableType c = Table$TableType.a;
  
  private String d;
  
  private String e;
  
  private String f;
  
  private String g;
  
  private long h = -1L;
  
  private int i = -1, j = -1;
  
  public Table(Schema paramSchema, String paramString) {
    super(paramString);
    this.schema = paramSchema;
    this.columns = new Folder((Dbms.get(paramSchema.project.getDbId())).columnAlias.c_(), this, Column.class, false);
    this.foreignKeys = new Folder((Dbms.get(paramSchema.project.getDbId())).fkAlias.c_(), this, ForeignKey.class, false);
    if (paramSchema.is(UnitProperty.f).booleanValue())
      this.indexes.rename("Indexes", "Index"); 
    (new Folder[3])[0] = this.columns;
    (new Folder[3])[1] = this.indexes;
    (new Folder[3])[2] = this.foreignKeys;
    (new Folder[2])[0] = this.columns;
    (new Folder[2])[1] = this.foreignKeys;
    (new Folder[5])[0] = this.columns;
    (new Folder[5])[1] = this.indexes;
    (new Folder[5])[2] = this.foreignKeys;
    (new Folder[5])[3] = this.constraints;
    (new Folder[5])[4] = this.triggers;
    this.a = paramSchema.is(UnitProperty.f).booleanValue() ? new Folder[3] : (paramSchema.project.isElasticsearch() ? new Folder[2] : new Folder[5]);
    (new Folder[3])[0] = this.columns;
    (new Folder[3])[1] = this.indexes;
    (new Folder[3])[2] = this.foreignKeys;
    (new Folder[2])[0] = this.columns;
    (new Folder[2])[1] = this.foreignKeys;
    (new Folder[4])[0] = this.columns;
    (new Folder[4])[1] = this.indexes;
    (new Folder[4])[2] = this.foreignKeys;
    (new Folder[4])[3] = this.constraints;
    this.b = paramSchema.is(UnitProperty.f).booleanValue() ? new Folder[3] : (paramSchema.project.isElasticsearch() ? new Folder[2] : new Folder[4]);
  }
  
  public String getDisplayName(Diagram paramDiagram) {
    String str = super.getDisplayName(paramDiagram);
    return (str.length() > Sys.B.displayNamesMaxLength.a()) ? (str.substring(0, Sys.B.displayNamesMaxLength.a()) + "...") : str;
  }
  
  public Folder getAttributes() {
    return this.columns;
  }
  
  public Folder[] getSyncFolders() {
    return this.b;
  }
  
  public String getSymbolicName() {
    return this.schema.is(UnitProperty.f).booleanValue() ? "Collection" : (this.schema.is(UnitProperty.g).booleanValue() ? "Entity" : "Table");
  }
  
  public String getSymbolicIcon() {
    if (this.h > -1L && this.schema.a() > 0L) {
      double d = this.h * 100.0D / this.schema.a();
      if (d < 0.5D)
        return "table_small_0.png"; 
      if (d < 10.0D)
        return "table_small_1.png"; 
      if (d < 21.0D)
        return "table_small_2.png"; 
      if (d < 32.0D)
        return "table_small_3.png"; 
      if (d < 43.0D)
        return "table_small_4.png"; 
      if (d < 54.0D)
        return "table_small_5.png"; 
      if (d < 65.0D)
        return "table_small_6.png"; 
      if (d < 76.0D)
        return "table_small_7.png"; 
      if (d < 87.0D)
        return "table_small_8.png"; 
      if (d < 98.0D)
        return "table_small_9.png"; 
      return "table_small_10.png";
    } 
    return "table_small.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.table;
  }
  
  public Folder getRelations() {
    return this.foreignKeys;
  }
  
  public Folder getIndexes() {
    return this.indexes;
  }
  
  public int getChildrenCount() {
    return this.a.length;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.a[paramInt];
  }
  
  public Index getPrimaryKey() {
    return getIndexByType(IndexType.PRIMARY_KEY);
  }
  
  public Index getIndexByType(IndexType paramIndexType) {
    for (Index index : getIndexes()) {
      if (index.getType() == paramIndexType)
        return index; 
    } 
    return null;
  }
  
  public void presetColumnOrder() {
    Index index = getPrimaryKey();
    if (index != null) {
      byte b = 0;
      for (Column column : index.getColumns()) {
        this.columns.remove(column);
        this.columns.add(b, column);
        b++;
      } 
    } 
  }
  
  public Index getPrimaryKeyOrUniqueIndex() {
    Index index = null;
    for (Index index1 : this.indexes) {
      if (index1.getType() == IndexType.PRIMARY_KEY)
        return index1; 
      if (index1.getType() == IndexType.UNIQUE_KEY) {
        index = index1;
        continue;
      } 
      if (index1.getType() == IndexType.UNIQUE_INDEX)
        index = index1; 
    } 
    return index;
  }
  
  public Index getIndexFittingColumns(List paramList) {
    for (Index index : this.indexes) {
      if (Index.attributesAreEqual(index.getColumns(), paramList))
        return index; 
    } 
    return null;
  }
  
  public Table$TableType getType() {
    return this.c;
  }
  
  public void setType(Table$TableType paramTable$TableType) {
    if (paramTable$TableType != null)
      this.c = paramTable$TableType; 
  }
  
  public void refresh() {
    if (isMarkedForDeletion()) {
      for (Column column : this.columns)
        column.markForDeletion(); 
      for (Constraint constraint : this.constraints)
        constraint.markForDeletion(); 
      for (Index index : this.indexes)
        index.markForDeletion(); 
      for (ForeignKey foreignKey : this.foreignKeys)
        foreignKey.markForDeletion(); 
      for (Trigger trigger : this.triggers)
        trigger.markForDeletion(); 
    } 
    this.columns.refresh();
    this.constraints.refresh();
    this.indexes.refresh();
    this.foreignKeys.refresh();
    this.triggers.refresh();
    if (!is(UnitProperty.g).booleanValue())
      for (ForeignKey foreignKey : this.foreignKeys) {
        boolean bool1 = false;
        boolean bool2 = true;
        for (Column column : foreignKey.getSourceAttributes()) {
          if (column != null && !column.isMandatory()) {
            bool2 = false;
            break;
          } 
        } 
        foreignKey.setRelationCardinality(RelationCardinality.a);
        for (Index index : getIndexes()) {
          if ((index.getType()).isUnique && Index.attributesAreEqual(index.getColumns(), foreignKey.getSourceAttributes())) {
            bool1 = true;
            foreignKey.setRelationCardinality(RelationCardinality.b);
          } 
        } 
        if (this.schema.project.isNotationStrict()) {
          foreignKey.setRelationCardinality(bool1 ? RelationCardinality.b : RelationCardinality.a);
          continue;
        } 
        foreignKey.setRelationCardinality(bool1 ? (bool2 ? RelationCardinality.d : RelationCardinality.b) : (
            bool2 ? RelationCardinality.c : RelationCardinality.a));
      }  
    this.importedRelations.removeIf(paramRelation -> (paramRelation.isMarkedForDeletion() || paramRelation.getTargetEntity() != this || paramRelation.getEntity().isMarkedForDeletion()));
  }
  
  public TreeUnit getParent() {
    return this.schema.tables;
  }
  
  public Schema getSchema() {
    return this.schema;
  }
  
  @GroovyMethod
  public Column createColumn(String paramString1, String paramString2) {
    return createColumn(paramString1, DbmsTypes.get(this.schema.project.getDbId()).getType(paramString2));
  }
  
  public Column createColumn(String paramString, DataType paramDataType, int paramInt) {
    Column column = createColumn(paramString, paramDataType);
    column.setLength(paramInt);
    return column;
  }
  
  public Column createColumn(String paramString, DataType paramDataType) {
    return createColumn(paramString, paramDataType, AttributeSpec.normal);
  }
  
  public Column createColumn(String paramString, DataType paramDataType, AttributeSpec paramAttributeSpec) {
    if (paramString.contains(".") || paramDataType.isJsonMapOrArray()) {
      Column column1 = Column.getColumnByPath(this.columns, paramString);
      if (column1 != null) {
        String str = column1.getNameWithPath();
        if (str.equals(paramString))
          return column1; 
        if (str.length() + 1 < paramString.length()) {
          String str1 = paramString.substring(column1.getNameWithPath().length() + 1);
          return column1.getCreateChildEntity().createColumn(str1, paramDataType);
        } 
      } 
    } 
    Column column = new Column(this, paramString, paramDataType);
    column.setSpec(paramAttributeSpec);
    this.columns.add(column);
    return column;
  }
  
  @GroovyMethod
  public ForeignKey createRelation(String paramString) {
    ForeignKey foreignKey = new ForeignKey(this, paramString);
    this.foreignKeys.add(foreignKey);
    return foreignKey;
  }
  
  public Index createPrimaryKey(String paramString) {
    Index index = createIndex(paramString);
    index.setType(IndexType.PRIMARY_KEY);
    return index;
  }
  
  public Index createIndex(String paramString) {
    Index index = new Index(this, paramString);
    this.indexes.add(index);
    return index;
  }
  
  public Constraint createConstraint(String paramString) {
    Constraint constraint = new Constraint(this, paramString);
    this.constraints.add(constraint);
    return constraint;
  }
  
  public boolean isView() {
    return false;
  }
  
  public String getOptions() {
    return this.d;
  }
  
  public boolean setOptions(String paramString) {
    boolean bool = !StringUtil.areEqual(paramString, this.d) ? true : false;
    this.d = paramString;
    return bool;
  }
  
  public String getSpecificationOptions() {
    return this.e;
  }
  
  public boolean setSpecificationOptions(String paramString) {
    boolean bool = !StringUtil.areEqual(this.e, this.e) ? true : false;
    this.e = paramString;
    return bool;
  }
  
  public void merge(Table paramTable) {
    setComment(paramTable.getComment());
    setCommentTags(paramTable.getCommentTags());
    setType(paramTable.getType());
    setOptions(paramTable.getOptions());
    for (Column column1 : paramTable.getAttributes()) {
      Column column2 = createColumn(column1.getName(), column1.getDataType());
      column2.cloneFrom(column1);
    } 
    for (Index index1 : paramTable.getIndexes()) {
      Index index2 = createIndex(index1.getName());
      index2.setType(index1.getType());
      for (Column column1 : index1.getColumns()) {
        Column column2 = getColumnByNameOrPath(column1.getNameWithPath());
        if (column2 != null)
          index2.addColumn(column2); 
      } 
    } 
    for (Constraint constraint1 : paramTable.constraints) {
      Constraint constraint2 = createConstraint(constraint1.getName());
      constraint2.setText(constraint1.getText());
      constraint2.setType(constraint1.getType());
    } 
  }
  
  public int getGeneratorRowsCount() {
    return this.i;
  }
  
  public void setGeneratorRowsCount(int paramInt) {
    this.i = paramInt;
  }
  
  public int getGeneratorOrder() {
    return this.j;
  }
  
  public void setGeneratorOrder(int paramInt) {
    this.j = paramInt;
  }
  
  public boolean hasFkOrRef() {
    return (this.foreignKeys.size() + this.importedRelations.size() > 0);
  }
  
  public void setRowCount(long paramLong) {
    this.h = paramLong;
  }
  
  public long getRowCount() {
    return this.h;
  }
  
  public boolean hasOneColumnPk() {
    return (getPrimaryKey() != null && (getPrimaryKey()).columns.size() == 1);
  }
  
  public String getDbId() {
    return this.schema.getDbId();
  }
  
  public boolean setPreScript(String paramString) {
    boolean bool = !StringUtil.areEqual(paramString, this.f) ? true : false;
    this.f = paramString;
    return bool;
  }
  
  public String getPreScript() {
    return this.f;
  }
  
  public boolean setPostScript(String paramString) {
    boolean bool = !StringUtil.areEqual(paramString, this.f) ? true : false;
    this.g = paramString;
    return bool;
  }
  
  public String getPostScript() {
    return this.g;
  }
  
  public Index getPkOrUniqueIndexContaining(List<?> paramList, boolean paramBoolean) {
    for (Index index : this.indexes) {
      if (index.isUnique() && index.columns.containsAll(paramList) && (!paramBoolean || index.columns.size() == paramList.size()))
        return index; 
    } 
    return null;
  }
  
  public boolean columnIsPk(Column paramColumn) {
    Index index = getPrimaryKey();
    return (index != null && index.getColumns().contains(paramColumn));
  }
  
  @GroovyMethod
  public GeneratorTable getTableGenerator() {
    return new GeneratorTable(this, true);
  }
  
  public void removeDuplicateIndexes() {
    for (Index index : this.indexes) {
      for (Index index1 : this.indexes) {
        if (index1 != index && index1.sameAs(index) && (index.getType()).category == (index1.getType()).category && !index.isMarkedForDeletion() && !index1.isMarkedForDeletion()) {
          Index index2 = (index.getType() == IndexType.PRIMARY_KEY) ? index1 : index;
          Log.d("Import remove duplicate index '" + String.valueOf(index) + "' and keep '" + String.valueOf(index1) + "'");
          index2.markForDeletion();
        } 
      } 
    } 
  }
}
