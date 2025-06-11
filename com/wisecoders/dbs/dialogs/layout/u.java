package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

class u extends ListCell {
  protected void a(Entity paramEntity, boolean paramBoolean) {
    super.updateItem(paramEntity, paramBoolean);
    setText(null);
    setGraphic(null);
    if (paramEntity != null && !paramBoolean) {
      setText(paramEntity.getNameWithSchemaName());
      if (paramEntity instanceof AbstractUnit) {
        AbstractUnit abstractUnit = (AbstractUnit)paramEntity;
        if (abstractUnit.getSymbolicIcon() != null) {
          setGraphic((Node)new ImageView(Rx.c(abstractUnit.getSymbolicIcon())));
        } else if (abstractUnit.getSymbolicGlyph() != null) {
          setGraphic((Node)abstractUnit.getSymbolicGlyph().glyph(new String[0]));
        } 
      } 
    } 
  }
}
