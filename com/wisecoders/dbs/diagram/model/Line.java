package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.List;

public class Line extends AbstractUnit implements Relation {
  private int e = 0;
  
  public final Shape a;
  
  private Shape f;
  
  public Line(Shape paramShape, String paramString) {
    super(paramString);
    this.a = paramShape;
  }
  
  public void refresh() {}
  
  public TreeUnit getParent() {
    return this.a;
  }
  
  public String getSymbolicName() {
    return "Line";
  }
  
  public String getSymbolicIcon() {
    return "marker_fk.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return null;
  }
  
  public Shape a() {
    return this.a;
  }
  
  public void a(Shape paramShape) {
    this.f = paramShape;
    this.f.d.add(this);
  }
  
  public Shape b() {
    return this.f;
  }
  
  private static final ArrayList g = new ArrayList();
  
  public List getSourceAttributes() {
    return g;
  }
  
  public List getTargetAttributes() {
    return g;
  }
  
  public Attribute getColumnAt(int paramInt, boolean paramBoolean) {
    return null;
  }
  
  public String getColumnsName() {
    return null;
  }
  
  public String getCascadeText() {
    return null;
  }
  
  public int getColumnCount() {
    return 0;
  }
  
  public boolean isAutoReference() {
    return false;
  }
  
  public Attribute getLastSourceAttribute() {
    return null;
  }
  
  public Attribute getLastTargetAttribute() {
    return null;
  }
  
  public void setFlag(int paramInt) {
    this.e |= paramInt;
  }
  
  public void resetFlag(int paramInt) {
    this.e &= paramInt ^ 0xFFFFFFFF;
  }
  
  public boolean hasFlag(int paramInt) {
    return ((this.e & paramInt) > 0);
  }
  
  public RelationType getRelationType() {
    return RelationType.b;
  }
  
  public RelationCardinality getRelationCardinality() {
    return RelationCardinality.d;
  }
  
  public boolean isDeduced() {
    return false;
  }
}
