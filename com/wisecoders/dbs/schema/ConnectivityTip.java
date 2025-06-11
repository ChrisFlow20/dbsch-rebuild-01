package com.wisecoders.dbs.schema;

public enum ConnectivityTip {
  a("<i>Inform DbSchema team using Help / Technical Support."),
  b("Check the database settings for (remote) access and the Windows Firewall. Details in Help."),
  c("Tip: See the SQL statement in the History Pane, on the left.<br>The SQL syntax is configurable in Model / Settings / SQL Syntax.<br>Please inform us using the 'Support Ticket' button.");
  
  public final String d;
  
  ConnectivityTip(String paramString1) {
    this.d = paramString1;
  }
}
