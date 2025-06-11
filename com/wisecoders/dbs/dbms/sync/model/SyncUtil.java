package com.wisecoders.dbs.dbms.sync.model;

import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncSide;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.parser.MatcherCommaPhrase;
import com.wisecoders.dbs.sql.parser.MatcherStatement;
import com.wisecoders.dbs.sql.parser.PatternPhrase;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;

public class SyncUtil {
  public static List a(List paramList) {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    if (paramList != null)
      for (Unit unit : paramList) {
        if (unit instanceof Layout) {
          for (Depict depict : ((Layout)unit).getDepicts())
            uniqueArrayList.add(depict.getEntity()); 
          continue;
        } 
        if (unit.getEntity() instanceof com.wisecoders.dbs.diagram.model.AbstractTable) {
          uniqueArrayList.add(unit.getEntity());
          continue;
        } 
        if (unit instanceof TreeUnit)
          uniqueArrayList.add((TreeUnit)unit); 
      }  
    return uniqueArrayList;
  }
  
  public static Schema a(TreeUnit paramTreeUnit) {
    TreeUnit treeUnit = paramTreeUnit;
    while (treeUnit != null) {
      if (treeUnit instanceof Schema)
        return (Schema)treeUnit; 
      treeUnit = treeUnit.getParent();
    } 
    return null;
  }
  
  public static void a(Project paramProject, TreeSelection paramTreeSelection) {
    for (Schema schema : paramProject.schemas) {
      if (paramTreeSelection.hasSelectedChildren(schema)) {
        for (Table table : schema.tables) {
          if (!paramTreeSelection.isSelected(table))
            table.markForDeletion(); 
        } 
        for (View view : schema.views) {
          if (!paramTreeSelection.isSelected(view))
            view.markForDeletion(); 
        } 
        for (Procedure procedure : schema.procedures) {
          if (!paramTreeSelection.isSelected(procedure))
            procedure.markForDeletion(); 
        } 
        for (Function function : schema.functions) {
          if (!paramTreeSelection.isSelected(function))
            function.markForDeletion(); 
        } 
        for (Trigger trigger : schema.triggers) {
          if (!paramTreeSelection.isSelected(trigger))
            trigger.markForDeletion(); 
        } 
        for (Sequence sequence : schema.sequences) {
          if (!paramTreeSelection.isSelected(sequence))
            sequence.markForDeletion(); 
        } 
        continue;
      } 
      schema.markForDeletion();
    } 
    paramProject.refresh();
  }
  
  public static boolean a(TreeUnit paramTreeUnit1, TreeUnit paramTreeUnit2) {
    TreeUnit treeUnit = paramTreeUnit2;
    while (true) {
      if (treeUnit == paramTreeUnit1)
        return true; 
      if ((treeUnit = treeUnit.getParent()) == null)
        return false; 
    } 
  }
  
  public static String b(TreeUnit paramTreeUnit) {
    StringBuilder stringBuilder = new StringBuilder();
    TreeUnit treeUnit = paramTreeUnit;
    while (treeUnit != null) {
      if (!stringBuilder.isEmpty())
        stringBuilder.insert(0, "."); 
      stringBuilder.insert(0, treeUnit.toString());
      treeUnit = treeUnit.getParent();
    } 
    return stringBuilder.toString();
  }
  
  public static boolean a(Table paramTable1, Table paramTable2) {
    for (ForeignKey foreignKey : paramTable1.foreignKeys) {
      if (foreignKey.getTargetEntity() == paramTable2)
        return true; 
    } 
    for (ForeignKey foreignKey : paramTable2.foreignKeys) {
      if (foreignKey.getTargetEntity() == paramTable1)
        return true; 
    } 
    return false;
  }
  
  public static void a(WorkspaceWindow paramWorkspaceWindow, Table paramTable) {
    Schema schema = null;
    if (paramTable.schema.project.schemas.size() == 1) {
      schema = paramTable.schema;
    } else {
      ChoiceDialog choiceDialog = new ChoiceDialog(null, paramTable.schema.project.schemas);
      choiceDialog.initOwner(paramWorkspaceWindow.getDialogScene().getWindow());
      paramWorkspaceWindow.getRx().a((Dialog)choiceDialog, "chooseSchemaDialog");
      Optional<Schema> optional = choiceDialog.showAndWait();
      if (optional.isPresent())
        schema = optional.get(); 
    } 
    if (schema != null) {
      Table table = a(paramWorkspaceWindow, schema, paramTable, false);
      try {
        SyncPair syncPair = new SyncPair(paramTable.getSchema(), null, table.getSchema(), table, false, paramWorkspaceWindow.getWorkspace().r() ? (paramWorkspaceWindow.getWorkspace().s()).mapping : null);
        syncPair.commitIntoDatabase(paramWorkspaceWindow.getWorkspace().s(), SyncSide.right, null);
      } catch (Exception exception) {
        Log.a("Error by clone unit", exception);
        table.markForDeletion();
        paramWorkspaceWindow.getRx().b(paramWorkspaceWindow.getDialogScene(), exception.toString(), exception);
      } 
      paramWorkspaceWindow.getWorkspace().y();
    } 
  }
  
