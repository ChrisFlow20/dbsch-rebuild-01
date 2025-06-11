package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@DoNotObfuscate
public class ForeignKey extends AbstractUnit implements Relation, DbUnit {
  private ForeignKeyAction e = ForeignKeyAction.noAction;
  
  private ForeignKeyAction f = ForeignKeyAction.noAction;
  
  private AbstractTable h = null;
  
  private final List i = new CopyOnWriteArrayList();
  
  private final List j = new CopyOnWriteArrayList();
  
  private int k = 0;
  
  private int l = 0;
  
  private boolean m = false;
  
  public static final String FOREIGN_KEY_ICON = "marker_fk.png";
  
  private final AbstractTable g;
  
  private String n;
  
  private static final int o = 268435456;
  
  private static final int p = 65280;
  
  private static final int q = 255;
  
  public ForeignKey(AbstractTable paramAbstractTable, String paramString) {
    super(paramString);
    this.g = paramAbstractTable;
  }
  
  public String getSymbolicName() {
    return (this.g.is(UnitProperty.f).booleanValue() || this.g.is(UnitProperty.g).booleanValue()) ? "Relationship" : "Foreign Key";
  }
  
  public String getSymbolicIcon() {
    return "marker_fk.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.arrow_up_right;
  }
  
  public Column getColumnAt(int paramInt, boolean paramBoolean) {
    return paramBoolean ? this.j.get(paramInt) : this.i.get(paramInt);
  }
  
  public void clearAllAttributes() {
    this.j.clear();
    this.i.clear();
  }
  
  public ForeignKeyAction getDeleteAction() {
    return this.e;
  }
  
  public TreeUnit getParent() {
    return this.g.getRelations();
  }
  
  public void setDeleteAction(ForeignKeyAction paramForeignKeyAction) {
    if (paramForeignKeyAction != null)
      this.e = paramForeignKeyAction; 
  }
  
  public ForeignKeyAction getUpdateAction() {
    return this.f;
  }
  
  public void setUpdateAction(ForeignKeyAction paramForeignKeyAction) {
    if (paramForeignKeyAction != null)
      this.f = paramForeignKeyAction; 
  }
  
  public void setFlag(int paramInt) {
    this.k |= paramInt;
  }
  
  public void resetFlag(int paramInt) {
    this.k &= paramInt ^ 0xFFFFFFFF;
  }
  
  public boolean hasFlag(int paramInt) {
    return ((this.k & paramInt) > 0);
  }
  
  public void refresh() {
    if (this.h == null || this.h.isMarkedForDeletion() || this.g.isMarkedForDeletion()) {
      markForDeletion();
      return;
    } 
    boolean bool = getEntity().is(UnitProperty.g).booleanValue();
    if (bool) {
      resetFlag(7);
      if (getRelationType() != null)
        switch (ForeignKey$1.a[getRelationType().ordinal()]) {
          case 1:
            setFlag(3);
            break;
          case 2:
            if (isLogicalMandatory())
              setFlag(1); 
            break;
          case 3:
            setFlag(4);
            break;
        }  
    } else {
      setFlag(1);
      setFlag(2);
      boolean bool1 = (this.g.getSchema() != null && (this.g.getSchema()).project.isSyncProject()) ? true : false;
      if (!isVirtual() && !bool1) {
        setRelationType(RelationType.a);
        if (this.j.isEmpty() || this.i.isEmpty())
          markForDeletion(); 
      } 
    } 
    for (Column column : this.j) {
      if (column != null) {
        if (column.isMarkedForDeletion()) {
          int i = this.j.indexOf(column);
          this.j.remove(i);
          this.i.remove(i);
          continue;
        } 
        column.setMarker(32);
        if (!bool && !column.isMandatory())
          resetFlag(1); 
      } 
    } 
    for (Column column : this.i) {
      if (column != null) {
        if (column.isMarkedForDeletion()) {
          int i = this.i.indexOf(column);
          this.j.remove(i);
          this.i.remove(i);
          continue;
        } 
        column.setMarker(64);
        if (!bool && !column.isMandatory())
          resetFlag(2); 
      } 
    } 
  }
  
