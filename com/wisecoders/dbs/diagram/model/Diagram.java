package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.model.undo.DiagramAttachChange;
import com.wisecoders.dbs.diagram.model.undo.DiagramChange;
import com.wisecoders.dbs.diagram.model.undo.DiagramDetachChange;
import com.wisecoders.dbs.diagram.model.undo.DiagramDropCalloutChange;
import com.wisecoders.dbs.diagram.model.undo.DiagramMoveChange;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;

@DoNotObfuscate
public class Diagram extends AbstractDiagram {
  public static final int MARKER_FK = 1;
  
  public static final int MARKER_REF = 2;
  
  public static final int MARKER_UNLINKED = 4;
  
  private static final int b = 300;
  
  public static int headerY = 2;
  
  public final List depicts = new CopyOnWriteArrayList();
  
  public final List groups = new CopyOnWriteArrayList();
  
  public final List callouts = new CopyOnWriteArrayList();
  
  public final List shapes = new CopyOnWriteArrayList();
  
  private double c;
  
  private double d;
  
  private boolean e = false;
  
  private boolean f = false;
  
  private boolean g = false;
  
  private boolean h = false;
  
  private boolean i = false;
  
  private boolean j = false;
  
  private boolean k = false;
  
  private static final String l = "fkTextOnLine";
  
  private static LineTextType m = LineTextType.b;
  
  private LineTextType n = m;
  
  static {
    String str = Pref.a("fkTextOnLine");
    for (LineTextType lineTextType : LineTextType.values()) {
      if (lineTextType.toString().equals(str))
        m = lineTextType; 
    } 
  }
  
  private final Map o = new HashMap<>();
  
  private String p;
  
  private final List q = new CopyOnWriteArrayList();
  
  public final boolean json;
  
  private boolean r;
  
  public void bringToFront(Depict paramDepict) {
    if (this.depicts.contains(paramDepict)) {
      this.depicts.remove(paramDepict);
      this.depicts.add(paramDepict);
    } 
    for (Group group : this.groups)
      group.bringToFront(paramDepict); 
  }
  
  public void bringToFront(Callout paramCallout) {
    if (paramCallout == null)
      return; 
    if (this.callouts.contains(paramCallout)) {
      this.callouts.remove(paramCallout);
      this.callouts.add(paramCallout);
    } 
  }
  
  public void bringToFront(Shape paramShape) {
    if (paramShape == null)
      return; 
    if (this.shapes.contains(paramShape)) {
      this.shapes.remove(paramShape);
      this.shapes.add(paramShape);
    } 
  }
  
  public Entity getEntity(String paramString) {
    for (Depict depict : this.depicts) {
      if (paramString.equals(depict.getEntity().getName()))
        return depict.getEntity(); 
    } 
    return null;
  }
  
  public Group createGroup(String paramString) {
    Group group = new Group(this, paramString);
    this.groups.add(group);
    return group;
  }
  
  public Group getGroup(String paramString) {
    for (Group group : this.groups) {
      if (group.getName().equals(paramString))
        return group; 
    } 
    return null;
  }
  
  public Group addInSameGroupAs(Depict paramDepict1, Depict paramDepict2) {
    for (Group group : this.groups) {
      if (group.getDepicts().contains(paramDepict1) && !group.getDepicts().contains(paramDepict2)) {
        group.attachDepict(paramDepict2);
        return group;
      } 
    } 
    return null;
  }
  
  public boolean isJoinedRouting() {
    return this.j;
  }
  
  public void setJoinedRouting(boolean paramBoolean) {
    this.j = paramBoolean;
  }
  
  public void setShowDeducedFks(boolean paramBoolean) {
    this.k = paramBoolean;
  }
  
  public boolean isShowDeducedFks() {
    return this.k;
  }
  
  public int getMarker(Attribute paramAttribute) {
    Integer integer = (Integer)this.o.get(paramAttribute);
    return (integer != null) ? integer.intValue() : 0;
  }
  
  public boolean hasMarker(Attribute paramAttribute, int paramInt) {
    Integer integer = (Integer)this.o.get(paramAttribute);
    return (integer != null && (integer.intValue() & paramInt) > 0);
  }
  
