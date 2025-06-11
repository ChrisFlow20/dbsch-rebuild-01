package com.wisecoders.dbs.dbms.sync.engine.nodes;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public enum SyncSide {
  left, right;
  
  public SyncSide opposite() {
    if (this == left)
      return right; 
    return left;
  }
}
