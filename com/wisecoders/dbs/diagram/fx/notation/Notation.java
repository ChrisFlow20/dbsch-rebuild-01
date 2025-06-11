package com.wisecoders.dbs.diagram.fx.notation;

import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.schema.RelationCardinality;
import java.io.PrintWriter;
import javafx.scene.canvas.GraphicsContext;

public abstract class Notation {
  public float c = -1.0F;
  
  protected static final float d = 0.07F;
  
  float e;
  
  float f;
  
  float g;
  
  float h;
  
  float i;
  
  float j;
  
  float k;
  
  float l;
  
  float m;
  
  float n;
  
  float o;
  
  float p;
  
  float q;
  
  public Notation() {
    a(Diagram.cell);
  }
  
  public abstract RelationCardinality a(RelationCardinality paramRelationCardinality, boolean paramBoolean1, boolean paramBoolean2);
  
  public void a(float paramFloat) {
    this.c = paramFloat;
    this.e = paramFloat / 2.0F;
    this.f = paramFloat / 3.0F;
    this.g = paramFloat / 4.0F;
    this.h = paramFloat / 5.0F;
    this.i = paramFloat / 6.0F;
    this.j = paramFloat / 7.0F;
    this.k = paramFloat / 8.0F;
    this.l = 2.0F * paramFloat / 3.0F;
    this.m = 3.0F * paramFloat / 4.0F;
    this.n = 5.0F * paramFloat / 6.0F;
    this.o = paramFloat / 10.0F;
    this.p = paramFloat / 2.5F;
    this.q = paramFloat - this.p;
  }
  
  public final void a(GraphicsContext paramGraphicsContext, RelationCardinality paramRelationCardinality, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt) {
    paramGraphicsContext.translate(this.e, this.e);
    if (paramBoolean1) {
      paramRelationCardinality = a(paramRelationCardinality, paramBoolean2, paramBoolean3);
      paramInt = a(paramInt);
    } 
    switch (paramInt) {
      case 0:
        paramGraphicsContext.rotate(90.0D);
        a(paramGraphicsContext, paramRelationCardinality);
        paramGraphicsContext.rotate(-90.0D);
        break;
      case 1:
        paramGraphicsContext.rotate(180.0D);
        a(paramGraphicsContext, paramRelationCardinality);
        paramGraphicsContext.rotate(-180.0D);
        break;
      case 2:
        paramGraphicsContext.rotate(270.0D);
        a(paramGraphicsContext, paramRelationCardinality);
        paramGraphicsContext.rotate(-270.0D);
        break;
      case 3:
        a(paramGraphicsContext, paramRelationCardinality);
        break;
    } 
    paramGraphicsContext.translate(-this.e, -this.e);
  }
  
  public abstract void a(GraphicsContext paramGraphicsContext, RelationCardinality paramRelationCardinality);
  
  public abstract boolean a();
  
