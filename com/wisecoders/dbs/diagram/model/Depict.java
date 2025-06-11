package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueCopyOnWriteArrayList;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.paint.Color;

@DoNotObfuscate
public class Depict implements Depictable, Unit {
  public static Color DEFAULT_COLOR = Color.web("0x3986c1");
  
  public final Diagram diagram;
  
  public final Entity entity;
  
  private final Rect a = new Rect();
  
  private boolean b = true;
  
  private boolean c;
  
  private Color d = DEFAULT_COLOR;
  
  private List e;
  
  private List f;
  
  private int g;
  
  private int h;
  
  public Depict(Diagram paramDiagram, Entity paramEntity) {
    this.diagram = paramDiagram;
    this.entity = paramEntity;
  }
  
  public Entity getEntity() {
    return this.entity;
  }
  
  public Entity getParentEntity() {
    return this.entity;
  }
  
  public void setLocation(double paramDouble1, double paramDouble2) {
    this.a.a(paramDouble1, paramDouble2);
  }
  
  public void moveTo(double paramDouble1, double paramDouble2) {
    this.a.a(paramDouble1, paramDouble2);
  }
  
  public void translate(double paramDouble1, double paramDouble2) {
    this.a.c(paramDouble1, paramDouble2);
  }
  
  public void setSize(int paramInt1, int paramInt2) {
    this.a.b(paramInt1, paramInt2);
  }
  
  public Rect getPosition() {
    return this.a;
  }
  
  public boolean isGroupFree() {
    return this.b;
  }
  
