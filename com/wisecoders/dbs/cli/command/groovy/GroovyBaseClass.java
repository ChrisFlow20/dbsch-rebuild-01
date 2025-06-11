package com.wisecoders.dbs.cli.command.groovy;

import groovy.lang.Script;

public abstract class GroovyBaseClass extends Script {
  private static boolean a = false;
  
  public GroovyBaseClass() {
    a = false;
  }
  
  public static void a() {
    a = true;
  }
  
  public static boolean b() {
    return a;
  }
}
