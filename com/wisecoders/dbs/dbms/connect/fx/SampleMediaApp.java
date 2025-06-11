package com.wisecoders.dbs.dbms.connect.fx;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class SampleMediaApp extends Application {
  private final Scene c = new Scene((Parent)new VBox(), 400.0D, 350.0D);
  
  private final File d = new File("~/temp");
  
  protected final ProgressBar a = new ProgressBar(1.0D);
  
  private final TableView e = new TableView();
  
  private final Button f = new Button("Play");
  
  protected final MediaView b = new MediaView();
  
  private final ProgressBar g = new ProgressBar();
  
  private final Tab h = new Tab("Index", (Node)this.e);
  
  private final SampleMediaApp$MediaBox i = new SampleMediaApp$MediaBox(this.b);
  
  private final Tab j = new Tab("Player", (Node)this.i);
  
  private final TabPane k = new TabPane(new Tab[] { this.h, this.j });
  
  public static void main(String[] paramArrayOfString) {
    launch(paramArrayOfString);
  }
  
  public void start(Stage paramStage) {
    paramStage.setTitle("Menu Sample");
    this.c.setFill((Paint)Color.OLDLACE);
    this.f.setOnAction(paramActionEvent -> d());
    String str = "DbSchema Presentation|Understand DbSchema main features|11:46|DbSchema.mp4\nDatabase Connectivity|How to connect to different databases|1:58|ConnectionDialog.mp4\nLayout & HTML Documentation|Create interactive layouts and generate HTML5 documentation|1:27|HTML5Documentation.mp4\nThe Design Model & Team Collaboration|Collaborate on schema design using GIT|3:05|SchemaSynchronization.mp4\nRelational Data Editor & Virtual Foreign Keys|Explore the data from multiple tables using the mouse|1:45|RelationalDataExplorer.mp4\nVisual Query Builder|Build queries using the mouse|1:04|QueryBuilder.mp4\nSQL Editor & Automation Scripts|Run database queries and automation scripts|1:39|SQLEditor.mp4\nData Generator|Fill tables with random generated data|0:39|DataGenerator.mp4\nMongoDB Features|Diagrams, query & data tools for MongoDB|2:32|MongoDB.mp4\nWork With CSV Files|Design and Query CSV Files|1:16|CSVFiles.mp4\nLogical Design|Design the logical schema|2:17|LogicalDesign.mp4\nDbSchemaCLI|A command line client for database operations|1:23|DbSchemaCLI.mp4\nLicensing & Data Privacy|Considerations about licensing and data privacy|2:10|Licensing.mp4";
    for (String str1 : "DbSchema Presentation|Understand DbSchema main features|11:46|DbSchema.mp4\nDatabase Connectivity|How to connect to different databases|1:58|ConnectionDialog.mp4\nLayout & HTML Documentation|Create interactive layouts and generate HTML5 documentation|1:27|HTML5Documentation.mp4\nThe Design Model & Team Collaboration|Collaborate on schema design using GIT|3:05|SchemaSynchronization.mp4\nRelational Data Editor & Virtual Foreign Keys|Explore the data from multiple tables using the mouse|1:45|RelationalDataExplorer.mp4\nVisual Query Builder|Build queries using the mouse|1:04|QueryBuilder.mp4\nSQL Editor & Automation Scripts|Run database queries and automation scripts|1:39|SQLEditor.mp4\nData Generator|Fill tables with random generated data|0:39|DataGenerator.mp4\nMongoDB Features|Diagrams, query & data tools for MongoDB|2:32|MongoDB.mp4\nWork With CSV Files|Design and Query CSV Files|1:16|CSVFiles.mp4\nLogical Design|Design the logical schema|2:17|LogicalDesign.mp4\nDbSchemaCLI|A command line client for database operations|1:23|DbSchemaCLI.mp4\nLicensing & Data Privacy|Considerations about licensing and data privacy|2:10|Licensing.mp4".split("\\r?\\n")) {
      String[] arrayOfString = str1.split("\\|");
      if (arrayOfString.length == 4) {
        SampleMediaApp$MediaEntry sampleMediaApp$MediaEntry = new SampleMediaApp$MediaEntry(this, arrayOfString[0], arrayOfString[1], arrayOfString[2], arrayOfString[3]);
        this.e.getItems().add(sampleMediaApp$MediaEntry);
      } 
    } 
    TableColumn tableColumn1 = new TableColumn("Name");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((SampleMediaApp$MediaEntry)paramCellDataFeatures.getValue()).a));
    tableColumn1.setCellFactory(paramTableColumn -> new SampleMediaApp$1(this));
    TableColumn tableColumn2 = new TableColumn("Duration");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((SampleMediaApp$MediaEntry)paramCellDataFeatures.getValue()).c));
    tableColumn2.setStyle("-fx-alignment: center;");
    TableColumn tableColumn3 = new TableColumn("Description");
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((SampleMediaApp$MediaEntry)paramCellDataFeatures.getValue()).b));
    this.e.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2, tableColumn3 });
    this.e.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    this.e.getStyleClass().add("font-xl");
    this.e.addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() > 1)
            d(); 
        });
    this.a.setOnMouseDragged(paramMouseEvent -> this.a.progressProperty().set(paramMouseEvent.getX() / this.a.getWidth()));
    this.a.addEventHandler(MouseEvent.MOUSE_RELEASED, paramMouseEvent -> this.a.progressProperty().set(paramMouseEvent.getX() / this.a.getWidth()));
    this.c.getWindow().setOnCloseRequest(paramWindowEvent -> {
          if (this.b.getMediaPlayer() != null)
            this.b.getMediaPlayer().stop(); 
          this.c.getWindow().hide();
        });
    this.g.setMaxWidth(Double.MAX_VALUE);
    this.g.setVisible(false);
    this.g.addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> {
          MediaPlayer mediaPlayer = this.b.getMediaPlayer();
          if (mediaPlayer != null) {
            double d = mediaPlayer.getTotalDuration().toMillis() * paramMouseEvent.getX() / this.g.getWidth();
            mediaPlayer.seek(new Duration(d));
          } 
        });
    this.b.setPreserveRatio(true);
    BorderPane borderPane = new BorderPane((Node)this.k);
    this.c.setRoot((Parent)borderPane);
    paramStage.setScene(this.c);
    paramStage.show();
  }
  
  public Parent a() {
    this.k.setPrefSize(1100.0D, 650.0D);
    this.h.setClosable(false);
    this.j.setClosable(false);
    Button button1 = new Button("Up");
    button1.setOnAction(paramActionEvent -> c());
    Button button2 = new Button("Low");
    button2.setOnAction(paramActionEvent -> b());
    BorderPane borderPane = new BorderPane((Node)this.k);
    HBox hBox = new HBox();
    hBox.setSpacing(5.0D);
    hBox.getChildren().addAll((Object[])new Node[] { (Node)this.g, (Node)new h(this), (Node)this.f, (Node)new h(this), (Node)button1, (Node)this.a, (Node)button2 });
    borderPane.setBottom((Node)hBox);
    return (Parent)borderPane;
  }
  
  public void b() {
    this.a.progressProperty().set(Math.max(0.0D, this.a.progressProperty().get() - 0.1D));
  }
  
  public void c() {
    this.a.progressProperty().set(Math.min(1.0D, this.a.progressProperty().get() + 0.1D));
  }
  
  private final ExecutorService l = Executors.newFixedThreadPool(5);
  
  public void d() {
    if (this.k.getSelectionModel().getSelectedItem() == this.h) {
      SampleMediaApp$MediaEntry sampleMediaApp$MediaEntry = (SampleMediaApp$MediaEntry)this.e.getSelectionModel().getSelectedItem();
      if (sampleMediaApp$MediaEntry != null)
        this.l.execute((Runnable)new g(this, sampleMediaApp$MediaEntry)); 
      this.k.getSelectionModel().select(this.j);
    } else {
      MediaPlayer mediaPlayer = this.b.getMediaPlayer();
      if (mediaPlayer != null)
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
          mediaPlayer.pause();
        } else {
          if (mediaPlayer.getCurrentTime().toMillis() == mediaPlayer.getTotalDuration().toMillis())
            mediaPlayer.seek(new Duration(0.0D)); 
          mediaPlayer.play();
        }  
    } 
  }
  
  public void e() {
    MediaPlayer mediaPlayer = this.b.getMediaPlayer();
    if (mediaPlayer != null)
      mediaPlayer.pause(); 
  }
  
  public void f() {
    MediaPlayer mediaPlayer = this.b.getMediaPlayer();
    if (mediaPlayer != null) {
      mediaPlayer.volumeProperty().unbind();
      mediaPlayer.stop();
      mediaPlayer.dispose();
      this.b.setMediaPlayer(null);
    } 
  }
  
  private void g() {
    MediaPlayer mediaPlayer = this.b.getMediaPlayer();
    if (mediaPlayer == null) {
      this.g.setVisible(false);
    } else {
      this.g.setVisible(true);
      this.g.setProgress(mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
    } 
  }
  
  public static void a(String paramString1, String paramString2) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(paramString2);
    alert.setContentText(paramString1);
    alert.showAndWait();
  }
}
