package com.wisecoders.dbs.sys.fx;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Point3D;
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
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Cube3D2 {
  public static final int a = 2;
  
  public static final int b = 6;
  
  public static final float c = 0.071428575F;
  
  public static final float d = 0.21428572F;
  
  public static final float e = 0.35714287F;
  
  public static final float f = 0.5F;
  
  public static final float g = 0.64285713F;
  
  public static final float h = 0.78571427F;
  
  public static final float i = 0.9285714F;
  
  private double j;
  
  private double k;
  
  private double l;
  
  private double m;
  
  public Cube3D2() {
    Group group1 = new Group();
    Scene scene = new Scene((Parent)group1, 600.0D, 600.0D, true, SceneAntialiasing.BALANCED);
    PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
    perspectiveCamera.setTranslateZ(-10.0D);
    scene.setCamera((Camera)perspectiveCamera);
    PhongMaterial phongMaterial1 = new PhongMaterial();
    phongMaterial1.setDiffuseColor(Color.web("17afde"));
    PhongMaterial phongMaterial2 = new PhongMaterial();
    phongMaterial2.setDiffuseColor(Color.web("eeeeee"));
    Group group2 = new Group();
    AtomicInteger atomicInteger = new AtomicInteger();
    D.forEach(paramArrayOfint -> {
          MeshView meshView = new MeshView();
          meshView.setMesh((Mesh)a(paramArrayOfint));
          meshView.setMaterial((paramAtomicInteger.intValue() < 1) ? (Material)paramPhongMaterial1 : (Material)paramPhongMaterial2);
          Point3D point3D = E.get(paramAtomicInteger.getAndIncrement());
          meshView.getTransforms().addAll((Object[])new Transform[] { (Transform)new Translate(point3D.getX(), point3D.getY(), point3D.getZ()) });
          paramGroup.getChildren().add(meshView);
        });
    Rotate rotate1 = new Rotate(0.0D, 0.0D, 0.0D, 0.0D, Rotate.X_AXIS);
    Rotate rotate2 = new Rotate(0.0D, 0.0D, 0.0D, 0.0D, Rotate.Y_AXIS);
    group2.getTransforms().addAll((Object[])new Transform[] { (Transform)rotate1, (Transform)rotate2 });
    PointLight pointLight = new PointLight();
    pointLight.setColor(Color.web("#ffffff"));
    pointLight.getTransforms().add(new Translate(-500.0D, -200.0D, 0.0D));
    group1.getChildren().addAll((Object[])new Node[] { (Node)group2 });
    scene.setOnMousePressed(paramMouseEvent -> {
          this.l = paramMouseEvent.getSceneX();
          this.m = paramMouseEvent.getSceneY();
        });
    scene.setOnMouseDragged(paramMouseEvent -> {
          this.j = paramMouseEvent.getSceneX();
          this.k = paramMouseEvent.getSceneY();
          paramRotate1.setAngle(paramRotate1.getAngle() - this.k - this.m);
          paramRotate2.setAngle(paramRotate2.getAngle() + this.j - this.l);
          this.l = this.j;
          this.m = this.k;
        });
    Stage stage = new Stage();
    stage.setTitle("Simple Rubik's Cube - JavaFX");
    stage.setScene(scene);
    stage.show();
  }
  
  private static final int[] n = new int[] { 2, 2, 2, 2, 2, 2 };
  
  private static final int[] o = new int[] { 6, 6, 6, 6, 6, 6 };
  
  private static final int[] p = new int[] { 6, 6, 6, 6, 6, 6 };
  
  private static final int[] q = new int[] { 6, 6, 6, 6, 6, 6 };
  
  private static final Point3D r = new Point3D(-0.55D, 0.55D, -0.55D);
  
  private static final Point3D s = new Point3D(0.55D, 0.55D, -0.55D);
  
  private static final Point3D t = new Point3D(-0.55D, -0.55D, -0.55D);
  
  private static final Point3D u = new Point3D(0.55D, -0.55D, -0.55D);
  
  private static final int[] v = new int[] { 6, 6, 6, 6, 6, 6 };
  
  private static final int[] w = new int[] { 6, 6, 6, 6, 6, 6 };
  
  private static final int[] x = new int[] { 6, 6, 6, 6, 6, 6 };
  
  private static final int[] y = new int[] { 6, 6, 6, 6, 6, 6 };
  
  private static final Point3D z = new Point3D(-0.55D, 0.55D, 0.55D);
  
  private static final Point3D A = new Point3D(0.55D, 0.55D, 0.55D);
  
  private static final Point3D B = new Point3D(-0.55D, -0.55D, 0.55D);
  
  private static final Point3D C = new Point3D(0.55D, -0.55D, 0.55D);
  
  private static final List D = Arrays.asList(new int[][] { n, o, p, q, v, w, x, y });
  
  private static final List E = Arrays.asList(new Point3D[] { r, s, t, u, z, A, B, C });
  
  private TriangleMesh a(int[] paramArrayOfint) {
    TriangleMesh triangleMesh = new TriangleMesh();
    triangleMesh.getPoints().addAll(new float[] { 
          0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 0.5F, 0.5F, -0.5F, 0.5F, 
          -0.5F, -0.5F, -0.5F, 0.5F, 0.5F, -0.5F, -0.5F, 0.5F, -0.5F, 0.5F, 
          -0.5F, -0.5F, -0.5F, -0.5F });
    triangleMesh.getTexCoords().addAll(new float[] { 
          0.071428575F, 0.5F, 0.21428572F, 0.5F, 0.35714287F, 0.5F, 0.5F, 0.5F, 0.64285713F, 0.5F, 
          0.78571427F, 0.5F, 0.9285714F, 0.5F });
    triangleMesh.getFaces().addAll(new int[] { 
          2, paramArrayOfint[0], 3, paramArrayOfint[0], 6, paramArrayOfint[0], 3, paramArrayOfint[0], 7, paramArrayOfint[0], 
          6, paramArrayOfint[0], 0, paramArrayOfint[1], 1, paramArrayOfint[1], 2, paramArrayOfint[1], 2, paramArrayOfint[1], 
          1, paramArrayOfint[1], 3, paramArrayOfint[1], 1, paramArrayOfint[2], 5, paramArrayOfint[2], 3, paramArrayOfint[2], 
          5, paramArrayOfint[2], 7, paramArrayOfint[2], 3, paramArrayOfint[2], 0, paramArrayOfint[3], 4, paramArrayOfint[3], 
          1, paramArrayOfint[3], 4, paramArrayOfint[3], 5, paramArrayOfint[3], 1, paramArrayOfint[3], 4, paramArrayOfint[4], 
          6, paramArrayOfint[4], 5, paramArrayOfint[4], 6, paramArrayOfint[4], 7, paramArrayOfint[4], 5, paramArrayOfint[4], 
          0, paramArrayOfint[5], 2, paramArrayOfint[5], 4, paramArrayOfint[5], 2, paramArrayOfint[5], 6, paramArrayOfint[5], 
          4, paramArrayOfint[5] });
    return triangleMesh;
  }
}