  public void setTargetEntity(AbstractTable paramAbstractTable) {
    setTargetEntity(paramAbstractTable, true);
  }
  
  public void setTargetEntity(AbstractTable paramAbstractTable, boolean paramBoolean) {
    this.h = paramAbstractTable;
    if (paramAbstractTable != null && paramBoolean)
      paramAbstractTable.importedRelations.add(this); 
    this.j.clear();
    this.i.clear();
  }
  
  public AbstractTable getEntity() {
    return this.g;
  }
  
  public AbstractTable getTargetEntity() {
    return this.h;
  }
  
  @GroovyMethod
  public void addColumns(Column paramColumn1, Column paramColumn2) {
    if (!a && (paramColumn1 == null || paramColumn2 == null))
      throw new AssertionError(); 
    if (this.h == null)
      this.h = paramColumn2.getEntity(); 
    this.j.add(paramColumn1);
    this.i.add(paramColumn2);
  }
  
  public List getTargetAttributes() {
    return this.i;
  }
  
  public List getSourceAttributes() {
    return this.j;
  }
  
  public boolean sameAs(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    if (paramAbstractUnit instanceof ForeignKey) {
      ForeignKey foreignKey = (ForeignKey)paramAbstractUnit;
      if (this.h == null || foreignKey.h == null)
        return (this.h == null && foreignKey.h == null && (
          getName().equalsIgnoreCase(foreignKey.getName()) || compareLists(this.j, foreignKey.j))); 
      if (this.h.sameAs(foreignKey.h, paramBoolean)) {
        if (compareLists(this.j, foreignKey.j) && 
          compareLists(this.i, foreignKey.i))
          return true; 
        return getName().equalsIgnoreCase(foreignKey.getName());
      } 
    } 
    return false;
  }
  
  public boolean usesSameColumns(ForeignKey paramForeignKey) {
    if (this.h == null || paramForeignKey.h == null)
      return (this.h == null && paramForeignKey.h == null && 
        compareLists(this.j, paramForeignKey.j)); 
    if (this.h.sameAs(paramForeignKey.h, false))
      return (compareLists(this.j, paramForeignKey.j) && 
        compareLists(this.i, paramForeignKey.i)); 
    return false;
  }
  
  public static boolean compareLists(List paramList1, List paramList2) {
    if (paramList2 == null || paramList1 == null || paramList2.size() != paramList1.size())
      return false; 
    for (byte b = 0; b < paramList1.size(); b++) {
      if (paramList2.get(b) != null && !String.valueOf(paramList1.get(b)).equalsIgnoreCase(String.valueOf(paramList2.get(b))))
        return false; 
    } 
    return true;
  }
  
  public int getColumnCount() {
    return Math.min(getSourceAttributes().size(), getTargetAttributes().size());
  }
  
