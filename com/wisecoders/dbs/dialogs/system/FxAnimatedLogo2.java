package com.wisecoders.dbs.dialogs.system;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class FxAnimatedLogo2 extends SubScene {
  private static final Color a = Color.web("#17afde");
  
  private static final Color b = Color.web("#eeeeee");
  
  private double c;
  
  private double d;
  
  private double e;
  
  private double f;
  
  private static final int g = 14;
  
  private static final int h = 10;
  
  private static final int i = 7;
  
  public FxAnimatedLogo2(Group paramGroup) {
    super((Parent)paramGroup, 450.0D, 350.0D, true, SceneAntialiasing.BALANCED);
    setFill((Paint)Color.TRANSPARENT);
    PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
    perspectiveCamera.setNearClip(0.3D);
    perspectiveCamera.setFarClip(400.0D);
    perspectiveCamera.setTranslateZ(-320.0D);
    setCamera((Camera)perspectiveCamera);
    Group group = new Group();
    Label label = new Label("Drag me");
    label.setTranslateX(-30.0D);
    label.setTranslateY(30.0D);
    group.getChildren().add(label);
    for (byte b = 0; b < 7; b++) {
      for (byte b1 = 0; b1 < 7; b1++) {
        for (byte b2 = 0; b2 < 7; b2++)
          group.getChildren().add(a(b2, b1, b)); 
      } 
    } 
    PointLight pointLight = new PointLight();
    pointLight.setColor(Color.web("#f5f6eb"));
    pointLight.getTransforms().add(new Translate(-250.0D, 0.0D, -200.0D));
    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(15.0D), (Node)group);
    rotateTransition.setAxis(Rotate.Y_AXIS);
    rotateTransition.setFromAngle(360.0D);
    rotateTransition.setToAngle(0.0D);
    rotateTransition.setCycleCount(-1);
    rotateTransition.setInterpolator(Interpolator.LINEAR);
    rotateTransition.play();
    paramGroup.getChildren().addAll((Object[])new Node[] { (Node)group, (Node)new AmbientLight(Color.web("#777777")), (Node)pointLight });
    Rotate rotate1 = new Rotate(0.0D, 0.0D, 0.0D, 0.0D, Rotate.X_AXIS);
    Rotate rotate2 = new Rotate(0.0D, 0.0D, 0.0D, 0.0D, Rotate.Z_AXIS);
    group.getTransforms().addAll((Object[])new Transform[] { (Transform)rotate1, (Transform)rotate2 });
    setOnMousePressed(paramMouseEvent -> {
          this.e = paramMouseEvent.getSceneX();
          this.f = paramMouseEvent.getSceneY();
        });
    setOnMouseDragged(paramMouseEvent -> {
          this.c = paramMouseEvent.getSceneX();
          this.d = paramMouseEvent.getSceneY();
          paramRotate1.setAngle(paramRotate1.getAngle() - this.d - this.f);
          paramRotate2.setAngle(paramRotate2.getAngle() + this.c - this.e);
          this.e = this.c;
          this.f = this.d;
        });
  }
  
  private Box a(int paramInt1, int paramInt2, int paramInt3) {
    Box box = new Box(10.0D, 10.0D, 10.0D);
    box.setMaterial((Material)new PhongMaterial((paramInt1 < 3 && paramInt2 < 3 && paramInt3 < 3) ? a : b));
    box.translateXProperty().set((-50 + paramInt1 * 14));
    box.translateYProperty().set((-50 + paramInt2 * 14));
    box.translateZProperty().set((-50 + paramInt3 * 14));
    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.7D), (Node)box);
    scaleTransition.setDelay(Duration.seconds(paramInt3 / 5.0D));
    scaleTransition.setToX(0.1D);
    scaleTransition.setToY(0.1D);
    scaleTransition.setToZ(0.1D);
    scaleTransition.setAutoReverse(true);
    scaleTransition.setCycleCount(-1);
    scaleTransition.setInterpolator(Interpolator.EASE_IN);
    scaleTransition.play();
    return box;
  }
}
