package com.wisecoders.dbs.dbms.probe.probes;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.probe.model.ProbeSet;
import com.wisecoders.dbs.dbms.probe.model.ProbeSync;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class ProbeSynchronization extends ProbeSet {
  public ProbeSynchronization(String paramString, Schema paramSchema) {
    super(paramString, "Syncronization");
    Table table1 = paramSchema.createTable("ProbeMaster");
    Column column1 = table1.createColumn("Id", DbmsTypes.get(paramString).getDataType(4));
    column1.setMandatory(true);
    Column column2 = table1.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 100);
    column2.setMandatory(true);
    Index index1 = table1.createPrimaryKey("Pk_Master");
    index1.addColumn(column1);
    Table table2 = paramSchema.createTable("ProbeSlave");
    Column column3 = table2.createColumn("Id", DbmsTypes.get(paramString).getDataType(4));
    column3.setMandatory(true);
    Column column4 = table2.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 120);
    column4.setMandatory(true);
    Index index2 = table2.createPrimaryKey("Pk_Slave");
    index2.addColumn(column3);
    ForeignKey foreignKey = table2.createRelation("Fk");
    foreignKey.addColumns(column3, column1);
    a(new SyncPair(paramSchema, null, null, table1, false, null), "Create");
    a(new SyncPair(paramSchema, null, null, table2, false, null), "Create Foreign Key Inline");
    a(new ProbeSync(paramSchema), "Synchronization compare");
    a(new SyncPair(paramSchema, table2, null, null, false, null), "Drop");
    a(new SyncPair(paramSchema, table1, null, null, false, null), "Drop");
  }
}
