package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;

public class Shape extends AbstractUnit implements Entity {
  public final Depict a;
  
  public final Diagram b;
  
  private ShapeStyle e = ShapeStyle.a;
  
  public final List c = new ArrayList();
  
  protected final List d = new ArrayList();
  
  private Color f = Color.WHITE;
  
  public Shape(Diagram paramDiagram, String paramString) {
    super("");
    this.b = paramDiagram;
    setComment(paramString);
    this.a = new Depict(paramDiagram, this);
  }
  
  private static final String[] g = new String[0];
  
  public String[] a() {
    String str = getComment();
    if (str != null)
      return StringUtil.splitTextInLines(str); 
    return g;
  }
  
  public void refresh() {
    if (StringUtil.isEmptyTrim(getComment()))
      markForDeletion(); 
    this.c.removeIf(paramLine -> (paramLine.isMarkedForDeletion() || paramLine.a() != this));
    this.d.removeIf(paramLine -> (paramLine.isMarkedForDeletion() || paramLine.b() != this || paramLine.a().isMarkedForDeletion()));
    this.a.refresh();
  }
  
  public TreeUnit getParent() {
    return null;
  }
  
  public String getSymbolicName() {
    return "Shape";
  }
  
  public String getSymbolicIcon() {
    return "marker_fk.png";
  }
  
  public Glyph getSymbolicGlyph() {
    return BootstrapIcons.arrow_up_right;
  }
  
  public String getNameWithSchemaName() {
    return getName();
  }
  
  public List getAttributes() {
    return Collections.emptyList();
  }
  
  public List getRelations() {
    return this.c;
  }
  
  public List getImportedRelations() {
    return this.d;
  }
  
  public boolean isView() {
    return false;
  }
  
  public boolean isChildEntity() {
    return false;
  }
  
  public boolean isChildEntityArray() {
    return false;
  }
  
  public Schema getSchema() {
    return null;
  }
  
  public Entity getEntity() {
    return this;
  }
  
  public ShapeStyle b() {
    return this.e;
  }
  
  public void a(ShapeStyle paramShapeStyle) {
    if (paramShapeStyle != null)
      this.e = paramShapeStyle; 
  }
  
  public Line a(Shape paramShape, String paramString) {
    Line line = new Line(this, paramString);
    line.a(paramShape);
    this.c.add(line);
    return line;
  }
  
  public void a(Color paramColor) {
    this.f = paramColor;
  }
  
  public Color c() {
    return this.f;
  }
}