  public static Table a(WorkspaceWindow paramWorkspaceWindow, Schema paramSchema, Table paramTable, boolean paramBoolean) {
    Table table = paramSchema.cloneTable(paramTable, paramBoolean);
    if (paramWorkspaceWindow.getWorkspace().o() != null) {
      Depict depict1 = (paramWorkspaceWindow.getWorkspace().o()).c.a.getDepictFor(paramTable);
      Depict depict2 = (paramWorkspaceWindow.getWorkspace().o()).c.a(table);
      if (depict1 != null)
        depict2.setLocation(depict1.getPosition().a(), depict1.getPosition().b()); 
      (paramWorkspaceWindow.getWorkspace().o()).c.k();
    } 
    return table;
  }
  
  public static boolean a(String paramString1, String paramString2) {
    if (paramString1 == null)
      return (paramString2 == null); 
    if (paramString2 == null)
      return false; 
    paramString1 = StringUtil.removeSpaces(paramString1).replaceAll("\\(1,1\\)", "");
    paramString2 = StringUtil.removeSpaces(paramString2).replaceAll("\\(1,1\\)", "");
    return paramString1.equalsIgnoreCase(paramString2);
  }
  
  public static Table a(Project paramProject, String paramString1, String paramString2) {
    MatcherCommaPhrase matcherCommaPhrase = (new MatcherStatement(paramString2, paramProject.getDbId())).i();
    PatternPhrase patternPhrase;
    if ((patternPhrase = matcherCommaPhrase.b("?.?.?")) != null) {
      Schema schema = paramProject.getSchema(patternPhrase.f(0), patternPhrase.f(1));
      if (schema != null)
        return schema.getTable(patternPhrase.f(2)); 
    } else if ((patternPhrase = matcherCommaPhrase.b("?.?")) != null) {
      Schema schema = paramProject.getSchema(paramString1, patternPhrase.f(0));
      if (schema != null)
        return schema.getTable(patternPhrase.f(1)); 
    } else if ((patternPhrase = matcherCommaPhrase.b("?")) != null) {
      String str = patternPhrase.f(0);
      String[] arrayOfString = str.split("\\.");
      if (arrayOfString.length == 3) {
        Schema schema = paramProject.getSchema(arrayOfString[0], arrayOfString[1]);
        if (schema != null)
          return schema.getTable(arrayOfString[2]); 
      } else if (arrayOfString.length == 2) {
        Schema schema = paramProject.getSchema(paramString1, arrayOfString[0]);
        if (schema != null)
          return schema.getTable(arrayOfString[1]); 
      } else if (paramProject.getInUseSchema() != null) {
        return paramProject.getInUseSchema().getTable(patternPhrase.f(0));
      } 
    } 
    return null;
  }
  
  public static String b(List paramList) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (Object object : paramList) {
      stringBuilder.append(bool ? ", " : "").append(object);
      bool = true;
    } 
    return stringBuilder.toString();
  }
  
  public static boolean c(TreeUnit paramTreeUnit) {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull -> 6
    //   4: iconst_0
    //   5: ireturn
    //   6: aload_0
    //   7: instanceof com/wisecoders/dbs/diagram/model/AbstractUnit
    //   10: ifeq -> 25
    //   13: aload_0
    //   14: checkcast com/wisecoders/dbs/diagram/model/AbstractUnit
    //   17: astore_1
    //   18: aload_1
    //   19: invokevirtual isVirtual : ()Z
    //   22: ifne -> 37
    //   25: aload_0
    //   26: invokeinterface getParent : ()Lcom/wisecoders/dbs/diagram/model/TreeUnit;
    //   31: invokestatic c : (Lcom/wisecoders/dbs/diagram/model/TreeUnit;)Z
    //   34: ifeq -> 41
    //   37: iconst_1
    //   38: goto -> 42
    //   41: iconst_0
    //   42: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #201	-> 0
    //   #202	-> 6
  }
}
