package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.config.model.OptionsProperty;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@DoNotObfuscate
public class DataType extends AbstractTable {
  public static int UNSET = -9999999;
  
  public static final int JSON_MAP = 4999544;
  
  public static final int JSON_LIST = 4999545;
  
  public static final int UUID = 102;
  
  public final String dbId;
  
  public final Folder columns = new Folder("Field", this, Column.class);
  
  private int a = 12;
  
  private String b;
  
  private String c;
  
  private String d;
  
  private String e;
  
  private String f;
  
  private Precision g = Precision.NONE;
  
  private int h = -1;
  
  private int i = -1;
  
  public final OptionsProperty options = new OptionsProperty();
  
  public final List matcherPatterns;
  
  public DataType(String paramString1, String paramString2) {
    super(paramString2);
    this.matcherPatterns = new ArrayList();
    this.dbId = paramString1;
  }
  
  public DataType(String paramString1, String paramString2, int paramInt) {
    super(paramString2);
    this.matcherPatterns = new ArrayList();
    this.dbId = paramString1;
    this.a = paramInt;
  }
  
  public boolean isJsonMap() {
    return (this.a == 4999544);
  }
  
  public boolean isJsonList() {
    return (this.a == 4999545);
  }
  
  public boolean isJsonMapOrArray() {
    return (this.a == 4999544 || this.a == 4999545);
  }
  
  public void setAliases(String paramString) {
    this.b = paramString;
  }
  
  public String getAliases() {
    return this.b;
  }
  
  public String getUpdateCast() {
    return this.d;
  }
  
  public void setUpdateCast(String paramString) {
    this.d = paramString;
  }
  
  public Precision getPrecision() {
    return this.g;
  }
  
  public void setJavaType(int paramInt) {
    this.a = paramInt;
  }
  
  public int getJavaType() {
    return this.a;
  }
  
  public void setDefaultLength(int paramInt) {
    this.h = paramInt;
  }
  
  public int getDefaultLength() {
    return this.h;
  }
  
  public boolean hasDefoLegth() {
    return (this.h != -1);
  }
  
  public void setDefaultDecimal(int paramInt) {
    this.i = paramInt;
  }
  
  public int getDefaultDecimal() {
    return this.i;
  }
  
  public boolean hasDefoDecimal() {
    return (this.i != -1);
  }
  
  public void setPrecision(Precision paramPrecision) {
    this.g = paramPrecision;
  }
  
  public boolean isTimestamp() {
    return DataTypeUtil.isTimestamp(this.a);
  }
  
  public boolean isDate() {
    return DataTypeUtil.isDate(this.a);
  }
  
  public boolean isTime() {
    return DataTypeUtil.isTime(this.a);
  }
  
  public boolean isUUID() {
    return "uuid".equalsIgnoreCase(getName());
  }
  
  public boolean isInet() {
    return "inet".equalsIgnoreCase(getName());
  }
  
  public boolean isBoolean() {
    return DataTypeUtil.isBoolean(this.a);
  }
  
  public void setOptions(String paramString) {
    this.options.b(paramString);
  }
  
  public void setOptionsTitle(String paramString) {
    this.options.d(paramString);
  }
  
  public boolean isBlobOrSimilar() {
    switch (this.a) {
      case -4:
      case -3:
      case -2:
      case 2004:
      case 2005:
      
    } 
    return false;
  }
  
  public boolean isNumeric() {
    return DataTypeUtil.isNumeric(this.a);
  }
  
  public boolean isMongoDbObjectId() {
    return (("MongoDb".equals(this.dbId) && this.a == -8 && "oid".equalsIgnoreCase(getName())) || "objectId".equalsIgnoreCase(getName()));
  }
  
  public boolean isSerial() {
    return ("serial".equalsIgnoreCase(getName()) || "bigserial".equalsIgnoreCase(getName()) || "smallserial".equalsIgnoreCase(getName()));
  }
  
  public boolean isText() {
    switch (this.a) {
      case -1:
      case 12:
      
    } 
    return false;
  }
  
  public boolean isChar() {
    return (this.a == 1);
  }
  
  public boolean isArray() {
    return (this.a == 2003);
  }
  
  public void setPattern(String paramString) {
    this.c = paramString;
  }
  
  public String getPattern() {
    return this.c;
  }
  
  public String getTypeString(DataTypeFormat paramDataTypeFormat, int paramInt1, int paramInt2, String paramString1, String paramString2) {
    return a((paramDataTypeFormat == DataTypeFormat.c && this.f != null) ? this.f : getName(), paramInt1, paramInt2, paramString1, paramString2);
  }
  
