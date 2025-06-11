package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.fx.ToolTipFactory;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.PropertyAddOnFolder;
import com.wisecoders.dbs.diagram.model.ScriptAddOnFolder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.schema.LayoutDepict;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;

public class FxTreeCell extends TreeCell {
  private final Tooltip a = new Tooltip();
  
  protected void a(TreeUnit paramTreeUnit, boolean paramBoolean) {
    super.updateItem(paramTreeUnit, paramBoolean);
    setGraphic(null);
    setText(null);
    setTooltip(null);
    if (!paramBoolean)
      if (paramTreeUnit instanceof PropertyAddOnFolder) {
        PropertyAddOnFolder propertyAddOnFolder = (PropertyAddOnFolder)paramTreeUnit;
        setText(propertyAddOnFolder.getName());
        setGraphic((Node)Rx.q(((TreeItem)treeItemProperty().getValue()).isExpanded() ? "folder-addon-expanded.png" : "folder-addon.png"));
      } else if (paramTreeUnit instanceof ScriptAddOnFolder) {
        ScriptAddOnFolder scriptAddOnFolder = (ScriptAddOnFolder)paramTreeUnit;
        setText(scriptAddOnFolder.getName());
        setGraphic((Node)Rx.q(((TreeItem)treeItemProperty().getValue()).isExpanded() ? "folder-addon-expanded.png" : "folder-addon.png"));
      } else if (paramTreeUnit instanceof Folder) {
        Folder folder = (Folder)paramTreeUnit;
        setText(folder.getName() + " (" + folder.getName() + ")");
        setGraphic((Node)Rx.q(((TreeItem)treeItemProperty().getValue()).isExpanded() ? "folder-expanded.png" : "folder.png"));
      } else if (paramTreeUnit instanceof AbstractUnit) {
        AbstractUnit abstractUnit = (AbstractUnit)paramTreeUnit;
        LayoutDepict layoutDepict = (LayoutDepict)abstractUnit;
        setText((abstractUnit instanceof LayoutDepict) ? layoutDepict.a() : abstractUnit.toString());
        this.a.setText(ToolTipFactory.a(abstractUnit));
        setTooltip(this.a);
        if (abstractUnit.isVirtual()) {
          setGraphic((Node)Rx.q("virtual.png"));
        } else if (abstractUnit.getSymbolicIcon() == null) {
          setGraphic((Node)abstractUnit.getSymbolicGlyph().glyph(new String[0]));
        } else {
          setGraphic((Node)Rx.q(abstractUnit.getSymbolicIcon()));
        } 
        if (paramTreeUnit instanceof Schema) {
          Schema schema = (Schema)paramTreeUnit;
          if (!(Dbms.get(schema.getDbId())).prefixSchemaWithCatalog.b() && !schema.project.hasSchemesWithSameName())
            setText(schema.getName()); 
          if (StringUtil.isEmptyTrim(getText()))
            setText("--Default--"); 
        } 
      }  
  }
}
