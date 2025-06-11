package com.wisecoders.dbs.project.governance.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.project.governance.store.InspectorLoader;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class InspectorRoot extends InspectorFolder {
  private String f;
  
  private String g;
  
  private String h;
  
  public final List c = new ArrayList();
  
  public InspectorRoot(String paramString) {
    super(null, InspectorFolder$Operation.a);
    this.f = paramString;
  }
  
  public boolean a(AbstractUnit paramAbstractUnit, StringWriter paramStringWriter) {
    this.c.clear();
    return super.a(paramAbstractUnit, paramStringWriter);
  }
  
  public InspectorWarning a(AbstractUnit paramAbstractUnit) {
    for (InspectorWarning inspectorWarning : this.c) {
      if (inspectorWarning.b == paramAbstractUnit)
        return inspectorWarning; 
    } 
    return null;
  }
  
  public void a(AbstractUnit paramAbstractUnit, InspectorDefinition paramInspectorDefinition) {
    InspectorWarning inspectorWarning = a(paramAbstractUnit);
    if (inspectorWarning == null) {
      inspectorWarning = new InspectorWarning(this, paramAbstractUnit);
      this.c.add(inspectorWarning);
    } 
    inspectorWarning.a(paramInspectorDefinition);
  }
  
  public void a(String paramString) {
    this.h = paramString;
  }
  
  public String m() {
    return this.h;
  }
  
  public static final List d = new ArrayList();
  
  public static final List e = new ArrayList();
  
  static {
    File[] arrayOfFile1 = Sys.b().toFile().listFiles();
    if (arrayOfFile1 != null)
      for (File file : arrayOfFile1) {
        if (file.getName().endsWith(".xml"))
          try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
              InspectorLoader inspectorLoader = new InspectorLoader();
              inspectorLoader.parse(fileInputStream);
              d.add(inspectorLoader.a());
              fileInputStream.close();
            } catch (Throwable throwable) {
              try {
                fileInputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
          } catch (Exception exception) {
            Log.b(exception);
          }  
      }  
    File[] arrayOfFile2 = Sys.c().toFile().listFiles();
    if (arrayOfFile2 != null)
      for (File file : arrayOfFile2) {
        if (file.getName().endsWith(".xml"))
          try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
              InspectorLoader inspectorLoader = new InspectorLoader();
              inspectorLoader.parse(fileInputStream);
              e.add(inspectorLoader.a());
              fileInputStream.close();
            } catch (Throwable throwable) {
              try {
                fileInputStream.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              } 
              throw throwable;
            } 
          } catch (Exception exception) {
            Log.b(exception);
          }  
      }  
  }
  
  public static InspectorRoot a(String paramString, Mode paramMode) {
    for (InspectorRoot inspectorRoot : (paramMode == Mode.a) ? d : e) {
      if (inspectorRoot.f.equals(paramString))
        return inspectorRoot; 
    } 
    return null;
  }
  
  public static InspectorRoot b(String paramString, Mode paramMode) {
    InspectorRoot inspectorRoot = new InspectorRoot(paramString);
    if (paramMode == Mode.b) {
      e.add(inspectorRoot);
    } else {
      d.add(inspectorRoot);
    } 
    return inspectorRoot;
  }
  
  public static boolean a(Project paramProject) {
    for (InspectorRoot inspectorRoot : d) {
      if (!inspectorRoot.a(paramProject, (StringWriter)null))
        return false; 
    } 
    return true;
  }
  
  public void b(String paramString) {
    this.g = paramString;
  }
  
  public String n() {
    return this.g;
  }
  
  public void c(String paramString) {
    this.f = paramString;
  }
  
  public String o() {
    return this.f;
  }
}
