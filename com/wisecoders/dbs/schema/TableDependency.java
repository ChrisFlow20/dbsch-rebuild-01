package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.EntityIterable;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.ArrayList;
import java.util.List;

@DoNotObfuscate
public class TableDependency {
  private final List a;
  
  private final List b;
  
  private final List c;
  
  private final boolean d;
  
  public TableDependency(EntityIterable paramEntityIterable, boolean paramBoolean) {
    this(a(paramEntityIterable), paramBoolean, false);
  }
  
  public TableDependency(List paramList, boolean paramBoolean) {
    this(paramList, paramBoolean, false);
  }
  
  public TableDependency(List paramList, boolean paramBoolean1, boolean paramBoolean2) {
    boolean bool;
    this.a = new UniqueArrayList();
    this.b = new UniqueArrayList();
    this.c = new UniqueArrayList();
    this.d = paramBoolean2;
    do {
      bool = false;
      for (Table table : paramList) {
        if (!this.a.contains(table)) {
          boolean bool1 = true;
          for (ForeignKey foreignKey : table.getRelations()) {
            AbstractTable abstractTable = foreignKey.getTargetEntity();
            if ((!foreignKey.isVirtual() || paramBoolean2) && paramList.contains(abstractTable) && !this.a.contains(abstractTable) && abstractTable != table) {
              bool1 = false;
              break;
            } 
          } 
          if (bool1) {
            this.a.add(table);
            bool = true;
            for (ForeignKey foreignKey : table.getRelations()) {
              if (paramBoolean1 && (!foreignKey.isVirtual() || paramBoolean2))
                this.c.add(foreignKey); 
            } 
          } 
        } 
      } 
    } while (bool);
    for (Table table : paramList) {
      if (!this.a.contains(table)) {
        this.a.add(table);
        this.b.add(table);
      } 
    } 
  }
  
  private static List a(EntityIterable paramEntityIterable) {
    ArrayList<Table> arrayList = new ArrayList();
    for (AbstractTable abstractTable : paramEntityIterable) {
      if (abstractTable instanceof Table)
        arrayList.add((Table)abstractTable); 
    } 
    return arrayList;
  }
  
  public List getTablesInCreationOrder() {
    return this.a;
  }
  
  public List getTablesInCyclicLoop() {
    return this.b;
  }
  
  public List getInlineForeignKeys() {
    return this.c;
  }
  
  public int compareByCreationOrder(Table paramTable1, Table paramTable2) {
    Integer integer1 = Integer.valueOf(this.a.indexOf(paramTable1));
    Integer integer2 = Integer.valueOf(this.a.indexOf(paramTable2));
    return integer1.compareTo(integer2);
  }
  
  public ForeignKey getForeignKeyToExcludeForSolvingCyclicForeignKeys() {
    int i = -1;
    ForeignKey foreignKey = null;
    for (Table table : this.b) {
      for (ForeignKey foreignKey1 : table.getRelations()) {
        int j = a(foreignKey1);
        if (j > i) {
          i = j;
          foreignKey = foreignKey1;
        } 
      } 
    } 
    return foreignKey;
  }
  
  private int a(ForeignKey paramForeignKey) {
    ArrayList<Table> arrayList = new ArrayList();
    while (true) {
      boolean bool = false;
      for (Table table : this.b) {
        if (!arrayList.contains(table)) {
          boolean bool1 = true;
          for (ForeignKey foreignKey : table.getRelations()) {
            AbstractTable abstractTable = foreignKey.getTargetEntity();
            if (foreignKey != paramForeignKey && (
              !foreignKey.isVirtual() || this.d) && this.b
              .contains(abstractTable) && 
              !arrayList.contains(abstractTable)) {
              bool1 = false;
              break;
            } 
          } 
          if (bool1) {
            arrayList.add(table);
            bool = true;
          } 
        } 
      } 
      if (!bool)
        return arrayList.size(); 
    } 
  }
}
