package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class a extends ListCell {
  private a() {
    getStyleClass().addAll((Object[])new String[] { "font-xl" });
    setGraphicTextGap(15.0D);
  }
  
  protected void a(String paramString, boolean paramBoolean) {
    super.updateItem(paramString, paramBoolean);
    setGraphic(null);
    setText(null);
    if (paramString != null && !paramBoolean) {
      setText(paramString);
      setStyle(FxChooseDbmsDialog.i.contains(paramString) ? "-fx-font-weight: bold;" : null);
      Image image = Rx.d("dbms/" + paramString.toLowerCase() + ".png");
      if (image == null)
        image = Rx.c("dbms/db.png"); 
      setGraphic((Node)new ImageView(image));
    } 
  }
}
