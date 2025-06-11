package com.wisecoders.dbs.generator.engine.tasks;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.generator.engine.plan.HasData;
import com.wisecoders.dbs.generator.fx.FxGeneratorEditor;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.GeneratorPlan;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;
import javafx.concurrent.Task;

public class FxCheckDataPresenceTask extends Task {
  private final FxGeneratorEditor a;
  
  private final GeneratorPlan b;
  
  private final Envoy c;
  
  public FxCheckDataPresenceTask(FxGeneratorEditor paramFxGeneratorEditor, Connector paramConnector, GeneratorPlan paramGeneratorPlan) {
    this.a = paramFxGeneratorEditor;
    this.b = paramGeneratorPlan;
    this.c = paramConnector.startEnvoy("Verify Tables Has Data");
  }
  
  protected Void a() {
    updateMessage("Checking if tables are empty...");
    boolean bool = false;
    int i = this.b.getTableGenerators().size();
    if (!isCancelled())
      for (GeneratorTable generatorTable : this.b.getTableGenerators()) {
        updateProgress(bool, i);
        updateMessage("Check table '" + generatorTable.table.getName() + "'");
        String str = Dbms.get(this.c.e()).getSelectQuery(generatorTable.table, false);
        SelectStatement selectStatement = this.c.a(str, new Object[0]);
        try {
          selectStatement.c("Check presence of the data");
          ResultSet resultSet = selectStatement.j();
          generatorTable.setHasData(resultSet.next() ? HasData.b : HasData.c);
          resultSet.close();
          if (selectStatement != null)
            selectStatement.close(); 
        } catch (Throwable throwable) {
          if (selectStatement != null)
            try {
              selectStatement.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      }  
    this.c.close();
    return null;
  }
  
  protected void failed() {
    Throwable throwable = getException();
    this.c.a(throwable);
    Log.a("Error when verifying table data existence in Data Generator", throwable);
    this.a.showError(throwable.toString());
  }
}
