package com.wisecoders.dbs.dbms.probe.probes;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.probe.model.ProbeSet;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class ProbeAlterForeignKey extends ProbeSet {
  public ProbeAlterForeignKey(String paramString, Schema paramSchema) {
    super(paramString, "Foreign Key");
    Table table1 = new Table(paramSchema, "ProbeMaster");
    Column column1 = table1.createColumn("Id", DbmsTypes.get(paramString).getDataType(4));
    column1.setMandatory(true);
    column1.setIdentity((Dbms.get(paramString)).columnIdentityOptions.c_());
    Column column2 = table1.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 100);
    column2.setMandatory(true);
    Index index1 = table1.createPrimaryKey("Pk_Master");
    index1.addColumn(column1);
    Table table2 = new Table(paramSchema, "ProbeSlave");
    Column column3 = table2.createColumn("Id", DbmsTypes.get(paramString).getDataType(4));
    column3.setMandatory(true);
    column3.setIdentity((Dbms.get(paramString)).columnIdentityOptions.c_());
    Column column4 = table2.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 120);
    column4.setMandatory(true);
    Index index2 = table2.createPrimaryKey("Pk_Slave");
    index2.addColumn(column3);
    ForeignKey foreignKey1 = table2.createRelation("Fk");
    foreignKey1.setTargetEntity(table1);
    foreignKey1.addColumns(column3, column1);
    a(new SyncPair(paramSchema, null, null, table1, false, null), "Create");
    a(new SyncPair(paramSchema, null, null, table2, false, null), "Create Foreign Key Inline");
    if ((Dbms.get(paramString)).alterForeignKey.d()) {
      ForeignKey foreignKey = table2.createRelation("Fk");
      foreignKey.setTargetEntity(table1);
      foreignKey.addColumns(column3, column1);
      a(new SyncPair(table2, foreignKey1, null, foreignKey, true, null), "Rename");
      a(new SyncPair(table2, foreignKey, null, null, true, null), "Drop");
    } else {
      a(new SyncPair(table2, foreignKey1, null, null, true, null), "Drop");
    } 
    ForeignKey foreignKey2 = table2.createRelation("Fk");
    foreignKey2.addColumns(column3, column1);
    a(new SyncPair(table2, null, null, foreignKey2, false, null), "Create Foreign Key");
    a(new SyncPair(paramSchema, table2, null, null, false, null), "Drop");
    a(new SyncPair(paramSchema, table1, null, null, false, null), "Drop");
  }
}