  public static String listAttributes(List paramList1, String paramString, List paramList2) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Iterator<Attribute> iterator1 = paramList1.iterator(), iterator2 = paramList2.iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append(", "); 
      Attribute attribute1 = iterator1.next(), attribute2 = iterator2.next();
      if (attribute1 != null && attribute2 != null && attribute1.getName().equals(attribute2.getName())) {
        stringBuilder.append(attribute1);
        continue;
      } 
      stringBuilder.append((attribute1 != null) ? attribute1 : "-").append(paramString).append(attribute2);
    } 
    return stringBuilder.toString();
  }
  
  public Column getLastSourceAttribute() {
    if (getSourceAttributes().isEmpty())
      return null; 
    return getSourceAttributes().get(getSourceAttributes().size() - 1);
  }
  
  public Column getLastTargetAttribute() {
    if (getTargetAttributes().isEmpty())
      return null; 
    return getTargetAttributes().get(getTargetAttributes().size() - 1);
  }
  
  public boolean isAutoReference() {
    return (getEntity() == getTargetEntity());
  }
  
  public String getColumnsName() {
    StringBuilder stringBuilder = new StringBuilder();
    for (Column column : getSourceAttributes())
      stringBuilder.append(!stringBuilder.isEmpty() ? "," : "").append((column != null) ? column.getName() : "-"); 
    return stringBuilder.toString();
  }
  
  public String getCascadeText() {
    StringBuilder stringBuilder = new StringBuilder();
    switch (ForeignKey$1.b[this.e.ordinal()]) {
      case 1:
        stringBuilder.append("Del(C)");
        break;
      case 2:
        stringBuilder.append("Del(N)");
        break;
      case 3:
        stringBuilder.append("Del(D)");
        break;
      case 4:
        stringBuilder.append("Del(R)");
        break;
    } 
    switch (ForeignKey$1.b[this.f.ordinal()]) {
      case 1:
        stringBuilder.append("Upd(C)");
        break;
      case 2:
        stringBuilder.append("Upd(N)");
        break;
      case 3:
        stringBuilder.append("Upd(D)");
        break;
      case 4:
        stringBuilder.append("Upd(R)");
        break;
    } 
    return !stringBuilder.isEmpty() ? stringBuilder.toString() : null;
  }
  
  public Entity getParentEntity() {
    return (this.j.isEmpty() || this.j.get(0) == null) ? getEntity() : ((Column)this.j.get(0)).getParentEntity();
  }
  
  public String getDbId() {
    return getEntity().getDbId();
  }
  
  public Schema getSchema() {
    return this.g.getSchema();
  }
  
  public void setOptions(String paramString) {
    this.n = paramString;
  }
  
  public String getOptions() {
    return this.n;
  }
  
  public int getLogical() {
    return this.l;
  }
  
  public void setLogical(int paramInt) {
    this.l = paramInt;
  }
  
  public void setLogicalMandatory(boolean paramBoolean) {
    if (paramBoolean) {
      this.l |= 0x10000000;
    } else {
      this.l &= 0xEFFFFFFF;
    } 
  }
  
  public boolean isLogicalMandatory() {
    return ((this.l & 0x10000000) > 0);
  }
  
  public void setRelationType(RelationType paramRelationType) {
    if (paramRelationType != null) {
      this.l &= 0xFF0FFFFF;
      this.l |= paramRelationType.d;
    } 
  }
  
  public RelationType getRelationType() {
    return RelationType.a(this.l);
  }
  
  public void setRelationCardinality(RelationCardinality paramRelationCardinality) {
    if (paramRelationCardinality != null) {
      this.l &= 0xF0FFFFFF;
      this.l |= paramRelationCardinality.h;
    } 
  }
  
  public RelationCardinality getRelationCardinality() {
    return RelationCardinality.a(this.l);
  }
  
  public void setLogicalRangeFrom(int paramInt) {
    this.l &= 0xFFFF00FF;
    if (paramInt > -1)
      this.l |= Math.min(paramInt, 255) << 8; 
  }
  
  public int getLogicalRangeFrom() {
    return (this.l & 0xFF00) >> 8;
  }
  
  public void setLogicalRangeTo(int paramInt) {
    this.l &= 0xFFFFFF00;
    if (paramInt > -1)
      this.l |= Math.min(paramInt, 255); 
  }
  
  public int getLogicalRangeTo() {
    return this.l & 0xFF;
  }
  
  public Column getTargetColumnFor(Column paramColumn) {
    if (this.j.contains(paramColumn)) {
      int i = this.j.indexOf(paramColumn);
      if (this.i.size() > i)
        return this.i.get(i); 
    } 
    return null;
  }
  
  public void resolveLogical() {
    AbstractTable abstractTable = this.g;
    if (abstractTable instanceof Table) {
      Table table = (Table)abstractTable;
      abstractTable = this.h;
      if (abstractTable instanceof Table) {
        Table table1 = (Table)abstractTable;
        abstractTable = DbmsTypes.get(table.getDbId()).getDataType(4);
        if (getRelationType() == RelationType.b || getRelationType() == RelationType.a) {
          Index index1 = table1.getPrimaryKey();
          if (this.j.isEmpty())
            if (index1 == null) {
              String str = table.columns.proposeName("id");
              str = table1.columns.proposeName(str);
              Column column1 = table1.createColumn(str, (DataType)abstractTable);
              column1.setMandatory(true);
              Column column2 = table.createColumn(str, (DataType)abstractTable);
              addColumns(column2, column1);
            } else {
              for (Column column1 : index1.columns) {
                String str = table.columns.proposeName(column1.getName());
                Column column2 = table.createColumn(str, (DataType)abstractTable);
                addColumns(column2, column1);
              } 
            }  
          if (index1 == null) {
            index1 = table1.createIndex(table1.indexes.proposeName("pk_" + String.valueOf(getLastTargetAttribute())));
            index1.setType(IndexType.PRIMARY_KEY);
            index1.columns.addAll(this.i);
          } else if (table1.getPkOrUniqueIndexContaining(this.i, true) == null) {
            index1 = table1.createIndex(table1.indexes.proposeName("unq_" + String.valueOf(getLastTargetAttribute())));
            index1.setType(IndexType.UNIQUE_INDEX);
            index1.columns.addAll(this.i);
          } 
          Index index2 = table.getPrimaryKey();
          if (getRelationType() == RelationType.b) {
            if (table.getPkOrUniqueIndexContaining(this.j, false) == null)
              if (index2 == null) {
                Index index = table.createPrimaryKey(table.indexes.proposeName("pk"));
                index.setType(IndexType.PRIMARY_KEY);
                index.columns.addAll(this.j);
              } else {
                for (Column column : this.j) {
                  if (!index2.columns.contains(column))
                    index2.columns.add(column); 
                } 
              }  
          } else if (getRelationType() == RelationType.a) {
          
          } 
          if (isLogicalMandatory())
            for (Column column : this.j)
              column.setMandatory(true);  
        } 
      } 
    } 
  }
  
  public void resolveManyToMany() {
    if (getRelationType() == RelationType.c) {
      Column column1, column2;
      Table table1 = (Table)this.g;
      Table table2 = (Table)this.h;
      DataType dataType = DbmsTypes.get(table1.getDbId()).getDataType(4);
      Index index1 = table2.getPrimaryKey();
      if (index1 == null) {
        String str = table2.columns.proposeName("id");
        column1 = table2.createColumn(str, dataType);
        column1.setMandatory(true);
        index1 = table2.createIndex(table2.indexes.proposeName("pk"));
        index1.setType(IndexType.PRIMARY_KEY);
        index1.getColumns().add(column1);
      } else {
        column1 = (Column)index1.columns.get(0);
      } 
      Index index2 = table1.getPrimaryKey();
      if (index2 == null) {
        String str = table1.columns.proposeName("id");
        column2 = table1.createColumn(str, dataType);
        column2.setMandatory(true);
        index2 = table1.createIndex(table1.indexes.proposeName("pk"));
        index2.setType(IndexType.PRIMARY_KEY);
        index2.getColumns().add(column2);
      } else {
        column2 = (Column)index2.columns.get(0);
      } 
      Table table3 = table1.schema.createTable(table1.getName() + "_" + table1.getName());
      Column column3 = table3.createColumn(table3.columns.proposeName(column1.getName()), column1.getDataType());
      column3.setLength(column1.getLength());
      column3.setDecimal(column1.getDecimal());
      Column column4 = table3.createColumn(table3.columns.proposeName(column2.getName()), column2.getDataType());
      column4.setLength(column2.getLength());
      column4.setDecimal(column2.getDecimal());
      Index index3 = table3.createIndex("pk");
      index3.setType(IndexType.PRIMARY_KEY);
      index3.columns.add(column3);
      index3.columns.add(column4);
      ForeignKey foreignKey1 = table3.createRelation("fkt_" + table3.getName());
      foreignKey1.setTargetEntity(table2);
      foreignKey1.addColumns(column3, column1);
      ForeignKey foreignKey2 = table3.createRelation("fks_" + table3.getName());
      foreignKey2.setTargetEntity(table1);
      foreignKey2.addColumns(column4, column2);
      markForDeletion();
    } 
  }
  
  public void setDeduced(boolean paramBoolean) {
    this.m = paramBoolean;
  }
  
  public boolean isDeduced() {
    return this.m;
  }
}
