package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultRow;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

public class FxResultTreeTableItem extends TreeItem {
  public FxResultTreeTableItem(Result paramResult, ResultRow paramResultRow) {
    setExpanded(true);
    if (paramResultRow != null)
      for (ResultColumn resultColumn : paramResult.f)
        getChildren().add(new FxResultTreeTableItem(new Tuple(resultColumn, paramResultRow.a(resultColumn))));  
  }
  
  public FxResultTreeTableItem(Tuple paramTuple) {
    super(paramTuple);
    Object object = paramTuple.d;
    if (object instanceof Map) {
      Map map = (Map)object;
      for (Object object1 : map.keySet())
        getChildren().add(new FxResultTreeTableItem(new Tuple(object1.toString(), map.get(object1), false))); 
    } else {
      object = paramTuple.d;
      if (object instanceof List) {
        List list = (List)object;
        for (byte b = 0; b < list.size(); b++)
          getChildren().add(new FxResultTreeTableItem(new Tuple("[" + b + "]", list.get(b), true))); 
      } else if (paramTuple.d != null && paramTuple.d.getClass().isArray()) {
        for (byte b = 0; b < Array.getLength(paramTuple.d); b++)
          getChildren().add(new FxResultTreeTableItem(new Tuple("[" + b + "]", Array.get(paramTuple.d, b), true))); 
      } 
    } 
    expandedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> a(this, paramBoolean2.booleanValue()));
  }
  
  protected void a(FxResultTreeTableItem paramFxResultTreeTableItem, boolean paramBoolean) {
    if (getParent() != null)
      ((FxResultTreeTableItem)getParent()).a(paramFxResultTreeTableItem, paramBoolean); 
  }
  
  protected String a() {
    String str = "";
    FxResultTreeTableItem fxResultTreeTableItem = this;
    while (true) {
      if (fxResultTreeTableItem.getValue() != null)
        str = ((Tuple)fxResultTreeTableItem.getValue()).toString() + ((Tuple)fxResultTreeTableItem.getValue()).toString() + (!str.isEmpty() ? "." : ""); 
      if ((fxResultTreeTableItem = (FxResultTreeTableItem)fxResultTreeTableItem.getParent()) == null)
        return str; 
    } 
  }
}
