package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.fx.ToolTipFactory;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;

@DoNotObfuscate
public class VirtualForeignKeySuggestion {
  public final String query;
  
  public final ForeignKey foreignKey;
  
  private final String a;
  
  private boolean b;
  
  public VirtualForeignKeySuggestion(String paramString, ForeignKey paramForeignKey, boolean paramBoolean) {
    this(paramString, paramForeignKey, null, paramBoolean);
  }
  
  public VirtualForeignKeySuggestion(String paramString1, ForeignKey paramForeignKey, String paramString2, boolean paramBoolean) {
    this.query = paramString1;
    this.foreignKey = paramForeignKey;
    this.b = (paramBoolean && paramForeignKey != null);
    this.a = paramString2;
  }
  
  public boolean isSelected() {
    return this.b;
  }
  
  public void setSelected(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public String getFromTableString() {
    if (this.foreignKey != null) {
      StringBuilder stringBuilder = new StringBuilder(this.foreignKey.getEntity().ref());
      stringBuilder.append(" (");
      for (byte b = 0; b < this.foreignKey.getSourceAttributes().size(); b++) {
        if (b > 0)
          stringBuilder.append(", "); 
        stringBuilder.append(this.foreignKey.getSourceAttributes().get(b));
      } 
      stringBuilder.append(")");
      return stringBuilder.toString();
    } 
    return "-";
  }
  
  public String getToTableString() {
    if (this.foreignKey != null) {
      StringBuilder stringBuilder = new StringBuilder(this.foreignKey.getTargetEntity().ref());
      stringBuilder.append(" (");
      for (byte b = 0; b < this.foreignKey.getTargetAttributes().size(); b++) {
        if (b > 0)
          stringBuilder.append(", "); 
        stringBuilder.append(this.foreignKey.getTargetAttributes().get(b));
      } 
      stringBuilder.append(")");
      return stringBuilder.toString();
    } 
    return "-";
  }
  
  public String getQuery() {
    return (this.query != null) ? StringUtil.removeNewLine(StringUtil.cutOfWithDots(this.query, 150), false) : null;
  }
  
  public String getErrorString() {
    return (this.a != null) ? StringUtil.cutOfWithDots(this.a, 150) : null;
  }
  
  public VirtualForeignKeySuggestion$Status getStatus() {
    if (this.a != null)
      return VirtualForeignKeySuggestion$Status.c; 
    if (this.foreignKey != null)
      return VirtualForeignKeySuggestion$Status.a; 
    return VirtualForeignKeySuggestion$Status.b;
  }
  
  public String toString() {
    return ToolTipFactory.a(this.foreignKey);
  }
}
