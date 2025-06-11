package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javafx.scene.control.TreeItem;

class j extends TreeItem {
  protected final List b = new ArrayList();
  
  public j(FxGridEditor paramFxGridEditor, AbstractUnit paramAbstractUnit) {
    super(paramAbstractUnit);
  }
  
  public void b(Class<Column> paramClass) {
    this.b.removeIf(paramj -> (paramj.getValue() != null && ((AbstractUnit)paramj.getValue()).isMarkedForDeletion()));
    Object object = getValue();
    if (object instanceof Table) {
      Table table = (Table)object;
      if (this.b.isEmpty())
        setExpanded(true); 
      if (paramClass == Column.class) {
        for (byte b = 0; b < table.columns.size(); b++) {
          Column column = (Column)table.columns.get(b);
          if (b >= this.b.size() || ((j)this.b.get(b)).getValue() != column)
            this.b.add(b, new j(this.c, column)); 
        } 
        while (this.b.size() > table.columns.size())
          this.b.remove(table.columns.size()); 
      } else if (paramClass == ForeignKey.class) {
        for (byte b = 0; b < table.foreignKeys.size(); b++) {
          ForeignKey foreignKey = (ForeignKey)table.foreignKeys.get(b);
          if (b >= this.b.size() || ((j)this.b.get(b)).getValue() != foreignKey)
            this.b.add(b, new j(this.c, foreignKey)); 
        } 
        while (this.b.size() > table.foreignKeys.size())
          this.b.remove(table.foreignKeys.size()); 
      } else if (paramClass == Index.class) {
        for (byte b = 0; b < table.indexes.size(); b++) {
          Index index = (Index)table.indexes.get(b);
          if (b >= this.b.size() || ((j)this.b.get(b)).getValue() != index)
            this.b.add(b, new j(this.c, index)); 
        } 
        while (this.b.size() > table.indexes.size())
          this.b.remove(table.indexes.size()); 
      } 
    } else {
      object = getValue();
      if (object instanceof Column) {
        Column column = (Column)object;
        if (column.hasChildEntity() && 
          paramClass == Column.class) {
          for (byte b = 0; b < (column.getChildEntity()).columns.size(); b++) {
            Column column1 = (Column)(column.getChildEntity()).columns.get(b);
            if (b >= this.b.size() || ((j)this.b.get(b)).getValue() != column1)
              this.b.add(b, new j(this.c, column1)); 
          } 
          while (this.b.size() > (column.getChildEntity()).columns.size())
            this.b.remove((column.getChildEntity()).columns.size()); 
        } 
      } 
    } 
    for (TreeItem treeItem : this.b) {
      if (treeItem instanceof j) {
        object = treeItem;
        object.b(paramClass);
      } 
    } 
    e();
  }
  
  public void e() {
    getChildren().retainAll(this.b);
    byte b = 0;
    for (j j1 : this.b) {
      if (a((TreeUnit)j1.getValue())) {
        if (!getChildren().contains(j1))
          getChildren().add(b, j1); 
        b++;
        j1.e();
        continue;
      } 
      getChildren().remove(j1);
    } 
  }
  
  private boolean a(TreeUnit paramTreeUnit) {
    if (StringUtil.isEmptyTrim(this.c.F))
      return true; 
    if (paramTreeUnit instanceof AbstractUnit && 
      a((AbstractUnit)paramTreeUnit))
      return true; 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++) {
      TreeUnit treeUnit = paramTreeUnit.getChildAt(b);
      if (a(treeUnit))
        return true; 
    } 
    return false;
  }
  
  private boolean a(AbstractUnit paramAbstractUnit) {
    if (this.c.G)
      try {
        Pattern pattern = Pattern.compile(this.c.F, 2);
        return (pattern.matcher(paramAbstractUnit.getName()).find() || (paramAbstractUnit
          .getComment() != null && pattern
          .matcher(paramAbstractUnit.getComment()).find()));
      } catch (PatternSyntaxException patternSyntaxException) {
        return false;
      }  
    return (paramAbstractUnit.getName().toLowerCase().contains(this.c.F) || (paramAbstractUnit
      .getComment() != null && paramAbstractUnit
      .getComment().toLowerCase().contains(this.c.F)));
  }
  
  public List a(String paramString, Class paramClass, List<j> paramList) {
    if (getValue() != null && paramClass.isAssignableFrom(((AbstractUnit)getValue()).getClass()) && StringUtil.areEqual(paramString, ((AbstractUnit)getValue()).getName()))
      paramList.add(this); 
    for (TreeItem treeItem : getChildren())
      ((j)treeItem).a(paramString, paramClass, paramList); 
    return paramList;
  }
}
