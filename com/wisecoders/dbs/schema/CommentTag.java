package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import javafx.beans.property.SimpleBooleanProperty;

@DoNotObfuscate
public class CommentTag {
  public static final String PROJECT = "p";
  
  public static final String SCHEMA = "s";
  
  public static final String ENTITY = "e";
  
  public static final String FIELD = "f";
  
  public static final String RELATION = "r";
  
  public static final String INDEX = "i";
  
  public final SimpleBooleanProperty isForProject = new SimpleBooleanProperty();
  
  public final SimpleBooleanProperty isForSubjectArea = new SimpleBooleanProperty();
  
  public final SimpleBooleanProperty isForEntity = new SimpleBooleanProperty();
  
  public final SimpleBooleanProperty isForField = new SimpleBooleanProperty();
  
  public final SimpleBooleanProperty isForIndex = new SimpleBooleanProperty();
  
  public final SimpleBooleanProperty isForRelation = new SimpleBooleanProperty();
  
  private boolean a = false;
  
  private boolean b = false;
  
  private final String c;
  
  private String d;
  
  public CommentTag(String paramString1, String paramString2) {
    this.c = paramString1;
    if (paramString2 != null) {
      this.isForProject.set(paramString2.contains("p"));
      this.isForSubjectArea.set(paramString2.contains("s"));
      this.isForEntity.set(paramString2.contains("e"));
      this.isForField.set(paramString2.contains("f"));
      this.isForIndex.set(paramString2.contains("i"));
      this.isForRelation.set(paramString2.contains("r"));
    } 
  }
  
  public String getName() {
    return this.c;
  }
  
  public String getAppliesTo() {
    return (this.isForProject.getValue().booleanValue() ? "p" : "") + (this.isForProject.getValue().booleanValue() ? "p" : "") + (
      this.isForSubjectArea.getValue().booleanValue() ? "s" : "") + (
      this.isForEntity.getValue().booleanValue() ? "e" : "") + (
      this.isForField.getValue().booleanValue() ? "f" : "") + (
      this.isForIndex.getValue().booleanValue() ? "i" : "");
  }
  
  public String toString() {
    return this.c;
  }
  
  public void setCommaSeparatedValuesOrGroovyScript(String paramString) {
    this.d = paramString;
  }
  
  public String getCommaSeparatedValuesOrGroovyScript() {
    return this.d;
  }
  
  public void setAutoSetOnCreate(boolean paramBoolean) {
    this.a = paramBoolean;
  }
  
  public boolean isAutoSetOnCreate() {
    return this.a;
  }
  
  public void setAutoSetOnUpdate(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public boolean isAutoSetOnUpdate() {
    return this.b;
  }
}
