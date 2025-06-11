package com.wisecoders.dbs.dbms.probe.probes;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.probe.model.ProbeSet;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class ProbeAlterColumn extends ProbeSet {
  public ProbeAlterColumn(String paramString, Schema paramSchema) {
    super(paramString, "Column");
    Table table = new Table(paramSchema, "Probe");
    Column column1 = table.createColumn("Id", DbmsTypes.get(paramString).getDataType(4));
    column1.setMandatory(true);
    column1.setIdentity((Dbms.get(paramString)).columnIdentityOptions.c_());
    Column column2 = table.createColumn("Name", DbmsTypes.get(paramString).getDataType(12), 100);
    column2.setMandatory(true);
    Index index = table.createPrimaryKey("Pk_Person");
    index.addColumn(column1);
    a(new SyncPair(paramSchema, null, null, table, true, null), "Create Table");
    Column column3 = table.createColumn("Age", DbmsTypes.get(paramString).getDataType(4));
    column3.setMandatory(true);
    column3.setDefaultValue("12");
    a(new SyncPair(table, null, null, column3, false, null), "Change Mandatory and Default Value");
    Column column4 = table.createColumn("Income", DbmsTypes.get(paramString).getDataType(4));
    a(new SyncPair(table, null, null, column4, false, null), "Add one more column");
    Dbms dbms = Dbms.get(paramSchema.project.getDbId());
    if (dbms.alterColumn.g()) {
      Column column = new Column(table, column2.getName());
      column.cloneFrom(column2);
      column.setMandatory(false);
      a(new SyncPair(table, column2, null, column, false, null), "Reset Mandatory");
    } 
    if (dbms.alterColumn.g()) {
      Column column = new Column(table, column2.getName());
      column.cloneFrom(column2);
      column2.setMandatory(true);
      a(new SyncPair(table, column2, null, column, false, null), "Set Mandatory");
    } 
    Column column5 = new Column(table, column2.getName());
    column5.cloneFrom(column2);
    column5.setLength(120);
    a(new SyncPair(table, column2, null, column5, false, null), "Change Size");
    Column column6 = new Column(table, column3.getName());
    column6.cloneFrom(column3);
    if (dbms.alterColumn.g()) {
      column6.setUnsigned(true);
      a(new SyncPair(table, column3, null, column6, false, null), "Set Unsigned");
      column6.setUnsigned(false);
      a(new SyncPair(table, column3, null, column6, false, null), "Unset Unsigned");
    } 
    Column column7 = new Column(table, column4.getName());
    column7.cloneFrom(column4);
    column7.setDefaultValue("1200");
    a(new SyncPair(table, column4, null, column7, false, null), "Change Default");
    Column column8 = new Column(table, column4.getName());
    column8.cloneFrom(column4);
    column8.setDefaultValue((String)null);
    a(new SyncPair(table, column4, null, column8, false, null), "Clear Default");
    Column column9 = new Column(table, column4.getName());
    column9.cloneFrom(column4);
    column9.rename("income_brut");
    a(new SyncPair(table, column4, null, column9, true, null), "Rename");
    a(new SyncPair(table, column3, null, null, false, null), "Drop Column");
    a(new SyncPair(paramSchema, table, null, null, false, null), "Drop Table");
  }
}
