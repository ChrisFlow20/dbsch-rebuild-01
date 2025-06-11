package com.wisecoders.dbs.dbms.probe.probes;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.probe.model.ProbeSet;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class ProbeAlterTable extends ProbeSet {
  public ProbeAlterTable(String paramString, Schema paramSchema) {
    super(paramString, "Table");
    Table table = new Table(paramSchema, "Probe");
    table.setComment("Some comment");
    Column column1 = table.createColumn("Id", DbmsTypes.get(paramString).getDataType(4));
    column1.setMandatory(true);
    column1.setIdentity((Dbms.get(paramString)).columnIdentityOptions.c_());
    Column column2 = table.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 100);
    column2.setMandatory(true);
    Index index = table.createPrimaryKey("Pk_Master");
    index.addColumn(column1);
    a(new SyncPair(paramSchema, null, null, table, false, null), "Create");
    if ((Dbms.get(paramString)).alterTable.c()) {
      Table table1 = new Table(paramSchema, "ProbeTableRenamed");
      a(new SyncPair(paramSchema, table, null, table1, true, null), "Rename");
      a(new SyncPair(paramSchema, table1, null, null, false, null), "Drop");
    } else {
      a(new SyncPair(paramSchema, table, null, null, false, null), "Drop");
    } 
  }
}
