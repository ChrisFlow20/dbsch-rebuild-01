package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.generator.engine.plan.CyclicFkWarn;
import com.wisecoders.dbs.generator.engine.plan.HasData;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@DoNotObfuscate
public class GeneratorPlan {
  private final ObservableList a = FXCollections.observableArrayList();
  
  public GeneratorTable addTable(Table paramTable) {
    GeneratorTable generatorTable = new GeneratorTable(paramTable, false);
    this.a.add(generatorTable);
    return generatorTable;
  }
  
  public void removeTable(Table paramTable) {
    for (Iterator<GeneratorTable> iterator = this.a.iterator(); iterator.hasNext(); ) {
      GeneratorTable generatorTable = iterator.next();
      if (generatorTable.table == paramTable)
        iterator.remove(); 
    } 
  }
  
  public GeneratorTable getTableGenerator(AbstractTable paramAbstractTable) {
    for (GeneratorTable generatorTable : this.a) {
      if (generatorTable.table == paramAbstractTable)
        return generatorTable; 
    } 
    return null;
  }
  
  public boolean containsTable(AbstractTable paramAbstractTable) {
    for (GeneratorTable generatorTable : this.a) {
      if (generatorTable.table == paramAbstractTable)
        return true; 
    } 
    return false;
  }
  
  public boolean hasGenerators() {
    return (this.a.size() > 0);
  }
  
  public ObservableList getTableGenerators() {
    return this.a;
  }
  
  public void resetTableGenerator() {
    for (GeneratorTable generatorTable : this.a)
      generatorTable.a(); 
  }
  
  public void clearWarns() {
    for (GeneratorTable generatorTable : this.a)
      generatorTable.b(); 
  }
  
  public boolean tablesHaveOrderSet() {
    for (GeneratorTable generatorTable : this.a) {
      if (generatorTable.table.getGeneratorOrder() > -1)
        return true; 
    } 
    return false;
  }
  
  public void sortByTableOrder() {
    Collections.sort((List<?>)this.a, Comparator.comparingInt(paramGeneratorTable -> paramGeneratorTable.table.getGeneratorOrder()));
  }
  
  public void setTableOrder() {
    ArrayList<Table> arrayList = new ArrayList();
    for (GeneratorTable generatorTable : this.a)
      arrayList.add(generatorTable.table); 
    TableDependency tableDependency = new TableDependency(arrayList, true, true);
    Collections.sort((List<?>)this.a, (paramGeneratorTable1, paramGeneratorTable2) -> {
          int i = paramTableDependency.getTablesInCreationOrder().indexOf(paramGeneratorTable1.table);
          int j = paramTableDependency.getTablesInCreationOrder().indexOf(paramGeneratorTable2.table);
          return Integer.compare(i, j);
        });
    if (tableDependency.getTablesInCyclicLoop().size() > 0) {
      ForeignKey foreignKey = tableDependency.getForeignKeyToExcludeForSolvingCyclicForeignKeys();
      if (foreignKey != null) {
        GeneratorTable generatorTable = getTableGenerator(foreignKey.getEntity());
        generatorTable.a(new CyclicFkWarn(generatorTable.table, foreignKey));
      } 
    } 
    saveGeneratorOrder(true);
  }
  
  public void checkPatterns() {
    for (GeneratorTable generatorTable : this.a)
      generatorTable.validate(); 
  }
  
  public void presetNumberOfRows() {
    for (GeneratorTable generatorTable : this.a) {
      if (generatorTable.table.getGeneratorRowsCount() == -1) {
        generatorTable.table.setGeneratorRowsCount(100);
        for (ForeignKey foreignKey : generatorTable.table.getRelations()) {
          GeneratorTable generatorTable1 = getTableGenerator(foreignKey.getTargetEntity());
          if (generatorTable1 != null && generatorTable1.table.getGeneratorRowsCount() > -1)
            generatorTable.table.setGeneratorRowsCount((int)Math.max(generatorTable1.table.getGeneratorRowsCount() * 1.5D, generatorTable.table.getGeneratorRowsCount())); 
        } 
      } 
    } 
  }
  
  public HasData hasData() {
    for (GeneratorTable generatorTable : this.a) {
      switch (GeneratorPlan$1.a[generatorTable.hasData().ordinal()]) {
        case 1:
          return HasData.a;
        case 2:
          return HasData.b;
      } 
    } 
    return HasData.c;
  }
  
  public int getTotalRows() {
    int i = 0;
    for (GeneratorTable generatorTable : this.a)
      i += generatorTable.table.getGeneratorRowsCount(); 
    return i;
  }
  
  public boolean moveUp(int paramInt) {
    if (paramInt < 1 || paramInt > this.a.size())
      return false; 
    GeneratorTable generatorTable = (GeneratorTable)this.a.remove(paramInt);
    this.a.add(paramInt - 1, generatorTable);
    return true;
  }
  
  public boolean moveDown(int paramInt) {
    if (paramInt < 0 || paramInt > this.a.size() - 1)
      return false; 
    GeneratorTable generatorTable = (GeneratorTable)this.a.remove(paramInt);
    this.a.add(paramInt + 1, generatorTable);
    return true;
  }
  
  public GeneratorTable get(Table paramTable) {
    for (GeneratorTable generatorTable : this.a) {
      if (generatorTable.table == paramTable)
        return generatorTable; 
    } 
    return null;
  }
  
  public void saveGeneratorOrder(boolean paramBoolean) {
    byte b = 0;
    for (GeneratorTable generatorTable : this.a)
      generatorTable.table.setGeneratorOrder(paramBoolean ? -1 : b++); 
  }
}
