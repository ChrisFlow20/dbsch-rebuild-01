package com.wisecoders.dbs.dbms.probe.probes;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.probe.model.ProbeSet;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;

public class ProbeAlterSchema extends ProbeSet {
  public ProbeAlterSchema(String paramString, Project paramProject) {
    super(paramString, "Schema");
    Schema schema1 = new Schema(paramProject, "Probe", null);
    Schema schema2 = new Schema(paramProject, "ProbeRenamed", null);
    a(new SyncPair(paramProject, null, null, schema1, false, null), "Create");
    if ((Dbms.get(paramString)).alterSchema.c()) {
      a(new SyncPair(paramProject, schema1, null, schema2, true, null), "Rename");
      a(new SyncPair(paramProject, schema2, null, null, true, null), "DropR");
    } else {
      a(new SyncPair(paramProject, schema1, null, null, false, null), "Drop");
    } 
  }
}
