package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;
import javafx.scene.image.ImageView;

public class FxTreeTableCell extends TreeTableCell {
  private final boolean a;
  
  public FxTreeTableCell(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  protected void a(TreeUnit paramTreeUnit, boolean paramBoolean) {
    super.updateItem(paramTreeUnit, paramBoolean);
    getStylesheets().clear();
    setGraphic(null);
    setText(null);
    if (!paramBoolean)
      if (paramTreeUnit instanceof Folder) {
        Folder folder = (Folder)paramTreeUnit;
        setText(this.a ? (folder.getName() + " (" + folder.getName() + ")") : folder.getName());
        setGraphic((Node)new ImageView(Rx.c("folder.png")));
      } else if (paramTreeUnit instanceof AbstractUnit) {
        AbstractUnit abstractUnit = (AbstractUnit)paramTreeUnit;
        setText(abstractUnit.getName());
        if (abstractUnit.getSymbolicIcon() != null) {
          setGraphic((Node)new ImageView(Rx.c(abstractUnit.getSymbolicIcon())));
        } else if (abstractUnit.getSymbolicGlyph() != null) {
          setGraphic((Node)abstractUnit.getSymbolicGlyph().glyph(new String[0]));
        } 
      }  
  }
}
