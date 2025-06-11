package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class Menu$ extends Menu {
  public static String a = "separator";
  
  public Menu$(String paramString) {
    super(paramString);
  }
  
  public Menu$(String paramString, Node paramNode) {
    super(paramString, paramNode);
  }
  
  public Menu$ a(Rx paramRx, String... paramVarArgs) {
    for (String str : paramVarArgs) {
      if (a.equals(str)) {
        getItems().add(new SeparatorMenuItem());
      } else {
        getItems().addAll((Object[])new MenuItem[] { paramRx.A(str) });
      } 
    } 
    return this;
  }
  
  public Menu$ b(Rx paramRx, String... paramVarArgs) {
    for (String str : paramVarArgs) {
      if (a.equals(str)) {
        getItems().add(new SeparatorMenuItem());
      } else {
        getItems().addAll((Object[])new MenuItem[] { (MenuItem)paramRx.y(str) });
      } 
    } 
    return this;
  }
}
