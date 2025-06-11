package com.wisecoders.dbs.dialogs.system;

import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class FxAnimatedLogo extends Group {
  private double a;
  
  private double b;
  
  private double c;
  
  private double d;
  
  private static final StyleablePropertyFactory e = new StyleablePropertyFactory(getClassCssMetaData());
  
  private final StyleableProperty f;
  
  private final StyleableProperty g;
  
  private final StyleableProperty h;
  
  private final StyleableProperty i;
  
  private final Group j;
  
  private final PointLight k;
  
  private final AmbientLight l;
  
  private static final int m = 4;
  
  private static final double n = 5.0D;
  
  private static final double o = 6.0D;
  
  private static final double p = 12.0D;
  
  private static final String q = "Main";
  
  public List getCssMetaData() {
    return e.getCssMetaData();
  }
  
  public FxAnimatedLogo(boolean paramBoolean) {
    this.f = e.createStyleableColorProperty((Styleable)this, "point-light-color", "point-light-color", paramFxAnimatedLogo -> paramFxAnimatedLogo.f, Color.web("#cac6ab"));
    this.g = e.createStyleableColorProperty((Styleable)this, "ambient-light-color", "ambient-light-color", paramFxAnimatedLogo -> paramFxAnimatedLogo.g, Color.web("#818892"));
    this.h = e.createStyleableColorProperty((Styleable)this, "main-cube-color", "main-cube-color", paramFxAnimatedLogo -> paramFxAnimatedLogo.h, Color.web("#497dab"));
    this.i = e.createStyleableColorProperty((Styleable)this, "secondary-cube-color", "secondary-cube-color", paramFxAnimatedLogo -> paramFxAnimatedLogo.i, Color.web("#d9dfe3"));
    this.j = new Group();
    this.k = new PointLight();
    this.l = new AmbientLight((Color)this.g.getValue());
    getStyleClass().addAll((Object[])new String[] { "animated-logo" });
    if (paramBoolean)
      getTransforms().addAll((Object[])new Transform[] { (Transform)new Scale(2.0D, 2.0D), (Transform)new Translate(-150.0D, -150.0D) }); 
    for (byte b = 0; b < 64; b++)
      this.j.getChildren().add(a(b)); 
    this.k.getTransforms().add(new Translate(-40.0D, -40.0D, -70.0D));
    this.j.getChildren().addAll((Object[])new Node[0]);
    Rotate rotate1 = new Rotate(-12.0D, 0.0D, 0.0D, 0.0D, Rotate.X_AXIS);
    Rotate rotate2 = new Rotate(-72.0D, 0.0D, 0.0D, 0.0D, Rotate.Z_AXIS);
    this.j.getTransforms().addAll((Object[])new Transform[] { (Transform)rotate1, (Transform)rotate2 });
    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(27.0D), (Node)this.j);
    rotateTransition.setAxis(Rotate.Y_AXIS);
    rotateTransition.setFromAngle(360.0D);
    rotateTransition.setToAngle(0.0D);
    rotateTransition.setCycleCount(-1);
    rotateTransition.setInterpolator(Interpolator.LINEAR);
    rotateTransition.play();
    getChildren().addAll((Object[])new Node[] { (Node)this.j, (Node)new AmbientLight((Color)this.g.getValue()), (Node)this.k });
    setOnMousePressed(paramMouseEvent -> {
          this.c = paramMouseEvent.getSceneX();
          this.d = paramMouseEvent.getSceneY();
        });
    setOnMouseDragged(paramMouseEvent -> {
          this.a = paramMouseEvent.getSceneX();
          this.b = paramMouseEvent.getSceneY();
          paramRotate1.setAngle(paramRotate1.getAngle() - this.b - this.d);
          paramRotate2.setAngle(paramRotate2.getAngle() + this.a - this.c);
          this.c = this.a;
          this.d = this.b;
        });
  }
  
  public SubScene a() {
    PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
    perspectiveCamera.setNearClip(0.4D);
    perspectiveCamera.setFarClip(400.0D);
    perspectiveCamera.setTranslateZ(-120.0D);
    SubScene subScene = new SubScene((Parent)this, 500.0D, 500.0D, true, SceneAntialiasing.BALANCED);
    subScene.setFill((Paint)Color.TRANSPARENT);
    subScene.setCamera((Camera)perspectiveCamera);
    return subScene;
  }
  
  public void requestLayout() {
    super.requestLayout();
    for (Node node : this.j.getChildren()) {
      if (node instanceof Box) {
        Box box = (Box)node;
        box.setMaterial((Material)new PhongMaterial((box.getUserData() == "Main") ? (Color)this.h.getValue() : (Color)this.i.getValue()));
      } 
    } 
    this.k.setColor((Color)this.f.getValue());
    this.l.setColor((Color)this.g.getValue());
  }
  
  private Box a(int paramInt) {
    Box box = new Box(5.0D, 5.0D, 5.0D);
    int i = paramInt % 4;
    int j = paramInt / 4 % 4;
    int k = paramInt / 16 % 4;
    boolean bool1 = (i == 0) ? true : false;
    boolean bool2 = (j == 0) ? true : false;
    boolean bool3 = (k == 0) ? true : false;
    boolean bool4 = (i == 3) ? true : false;
    boolean bool5 = (j == 3) ? true : false;
    boolean bool6 = (k == 3) ? true : false;
    boolean bool7 = (bool4 || bool5 || bool6) ? true : false;
    if (paramInt == 0 || (Math.random() < 0.1D && bool7))
      box.setUserData("Main"); 
    box.translateXProperty().set((Math.random() - 0.5D) * 40.0D);
    box.translateYProperty().set((Math.random() - 0.5D) * 40.0D);
    box.translateZProperty().set((Math.random() - 0.5D) * 30.0D);
    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8D), (Node)box);
    translateTransition.setDelay(Duration.seconds(2.0D + paramInt / 80.0D));
    byte b = 10;
    translateTransition.setToX(i * 6.0D - 12.0D + (bool4 ? (Math.random() * b) : 0.0D) + ((bool1 && (bool5 || bool6)) ? (-Math.random() * b) : 0.0D));
    translateTransition.setToY(j * 6.0D - 12.0D + (bool5 ? (Math.random() * b) : 0.0D) + ((bool2 && (bool4 || bool6)) ? (-Math.random() * b) : 0.0D));
    translateTransition.setToZ(k * 6.0D - 12.0D + (bool6 ? (Math.random() * b) : 0.0D) + ((bool3 && (bool4 || bool5)) ? (-Math.random() * b) : 0.0D));
    translateTransition.play();
    return box;
  }
}
