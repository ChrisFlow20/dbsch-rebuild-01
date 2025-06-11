package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.util.print.AnimatedGifEncoder;
import com.wisecoders.dbs.sys.Log;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;

public class Snapshot {
  private static final double a = 1280.0D;
  
  private static final double b = 720.0D;
  
  private static final ByteArrayOutputStream c = new ByteArrayOutputStream();
  
  private static final AnimatedGifEncoder d = new AnimatedGifEncoder();
  
  private static final int e = 15;
  
  private static int f = 0;
  
  public static void a(Scene paramScene) {
    if (paramScene != null)
      a((Node)paramScene.getRoot()); 
  }
  
  public static void a(Node paramNode) {
    if (paramNode != null && f < 15)
      try {
        double d = Math.min(1280.0D / (1.0D + paramNode.getBoundsInLocal().getWidth()), 720.0D / (1.0D + paramNode.getBoundsInLocal().getHeight()));
        WritableImage writableImage1 = new WritableImage(1280, 720);
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setTransform((Transform)Transform.scale(d, d));
        WritableImage writableImage2 = paramNode.snapshot(snapshotParameters, writableImage1);
        if (f == 0) {
          d.a(c);
          d.a(2500);
          d.c(500);
        } 
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage((Image)writableImage2, null);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.GREEN);
        graphics.fillOval(775, 0, 20, 20);
        graphics.setColor(Color.WHITE);
        graphics.drawString("" + f, 780, 12);
        d.a(bufferedImage);
        f++;
      } catch (Throwable throwable) {
        Log.h();
      }  
  }
  
  public static void a() {
    c.reset();
    f = 0;
  }
  
  public static boolean b() {
    return (c.size() > 0);
  }
  
  public static byte[] c() {
    return c.toByteArray();
  }
}
