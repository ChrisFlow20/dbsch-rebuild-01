package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.sys.ColorUtil;
import com.wisecoders.dbs.sys.Log;
import java.util.ArrayList;
import javafx.concurrent.Task;

public class DiagramRouterTask extends Task {
  private int j;
  
  private int i;
  
  private int h;
  
  private int g;
  
  private int f;
  
  private boolean e;
  
  private boolean d = true;
  
  private final FxAbstractDiagramPane c;
  
  private final Diagram b;
  
  protected Void a() {
    if (isCancelled())
      return null; 
    Thread.currentThread().setPriority(1);
    Thread.currentThread().setName("DbSchema: Compute Diagram Lines Task (" + this.b.depicts.size() + " depicts)");
    if (!isCancelled())
      c(); 
    return null;
  }
  
  protected void succeeded() {
    this.b.setStatus(this.d ? PainterStatus.c : PainterStatus.d);
    if (this.c != null) {
      this.c.b(this.d);
      this.c.j();
    } 
  }
  
  protected void failed() {
    Log.b(getException());
  }
  
  private void b() {
    try {
      synchronized (this.b) {
        int i = (int)(this.b.getWidth() / Diagram.cell);
        int j = (int)(this.b.getHeight() / Diagram.cell);
        if (this.b.dim_X != i || this.b.dim_Y != j) {
          this.b.dim_X = i;
          this.b.dim_Y = j;
          this.b.links = new long[this.b.dim_X][this.b.dim_Y];
          this.b.lengthTable = new int[this.b.dim_X][this.b.dim_Y];
          if (this.c != null)
            this.c.S(); 
        } 
        for (byte b = 0; b < this.b.dim_X; b++) {
          for (byte b1 = 0; b1 < this.b.dim_Y; b1++) {
            this.b.links[b][b1] = 0L;
            this.b.lengthTable[b][b1] = 0;
          } 
          if (isCancelled())
            return; 
        } 
      } 
    } catch (Throwable throwable) {
      this.d = false;
      Log.a("Failed to set layout size ( " + this.b.dim_X + ", " + this.b.dim_Y + "). Consider increasing memory settings in DbSchema.vmoptions.", throwable);
    } 
  }
  
  private void c() {
    try {
      if (this.b.links != null) {
        this.d = true;
        for (Depict depict : this.b.depicts) {
          a(depict);
          if (isCancelled())
            break; 
        } 
        for (Shape shape : this.b.shapes) {
          a(shape.a);
          if (isCancelled())
            break; 
        } 
        d();
      } 
    } catch (Exception exception) {
      this.d = false;
      Log.a("Failed to reconnect layout ( " + this.b.dim_X + ", " + this.b.dim_Y + ")", exception);
    } 
  }
  
  private void a(Depict paramDepict) {
    double d1 = paramDepict.getPosition().a() / Diagram.cell, d2 = paramDepict.getPosition().b() / Diagram.cell, d3 = paramDepict.getPosition().c() / Diagram.cell, d4 = paramDepict.getPosition().d() / Diagram.cell;
    if (d1 - 1.0D >= 0.0D && d1 + d3 < this.b.dim_X && d2 - 1.0D >= 0.0D && d2 + d4 < this.b.dim_Y)
      for (int i = (int)d2 - 1; i < d2 + d4 + 1.0D; i++) {
        for (int j = (int)d1 - 1; j < d1 + d3 + 1.0D; j++)
          this.b.links[j][i] = this.b.links[j][i] | 0x10000L; 
      }  
  }
  
