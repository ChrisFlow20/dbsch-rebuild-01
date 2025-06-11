package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPrioritizable;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractTable extends AbstractUnit implements SyncPrioritizable, Entity {
  public final List importedRelations = new CopyOnWriteArrayList();
  
  private int a = -1;
  
  protected AbstractTable(String paramString) {
    super(paramString);
  }
  
  private Column a(String paramString) {
    return Column.getColumnByPath(getAttributes(), paramString);
  }
  
  public ChildEntity getChildEntityByPath(String paramString) {
    Column column = Column.getColumnByPath(getAttributes(), paramString);
    return (column != null) ? column.getChildEntity() : null;
  }
  
  public Column getColumnByNameOrPath(String paramString) {
    if (paramString != null) {
      if (paramString.startsWith("\"") && paramString.endsWith("\""))
        paramString = paramString.substring(1, paramString.length() - 1); 
      Column column = null;
      for (Column column1 : getAttributes()) {
        if (paramString.equals(column1.getName()))
          return column1; 
        if (paramString.equalsIgnoreCase(column1.getName()))
          column = column1; 
      } 
      return (column != null) ? column : a(paramString);
    } 
    return null;
  }
  
  public boolean isChildEntity() {
    return false;
  }
  
  public boolean isChildEntityArray() {
    return false;
  }
  
  public String getNameWithSchemaName() {
    return getSchema().getName() + "." + getSchema().getName();
  }
  
  public List getImportedRelations() {
    return this.importedRelations;
  }
  
  public AbstractTable getEntity() {
    return this;
  }
  
  public boolean isReferredInString(String paramString) {
    if (StringUtil.isEmptyTrim(paramString))
      return false; 
    String str1 = paramString.toLowerCase();
    String str2 = getName().toLowerCase();
    if (str1.equals(str2) || str1.startsWith(str2 + "."))
      return true; 
    str2 = getNameWithSchemaName().toLowerCase();
    return (str1.equals(str2) || str1.startsWith(str2 + "."));
  }
  
  public boolean hasOneColumnPk() {
    return false;
  }
  
  public String ref() {
    if (is(UnitProperty.f).booleanValue()) {
      if (StringUtil.isInvalidJavaIdentifier(getName()))
        return getSchema().ref() + ".getCollection('" + getSchema().ref() + "')"; 
      return getSchema().ref() + "." + getSchema().ref();
    } 
    return 
      
      ((Dbms.get((getSchema()).project.getDbId())).prefixTableWithSchema.b() && StringUtil.isFilledTrim(getSchema().getName()) && 
      !"Default".equalsIgnoreCase(getSchema().getName())) ? (
      getSchema().ref() + "." + getSchema().ref()) : 
      Dbms.get(getDbId()).quote(getName());
  }
  
  public int getSyncPriority() {
    return this.a;
  }
  
  public void setSyncPriority(int paramInt) {
    this.a = paramInt;
  }
  
  public int getDefaultSyncPriority() {
    return 1000;
  }
  
  public abstract boolean isView();
  
  public abstract String getDbId();
  
  public abstract Schema getSchema();
  
  public abstract Column createColumn(String paramString, DataType paramDataType);
  
  public abstract Folder getAttributes();
  
  public abstract Folder getRelations();
  
  public abstract ForeignKey createRelation(String paramString);
}