  public void setMarker(Attribute paramAttribute, Integer paramInteger) {
    if (paramAttribute != null && paramInteger != null)
      this.o.put(paramAttribute, Integer.valueOf(paramInteger.intValue() | getMarker(paramAttribute))); 
  }
  
  public Diagram(boolean paramBoolean) {
    this.r = false;
    this.t = new CopyOnWriteArrayList();
    this.u = new CopyOnWriteArrayList();
    this.canUndo = new SimpleBooleanProperty(false);
    this.canRedo = new SimpleBooleanProperty(false);
    this.json = paramBoolean;
  }
  
  public void resetChanged() {
    this.r = false;
  }
  
  public boolean isChanged() {
    return this.r;
  }
  
  public void refresh() {
    refresh(false);
  }
  
  public void refresh(boolean paramBoolean) {
    headerY = (int)(cell * 0.2D);
    this.o.clear();
    boolean bool = true;
    Rect rect = new Rect();
    for (Depict depict : this.depicts) {
      if (!paramBoolean)
        depict.refresh(); 
      if (depict.isMarkedForDeletion()) {
        this.depicts.remove(depict);
        this.r = true;
        continue;
      } 
      depict.setGroupFree(true);
      if (bool) {
        rect.a(depict.getPosition());
      } else {
        rect.c(depict.getPosition());
      } 
      bool = false;
    } 
    for (Callout callout : this.callouts) {
      callout.refresh();
      if (callout.isMarkedForDeletion()) {
        this.callouts.remove(callout);
        this.r = true;
        continue;
      } 
      if (bool) {
        rect.a(callout.getPosition());
      } else {
        rect.c(callout.getPosition());
      } 
      bool = false;
    } 
    for (Shape shape : this.shapes) {
      shape.refresh();
      if (shape.isMarkedForDeletion()) {
        this.shapes.remove(shape);
        this.r = true;
        continue;
      } 
      if (bool) {
        rect.a(shape.a.getPosition());
      } else {
        rect.c(shape.a.getPosition());
      } 
      bool = false;
    } 
    rect.e((3 * cell), (cell * (this.groups.isEmpty() ? 3 : 5)));
    if (rect.a() != 0.0D || rect.b() != 0.0D) {
      for (Depict depict : this.depicts)
        depict.translate(-rect.a(), -rect.b()); 
      for (Callout callout : this.callouts)
        callout.translate(-rect.a(), -rect.b()); 
      for (Shape shape : this.shapes)
        shape.a.translate(-rect.a(), -rect.b()); 
    } 
    this.c = rect.c();
    this.d = rect.d();
    for (Group group : this.groups) {
      group.refresh();
      if (group.isMarkedForDeletion()) {
        this.groups.remove(group);
        this.r = true;
        continue;
      } 
      for (Depict depict : group.getDepicts())
        depict.setGroupFree(false); 
    } 
    if (this.p != null) {
      for (Depict depict : this.depicts) {
        for (Relation relation : depict.getEntity().getRelations()) {
          if (this.p.contains(relation.getName() + ","))
            this.q.add(relation); 
        } 
      } 
      this.p = null;
    } 
    this.q.removeIf(Unit::isMarkedForDeletion);
    this.t.removeIf(DiagramChange::a);
    for (char c = 'Ĭ'; c < this.t.size(); c++)
      this.t.remove(0); 
    a();
  }
  
  public Callout createCallout(String paramString, Point paramPoint) {
    Callout callout = new Callout(this);
    callout.setComment(paramString);
    callout.a(paramPoint);
    this.callouts.add(callout);
    return callout;
  }
  
  public Shape createShape(String paramString, Point paramPoint) {
    Shape shape = new Shape(this, paramString);
    shape.a.setLocation(paramPoint.a, paramPoint.b);
    this.shapes.add(shape);
    return shape;
  }
  
  public Callout createCallout(Unit paramUnit, Point paramPoint) {
    Callout callout = getCalloutFor(paramUnit);
    if (callout != null)
      return callout; 
    Depictable depictable = null;
    if (paramUnit instanceof Depictable) {
      depictable = (Depictable)paramUnit;
    } else if (paramUnit != null) {
      depictable = getDepictFor(paramUnit.getEntity());
    } 
    callout = new Callout(this, depictable, paramUnit);
    callout.a(paramPoint);
    this.callouts.add(callout);
    return callout;
  }
  