  private void d() {
    this.b.relationPositions.clear();
    Rect rect1 = new Rect();
    Rect rect2 = new Rect();
    ArrayList<Depict> arrayList = new ArrayList(this.b.depicts);
    for (Shape shape : this.b.shapes)
      arrayList.add(shape.a); 
    arrayList.sort((paramDepict1, paramDepict2) -> {
          int i = paramDepict1.getEntity().getImportedRelations().size();
          int j = paramDepict2.getEntity().getImportedRelations().size();
          return (i != j) ? ((i < j) ? -1 : 1) : paramDepict1.getName().compareTo(paramDepict2.getName());
        });
    for (Depict depict : arrayList) {
      if (this.b.depicts.contains(depict) || depict.entity instanceof Shape)
        for (Relation relation : depict.getEntity().getRelations()) {
          boolean bool = relation instanceof Line;
          Depict depict1 = this.b.getDepictIncludingShapeFor(relation.getEntity());
          Depict depict2 = this.b.getDepictIncludingShapeFor(relation.getTargetEntity());
          if (!this.b.a(relation) && depict2 != null && depict1 != null && !isCancelled() && (!relation.isDeduced() || this.b.isShowDeducedFks())) {
            rect2.a(depict1.getPosition());
            rect2.c(depict2.getPosition());
            rect2.a(Diagram.cell);
            rect2.e(16.0D, 16.0D);
            rect2.a(Math.max(0.0D, rect2.a()));
            rect2.b(Math.max(0.0D, rect2.b()));
            rect2.c(Math.min(this.b.dim_X - rect2.a() - 1.0D, rect2.c()));
            rect2.d(Math.min(this.b.dim_Y - rect2.b() - 1.0D, rect2.d()));
            boolean bool1 = (depict1 == depict2) ? true : false;
            int i;
            for (i = 0; i < rect2.c(); i++) {
              for (byte b = 0; b < rect2.d(); b++) {
                this.b.links[(int)rect2.a() + i][(int)rect2.b() + b] = this.b.links[(int)rect2.a() + i][(int)rect2.b() + b] & 0xFFFFFFFFFFFDFC00L;
                this.b.lengthTable[(int)rect2.a() + i][(int)rect2.b() + b] = 0;
              } 
            } 
            if (this.b.isJoinedRouting() && !bool) {
              rect1.a(depict1.getPosition());
              rect1.a(Diagram.cell);
              i = 2 + depict1.getVisibleAttributes().indexOf(relation.getLastSourceAttribute());
              a(rect1.a() - 1.0D, rect1.b() + i, 0x20000L | AbstractDiagram.COST_START | 0x300L, rect2);
              a(rect1.a() + rect1.c(), rect1.b() + i, 0x20000L | AbstractDiagram.COST_START | 0x100L, rect2);
              rect1.a(depict2.getPosition());
              rect1.a(Diagram.cell);
              i = 2 + depict2.getVisibleAttributes().indexOf(relation.getLastTargetAttribute());
              a(rect1.a() - 1.0D, rect1.b() + i, 0x20000L | AbstractDiagram.COST_END | 0x100L, rect2);
              a(rect1.a() + rect1.c(), rect1.b() + i, 0x20000L | AbstractDiagram.COST_END | 0x300L, rect2);
            } else {
              rect1.a(depict1.getPosition());
              rect1.a(Diagram.cell);
              for (i = 1; i < rect1.c() && !bool1; i++) {
                long l = (AbstractDiagram.COST_START + ((bool && a(i, rect1.c())) ? 3 : 0));
                a(rect1.a() + i, rect1.b() - 1.0D, 0x20000L | l | 0x0L, rect2);
                a(rect1.a() + i, rect1.b() + rect1.d(), 0x20000L | l | 0x200L, rect2);
              } 
              for (i = 1; i < rect1.d(); i++) {
                long l = (AbstractDiagram.COST_START + ((bool && a(i, rect1.d())) ? 3 : 0));
                a(rect1.a() - 1.0D, rect1.b() + i - 1.0D, 0x20000L | l | 0x300L, rect2);
                a(rect1.a() + rect1.c(), rect1.b() + i, 0x20000L | l | 0x100L, rect2);
              } 
              rect1.a(depict2.getPosition());
              rect1.a(Diagram.cell);
              for (i = 1; i < rect1.c(); i++) {
                long l = (AbstractDiagram.COST_END - ((bool && a(i, rect1.c())) ? 3 : 0));
                a(rect1.a() + i, rect1.b() - 1.0D, 0x20000L | l | 0x200L, rect2);
                a(rect1.a() + i, rect1.b() + rect1.d(), 0x20000L | l | 0x0L, rect2);
              } 
              for (i = 1; i < rect1.d() && !bool1; i++) {
                long l = (AbstractDiagram.COST_END - ((bool && a(i, rect1.d())) ? 3 : 0));
                a(rect1.a() - 1.0D, rect1.b() + i - 1.0D, 0x20000L | l | 0x100L, rect2);
                a(rect1.a() + rect1.c(), rect1.b() + i, 0x20000L | l | 0x300L, rect2);
              } 
            } 
            if (this.b.isJoinedRouting())
              a(relation); 
            a(relation, new c(rect2));
          } 
        }  
    } 
  }
  
