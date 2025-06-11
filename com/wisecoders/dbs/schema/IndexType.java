package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.DoNotObfuscate;

@DoNotObfuscate
public enum IndexType {
  PRIMARY_KEY(true, 0),
  UNIQUE_KEY(true, 0),
  UNIQUE_INDEX(true, 0),
  NORMAL(false, 0),
  CLUSTER(false, 1),
  PARTITION(false, 2),
  SORT(false, 3),
  INDEX1(false, 4),
  INDEX2(false, 5);
  
  public final boolean isUnique;
  
  public final int category;
  
  IndexType(boolean paramBoolean, int paramInt1) {
    this.isUnique = paramBoolean;
    this.category = paramInt1;
  }
  
  public boolean sameAs(IndexType paramIndexType) {
    return (this == paramIndexType);
  }
}