  public Callout getCalloutFor(Unit paramUnit) {
    if (paramUnit != null)
      for (Callout callout : this.callouts) {
        if (callout.c == paramUnit)
          return callout; 
      }  
    return null;
  }
  
  public boolean isShowPageBorders() {
    return this.e;
  }
  
  public void setShowPageBorders(boolean paramBoolean) {
    this.e = paramBoolean;
  }
  
  public boolean isShowSchemaName() {
    return this.f;
  }
  
  public void setShowSchemaName(boolean paramBoolean) {
    this.f = paramBoolean;
  }
  
  public boolean isShowDataType() {
    return this.g;
  }
  
  public void setShowDataType(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public void setLineTextType(LineTextType paramLineTextType) {
    this.n = paramLineTextType;
  }
  
  public void setDefaultLineText(LineTextType paramLineTextType) {
    Pref.a("fkTextOnLine", paramLineTextType.toString());
    m = paramLineTextType;
  }
  
  public static LineTextType getDefaultLineTextType() {
    return m;
  }
  
  public LineTextType getLineTextType() {
    return this.n;
  }
  
  public boolean containsDepictFor(Unit paramUnit) {
    return (getDepictFor(paramUnit) != null);
  }
  
  public Depict getDepictIncludingShapeFor(Unit paramUnit) {
    if (paramUnit instanceof Shape)
      return ((Shape)paramUnit).a; 
    for (Depict depict : this.depicts) {
      if (depict.getEntity() == paramUnit)
        return depict; 
    } 
    return null;
  }
  
  public Depict getDepictFor(Unit paramUnit) {
    for (Depict depict : this.depicts) {
      if (depict.getEntity() == paramUnit)
        return depict; 
    } 
    return null;
  }
  
  public final Depictable moveUndoable(Depictable paramDepictable, double paramDouble1, double paramDouble2) {
    DiagramMoveChange diagramMoveChange = new DiagramMoveChange(paramDepictable, paramDouble1, paramDouble2);
    this.t.add(diagramMoveChange);
    return diagramMoveChange.a(this);
  }
  
  private Depict a(Entity paramEntity, double paramDouble1, double paramDouble2) {
    DiagramAttachChange diagramAttachChange = new DiagramAttachChange(paramEntity, paramDouble1, paramDouble2);
    this.t.add(diagramAttachChange);
    return (Depict)diagramAttachChange.a(this);
  }
  
  public final Depict attach(Entity paramEntity, double paramDouble1, double paramDouble2) {
    Depict depict = getDepictFor(paramEntity);
    if (depict == null) {
      depict = new Depict(this, paramEntity);
      this.depicts.add(depict);
      depict.setLocation(paramDouble1, paramDouble2);
      depict.refresh();
    } 
    return depict;
  }
  
  public final Depictable dropCalloutUnduable(Callout paramCallout) {
    DiagramDropCalloutChange diagramDropCalloutChange = new DiagramDropCalloutChange(paramCallout);
    this.t.add(diagramDropCalloutChange);
    return diagramDropCalloutChange.a(this);
  }
  
  public final Depict detachUndoable(Depict paramDepict) {
    DiagramDetachChange diagramDetachChange = new DiagramDetachChange(paramDepict);
    this.t.add(diagramDetachChange);
    return diagramDetachChange.c(this);
  }
  
  public final boolean detach(Depict paramDepict) {
    boolean bool = this.depicts.contains(paramDepict);
    this.depicts.remove(paramDepict);
    return bool;
  }
  
  public void attachRecursiveAndCreateGroupForHierarchicalEntities(Entity paramEntity, Point paramPoint) {
    if (getDepictFor(paramEntity) == null) {
      List list = attachRecursive(paramEntity, paramPoint, false);
      for (Depict depict : list) {
        if (depict.getEntity() == paramEntity)
          depict.hideColumnsIfDepictIsLarge(); 
      } 
      if (list.size() > 1) {
        Group group = createGroup(paramEntity.getName());
        group.attachAllDepicts(list);
      } 
      paramPoint.c(120.0D, 0.0D);
    } 
  }
  
  public List attachRecursive(Entity paramEntity, Point paramPoint, boolean paramBoolean) {
    List list = recursiveFindAndAttachChildrenEntities(paramEntity, paramPoint, paramBoolean);
    new ArrangerDepictTree(list, ArrangerSpacing.a);
    return list;
  }
  
  public List recursiveFindAndAttachChildrenEntities(Entity paramEntity, Point paramPoint, boolean paramBoolean) {
    ArrayList arrayList = new ArrayList();
    a(paramEntity, arrayList, paramPoint, paramBoolean);
    return arrayList;
  }
  
  private void a(Entity paramEntity, List<Depict> paramList, Point paramPoint, boolean paramBoolean) {
    if (getDepictFor(paramEntity) == null)
      paramList.add(a(paramEntity, paramPoint.a, paramPoint.b)); 
    byte b = 0;
    for (Attribute attribute : paramEntity.getAttributes()) {
      if (attribute instanceof Column) {
        Column column = (Column)attribute;
        if (column.hasChildEntity() && (paramBoolean || !column.getChildEntity().getAttributes().isEmpty()))
          b++; 
      } 
    } 
    if (b < Sys.B.displaySubDocumentsMaxFields.a())
      for (Attribute attribute : paramEntity.getAttributes()) {
        if (attribute instanceof Column) {
          Column column = (Column)attribute;
          if (column.hasChildEntity() && (paramBoolean || !column.getChildEntity().getAttributes().isEmpty()))
            a(column.getChildEntity(), paramList, paramPoint, paramBoolean); 
        } 
      }  
  }
  
  public Depict getMostReferredDepict() {
    Depict depict = null;
    int i = -1;
    for (Depict depict1 : this.depicts) {
      if (!(depict1.entity instanceof com.wisecoders.dbs.schema.ChildEntity) && !depict1.getEntity().getAttributes().isEmpty()) {
        int j = depict1.getEntity().getRelations().size() + depict1.getEntity().getImportedRelations().size();
        if (j > i) {
          depict = depict1;
          i = j;
        } 
      } 
    } 
    return depict;
  }
  
  public double getWidth() {
    return this.c;
  }
  
  public double getHeight() {
    return this.d;
  }
  
  public void hideFunctionalColumns() {
    for (Depict depict : this.depicts)
      depict.hideFunctionalColumns(); 
  }
  
  public void hideColumnsOnLargeDepicts() {
    for (Depict depict : this.depicts)
      depict.hideColumnsIfDepictIsLarge(); 
  }
  
  public void hideAllColumns() {
    for (Depict depict : this.depicts)
      depict.hideAllAttributes(); 
  }
  
  public void showAllColumns() {
    for (Depict depict : this.depicts)
      depict.showAllAttributes(); 
  }
  
  public void autoArrange(Group paramGroup) {
    if (this.json) {
      new ArrangerDepictTree(paramGroup.getDepicts(), paramGroup.b());
    } else {
      new a(paramGroup.getDepicts(), paramGroup.b());
    } 
  }
  
  @DoNotObfuscate
  public void autoArrange(ArrangerMode paramArrangerMode) {
    if (FxUtil.a()) {
      a(paramArrangerMode);
    } else {
      FxUtil.a(() -> a(paramArrangerMode));
    } 
  }
  
  private void a(ArrangerMode paramArrangerMode) {
    if (this.json) {
      new ArrangerTree(this, paramArrangerMode);
    } else {
      new ArrangerMixed(this, paramArrangerMode);
    } 
  }
  
  public void hideRelation(Relation paramRelation) {
    this.q.add(paramRelation);
  }
  
  public void showAllForeignKeys() {
    this.q.clear();
  }
  
  public void showAllEntityForeignKeys(Entity paramEntity) {
    ArrayList<Relation> arrayList = new ArrayList();
    for (Relation relation : this.q) {
      if (relation.getEntity() == paramEntity || relation.getTargetEntity() == paramEntity)
        arrayList.add(relation); 
    } 
    this.q.removeAll(arrayList);
  }
  
  boolean a(Relation paramRelation) {
    return this.q.contains(paramRelation);
  }
  
  public void setHideRelations(String paramString) {
    this.p = paramString;
  }
  
  public String getHiddenRelationNames() {
    if (this.q.isEmpty())
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    for (Relation relation : this.q)
      stringBuilder.append(relation.getName()).append(','); 
    return stringBuilder.toString();
  }
  
  private static final float[] s = new float[] { 217.0F, 110.0F, 34.0F, 240.0F, 261.0F };
  
  private final List t;
  
  private final List u;
  
  public final SimpleBooleanProperty canUndo;
  
  public final SimpleBooleanProperty canRedo;
  
  public void colorGroups(boolean paramBoolean) {
    byte b = 0;
    for (Group group : this.groups) {
      int i = b++ % s.length;
      Color color1 = Color.hsb(s[i], 0.04500000178813934D, 0.9700000286102295D);
      Color color2 = Color.hsb(s[i], 0.2199999988079071D, 0.9599999785423279D);
      group.setColor(color1);
      if (paramBoolean)
        for (Depict depict : group.getDepicts())
          depict.setColor(color2);  
    } 
  }
  
  public static List findRelationsWithEntity(Entity paramEntity1, Entity paramEntity2) {
    ArrayList<Relation> arrayList = new ArrayList();
    for (Relation relation : paramEntity1.getRelations()) {
      if (relation.getTargetEntity() == paramEntity2)
        arrayList.add(relation); 
    } 
    for (Relation relation : paramEntity1.getImportedRelations()) {
      if (relation.getEntity() == paramEntity2)
        arrayList.add(relation); 
    } 
    return arrayList;
  }
  
  public Point getFreePlacePoint() {
    double d1 = -2.147483648E9D;
    double d2 = 2.147483647E9D;
    for (Depict depict : this.depicts) {
      if (depict.getPosition().a() + depict.getPosition().c() > d1)
        d1 = depict.getPosition().a() + depict.getPosition().c() + 20.0D; 
      if (depict.getPosition().b() < d2)
        d2 = depict.getPosition().b(); 
    } 
    return new Point((d1 == -2.147483648E9D) ? 20.0D : (int)d1, (d2 == 2.147483647E9D) ? 20.0D : (int)d2);
  }
  
  void a() {
    this.canUndo.set((this.t.size() > 0));
    this.canRedo.set((this.u.size() > 0));
    if (this.t.size() > 300)
      this.t.remove(0); 
    if (this.u.size() > 300)
      this.u.remove(0); 
  }
  
  public void undo() {
    if (this.t.size() > 0) {
      long l = ((DiagramChange)this.t.get(this.t.size() - 1)).a;
      DiagramChange diagramChange;
      while (this.t.size() > 0 && (diagramChange = this.t.get(this.t.size() - 1)).a > l - 500L) {
        diagramChange.b(this);
        this.t.remove(diagramChange);
        this.u.add(diagramChange);
      } 
      a();
    } 
  }
  
  public void redo() {
    if (this.u.size() > 0) {
      long l = ((DiagramChange)this.u.get(this.u.size() - 1)).a;
      DiagramChange diagramChange;
      while (this.u.size() > 0 && (diagramChange = this.u.get(this.u.size() - 1)).a < l + 500L) {
        diagramChange.a(this);
        this.u.remove(diagramChange);
        this.t.add(diagramChange);
      } 
      a();
    } 
  }
  
  public boolean isShowPhysicalName() {
    return this.h;
  }
  
  public void setShowPhysicalName(boolean paramBoolean) {
    this.h = paramBoolean;
    if (paramBoolean)
      this.i = false; 
  }
  
  public boolean isShowPhysicalDictionaryName() {
    return this.i;
  }
  
  public void setShowPhysicalDictionaryName(boolean paramBoolean) {
    this.i = paramBoolean;
    if (paramBoolean)
      this.h = false; 
  }
  
  public RelationCardinality getTargetTerminatorCardinality(Notation paramNotation, Relation paramRelation) {
    boolean bool = (paramRelation.getRelationType() == RelationType.c) ? true : false;
    boolean bool1 = paramRelation.hasFlag(1);
    return paramNotation.a(getRelationCardinality(paramRelation, true), bool, bool1);
  }
}