  public static boolean a(double paramDouble1, double paramDouble2) {
    return (paramDouble2 % 2.0D == 0.0D) ? ((paramDouble1 != paramDouble2 / 2.0D)) : ((paramDouble1 != (paramDouble2 - 1.0D) / 2.0D && paramDouble1 != (paramDouble2 + 1.0D) / 2.0D));
  }
  
  private void a(double paramDouble1, double paramDouble2, long paramLong, Rect paramRect) {
    if (paramDouble1 >= paramRect.a() && paramDouble1 < paramRect.a() + paramRect.c() && paramDouble2 >= paramRect.b() && paramDouble2 < paramRect.b() + paramRect.d()) {
      int i = (int)paramDouble1, j = (int)paramDouble2;
      if ((this.b.links[i][j] & 0x800000L) == 0L)
        this.b.links[i][j] = this.b.links[i][j] & 0xFFFFFFFFFFFFFC00L | paramLong; 
    } 
  }
  
  public DiagramRouterTask(FxAbstractDiagramPane paramFxAbstractDiagramPane, Diagram paramDiagram) {
    this.e = false;
    this.f = -1;
    this.g = -1;
    this.h = -1;
    this.i = -1;
    this.j = -1;
    this.c = paramFxAbstractDiagramPane;
    this.b = paramDiagram;
    b();
    paramDiagram.setStatus(PainterStatus.b);
    (new Thread((Runnable)this)).start();
  }
  
  public DiagramRouterTask(Diagram paramDiagram) {
    this.e = false;
    this.f = -1;
    this.g = -1;
    this.h = -1;
    this.i = -1;
    this.j = -1;
    this.c = null;
    this.b = paramDiagram;
    paramDiagram.setStatus(PainterStatus.b);
    b();
    c();
  }
  
  private void a(Relation paramRelation, c paramc) {
    this.e = false;
    this.i = -1;
    for (byte b = 1; b < 62 && !this.e; b++) {
      this.i = -1;
      for (int i = paramc.b; i < paramc.b + paramc.d; i++) {
        if (isCancelled())
          return; 
        for (int j = paramc.a; j < paramc.a + paramc.c; j++) {
          long l = this.b.links[j][i];
          if ((l & AbstractDiagram.MAP_COST) == b) {
            int k = (int)(l >> 8L & 0x3L);
            a(j, i, b, k, this.b.lengthTable[j][i], paramc);
          } 
        } 
      } 
      if (this.e)
        a(paramRelation, this.f, this.g, this.h, paramc); 
    } 
    if (!this.e) {
      this.b.relationPositions.remove(paramRelation);
      this.b.setMarker(paramRelation.getLastSourceAttribute(), Integer.valueOf(4));
      this.d = false;
    } 
  }
  
