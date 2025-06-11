package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class SqlScriptDiff extends AbstractDiff {
  public SqlScriptDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public String getDiffString(SyncSide paramSyncSide) {
    return "Script";
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Sql sql1 = (Sql)this.pair.getUnit(paramSyncSide.opposite()), sql2 = (Sql)this.pair.getUnit(paramSyncSide);
    sql2.cloneFrom(sql1);
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Dbms dbms = Dbms.get(paramString);
    Sql sql1 = (Sql)this.pair.getUnit(paramSyncSide.opposite()), sql2 = (Sql)this.pair.getUnit(paramSyncSide);
    alterScript.addAll(dbms.alterPlSql.b(this.pair, sql1));
    alterScript.addAll(dbms.alterPlSql.a(this.pair, sql2));
    return alterScript;
  }
}
