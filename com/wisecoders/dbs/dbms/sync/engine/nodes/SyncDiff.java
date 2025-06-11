package com.wisecoders.dbs.dbms.sync.engine.nodes;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.diagram.model.AbstractUnit;

public interface SyncDiff {
  SyncAction getAction();
  
  void setAction(SyncAction paramSyncAction, boolean paramBoolean);
  
  AbstractUnit getUnit(SyncSide paramSyncSide);
  
  AbstractDiff getNodeDiff();
  
  boolean matches(SyncDiffFilter paramSyncDiffFilter);
}
