package com.wisecoders.dbs.diagram.util.sql;

import java.io.FileInputStream;

public class LexerRunner {
  public static void main(String[] paramArrayOfString) {
    for (String str : paramArrayOfString) {
      try {
        b b;
        System.out.println("Lexing [" + str + "]");
        a a = new a(new FileInputStream(str));
        do {
          b = a.e();
        } while (b != null);
      } catch (Exception exception) {
        exception.printStackTrace(System.out);
      } 
    } 
  }
}
