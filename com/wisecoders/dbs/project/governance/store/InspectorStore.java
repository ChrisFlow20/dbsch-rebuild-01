package com.wisecoders.dbs.project.governance.store;

import com.wisecoders.dbs.project.governance.model.Inspector;
import com.wisecoders.dbs.project.governance.model.InspectorFolder;
import com.wisecoders.dbs.project.governance.model.InspectorNode;
import com.wisecoders.dbs.project.governance.model.InspectorParameter;
import com.wisecoders.dbs.project.governance.model.InspectorRoot;
import com.wisecoders.dbs.project.store.XMLWriter;
import java.io.Writer;

public class InspectorStore {
  public InspectorStore(InspectorRoot paramInspectorRoot, Writer paramWriter) {
    XMLWriter xMLWriter = new XMLWriter(paramWriter);
    paramInspectorRoot.c();
    xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    a(paramInspectorRoot, xMLWriter);
    xMLWriter.b();
  }
  
  private void a(InspectorFolder paramInspectorFolder, XMLWriter paramXMLWriter) {
    paramXMLWriter.a(VxKey.iterator).e(VxKey.on, paramInspectorFolder.a);
    if (paramInspectorFolder instanceof InspectorRoot) {
      InspectorRoot inspectorRoot = (InspectorRoot)paramInspectorFolder;
      paramXMLWriter.e(VxKey.name, inspectorRoot.o())
        .e(VxKey.pattern, inspectorRoot.n())
        .e(VxKey.dbms, inspectorRoot.m());
    } 
    for (InspectorNode inspectorNode : paramInspectorFolder.b) {
      if (inspectorNode instanceof InspectorFolder) {
        a((InspectorFolder)inspectorNode, paramXMLWriter);
        continue;
      } 
      if (inspectorNode instanceof Inspector)
        a((Inspector)inspectorNode, paramXMLWriter); 
    } 
    paramXMLWriter.b(VxKey.iterator);
  }
  
  private void a(Inspector paramInspector, XMLWriter paramXMLWriter) {
    VxKey vxKey = VxKey.validator;
    if (paramInspector.a instanceof com.wisecoders.dbs.project.governance.model.FieldDefinition) {
      vxKey = VxKey.field;
    } else if (paramInspector.h()) {
      vxKey = VxKey.filter;
    } 
    paramXMLWriter.a(vxKey)
      .e(VxKey.name, paramInspector.a.a);
    for (InspectorParameter inspectorParameter : paramInspector.b)
      a(inspectorParameter, paramXMLWriter); 
    paramXMLWriter.b(vxKey);
  }
  
  private void a(InspectorParameter paramInspectorParameter, XMLWriter paramXMLWriter) {
    if (paramInspectorParameter.a() != null) {
      paramXMLWriter.a(VxKey.parameter)
        .e(VxKey.name, paramInspectorParameter.a)
        .e(VxKey.value, paramInspectorParameter.a());
      paramXMLWriter.b(VxKey.parameter);
    } 
  }
}
