package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.scene.paint.Color;

@DoNotObfuscate
public class Group extends AbstractUnit implements Depictable {
  public static final Color DEFAULT_COLOR = Color.web("#c4e0f9");
  
  private Color a = DEFAULT_COLOR;
  
  private Color b = DEFAULT_COLOR;
  
  private String c;
  
  public final Diagram diagram;
  
  private final List d = new CopyOnWriteArrayList();
  
  private final Rect e = new Rect();
  
  private boolean f = false;
  
  private double g;
  
  public Group(Diagram paramDiagram, String paramString) {
    super(paramString);
    this.diagram = paramDiagram;
    setColor(DEFAULT_COLOR);
  }
  
  public Diagram getLayout() {
    return this.diagram;
  }
  
  public void setColor(Color paramColor) {
    if (paramColor != null) {
      this.a = paramColor;
      c();
    } 
  }
  
  public Color getColor() {
    return this.a;
  }
  
  public Color getBorderColor() {
    return this.b;
  }
  
  public Color getBorderSelectedColor() {
    return DEFAULT_COLOR;
  }
  
  public boolean isMarkedForDeletion() {
    return this.f;
  }
  
  public void markForDeletion() {
    this.f = true;
  }
  
  public Rect getPosition() {
    return this.e;
  }
  
  public void moveTo(double paramDouble1, double paramDouble2) {
    double d1 = paramDouble1 - this.e.a(), d2 = paramDouble2 - this.e.b();
    this.e.a(paramDouble1, paramDouble2);
    for (Depict depict : getDepicts())
      depict.translate(d1, d2); 
  }
  
  public void translate(double paramDouble1, double paramDouble2) {
    this.e.c(paramDouble1, paramDouble2);
    for (Depict depict : getDepicts())
      depict.translate(paramDouble1, paramDouble2); 
  }
  
  public AbstractTable getEntity() {
    return null;
  }
  
  public AbstractTable getParentEntity() {
    return null;
  }
  
  public final void attachAllTables(Collection paramCollection) {
    for (Entity entity : paramCollection) {
      Depict depict = this.diagram.getDepictFor(entity);
      if (depict != null)
        attachDepict(depict); 
    } 
  }
  
  public final void attachAllDepicts(Collection paramCollection) {
    this.d.addAll(paramCollection);
  }
  
  public final void attachDepict(Depict paramDepict) {
    if (!this.d.contains(paramDepict) && this.diagram.depicts.contains(paramDepict)) {
      if (!paramDepict.isGroupFree())
        for (Group group : this.diagram.groups)
          group.dettach(paramDepict);  
      this.d.add(paramDepict);
    } 
  }
  
  public final List getDepicts() {
    return this.d;
  }
  
  public final void dettachAll(Collection<?> paramCollection) {
    this.d.removeAll(paramCollection);
  }
  
  public final boolean dettach(Depict paramDepict) {
    if (!this.d.contains(paramDepict))
      return false; 
    return this.d.remove(paramDepict);
  }
  
  public void refresh() {
    if (this.d.isEmpty())
      markForDeletion(); 
    if (isMarkedForDeletion()) {
      this.d.clear();
    } else {
      this.d.retainAll(this.diagram.depicts);
      boolean bool = true;
      for (Depict depict : this.d) {
        if (depict.isMarkedForDeletion()) {
          this.d.remove(depict);
          continue;
        } 
        if (bool) {
          this.e.a(depict.getPosition());
        } else {
          this.e.c(depict.getPosition());
        } 
        bool = false;
      } 
      int i = Sys.B.groupInsets.a();
      this.e.e(i, (i + 9));
      this.e.c(0.0D, -10.0D);
    } 
    c();
  }
  
  private void c() {
    Float[] arrayOfFloat = { Float.valueOf(0.08F), Float.valueOf(0.88F) };
    this.b = Color.hsb(this.a.getHue(), arrayOfFloat[0].floatValue(), arrayOfFloat[1].floatValue());
  }
  
  public void bringToFront(Depict paramDepict) {
    if (this.d.contains(paramDepict)) {
      this.d.remove(paramDepict);
      this.d.add(paramDepict);
    } 
  }
  
  public String getComment() {
    return this.c;
  }
  
  public void setComment(String paramString) {
    this.c = paramString;
  }
  
  public String getSymbolicName() {
    return "Group";
  }
  
  public int size() {
    return this.d.size();
  }
  
  public void mergeWith(Group paramGroup) {
    if (paramGroup.diagram == this.diagram) {
      for (Depict depict : paramGroup.d) {
        this.d.add(depict);
        paramGroup.dettach(depict);
      } 
      this.diagram.groups.remove(paramGroup);
    } 
  }
  
  public void orderDepictsAndRenameGroup() {
    ArrayList arrayList = new ArrayList(this.d);
    arrayList.sort((paramDepict1, paramDepict2) -> {
          int i = paramDepict1.entity.getRelations().size() + (this.diagram.json ? paramDepict1.entity.getImportedRelations().size() : 0);
          int j = paramDepict2.entity.getRelations().size() + (this.diagram.json ? paramDepict1.entity.getImportedRelations().size() : 0);
          return Integer.compare(i, j);
        });
    this.d.clear();
    this.d.addAll(arrayList);
    if (!arrayList.isEmpty())
      rename(((Depict)arrayList.get(0)).entity.getName()); 
  }
  
  public double getGlobalCost(String paramString) {
    double d = 0.0D;
    for (Depict depict : this.d)
      d += (depict.getEntity().getRelations().size() + depict.getEntity().getImportedRelations().size()); 
    if (paramString != null)
      d += StringUtil.wordSimilarityByLevensthein(getName(), paramString); 
    return d;
  }
  
  public double getDependencyCost(Depictable paramDepictable) {
    double d = 0.0D;
    if (paramDepictable instanceof Group) {
      Group group = (Group)paramDepictable;
      for (Depict depict : this.d) {
        for (Depict depict1 : group.getDepicts())
          d += depict.getDependencyCost(depict1); 
      } 
    } 
    return d;
  }
  
  double a() {
    return this.g;
  }
  
  void a(double paramDouble) {
    this.g = paramDouble;
  }
  
  ArrangerSpacing b() {
    int i = 0;
    for (Depict depict : this.d)
      i += depict.getEntity().getRelations().size() + depict.getEntity().getImportedRelations().size(); 
    if (i * 0.2D > this.d.size())
      return ArrangerSpacing.c; 
    if (i * 0.3D > this.d.size())
      return ArrangerSpacing.b; 
    return ArrangerSpacing.a;
  }
  
  public Boolean is(UnitProperty paramUnitProperty) {
    return Boolean.FALSE;
  }
  
  public TreeUnit getParent() {
    return null;
  }
  
  public String getSymbolicIcon() {
    return null;
  }
  
  public Glyph getSymbolicGlyph() {
    return null;
  }
}
