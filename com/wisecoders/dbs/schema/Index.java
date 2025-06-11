package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@DoNotObfuscate
public final class Index extends AbstractUnit implements DbUnit {
  public static final String INCLUDE_COLUMN_OPTION = "include";
  
  public final Folder columns = new Folder("Columns", "Column", this, Column.class, false);
  
  private HashMap a;
  
  private final Table b;
  
  private IndexType c = IndexType.NORMAL;
  
  private String d;
  
  private String e;
  
  public Index(Table paramTable, String paramString) {
    this(paramTable, paramString, false);
  }
  
  public Index(Table paramTable, String paramString, boolean paramBoolean) {
    super(paramString);
    this.b = paramTable;
    setVirtual(paramBoolean);
  }
  
  public String getSymbolicName() {
    if (this.c == IndexType.UNIQUE_KEY || this.c == IndexType.UNIQUE_INDEX)
      return "Unique Index"; 
    if (this.c == IndexType.PRIMARY_KEY)
      return "Primary Key"; 
    return "Index";
  }
  
  public IndexType getType() {
    return this.c;
  }
  
  public void setType(IndexType paramIndexType) {
    this.c = paramIndexType;
  }
  
  public void setOptions(String paramString) {
    this.d = c(paramString);
  }
  
  public String getOptions() {
    return this.d;
  }
  
  public void setSpecificationOptions(String paramString) {
    this.e = c(paramString);
  }
  
  public String getSpecificationOptions() {
    return this.e;
  }
  
  public String getSymbolicIcon() {
    switch (Index$1.a[this.c.ordinal()]) {
      case 1:
      
      case 2:
      case 3:
      case 4:
      
      case 5:
      
      case 6:
      case 7:
      
    } 
    return 



      
      "index.png";
  }
  
  public Glyph getSymbolicGlyph() {
    switch (Index$1.a[this.c.ordinal()]) {
      case 1:
      
      case 2:
      case 3:
      case 4:
      
      case 5:
      
      case 6:
      case 7:
      
    } 
    return 



      
      BootstrapIcons.search;
  }
  
  public boolean isUnique() {
    return this.c.isUnique;
  }
  
  public void refresh() {
    boolean bool1 = true;
    boolean bool2 = true;
    this.columns.refresh();
    for (Column column : getColumns()) {
      if (!column.isMandatory())
        bool1 = false; 
      switch (Index$1.a[this.c.ordinal()]) {
        case 5:
          column.setMarker(1);
          break;
        case 6:
        case 7:
          column.setMarker(8);
          break;
        case 8:
          column.setMarker(4);
          break;
        case 1:
          column.setMarker(65536);
          break;
        case 2:
          column.setMarker(131072);
          break;
        case 3:
          column.setMarker(262144);
          break;
        case 4:
          column.setMarker(524288);
          break;
      } 
      if (bool2)
        column.setMarker(16); 
      bool2 = false;
    } 
    if (getColumns().isEmpty())
      markForDeletion(); 
    if (!bool1 && this.c == IndexType.PRIMARY_KEY && !(Dbms.get(getDbId())).pkCanUseNonMandatoryColumns.b())
      this.c = IndexType.UNIQUE_KEY; 
    if (this.a != null) {
      for (Column column : new ArrayList(this.a.keySet())) {
        if (column.isMarkedForDeletion() || !this.columns.contains(column)) {
          this.a.remove(column);
          continue;
        } 
        if ("ASC".equals(this.a.get(column))) {
          column.setMarker(1048576);
          continue;
        } 
        if ("DESC".equals(this.a.get(column)))
          column.setMarker(2097152); 
      } 
      if (isMarkedForDeletion() || this.a.isEmpty()) {
        this.a.clear();
        this.a = null;
      } 
    } 
  }
  
  public boolean areColumnsMandatory() {
    for (Column column : this.columns) {
      if (!column.isMandatory())
        return false; 
    } 
    return true;
  }
  
  public TreeUnit getParent() {
    return this.b.getIndexes();
  }
  
  public int getChildrenCount() {
    return 1;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.columns;
  }
  
  public void clearAttributes() {
    this.columns.clear();
  }
  
  public void addColumn(Column paramColumn) {
    if (paramColumn == null)
      return; 
    this.columns.add(paramColumn);
  }
  
  public List getColumns() {
    return this.columns;
  }
  
  public boolean sameAs(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    if (paramAbstractUnit instanceof Index) {
      Index index = (Index)paramAbstractUnit;
      if (this.columns.size() != index.columns.size())
        return false; 
      if (this.c.category != index.c.category)
        return false; 
      if (!StringUtil.areEqual(this.d, index.d) && ((this.d != null && this.d.toLowerCase().contains("include ")) || (index.d != null && index.d.toLowerCase().contains("include "))))
        return false; 
      if (!StringUtil.areEqualRemoveChars(this.d, index.d, " ()") && ((this.d != null && this.d.toLowerCase().contains("where ")) || (index.d != null && index.d.toLowerCase().contains("where "))))
        return false; 
      for (Iterator<E> iterator1 = this.columns.iterator(), iterator2 = index.columns.iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
        Column column1 = (Column)iterator1.next();
        Column column2 = (Column)iterator2.next();
        if (!column1.sameAs(column2, paramBoolean))
          return false; 
      } 
      return true;
    } 
    return false;
  }
  
  public void merge(Index paramIndex) {
    setType(paramIndex.getType());
    setOptions(paramIndex.getOptions());
    setSpecificationOptions(paramIndex.getSpecificationOptions());
    this.columns.clear();
    for (Column column1 : paramIndex.getColumns()) {
      Column column2 = (Column)this.columns.getByName(column1.getName());
      if (column2 != null)
        addColumn(column2); 
      setColumnOptions(column2, paramIndex.getColumnOptions(column1));
    } 
  }
  
  public Table getEntity() {
    return this.b;
  }
  
  public static boolean attributesAreEqual(Collection paramCollection1, Collection paramCollection2) {
    if (!paramCollection1.isEmpty() && paramCollection1.size() == paramCollection2.size()) {
      Iterator<Column> iterator1 = paramCollection1.iterator();
      Iterator<Column> iterator2 = paramCollection2.iterator();
      while (iterator1.hasNext()) {
        Column column1 = iterator1.next(), column2 = iterator2.next();
        if (!String.valueOf(column1).equalsIgnoreCase(String.valueOf(column2)))
          return false; 
      } 
      return true;
    } 
    return false;
  }
  
  public static String listAttributes(List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Attribute attribute : paramList) {
      if (stringBuilder.length() > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(attribute);
    } 
    return stringBuilder.toString();
  }
  
  public void setColumnOptions(Column paramColumn, String paramString) {
    if (paramColumn != null)
      if (paramString == null) {
        if (this.a != null)
          this.a.remove(paramColumn); 
      } else {
        if (this.a == null)
          this.a = new HashMap<>(); 
        this.a.put(paramColumn, paramString);
      }  
  }
  
  public String getColumnOptions(Column paramColumn) {
    return (this.a != null && !"include".equals(this.a.get(paramColumn))) ? (String)this.a.get(paramColumn) : null;
  }
  
  public Map getColumnOptions() {
    return this.a;
  }
  
  public boolean hasColumnOption(Column paramColumn, String paramString) {
    return (getColumnOptions(paramColumn) != null && getColumnOptions(paramColumn).contains(paramString));
  }
  
  public String getDbId() {
    return this.b.getDbId();
  }
  
  public Schema getSchema() {
    return getEntity().getSchema();
  }
}
