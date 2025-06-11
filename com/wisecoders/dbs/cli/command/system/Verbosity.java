package com.wisecoders.dbs.cli.command.system;

public enum Verbosity {
  a(true),
  b(false);
  
  private boolean c;
  
  Verbosity(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public boolean a() {
    return this.c;
  }
}
