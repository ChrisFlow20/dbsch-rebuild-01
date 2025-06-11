package com.wisecoders.dbs.generator.engine.plan;

import com.wisecoders.dbs.generator.store.PatternLoader;
import com.wisecoders.dbs.generator.store.PatternStore;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.sys.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Domain {
  private static final ObservableList a = FXCollections.observableArrayList();
  
  static {
    if (PatternStore.a.exists()) {
      new PatternLoader(PatternStore.a);
    } else {
      new PatternLoader();
    } 
  }
  
  public static void a() {
    a.clear();
    if (PatternStore.a.exists())
      FileUtils.b(PatternStore.a); 
    new PatternLoader();
  }
  
  public static Category a(String paramString) {
    for (Category category1 : a) {
      if (category1.a.equalsIgnoreCase(paramString))
        return category1; 
    } 
    Category category = new Category(paramString);
    a.add(category);
    return category;
  }
  
  public static void a(Category paramCategory) {
    a.remove(paramCategory);
  }
  
  public static ObservableList b() {
    return a;
  }
  
  public static DefinedPattern b(String paramString) {
    for (Category category : a) {
      for (DefinedPattern definedPattern : category.a()) {
        if (definedPattern.b().equalsIgnoreCase(paramString))
          return definedPattern; 
      } 
    } 
    return null;
  }
  
  public static DefinedPattern a(Column paramColumn) {
    DefinedPattern definedPattern = null;
    int i = -1;
    for (Category category : b()) {
      for (DefinedPattern definedPattern1 : category.a()) {
        for (PatternMatcher patternMatcher : definedPattern1.a()) {
          int j = patternMatcher.a(paramColumn);
          if (j > i) {
            i = j;
            definedPattern = definedPattern1;
          } 
        } 
      } 
    } 
    return definedPattern;
  }
}
