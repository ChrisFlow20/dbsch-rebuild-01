package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.driver.model.DriverJarClass;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;

class d extends Task {
  private final boolean b;
  
  private final boolean c;
  
  private final DriverManager d = DriverManager.a(this.a.F.dbId);
  
  private d(FxConnectorEditor paramFxConnectorEditor, boolean paramBoolean1, boolean paramBoolean2) {
    this.b = paramBoolean1;
    this.c = paramBoolean2;
    paramFxConnectorEditor.V.getItems().clear();
    Platform.runLater(() -> this.a.rx.a(this));
  }
  
  protected List a() {
    updateMessage("Loading drivers...");
    ArrayList arrayList = new ArrayList(this.d.j());
    if (arrayList.isEmpty())
      arrayList.addAll(this.d.i()); 
    return arrayList;
  }
  
  protected void succeeded() {
    this.a.b(false);
    List list = (List)getValue();
    DriverJarClass driverJarClass = null;
    for (DriverJarClass driverJarClass1 : list) {
      this.a.V.getItems().add(driverJarClass1);
      if (this.a.F.getDriverJarFileName() != null && this.a.F.getDriverJarFileName().equals((driverJarClass1.b()).b.getName()))
        driverJarClass = driverJarClass1; 
      if (driverJarClass == null || this.d.a(driverJarClass1))
        driverJarClass = driverJarClass1; 
    } 
    this.a.V.setValue(driverJarClass);
    this.a.g();
    if (this.a.V.getItems().isEmpty()) {
      boolean bool = false;
      if ((Dbms.get(this.a.F.dbId)).hasJDBCDriverOnWeb.b() && !StringUtil.areEqual(this.a.F.dbId, this.a.aT)) {
        this.a.rx.a(new b(this.a));
      } else if (this.c && 
        this.a.rx.a(this.a.getDialogPane().getScene(), "No " + this.a.F.dbId + " JDBC drivers are loaded. Load now ?", "Load Now")) {
        bool = true;
      } 
      if (bool)
        new d$1(this, this.a, this.a.F.dbId); 
    } 
    if (this.b)
      this.a.m(); 
    this.a.b(true);
    this.a.h();
  }
  
  protected void failed() {
    Throwable throwable = getException();
    Log.b(throwable);
    this.a.rx.b(this.a.getDialogPane().getScene(), "loadDriverError", throwable);
  }
}
