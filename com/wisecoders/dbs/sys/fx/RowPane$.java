package com.wisecoders.dbs.sys.fx;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RowPane$ extends VBox$ {
  private Pos a = Pos.CENTER_LEFT;
  
  private boolean b = false;
  
  public RowPane$ a(boolean paramBoolean) {
    this.b = paramBoolean;
    return this;
  }
  
  public RowPane$ a(int paramInt) {
    super.b(paramInt);
    return this;
  }
  
  public RowPane$ a() {
    super.l();
    return this;
  }
  
  public RowPane$ a(Pos paramPos) {
    this.a = paramPos;
    return this;
  }
  
  public RowPane$ b() {
    setPadding(GridPane$.b);
    return this;
  }
  
  public RowPane$ c() {
    setPadding(new Insets(0.0D, 0.0D, 0.0D, 20.0D));
    return this;
  }
  
  public RowPane$ d() {
    setPadding(GridPane$.c);
    return this;
  }
  
  public RowPane$ e() {
    setPadding(GridPane$.d);
    return this;
  }
  
  public RowPane$ f() {
    setPadding(GridPane$.d);
    setSpacing(8.0D);
    return this;
  }
  
  public RowPane$ g() {
    a();
    return b();
  }
  
  public RowPane$ a(Node paramNode) {
    getChildren().add(paramNode);
    return this;
  }
  
  public RowPane$ a(Node... paramVarArgs) {
    Pane pane = o();
    pane.getChildren().addAll((Object[])paramVarArgs);
    getChildren().addAll((Object[])new Node[] { (Node)pane });
    return this;
  }
  
  public RowPane$ a(List paramList) {
    Pane pane = o();
    pane.getChildren().addAll(paramList);
    getChildren().addAll((Object[])new Node[] { (Node)pane });
    return this;
  }
  
  public RowPane$ b(Node paramNode) {
    VBox.setVgrow(paramNode, Priority.ALWAYS);
    getChildren().add(paramNode);
    return this;
  }
  
  public void c(Node paramNode) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   4: invokeinterface isEmpty : ()Z
    //   9: ifne -> 53
    //   12: aload_0
    //   13: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   16: aload_0
    //   17: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   20: invokeinterface size : ()I
    //   25: iconst_1
    //   26: isub
    //   27: invokeinterface get : (I)Ljava/lang/Object;
    //   32: astore #4
    //   34: aload #4
    //   36: instanceof javafx/scene/layout/Pane
    //   39: ifeq -> 53
    //   42: aload #4
    //   44: checkcast javafx/scene/layout/Pane
    //   47: astore_3
    //   48: aload_3
    //   49: astore_2
    //   50: goto -> 69
    //   53: aload_0
    //   54: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   57: aload_0
    //   58: invokevirtual o : ()Ljavafx/scene/layout/Pane;
    //   61: dup
    //   62: astore_2
    //   63: invokeinterface add : (Ljava/lang/Object;)Z
    //   68: pop
    //   69: aload_2
    //   70: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   73: aload_1
    //   74: invokeinterface add : (Ljava/lang/Object;)Z
    //   79: pop
    //   80: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #94	-> 0
    //   #95	-> 48
    //   #97	-> 53
    //   #99	-> 69
    //   #100	-> 80
  }
  
  public void h() {
    getChildren().add(o());
  }
  
  private Pane o() {
    return this.b ? (Pane)(new FlowPane$()).a(getSpacing()).a(this.a) : (Pane)(new HBox$()).a(getSpacing()).a(this.a);
  }
}
