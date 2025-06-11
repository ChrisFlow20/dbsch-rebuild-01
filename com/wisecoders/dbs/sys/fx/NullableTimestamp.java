package com.wisecoders.dbs.sys.fx;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class NullableTimestamp extends Timestamp {
  public NullableTimestamp() {
    super(0L);
  }
  
  public NullableTimestamp(long paramLong) {
    super(paramLong);
  }
  
  public String toString() {
    return (getTime() > 0L) ? super.toString() : "";
  }
  
  public static NullableTimestamp a(LocalDateTime paramLocalDateTime) {
    return new NullableTimestamp(Timestamp.valueOf(paramLocalDateTime).getTime());
  }
}
