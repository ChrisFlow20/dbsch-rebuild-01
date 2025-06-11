package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import java.util.List;

public class ThoughtSpot extends Dbms {
  public static final String a = "ThoughtSpot";
  
  public ThoughtSpot() {
    super("ThoughtSpot");
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("show databases", new Object[0]));
  }
  
  public SyncFilter getSynchronizationFilter() {
    return paramAbstractDiff -> 
      (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.ForeignKeyCascadeDiff || (paramAbstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff && (paramAbstractDiff.pair.getUnit() instanceof Index || paramAbstractDiff.pair.getUnit() instanceof com.wisecoders.dbs.schema.ForeignKey)));
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    super.importSchemaAdditions(paramImporter, paramSchema);
    for (Table table : paramSchema.tables) {
      for (Index index : table.indexes) {
        if ((index.getType() == IndexType.UNIQUE_KEY || index.getType() == IndexType.UNIQUE_INDEX) && index.columns.size() == table.columns.size())
          index.markForDeletion(); 
      } 
    } 
  }
}
