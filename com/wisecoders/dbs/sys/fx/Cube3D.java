package com.wisecoders.dbs.sys.fx;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Cube3D extends Stage {
  private static final Color a = Color.web("#17afde");
  
  private double b;
  
  private double c;
  
  private double d;
  
  private double e;
  
  public Cube3D() {
    Group group1 = new Group();
    Scene scene = new Scene((Parent)group1, 850.0D, 650.0D, true, SceneAntialiasing.BALANCED);
    scene.setFill((Paint)Color.web("#eeeeee"));
    PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
    perspectiveCamera.setNearClip(0.1D);
    perspectiveCamera.setFarClip(10000.0D);
    perspectiveCamera.setTranslateZ(-120.0D);
    scene.setCamera((Camera)perspectiveCamera);
    Group group2 = new Group();
    Color color = Color.web("#c8ced4");
    for (byte b = 0; b < 3; b++) {
      for (byte b1 = 0; b1 < 3; b1++) {
        for (byte b2 = 0; b2 < 3; b2++)
          group2.getChildren().add(a(b, b1, b2, ((b == 0 && b1 == 0 && b2 == 0) || Math.random() < 0.1D) ? a : color)); 
      } 
    } 
    PointLight pointLight = new PointLight();
    pointLight.setColor(Color.web("#f5f6eb"));
    pointLight.getTransforms().add(new Translate(-150.0D, 0.0D, -200.0D));
    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(15.0D), (Node)group2);
    rotateTransition.setAxis(Rotate.Y_AXIS);
    rotateTransition.setFromAngle(0.0D);
    rotateTransition.setToAngle(360.0D);
    rotateTransition.setCycleCount(-1);
    rotateTransition.setInterpolator(Interpolator.LINEAR);
    rotateTransition.play();
    group1.getChildren().addAll((Object[])new Node[] { (Node)group2, (Node)new AmbientLight(Color.web("#777777")), (Node)pointLight });
    Rotate rotate1 = new Rotate(0.0D, 0.0D, 0.0D, 0.0D, Rotate.X_AXIS);
    Rotate rotate2 = new Rotate(0.0D, 0.0D, 0.0D, 0.0D, Rotate.Z_AXIS);
    group2.getTransforms().addAll((Object[])new Transform[] { (Transform)rotate1, (Transform)rotate2 });
    scene.setOnMousePressed(paramMouseEvent -> {
          this.d = paramMouseEvent.getSceneX();
          this.e = paramMouseEvent.getSceneY();
        });
    scene.setOnMouseDragged(paramMouseEvent -> {
          this.b = paramMouseEvent.getSceneX();
          this.c = paramMouseEvent.getSceneY();
          paramRotate1.setAngle(paramRotate1.getAngle() - this.c - this.e);
          paramRotate2.setAngle(paramRotate2.getAngle() + this.b - this.d);
          this.d = this.b;
          this.e = this.c;
        });
    setTitle("Hello World!");
    setScene(scene);
    show();
  }
  
  private Box a(int paramInt1, int paramInt2, int paramInt3, Color paramColor) {
    Box box = new Box(5.0D, 5.0D, 5.0D);
    box.setMaterial((Material)new PhongMaterial(paramColor));
    box.translateXProperty().set(Math.random() * 40.0D - 20.0D);
    box.translateYProperty().set(Math.random() * 40.0D - 20.0D);
    box.translateZProperty().set(Math.random() * 40.0D - 20.0D);
    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.2D), (Node)box);
    translateTransition.setDelay(Duration.seconds((paramInt1 + paramInt2 + paramInt3) / 2.0D));
    translateTransition.setToX(((paramInt1 - 2) * 6));
    translateTransition.setToY(((paramInt2 - 2) * 6));
    translateTransition.setToZ(((paramInt3 - 2) * 6));
    translateTransition.play();
    if (paramInt1 > 1 || paramInt2 > 1 || paramInt3 > 1) {
      TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(1.2D), (Node)box);
      translateTransition1.setDelay(Duration.seconds(8.0D + (paramInt1 + paramInt2 + paramInt3) / 2.0D));
      translateTransition1.setFromX(((paramInt1 - 2) * 6));
      translateTransition1.setFromY(((paramInt2 - 2) * 6));
      translateTransition1.setFromZ(((paramInt3 - 2) * 6));
      translateTransition1.setToX(Math.random() * 40.0D - 20.0D);
      translateTransition1.setToY(Math.random() * 40.0D - 20.0D);
      translateTransition1.setToZ(Math.random() * 40.0D - 20.0D);
      translateTransition1.setOnFinished(paramActionEvent -> paramTranslateTransition.play());
      translateTransition1.play();
    } 
    return box;
  }
}
