package com.wisecoders.dbs.generator.engine.tasks;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.generator.fx.FxGeneratorEditor;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.GeneratorPlan;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.schema.GeneratorTable$Status;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.concurrent.Task;

public class FxGeneratorToFileTask extends Task {
  private final FxGeneratorEditor a;
  
  private final Connector b;
  
  private final GeneratorPlan c;
  
  private Envoy d;
  
  private final File e;
  
  private final int f;
  
  private final int g;
  
  private int h = 0;
  
  private final boolean i;
  
  protected FxGeneratorToFileTask(FxGeneratorEditor paramFxGeneratorEditor, Connector paramConnector, GeneratorPlan paramGeneratorPlan, File paramFile, boolean paramBoolean) {
    this.a = paramFxGeneratorEditor;
    this.b = paramConnector;
    this.e = paramFile;
    this.c = paramGeneratorPlan;
    this.f = paramGeneratorPlan.getTotalRows();
    this.g = paramGeneratorPlan.getTableGenerators().size();
    this.i = paramBoolean;
    if (paramConnector != null)
      this.d = paramConnector.startEnvoy("Generate Data Task"); 
    paramGeneratorPlan.resetTableGenerator();
  }
  
  protected Void a() {
    for (GeneratorTable generatorTable : this.c.getTableGenerators())
      generatorTable.initialize(); 
    FileWriter fileWriter = new FileWriter(this.e);
    try {
      boolean bool = false;
      for (GeneratorTable generatorTable : this.c.getTableGenerators()) {
        generatorTable.setStatus(GeneratorTable$Status.d);
        if (!isCancelled()) {
          updateMessage("Generate table '" + String.valueOf(generatorTable) + "' data");
          updateProgress(bool, this.g);
          if (this.f > 0)
            updateProgress(Math.min(this.h, this.f), this.f); 
          if (this.d != null)
            generatorTable.learnFkColumnValues(this.d); 
          if (this.i)
            fileWriter.write(generatorTable.buildCSVHeader()); 
          while (generatorTable.insertMore() && !isCancelled()) {
            try {
              generatorTable.setStatus(GeneratorTable$Status.d);
              Map map = generatorTable.generateValuesSet();
              a(generatorTable.table, map);
              if (this.i) {
                fileWriter.write(generatorTable.buildCSVRecord(map));
                fileWriter.write("\n");
              } else {
                fileWriter.write(generatorTable.buildSQL(map));
                fileWriter.write((Dbms.get(generatorTable.table.getDbId())).statementDelimiter.c_());
                fileWriter.write("\n");
              } 
              generatorTable.increaseSucceedCount(1);
            } catch (Throwable throwable) {
              generatorTable.increaseFailedCount();
              generatorTable.createFlatTextWarn(SqlUtil.getExceptionText(throwable));
            } 
          } 
          fileWriter.flush();
          this.h += generatorTable.table.getGeneratorRowsCount();
          if (!Sys.B.skipErrors.b() && generatorTable.hasReachedConsecutiveFailureCount())
            cancel(false); 
        } 
        generatorTable.setStatus(GeneratorTable$Status.b);
      } 
      if (this.d != null)
        this.d.close(); 
      fileWriter.close();
    } catch (Throwable throwable) {
      try {
        fileWriter.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return null;
  }
  
  private void a(Table paramTable, Map paramMap) {
    for (Relation relation : paramTable.getImportedRelations()) {
      if (relation instanceof ForeignKey) {
        ForeignKey foreignKey = (ForeignKey)relation;
        GeneratorTable generatorTable = this.c.getTableGenerator(foreignKey.getEntity());
        if (generatorTable != null) {
          HashMap<Object, Object> hashMap = new HashMap<>();
          for (Iterator<Column> iterator1 = foreignKey.getSourceAttributes().iterator(), iterator2 = foreignKey.getTargetAttributes().iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
            Column column1 = iterator1.next(), column2 = iterator2.next();
            Object object = paramMap.get(column2);
            if (object != null)
              hashMap.put(column1, object); 
          } 
          generatorTable.loadForeignKeyGeneratorsValues(hashMap);
        } 
      } 
    } 
  }
  
  protected void succeeded() {}
  
  protected void failed() {
    Throwable throwable = getException();
    if (this.d != null)
      this.d.a(throwable); 
    Log.a("Error in Data Generator Task", throwable);
    this.a.showError(throwable.toString(), throwable);
  }
}
