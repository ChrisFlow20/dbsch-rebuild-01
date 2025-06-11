package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

class i extends Stage {
  private final Canvas a = new Canvas();
  
  private final Robot b = new Robot();
  
  private double c;
  
  private double d;
  
  private double e;
  
  private double f;
  
  private double g;
  
  private double h;
  
  private double i;
  
  private double j;
  
  private j k = j.a;
  
  private File l;
  
  private final Path m;
  
  i(Screen paramScreen, Path paramPath) {
    this.m = paramPath;
    initStyle(StageStyle.TRANSPARENT);
    setAlwaysOnTop(true);
    StackPane stackPane = new StackPane();
    stackPane.setStyle("-fx-background-color:transparent;");
    stackPane.getChildren().add(this.a);
    Rectangle2D rectangle2D = paramScreen.getVisualBounds();
    Scene scene = new Scene((Parent)stackPane, rectangle2D.getWidth(), rectangle2D.getHeight(), (Paint)Color.TRANSPARENT);
    scene.setFill((Paint)Color.TRANSPARENT);
    setScene(scene);
    setX(rectangle2D.getMinX());
    setY(rectangle2D.getMinY());
    scene.addEventHandler(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.ESCAPE)
            close(); 
        });
    this.a.setWidth(rectangle2D.getWidth());
    this.a.setHeight(rectangle2D.getHeight());
    this.a.setOnMouseMoved(paramMouseEvent -> {
          this.c = paramMouseEvent.getX();
          this.d = paramMouseEvent.getY();
          c();
        });
    this.a.setOnMousePressed(paramMouseEvent -> {
          if (paramMouseEvent.getButton() == MouseButton.PRIMARY) {
            this.c = paramMouseEvent.getX();
            this.d = paramMouseEvent.getY();
            this.g = this.b.getMouseX();
            this.h = this.b.getMouseY();
          } 
        });
    this.a.setOnMouseDragged(paramMouseEvent -> {
          if (paramMouseEvent.getButton() == MouseButton.PRIMARY) {
            this.k = j.b;
            this.e = paramMouseEvent.getX();
            this.f = paramMouseEvent.getY();
            this.i = this.b.getMouseX();
            this.j = this.b.getMouseY();
            c();
          } 
        });
    this.a.setOnMouseReleased(paramMouseEvent -> {
          if (this.k == j.b) {
            try {
              if (b()) {
                Media media = new Media(String.valueOf(i.class.getResource("/icons/sounds/camera_shooter.mp3")));
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
              } 
            } catch (Throwable throwable) {
              Log.b(throwable);
            } 
            close();
          } 
        });
    Platform.runLater(this::c);
  }
  
  private boolean b() {
    double d1 = Math.min(this.g, this.i);
    double d2 = Math.min(this.h, this.j);
    double d3 = Math.abs(this.i - this.g - 1.0D);
    double d4 = Math.abs(this.j - this.h - 1.0D);
    if (d3 > 0.0D && d4 > 0.0D) {
      WritableImage writableImage = this.b.getScreenCapture(null, d1, d2, d3, d4, false);
      byte b = 0;
      File file;
      while ((file = this.m.resolve("snapshot_" + b + ".png").toFile()).exists())
        b++; 
      try {
        ImageIO.setUseCache(false);
        ImageIO.write(SwingFXUtils.fromFXImage((Image)writableImage, null), "png", file);
        this.l = file;
      } catch (IOException iOException) {
        Log.b(iOException);
        (new Alert$(Alert.AlertType.ERROR))
          .a(getScene())
          .a("Error")
          .c(iOException.toString())
          .a(iOException)
          .show();
      } 
      return true;
    } 
    return false;
  }
  
  private void c() {
    if (this.a.isVisible() && this.a.getScene() != null) {
      GraphicsContext graphicsContext = this.a.getGraphicsContext2D();
      graphicsContext.clearRect(0.0D, 0.0D, this.a.getWidth(), this.a.getHeight());
      graphicsContext.setFill((Paint)Color.rgb(0, 0, 0, 0.2D));
      graphicsContext.fillRect(0.0D, 0.0D, this.a.getWidth(), this.a.getHeight());
      if (this.k == j.a) {
        graphicsContext.setStroke((Paint)Color.web("#672824"));
        graphicsContext.setFill((Paint)Color.web("#672824"));
        graphicsContext.setLineWidth(1.0D);
        graphicsContext.strokeLine(this.c, 0.0D, this.c, this.a.getHeight());
        graphicsContext.strokeLine(0.0D, this.d, this.a.getWidth(), this.d);
        graphicsContext.fillText("Drag to Select Screen Area", this.c + 10.0D, this.d - 15.0D);
      } else {
        graphicsContext.clearRect(this.c, this.d, Math.abs(this.e - this.c), Math.abs(this.f - this.d));
        graphicsContext.setStroke((Paint)Color.RED);
        graphicsContext.setLineWidth(1.0D);
        graphicsContext.strokeRect(this.c - 1.0D, this.d - 1.0D, Math.abs(this.e - this.c) + 2.0D, Math.abs(this.f - this.d) + 2.0D);
      } 
    } 
  }
  
  File a() {
    return this.l;
  }
}
