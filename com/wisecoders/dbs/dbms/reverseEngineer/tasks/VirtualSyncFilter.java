package com.wisecoders.dbs.dbms.reverseEngineer.tasks;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;

public class VirtualSyncFilter implements SyncFilter {
  public boolean rejectDiff(AbstractDiff paramAbstractDiff) {
    return ((paramAbstractDiff.pair.left != null && paramAbstractDiff.pair.left.isVirtual()) || (paramAbstractDiff.pair.right != null && paramAbstractDiff.pair.right.isVirtual()));
  }
}