  public void setGroupFree(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public void setAttributeVisible(Attribute paramAttribute, boolean paramBoolean) {
    if (this.entity.getAttributes().contains(paramAttribute)) {
      if (!paramBoolean && (
        paramAttribute.hasMarker(1) || paramAttribute.hasMarker(32)))
        return; 
      if (paramBoolean && this.f == null)
        return; 
      if (this.f == null) {
        this.f = new UniqueCopyOnWriteArrayList();
        this.e = new UniqueCopyOnWriteArrayList();
      } 
      if (paramBoolean) {
        this.f.remove(paramAttribute);
      } else {
        this.f.add(paramAttribute);
      } 
    } 
  }
  
  @GroovyMethod
  public void hideAllAttributes() {
    for (Attribute attribute : this.entity.getAttributes())
      setAttributeVisible(attribute, false); 
  }
  
  @GroovyMethod
  public void showAllAttributes() {
    for (Attribute attribute : this.entity.getAttributes())
      setAttributeVisible(attribute, true); 
  }
  
  public List getVisibleAttributes() {
    return (this.e != null) ? this.e : this.entity.getAttributes();
  }
  
  private static final List i = new ArrayList();
  
  public List getHiddenAttributes() {
    return (this.f != null) ? this.f : i;
  }
  
  public void refresh() {
    if (this.entity.isMarkedForDeletion()) {
      this.c = true;
    } else {
      if (this.f != null) {
        this.e.retainAll(this.entity.getAttributes());
        this.f.retainAll(this.entity.getAttributes());
        this.e.addAll(this.entity.getAttributes());
        this.e.removeAll(this.f);
        if (this.f.isEmpty()) {
          this.f = null;
          this.e = null;
        } 
      } 
      int i = (int)FxAbstractDiagramPane.E().getSize();
      int j = (int)FxAbstractDiagramPane.F().getSize();
      if (FxUtil.a()) {
        this.g = FxAbstractDiagramPane.c(this.entity.getDisplayName(this.diagram));
        this.h = FxAbstractDiagramPane.c(this.entity.getNameWithSchemaName());
        int k = getNameWidth(this.diagram.isShowSchemaName()) + i;
        int m = 0;
        for (Attribute attribute : getVisibleAttributes()) {
          int n = 0;
          if (this.diagram.isShowDataType() && attribute.getTypeString(DataTypeFormat.b) != null) {
            String str = StringUtil.cutOfWithDots(attribute.getTypeString(DataTypeFormat.b), 24);
            n = FxAbstractDiagramPane.b(str);
          } 
          m = Math.max(m, FxAbstractDiagramPane.a(attribute.getDisplayName(this.diagram)) + n);
        } 
        double d1 = Math.max(k, 2.8F * i + m);
        double d2 = ((3 + getVisibleAttributes().size()) * Diagram.cell);
        Entity entity = this.entity;
        if (entity instanceof Shape) {
          Shape shape = (Shape)entity;
          String[] arrayOfString = shape.a();
          for (String str : arrayOfString)
            m = Math.max(m, FxAbstractDiagramPane.a(str)); 
          switch (Depict$1.a[shape.b().ordinal()]) {
            case 1:
              d1 = Diagram.formatOnEvenCell((m + 2 * i));
              d2 = Diagram.formatOnEvenCell(Math.ceil(((1 + arrayOfString.length) * j) * Callout.d));
              break;
            case 2:
              d1 = Diagram.formatOnEvenCell((m + 4 * Diagram.cell));
              d2 = Diagram.formatOnEvenCell(((4 + arrayOfString.length) * j) * Callout.d);
              break;
            case 3:
              d1 = Diagram.formatOnEvenCell(m * 1.5D);
              d2 = Diagram.formatOnEvenCell((3 * arrayOfString.length * j) * Callout.d);
              break;
            case 4:
              d1 = Diagram.formatOnEvenCell(m);
              d2 = Diagram.formatOnEvenCell((arrayOfString.length * j) * Callout.d);
              break;
          } 
        } 
        this.a.a((int)(Diagram.cell * Math.rint(((float)this.a.a() / Diagram.cell))), (int)(Diagram.cell * Math.rint(((float)this.a.b() / Diagram.cell))));
        this.a.b((int)(Diagram.cell * Math.ceil(d1 / Diagram.cell)), (int)(Diagram.cell * Math.ceil(d2 / Diagram.cell)));
      } 
    } 
  }
  
  public boolean isMarkedForDeletion() {
    return this.c;
  }
  
  public String toString() {
    return this.entity.toString() + " (" + this.entity.toString() + ", " + this.a.a() + " )";
  }
  
  public int getNameWidth(boolean paramBoolean) {
    return paramBoolean ? this.h : this.g;
  }
  
  public boolean hasHiddenAttributes() {
    return (this.f != null && !this.f.isEmpty());
  }
  
  public String getName() {
    return this.entity.getName();
  }
  
  public String getSymbolicName() {
    return "Depict";
  }
  
  public void markForDeletion() {
    this.c = true;
  }
  
  public void setComment(String paramString) {}
  
  public String getComment() {
    return null;
  }
  
  public boolean setColor(Color paramColor) {
    if (paramColor != null) {
      boolean bool = !paramColor.equals(this.d) ? true : false;
      this.d = paramColor;
      return bool;
    } 
    return false;
  }
  
  public Color getColor() {
    return this.d;
  }
  
  public void hideColumnsIfDepictIsLarge() {
    if (getVisibleAttributes().size() > Sys.B.hideColumnsOnLargeTablesByImport.a()) {
      byte b = 0;
      for (Attribute attribute : this.entity.getAttributes()) {
        if (b++ > 10)
          setAttributeVisible(attribute, false); 
      } 
    } 
  }
  
  public void hideFunctionalColumns() {
    for (Attribute attribute : this.entity.getAttributes()) {
      if (attribute.getSpec() == AttributeSpec.functional)
        setAttributeVisible(attribute, false); 
    } 
  }
  
  public double getGlobalCost(String paramString) {
    double d = (getEntity().getRelations().size() + getEntity().getImportedRelations().size());
    if (paramString != null)
      d += StringUtil.wordSimilarityByLevensthein(getName(), paramString) * 2.0D; 
    return d;
  }
  
  public double getDependencyCost(Depictable paramDepictable) {
    double d = 0.0D;
    if (paramDepictable instanceof Depict) {
      Depict depict = (Depict)paramDepictable;
      if (!this.diagram.json)
        d += StringUtil.wordSimilarityByLevensthein(getEntity().getName(), depict.getEntity().getName()); 
      for (Relation relation : getEntity().getRelations()) {
        if (relation.getTargetEntity() == depict.getEntity() && (!this.diagram.json || !relation.isVirtual()))
          d += 1.01D; 
      } 
      for (Relation relation : getEntity().getImportedRelations()) {
        if (relation.getEntity() == depict.getEntity() && (!this.diagram.json || !relation.isVirtual()))
          d += 1.01D; 
      } 
    } 
    return d;
  }
  
  public Boolean is(UnitProperty paramUnitProperty) {
    return Boolean.FALSE;
  }
  
  public static double middleOf(double paramDouble1, double paramDouble2) {
    return paramDouble1 + (paramDouble2 - paramDouble1) / 2.0D;
  }
  
  public String ref() {
    return "";
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
    return "";
  }
}
