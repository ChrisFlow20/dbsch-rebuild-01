package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.dialogs.git.model.GitFile;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class FxGitTreeView extends TreeView {
  private final String a;
  
  public final TreeItem b;
  
  private final FxGitDialog c;
  
  public FxGitTreeView(FxGitDialog paramFxGitDialog, String paramString) {
    this.a = paramString;
    this.c = paramFxGitDialog;
    this.b = new TreeItem(null);
    this.b.setExpanded(true);
    setRoot(this.b);
    setCellFactory(paramTreeView -> new FxGitTreeView$FxGitTreeCell(this));
    VBox.setVgrow((Node)this, Priority.ALWAYS);
  }
  
  public List b() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (TreeItem treeItem : this.b.getChildren()) {
      if (((GitFile)treeItem.getValue()).b())
        uniqueArrayList.add(((GitFile)treeItem.getValue()).i); 
    } 
    return uniqueArrayList;
  }
  
  public boolean c() {
    for (TreeItem treeItem : this.b.getChildren()) {
      if (((GitFile)treeItem.getValue()).b())
        return true; 
    } 
    return false;
  }
  
  public abstract void a();
}
