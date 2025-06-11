package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.dialogs.web.fx.FxVideoDialog;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.Iterator;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;

public class FxNewLayoutTab extends Tab {
  private final Rx a = new Rx(FxNewLayoutTab.class, this);
  
  private final FxFrame b;
  
  private final Button c;
  
  private final VBox$ d = new VBox$();
  
  public FxNewLayoutTab(FxFrame paramFxFrame) {
    setGraphic((Node)BootstrapIcons.plus_circle.glyph(new String[] { "font-bold", "glyph-blue" }));
    this.b = paramFxFrame;
    setClosable(false);
    VBox$ vBox$ = (new VBox$()).k().m();
    vBox$.getStyleClass().addAll((Object[])new String[] { "wallpaper", "welcome" });
    vBox$.getChildren().add(this.a.e("layoutTitle"));
    vBox$.getChildren().add(this.a.e("layoutDescription"));
    vBox$.getChildren().add(this.c = this.a.j("newLayout"));
    vBox$.getChildren().add(this.a.e("reopenLayoutTitle"));
    vBox$.getChildren().add(this.a.e("reopenLayoutDescription"));
    vBox$.getChildren().add(this.d);
    setContent((Node)vBox$);
  }
  
  @Action
  public void newLayout() {
    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().add(new MenuItem("generic"));
    contextMenu.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramContextMenu.getItems().clear();
          paramContextMenu.getItems().addAll(this.b.a.e(new String[] { "newLayout", "separator" }));
          for (Layout layout : (this.b.m()).layouts) {
            MenuItem menuItem = new MenuItem("Reopen " + layout.getName());
            menuItem.setOnAction(());
            paramContextMenu.getItems().add(menuItem);
          } 
        });
    contextMenu.show((Node)this.c, Side.TOP, 40.0D, 40.0D);
    this.b.newLayout();
  }
  
  @Action
  public void videoTutorials() {
    (new FxVideoDialog(this.b, "layouts")).showDialog();
  }
  
  public void a() {
    this.d.getChildren().removeAll((Object[])new Node[0]);
    this.d.setAlignment(Pos.CENTER);
    for (Iterator<E> iterator = (this.b.m()).layouts.iterator(); iterator.hasNext(); ) {
      Layout layout = (Layout)iterator.next();
      Hyperlink hyperlink = new Hyperlink(layout.getName());
      hyperlink.setUnderline(true);
      hyperlink.setOnAction(paramActionEvent -> this.b.a(paramLayout));
      this.d.getChildren().add(hyperlink);
    } 
  }
}
