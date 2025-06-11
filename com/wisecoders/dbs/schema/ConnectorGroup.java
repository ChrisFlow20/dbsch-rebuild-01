package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.List;

@DoNotObfuscate
public class ConnectorGroup extends AbstractUnit {
  private final List a = new ArrayList();
  
  public ConnectorGroup(String paramString) {
    super(paramString);
  }
  
  public void addConnector(Connector paramConnector) {
    if (paramConnector != null)
      this.a.add(paramConnector); 
  }
  
  public void addAll(ConnectorGroup paramConnectorGroup) {
    if (paramConnectorGroup != null)
      this.a.addAll(paramConnectorGroup.getConnectors()); 
  }
  
  public boolean isEmpty() {
    return this.a.isEmpty();
  }
  
  public List getConnectors() {
    return this.a;
  }
  
  public void refresh() {
    this.a.removeIf(Connector::isMarkedForDeletion);
  }
  
  public TreeUnit getParent() {
    return null;
  }
  
  public String getSymbolicName() {
    return null;
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return null;
  }
  
  public Entity getEntity() {
    return null;
  }
}
