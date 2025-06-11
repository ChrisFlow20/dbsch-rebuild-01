package com.wisecoders.dbs.dialogs.system;

import java.io.File;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

class a extends ListCell {
  protected void a(File paramFile, boolean paramBoolean) {
    super.updateItem(paramFile, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramFile != null && !paramBoolean) {
      setText(paramFile.getName());
      if (paramFile.getName().endsWith(".png") || paramFile.getName().endsWith(".bmp") || paramFile.getName().endsWith(".gif") || paramFile.getName().endsWith(".jpeg")) {
        ImageView imageView = new ImageView(paramFile.toURI().toString());
        imageView.setFitHeight(120.0D);
        imageView.setPreserveRatio(true);
        setGraphic((Node)imageView);
        setGraphicTextGap(10.0D);
      } 
    } 
  }
}
