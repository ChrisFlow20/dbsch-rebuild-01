package com.wisecoders.dbs.cli.command.base;

public class ParameterException extends Exception {
  public ParameterException() {}
  
  public ParameterException(String paramString) {
    super(paramString);
  }
}
