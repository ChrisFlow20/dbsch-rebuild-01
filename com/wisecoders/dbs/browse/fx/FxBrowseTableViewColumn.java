package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.data.fx.FxTableViewCell;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.CombinedImage;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FxUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class FxBrowseTableViewColumn extends TableColumn {
  private final FxBrowseTableView c;
  
  public final ResultColumn a;
  
  private boolean d = false;
  
  public static final String b = "browseVisible";
  
  FxBrowseTableViewColumn(FxBrowseTableView paramFxBrowseTableView, ResultColumn paramResultColumn) {
    super("");
    this.c = paramFxBrowseTableView;
    this.a = paramResultColumn;
    setEditable(true);
    setSortable(false);
    setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(((ResultRow)paramCellDataFeatures.getValue()).a(paramResultColumn)));
    setCellFactory(paramTableColumn -> new FxTableViewCell(paramResultColumn, true));
    setUserData(paramResultColumn);
    setMaxWidth(800.0D);
    a(true);
    widthProperty().addListener((paramObservableValue, paramNumber1, paramNumber2) -> {
          if (this.d)
            FxUtil.a(this); 
        });
    a();
    b();
  }
  
  public void a(boolean paramBoolean) {
    this.d = false;
    FxUtil.a(this.c, this, this.a, paramBoolean);
    this.d = true;
  }
  
  private void b() {
    setVisible(Pref.b("browseVisible" + this.a.b(), true));
  }
  
  void a() {
    Attribute attribute = this.a.a();
    if (attribute != null) {
      CombinedImage combinedImage = new CombinedImage(new javafx.scene.image.Image[0]);
      if (this.c.d.a(attribute)) {
        combinedImage.a(Rx.c("browse_filters.png"));
      } else if (attribute.hasMarker(1)) {
        combinedImage.a(Rx.c("marker_pk.png"));
      } else if (attribute.hasMarker(8) && attribute.hasMarker(16)) {
        combinedImage.a(Rx.c("marker_unq.png"));
      } else if (attribute.hasMarker(4) && attribute.hasMarker(16)) {
        combinedImage.a(Rx.c("marker_idx.png"));
      } 
      int i = this.c.d.e(attribute);
      if (i == 1) {
        combinedImage.a(Rx.c("order_asc.png"));
      } else if (i == -1) {
        combinedImage.a(Rx.c("order_desc.png"));
      } 
      Label label = new Label(attribute.getName(), (Node)new ImageView(combinedImage.b()));
      label.setTooltip(new Tooltip(attribute.getName()));
      label.setPrefWidth(2.147483647E9D);
      addEventHandler(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> {
            if (MouseButton.PRIMARY.equals(paramMouseEvent.getButton()) && !paramMouseEvent.isShortcutDown())
              this.c.b(this); 
          });
      label.setOnMouseClicked(paramMouseEvent -> {
            this.c.a(this);
            if (MouseButton.PRIMARY.equals(paramMouseEvent.getButton()) && !paramMouseEvent.isShortcutDown())
              this.c.b(this); 
          });
      setGraphic((Node)label);
    } 
  }
}
