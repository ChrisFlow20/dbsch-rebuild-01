package com.wisecoders.dbs.cli.command.sql.dependency;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Table;
import java.util.ArrayList;
import java.util.List;

public class CLITablePath {
  private Table b;
  
  private final List c = new ArrayList();
  
  private final CLITableHierarchy d;
  
  public final Table a;
  
  private final List e = new ArrayList();
  
  public CLITablePath(CLITableHierarchy paramCLITableHierarchy, Table paramTable) {
    this.d = paramCLITableHierarchy;
    this.b = paramCLITableHierarchy.a;
    this.a = paramTable;
    this.c.addAll(paramCLITableHierarchy.b.columns);
    if (paramTable != this.b && !paramCLITableHierarchy.d().contains(paramTable)) {
      Table table = paramTable;
      ForeignKey foreignKey;
      while ((foreignKey = paramCLITableHierarchy.a(table)) != null) {
        table = (Table)foreignKey.getTargetEntity();
        this.e.add(0, foreignKey);
      } 
    } 
    c();
  }
  
  public int a() {
    if (this.d.c().containsKey(this.a))
      return 1; 
    if (this.d.d().contains(this.a))
      return 0; 
    return this.c.size();
  }
  
  private void c() {
    boolean bool;
    do {
      bool = false;
      if (this.e.size() <= 0)
        continue; 
      ForeignKey foreignKey = this.e.get(0);
      if (!Index.attributesAreEqual(this.c, foreignKey.getTargetAttributes()))
        continue; 
      this.e.remove(foreignKey);
      this.c.clear();
      this.c.addAll(foreignKey.getSourceAttributes());
      this.b = (Table)foreignKey.getEntity();
      bool = true;
    } while (bool);
  }
  
  public String b() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.d.d().contains(this.a)) {
      stringBuilder.append("SELECT * FROM ").append(this.a);
    } else if (this.d.c().containsKey(this.a)) {
      stringBuilder.append("SELECT\n  ");
      stringBuilder.append((String)this.d.c().get(this.a));
      for (Column column : this.a.columns) {
        stringBuilder.append(", ");
        stringBuilder.append(column.getName());
      } 
      stringBuilder.append(" FROM ").append(this.a.getName());
    } else if (this.e.isEmpty()) {
      stringBuilder.append("SELECT\n  ");
      boolean bool = true;
      for (Column column : this.c) {
        if (bool) {
          bool = false;
        } else {
          stringBuilder.append(", ");
        } 
        stringBuilder.append(column.getName());
      } 
      for (Column column : this.a.columns) {
        if (bool) {
          bool = false;
        } else {
          stringBuilder.append(", ");
        } 
        stringBuilder.append(column.getName());
      } 
      stringBuilder.append(" FROM ").append(this.a.getName());
    } else {
      stringBuilder.append("SELECT\n  ");
      boolean bool = true;
      for (Column column : this.c) {
        if (bool) {
          bool = false;
        } else {
          stringBuilder.append(", ");
        } 
        stringBuilder.append(this.b.getName()).append(".").append(column.getName());
      } 
      stringBuilder.append(", ").append(this.a.getName()).append(".*\n");
      stringBuilder.append("FROM ").append(this.b.getName());
      for (ForeignKey foreignKey : this.e) {
        stringBuilder.append("\nJOIN ").append(foreignKey.getEntity().getName()).append(" ON ( ");
        for (byte b = 0; b < foreignKey.getTargetAttributes().size(); b++) {
          if (b > 0)
            stringBuilder.append(" AND "); 
          Column column1 = foreignKey.getTargetAttributes().get(b);
          Column column2 = foreignKey.getSourceAttributes().get(b);
          stringBuilder.append(column1.table.getName()).append(".").append(column1.getName()).append("=").append(column2.table.getName()).append(".").append(column2.getName());
        } 
        stringBuilder.append(" ) ");
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.c).append(" ").append(b());
    return stringBuilder.toString();
  }
}
