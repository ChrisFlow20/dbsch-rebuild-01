package com.wisecoders.dbs.generator.engine.tasks;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.AbstractStatement;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.UpdateStatement;
import com.wisecoders.dbs.generator.engine.plan.HasData;
import com.wisecoders.dbs.generator.fx.FxGeneratorEditor;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.GeneratorPlan;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.schema.GeneratorTable$Status;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.concurrent.Task;

public class FxGeneratorTask extends Task {
  private final FxGeneratorEditor a;
  
  private final Connector b;
  
  private final GeneratorPlan c;
  
  private final boolean d;
  
  private final Envoy e;
  
  private AbstractStatement f;
  
  private final int g;
  
  private final int h;
  
  private int i = 0;
  
  private long j;
  
  protected Void a() {
    this.b.throwSQLExceptionIfReadOnly();
    if (this.d) {
      for (int i = this.h - 1; i > -1 && !isCancelled(); i--) {
        GeneratorTable generatorTable = (GeneratorTable)this.c.getTableGenerators().get(i);
        generatorTable.setStatus(GeneratorTable$Status.c);
        updateMessage("Delete " + String.valueOf(generatorTable) + " data");
        updateProgress((this.h - i), this.h);
        String str = (Dbms.get(this.b.dbId)).deleteStatement.c_();
        if (!isCancelled() && StringUtil.isFilledTrim(str)) {
          str = str.replaceAll("\\$\\{table\\}", generatorTable.table.ref());
          UpdateStatement updateStatement = this.e.b(str, new Object[0]);
          try {
            updateStatement.a();
            if (updateStatement != null)
              updateStatement.close(); 
          } catch (Throwable throwable) {
            if (updateStatement != null)
              try {
                updateStatement.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
        if (isCancelled()) {
          this.e.q();
        } else {
          this.e.p();
        } 
        str = (Dbms.get(this.b.dbId)).resetIdentity.c_();
        if (!isCancelled() && StringUtil.isFilledTrim(str)) {
          str = str.replaceAll("\\$\\{table\\}", generatorTable.table.ref());
          UpdateStatement updateStatement = this.e.b(str, new Object[0]);
          try {
            updateStatement.a();
            if (updateStatement != null)
              updateStatement.close(); 
          } catch (Throwable throwable) {
            if (updateStatement != null)
              try {
                updateStatement.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
          if (isCancelled()) {
            this.e.q();
          } else {
            this.e.p();
          } 
        } 
        generatorTable.setHasData(HasData.c);
        generatorTable.setStatus(GeneratorTable$Status.a);
      } 
      this.e.p();
    } 
    for (GeneratorTable generatorTable : this.c.getTableGenerators()) {
      generatorTable.setStatus(GeneratorTable$Status.d);
      if (!isCancelled()) {
        updateMessage("Generate table '" + String.valueOf(generatorTable) + "' data");
        if (this.g > 0)
          updateProgress(Math.min(this.i, this.g), this.g); 
        this





          
          .f = this.b.isMongo() ? new FxGeneratorTask$1(this, this.e, generatorTable, generatorTable) : new FxGeneratorTask$2(this, this.e, generatorTable, generatorTable);
        this.f.a();
        this.f.close();
        this.i += generatorTable.table.getGeneratorRowsCount();
        if (!Sys.B.skipErrors.b() && generatorTable.hasReachedConsecutiveFailureCount())
          cancel(false); 
      } 
      generatorTable.setStatus(GeneratorTable$Status.b);
    } 
    this.e.close();
    return null;
  }
  
  protected FxGeneratorTask(FxGeneratorEditor paramFxGeneratorEditor, Connector paramConnector, GeneratorPlan paramGeneratorPlan, boolean paramBoolean) {
    this.j = System.currentTimeMillis();
    this.a = paramFxGeneratorEditor;
    this.b = paramConnector;
    this.c = paramGeneratorPlan;
    this.d = paramBoolean;
    this.g = paramGeneratorPlan.getTotalRows();
    this.h = paramGeneratorPlan.getTableGenerators().size();
    this.e = paramConnector.startEnvoy("Generate Data Task");
    paramGeneratorPlan.resetTableGenerator();
  }
  
  private void a(GeneratorTable paramGeneratorTable) {
    if (System.currentTimeMillis() - this.j > 100L) {
      updateMessage("Succeed " + paramGeneratorTable.getSucceedCount() + " rows / " + paramGeneratorTable.getFailedCount() + " failures of " + paramGeneratorTable.table.getGeneratorRowsCount() + " total rows in '" + String.valueOf(paramGeneratorTable) + "'.");
      updateProgress((this.i + paramGeneratorTable.getSucceedCount()), this.g);
      this.j = System.currentTimeMillis();
    } 
  }
  
  protected void succeeded() {}
  
  protected void failed() {
    Throwable throwable = getException();
    this.e.a(throwable);
    Log.a("Error in Data Generator Task", throwable);
    this.a.showError(throwable.toString(), throwable);
  }
  
  public boolean cancel(boolean paramBoolean) {
    if (this.f != null)
      this.f.c(); 
    return super.cancel(paramBoolean);
  }
}
