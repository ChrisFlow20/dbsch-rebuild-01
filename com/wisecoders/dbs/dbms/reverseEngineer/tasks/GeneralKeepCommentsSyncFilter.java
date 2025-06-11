package com.wisecoders.dbs.dbms.reverseEngineer.tasks;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.CommentDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;

public class GeneralKeepCommentsSyncFilter implements SyncFilter {
  public boolean rejectDiff(AbstractDiff paramAbstractDiff) {
    if (paramAbstractDiff instanceof CommentDiff) {
      CommentDiff commentDiff = (CommentDiff)paramAbstractDiff;
      if (commentDiff.getUnit(SyncSide.right).getComment() == null)
        return true; 
    } 
    return false;
  }
}
