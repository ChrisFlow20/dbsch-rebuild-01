package com.wisecoders.dbs.sys;

import java.text.SimpleDateFormat;

public class StrictDateFormat extends SimpleDateFormat {
  public StrictDateFormat() {
    setLenient(false);
  }
}