  private void a(Relation paramRelation) {
    for (Relation relation : this.b.relationPositions.keySet()) {
      if (relation != paramRelation && relation.getTargetEntity() == paramRelation.getTargetEntity()) {
        if (relation.getLastSourceAttribute() == paramRelation.getLastSourceAttribute())
          a((RelationPosition)this.b.relationPositions.get(relation), true); 
        if (relation.getLastTargetAttribute() == paramRelation.getLastTargetAttribute())
          a((RelationPosition)this.b.relationPositions.get(relation), false); 
      } 
    } 
  }
  
  private void a(RelationPosition paramRelationPosition, boolean paramBoolean) {
    if (paramRelationPosition == null)
      return; 
    int i = paramRelationPosition.d, j = paramRelationPosition.e;
    int k = paramRelationPosition.f;
    int m = paramBoolean ? AbstractDiagram.COST_START : AbstractDiagram.COST_END;
    byte b = 0;
    while ((i += AbstractDiagram.DECREMENTX[k]) >= 1 && i < this.b.dim_X - 1 && (j += AbstractDiagram.DECREMENTY[k]) >= 1 && j < this.b.dim_Y - 1) {
      long l = this.b.links[i][j];
      if ((l & 0x800000L) > 0L)
        return; 
      if ((l & 0xF00000L) == 1048576L)
        this.b.links[i][j] = this.b.links[i][j] | 0x20000L | m; 
      k = (int)(((l & 0xF00000L) == 2097152L) ? k : (l >> 12L & 0x3L));
      if (++b > 999999) {
        Log.f("DIAGRAM ROUTING PROBLEMS DETECTED. PLEASE WRITE TO DBSCHEMA SUPPORT FOR INVESTIGATION, THE PROBLEM SHOULD BE FIXED.");
        return;
      } 
    } 
  }
  