  protected void c(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(-this.e, 0.0D);
    paramGraphicsContext.lineTo(this.e, 0.0D);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void d(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(0.0D, 0.0D);
    paramGraphicsContext.lineTo(this.e, 0.0D);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void e(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(-this.e, 0.0D);
    paramGraphicsContext.lineTo((1.0F * this.h - this.e), 0.0D);
    paramGraphicsContext.moveTo((2.0F * this.h - this.e), 0.0D);
    paramGraphicsContext.lineTo((3.0F * this.h - this.e), 0.0D);
    paramGraphicsContext.moveTo((4.0F * this.h - this.e), 0.0D);
    paramGraphicsContext.lineTo(this.e, 0.0D);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void a(GraphicsContext paramGraphicsContext, float paramFloat) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo((paramFloat - this.e), -this.f);
    paramGraphicsContext.lineTo((paramFloat - this.e), (this.n - this.e));
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void f(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(0.0D, 0.0D);
    paramGraphicsContext.lineTo(this.e, 0.0D);
    paramGraphicsContext.moveTo(this.e, -this.f);
    paramGraphicsContext.lineTo(0.0D, 0.0D);
    paramGraphicsContext.lineTo(this.e, (this.n - this.e));
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void a(GraphicsContext paramGraphicsContext, boolean paramBoolean) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(this.e, 0.0D);
    paramGraphicsContext.lineTo(-this.e, 0.0D);
    paramGraphicsContext.moveTo(0.0D, -this.i);
    paramGraphicsContext.lineTo(this.e, 0.0D);
    paramGraphicsContext.lineTo(0.0D, this.i);
    if (paramBoolean)
      paramGraphicsContext.fill(); 
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void g(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(-this.e, 0.0D);
    paramGraphicsContext.quadraticCurveTo(-this.g, -this.e, 0.0D, 0.0D);
    paramGraphicsContext.quadraticCurveTo(-this.g, this.e, -this.e, 0.0D);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void b(GraphicsContext paramGraphicsContext, boolean paramBoolean) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(this.i, 0.0D);
    paramGraphicsContext.quadraticCurveTo(this.f, (this.h - this.e), this.e, 0.0D);
    paramGraphicsContext.quadraticCurveTo(this.f, this.f, this.i, 0.0D);
    if (paramBoolean)
      paramGraphicsContext.fill(); 
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  protected void c(GraphicsContext paramGraphicsContext, boolean paramBoolean) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(this.e, 0.0D);
    paramGraphicsContext.quadraticCurveTo(this.f, (this.h - this.e), this.i, 0.0D);
    paramGraphicsContext.quadraticCurveTo(this.f, this.f, this.e, 0.0D);
    if (paramBoolean)
      paramGraphicsContext.fill(); 
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  public abstract void a(PrintWriter paramPrintWriter, RelationCardinality paramRelationCardinality);
  
  public void a(GraphicsContext paramGraphicsContext, int paramInt1, int paramInt2) {
    switch (paramInt1) {
      case 0:
        switch (paramInt2) {
          case 0:
          case 2:
            a_(paramGraphicsContext);
            break;
          case 1:
            a(paramGraphicsContext, 270.0D);
            break;
          case 3:
            a(paramGraphicsContext, 0.0D);
            break;
        } 
        break;
      case 1:
        switch (paramInt2) {
          case 0:
            a(paramGraphicsContext, 90.0D);
            break;
          case 2:
            a(paramGraphicsContext, 0.0D);
            break;
          case 1:
          case 3:
            b(paramGraphicsContext);
            break;
        } 
        break;
      case 2:
        switch (paramInt2) {
          case 1:
            a(paramGraphicsContext, 180.0D);
            break;
          case 3:
            a(paramGraphicsContext, 90.0D);
            break;
          case 0:
          case 2:
            a_(paramGraphicsContext);
            break;
        } 
        break;
      case 3:
        switch (paramInt2) {
          case 0:
            a(paramGraphicsContext, 180.0D);
            break;
          case 2:
            a(paramGraphicsContext, 270.0D);
            break;
          case 1:
          case 3:
            b(paramGraphicsContext);
            break;
        } 
        break;
    } 
  }
  
  public void a_(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(this.e, 0.0D);
    paramGraphicsContext.lineTo(this.e, this.c);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  public void b(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(0.0D, this.e);
    paramGraphicsContext.lineTo(this.c, this.e);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  public void a(GraphicsContext paramGraphicsContext, double paramDouble) {
    paramGraphicsContext.translate(this.e, this.e);
    paramGraphicsContext.rotate(paramDouble);
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(0.0D, -this.e);
    paramGraphicsContext.quadraticCurveTo((0.07F * this.c), (0.07F * this.c), this.e, 0.0D);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
    paramGraphicsContext.rotate(-paramDouble);
    paramGraphicsContext.translate(-this.e, -this.e);
  }
  
  public void h(GraphicsContext paramGraphicsContext) {
    paramGraphicsContext.beginPath();
    paramGraphicsContext.moveTo(0.0D, this.e);
    paramGraphicsContext.lineTo(this.g, this.e);
    paramGraphicsContext.quadraticCurveTo(this.e, this.j, this.m, this.e);
    paramGraphicsContext.lineTo(this.c, this.e);
    paramGraphicsContext.stroke();
    paramGraphicsContext.closePath();
  }
  
  public static int a(int paramInt) {
    switch (paramInt) {
      case 0:
        return 2;
      case 1:
        return 3;
      case 2:
        return 0;
    } 
    return 1;
  }
}
