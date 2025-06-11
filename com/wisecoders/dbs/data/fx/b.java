package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.explain.ExecutionPlanNode;
import javafx.scene.control.TreeItem;

class b extends TreeItem {
  public b(ExecutionPlanNode paramExecutionPlanNode) {
    super(paramExecutionPlanNode);
    setExpanded(true);
    for (ExecutionPlanNode executionPlanNode : paramExecutionPlanNode.e)
      getChildren().add(new b(executionPlanNode)); 
  }
}
