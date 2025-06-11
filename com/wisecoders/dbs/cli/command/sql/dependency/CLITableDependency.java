package com.wisecoders.dbs.cli.command.sql.dependency;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Table;
import java.util.ArrayList;
import java.util.List;

public class CLITableDependency {
  private final List a = new ArrayList();
  
  private final List b = new ArrayList();
  
  public CLITableDependency(List<?> paramList) {
    ArrayList arrayList = new ArrayList(paramList);
    while (true) {
      boolean bool = false;
      for (Table table : paramList) {
        if (!this.a.contains(table)) {
          boolean bool1 = true;
          for (ForeignKey foreignKey : table.getRelations()) {
            AbstractTable abstractTable = foreignKey.getTargetEntity();
            if (!this.b.contains(foreignKey) && paramList.contains(abstractTable) && !this.a.contains(abstractTable)) {
              bool1 = false;
              break;
            } 
          } 
          if (bool1) {
            this.a.add(table);
            bool = true;
          } 
        } 
      } 
      if (!bool) {
        arrayList.removeAll(this.a);
        if (!arrayList.isEmpty()) {
          ForeignKey foreignKey = a(arrayList);
          System.out.println("Cyclic Foreign Key " + foreignKey.getComment() + ".");
          this.b.add(foreignKey);
        } 
        if (arrayList.isEmpty())
          break; 
      } 
    } 
  }
  
  public List a() {
    return this.a;
  }
  
  public List b() {
    return this.b;
  }
  
  public static ForeignKey a(List paramList) {
    int i = -1;
    ForeignKey foreignKey = null;
    for (Table table : paramList) {
      for (ForeignKey foreignKey1 : table.getRelations()) {
        int j = a(paramList, foreignKey1);
        if (j > i) {
          i = j;
          foreignKey = foreignKey1;
        } 
      } 
    } 
    return foreignKey;
  }
  
  public static int a(List paramList, ForeignKey paramForeignKey) {
    ArrayList<Table> arrayList = new ArrayList();
    while (true) {
      boolean bool = false;
      for (Table table : paramList) {
        if (!arrayList.contains(table)) {
          boolean bool1 = true;
          for (ForeignKey foreignKey : table.getRelations()) {
            AbstractTable abstractTable = foreignKey.getEntity();
            if (foreignKey != paramForeignKey && paramList
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
