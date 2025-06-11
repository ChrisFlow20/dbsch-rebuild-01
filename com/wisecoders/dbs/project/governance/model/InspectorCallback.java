package com.wisecoders.dbs.project.governance.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;

public interface InspectorCallback {
  Object evaluate(AbstractUnit paramAbstractUnit, String[] paramArrayOfString);
}
