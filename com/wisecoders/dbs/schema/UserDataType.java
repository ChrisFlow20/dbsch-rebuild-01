package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class UserDataType extends DataType implements DbUnit {
  public final Schema schema;
  
  private String a;
  
  public final Folder relations = new Folder("Relations", this, ChildEntityRelation.class);
  
  public final Folder subTypes = new Folder("SubType", this, Column.class);
  
  private final Folder[] b;
  
  public UserDataType(Schema paramSchema, String paramString) {
    super(paramSchema.project.getDbId(), paramString);
    this.schema = paramSchema;
    (new Folder[1])[0] = this.subTypes;
    this.b = paramSchema.is(UnitProperty.g).booleanValue() ? new Folder[1] : null;
  }
  
  public String getUpdateCast() {
    String str = (Dbms.get(this.schema.getDbId())).userDataUpdateCast.c_();
    return (str != null) ? str.replaceAll("\\$\\{name}", getName()) : null;
  }
  
  public void setScript(String paramString) {
    this.a = paramString;
  }
  
  public String getScript() {
    return this.a;
  }
  
  private static final Pattern c = Pattern.compile("\\s*create\\s+type\\s+\\S*\\s+(.*)\\s*", 42);
  
  public void removeCreateTypeFromScript() {
    if (this.a != null) {
      Matcher matcher = c.matcher(this.a);
      if (matcher.matches())
        this.a = matcher.group(1); 
    } 
  }
  
  public TreeUnit getParent() {
    return this.schema;
  }
  
  public Column createColumn(String paramString, DataType paramDataType) {
    Column column = new Column(this, paramString, paramDataType);
    this.columns.add(column);
    return column;
  }
  
  public void refresh() {
    if (isMarkedForDeletion())
      for (Column column : this.columns)
        column.markForDeletion();  
    this.columns.refresh();
    ArrayList<Column> arrayList = new ArrayList();
    for (Column column : this.columns) {
      if (column.hasChildEntity())
        arrayList.add(column); 
    } 
    this.subTypes.retainAll(arrayList);
    this.subTypes.addAllAbsent((Collection)arrayList);
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.person;
  }
  
  public String getTypeString(DataTypeFormat paramDataTypeFormat, int paramInt1, int paramInt2, String paramString1, String paramString2) {
    if (paramDataTypeFormat == DataTypeFormat.a && isVirtual() && StringUtil.isFilledTrim(this.a))
      return a(this.a, paramInt1, paramInt2, paramString1, paramString2); 
    return a(ref(), paramInt1, paramInt2, paramString1, paramString2);
  }
  
  public String getDbId() {
    return this.schema.getDbId();
  }
  
  public Schema getSchema() {
    return this.schema;
  }
  
  public Folder getRelations() {
    return this.relations;
  }
  
  public String ref() {
    return (getSchema() != null && (Dbms.get(this.schema.project.getDbId())).prefixTableWithSchema.b()) ? (
      Dbms.get(this).quote(this.schema.getName()) + "." + Dbms.get(this).quote(this.schema.getName())) : 
      Dbms.quote(this);
  }
  
  public int getChildrenCount() {
    return (this.b != null) ? this.b.length : 0;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return this.b[paramInt];
  }
  
  public List getEnumerationValues() {
    if (StringUtil.isFilled(this.a)) {
      Pattern pattern = Pattern.compile("(ENUM|ENUMERATION)\\s*\\((.*)\\)");
      Matcher matcher = pattern.matcher(this.a);
      if (matcher.find())
        return StringUtil.parseLOV(matcher.group(2)); 
    } 
    return null;
  }
  
  public int getDefaultSyncPriority() {
    return 900;
  }
}
