package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.TableCell;

class a extends TableCell {
  protected void a(GeneratorTable paramGeneratorTable, boolean paramBoolean) {
    super.updateItem(paramGeneratorTable, paramBoolean);
    setText(null);
    setGraphic(null);
    if (!paramBoolean && paramGeneratorTable != null)
      switch (FxGeneratorEditor$7.a[paramGeneratorTable.getStatus().ordinal()]) {
        case 2:
          setText("Dropping Data...");
          break;
        case 3:
          setText("Generating... Succeed " + paramGeneratorTable.getSucceedCount() + " failed " + paramGeneratorTable.getFailedCount());
          break;
        case 4:
          if (paramGeneratorTable.getSucceedCount() > 0 && paramGeneratorTable.getFailedCount() == 0) {
            setText("Succeed " + paramGeneratorTable.getSucceedCount());
            setGraphic((Node)BootstrapIcons.check2_circle.glyph(new String[] { "glyph-green" }));
            break;
          } 
          if (paramGeneratorTable.getSucceedCount() == 0 && paramGeneratorTable.getFailedCount() > 0) {
            setText("Failed. " + paramGeneratorTable.getFailedCount() + " errors.");
            setGraphic((Node)BootstrapIcons.exclamation_circle_fill.glyph(new String[] { "glyph-red" }));
            break;
          } 
          setText("Succeed " + paramGeneratorTable.getSucceedCount() + " failed " + paramGeneratorTable.getFailedCount());
          setGraphic((Node)BootstrapIcons.check_circle_fill.glyph(new String[] { "glyph-orange" }));
          break;
      }  
  }
}
