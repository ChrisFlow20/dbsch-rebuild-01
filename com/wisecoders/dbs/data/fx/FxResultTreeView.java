package com.wisecoders.dbs.data.fx;

import com.wisecoders.dbs.browse.store.FxBrowseManager;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.schema.DataTypeUtil;
import com.wisecoders.dbs.sys.Rx;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class FxResultTreeView extends TreeTableView implements FxUpdatableTableView {
  protected final FxBrowseManager c;
  
  private final Map a;
  
  protected FxResultTreeView(FxBrowseManager paramFxBrowseManager) {
    this.a = new HashMap<>();
    this.c = paramFxBrowseManager;
    TreeTableColumn treeTableColumn1 = new TreeTableColumn("Column");
    treeTableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn1.setCellFactory(paramTreeTableColumn -> new f());
    treeTableColumn1.setEditable(false);
    TreeTableColumn treeTableColumn2 = new TreeTableColumn("Value");
    treeTableColumn2.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty((paramCellDataFeatures.getValue() != null && paramCellDataFeatures.getValue().getValue() != null && paramCellDataFeatures.getValue().getChildren().isEmpty()) ? paramCellDataFeatures.getValue().getValue() : null));
    treeTableColumn2.setCellFactory(paramTreeTableColumn -> new FxTreeViewCell());
    treeTableColumn2.setEditable(true);
    treeTableColumn2.setGraphic(Rx.a());
    TreeTableColumn treeTableColumn3 = new TreeTableColumn("Type");
    treeTableColumn3.setCellValueFactory(paramCellDataFeatures -> (paramCellDataFeatures != null && paramCellDataFeatures.getValue() != null && paramCellDataFeatures.getValue().getValue() != null) ? (ObservableValue)new ReadOnlyObjectWrapper(a((Tuple)paramCellDataFeatures.getValue().getValue())) : null);
    treeTableColumn3.setEditable(false);
    treeTableColumn3.getStyleClass().add("text-gray");
    getColumns().addAll((Object[])new TreeTableColumn[] { treeTableColumn1, treeTableColumn2, treeTableColumn3 });
    setEditable(true);
    setStyle("-fx-background-insets: 0;");
    setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    getStyleClass().add("data-pane");
    getSelectionModel().setCellSelectionEnabled(true);
    getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    setShowRoot(false);
  }
  
  public void a(Result paramResult, ResultRow paramResultRow) {
    FxResultTreeView$1 fxResultTreeView$1 = new FxResultTreeView$1(this, paramResult, paramResultRow);
    setRoot(fxResultTreeView$1);
    a(fxResultTreeView$1);
  }
  
  private void a(TreeItem paramTreeItem) {
    if (paramTreeItem instanceof FxResultTreeTableItem) {
      String str = ((FxResultTreeTableItem)paramTreeItem).a();
      if (this.a.containsKey(str) && ((Boolean)this.a.get(str)).booleanValue())
        paramTreeItem.setExpanded(true); 
    } 
    for (TreeItem treeItem : paramTreeItem.getChildren())
      a(treeItem); 
  }
  
  private String a(Tuple paramTuple) {
    if (paramTuple.d instanceof List)
      return "List[" + ((List)paramTuple.d).size() + "]"; 
    if (paramTuple.d instanceof Map)
      return "Object"; 
    if (paramTuple.d != null)
      return paramTuple.d.getClass().getSimpleName(); 
    if (paramTuple.a != null)
      return DataTypeUtil.getTypeName(paramTuple.a.b); 
    return "";
  }
  
  public boolean a(List paramList, Object paramObject) {
    return false;
  }
}
