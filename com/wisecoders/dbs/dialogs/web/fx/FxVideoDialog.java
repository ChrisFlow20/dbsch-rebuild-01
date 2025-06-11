package com.wisecoders.dbs.dialogs.web.fx;

import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.ProgressBarWithText;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class FxVideoDialog extends Dialog$ {
  protected final ProgressBar a = new ProgressBar(1.0D);
  
  protected final Rx b = new Rx(FxVideoDialog.class, this);
  
  private final TableView d = new TableView();
  
  private final Button e;
  
  protected final MediaView c = new MediaView();
  
  private final ProgressBarWithText f = new ProgressBarWithText(false);
  
  private final Tab i = new Tab("Index", (Node)this.d);
  
  private final FxVideoDialog$MediaBox j = new FxVideoDialog$MediaBox(this.c);
  
  private final Tab k = new Tab("Player", (Node)this.j);
  
  private final TabPane l = new TabPane(new Tab[] { this.i, this.k });
  
  private a m;
  
  private boolean n;
  
  public FxVideoDialog(Scene paramScene, String paramString) {
    super(paramScene.getWindow());
    this.b.a("flagCanPlay", () -> ((this.l.getSelectionModel().getSelectedItem() == this.i && this.d.getSelectionModel().getSelectedItem() != null) || this.c.getMediaPlayer() != null));
    this.e = this.b.j("play");
    String str = this.b.a("video.list", new String[0]);
    if (str != null)
      for (String str1 : str.split("\\r?\\n")) {
        String[] arrayOfString = str1.split("\\|");
        if (arrayOfString.length == 5) {
          FxVideoDialog$MediaEntry fxVideoDialog$MediaEntry = new FxVideoDialog$MediaEntry(arrayOfString[0], arrayOfString[1], Integer.parseInt(arrayOfString[2]), arrayOfString[3], arrayOfString[4]);
          this.d.getItems().add(fxVideoDialog$MediaEntry);
        } 
      }  
    TableColumn tableColumn1 = new TableColumn("Name");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new SimpleObjectProperty(paramCellDataFeatures.getValue()));
    tableColumn1.setCellFactory(paramTableColumn -> new FxVideoDialog$1(this));
    TableColumn tableColumn2 = new TableColumn("Duration");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((FxVideoDialog$MediaEntry)paramCellDataFeatures.getValue()).c));
    tableColumn2.setStyle("-fx-alignment: center;");
    TableColumn tableColumn3 = new TableColumn("Description");
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyStringWrapper(((FxVideoDialog$MediaEntry)paramCellDataFeatures.getValue()).b));
    tableColumn3.getStyleClass().add("font-italic");
    this.d.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2, tableColumn3 });
    this.d.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    Rx.a(this.d, new double[] { 0.4D, 0.1D, 0.5D });
    this.d.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramFxVideoDialog$MediaEntry1, paramFxVideoDialog$MediaEntry2) -> this.b.b());
    this.d.getStyleClass().add("font-xl");
    this.d.addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() > 1)
            play(); 
        });
    this.a.setOnMouseDragged(paramMouseEvent -> this.a.progressProperty().set(paramMouseEvent.getX() / this.a.getWidth()));
    this.a.addEventHandler(MouseEvent.MOUSE_RELEASED, paramMouseEvent -> this.a.progressProperty().set(paramMouseEvent.getX() / this.a.getWidth()));
    getDialogPane().getScene().getWindow().setOnCloseRequest(paramWindowEvent -> {
          if (this.c.getMediaPlayer() != null)
            this.c.getMediaPlayer().stop(); 
          getDialogPane().getScene().getWindow().hide();
        });
    this.f.setMaxWidth(Double.MAX_VALUE);
    this.f.setVisible(false);
    this.f.addEventHandler(MouseEvent.MOUSE_CLICKED, paramMouseEvent -> {
          MediaPlayer mediaPlayer = this.c.getMediaPlayer();
          if (mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            double d = mediaPlayer.getTotalDuration().toMillis() * paramMouseEvent.getX() / this.f.getWidth();
            mediaPlayer.seek(new Duration(d));
            if (status == MediaPlayer.Status.PLAYING && mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING)
              mediaPlayer.play(); 
          } 
        });
    this.c.setPreserveRatio(true);
    setOnHidden(paramDialogEvent -> {
          if (this.m != null)
            this.m.cancel(); 
        });
  }
  
  public Parent a() {
    setRegionPrefSize((Region)this.l, 1100.0D, 650.0D);
    this.i.setClosable(false);
    this.k.setClosable(false);
    BorderPane borderPane = new BorderPane((Node)this.l);
    borderPane.setBottom((Node)(new HBox$()).i().a(Pos.CENTER).a(new Node[] { (Node)this.f, (Node)new HGrowBox$(), (Node)this.e, (Node)new HGrowBox$(), (Node)this.b



            
            .j("volumeDown"), (Node)this.a, (Node)this.b
            
            .j("volumeUp") }));
    return (Parent)borderPane;
  }
  
  public void createButtons() {}
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public void volumeDown() {
    this.a.progressProperty().set(Math.max(0.0D, this.a.progressProperty().get() - 0.1D));
  }
  
  @Action
  public void volumeUp() {
    this.a.progressProperty().set(Math.min(1.0D, this.a.progressProperty().get() + 0.1D));
  }
  
  @Action(b = "flagCanPlay")
  public void play() {
    if (this.l.getSelectionModel().getSelectedItem() == this.i) {
      FxVideoDialog$MediaEntry fxVideoDialog$MediaEntry = (FxVideoDialog$MediaEntry)this.d.getSelectionModel().getSelectedItem();
      if (fxVideoDialog$MediaEntry != null)
        this.b.a(this.m = new a(this, fxVideoDialog$MediaEntry)); 
      this.l.getSelectionModel().select(this.k);
    } else {
      MediaPlayer mediaPlayer = this.c.getMediaPlayer();
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
  
  private void a(MediaPlayer.Status paramStatus) {
    this.e.setGraphic((paramStatus == MediaPlayer.Status.PLAYING) ? (Node)BootstrapIcons.pause_fill.glyph(new String[] { "glyph-blue", "glyph-32" }) : (Node)BootstrapIcons.play_fill.glyph(new String[] { "glyph-blue", "glyph-32" }));
  }
  
  public void b() {
    MediaPlayer mediaPlayer = this.c.getMediaPlayer();
    if (mediaPlayer != null)
      mediaPlayer.pause(); 
  }
  
  public void c() {
    MediaPlayer mediaPlayer = this.c.getMediaPlayer();
    if (mediaPlayer != null) {
      mediaPlayer.volumeProperty().unbind();
      mediaPlayer.stop();
      mediaPlayer.dispose();
      this.c.setMediaPlayer(null);
    } 
  }
  
  private void a(FxVideoDialog$MediaEntry paramFxVideoDialog$MediaEntry) {
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(paramFxVideoDialog$MediaEntry.e.toURI().toString()));
    try {
      mediaPlayer.currentTimeProperty().addListener((paramObservableValue, paramDuration1, paramDuration2) -> d());
      mediaPlayer.volumeProperty().bind((ObservableValue)this.a.progressProperty());
      mediaPlayer.statusProperty().addListener((paramObservableValue, paramStatus1, paramStatus2) -> a(paramStatus2));
      mediaPlayer.errorProperty().addListener((paramObservableValue, paramMediaException1, paramMediaException2) -> {
            Log.a("Video Player error", (Throwable)paramMediaException2);
            if (this.n) {
              FxUtil.a(1.0D, ());
            } else {
              this.b.b(getDialogScene(), "Please try again to start the video. " + String.valueOf(paramMediaException2), (Throwable)paramMediaException2);
            } 
            this.n = false;
          });
      this.c.setMediaPlayer(mediaPlayer);
      FxUtil.a(0.5D, paramActionEvent -> paramMediaPlayer.play());
      this.b.b();
    } catch (Throwable throwable) {
      mediaPlayer.dispose();
      Log.b(throwable);
      this.b.c(getDialogScene(), throwable.getLocalizedMessage());
    } 
  }
  
  private void d() {
    MediaPlayer mediaPlayer = this.c.getMediaPlayer();
    if (mediaPlayer == null) {
      this.f.setVisible(false);
    } else {
      this.f.setVisible(true);
      this.f.b.setProgress(mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
      this.f.c.setText(StringUtil.durationAsString((long)mediaPlayer.getCurrentTime().toSeconds()) + " of " + StringUtil.durationAsString((long)mediaPlayer.getCurrentTime().toSeconds()));
    } 
  }
}
