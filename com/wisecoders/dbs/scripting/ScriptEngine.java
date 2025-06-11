package com.wisecoders.dbs.scripting;

import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.scripting.actions.ScriptFileAction;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import javafx.embed.swing.JFXPanel;

public class ScriptEngine {
  private Project a = null;
  
  private Connector b = null;
  
  private Envoy c = null;
  
  private final ArrayList d = new ArrayList();
  
  private final PrintStream e = System.out;
  
  public String a() {
    if (this.d.isEmpty())
      return (new File(".")).getPath(); 
    return this.d.get(this.d.size() - 1);
  }
  
  public void a(String paramString) {
    this.d.add(paramString);
  }
  
  public boolean b() {
    return !this.d.isEmpty();
  }
  
  public void c() {
    if (this.d.isEmpty())
      return; 
    this.d.remove(this.d.size() - 1);
  }
  
  public ScriptEngine(String[] paramArrayOfString) {
    new JFXPanel();
    this.e.println("DbSchema " + ScriptEngine.class.getPackage().getSpecificationVersion() + " build " + ScriptEngine.class.getPackage().getImplementationVersion());
    (new ScriptFileAction()).a(this, paramArrayOfString);
  }
  
  public void a(Project paramProject) {
    this.a = paramProject;
  }
  
  public void a(Connector paramConnector) {
    this.b = paramConnector;
    this.c = null;
    if (paramConnector != null)
      this.c = paramConnector.startEnvoy("Script engine"); 
  }
  
  public Project d() {
    return this.a;
  }
  
  public Connector e() {
    return this.b;
  }
  
  public Envoy f() {
    return this.c;
  }
  
  public void b(String paramString) {
    this.e.println(paramString);
  }
  
  public void c(String paramString) {
    this.e.print(paramString);
  }
  
  public PrintStream g() {
    return this.e;
  }
}
