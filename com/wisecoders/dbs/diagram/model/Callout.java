package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.Map;

public class Callout implements Depictable, Unit {
  public final Diagram a;
  
  public final Depictable b;
  
  public final Unit c;
  
  private final Rect e = new Rect();
  
  private boolean f = false;
  
  private CalloutPointer g = CalloutPointer.f;
  
  private String h;
  
  private boolean i = false;
  
  public Callout(Diagram paramDiagram) {
    this.a = paramDiagram;
    this.b = null;
    this.c = null;
  }
  
  public Callout(Diagram paramDiagram, Depictable paramDepictable, Unit paramUnit) {
    this.a = paramDiagram;
    this.b = paramDepictable;
    this.c = paramUnit;
  }
  
  public String getComment() {
    return (this.c != null) ? this.c.getComment() : this.h;
  }
  
  public void a(CalloutPointer paramCalloutPointer) {
    this.g = paramCalloutPointer;
  }
  
  public CalloutPointer a() {
    return this.g;
  }
  
  public static double d = 1.4D;
  
  public void refresh() {
    if (StringUtil.isEmptyTrim(getComment()) || (this.b != null && this.b.isMarkedForDeletion())) {
      markForDeletion();
    } else {
      double d1 = 30.0D;
      double d2 = 0.0D;
      if (FxUtil.a()) {
        int i = (int)FxAbstractDiagramPane.F().getSize();
        String[] arrayOfString = d();
        if (arrayOfString != null) {
          for (String str : arrayOfString)
            d1 = Math.max(d1, FxAbstractDiagramPane.d(str)); 
          d2 = Math.ceil((arrayOfString.length * i) * d);
        } 
        this.e.a((int)(Diagram.cell * Math.rint(this.e.a() / Diagram.cell)), (int)(Diagram.cell * Math.rint(this.e.b() / Diagram.cell)));
        this.e.b((int)(Diagram.cell * Math.ceil(d1 / Diagram.cell)), (int)d2);
      } 
    } 
  }
  
  public void markForDeletion() {
    this.i = true;
  }
  
  public boolean isMarkedForDeletion() {
    return this.i;
  }
  
  public AbstractTable b() {
    return null;
  }
  
  public AbstractTable c() {
    return null;
  }
  
  public void setComment(String paramString) {
    if (this.c != null) {
      this.c.setComment(paramString);
    } else {
      this.h = paramString;
    } 
  }
  
  private static final String[] j = new String[0];
  
  private double k;
  
  private double l;
  
  public String[] d() {
    String str = getComment();
    if (str != null)
      return StringUtil.splitTextInLines(str); 
    return j;
  }
  
  public Rect getPosition() {
    return this.e;
  }
  
  public void a(Point paramPoint) {
    this.e.a(paramPoint.a, paramPoint.b);
  }
  
  public void moveTo(double paramDouble1, double paramDouble2) {
    this.e.a(paramDouble1, paramDouble2);
  }
  
  public void translate(double paramDouble1, double paramDouble2) {
    this.e.c(paramDouble1, paramDouble2);
  }
  
  public String toString() {
    return getComment();
  }
  
  public void e() {
    if (this.b != null) {
      this.k = this.e.a() - this.b.getPosition().a();
      this.l = this.e.b() - this.b.getPosition().b();
    } 
  }
  
  public void f() {
    if (this.b != null)
      this.e.a(this.b.getPosition().a() + this.k, this.b.getPosition().b() + this.l); 
  }
  
  public String getName() {
    return "Callout";
  }
  
  public String getSymbolicName() {
    return "Callout";
  }
  
  public void a(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public boolean g() {
    return this.f;
  }
  
  public double getGlobalCost(String paramString) {
    return 0.0D;
  }
  
  public double getDependencyCost(Depictable paramDepictable) {
    return 0.0D;
  }
  
  public Boolean is(UnitProperty paramUnitProperty) {
    return Boolean.FALSE;
  }
  
  public String ref() {
    return "";
  }
  
  public Map getCommentTags() {
    return null;
  }
  
  public String getCommentTag(String paramString) {
    return null;
  }
  
  public void setCommentTags(Map paramMap) {}
  
  public void setCommentTag(String paramString1, String paramString2) {}
  
  public String getDisplayName(Diagram paramDiagram) {
    return getName();
  }
}
