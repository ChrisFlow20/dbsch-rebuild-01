package com.wisecoders.dbs.loader.fx;

import com.wisecoders.dbs.dialogs.layout.FxColumnEditor;
import com.wisecoders.dbs.loader.model.LoaderColumn;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.layout.Priority;

class c extends TableCell {
  final HBox$ a;
  
  final Button b;
  
  final ComboBox c;
  
  private c(FxLoaderDialog paramFxLoaderDialog) {
    this.a = new HBox$();
    this.b = new Button("", (Node)BootstrapIcons.pencil_fill.glyph(new String[0]));
    this.c = new ComboBox();
    this.c.setPrefWidth(2.147483647E9D);
    this.a.getChildren().addAll((Object[])new Node[] { (Node)this.c, (Node)this.b });
    HBox$.setHgrow((Node)this.c, Priority.ALWAYS);
  }
  
  public void a(LoaderColumn paramLoaderColumn, boolean paramBoolean) {
    super.updateItem(paramLoaderColumn, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramLoaderColumn != null) {
      this.c.getStyleClass().remove("combo-error");
      this.c.setOnAction(paramActionEvent -> {
          
          });
      this.c.getItems().clear();
      this.c.getItems().add(null);
      if (!this.d.f.g())
        for (Column column : this.d.f.d.columns)
          this.c.getItems().add(column);  
      if (paramLoaderColumn.a() != null) {
        if (!this.c.getItems().contains(paramLoaderColumn.a()))
          this.c.getItems().add(paramLoaderColumn.a()); 
        this.c.getSelectionModel().select(paramLoaderColumn.a());
      } 
      if (paramLoaderColumn.h())
        this.c.setValue(null); 
      this.c.setOnAction(paramActionEvent -> {
            paramLoaderColumn.c((this.c.getValue() == null));
            if (this.c.getValue() != null)
              paramLoaderColumn.a((Column)this.c.getValue()); 
            this.d.f.i();
            this.d.e.refresh();
          });
      if (paramLoaderColumn.g())
        this.c.getStyleClass().add("combo-error"); 
      this.c.setButtonCell(new b());
      this.c.setCellFactory(paramListView -> new b());
      this.b.setOnAction(paramActionEvent -> {
            (new FxColumnEditor(FxLoaderDialog.a(this.d), paramLoaderColumn.a(), false)).showDialog();
            this.d.e.refresh();
          });
      this.b.setDisable(paramLoaderColumn.h());
      setGraphic((Node)this.a);
    } 
  }
}
