package com.wisecoders.dbs.sql.scheduler;

import java.security.InvalidParameterException;

public class InvalidScheduleValue extends InvalidParameterException {
  public InvalidScheduleValue(int paramInt1, int paramInt2) {
    super("Invalid value " + paramInt2 + ". Allowed is from " + Schedule.g[paramInt1] + " to " + Schedule.h[paramInt1]);
  }
}
