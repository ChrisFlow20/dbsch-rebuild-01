package com.wisecoders.dbs.diagram.model;

import java.beans.Transient;

public class Point {
  public double a;
  
  public double b;
  
  public Point() {
    this(0.0D, 0.0D);
  }
  
  public Point(Point paramPoint) {
    this(paramPoint.a, paramPoint.b);
  }
  
  public Point(double paramDouble1, double paramDouble2) {
    this.a = paramDouble1;
    this.b = paramDouble2;
  }
  
  public double a() {
    return this.a;
  }
  
  public double b() {
    return this.b;
  }
  
  @Transient
  public Point c() {
    return new Point(this.a, this.b);
  }
  
  public void a(Point paramPoint) {
    a(paramPoint.a, paramPoint.b);
  }
  
  public void a(double paramDouble1, double paramDouble2) {
    b(paramDouble1, paramDouble2);
  }
  
  public void b(double paramDouble1, double paramDouble2) {
    this.a = paramDouble1;
    this.b = paramDouble2;
  }
  
  public void c(double paramDouble1, double paramDouble2) {
    this.a += paramDouble1;
    this.b += paramDouble2;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof Point) {
      Point point = (Point)paramObject;
      return (this.a == point.a && this.b == point.b);
    } 
    return super.equals(paramObject);
  }
  
  public String toString() {
    return getClass().getName() + "[x=" + getClass().getName() + ",y=" + this.a + "]";
  }
}
