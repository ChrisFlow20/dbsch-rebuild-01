package com.wisecoders.dbs.dbms.probe.probes;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.probe.model.ProbeSet;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class ProbeAlterPrimaryKey extends ProbeSet {
  public ProbeAlterPrimaryKey(String paramString, Schema paramSchema) {
    super(paramString, "Primary Key");
    Table table = new Table(paramSchema, "Probe");
    Column column1 = table.createColumn("SportId", DbmsTypes.get(paramString).getDataType(4));
    column1.setMandatory(true);
    Column column2 = table.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 10);
    column2.setMandatory(true);
    Column column3 = table.createColumn(Dbms.get(paramString).toDefaultCases("Performance"), DbmsTypes.get(paramString).getDataType(4), 10);
    column3.setMandatory(true);
    a(new SyncPair(paramSchema, null, null, table, false, null), "Create Test Table");
    Index index1 = table.createPrimaryKey("PkSports");
    index1.addColumn(column1);
    a(new SyncPair(table, null, null, index1, false, null), "Add Primary Key");
    Index index2 = table.createPrimaryKey("PkSports");
    index2.addColumn(column1);
    index2.addColumn(column2);
    a(new SyncPair(table, index1, null, index2, false, null), "Add More Column to Primary Key");
    if ((Dbms.get(paramString)).alterPrimaryKey.c()) {
      Index index = table.createPrimaryKey("PkSports");
      index.addColumn(column1);
      index.rename("PkSports2");
      a(new SyncPair(table, index1, null, index, false, null), "Rename");
      a(new SyncPair(table, index, null, null, false, null), "Drop");
    } else {
      a(new SyncPair(table, index1, null, null, false, null), "Drop");
    } 
    a(new SyncPair(paramSchema, table, null, null, false, null), "Drop Table");
  }
}