  private void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, c paramc) {
    a(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, true, paramc);
    while (a(paramInt1 += AbstractDiagram.INCREMENTX[paramInt4], paramInt2 += AbstractDiagram.INCREMENTY[paramInt4], paramInt3, paramInt4, ++paramInt5, true, paramc));
  }
  
  private boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, c paramc) {
    if (paramInt1 < paramc.a || paramInt1 >= paramc.a + paramc.c || paramInt2 < paramc.b || paramInt2 >= paramc.b + paramc.d)
      return false; 
    long l1 = this.b.links[paramInt1][paramInt2];
    if ((l1 & 0x800000L) != 0L || (l1 & 0xF00000L) == 2097152L)
      return false; 
    if ((l1 & 0x20000L) != 0L) {
      if ((l1 & AbstractDiagram.MAP_COST) == AbstractDiagram.COST_END) {
        if (this.i == -1 || 2 * paramInt3 + paramInt5 < 2 * this.i + this.j) {
          this.f = paramInt1;
          this.g = paramInt2;
          this.h = paramInt4;
          this.i = paramInt3;
          this.j = paramInt5;
        } 
        this.e = true;
      } 
      return false;
    } 
    if ((l1 & 0x10000L) != 0L)
      return false; 
    if ((l1 & 0xF00000L) != 0L) {
      long l3 = l1 >> 12L & 0x3L;
      long l4 = l1 >> 10L & 0x3L;
      if ((l1 & 0xF00000L) == 3145728L)
        return false; 
      if (l3 != l4)
        return false; 
      if ((l3 == 0L || l3 == 2L) && (paramInt4 == 0 || paramInt4 == 2))
        return false; 
      if ((l3 == 1L || l3 == 3L) && (paramInt4 == 1 || paramInt4 == 3))
        return false; 
      paramInt3 += 2;
    } 
    long l2 = l1 & AbstractDiagram.MAP_COST;
    if (paramInt3 < l2 || l2 == 0L || (l2 == paramInt3 && this.b.lengthTable[paramInt1][paramInt2] >= paramInt5)) {
      this.b.links[paramInt1][paramInt2] = l1 & 0xFFFFFFFFFFFFFC00L | paramInt3 | paramInt4 << 8L;
      this.b.lengthTable[paramInt1][paramInt2] = paramInt5;
      if (paramBoolean)
        if (paramInt4 == 0 || paramInt4 == 2) {
          a(paramInt1 - 1, paramInt2, paramInt3 + 1, 3, paramInt5 + 1, false, paramc);
          a(paramInt1 + 1, paramInt2, paramInt3 + 1, 1, paramInt5 + 1, false, paramc);
        } else {
          a(paramInt1, paramInt2 + 1, paramInt3 + 1, 2, paramInt5 + 1, false, paramc);
          a(paramInt1, paramInt2 - 1, paramInt3 + 1, 0, paramInt5 + 1, false, paramc);
        }  
      return true;
    } 
    return false;
  }
  
  private void a(Relation paramRelation, int paramInt1, int paramInt2, int paramInt3, c paramc) {
    if (!a && paramRelation == null)
      throw new AssertionError(); 
    int i = paramInt1, j = paramInt2, k = paramInt3;
    long l1 = this.b.links[paramInt1][paramInt2];
    long l2 = (paramRelation.hasFlag(1) ? 268435456L : 0L) | (paramRelation.hasFlag(2) ? 536870912L : 0L) | ((paramRelation.getRelationType() == RelationType.c) ? 134217728L : 0L) | (paramRelation.getRelationCardinality()).h | (paramRelation.isVirtual() ? 68719476736L : 0L) | (paramRelation.isDeduced() ? 274877906944L : 0L) | ((paramRelation instanceof com.wisecoders.dbs.schema.ChildEntityRelation) ? 1073741824L : 0L);
    if (paramRelation instanceof Line) {
      Shape shape = ((Line)paramRelation).a();
      l2 |= 0x2000000000L | ColorUtil.a(shape.c()) << 40L;
    } 
    if ((l1 & 0xF00000L) == 1048576L) {
      this.b.links[paramInt1][paramInt2] = l1 & 0xFFF0FFFFFL | 0x300000L | l2;
    } else {
      this.b.links[paramInt1][paramInt2] = 0x800000L | paramInt3 << 12L | paramInt3 << 10L | l2;
    } 
    while ((paramInt1 += AbstractDiagram.DECREMENTX[paramInt3]) >= paramc.a && paramInt1 < paramc.a + paramc.c && (paramInt2 += AbstractDiagram.DECREMENTY[paramInt3]) >= paramc.b && paramInt2 < paramc.b + paramc.d) {
      l1 = this.b.links[paramInt1][paramInt2];
      int m = (int)(l1 >> 8L & 0x3L);
      if ((l1 & 0x20000L) > 0L) {
        String str;
        this.b.links[paramInt1][paramInt2] = 0x900000L | (m << 12) | paramInt3 << 10L | l2;
        if (paramRelation.getEntity().isChildEntity()) {
          str = paramRelation.getEntity().isChildEntityArray() ? "Ary" : "Obj";
        } else if (paramRelation instanceof Line) {
          str = paramRelation.getName();
        } else {
          switch (DiagramRouterTask$1.a[this.b.getLineTextType().ordinal()]) {
            case 1:
            
            case 2:
            
            case 3:
            
            default:
              break;
          } 
          str = "";
        } 
        this.b.relationPositions.put(paramRelation, new RelationPosition(paramInt1, paramInt2, paramInt3, i, j, k, str));
        return;
      } 
      if ((l1 & 0xF00000L) == 0L) {
        this.b.links[paramInt1][paramInt2] = l1 & 0xFFFFFFFFFFFDFC00L | 0x100000L | (m << 12) | paramInt3 << 10L | l2;
        paramInt3 = m;
        continue;
      } 
      this.b.links[paramInt1][paramInt2] = this.b.links[paramInt1][paramInt2] & 0xFFF0FFFFFL | 0x200000L | l2;
    } 
  }
}
