package com.wisecoders.dbs.editors.csv.model;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.text.ParseException;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

public class CsvColumn implements Attribute {
  private String C;
  
  private int D;
  
  private boolean E = false;
  
  private static final DataType F = new DataType("Groovy", "text", 12);
  
  private static final DataType G = new DataType("Groovy", "numeric", 3);
  
  private static final DataType H = new DataType("Groovy", "boolean", 16);
  
  private static final DataType I = new DataType("Groovy", "date", 91);
  
  public boolean w = true;
  
  public boolean x = true;
  
  public boolean y = false;
  
  public boolean z = true;
  
  public boolean A = false;
  
  private String[] J = B;
  
  public CsvColumn(String paramString, int paramInt) {
    this.C = paramString;
    this.D = paramInt;
  }
  
  public void a(String paramString) {
    if (StringUtil.isFilled(paramString)) {
      this.A = true;
      if (this.x && !"true".equalsIgnoreCase(paramString) && !"false".equalsIgnoreCase(paramString) && !"yes".equalsIgnoreCase(paramString) && !"no".equalsIgnoreCase(paramString))
        this.x = false; 
      if (this.w && !NumberUtils.isCreatable(paramString))
        this.w = false; 
      if (this.J != null)
        this.J = a(paramString, this.J); 
      if (!this.y && (paramString.contains("\r") || paramString.contains("\n")))
        this.y = true; 
    } else {
      this.z = false;
    } 
  }
  
  static final String[] B = new String[] { 
      "yyyy.MM.dd G 'at' HH:mm:ss z", "EEE, MMM d, ''yy", "h:mm a", "hh 'o''clock' a, zzzz", "K:mm a, z", "yyyyy.MMMMM.dd GGG hh:mm aaa", "EEE, d MMM yyyy HH:mm:ss Z", "yyMMddHHmmssZ", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", 
      "YYYY-'W'ww-u", "EEE, dd MMM yyyy HH:mm:ss z", "EEE, dd MMM yyyy HH:mm zzzz", "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", "yyyy-MM-dd'T'HH:mm:sszzzz", "yyyy-MM-dd'T'HH:mm:ss z", "yyyy-MM-dd'T'HH:mm:ssz", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HHmmss.SSSz", 
      "yyyy-MM-dd", "yyyyMMdd", "d-MMM-yyyy", "MM/dd/yyyy", "dd/MM/yyyy", "MM/dd/yyyy", "dd/MM/yyyy", "dd/MM/yy", "dd-MM-yy" };
  
  public String[] a(String paramString, String[] paramArrayOfString) {
    try {
      DateUtils.parseDateStrictly(paramString, paramArrayOfString);
      if (paramArrayOfString.length == 1)
        return paramArrayOfString; 
      for (String str : paramArrayOfString) {
        try {
          DateUtils.parseDateStrictly(paramString, new String[] { str });
          return new String[] { str };
        } catch (ParseException parseException) {
          Log.h();
        } 
      } 
    } catch (ParseException parseException) {
      Log.h();
    } 
    return null;
  }
  
  public String getName() {
    return this.C;
  }
  
  public int a() {
    return this.D;
  }
  
  public void a(int paramInt) {
    this.D = paramInt;
  }
  
  public void b(String paramString) {
    this.C = paramString;
  }
  
  public void a(boolean paramBoolean) {
    this.E = paramBoolean;
  }
  
  public boolean b() {
    return this.E;
  }
  
  public void b(boolean paramBoolean) {
    this.y = paramBoolean;
  }
  
  public boolean c() {
    return this.y;
  }
  
  public void setTicked(boolean paramBoolean) {}
  
  public boolean isTicked() {
    return true;
  }
  
  public String getTypeString(DataTypeFormat paramDataTypeFormat) {
    return null;
  }
  
  public boolean isMandatory() {
    return this.z;
  }
  
  public boolean hasMarker(int paramInt) {
    return false;
  }
  
  public boolean isSelectable() {
    return false;
  }
  
  public short getToDoFlag() {
    return 0;
  }
  
  public DataType getDataType() {
    if (this.A) {
      if (this.x)
        return H; 
      if (this.w)
        return G; 
      if (this.J != null)
        return I; 
    } 
    return F;
  }
  
  public String d() {
    return (this.J != null && this.J.length > 0) ? this.J[0] : null;
  }
  
  public String getIndexingIcon() {
    return null;
  }
  
  public Sequence getAssociatedSequence() {
    return null;
  }
  
  public AttributeSpec getSpec() {
    return null;
  }
  
  public Entity getEntity() {
    return null;
  }
  
  public Entity getParentEntity() {
    return null;
  }
  
  public String getSymbolicName() {
    return null;
  }
  
  public void markForDeletion() {
    this.E = true;
  }
  
  public boolean isMarkedForDeletion() {
    return this.E;
  }
  
  public void setComment(String paramString) {}
  
  public String getComment() {
    return null;
  }
  
  public Boolean is(UnitProperty paramUnitProperty) {
    return null;
  }
  
  public String toString() {
    return this.C;
  }
  
  public Object c(String paramString) {
    if (paramString != null) {
      try {
        if ("".equals(paramString))
          return null; 
        if (this.A && this.x)
          return Boolean.valueOf(Boolean.parseBoolean(paramString)); 
        if (this.A && this.w)
          return Double.valueOf(Double.parseDouble(paramString)); 
        if (this.A && d() != null)
          return DateUtils.parseDateStrictly(paramString, new String[] { d() }); 
      } catch (Exception exception) {
        Log.b(exception);
      } 
      return paramString;
    } 
    return null;
  }
  
  public String e() {
    return "c" + this.D;
  }
  
  public String ref() {
    return this.C;
  }
  
  public Map getCommentTags() {
    return null;
  }
  
  public void setCommentTags(Map paramMap) {}
  
  public void setCommentTag(String paramString1, String paramString2) {}
  
  public String getCommentTag(String paramString) {
    return null;
  }
  
  public String getDisplayName(Diagram paramDiagram) {
    return getName();
  }
  
  public String getNameWithPath() {
    return getName();
  }
}
