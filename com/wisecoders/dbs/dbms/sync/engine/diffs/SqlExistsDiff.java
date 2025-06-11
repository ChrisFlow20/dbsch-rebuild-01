package com.wisecoders.dbs.dbms.sync.engine.diffs;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Rule;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public class SqlExistsDiff extends AbstractExistsDiff {
  public SqlExistsDiff(SyncPair paramSyncPair) {
    super(paramSyncPair);
  }
  
  public void merge(SyncSide paramSyncSide, GenericLayoutPane paramGenericLayoutPane) {
    Sql sql = (Sql)this.pair.getUnit(paramSyncSide.opposite());
    if (sql != null) {
      Function function;
      Schema schema = (Schema)this.pair.getParent(paramSyncSide);
      Procedure procedure = null;
      if (sql instanceof Procedure) {
        procedure = schema.createProcedure(sql.getName());
      } else if (sql instanceof Rule) {
        Rule rule2 = (Rule)sql;
        Table table1 = rule2.getOwningTable();
        Table table2 = (table1 != null) ? schema.getTable(table1.getName()) : null;
        Rule rule1 = schema.createRule(sql.getName(), table2);
      } else if (sql instanceof Trigger) {
        Trigger trigger2 = (Trigger)sql;
        Table table1 = trigger2.getOwningTable();
        Table table2 = (table1 != null) ? schema.getTable(table1.getName()) : null;
        Trigger trigger1 = schema.createTrigger(sql.getName(), table2);
      } else if (sql instanceof Function) {
        function = schema.createFunction(sql.getName());
      } 
      if (function != null)
        function.cloneFrom(sql); 
    } 
  }
  
  public AlterScript commitInto(String paramString, SyncSide paramSyncSide, boolean paramBoolean) {
    AlterScript alterScript = new AlterScript(paramString);
    Sql sql1 = (Sql)this.pair.getUnit(paramSyncSide.opposite()), sql2 = (Sql)this.pair.getUnit(paramSyncSide);
    Dbms dbms = Dbms.get(paramString);
    if (sql2 != null) {
      char c;
      if (sql2.getSyncPriority() != -1) {
        c = sql2.getSyncPriority();
      } else if (sql2.isSystem()) {
        c = 'ʼ';
      } else if (sql2 instanceof Function) {
        c = '᭘';
      } else if (sql2 instanceof Trigger) {
        c = '✐';
      } else {
        c = '⌨';
      } 
      alterScript.addAll(dbms.alterPlSql.a(this.pair, sql2));
      alterScript.setSortOrder(c);
    } 
    if (sql1 != null)
      alterScript.addAll(dbms.alterPlSql.b(this.pair, sql1)); 
    return alterScript;
  }
}
