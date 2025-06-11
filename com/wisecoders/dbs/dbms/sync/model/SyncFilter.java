package com.wisecoders.dbs.dbms.sync.model;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public interface SyncFilter {
  @GroovyMethod
  boolean rejectDiff(AbstractDiff paramAbstractDiff);
}
