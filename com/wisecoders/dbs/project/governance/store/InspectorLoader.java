package com.wisecoders.dbs.project.governance.store;

import com.wisecoders.dbs.project.governance.model.Inspector;
import com.wisecoders.dbs.project.governance.model.InspectorFolder;
import com.wisecoders.dbs.project.governance.model.InspectorFolder$Operation;
import com.wisecoders.dbs.project.governance.model.InspectorNode;
import com.wisecoders.dbs.project.governance.model.InspectorParameter;
import com.wisecoders.dbs.project.governance.model.InspectorRepository;
import com.wisecoders.dbs.project.governance.model.InspectorRoot;
import com.wisecoders.dbs.project.store.AbstractContentHandler;

public class InspectorLoader extends AbstractContentHandler {
  private final VxKey[] a = new VxKey[10];
  
  private final InspectorNode[] b = new InspectorNode[10];
  
  private InspectorRoot c;
  
  protected void a(String paramString, int paramInt) {
    VxKey vxKey = VxKey.valueOf(paramString);
    this.a[paramInt] = vxKey;
    this.b[paramInt] = a((paramInt > 0) ? this.a[paramInt - 1] : null, (paramInt > 0) ? this.b[paramInt - 1] : null, vxKey);
  }
  
  private InspectorNode a(VxKey paramVxKey1, InspectorNode paramInspectorNode, VxKey paramVxKey2) {
    Inspector inspector;
    InspectorParameter inspectorParameter;
    switch (InspectorLoader$1.a[paramVxKey2.ordinal()]) {
      case 1:
        if (paramInspectorNode == null) {
          this.c = new InspectorRoot(get(VxKey.name));
          this.c.b(get(VxKey.pattern));
          this.c.a(get(VxKey.dbms));
          return this.c;
        } 
        return ((InspectorFolder)paramInspectorNode).a(InspectorFolder$Operation.valueOf(get(VxKey.on)));
      case 2:
        return ((InspectorFolder)paramInspectorNode).a(InspectorRepository.b(get(VxKey.name)));
      case 3:
        return ((InspectorFolder)paramInspectorNode).a(InspectorRepository.a(get(VxKey.name)), false);
      case 4:
        inspector = ((InspectorFolder)paramInspectorNode).a(InspectorRepository.a(get(VxKey.name)), true);
        inspector.a(true);
        return inspector;
      case 5:
        inspectorParameter = ((Inspector)paramInspectorNode).a(get(VxKey.name));
        if (inspectorParameter != null)
          inspectorParameter.a(get(VxKey.value)); 
        return inspectorParameter;
    } 
    return null;
  }
  
  public InspectorRoot a() {
    return this.c;
  }
}
