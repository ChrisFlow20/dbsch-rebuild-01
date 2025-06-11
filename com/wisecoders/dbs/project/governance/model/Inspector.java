package com.wisecoders.dbs.project.governance.model;

import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.lang.Binding;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inspector implements InspectorNode {
  private final InspectorFolder c;
  
  public final InspectorDefinition a;
  
  public final ArrayList b = new ArrayList();
  
  private boolean d = false;
  
  private boolean e = false;
  
  private final Binding f;
  
  public InspectorParameter a(String paramString) {
    for (InspectorParameter inspectorParameter : this.b) {
      if (inspectorParameter.a.equals(paramString))
        return inspectorParameter; 
    } 
    return null;
  }
  
  public String[] a() {
    String[] arrayOfString = new String[this.b.size()];
    for (byte b = 0; b < this.b.size(); b++)
      arrayOfString[b] = ((InspectorParameter)this.b.get(b)).a(); 
    return arrayOfString;
  }
  
  public Inspector(InspectorFolder paramInspectorFolder, InspectorDefinition paramInspectorDefinition) {
    this.f = new Binding();
    this.c = paramInspectorFolder;
    this.a = paramInspectorDefinition;
    if (paramInspectorDefinition.c != null)
      for (String str : paramInspectorDefinition.c)
        this.b.add(new InspectorParameter(str));  
  }
  
  private static final Map g = new HashMap<>();
  
  private static final SimpleTemplateEngine h = Groovy.b.b();
  
  public boolean a(AbstractUnit paramAbstractUnit, StringWriter paramStringWriter) {
    InspectorDefinition inspectorDefinition = this.a;
    if (inspectorDefinition instanceof FilterDefinition) {
      FilterDefinition filterDefinition = (FilterDefinition)inspectorDefinition;
      if (!((Boolean)filterDefinition.d.evaluate(paramAbstractUnit, a())).booleanValue()) {
        if (!this.d)
          this.c.a().a(paramAbstractUnit, this.a); 
        return false;
      } 
    } else if (paramStringWriter != null) {
      inspectorDefinition = this.a;
      if (inspectorDefinition instanceof FieldDefinition) {
        FieldDefinition fieldDefinition = (FieldDefinition)inspectorDefinition;
        String[] arrayOfString = a();
        InspectorRoot inspectorRoot = k();
        String str1 = (inspectorRoot != null) ? inspectorRoot.n() : "${value}";
        if (arrayOfString != null && arrayOfString.length > 0 && StringUtil.isFilled(arrayOfString[0]))
          str1 = arrayOfString[0]; 
        Template template = (Template)g.get(str1);
        if (template == null) {
          template = h.createTemplate(str1);
          g.put(str1, template);
        } 
        String str2 = (String)fieldDefinition.d.evaluate(paramAbstractUnit, arrayOfString);
        this.f.setVariable("value", str2);
        StringWriter stringWriter = new StringWriter();
        template.make(this.f.getVariables()).writeTo(stringWriter);
        paramStringWriter.append(stringWriter.toString());
      } 
    } 
    return true;
  }
  
  private InspectorRoot k() {
    InspectorFolder inspectorFolder = j();
    while (true) {
      if (inspectorFolder instanceof InspectorRoot)
        return (InspectorRoot)inspectorFolder; 
      if ((inspectorFolder = inspectorFolder.j()) == null)
        return null; 
    } 
  }
  
  public boolean b() {
    return false;
  }
  
  public String toString() {
    return this.a.a;
  }
  
  public void c() {
    this.b.removeIf(InspectorNode::e);
  }
  
  public void d() {
    this.e = true;
  }
  
  public boolean e() {
    return this.e;
  }
  
  public boolean f() {
    return true;
  }
  
  public List g() {
    return this.b;
  }
  
  public void a(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public boolean h() {
    return this.d;
  }
  
  public boolean i() {
    for (InspectorParameter inspectorParameter : this.b) {
      if (this.a instanceof FilterDefinition && !inspectorParameter.i())
        return false; 
    } 
    return true;
  }
  
  public InspectorFolder j() {
    return this.c;
  }
}
