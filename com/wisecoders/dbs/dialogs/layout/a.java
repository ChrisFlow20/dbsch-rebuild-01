package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;

class a extends ListCell {
  private final Tooltip b = new Tooltip();
  
  private a(FxColumnEditor paramFxColumnEditor) {}
  
  protected void updateItem(Object paramObject, boolean paramBoolean) {
    super.updateItem(paramObject, paramBoolean);
    setGraphic(null);
    setText(null);
    setStyle("");
    setTooltip(null);
    if (!paramBoolean) {
      setText(paramObject.toString());
      if (paramObject instanceof UserDataType) {
        UserDataType userDataType = (UserDataType)paramObject;
        setText(userDataType.ref());
        setGraphic((Node)userDataType.getSymbolicGlyph().glyph(new String[] { "glyph-orange" }));
      } else if (paramObject instanceof DataType) {
        DataType dataType = (DataType)paramObject;
        setGraphic((Node)dataType.getSymbolicGlyph().glyph(new String[] { "font-small", dataType.isJsonMapOrArray() ? "glyph-orange" : "glyph-gray" }));
        String str = this.a.rx.H(dataType.getName().toLowerCase());
        if (str != null) {
          this.b.setText(str);
          setTooltip(this.b);
        } 
      } else {
        setGraphic((Node)BootstrapIcons.gear.glyph(new String[0]));
        setStyle("-fx-font-style:italic;");
      } 
    } 
  }
}
