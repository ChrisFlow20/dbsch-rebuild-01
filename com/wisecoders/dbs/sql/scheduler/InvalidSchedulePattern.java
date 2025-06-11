package com.wisecoders.dbs.sql.scheduler;

import java.security.InvalidParameterException;

public class InvalidSchedulePattern extends InvalidParameterException {
  public InvalidSchedulePattern() {
    super("<html>Invalid value. Allowed are : <ul><li> * : means any value<li> */5 : means each 5th<li> 1,2,3 : list of values<li> 1-2 : interval</ul>");
  }
}
