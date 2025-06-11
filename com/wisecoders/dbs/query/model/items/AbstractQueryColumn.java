package com.wisecoders.dbs.query.model.items;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;

public abstract class AbstractQueryColumn extends AbstractUnit implements Attribute {
  static final String w = "marker_column.png";
  
  private String y;
  
  private boolean z = false;
  
  private final QueryTable A;
  
  private int B;
  
  public final Attribute x;
  
  public AbstractQueryColumn(QueryTable paramQueryTable, Attribute paramAttribute, String paramString) {
    super(paramString);
    this.A = paramQueryTable;
    this.x = paramAttribute;
  }
  
  public boolean isMandatory() {
    return true;
  }
  
  public void setTicked(boolean paramBoolean) {
    this.z = paramBoolean;
  }
  
  public boolean isTicked() {
    return this.z;
  }
  
  public QueryTable a() {
    return this.A;
  }
  
  public String getTypeString(DataTypeFormat paramDataTypeFormat) {
    return "";
  }
  
  public boolean isSelectable() {
    return true;
  }
  
  public TreeUnit getParent() {
    return this.A;
  }
  
  public String getSymbolicName() {
    return "Column";
  }
  
  public String getSymbolicIcon() {
    return "marker_column.png";
  }
  
  public void refresh() {}
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.check;
  }
  
  public void a(int paramInt) {
    this.B |= paramInt;
  }
  
  public boolean hasMarker(int paramInt) {
    return ((this.B & paramInt) > 0);
  }
  
  public short getToDoFlag() {
    return -1;
  }
  
  public DataType getDataType() {
    return this.x.getDataType();
  }
  
  public String getIndexingIcon() {
    return null;
  }
  
  public Sequence getAssociatedSequence() {
    return null;
  }
  
  public AttributeSpec getSpec() {
    return this.x.getSpec();
  }
  
  public void a(String paramString) {
    this.y = StringUtil.isFilledTrim(paramString) ? paramString.trim() : null;
  }
  
  public String b() {
    return this.y;
  }
  
  public boolean c() {
    return (this.y != null);
  }
  
  public String getNameWithPath() {
    return getName();
  }
}
