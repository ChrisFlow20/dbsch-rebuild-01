package com.wisecoders.dbs.dbms.probe.probes;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.probe.model.ProbeSet;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class ProbeAlterConstraint extends ProbeSet {
  public ProbeAlterConstraint(String paramString, Schema paramSchema) {
    super(paramString, "Constraint");
    Table table = new Table(paramSchema, "Probe");
    Column column1 = table.createColumn("FriendId", DbmsTypes.get(paramString).getDataType(4));
    column1.setMandatory(true);
    column1.setIdentity((Dbms.get(paramString)).columnIdentityOptions.c_());
    Column column2 = table.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 100);
    column2.setMandatory(true);
    Column column3 = table.createColumn(Dbms.get(paramString).toDefaultCases("Age"), DbmsTypes.get(paramString).getDataType(4));
    column3.setMandatory(true);
    Index index = table.createPrimaryKey("PkFriends");
    index.addColumn(column1);
    a(new SyncPair(paramSchema, null, null, table, false, null), "Create Table");
    Constraint constraint1 = table.createConstraint("limit_age");
    constraint1.setText(Dbms.get(paramString).toDefaultCases("Age > 18"));
    a(new SyncPair(table, null, null, constraint1, false, null), "Create Constraint");
    Constraint constraint2 = table.createConstraint("limit_age");
    constraint2.setText(Dbms.get(paramString).toDefaultCases("Age > 20"));
    a(new SyncPair(table, constraint1, null, constraint2, false, null), "Modify Constraint");
    constraint2.rename("verify_age");
    a(new SyncPair(table, constraint1, null, constraint2, false, null), "Rename Constraint");
    a(new SyncPair(table, constraint2, null, null, false, null), "Drop Constraint");
    a(new SyncPair(paramSchema, table, null, null, false, null), "Drop Table");
  }
}
