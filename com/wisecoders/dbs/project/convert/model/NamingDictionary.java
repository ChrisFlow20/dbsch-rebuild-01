package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.project.convert.store.NamingDictionaryLoader;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class NamingDictionary {
  public static final String a = "1.1";
  
  public static final String b = "NamingDictionary";
  
  public static NamingDictionary c = new NamingDictionary();
  
  private boolean e = true;
  
  private NamingDictionary$Separator f = NamingDictionary$Separator.c;
  
  private NamingDictionary$Separator g = NamingDictionary$Separator.a;
  
  public final ObservableList d = FXCollections.observableArrayList();
  
  private static final String h = "(?i)";
  
  public NamingDictionary() {
    new NamingDictionaryLoader(this, c());
  }
  
  public NamingDictionary$Separator a() {
    return this.g;
  }
  
  public void a(NamingDictionary$Separator paramNamingDictionary$Separator) {
    this.g = paramNamingDictionary$Separator;
  }
  
  public NamingDictionary$Separator b() {
    return this.f;
  }
  
  public void b(NamingDictionary$Separator paramNamingDictionary$Separator) {
    this.f = paramNamingDictionary$Separator;
  }
  
  public NameConverter a(String paramString1, String paramString2) {
    for (NameConverter nameConverter1 : this.d) {
      if (paramString1.equalsIgnoreCase(nameConverter1.a())) {
        nameConverter1.b(paramString2);
        return nameConverter1;
      } 
    } 
    NameConverter nameConverter = new NameConverter(paramString1, paramString2);
    this.d.add(nameConverter);
    return nameConverter;
  }
  
  public static File c() {
    File file = Sys.a().resolve(StringUtil.escapeForFileName("LogicalDesign")).toFile();
    if (!file.exists())
      file.mkdirs(); 
    return Sys.a().resolve(StringUtil.escapeForFileName("LogicalDesign") + "/NamingDictionary1.1.xml").toFile();
  }
  
  public void a(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean d() {
    return this.e;
  }
  
  public void a(Project paramProject, boolean paramBoolean) {
    for (Schema schema : paramProject.schemas) {
      a(schema, paramBoolean);
      for (Table table : schema.tables) {
        a(table, paramBoolean);
        for (Column column : table.columns)
          a(column, paramBoolean); 
        for (Index index : table.indexes)
          a(index, paramBoolean); 
        for (Constraint constraint : table.constraints)
          a(constraint, paramBoolean); 
        for (ForeignKey foreignKey : table.foreignKeys)
          a(foreignKey, paramBoolean); 
      } 
    } 
  }
  
  public void a(Project paramProject) {
    LetterCase letterCase = Dbms.get(paramProject.getDbId()).getLetterCases();
    if (this.e && letterCase != LetterCase.d)
      a(paramProject, letterCase); 
  }
  
  private void a(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    paramAbstractUnit.rename(paramBoolean ? a(paramAbstractUnit.getName()) : b(paramAbstractUnit.getName()));
  }
  
  public String a(String paramString) {
    if (paramString == null)
      return paramString; 
    ArrayList arrayList = new ArrayList((Collection<?>)this.d);
    arrayList.sort((paramNameConverter1, paramNameConverter2) -> Integer.compare(paramNameConverter2.a().length(), paramNameConverter1.a().length()));
    for (NameConverter nameConverter : arrayList) {
      if (nameConverter.a() != null && nameConverter.b() != null)
        switch (NamingDictionary$1.a[this.f.ordinal()]) {
          case 1:
            paramString = paramString.replaceAll("(?i)" + nameConverter.a(), Matcher.quoteReplacement(nameConverter.b()));
          case 2:
            paramString = paramString.replaceAll("(?i)(^|_)" + nameConverter.a() + "($|_)", "$1" + Matcher.quoteReplacement(nameConverter.b()) + "$2");
          case 3:
            paramString = paramString.replaceAll("(?i)(^|-)" + nameConverter.a() + "($|-)", "$1" + Matcher.quoteReplacement(nameConverter.b()) + "$2");
          case 4:
            paramString = paramString.replaceAll("(?i)(^|\\s)" + nameConverter.a() + "($|\\s)", "$1" + Matcher.quoteReplacement(nameConverter.b()) + "$2");
        }  
    } 
    if (b() != NamingDictionary$Separator.d && a() != NamingDictionary$Separator.d && b() != a())
      paramString = paramString.replaceAll("" + (b()).e, "" + (a()).e); 
    return paramString;
  }
  
  public String b(String paramString) {
    if (paramString == null)
      return paramString; 
    ArrayList arrayList = new ArrayList((Collection<?>)this.d);
    arrayList.sort((paramNameConverter1, paramNameConverter2) -> Integer.compare(paramNameConverter2.b().length(), paramNameConverter1.b().length()));
    for (NameConverter nameConverter : arrayList) {
      if (nameConverter.a() != null && nameConverter.b() != null)
        switch (NamingDictionary$1.a[this.g.ordinal()]) {
          case 1:
            paramString = paramString.replaceAll(nameConverter.b(), Matcher.quoteReplacement(nameConverter.a()));
          case 2:
            paramString = paramString.replaceAll("(^|_)" + nameConverter.b() + "($|_)", "$1" + Matcher.quoteReplacement(nameConverter.a()) + "$2");
          case 3:
            paramString = paramString.replaceAll("(^|-)" + nameConverter.b() + "($|-)", "$1" + Matcher.quoteReplacement(nameConverter.a()) + "$2");
          case 4:
            paramString = paramString.replaceAll("(^|\\s)" + nameConverter.b() + "($|\\s)", "$1" + Matcher.quoteReplacement(nameConverter.a()) + "$2");
        }  
    } 
    if (b() != NamingDictionary$Separator.d && a() != NamingDictionary$Separator.d && b() != a())
      paramString = paramString.replaceAll("" + (a()).e, "" + (b()).e); 
    return paramString;
  }
  
  private static void a(TreeUnit paramTreeUnit, LetterCase paramLetterCase) {
    if (paramTreeUnit instanceof com.wisecoders.dbs.schema.DbUnit)
      ((AbstractUnit)paramTreeUnit).rename((paramLetterCase == LetterCase.b) ? paramTreeUnit.getName().toUpperCase() : paramTreeUnit.getName().toLowerCase()); 
    ArrayList<TreeUnit> arrayList = new ArrayList();
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++)
      arrayList.add(paramTreeUnit.getChildAt(b)); 
    for (TreeUnit treeUnit : arrayList)
      a(treeUnit, paramLetterCase); 
  }
  
  public List c(String paramString) {
    if (paramString != null) {
      int i = c.b().a(paramString);
      ArrayList<String> arrayList = new ArrayList();
      String str1 = paramString.substring(i);
      String str2 = paramString.substring(0, i);
      for (NameConverter nameConverter : this.d) {
        if (nameConverter.a() != null && nameConverter.a().toLowerCase().startsWith(str1.toLowerCase()))
          arrayList.add(str2 + str2); 
      } 
      return arrayList;
    } 
    return null;
  }
  
  public void a(TextField paramTextField) {
    ContextMenu contextMenu = new ContextMenu();
    paramTextField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (paramTextField.isVisible() && paramTextField.getScene() != null) {
            paramContextMenu.getItems().clear();
            List list = c(paramString2);
            if (list == null || list.isEmpty()) {
              paramContextMenu.hide();
            } else {
              paramContextMenu.hide();
              for (String str : list) {
                MenuItem menuItem = new MenuItem(str);
                menuItem.setOnAction(());
                paramContextMenu.getItems().add(menuItem);
                if (paramContextMenu.getItems().size() == 60) {
                  paramContextMenu.getItems().add(new MenuItem("..."));
                  break;
                } 
              } 
              paramContextMenu.show((Node)paramTextField, Side.BOTTOM, 0.0D, 0.0D);
            } 
          } 
        });
    paramTextField.focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> paramContextMenu.hide());
  }
}