  protected String a(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3) {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.c == null) {
      stringBuilder.append(paramString1);
      switch (DataType$1.a[getPrecision().ordinal()]) {
        case 2:
          if (paramInt1 != UNSET) {
            stringBuilder.append("(");
            if (paramInt1 != UNSET) {
              stringBuilder.append(paramInt1);
              if (paramInt2 != UNSET)
                stringBuilder.append(",").append(paramInt2); 
            } 
            stringBuilder.append(")");
          } 
          break;
        case 3:
        case 4:
          if (paramInt1 != UNSET) {
            stringBuilder.append("(");
            if (paramInt1 != UNSET)
              stringBuilder.append(paramInt1); 
            if ("CHAR".equalsIgnoreCase(paramString3))
              stringBuilder.append(" CHAR"); 
            stringBuilder.append(")");
          } 
          break;
        case 5:
          if (paramString2 != null)
            stringBuilder.append("(").append(paramString2).append(")"); 
          break;
      } 
    } else {
      SimpleStatement simpleStatement = (new SimpleStatement(this.c)).set(K.ap, Integer.valueOf(paramInt1)).set(K.aq, (paramInt1 != UNSET) ? Integer.valueOf(paramInt1) : "").set(K.ar, Integer.valueOf(paramInt2)).set(K.G, paramString3);
      String str = simpleStatement.toString();
      return str.endsWith(" )") ? (str.substring(0, str.length() - 2) + ")") : str;
    } 
    if (!"CHAR".equalsIgnoreCase(paramString3) && StringUtil.isFilledTrim(paramString3))
      stringBuilder.append(" ").append(paramString3); 
    return stringBuilder.toString();
  }
  
  public void refresh() {}
  
  public TreeUnit getParent() {
    return null;
  }
  
  public String getSymbolicName() {
    return "Data Type";
  }
  
  public String getSymbolicIcon() {
    switch (this.a) {
      case -16:
      case -9:
      case 1:
      case 12:
      
      case -7:
      case 16:
      
      case -6:
      case -5:
      case 2:
      case 3:
      case 4:
      case 5:
      case 7:
      case 8:
      
      case 91:
      case 92:
      case 93:
      
      case 4999544:
      case 4999545:
      
    } 
    return "data_blob.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return getSymbolicGlyph(this.a);
  }
  
  public static Glyph getSymbolicGlyph(int paramInt) {
    switch (paramInt) {
      case -9:
      case 1:
      case 12:
      
      case -16:
      case -1:
      
      case -7:
      case 16:
      
      case -6:
      case -5:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      
      case 91:
      case 92:
      case 93:
      
      case 4999544:
      case 4999545:
      
    } 
    return BootstrapIcons.database_fill;
  }
  
  public AbstractTable getEntity() {
    return this;
  }
  
  public int compareTo(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit instanceof DataType) {
      DataType dataType = (DataType)paramAbstractUnit;
      if (this.a != dataType.getJavaType())
        return (this.a < dataType.getJavaType()) ? -1 : 1; 
    } 
    return super.compareTo(paramAbstractUnit);
  }
  
  public boolean isView() {
    return false;
  }
  
  public Schema getSchema() {
    return null;
  }
  
  public Column createColumn(String paramString, DataType paramDataType) {
    return null;
  }
  
  public Folder getAttributes() {
    return this.columns;
  }
  
  public Folder getRelations() {
    return null;
  }
  
  public ForeignKey createRelation(String paramString) {
    return null;
  }
  
  public boolean sameAs(DataType paramDataType) {
    return (this == paramDataType || getName().equalsIgnoreCase(paramDataType.getName()));
  }
  
  public String getDbId() {
    return this.dbId;
  }
  
  public String getBaseTypeName() {
    String str = getName();
    while (str.endsWith("[]"))
      str = str.substring(0, str.length() - "[]".length()); 
    return str;
  }
  
  public void setRegExMatchers(String paramString) {
    this.e = paramString;
    if (paramString != null)
      for (String str : paramString.split(";")) {
        try {
          this.matcherPatterns.add(Pattern.compile(str, 2));
        } catch (Throwable throwable) {
          Log.h();
        } 
      }  
  }
  
  public String getRegExMatchers() {
    return this.e;
  }
  
  public void setGeneric(String paramString) {
    this.f = paramString;
  }
  
  public String getGeneric() {
    return this.f;
  }
}
